package com.fiskmods.lightsabers.common.block;

import static net.minecraftforge.common.util.ForgeDirection.*;

import java.util.List;
import java.util.Random;

import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrystal extends BlockBasic implements ITileEntityProvider
{
    private Random rand = new Random();

    public BlockCrystal()
    {
        super(Material.glass);
        setLightLevel(0.25F);
        setHardness(2.0F);
        setResistance(10.0F);
        setStepSound(Block.soundTypeGlass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subBlocks)
    {
        for (CrystalColor color : CrystalColor.values())
        {
            subBlocks.add(ItemCrystal.create(color));
        }
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
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    private boolean canPlaceAt(World world, int x, int y, int z)
    {
        if (World.doesBlockHaveSolidTopSurface(world, x, y, z))
        {
            return true;
        }
        else
        {
            Block block = world.getBlock(x, y, z);
            return block.canPlaceTorchOnTop(world, x, y, z);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isSideSolid(x - 1, y, z, EAST, true) || world.isSideSolid(x + 1, y, z, WEST, true) || world.isSideSolid(x, y, z - 1, SOUTH, true) || world.isSideSolid(x, y, z + 1, NORTH, true) || canPlaceAt(world, x, y - 1, z) || canPlaceAt(world, x, y + 1, z);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        int j1 = metadata;

        if (side == 0 && canPlaceAt(world, x, y + 1, z))
        {
            j1 = 6;
        }

        if (side == 1 && canPlaceAt(world, x, y - 1, z))
        {
            j1 = 5;
        }

        if (side == 2 && world.isSideSolid(x, y, z + 1, NORTH, true))
        {
            j1 = 4;
        }

        if (side == 3 && world.isSideSolid(x, y, z - 1, SOUTH, true))
        {
            j1 = 3;
        }

        if (side == 4 && world.isSideSolid(x + 1, y, z, WEST, true))
        {
            j1 = 2;
        }

        if (side == 5 && world.isSideSolid(x - 1, y, z, EAST, true))
        {
            j1 = 1;
        }

        return j1;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);

        if (world.getBlockMetadata(x, y, z) == 0)
        {
            onBlockAdded(world, x, y, z);
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) == 0)
        {
            if (world.isSideSolid(x - 1, y, z, EAST, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            }
            else if (world.isSideSolid(x + 1, y, z, WEST, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 2, 2);
            }
            else if (world.isSideSolid(x, y, z - 1, SOUTH, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 3, 2);
            }
            else if (world.isSideSolid(x, y, z + 1, NORTH, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 4, 2);
            }
            else if (canPlaceAt(world, x, y - 1, z))
            {
                world.setBlockMetadataWithNotify(x, y, z, 5, 2);
            }
            else if (canPlaceAt(world, x, y + 1, z))
            {
                world.setBlockMetadataWithNotify(x, y, z, 6, 2);
            }
        }

        func_150109_e(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        func_150108_b(world, x, y, z, block);
    }

    protected boolean func_150108_b(World world, int x, int y, int z, Block block)
    {
        if (func_150109_e(world, x, y, z))
        {
            int l = world.getBlockMetadata(x, y, z);
            boolean flag = false;

            if (!world.isSideSolid(x - 1, y, z, EAST, true) && l == 1)
            {
                flag = true;
            }

            if (!world.isSideSolid(x + 1, y, z, WEST, true) && l == 2)
            {
                flag = true;
            }

            if (!world.isSideSolid(x, y, z - 1, SOUTH, true) && l == 3)
            {
                flag = true;
            }

            if (!world.isSideSolid(x, y, z + 1, NORTH, true) && l == 4)
            {
                flag = true;
            }

            if (!canPlaceAt(world, x, y - 1, z) && l == 5)
            {
                flag = true;
            }

            if (!canPlaceAt(world, x, y + 1, z) && l == 6)
            {
                flag = true;
            }

            if (flag)
            {
                dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    protected boolean func_150109_e(World world, int x, int y, int z)
    {
        if (!canPlaceBlockAt(world, x, y, z))
        {
            if (world.getBlock(x, y, z) == this)
            {
                dropBlockAsItem(world, x, y, z, 1, 0);
                world.setBlockToAir(x, y, z);
            }

            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec3, Vec3 vec31)
    {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float width = f * 6;
        float height = f * 6;

        if (l == 1)
        {
            setBlockBounds(0, 0.5F - width / 2, 0.5F - width / 2, height, 0.5F + width / 2, 0.5F + width / 2);
        }
        else if (l == 2)
        {
            setBlockBounds(1 - height, 0.5F - width / 2, 0.5F - width / 2, 1, 0.5F + width / 2, 0.5F + width / 2);
        }
        else if (l == 3)
        {
            setBlockBounds(0.5F - width / 2, 0.5F - width / 2, 0, 0.5F + width / 2, 0.5F + width / 2, height);
        }
        else if (l == 4)
        {
            setBlockBounds(0.5F - width / 2, 0.5F - width / 2, 1 - height, 0.5F + width / 2, 0.5F + width / 2, 1);
        }
        else if (l == 5)
        {
            setBlockBounds(0.5F - width / 2, 0, 0.5F - width / 2, 0.5F + width / 2, height, 0.5F + width / 2);
        }
        else
        {
            setBlockBounds(0.5F - width / 2, 1 - height, 0.5F - width / 2, 0.5F + width / 2, 1, 0.5F + width / 2);
        }

        return super.collisionRayTrace(world, x, y, z, vec3, vec31);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntityCrystal)
        {
            ((TileEntityCrystal) tile).setColor(ItemCrystal.get(itemstack));
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntityCrystal)
        {
            return ItemCrystal.create(((TileEntityCrystal) tile).getColor());
        }

        return super.getPickBlock(target, world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntityCrystal)
        {
            dropBlockAsItem(world, x, y, z, ItemCrystal.create(((TileEntityCrystal) tile).getColor()));
            world.setBlock(x, y, z, Blocks.air);

            return true;
        }

        return false;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntityCrystal)
        {
            return ((TileEntityCrystal) tile).getColor().color;
        }
        
        return -1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityCrystal();
    }
}
