package com.fiskmods.lightsabers;

import com.fiskmods.lightsabers.client.gui.GuiHandlerAL;
import com.fiskmods.lightsabers.common.command.CommandForce;
import com.fiskmods.lightsabers.common.config.ModConfig;
import com.fiskmods.lightsabers.common.generator.WorldGeneratorStructures;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.proxy.CommonProxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import fiskfille.utils.FiskUtils;
import fiskfille.utils.FiskUtils.Hook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Lightsabers.MODID, name = Lightsabers.NAME, version = Lightsabers.VERSION, dependencies = "required-after:Forge@[10.13.4.1558,);after:" + ALConstants.BATTLEGEAR + ";after:" + ALConstants.DYNAMIC_LIGHTS, guiFactory = "com.fiskmods.lightsabers.client.gui.GuiFactoryAL")
public class Lightsabers
{
    public static final String NAME = "Advanced Lightsabers";
    public static final String MODID = "lightsabers";
    public static final String VERSION = "${version}";

    @Instance
    public static Lightsabers instance;

    @SidedProxy(clientSide = "com.fiskmods.lightsabers.common.proxy.ClientProxy", serverSide = "com.fiskmods.lightsabers.common.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID)
    {
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
        FiskUtils.hook(Hook.PREINIT);
        
        isBattlegearLoaded = Loader.isModLoaded(ALConstants.BATTLEGEAR);
        isDynamicLightsLoaded = Loader.isModLoaded(ALConstants.DYNAMIC_LIGHTS);

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ModConfig.load(config);

        if (config.hasChanged())
        {
            config.save();
        }
        
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FiskUtils.hook(Hook.INIT);
        proxy.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        FiskUtils.hook(Hook.POSTINIT);
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandForce());
    }
}
