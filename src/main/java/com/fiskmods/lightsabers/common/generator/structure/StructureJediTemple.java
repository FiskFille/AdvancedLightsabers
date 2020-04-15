package com.fiskmods.lightsabers.common.generator.structure;

import static com.fiskmods.lightsabers.common.generator.ModChestGen.*;

import java.util.Random;

import com.fiskmods.lightsabers.common.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;

public class StructureJediTemple extends Structure
{
    private final float ruination;
    private Random rand;
    
    public StructureJediTemple(float ruin, World world, int x, int y, int z)
    {
        super(world, x, y, z);
        ruination = ruin;
    }
    
    @Override
    public void placeBlock(int x, int y, int z, Block block, int metadata, int flags)
    {
        if (ruination > 0)
        {
            if (block == Blocks.leaves || block == Blocks.water)
            {
                return;
            }
            else if (block.getMaterial() == Material.glass && rand.nextFloat() * rand.nextFloat() <= ruination)
            {
                return;
            }
            
            if (block == ModBlocks.lightForcestone && metadata == 0 && rand.nextFloat() < ruination)
            {
                if (rand.nextBoolean())
                {
                    super.placeBlock(x, y, z, ModBlocks.forcestoneSlab, rand.nextBoolean() ? 0 : 8, flags);
                }
                else
                {
                    super.placeBlock(x, y, z, ModBlocks.lightForcestoneStairs, rand.nextInt(8), flags);
                }
            }
            else if (block == ModBlocks.lightForcestoneStairs && rand.nextFloat() < ruination)
            {
                super.placeBlock(x, y, z, ModBlocks.forcestoneSlab, (metadata & 4) == 0 ? 0 : 8, flags);
            }
            else if (block == Blocks.planks && metadata == 4 && rand.nextFloat() < ruination)
            {
                if (rand.nextBoolean())
                {
                    super.placeBlock(x, y, z, Blocks.wooden_slab, rand.nextBoolean() ? 4 : 12, flags);
                }
                else
                {
                    super.placeBlock(x, y, z, Blocks.acacia_stairs, rand.nextInt(8), flags);
                }
            }
            else if (block == Blocks.water || block == Blocks.stained_hardened_clay || block.hasTileEntity(metadata) || rand.nextFloat() * 4 >= ruination * ruination)
            {
                super.placeBlock(x, y, z, block, metadata, flags);
            }
        }
        else
        {
            super.placeBlock(x, y, z, block, metadata, flags);
        }
    }

    @Override
    public void spawnStructure(Random random)
    {
        BiomeGenBase biome = worldObj.getBiomeGenForCoords(xCoord, zCoord);
        Block topBlock = biome.topBlock;
        Block fillerBlock = biome.fillerBlock;
        int topBlockMeta = biome.field_150604_aj;
        
        mirrorX = true;
        rand = random;

        for (int layer = 0; layer < 2; ++layer)
        {
            simulate = layer == 0;

            setBlock(Blocks.planks, 4, 5, 0, 13);
            setBlock(Blocks.planks, 4, 5, 0, 21);
            setBlock(ModBlocks.lightForcestoneStairs, 2, 3, 8, 7);
            setBlock(ModBlocks.lightForcestone, 0, 2, 1, 26);
            setBlock(ModBlocks.forcestoneSlab, 0, 2, 3, 26);
            setBlock(ModBlocks.lightForcestone, 0, 2, 3, 28);

            for (int i = 0; i < 2; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, i, 0, 3);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 2 + i, 0, 3 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 3 + i, 0, 3 + i);
                setBlock(ModBlocks.forcestoneSlab, 0, 3 + i, 0, 2 + i);
                setBlock(ModBlocks.lightForcestone, 0, i, 5, 7);
                setBlock(Blocks.planks, 4, 4 + i, 0, 12);
                setBlock(Blocks.planks, 4, 4 + i, 0, 22);
                setBlock(ModBlocks.forcestoneSlab, 0, 3 + i, 1, 24);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 2, 3 + i, 27 + i);
                setBlock(ModBlocks.lightForcestone, 4, 2, 1, 27 + i);
                setBlock(ModBlocks.lightForcestone, 0, 3 + i, 3, 29);
                setBlock(ModBlocks.forcestoneSlab, 0, i, 5, 29);
                setBlock(ModBlocks.lightForcestoneStairs, 2, i, 4, 31);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, 3 + i, 2, 28 + j);
                    setBlock(ModBlocks.forcestoneSlab, 0, i, 4, 29 + j);
                }

                for (int j = 0; j < 3; ++j)
                {
                    setBlock(Blocks.stained_glass_pane, 3, i, 6 + j, 7);
                    setBlock(ModBlocks.lightForcestone, 0, 3 + i, 1, 27 + j);
                }
            }

            for (int i = 0; i < 3; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, i, 0, 4);
                setBlock(ModBlocks.lightForcestone, 1, i, 4, 7);
                setBlock(ModBlocks.lightForcestoneStairs, 2, i, 0, 2);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 4, 0, 5 + i);
                setBlock(ModBlocks.forcestoneSlab, 0, i, 0, 1);
                setBlock(ModBlocks.lightActivatedForcestone, 0, 3, 1 + i, 7);
                setBlock(Blocks.stained_glass_pane, 3, 2, 1 + i, 7);
                setBlock(Blocks.stained_glass_pane, 3, 0, 9 + i, 7);
                setBlock(Blocks.planks, 4, 3 + i, 0, 11);
                setBlock(Blocks.planks, 4, 2 + i, 0, 23);
                setBlock(ModBlocks.forcestoneSlab, 0, 2 + i, 1, 25);
                setBlock(ModBlocks.lightForcestone, 1, 2, 2, 26 + i);
            }

            for (int i = 0; i < 4; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 3, 4 + i, 7);
                setBlock(ModBlocks.lightForcestone, 0, 2, 5 + i, 7);
                setBlock(ModBlocks.lightActivatedForcestone, 0, 6, 1 + i, 11);
                setBlock(ModBlocks.lightActivatedForcestone, 0, 6, 1 + i, 22);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(ModBlocks.lightForcestoneStairs, 2, 3 + j, 1 + i, 26 + i);
                }

                for (int j = 0; j < 3; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, i, 0, 5 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 2 + j, 0, 26 + i);
                }
            }

            for (int i = 0; i < 5; ++i)
            {
                setBlock(ModBlocks.forcestoneSlab, 0, 5, 0, 4 + i);
                setBlock(Blocks.planks, 4, i, 0, 8);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(Blocks.planks, 4, i, 0, 24 + j);
                }
            }

            for (int i = 0; i < 6; ++i)
            {
                for (int j = 0; j < 2; ++j)
                {
                    setBlock(Blocks.planks, 4, i, 0, 9 + j);
                }
            }

            for (int i = 0; i < 7; ++i)
            {
                setBlock(i % 2 == 0 ? ModBlocks.lightForcestone : Blocks.stained_hardened_clay, i % 2 == 0 ? 0 : 3, 5, 0, 14 + i);
            }

            for (int i = 0; i < 8; ++i)
            {
                setBlock(ModBlocks.lightForcestone, i == 0 ? 0 : 2, 5, i, 23);
                setBlock(ModBlocks.lightForcestone, 0, 2, 0, 30 + i);
                setBlock(i == 3 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 2, 1 + i, 29);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, 5, i, 9 + j);
                }

                for (int j = 0; j < 5; ++j)
                {
                    if (j == 4)
                    {
                        setBlock(ModBlocks.lightForcestone, 0, 5, i, 24 + j);
                    }
                    else if (j == 3)
                    {
                        setBlock(i == 3 ? Blocks.planks : ModBlocks.lightForcestone, i == 3 ? 4 : 0, 5, i, 24 + j);
                    }
                    else
                    {
                        setBlock(i == 2 || i == 3 ? Blocks.planks : ModBlocks.lightForcestone, i == 2 || i == 3 ? 4 : 0, 5, i, 24 + j);
                    }
                }
            }

            for (int i = 0; i < 9; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 4, i, 8);
                setBlock(i < 3 || i > 5 ? ModBlocks.lightForcestone : Blocks.stained_hardened_clay, i < 3 || i > 5 ? 0 : 3, 4, 0, 13 + i);
                setBlock(ModBlocks.lightForcestone, i > 4 && i < 8 ? 2 : 0, 5, i, 29);
                setBlock(i == 8 ? ModBlocks.lightForcestoneStairs : (i > 4 && i < 8 ? ModBlocks.lightActivatedForcestone : ModBlocks.lightForcestone), i == 8 ? 2 : 0, 6, i, 29);
            }

            for (int i = 0; i < 10; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 4, 6, 1, 12 + i);
                setBlock(Blocks.planks, 4, 6, 2, 12 + i);
                setBlock(Blocks.planks, 4, 6, 3, 12 + i);
                setBlock(ModBlocks.lightForcestone, 0, 6, 4, 12 + i);
            }

            for (int i = 0; i < 11; ++i)
            {
                setBlock(i < 2 || i > 8 || i == 5 ? ModBlocks.lightForcestone : Blocks.stained_hardened_clay, i < 2 || i > 8 || i == 5 ? 0 : 3, 3, 0, 12 + i);
            }

            for (int i = 0; i < 12; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 6, 0, 11 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 6, 5, 11 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 4, 5, 5, 11 + i);
                setBlock(Blocks.stained_glass_pane, 3, 5, 6, 11 + i);
                setBlock(Blocks.stained_glass_pane, 3, 5, 7, 11 + i);
                setBlock(i < 2 || i > 3 ? ModBlocks.lightForcestone : Blocks.stained_hardened_clay, i < 2 || i > 3 ? 0 : 3, 2, 0, 11 + i);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(Blocks.planks, 4, j, 0, 26 + i);
                }
            }

            for (int i = 0; i < 13; ++i)
            {
                setBlock((i < 1 || i > 2) && i != 5 ? ModBlocks.lightForcestone : Blocks.stained_hardened_clay, (i < 1 || i > 2) && i != 5 ? 0 : 3, 1, 0, 11 + i);
                setBlock(i == 0 || i == 12 ? ModBlocks.lightForcestone : Blocks.stained_hardened_clay, i == 0 || i == 12 ? 0 : 3, 0, 0, 11 + i);
            }

            for (int i = 0; i < 20; ++i)
            {
                setBlock(ModBlocks.lightForcestoneStairs, 1, 5, 8, 9 + i);
            }

            for (int i = 0; i < 21; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 4, 8, 9 + i);
            }

            for (int i = 0; i < 25; ++i)
            {
                setBlock(ModBlocks.forcestoneSlab, 8, 3, 8, 8 + i);
            }

            for (int i = 0; i < 28; ++i)
            {
                setBlock(ModBlocks.forcestoneSlab, 0, 0, 13, 7 + i);
                setBlock(i == 0 || i == 27 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, i == 0 || i == 27 ? 0 : 8, 0, 12, 7 + i);
                setBlock(i == 0 || i == 27 ? ModBlocks.lightForcestone : ModBlocks.lightForcestoneStairs, i == 0 || i == 27 ? 0 : 4, 1, 9, 7 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 1, 12, 7 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 2, 9, 7 + i);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, 1, 10 + j, 7 + i);
                }
            }

            zCoord += 30;

            setBlock(ModBlocks.lightForcestone, 0, 2, 0, 12);
            setBlock(ModBlocks.lightForcestone, 0, 2, 0, 16);
            setBlock(ModBlocks.forcestoneSlab, 0, 2, 1, 12);
            setBlock(ModBlocks.forcestoneSlab, 0, 2, 1, 16);
            setBlock(ModBlocks.lightActivatedForcestone, 0, 0, 1, 11);
            setBlock(ModBlocks.lightActivatedForcestone, 0, 0, 1, 17);
            setBlock(ModBlocks.lightActivatedForcestone, 0, 3, 1, 14);
            setBlock(ModBlocks.lightForcestoneStairs, 7, 2, 4, 4);
            setBlock(ModBlocks.lightForcestoneStairs, 6, 2, 4, 24);
            setBlock(ModBlocks.lightForcestoneStairs, 5, 1, 8, 28);
            setBlock(ModBlocks.forcestoneSlab, 8, 0, 7, 28);
            setBlock(ModBlocks.lightForcestoneStairs, 6, 14, 8, 13);
            setBlock(ModBlocks.lightForcestoneStairs, 7, 14, 8, 15);
            setBlock(ModBlocks.forcestoneSlab, 8, 14, 7, 14);

            for (int i = 0; i < 2; ++i)
            {
                int y = i * 4;

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, j, i, 4, 2 + j);
                    setBlock(ModBlocks.forcestoneSlab, 0, 2, 1, 4 + i * 3 + j * 17);
                    setBlock(ModBlocks.forcestoneSlab, 0, 7 + i * 3, 1, 12 + j * 4);
                    setBlock(ModBlocks.lightForcestone, 1, 13 + i, 3, 12 + j * 4);
                    setBlock(ModBlocks.lightActivatedForcestone, 0, 16 + i * 10, 1 + j, 9);
                    setBlock(ModBlocks.lightActivatedForcestone, 0, 16 + i * 10, 1 + j, 19);
                    setBlock(ModBlocks.lightActivatedForcestone, 0, 5, 1 + i, 30 + j * 10);
                    setBlock(fillerBlock, 0, 6 + i, -2, 7 + j);
                    setBlock(fillerBlock, 0, 6 + i, -2, 20 + j);
                    setBlock(Blocks.water, 0, 6 + i, -1, 7 + j);
                    setBlock(Blocks.water, 0, 6 + i, -1, 20 + j);
                    setBlock(ModBlocks.forcestoneSlab, 8, 10 + i, 8, 3 + j);
                    setBlock(ModBlocks.forcestoneSlab, 8, 10 + i, 8, 24 + j);
                }

                for (int j = 0; j < 3; ++j)
                {
                    setBlock(Blocks.planks, 4, 6, y, j);
                    setBlock(Blocks.planks, 4, 7, y, 1 + j);
                    setBlock(Blocks.planks, 4, 8, y, 1 + j);
                    setBlock(Blocks.planks, 4, 9, y, 2 + j);
                    setBlock(Blocks.planks, 4, 10, y, 3 + j);
                    setBlock(Blocks.planks, 4, 6, y, -j + 28);
                    setBlock(Blocks.planks, 4, 7, y, -(1 + j) + 28);
                    setBlock(Blocks.planks, 4, 8, y, -(1 + j) + 28);
                    setBlock(Blocks.planks, 4, 9, y, -(2 + j) + 28);
                    setBlock(Blocks.planks, 4, 10, y, -(3 + j) + 28);
                    setBlock(Blocks.planks, 4, 14 + i, 0, 13 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 12, 1 + j, 12 + i * 4);
                    setBlock(Blocks.stained_glass_pane, 3, i, 6 + j, 4);
                    setBlock(Blocks.stained_glass_pane, 3, i, 6 + j, 24);
                    setBlock(ModBlocks.forcestoneSlab, 8, 7 + i, 8, 1 + j);
                    setBlock(ModBlocks.forcestoneSlab, 8, 7 + i, 8, 25 + j);
                    setBlock(ModBlocks.forcestoneSlab, 8, 11 + j, 8, 6 + i);
                    setBlock(ModBlocks.forcestoneSlab, 8, 11 + j, 8, 21 + i);
                }

                for (int j = 0; j < 4; ++j)
                {
                    setBlock(Blocks.planks, 4, 14, y, 8 + j);
                    setBlock(Blocks.planks, 4, 14, y, 17 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 7 + j, 0, 12 + i * 4);
                    setBlock(ModBlocks.lightForcestone, 0, 27, j, 8 + i);
                    setBlock(ModBlocks.lightForcestone, 0, 27, j, 19 + i);
                    setBlock(ModBlocks.lightForcestone, 0, 5 + i, j, 41);
                    setBlock(ModBlocks.lightForcestone, 0, 10, 5 + j, 12 + i * 4);
                }

                for (int j = 0; j < 5; ++j)
                {
                    setBlock(Blocks.planks, 4, 11, y, 3 + j);
                    setBlock(Blocks.planks, 4, 11, y, -(3 + j) + 28);
                    setBlock(Blocks.planks, 4, i, 0, 21 + j);
                }

                for (int j = 0; j < 6; ++j)
                {
                    setBlock(Blocks.planks, 4, j, 4, 26 + i);
                }

                for (int j = 0; j < 17; ++j)
                {
                    setBlock(Blocks.planks, 4, 13, y, 6 + j);
                }

                for (int j = 0; j < 19; ++j)
                {
                    setBlock(Blocks.planks, 4, 12, y, 5 + j);
                }

                setBlock(ModBlocks.lightForcestone, 1, 2, 3, i);
                setBlock(Blocks.grass, 0, i, 0, 12);
                setBlock(Blocks.grass, 0, i, 0, 16);
                setBlock(ModBlocks.lightForcestone, 0, i, 0, 11);
                setBlock(ModBlocks.lightForcestone, 0, i, 0, 17);
                setBlock(ModBlocks.forcestoneSlab, 0, i, i == 0 ? 2 : 1, 11);
                setBlock(ModBlocks.forcestoneSlab, 0, i, i == 0 ? 2 : 1, 17);
                setBlock(Blocks.planks, 4, 2 + i, 0, 11 + i);
                setBlock(Blocks.planks, 4, 2 + i, 0, 17 - i);
                setBlock(Blocks.planks, 4, 4, 0, 10 + i);
                setBlock(Blocks.planks, 4, 4, 0, 17 + i);
                setBlock(Blocks.planks, 4, 5, 0, 11 + i * 6);
                setBlock(ModBlocks.lightForcestone, 1, i, 4, 25);
                setBlock(Blocks.stained_glass, 3, i, 4, 29);
                setBlock(Blocks.planks, 4, i, 0, 29);
                setBlock(ModBlocks.lightForcestone, 1, 2, 3, 27 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 2, i * 2, 1, 40);
                setBlock(ModBlocks.lightForcestoneStairs, 0, 8, 0, 6 + i * 16);
                setBlock(fillerBlock, 0, 5 + i, -2, 6);
                setBlock(fillerBlock, 0, 8, -2, 8 + i);
                setBlock(fillerBlock, 0, 5 + i, -2, 22);
                setBlock(fillerBlock, 0, 8, -2, 19 + i);
                setBlock(Blocks.water, 0, 5 + i, -1, 6);
                setBlock(Blocks.water, 0, 8, -1, 8 + i);
                setBlock(Blocks.water, 0, 5 + i, -1, 22);
                setBlock(Blocks.water, 0, 8, -1, 19 + i);
                setBlock(ModBlocks.lightForcestone, 0, i, 5, 4);
                setBlock(ModBlocks.lightForcestone, 0, i, 5, 24);
                setBlock(ModBlocks.lightForcestoneStairs, 4, 10, 4, 12 + i * 4);
                setBlock(topBlock, topBlockMeta, 4 + i, -1, 5);
                setBlock(topBlock, topBlockMeta, 4, -1, 6 + i);
                setBlock(topBlock, topBlockMeta, 5, -1, 7 + i);
                setBlock(topBlock, topBlockMeta, 6 + i, -1, 9);
                setBlock(topBlock, topBlockMeta, 7 + i, -1, 10);
                setBlock(topBlock, topBlockMeta, 9, -1, 10 - i);
                setBlock(topBlock, topBlockMeta, 4 + i, -1, 23);
                setBlock(topBlock, topBlockMeta, 4, -1, 21 + i);
                setBlock(topBlock, topBlockMeta, 5, -1, 20 + i);
                setBlock(topBlock, topBlockMeta, 6 + i, -1, 19);
                setBlock(topBlock, topBlockMeta, 7 + i, -1, 18);
                setBlock(topBlock, topBlockMeta, 9, -1, 18 + i);
                setBlock(Blocks.stained_glass_pane, 3, 8 + i, 5, 1);
                setBlock(Blocks.stained_glass_pane, 3, 13, 5, 5 + i);
                setBlock(Blocks.stained_glass_pane, 3, 8 + i, 5, 27);
                setBlock(Blocks.stained_glass_pane, 3, 13, 5, 22 + i);
                setBlock(Blocks.stained_glass_pane, 3, 11, 5, 3 + i * 22);
                setBlock(ModBlocks.lightForcestoneStairs, 0, 9, 8, 5 + i * 18);
                setBlock(ModBlocks.forcestoneSlab, 8, 9, 8, 2 + i);
                setBlock(ModBlocks.forcestoneSlab, 8, 11 + i, 8, 5);
                setBlock(ModBlocks.forcestoneSlab, 8, 11 + i, 8, 23);
                setBlock(ModBlocks.forcestoneSlab, 8, 9, 8, 25 + i);
            }

            for (int i = 0; i < 3; ++i)
            {
                setBlock(Blocks.stained_glass, 3, 15, 4, 13 + i);
                setBlock(ModBlocks.lightForcestone, 0, 2, 1 + i, 2);
                setBlock(ModBlocks.lightForcestone, 0, 3, 0, 13 + i);
                setBlock(ModBlocks.forcestoneSlab, 0, 3, i == 1 ? 2 : 1, 13 + i);
                setBlock(Blocks.planks, 4, 3, 0, 9 + i);
                setBlock(Blocks.planks, 4, 3, 0, 17 + i);
                setBlock(ModBlocks.lightForcestone, 0, i, 4, 28);
                setBlock(Blocks.planks, 4, 3 + i, 4, 28);
                setBlock(ModBlocks.lightForcestone, 0, 2, 1 + i, 26);
                setBlock(ModBlocks.lightForcestone, 1, 11, 4, 13 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 3, 19 + i * 2, 1, 9);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 19 + i * 2, 1, 19);
                setBlock(ModBlocks.lightForcestoneStairs, 0, 26, 1, 12 + i * 2);
                setBlock(ModBlocks.lightForcestoneStairs, 0, 5, 1, 33 + i * 2);
                setBlock(ModBlocks.lightForcestoneStairs, 3, 4 + i, 0, 4);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 7 + i, 0, 11);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 0 : 3, 6 + i, 0, 5);
                setBlock(ModBlocks.lightForcestoneStairs, i != 1 ? 3 : 0, 9, 0, 6 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 4 + i, 0, 24);
                setBlock(ModBlocks.lightForcestoneStairs, 3, 7 + i, 0, 17);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 0 : 2, 6 + i, 0, 23);
                setBlock(ModBlocks.lightForcestoneStairs, i != 1 ? 2 : 0, 9, 0, 20 + i);
                setBlock(ModBlocks.forcestoneSlab, 0, 4 + i, 0, 8 + i);
                setBlock(ModBlocks.forcestoneSlab, 0, 4 + i, 0, 20 - i);
                setBlock(Blocks.stained_glass_pane, 3, 0, 9 + i, 4);
                setBlock(Blocks.stained_glass_pane, 3, 0, 9 + i, 24);
                setBlock(ModBlocks.lightForcestone, 0, 10, 5, 13 + i);
                setBlock(Blocks.stained_glass_pane, 3, 10, 9 + i, 14);
                setBlock(Blocks.stained_glass_pane, 3, 6 + i, 5, 0);
                setBlock(Blocks.stained_glass_pane, 3, 9 + i, 5, 2);
                setBlock(Blocks.stained_glass_pane, 3, 12, 5, 3 + i);
                setBlock(Blocks.stained_glass_pane, 3, 14, 5, 6 + i);
                setBlock(Blocks.stained_glass_pane, 3, 14, 5, 20 + i);
                setBlock(Blocks.stained_glass_pane, 3, 12, 5, 23 + i);
                setBlock(Blocks.stained_glass_pane, 3, 9 + i, 5, 26);
                setBlock(Blocks.stained_glass_pane, 3, 6 + i, 5, 28);
                setBlock(ModBlocks.lightForcestoneStairs, i == 2 ? 0 : 3, 7 + i, 8, 4);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 3 : 0, 10, 8, 5 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 2 ? 2 : 0, 10, 8, 21 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 2 ? 0 : 2, 7 + i, 8, 24);

                for (int j = 0; j < 3; ++j)
                {
                    setBlock(ModBlocks.forcestoneSlab, 8, 15, 5, 9 + i + j * 4);
                    setBlock(ModBlocks.forcestoneSlab, 8, 15, 5, 9 + i + j * 4);
                    setBlock(Blocks.planks, 4, 3 + i, 0, j);
                    setBlock(Blocks.planks, 4, i, 0, 8 + j);
                    setBlock(Blocks.planks, 4, i, 0, 18 + j);
                    setBlock(Blocks.grass, 0, i, 0, 13 + j);
                    setBlock(Blocks.stained_glass_pane, 3, 10, 6 + i, 13 + j);
                    setBlock(ModBlocks.forcestoneSlab, 8, 4 + i, 8, j);
                }

                for (int j = 0; j < 4; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, 2 + j, 4, i);
                    setBlock(ModBlocks.forcestoneSlab, 8, 12 + i, 8, 8 + j);
                    setBlock(ModBlocks.forcestoneSlab, 8, 12 + i, 8, 17 + j);
                    setBlock(ModBlocks.forcestoneSlab, 8, 3 + j, 8, 26 + i);
                }

                for (int j = 0; j < 5; ++j)
                {
                    setBlock(Blocks.planks, 4, 4 + i, 0, 12 + j);
                    setBlock(Blocks.planks, 4, 7 + j, 0, 13 + i);
                    setBlock(i > 0 || j == 0 || j == 4 ? ModBlocks.lightForcestone : ModBlocks.lightForcestoneStairs, i > 0 || j == 0 || j == 4 ? 0 : 7, 10 + j, 9 + i, 13);
                    setBlock(i > 0 || j == 0 || j == 4 ? ModBlocks.lightForcestone : ModBlocks.lightForcestoneStairs, i > 0 || j == 0 || j == 4 ? 0 : 6, 10 + j, 9 + i, 15);
                }

                for (int j = 0; j < 6; ++j)
                {
                    setBlock(Blocks.planks, 4, j, 0, 26 + i);
                }
            }

            for (int i = 0; i < 4; ++i)
            {
                setBlock(i > 2 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 2, 1 + i, 3);
                setBlock(ModBlocks.lightForcestone, 0, 3 + i, 0, 8 + i);
                setBlock(ModBlocks.lightForcestone, 0, 3 + i, 0, 20 - i);
                setBlock(ModBlocks.forcestoneSlab, 0, 3 + i, 1, 8 + i);
                setBlock(ModBlocks.forcestoneSlab, 0, 3 + i, 1, 20 - i);
                setBlock(i == 3 ? ModBlocks.lightForcestoneStairs : ModBlocks.lightActivatedForcestone, i == 3 ? 1 : 0, 15, 5 + i, 8);
                setBlock(i == 3 ? ModBlocks.lightForcestoneStairs : ModBlocks.lightActivatedForcestone, i == 3 ? 1 : 0, 15, 5 + i, 20);
                setBlock(i == 3 ? ModBlocks.lightForcestoneStairs : ModBlocks.lightActivatedForcestone, i == 3 ? 3 : 0, 6, 5 + i, 29);
                setBlock(ModBlocks.lightForcestone, 0, 2, 0, 21 + i);
                setBlock(ModBlocks.lightForcestone, 0, 16, i, 8);
                setBlock(ModBlocks.lightForcestone, 0, 16, i, 20);
                setBlock(ModBlocks.lightForcestone, 0, 26, i, 8);
                setBlock(ModBlocks.lightForcestone, 0, 26, i, 20);
                setBlock(ModBlocks.lightForcestone, 0, 6, i, 30);
                setBlock(ModBlocks.lightForcestone, 0, 6, i, 40);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 3, 0, 4 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 0, 10, 0, 8 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 3, 0, 21 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 0, 10, 0, 17 + i);
                setBlock(ModBlocks.lightForcestone, 0, 2, 5 + i, 4);
                setBlock(ModBlocks.lightForcestone, 0, 2, 5 + i, 24);
                setBlock(Blocks.stained_glass_pane, 3, 0, 8 + i, 28);
                setBlock(Blocks.stained_glass_pane, 3, 14, 8 + i, 14);
                setBlock(topBlock, topBlockMeta, 6 + i, -1, 5 + i);
                setBlock(topBlock, topBlockMeta, 9 - i, -1, 20 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 1 : 3, 3 + i, 8, 3);
                setBlock(ModBlocks.lightForcestoneStairs, i == 3 ? 2 : 0, 11, 8, 8 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 3 : 0, 11, 8, 17 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 1 : 2, 3 + i, 8, 25);

                for (int j = 0; j < 9; ++j)
                {
                    setBlock(Blocks.planks, 4, 17 + j, i, 8);
                    setBlock(Blocks.planks, 4, 17 + j, i, 20);
                    setBlock(Blocks.planks, 4, 27, i, 10 + j);
                    setBlock(Blocks.planks, 4, 6, i, 31 + j);
                }
            }

            for (int i = 0; i < 5; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 9, i, 1);
                setBlock(ModBlocks.lightForcestone, 0, 13, i, 5);
                setBlock(ModBlocks.lightForcestone, 0, 13, i, 23);
                setBlock(ModBlocks.lightForcestone, 0, 9, i, 27);
                setBlock(ModBlocks.lightForcestone, 0, 14, 4, 12 + i);
                setBlock(Blocks.planks, 4, 14, 0, 12 + i);
                setBlock(i == 0 || i > 3 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 11, i, 12);
                setBlock(i == 0 || i > 3 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 11, i, 16);
                setBlock(i == 0 || i > 3 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 2, i, 25);
                setBlock(ModBlocks.forcestoneSlab, 8, i > 1 ? i + 1 : i, 5, 29);
                setBlock(ModBlocks.lightForcestone, 1, i, 3, 40);
                setBlock(ModBlocks.lightForcestoneStairs, 3, i, 4, 40);
                setBlock(i == 0 || i == 4 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, i == 0 || i == 4 ? 0 : 8, 0, 12, 24 + i);
                setBlock(ModBlocks.forcestoneSlab, 0, 0, 13, 24 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 1, 12, 24 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 2, 9, 24 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 10 + i, 9, 12);
                setBlock(ModBlocks.lightForcestoneStairs, 3, 10 + i, 9, 16);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 10 + i, 12, 13);
                setBlock(ModBlocks.lightForcestoneStairs, 3, 10 + i, 12, 15);
                setBlock(i == 0 || i == 4 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, i == 0 || i == 4 ? 0 : 4, 10 + i, 12, 14);
                setBlock(ModBlocks.forcestoneSlab, 0, 10 + i, 13, 14);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, 7 + j, i, 0);
                    setBlock(ModBlocks.lightForcestone, 0, 10 + j, i, 2);
                    setBlock(ModBlocks.lightForcestone, 0, 12, i, 3 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 14, i, 6 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 14, i, 21 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 12, i, 24 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 10 + j, i, 26);
                    setBlock(ModBlocks.lightForcestone, 0, 7 + j, i, 28);
                }

                for (int j = 0; j < 3; ++j)
                {
                    setBlock(j > 0 || i == 0 || i == 4 ? ModBlocks.lightForcestone : ModBlocks.lightForcestoneStairs, j > 0 || i == 0 || i == 4 ? 0 : 4, 1, 9 + j, 24 + i);
                }

                for (int j = 0; j < 4; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, 15, i, 8 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 15, i, 17 + j);
                    setBlock(ModBlocks.lightForcestone, 0, 3 + j, i, 29);
                    setBlock(Blocks.planks, 4, i, j, 41);
                }

                for (int j = 0; j < 10; ++j)
                {
                    setBlock(Blocks.stained_glass, 3, i, 4, 30 + j);
                }
            }

            for (int i = 0; i < 6; ++i)
            {
                setBlock(i == 0 || i > 3 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 15, i, 12);
                setBlock(i == 0 || i > 3 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 15, i, 16);
                setBlock(i == 0 || i > 3 ? ModBlocks.lightForcestone : ModBlocks.lightActivatedForcestone, 0, 2, i, 29);
                setBlock(ModBlocks.lightForcestone, 0, i, 4, 41);
            }

            for (int i = 0; i < 8; ++i)
            {
                Block block = ModBlocks.lightForcestone;
                int metadata = block == Blocks.stained_glass ? 3 : 0;

                for (int j = 0; j < 4; ++j)
                {
                    if (j > 0 || i > 4)
                    {
                        block = i < 2 ? ModBlocks.lightForcestone : (i < 4 || i == 6 ? Blocks.stained_glass : ModBlocks.lightForcestone);
                        metadata = block == Blocks.stained_glass ? 3 : 0;
                    }

                    setBlock(block, metadata, 3 + j, i, 3);
                    setBlock(block, metadata, 3 + j, i, 25);

                    setBlock(block, metadata, 11, i, 11 - j);
                    setBlock(block, metadata, 11, i, 17 + j);
                }

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(block, metadata, 7 + j, i, 4);
                    setBlock(block, metadata, 7 + j, i, 24);
                    setBlock(block, metadata, 10, i, 6 + j);
                    setBlock(block, metadata, 10, i, 21 + j);
                }

                setBlock(block, metadata, 9, i, 5);
                setBlock(block, metadata, 9, i, 23);
            }

            for (int i = 0; i < 9; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 1, 26, 3, 10 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 26, 4, 10 + i);

                for (int j = 0; j < 10; ++j)
                {
                    setBlock(Blocks.stained_glass, 3, 16 + j, 4, 10 + i);
                }
            }

            for (int i = 0; i < 11; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 27, 4, 9 + i);
                setBlock(ModBlocks.lightForcestone, 1, 16 + i, 3, 9);
                setBlock(ModBlocks.lightForcestone, 1, 16 + i, 3, 19);
                setBlock(ModBlocks.lightForcestoneStairs, 2, 16 + i, 4, 9);
                setBlock(ModBlocks.lightForcestoneStairs, 3, 16 + i, 4, 19);
                setBlock(ModBlocks.lightForcestone, 1, 5, 3, 30 + i);
                setBlock(ModBlocks.lightForcestoneStairs, 1, 5, 4, 30 + i);

                for (int j = 0; j < 6; ++j)
                {
                    setBlock(Blocks.planks, 4, j, 0, 30 + i);
                }

                for (int j = 0; j < 11; ++j)
                {
                    setBlock(Blocks.planks, 4, 16 + i, 0, 9 + j);
                }
            }

            for (int i = 0; i < 12; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 16 + i, 4, 8);
                setBlock(ModBlocks.lightForcestone, 0, 16 + i, 4, 20);
                setBlock(ModBlocks.lightForcestone, 0, 6, 4, 30 + i);
            }

            /**
             * Congrats! You just found an easter egg in the code. [~o~] Redeem this token at twitter.com/FiskFille for a prize, a shoutout and (if in a good mood) a pat on the back.
             */

            for (int i = 0; i < 3; ++i)
            {
                setBlock(Blocks.wooden_slab, 4, 20 + i, 0, 12);
                setBlock(Blocks.wooden_slab, 4, 20 + i, 0, 16);
                setBlock(Blocks.wooden_slab, 4, 2, 0, 34 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 2 ? 0 : 3, i, 0, 32);
                setBlock(ModBlocks.lightForcestoneStairs, i == 2 ? 0 : 2, i, 0, 38);

                for (int j = 0; j < 5; ++j)
                {
                    setBlock(Blocks.wooden_slab, 4, 19 + j, 0, 13 + i);

                    if (i < 2)
                    {
                        setBlock(Blocks.wooden_slab, 4, i, 0, 33 + j);
                    }
                }
            }

            for (int i = 0; i < 2; ++i)
            {
                setBlock(ModBlocks.lightForcestone, 0, 3, 0, 32 + i * 6);
                setBlock(ModBlocks.lightForcestoneStairs, 0, 2, 0, 33 + i * 4);

                for (int j = 0; j < 2; ++j)
                {
                    setBlock(ModBlocks.lightForcestone, 0, 18 + i * 6, 0, 11 + j * 6);
                    setBlock(ModBlocks.lightForcestoneStairs, 1 - i, 19 + i * 4, 0, 12 + j * 4);
                }
            }

            for (int i = 0; i < 5; ++i)
            {
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 1 : (i == 4 ? 0 : 3), 19 + i, 0, 11);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 1 : (i == 4 ? 0 : 2), 19 + i, 0, 17);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 3 : (i == 4 ? 2 : 1), 18, 0, 12 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 3 : (i == 4 ? 2 : 0), 24, 0, 12 + i);
                setBlock(ModBlocks.lightForcestoneStairs, i == 0 ? 3 : (i == 4 ? 2 : 0), 3, 0, 33 + i);
            }

            setBlock(ModBlocks.lightActivatedForcestone, 0, 21, 0, 14);
            setBlock(ModBlocks.lightActivatedForcestone, 0, 0, 0, 35);
            setBlock(ModBlocks.holocron, 0, 21, 1, 14);
            setBlock(ModBlocks.holocron, 0, 0, 1, 35);

            boolean prevMirrorX = mirrorX;
            mirrorX = false;

            generateTree(random);
            zCoord -= 30;

            if (simulate)
            {
                for (int i = 0; i < coverage.size(); ++i)
                {
                    StructurePoint p = coverage.get(i);
                    int y = p.posY;

                    int clearance = maxY + 8 - (int) (8 * random.nextDouble() * random.nextDouble() * random.nextDouble());

                    while (y > 0 && !worldObj.getBlock(p.posX, y - 1, p.posZ).isSideSolid(worldObj, p.posX, y - 1, p.posZ, ForgeDirection.UP))
                    {
                        worldObj.setBlock(p.posX, --y, p.posZ, fillerBlock);
                    }

                    for (y = p.posY; y < clearance; ++y)
                    {
                        if (!worldObj.isAirBlock(p.posX, y, p.posZ))
                        {
                            worldObj.setBlock(p.posX, y, p.posZ, Blocks.air);
                        }
                    }
                }

                mirrorX = prevMirrorX;
            }
            else
            {
                generateStructureChestContents(random, (int) (6 * ((random.nextFloat() - 0.5F) * 2)), -1, 11 + random.nextInt(12), ChestGenHooks.getItems(JEDI_TEMPLE, random), ChestGenHooks.getCount(JEDI_TEMPLE, random));
            }
        }
    }

    public void generateTree(Random random)
    {
        zCoord += 14;
        int height = 7;
        float leafCoverage = 1.5F;
        int leafCoverageI = MathHelper.ceiling_float_int(leafCoverage);

        for (int x = -1; x < 2; ++x)
        {
            for (int z = -1; z < 2; ++z)
            {
                for (int y = 0; y < height; ++y)
                {
                    if (y < 2 || x == 0 || z == 0 || random.nextFloat() > 0.2F + ((float) y / height))
                    {
                        setBlock(Blocks.log, 0, x, y + 1, z);
                    }
                    else
                    {
                        y = height;
                    }
                }
            }
        }

        setBlock(Blocks.log, 0, 0, 1 + height, 0);

        for (int i = 0; i < MathHelper.getRandomIntegerInRange(random, 18, 20); ++i)
        {
            float rotX = random.nextFloat() * 120;
            float rotY = random.nextFloat() * 360;
            int branchMin = 4;
            int branchMax = 6;

            for (int j = 0; j < (MathHelper.getRandomIntegerInRange(random, branchMin, branchMax) + MathHelper.getRandomIntegerInRange(random, branchMin, branchMax)) / 2; ++j)
            {
                Vec3 vec3 = Vec3.createVectorHelper(0, j, 0);
                vec3.rotateAroundX(rotX * (float) Math.PI / 180.0F);
                vec3.rotateAroundY(rotY * (float) Math.PI / 180.0F);

                setBlock(Blocks.log, 0, MathHelper.floor_double(vec3.xCoord), 1 + height + MathHelper.floor_double(vec3.yCoord), MathHelper.floor_double(vec3.zCoord));

                for (int x = -leafCoverageI; x <= leafCoverageI; ++x)
                {
                    for (int y = -leafCoverageI; y <= leafCoverageI; ++y)
                    {
                        for (int z = -leafCoverageI; z <= leafCoverageI; ++z)
                        {
                            Block block = worldObj.getBlock(xCoord + MathHelper.floor_double(vec3.xCoord) + x, yCoord + 1 + height + MathHelper.floor_double(vec3.yCoord) + y, zCoord + MathHelper.floor_double(vec3.zCoord) + z);

                            if (block != Blocks.log)
                            {
                                Vec3 src = Vec3.createVectorHelper(MathHelper.floor_double(vec3.xCoord) + 0.5F, 1 + height + MathHelper.floor_double(vec3.yCoord) + 0.5F, MathHelper.floor_double(vec3.zCoord) + 0.5F);
                                Vec3 dst = src.addVector(x, y, z);

                                if (src.distanceTo(dst) <= leafCoverage)
                                {
                                    setBlock(Blocks.leaves, 0, MathHelper.floor_double(vec3.xCoord) + x, 1 + height + MathHelper.floor_double(vec3.yCoord) + y, MathHelper.floor_double(vec3.zCoord) + z);
                                }
                            }
                        }
                    }
                }
            }
        }

        zCoord -= 14;
    }
}
