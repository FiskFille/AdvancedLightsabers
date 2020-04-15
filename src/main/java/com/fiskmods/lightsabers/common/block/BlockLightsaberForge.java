package com.fiskmods.lightsabers.common.block;

import java.util.List;
import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightsaberForge extends Block implements ITileEntityProvider
{
    public static final int[][] DIRECTIONS = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    
    public Block block;

    public BlockLightsaberForge(Block block)
    {
        super(Material.iron);
        setHardness(1.5F);
        setResistance(100.0F);
        setHarvestLevel("pickaxe", 0);
        setStepSound(soundTypeMetal);
        this.block = block;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ)
    {
        if (!player.isSneaking())
        {
            player.openGui(Lightsabers.instance, 0, world, x, y, z);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        setBounds(world.getBlockMetadata(x, y, z));
    }

    public void setBounds(int metadata)
    {
        int direction = getDirection(metadata);
        int i = isBlockSideOfPanel(metadata) ? 0 : 1;
        float f = 0.0625F * 13;

        if (direction == 0)
        {
            setBlockBounds(-i, 0, 0, 2 - i, f, 1);
        }
        else if (direction == 1)
        {
            setBlockBounds(0, 0, -i, 1, f, 2 - i);
        }
        else if (direction == 2)
        {
            setBlockBounds(-1 + i, 0, 0, 1 + i, f, 1);
        }
        else if (direction == 3)
        {
            setBlockBounds(0, 0, -1 + i, 1, f, 1 + i);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int direction = getDirection(metadata);

        if (isBlockSideOfPanel(metadata))
        {
            if (world.getBlock(x - DIRECTIONS[direction][0], y, z - DIRECTIONS[direction][1]) != this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else if (world.getBlock(x + DIRECTIONS[direction][0], y, z + DIRECTIONS[direction][1]) != this)
        {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
        return true;
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int i)
    {
        return /* isBlockSideOfPanel(metadata) ? Item.getItemById(0) : */super.getItemDropped(metadata, rand, i);
    }

    public static boolean isBlockSideOfPanel(int metadata)
    {
        return metadata >= 4;
    }

    public static int getDirection(int metadata)
    {
        return metadata % 4;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float f, int i)
    {
//		if (isBlockSideOfPanel(metadata))
        {
            super.dropBlockAsItemWithChance(world, x, y, z, metadata, f, 0);
        }
    }

    @Override
    public int getMobilityFlag()
    {
        return 2;
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && isBlockSideOfPanel(metadata))
        {
            int dir = getDirection(metadata);

            x += DIRECTIONS[dir][0];
            z += DIRECTIONS[dir][1];

            if (world.getBlock(x, y, z) == this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityLightsaberForge();
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return block.getIcon(side, meta);
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
    }
}
