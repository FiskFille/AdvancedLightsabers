package com.fiskmods.lightsabers.common.block;

import java.util.List;
import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.fiskmods.lightsabers.helper.ALHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDisassemblyStation extends BlockContainer
{
    public static final int[][] DIRECTIONS = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    
    private Random rand = new Random();

    public BlockDisassemblyStation()
    {
        super(Material.iron);
        setHardness(5.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 1);
        setStepSound(soundTypeMetal);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntity tile = getBase(world, x, y, z);

            if (tile != null)
            {
                player.openGui(Lightsabers.instance, 4, world, tile.xCoord, tile.yCoord, tile.zCoord);
            }
        }

        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int dir = getRotation(metadata);
        Part part = Part.get(metadata);

        if (part == Part.TOP)
        {
            if (world.getBlock(x, y - 1, z) != this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else if (part == Part.SIDE)
        {
            if (world.getBlock(x - DIRECTIONS[dir][0], y, z - DIRECTIONS[dir][1]) != this || world.getBlock(x, y + 1, z) != this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else if (world.getBlock(x + DIRECTIONS[dir][0], y, z + DIRECTIONS[dir][1]) != this || world.getBlock(x, y + 1, z) != this)
        {
            world.setBlockToAir(x, y, z);

            if (!world.isRemote)
            {
                dropBlockAsItem(world, x, y, z, metadata, 0);
            }
        }
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune)
    {
        return isBasePart(metadata) ? super.getItemDropped(metadata, rand, fortune) : Item.getItemById(0);
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int fortune)
    {
        if (isBasePart(metadata))
        {
            super.dropBlockAsItemWithChance(world, x, y, z, metadata, chance, 0);
        }
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player)
    {
        TileEntity tile;
        
        if (player.capabilities.isCreativeMode && !isBasePart(metadata) && (tile = getBase(world, x, y, z)) != null)
        {
            x = tile.xCoord;
            y = tile.yCoord;
            z = tile.zCoord;
            
            if (world.getBlock(x, y, z) == this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof IInventory)
        {
            IInventory inventory = (IInventory) tile;
            
            for (int i = 0; i < inventory.getSizeInventory(); ++i)
            {
                ItemStack stack = inventory.getStackInSlot(i);

                if (stack != null)
                {
                    ALHelper.dropItem(world, x, y, z, stack, rand);
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, metadata);
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
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        Part part = Part.get(metadata);
        float f = 0.0625F;
        
        if (part == Part.TOP)
        {
            int dir = getRotation(metadata);
            float width = f * 12.4F;
            float height = f * 13;
            float f1 = f;
            
            if (isBasePart(world.getBlockMetadata(x, y - 1, z)))
            {
                f1 = -f * 2;
            }
            
            if (dir == 0)
            {
                setBlockBounds(0, f1, 1 - width, 1, height, 1);
            }
            else if (dir == 1)
            {
                setBlockBounds(0, f1, 0, width, height, 1);
            }
            else if (dir == 2)
            {
                setBlockBounds(0, f1, 0, 1, height, width);
            }
            else if (dir == 3)
            {
                setBlockBounds(1 - width, f1, 0, 1, height, 1);
            }
        }
        else if (part == Part.SIDE)
        {
            setBlockBounds(0, 0, 0, 1, f * 17, 1);
        }
        else
        {
            setBlockBounds(0, 0, 0, 1, f * 14, 1);
        }
    }

    @Override
    public int getMobilityFlag()
    {
        return 2;
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side)
    {
        return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityDisassemblyStation();
    }
    
    @Override
    public boolean hasTileEntity(int metadata)
    {
        return isBasePart(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("iron_block");
    }

    public static int serialize(int rotation, Part part)
    {
        return part.serialize(rotation & 3);
    }

    public static int getRotation(int metadata)
    {
        return metadata & 3;
    }

    public static boolean isBasePart(int metadata)
    {
        return Part.get(metadata) == Part.BASE;
    }
    
    public static TileEntity getBase(World world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int dir = getRotation(metadata);
        Part part = Part.get(metadata);
        
        if (part == Part.BASE)
        {
            return world.getTileEntity(x, y, z);
        }
        else if (part == Part.TOP)
        {
            return getBase(world, x, y - 1, z);
        }

        return getBase(world, x - DIRECTIONS[dir][0], y, z - DIRECTIONS[dir][1]);
    }
    
    public enum Part
    {
        BASE,
        SIDE,
        TOP;
        
        public int serialize(int rotation)
        {
            return rotation | ((ordinal() & 3) << 2);
        }
        
        public static Part get(int metadata)
        {
            return values()[(metadata >> 2) & 3];
        }
    }
}
