package com.fiskmods.lightsabers.common.block;

import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockCrystalGen extends BlockBasic
{
    public BlockCrystalGen()
    {
        super(Material.air);
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        replace(world, x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        replace(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
//        if (block != this)
        {
            replace(world, x, y, z);
        }
    }
    
    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public static void replace(World world, int x, int y, int z)
    {
        if (ModBlocks.lightsaberCrystal.canPlaceBlockAt(world, x, y, z) && world.setBlock(x, y, z, ModBlocks.lightsaberCrystal, 0, 2))
        {
            TileEntityCrystal tile = (TileEntityCrystal) world.getTileEntity(x, y, z);

            if (tile != null)
            {
                Random rand = new Random(world.getSeed() + x * x * 0x4c1906 + x * 0x5ac0db + z * z * 0x4307a7L + z * 0x5f24f ^ 0x3ad8025f ^ y);
                tile.setColor(ItemCrystal.getRandomGen(rand));
            }
        }
        else
        {
            world.setBlock(x, y, z, Blocks.air, 0, 2);
        }
    }
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(Lightsabers.MODID + ":lightsaber_crystal");
    }
}
