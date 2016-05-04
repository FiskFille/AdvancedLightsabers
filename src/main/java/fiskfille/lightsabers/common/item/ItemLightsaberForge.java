package fiskfille.lightsabers.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fiskfille.lightsabers.common.block.BlockLightsaberForge;
import fiskfille.lightsabers.common.block.ModBlocks;

public class ItemLightsaberForge extends ItemBlock
{
    public ItemLightsaberForge(Block block)
    {
        super(block);
    }

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

            BlockLightsaberForge block = (BlockLightsaberForge) ModBlocks.lightsaberForge;
            int direction = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte x1 = 0;
            byte z1 = 0;

            if (direction == 0)
            {
                x1 = -1;
            }

            if (direction == 1)
            {
                z1 = -1;
            }

            if (direction == 2)
            {
                x1 = 1;
            }

            if (direction == 3)
            {
                z1 = 1;
            }
            
            if (player.canPlayerEdit(x, y, z, side, itemstack))
            {
                if (world.isAirBlock(x, y, z) && world.isAirBlock(x + x1, y, z + z1) && player.canPlayerEdit(x + x1, y, z + z1, side, itemstack) && world.isAirBlock(x - x1, y, z - z1) && player.canPlayerEdit(x - x1, y, z - z1, side, itemstack))
                {
                	world.setBlock(x, y, z, block, direction, 3);
                	
                	if (world.getBlock(x, y, z) == block)
                    {
                        world.setBlock(x - x1, y, z - z1, block, direction + 4, 2);
                    }
                	
                	if (world.getBlock(x, y, z) == block)
                    {
                        world.setBlock(x + x1, y, z + z1, block, direction + 8, 2);
                    }
                    
                	world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
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
