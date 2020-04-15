package com.fiskmods.lightsabers.common.item;

import static com.fiskmods.lightsabers.common.block.BlockDisassemblyStation.*;

import com.fiskmods.lightsabers.common.block.BlockDisassemblyStation.Part;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemDisassemblyTable extends ItemBlock
{
    public ItemDisassemblyTable(Block block)
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
        else
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }

            int dir = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5) & 3;
            byte x1 = 0;
            byte z1 = 0;

            if (dir == 0)
            {
                x1 = -1;
            }

            if (dir == 1)
            {
                z1 = -1;
            }

            if (dir == 2)
            {
                x1 = 1;
            }

            if (dir == 3)
            {
                z1 = 1;
            }

            if (player.canPlayerEdit(x, y, z, side, itemstack))
            {
                if (world.isAirBlock(x, y, z) && world.isAirBlock(x + x1, y, z + z1) && player.canPlayerEdit(x + x1, y, z + z1, side, itemstack))
                {
                    world.setBlock(x, y, z, field_150939_a, serialize(dir, Part.BASE), 3);

                    if (world.getBlock(x, y, z) == field_150939_a)
                    {
                        world.setBlock(x + x1, y, z + z1, field_150939_a, serialize(dir, Part.SIDE), 2);
                        world.setBlock(x + x1, y + 1, z + z1, field_150939_a, serialize(dir, Part.TOP), 2);
                        world.setBlock(x, y + 1, z, field_150939_a, serialize(dir, Part.TOP), 2);
                    }

                    world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, field_150939_a.stepSound.func_150496_b(), (field_150939_a.stepSound.getVolume() + 1) / 2, field_150939_a.stepSound.getPitch() * 0.8F);
                    --itemstack.stackSize;
                    return true;
                }
            }

            return false;
        }
    }
}
