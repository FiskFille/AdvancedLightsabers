package fiskfille.lightsabers.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketSithCoffin;
import fiskfille.lightsabers.common.tileentity.TileEntitySithCoffin;

public class BlockSithCoffin extends BlockDirectional implements ITileEntityProvider
{
	public static final int[][] directions = new int[][] {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    private Random rand = new Random();
    
    public BlockSithCoffin()
    {
        super(Material.rock);
        setHardness(50.0F);
		setResistance(2000.0F);
    }
    
    public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return false;
	}
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return -1;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean hasTileEntity()
    {
        return true;
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
    	setBounds(world.getBlockMetadata(x, y, z));
    	super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	setBounds(world.getBlockMetadata(x, y, z));
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        setBounds(world.getBlockMetadata(x, y, z));
    }

    public void setBounds(int metadata)
    {
    	int direction = getDirection(metadata);
		int i = isBlockFrontOfCoffin(metadata) ? 1 : 0;
		float f = 0.9575F;
		float height = 0.0625F * 15;

		if (direction == 0)
		{
			setBlockBounds(0, 0, -i, 1, height, 2 - i);
		}
		else if (direction == 1)
		{
			setBlockBounds(i - 1, 0, 0, 1 + i, height, 1);
		}
		else if (direction == 2)
		{
			setBlockBounds(0, 0, i - 1, 1, height, 1 + i);
		}
		else if (direction == 3)
		{
			setBlockBounds(-i, 0, 0, 2 - i, height, 1);
		}
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int direction = getDirection(metadata);

        if (isBlockFrontOfCoffin(metadata))
        {
            if (world.getBlock(x - directions[direction][0], y, z - directions[direction][1]) != this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else if (world.getBlock(x + directions[direction][0], y, z + directions[direction][1]) != this)
        {
            world.setBlockToAir(x, y, z);

            if (!world.isRemote)
            {
                dropBlockAsItem(world, x, y, z, metadata, 0);
            }
        }
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
    	int metadata = world.getBlockMetadata(x, y, z);
    	int direction = getDirection(metadata);
    	
    	if (isBlockFrontOfCoffin(metadata))
    	{
    		x -= directions[direction][0];
    		z -= directions[direction][1];
    	}
    	
    	TileEntitySithCoffin tile = (TileEntitySithCoffin)world.getTileEntity(x, y, z);
		
		if (tile != null)
		{
			if (!tile.hasBeenOpened || tile.lidOpenTimer == 0 || player.isSneaking())
			{
				sendActionPacket(tile, player, 0);
				return true;
			}
			else if (tile.lidOpenTimer == tile.lidOpenTimerMax)
			{
				player.openGui(Lightsabers.instance, 1, world, x, y, z);
				return true;
			}
		}
		
		return false;
    }
    
    public void sendActionPacket(TileEntitySithCoffin tile, EntityPlayer player, int action)
    {
    	ALNetworkManager.networkWrapper.sendToServer(new PacketSithCoffin(player, tile.xCoord, tile.yCoord, tile.zCoord, action));
    }

    public Item getItemDropped(int metadata, Random rand, int fortune)
    {
        return isBlockFrontOfCoffin(metadata) ? Item.getItemById(0) : super.getItemDropped(metadata, rand, fortune);
    }

    public static boolean isBlockFrontOfCoffin(int metadata)
    {
        return (metadata & 8) != 0;
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float dropChance, int fortune)
    {
        if (!isBlockFrontOfCoffin(metadata))
        {
            super.dropBlockAsItemWithChance(world, x, y, z, metadata, dropChance, 0);
        }
    }

    public int getMobilityFlag()
    {
        return 1;
    }

    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player)
    {
        if (player.capabilities.isCreativeMode && isBlockFrontOfCoffin(metadata))
        {
            int i1 = getDirection(metadata);
            x -= directions[i1][0];
            z -= directions[i1][1];

            if (world.getBlock(x, y, z) == this)
            {
                world.setBlockToAir(x, y, z);
            }
        }
    }
    
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntitySithCoffin();
    }
    
    @Override
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
        blockIcon = par1IIconRegister.registerIcon("obsidian");
    }
}
