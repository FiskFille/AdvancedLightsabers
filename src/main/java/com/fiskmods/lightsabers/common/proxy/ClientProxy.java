package com.fiskmods.lightsabers.common.proxy;

import com.fiskmods.lightsabers.ALReflection;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.gui.GuiOverlay;
import com.fiskmods.lightsabers.client.render.entity.RenderForceLightning;
import com.fiskmods.lightsabers.client.render.entity.RenderLightsaber;
import com.fiskmods.lightsabers.client.render.entity.RenderSithGhost;
import com.fiskmods.lightsabers.client.render.hilt.HiltRendererManager;
import com.fiskmods.lightsabers.client.render.item.RenderItemCrystal;
import com.fiskmods.lightsabers.client.render.item.RenderItemDisassemblyStation;
import com.fiskmods.lightsabers.client.render.item.RenderItemDoubleLightsaber;
import com.fiskmods.lightsabers.client.render.item.RenderItemHolocron;
import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaberForge;
import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaberPart;
import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaberStand;
import com.fiskmods.lightsabers.client.render.item.RenderItemSithCoffin;
import com.fiskmods.lightsabers.client.render.item.RenderItemSithStoneCoffin;
import com.fiskmods.lightsabers.client.render.tile.RenderCrystal;
import com.fiskmods.lightsabers.client.render.tile.RenderDisassemblyStation;
import com.fiskmods.lightsabers.client.render.tile.RenderHolocron;
import com.fiskmods.lightsabers.client.render.tile.RenderLightsaberForge;
import com.fiskmods.lightsabers.client.render.tile.RenderLightsaberStand;
import com.fiskmods.lightsabers.client.render.tile.RenderSithCoffin;
import com.fiskmods.lightsabers.client.render.tile.RenderSithStoneCoffin;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.entity.EntityForceLightning;
import com.fiskmods.lightsabers.common.entity.EntityLightsaber;
import com.fiskmods.lightsabers.common.entity.EntitySithGhost;
import com.fiskmods.lightsabers.common.event.ClientEventHandler;
import com.fiskmods.lightsabers.common.event.ClientEventHandlerBG;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.keybinds.ALKeyBinds;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberStand;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import mods.battlegear2.api.core.BattlegearUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
        ALReflection.client();
        ALKeyBinds.register();

        registerEventHandler(new ClientEventHandler());
        registerEventHandler(new GuiOverlay());

        if (Lightsabers.isBattlegearLoaded)
        {
            BattlegearUtils.RENDER_BUS.register(new ClientEventHandlerBG());
        }
    }

    @Override
    public void init()
    {
        super.init();
        HiltRendererManager.register();
        
        MinecraftForgeClient.registerItemRenderer(ModItems.lightsaber, new RenderItemLightsaber());
        MinecraftForgeClient.registerItemRenderer(ModItems.doubleLightsaber, new RenderItemDoubleLightsaber());
        MinecraftForgeClient.registerItemRenderer(ModItems.emitter, new RenderItemLightsaberPart(PartType.EMITTER));
        MinecraftForgeClient.registerItemRenderer(ModItems.switchSection, new RenderItemLightsaberPart(PartType.SWITCH_SECTION));
        MinecraftForgeClient.registerItemRenderer(ModItems.grip, new RenderItemLightsaberPart(PartType.BODY));
        MinecraftForgeClient.registerItemRenderer(ModItems.pommel, new RenderItemLightsaberPart(PartType.POMMEL));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), new RenderItemCrystal());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeLight), new RenderItemLightsaberForge());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeDark), new RenderItemLightsaberForge());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberStand), RenderItemLightsaberStand.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.disassemblyStation), RenderItemDisassemblyStation.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithCoffin), new RenderItemSithCoffin());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithStoneCoffin), new RenderItemSithStoneCoffin());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.holocron), new RenderItemHolocron());

        RenderingRegistry.registerEntityRenderingHandler(EntityLightsaber.class, new RenderLightsaber());
        RenderingRegistry.registerEntityRenderingHandler(EntitySithGhost.class, new RenderSithGhost());
        RenderingRegistry.registerEntityRenderingHandler(EntityForceLightning.class, new RenderForceLightning());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new RenderCrystal());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLightsaberForge.class, new RenderLightsaberForge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLightsaberStand.class, new RenderLightsaberStand());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisassemblyStation.class, new RenderDisassemblyStation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySithCoffin.class, new RenderSithCoffin());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySithStoneCoffin.class, new RenderSithStoneCoffin());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHolocron.class, new RenderHolocron());
    }
    
    @Override
    public Side getSide()
    {
        return Side.CLIENT;
    }

    @Override
    public float getRenderTick()
    {
        return ClientEventHandler.renderTick;
    }

    @Override
    public EntityPlayer getPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public boolean isClientPlayer(EntityLivingBase entity)
    {
        return entity instanceof EntityPlayer && !(entity instanceof EntityOtherPlayerMP);
    }
}
