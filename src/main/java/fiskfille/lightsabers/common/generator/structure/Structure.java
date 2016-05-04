package fiskfille.lightsabers.common.generator.structure;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public abstract class Structure
{
	protected final World worldObj;
	protected int xCoord;
	protected int yCoord;
	protected int zCoord;
	
	public Structure(World world, int x, int y, int z)
	{
		worldObj = world;
		xCoord = x;
		yCoord = y;
		zCoord = z;
	}

	public abstract void spawnStructure(Random random);
	
	public void setBlock(Block block, int metadata, int x, int y, int z)
	{
		if (yCoord + y > 4)
		{
			worldObj.setBlock(xCoord + x, yCoord + y, zCoord + z, block, metadata, 3);
		}
	}
	
	protected boolean generateStructureChestContents(Random random, int x, int y, int z, WeightedRandomChestContent[] chestContent, int itemsToGenerate)
    {
        int i = xCoord + x;
        int j = yCoord + y;
        int k = zCoord + z;

        if (worldObj.getBlock(i, j, k) != Blocks.chest)
        {
            worldObj.setBlock(i, j, k, Blocks.chest, 0, 2);
            TileEntityChest tile = (TileEntityChest)worldObj.getTileEntity(i, j, k);

            if (tile != null)
            {
                WeightedRandomChestContent.generateChestContents(random, chestContent, tile, itemsToGenerate);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
	
	protected boolean fillStructureInventory(Block block, Random random, int x, int y, int z, WeightedRandomChestContent[] chestContent, int itemsToGenerate)
    {
        int i = xCoord + x;
        int j = yCoord + y;
        int k = zCoord + z;

        if (worldObj.getBlock(i, j, k) == block)
        {
            IInventory inventory = (IInventory)worldObj.getTileEntity(i, j, k);

            if (inventory != null)
            {
                WeightedRandomChestContent.generateChestContents(random, chestContent, inventory, itemsToGenerate);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
