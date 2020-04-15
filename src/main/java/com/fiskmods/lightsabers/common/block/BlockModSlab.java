package com.fiskmods.lightsabers.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockModSlab extends BlockSlab
{
    public static final String[] field_150006_b = new String[] {"light", "dark"};
    
    @SideOnly(Side.CLIENT)
    private IIcon field_150007_M;

    public BlockModSlab(boolean flag)
    {
        super(flag, Material.rock);
        useNeighborBrightness = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        int k = metadata & 7;

        if (field_150004_a && (metadata & 8) != 0)
        {
            side = 1;
        }

        return k == 0 ? ModBlocks.lightForcestone.getBlockTextureFromSide(side) : ModBlocks.darkForcestone.getBlockTextureFromSide(side);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.forcestoneSlab);
    }

    @Override
    protected ItemStack createStackedBlock(int p_149644_1_)
    {
        return new ItemStack(Item.getItemFromBlock(ModBlocks.forcestoneSlab), 2, p_149644_1_ & 7);
    }

    @Override
    public String func_150002_b(int p_150002_1_)
    {
        if (p_150002_1_ < 0 || p_150002_1_ >= field_150006_b.length)
        {
            p_150002_1_ = 0;
        }

        return super.getUnlocalizedName() + "." + field_150006_b[p_150002_1_];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        if (item != Item.getItemFromBlock(ModBlocks.forcestoneDoubleSlab))
        {
            for (int i = 0; i < field_150006_b.length; ++i)
            {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(ModBlocks.forcestoneSlab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        if (field_150004_a)
        {
            return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
        }
        else if (p_149646_5_ != 1 && p_149646_5_ != 0 && !super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_))
        {
            return false;
        }
        else
        {
            int i1 = p_149646_2_ + Facing.offsetsXForSide[Facing.oppositeSide[p_149646_5_]];
            int j1 = p_149646_3_ + Facing.offsetsYForSide[Facing.oppositeSide[p_149646_5_]];
            int k1 = p_149646_4_ + Facing.offsetsZForSide[Facing.oppositeSide[p_149646_5_]];
            boolean flag = (p_149646_1_.getBlockMetadata(i1, j1, k1) & 8) != 0;
            return flag ? (p_149646_5_ == 0 ? true : (p_149646_5_ == 1 && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_) ? true : !func_150003_a(p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_)) || (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) & 8) == 0)) : (p_149646_5_ == 1 ? true : (p_149646_5_ == 0 && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_) ? true : !func_150003_a(p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_)) || (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) & 8) != 0));
        }
    }

    @SideOnly(Side.CLIENT)
    private static boolean func_150003_a(Block p_150003_0_)
    {
        return p_150003_0_ == ModBlocks.forcestoneSlab;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
    }
}
