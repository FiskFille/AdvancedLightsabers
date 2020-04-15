//package com.fiskmods.lightsabers.common.item;
//
//import com.fiskmods.lightsabers.common.block.BlockLightsaberStand;
//
//import net.minecraft.block.Block;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
//import net.minecraft.world.World;
//import net.minecraftforge.common.util.ForgeDirection;
//
//public class ItemLightsaberStand extends ItemBlock
//{
//    public ItemLightsaberStand(Block block)
//    {
//        super(block);
//    }
//
//    @Override
//    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
//    {
//        Block block = world.getBlock(x, y, z);
//
//        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
//        {
//            side = 1;
//        }
//        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z))
//        {
//            if (side == 0)
//            {
//                --y;
//            }
//
//            if (side == 1)
//            {
//                ++y;
//            }
//
//            if (side == 2)
//            {
//                --z;
//            }
//
//            if (side == 3)
//            {
//                ++z;
//            }
//
//            if (side == 4)
//            {
//                --x;
//            }
//
//            if (side == 5)
//            {
//                ++x;
//            }
//        }
//
//        if (stack.stackSize == 0)
//        {
//            return false;
//        }
//        else if (!player.canPlayerEdit(x, y, z, side, stack))
//        {
//            return false;
//        }
//        else if (y == 255 && field_150939_a.getMaterial().isSolid())
//        {
//            return false;
//        }
//        else if (world.canPlaceEntityOnSide(field_150939_a, x, y, z, false, side, player, stack))
//        {
//            int metadata = field_150939_a.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, getMetadata(stack.getItemDamage()));
//            ForgeDirection dir = BlockLightsaberStand.getDirection(metadata);
//            
//            if (world.isSideSolid(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite(), false) && placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
//            {
//                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, field_150939_a.stepSound.func_150496_b(), (field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, field_150939_a.stepSound.getPitch() * 0.8F);
//                --stack.stackSize;
//            }
//
//            return true;
//        }
//        
//        return false;
//    }
//}
