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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.tileentity.TileEntityLightsaberForge;

public class BlockLightsaberForge extends Block implements ITileEntityProvider
{
	public static final int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

	public BlockLightsaberForge()
	{
		super(Material.iron);
		setHarvestLevel("pickaxe", 0);
		setStepSound(soundTypeMetal);
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

	public boolean hasTileEntity()
	{
		return true;
	}

	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		int direction = getDirection(metadata);
		float f = 0.0625F;

		if (isBlockSideOfPanel(metadata))
		{
			if (isBlockRightSideOfPanel(metadata) && direction == 2 || isBlockLeftSideOfPanel(metadata) && direction == 0)
			{
				setBlockBounds(0, 0, 0, 1, f * 2, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(f * 13.5F, 0, 0, 1, f * 14, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(f * 7.5F, 0, 0, f * 13.5F, f * 31, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

				for (int i = 1; i <= 29; ++i)
				{
					float f1 = i / 29F;
					setBlockBounds(f * (f1 * 8), 0, 0, f * 7.5F, f * (2 + i), 1);
					super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				}
			}
			else if (isBlockRightSideOfPanel(metadata) && direction == 0 || isBlockLeftSideOfPanel(metadata) && direction == 2)
			{
				setBlockBounds(0, 0, 0, 1, f * 2, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(0, 0, 0, f * 2.5F, f * 14, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(f * 2.5F, 0, 0, f * 8.5F, f * 31, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

				for (int i = 1; i <= 29; ++i)
				{
					float f1 = i / 29F;
					setBlockBounds(f * 2.5F, 0, 0, 1 - f * (f1 * 8), f * (2 + i), 1);
					super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				}
			}
			else if (isBlockRightSideOfPanel(metadata) && direction == 3 || isBlockLeftSideOfPanel(metadata) && direction == 1)
			{
				setBlockBounds(0, 0, 0, 1, f * 2, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(0, 0, f * 13.5F, 1, f * 14, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(0, 0, f * 7.5F, 1, f * 31, f * 13.5F);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

				for (int i = 1; i <= 29; ++i)
				{
					float f1 = i / 29F;
					setBlockBounds(0, 0, f * (f1 * 8), 1, f * (2 + i), f * 7.5F);
					super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				}
			}
			else if (isBlockRightSideOfPanel(metadata) && direction == 1 || isBlockLeftSideOfPanel(metadata) && direction == 3)
			{
				setBlockBounds(0, 0, 0, 1, f * 2, 1);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(0, 0, 0, 1, f * 14, f * 2.5F);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				setBlockBounds(0, 0, f * 2.5F, 1, f * 31, f * 8.5F);
				super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

				for (int i = 1; i <= 29; ++i)
				{
					float f1 = i / 29F;
					setBlockBounds(0, 0, f * 2.5F, 1, f * (2 + i), 1 - f * (f1 * 8));
					super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
				}
			}
		}
		else
		{
			setBlockBoundsBasedOnState(world, x, y, z);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		}

		setBlockBoundsBasedOnState(world, x, y, z);
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
		int i = isBlockLeftSideOfPanel(metadata) ? 0 : (isBlockRightSideOfPanel(metadata) ? 2 : 1);
		float f = 0.9575F;
		float height = 0.0625F * 14;

		if (direction == 0)
		{
			setBlockBounds(-i, 0, 0, 3 - i, height, 1);
		}
		else if (direction == 1)
		{
			setBlockBounds(0, 0, -i, 1, height, 3 - i);
		}
		else if (direction == 2)
		{
			setBlockBounds(-2 + i, 0, 0, 1 + i, height, 1);
		}
		else if (direction == 3)
		{
			setBlockBounds(0, 0, -2 + i, 1, height, 1 + i);
		}
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		int direction = getDirection(metadata);

		if (isBlockSideOfPanel(metadata))
		{
			if (isBlockRightSideOfPanel(metadata))
			{
				if (world.getBlock(x + directions[direction][0], y, z + directions[direction][1]) != this)
				{
					world.setBlockToAir(x, y, z);
				}
			}

			if (isBlockLeftSideOfPanel(metadata))
			{
				if (world.getBlock(x - directions[direction][0], y, z - directions[direction][1]) != this)
				{
					world.setBlockToAir(x, y, z);
				}
			}
		}
		else if (world.getBlock(x + directions[direction][0], y, z + directions[direction][1]) != this || world.getBlock(x - directions[direction][0], y, z - directions[direction][1]) != this)
		{
			world.setBlockToAir(x, y, z);

			if (!world.isRemote)
			{
				this.dropBlockAsItem(world, x, y, z, metadata, 0);
			}
		}
	}

	public Item getItemDropped(int metadata, Random rand, int p_149650_3_)
	{
		return isBlockSideOfPanel(metadata) ? Item.getItemById(0) : super.getItemDropped(metadata, rand, p_149650_3_);
	}

	public static boolean isBlockSideOfPanel(int metadata)
	{
		return isBlockRightSideOfPanel(metadata) || isBlockLeftSideOfPanel(metadata);
	}

	public static boolean isBlockRightSideOfPanel(int metadata)
	{
		return metadata >= 4 && metadata < 8;
	}

	public static boolean isBlockLeftSideOfPanel(int metadata)
	{
		return metadata >= 8;
	}

	public static int getDirection(int metadata)
	{
		return metadata % 4;
	}

	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float p_149690_6_, int p_149690_7_)
	{
		if (!isBlockSideOfPanel(metadata))
		{
			super.dropBlockAsItemWithChance(world, x, y, z, metadata, p_149690_6_, 0);
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

			if (isBlockRightSideOfPanel(metadata))
			{
				x += directions[direction][0];
				z += directions[direction][1];

				if (world.getBlock(x, y, z) == this)
				{
					world.setBlockToAir(x, y, z);
				}
			}

			if (isBlockLeftSideOfPanel(metadata))
			{
				x -= directions[direction][0];
				z -= directions[direction][1];

				if (world.getBlock(x, y, z) == this)
				{
					world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityLightsaberForge();
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IIconRegister)
	{
		blockIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":dark_forcestone");
	}
}
