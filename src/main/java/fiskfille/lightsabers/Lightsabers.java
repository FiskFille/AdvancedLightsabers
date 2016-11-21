package fiskfille.lightsabers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import fiskfille.lightsabers.client.gui.GuiHandlerAL;
import fiskfille.lightsabers.common.config.ModConfig;
import fiskfille.lightsabers.common.generator.WorldGeneratorOres;
import fiskfille.lightsabers.common.generator.WorldGeneratorStructures;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.proxy.CommonProxy;

@Mod(modid = Lightsabers.modid, name = "Advanced Lightsabers", version = Lightsabers.version, guiFactory = "fiskfille.lightsabers.client.gui.GuiFactoryAL")
public class Lightsabers
{
	public static final String modid = "lightsabers";
	public static final String version = "${version}";

	@Instance(Lightsabers.modid)
	public static Lightsabers instance;

	@SidedProxy(clientSide = "fiskfille.lightsabers.common.proxy.ClientProxy", serverSide = "fiskfille.lightsabers.common.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tabLightsabers = new CreativeTabs(CreativeTabs.getNextID(), "Advanced Lightsabers")
	{
		@Override
		public String getTranslatedTabLabel()
		{
			return "Advanced Lightsabers";
		}

		@Override
		public Item getTabIconItem()
		{
			return ModItems.lightsaber;
		}
	};

	public static boolean isBattlegearLoaded;
	public static boolean isDynamicLightsLoaded;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		isBattlegearLoaded = Loader.isModLoaded("battlegear2");
		isDynamicLightsLoaded = Loader.isModLoaded("DynamicLights");

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ModConfig.load(config);

		if (config.hasChanged())
		{
			config.save();
		}

		ALNetworkManager.registerPackets();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerAL());
		GameRegistry.registerWorldGenerator(new WorldGeneratorStructures(), 0);
		GameRegistry.registerWorldGenerator(new WorldGeneratorOres(), 0);
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}
}
