package fiskfille.lightsabers.common.proxy;

import mods.battlegear2.api.core.BattlegearUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.LightsaberAPIClient;
import fiskfille.lightsabers.client.gui.GuiOverlay;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyDroideka;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyFulcrum;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyFury;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyGraflex;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyJuggernaut;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyKnighted;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyMandalorian;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyMauler;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyMechanical;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyProdigalSon;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyRedeemer;
import fiskfille.lightsabers.client.model.lightsaber.ModelBodyVaid;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterDroideka;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterFulcrum;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterFury;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterGraflex;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterJuggernaut;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterKnighted;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterMandalorian;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterMauler;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterMechanical;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterProdigalSon;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterRedeemer;
import fiskfille.lightsabers.client.model.lightsaber.ModelEmitterVaid;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelDroideka;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelFulcrum;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelFury;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelGraflex;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelJuggernaut;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelKnighted;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelMandalorian;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelMauler;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelMechanical;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelProdigalSon;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelRedeemer;
import fiskfille.lightsabers.client.model.lightsaber.ModelPommelVaid;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionDroideka;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionFulcrum;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionFury;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionGraflex;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionJuggernaut;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionKnighted;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionMandalorian;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionMauler;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionMechanical;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionProdigalSon;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionRedeemer;
import fiskfille.lightsabers.client.model.lightsaber.ModelSwitchSectionVaid;
import fiskfille.lightsabers.client.render.entity.RenderDoubleLightsaberEntity;
import fiskfille.lightsabers.client.render.entity.RenderForceLightning;
import fiskfille.lightsabers.client.render.entity.RenderLightsaberEntity;
import fiskfille.lightsabers.client.render.entity.RenderSithGhost;
import fiskfille.lightsabers.client.render.item.RenderDoubleLightsaber;
import fiskfille.lightsabers.client.render.item.RenderItemHolocron;
import fiskfille.lightsabers.client.render.item.RenderItemLightsaberForge;
import fiskfille.lightsabers.client.render.item.RenderItemSithCoffin;
import fiskfille.lightsabers.client.render.item.RenderItemSithStoneCoffin;
import fiskfille.lightsabers.client.render.item.RenderLightsaber;
import fiskfille.lightsabers.client.render.item.RenderLightsaberPart;
import fiskfille.lightsabers.client.render.tile.RenderCrystal;
import fiskfille.lightsabers.client.render.tile.RenderHolocron;
import fiskfille.lightsabers.client.render.tile.RenderLightsaberForge;
import fiskfille.lightsabers.client.render.tile.RenderSithCoffin;
import fiskfille.lightsabers.client.render.tile.RenderSithStoneCoffin;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.entity.EntityDoubleLightsaber;
import fiskfille.lightsabers.common.entity.EntityForceLightning;
import fiskfille.lightsabers.common.entity.EntityLightsaber;
import fiskfille.lightsabers.common.entity.EntitySithGhost;
import fiskfille.lightsabers.common.event.ClientEventHandler;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.keybinds.ALKeyBinds;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;
import fiskfille.lightsabers.common.lightsaber.LightsaberManager;
import fiskfille.lightsabers.common.tileentity.TileEntityCrystal;
import fiskfille.lightsabers.common.tileentity.TileEntityHolocron;
import fiskfille.lightsabers.common.tileentity.TileEntityLightsaberForge;
import fiskfille.lightsabers.common.tileentity.TileEntitySithCoffin;
import fiskfille.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

public class ClientProxy extends CommonProxy
{	
	public void preInit()
	{
		super.preInit();
		ALKeyBinds.register();
		
		registerEventHandler(new ClientEventHandler());
		registerEventHandler(new GuiOverlay());
		
		if (Lightsabers.isBattlegearLoaded)
		{
			BattlegearUtils.RENDER_BUS.register(new fiskfille.lightsabers.common.event.ClientEventHandlerBG());
		}
	}
	
	public void init()
	{
		super.init();
		
		registerLightsaberModels(LightsaberManager.lightsaberGraflex, new ModelEmitterGraflex(), new ModelSwitchSectionGraflex(), new ModelBodyGraflex(), new ModelPommelGraflex());
		registerLightsaberModels(LightsaberManager.lightsaberRedeemer, new ModelEmitterRedeemer(), new ModelSwitchSectionRedeemer(), new ModelBodyRedeemer(), new ModelPommelRedeemer());
		registerLightsaberModels(LightsaberManager.lightsaberMauler, new ModelEmitterMauler(), new ModelSwitchSectionMauler(), new ModelBodyMauler(), new ModelPommelMauler());
		registerLightsaberModels(LightsaberManager.lightsaberProdigalSon, new ModelEmitterProdigalSon(), new ModelSwitchSectionProdigalSon(), new ModelBodyProdigalSon(), new ModelPommelProdigalSon());
		registerLightsaberModels(LightsaberManager.lightsaberKnighted, new ModelEmitterKnighted(), new ModelSwitchSectionKnighted(), new ModelBodyKnighted(), new ModelPommelKnighted());
		registerLightsaberModels(LightsaberManager.lightsaberVaidAncient, new ModelEmitterVaid(), new ModelSwitchSectionVaid(), new ModelBodyVaid(), new ModelPommelVaid());
		registerLightsaberModels(LightsaberManager.lightsaberVaidModern, new ModelEmitterVaid(), new ModelSwitchSectionVaid(), new ModelBodyVaid(), new ModelPommelVaid());
		registerLightsaberModels(LightsaberManager.lightsaberDroideka, new ModelEmitterDroideka(), new ModelSwitchSectionDroideka(), new ModelBodyDroideka(), new ModelPommelDroideka());
		registerLightsaberModels(LightsaberManager.lightsaberFulcrum, new ModelEmitterFulcrum(), new ModelSwitchSectionFulcrum(), new ModelBodyFulcrum(), new ModelPommelFulcrum());
		registerLightsaberModels(LightsaberManager.lightsaberJuggernaut, new ModelEmitterJuggernaut(), new ModelSwitchSectionJuggernaut(), new ModelBodyJuggernaut(), new ModelPommelJuggernaut());
		registerLightsaberModels(LightsaberManager.lightsaberMechanical, new ModelEmitterMechanical(), new ModelSwitchSectionMechanical(), new ModelBodyMechanical(), new ModelPommelMechanical());
		registerLightsaberModels(LightsaberManager.lightsaberMandalorian, new ModelEmitterMandalorian(), new ModelSwitchSectionMandalorian(), new ModelBodyMandalorian(), new ModelPommelMandalorian());
		registerLightsaberModels(LightsaberManager.lightsaberFury, new ModelEmitterFury(), new ModelSwitchSectionFury(), new ModelBodyFury(), new ModelPommelFury());
		
		MinecraftForgeClient.registerItemRenderer(ModItems.lightsaber, new RenderLightsaber());
		MinecraftForgeClient.registerItemRenderer(ModItems.doubleLightsaber, new RenderDoubleLightsaber());
		MinecraftForgeClient.registerItemRenderer(ModItems.lightsaberEmitter, new RenderLightsaberPart(EnumPartType.EMITTER));
		MinecraftForgeClient.registerItemRenderer(ModItems.lightsaberSwitchSection, new RenderLightsaberPart(EnumPartType.SWITCH_SECTION));
		MinecraftForgeClient.registerItemRenderer(ModItems.lightsaberBody, new RenderLightsaberPart(EnumPartType.BODY));
		MinecraftForgeClient.registerItemRenderer(ModItems.lightsaberPommel, new RenderLightsaberPart(EnumPartType.POMMEL));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeLight), new RenderItemLightsaberForge());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeDark), new RenderItemLightsaberForge());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithCoffin), new RenderItemSithCoffin());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithStoneCoffin), new RenderItemSithStoneCoffin());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.holocron), new RenderItemHolocron());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityLightsaber.class, new RenderLightsaberEntity());
		RenderingRegistry.registerEntityRenderingHandler(EntityDoubleLightsaber.class, new RenderDoubleLightsaberEntity());
		RenderingRegistry.registerEntityRenderingHandler(EntitySithGhost.class, new RenderSithGhost());
		RenderingRegistry.registerEntityRenderingHandler(EntityForceLightning.class, new RenderForceLightning());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new RenderCrystal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLightsaberForge.class, new RenderLightsaberForge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySithCoffin.class, new RenderSithCoffin());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySithStoneCoffin.class, new RenderSithStoneCoffin());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHolocron.class, new RenderHolocron());
	}
	
	public void registerLightsaberModels(Lightsaber lightsaber, ModelBase emitter, ModelBase switchSection, ModelBase body, ModelBase pommel)
	{
		LightsaberAPIClient.registerLightsaberModel(lightsaber, EnumPartType.EMITTER, emitter);
		LightsaberAPIClient.registerLightsaberModel(lightsaber, EnumPartType.SWITCH_SECTION, switchSection);
		LightsaberAPIClient.registerLightsaberModel(lightsaber, EnumPartType.BODY, body);
		LightsaberAPIClient.registerLightsaberModel(lightsaber, EnumPartType.POMMEL, pommel);
	}
	
	public EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
	
	public boolean isClientPlayer(EntityPlayer player)
	{
		return player == getPlayer();
	}
	
	public float getRenderTicks()
	{
		return ClientEventHandler.RENDER_TICK;
	}
}
