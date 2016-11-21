package fiskfille.lightsabers.common.block;

import java.util.List;
import java.util.Random;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.tileentity.TileEntityLightsaberForge;

public class BlockLightsaberForge extends Block implements ITileEntityProvider
{
	public static final int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
	public Block block;
	
	public BlockLightsaberForge(Block block)
	{
		super(Material.iron);
		setHarvestLevel("pickaxe", 0);
		setStepSound(soundTypeMetal);
		this.block = block;
	}

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
	
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
		setBlockBoundsBasedOnState(world, x, y, z);
    	super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

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

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		int direction = getDirection(metadata);

		if (isBlockSideOfPanel(metadata))
		{
			if (world.getBlock(x - directions[direction][0], y, z - directions[direction][1]) != this)
			{
				world.setBlockToAir(x, y, z);
				
//				if (!world.isRemote)
//				{
//					dropBlockAsItem(world, x, y, z, metadata, 0);
//				}
			}
		}
		else if (world.getBlock(x + directions[direction][0], y, z + directions[direction][1]) != this)
		{
			world.setBlockToAir(x, y, z);

//			if (!world.isRemote)
//			{
//				dropBlockAsItem(world, x, y, z, metadata, 0);
//			}
		}
	}

	public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return true;
	}

	public Item getItemDropped(int metadata, Random rand, int i)
	{
		return /*isBlockSideOfPanel(metadata) ? Item.getItemById(0) : */super.getItemDropped(metadata, rand, i);
	}

	public static boolean isBlockSideOfPanel(int metadata)
	{
		return metadata >= 4;
	}

	public static int getDirection(int metadata)
	{
		return metadata % 4;
	}

	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float f, int i)
	{
//		if (isBlockSideOfPanel(metadata))
		{
			super.dropBlockAsItemWithChance(world, x, y, z, metadata, f, 0);
		}
	}

	public int getMobilityFlag()
	{
		return 1;
	}

	public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode && isBlockSideOfPanel(metadata))
		{
			int direction = getDirection(metadata);

			x += directions[direction][0];
			z += directions[direction][1];

			if (world.getBlock(x, y, z) == this)
			{
				world.setBlockToAir(x, y, z);
			}
		}
	}

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
