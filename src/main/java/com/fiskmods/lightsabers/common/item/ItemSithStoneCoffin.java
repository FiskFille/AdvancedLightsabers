package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemSithStoneCoffin extends ItemBlock
{
    public ItemSithStoneCoffin(Block block)
    {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3))
        {
            return false;
        }
        else
        {
            TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(x, y, z);

            if (tile != null)
            {
                if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("Equipment"))
                {
                    NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("Equipment");
                    tile.equipment = ItemStack.loadItemStackFromNBT(nbttagcompound);
                }

                tile.taskFinished = true;
            }
        }

        if (world.getBlock(x, y, z) == field_150939_a)
        {
            field_150939_a.onBlockPlacedBy(world, x, y, z, player, itemstack);
            field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
        }

        return true;
    }
}
