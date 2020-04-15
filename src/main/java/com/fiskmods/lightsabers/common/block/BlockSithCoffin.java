package com.fiskmods.lightsabers.common.block;

import java.util.List;
import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.network.PacketTileAction;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSithCoffin extends BlockContainer implements ITileEntityProvider
{
    public static final int[][] DIRECTIONS = new int[][] {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    
    private Random rand = new Random();

    public BlockSithCoffin()
    {
        super(Material.rock);
        setHardness(50.0F);
        setResistance(2000.0F);
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
        return false;
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
        int metadata = world.getBlockMetadata(x, y, z);
        int i = isBlockFrontOfCoffin(metadata) ? 1 : 0;
        int dir = getDirection(metadata);
        float f = 0.9575F;
        float height = 0.0625F * 15;

        if (dir == 0)
        {
            setBlockBounds(0, 0, -i, 1, height, 2 - i);
        }
        else if (dir == 1)
        {
            setBlockBounds(i - 1, 0, 0, 1 + i, height, 1);
        }
        else if (dir == 2)
        {
            setBlockBounds(0, 0, i - 1, 1, height, 1 + i);
        }
        else if (dir == 3)
        {
            setBlockBounds(-i, 0, 0, 2 - i, height, 1);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int dir = getDirection(metadata);

        if (isBlockFrontOfCoffin(metadata))
        {
            if (world.getBlock(x - DIRECTIONS[dir][0], y, z - DIRECTIONS[dir][1]) != this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else if (world.getBlock(x + DIRECTIONS[dir][0], y, z + DIRECTIONS[dir][1]) != this)
        {
            world.setBlockToAir(x, y, z);

            if (!world.isRemote)
            {
                dropBlockAsItem(world, x, y, z, metadata, 0);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int dir = getDirection(metadata);

        if (isBlockFrontOfCoffin(metadata))
        {
            x -= DIRECTIONS[dir][0];
            z -= DIRECTIONS[dir][1];
        }

        TileEntitySithCoffin tile = (TileEntitySithCoffin) world.getTileEntity(x, y, z);

        if (tile != null)
        {
            if (!tile.hasBeenOpened || tile.lidOpenTimer == 0 || player.isSneaking())
            {
                sendActionPacket(tile, player, 0);
                return true;
            }
            else if (tile.lidOpenTimer == TileEntitySithCoffin.LID_OPEN_MAX)
            {
                player.openGui(Lightsabers.instance, 1, world, x, y, z);
                return true;
            }
        }

        return false;
    }

    public void sendActionPacket(TileEntitySithCoffin tile, EntityPlayer player, int action)
    {
        if (player.worldObj.isRemote)
        {
            ALNetworkManager.wrapper.sendToServer(new PacketTileAction(player, tile.xCoord, tile.yCoord, tile.zCoord, action));
        }
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune)
    {
        return isBlockFrontOfCoffin(metadata) ? Item.getItemById(0) : super.getItemDropped(metadata, rand, fortune);
    }

    public static boolean isBlockFrontOfCoffin(int metadata)
    {
        return (metadata & 8) != 0;
    }

    public static int getDirection(int metadata)
    {
        return metadata & 3;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float dropChance, int fortune)
    {
        if (!isBlockFrontOfCoffin(metadata))
        {
            super.dropBlockAsItemWithChance(world, x, y, z, metadata, dropChance, 0);
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
        if (player.capabilities.isCreativeMode && isBlockFrontOfCoffin(metadata))
        {
            int dir = getDirection(metadata);
            x -= DIRECTIONS[dir][0];
            z -= DIRECTIONS[dir][1];

            if (world.getBlock(x, y, z) == this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntitySithCoffin();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("obsidian");
    }
}
