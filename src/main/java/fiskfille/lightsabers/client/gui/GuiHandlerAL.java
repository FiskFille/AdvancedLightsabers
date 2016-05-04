package fiskfille.lightsabers.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.container.ContainerLightsaberForge;
import fiskfille.lightsabers.common.container.ContainerSithCoffin;
import fiskfille.lightsabers.common.tileentity.TileEntitySithCoffin;

public class GuiHandlerAL implements IGuiHandler
{
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        switch (id)
        {
            case 0:
                return world.getBlock(x, y, z) == ModBlocks.lightsaberForge ? new ContainerLightsaberForge(player.inventory, world, x, y, z) : null;
            case 1:
                return world.getBlock(x, y, z) == ModBlocks.sithCoffin ? new ContainerSithCoffin(player.inventory, (TileEntitySithCoffin)tile) : null;
        }

        return null;
    }

    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        switch (id)
        {
            case 0:
                return world.getBlock(x, y, z) == ModBlocks.lightsaberForge ? new GuiLightsaberForge(player.inventory, world, x, y, z) : null;
            case 1:
                return world.getBlock(x, y, z) == ModBlocks.sithCoffin ? new GuiSithCoffin(player.inventory, (TileEntitySithCoffin)tile) : null;
        }

        return null;
    }
}
