package com.fiskmods.lightsabers.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSithCoffin extends ItemBlock
{
    public ItemSithCoffin(Block block)
    {
        super(block);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float f, float f1, float f2)
    {
        if (world.isRemote)
        {
            return true;
        }
        else if (side != 1)
        {
            return false;
        }
        else
        {
            int dir = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5) & 3;
            byte x1 = 0;
            byte z1 = 0;

            if (dir == 0)
            {
                z1 = 1;
            }

            if (dir == 1)
            {
                x1 = -1;
            }

            if (dir == 2)
            {
                z1 = -1;
            }

            if (dir == 3)
            {
                x1 = 1;
            }
            
            ++y;

            if (player.canPlayerEdit(x, y, z, side, itemstack) && player.canPlayerEdit(x + x1, y, z + z1, side, itemstack))
            {
                if (world.isAirBlock(x, y, z) && world.isAirBlock(x + x1, y, z + z1) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + x1, y - 1, z + z1))
                {
                    world.setBlock(x, y, z, field_150939_a, dir, 3);

                    if (world.getBlock(x, y, z) == field_150939_a)
                    {
                        world.setBlock(x + x1, y, z + z1, field_150939_a, dir + 8, 3);
                    }

                    --itemstack.stackSize;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
