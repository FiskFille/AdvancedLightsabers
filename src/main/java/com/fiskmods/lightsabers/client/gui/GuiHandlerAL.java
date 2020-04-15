package com.fiskmods.lightsabers.client.gui;

import com.fiskmods.lightsabers.common.block.BlockLightsaberForge;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.container.ContainerCrystalPouch;
import com.fiskmods.lightsabers.common.container.ContainerDisassemblyStation;
import com.fiskmods.lightsabers.common.container.ContainerLightsaberForge;
import com.fiskmods.lightsabers.common.container.ContainerSithCoffin;
import com.fiskmods.lightsabers.common.container.InventoryCrystalPouch;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerAL implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        switch (id)
        {
        case 0:
            return world.getBlock(x, y, z) instanceof BlockLightsaberForge ? new ContainerLightsaberForge(player.inventory, (TileEntityLightsaberForge) tile) : null;
        case 1:
            return world.getBlock(x, y, z) == ModBlocks.sithCoffin ? new ContainerSithCoffin(player.inventory, (TileEntitySithCoffin) tile) : null;
        case 3:
            return new ContainerCrystalPouch(player.inventory, new InventoryCrystalPouch(player, x));
        case 4:
            return tile instanceof TileEntityDisassemblyStation ? new ContainerDisassemblyStation(player.inventory, (TileEntityDisassemblyStation) tile) : null;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        switch (id)
        {
        case 0:
            return world.getBlock(x, y, z) instanceof BlockLightsaberForge ? new GuiLightsaberForge(player.inventory, (TileEntityLightsaberForge) tile) : null;
        case 1:
            return world.getBlock(x, y, z) == ModBlocks.sithCoffin ? new GuiSithCoffin(player.inventory, (TileEntitySithCoffin) tile) : null;
        case 2:
            return world.getBlock(x, y, z) == ModBlocks.holocron ? new GuiForcePowers(null, player, (TileEntityHolocron) tile) : null;
        case 3:
            return new GuiCrystalPouch(player.inventory, new InventoryCrystalPouch(player, x));
        case 4:
            return tile instanceof TileEntityDisassemblyStation ? new GuiDisassemblyStation(player.inventory, (TileEntityDisassemblyStation) tile) : null;
        }

        return null;
    }
}
