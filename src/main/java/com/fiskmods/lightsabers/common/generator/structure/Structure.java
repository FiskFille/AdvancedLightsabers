package com.fiskmods.lightsabers.common.generator.structure;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

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

    protected boolean mirrorX;
    protected boolean mirrorZ;

    protected int maxY;
    protected boolean simulate = false;
    protected List<StructurePoint> coverage = Lists.newArrayList();

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
        if (mirrorX && x > 0)
        {
            setBlock(xCoord - x, yCoord + y, zCoord + z, block, StructureHelper.mirrorMetadata(block, metadata));
        }

        if (mirrorZ && z > 0)
        {
            setBlock(xCoord + x, yCoord + y, zCoord - z, block, StructureHelper.mirrorMetadata(block, metadata));
        }

        setBlock(xCoord + x, yCoord + y, zCoord + z, block, metadata);
    }

    private void setBlock(int x, int y, int z, Block block, int metadata)
    {
        Block block1 = null;
        
        if (simulate || ((block1 = worldObj.getBlock(x, y, z)) != block || worldObj.getBlockMetadata(x, y, z) != metadata) && block1.getBlockHardness(worldObj, x, y, z) != -1)
        {
            if (simulate)
            {
                maxY = Math.max(maxY, y);

                StructurePoint p = new StructurePoint(x, y, z);

                if (coverage.contains(p))
                {
                    for (int i = 0; i < coverage.size(); ++i)
                    {
                        StructurePoint p1 = coverage.get(i);

                        if (p.equals(p1))
                        {
                            p1.posY = Math.min(p1.posY, y);
                            break;
                        }
                    }
                }
                else
                {
                    coverage.add(p);
                }
            }
            else
            {
                placeBlock(x, y, z, block, metadata, 2);
            }
        }
    }
    
    public void placeBlock(int x, int y, int z, Block block, int metadata, int flags)
    {
        worldObj.setBlock(x, y, z, block, metadata, flags);
    }

    protected boolean generateStructureChestContents(Random random, int x, int y, int z, WeightedRandomChestContent[] chestContent, int itemsToGenerate)
    {
        int i = xCoord + x;
        int j = yCoord + y;
        int k = zCoord + z;

        if (worldObj.getBlock(i, j, k) != Blocks.chest)
        {
            worldObj.setBlock(i, j, k, Blocks.chest, 0, 2);
            TileEntityChest tile = (TileEntityChest) worldObj.getTileEntity(i, j, k);

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
            IInventory inventory = (IInventory) worldObj.getTileEntity(i, j, k);

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
