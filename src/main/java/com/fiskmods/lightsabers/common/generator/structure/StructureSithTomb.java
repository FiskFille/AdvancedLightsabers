package com.fiskmods.lightsabers.common.generator.structure;

import static com.fiskmods.lightsabers.common.generator.ModChestGen.*;

import java.util.Random;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;

public class StructureSithTomb extends Structure
{
    public StructureSithTomb(World world, int x, int y, int z)
    {
        super(world, x, y, z);
    }

    @Override
    public void spawnStructure(Random random)
    {
        generateEntrance(random);
        int stairLength = 5;

        int k;

        for (k = 63; !worldObj.isAirBlock(xCoord, k + 1, zCoord + 17 + stairLength); ++k)
        {
            ;
        }

        while (yCoord - stairLength + 5 > k)
        {
            ++stairLength;
        }

        generateStairway(random, stairLength);
        generateAntechamber(random);
        generateAnnex(random);
        xCoord -= 17;
        zCoord -= 5;
        generateBurialChamber(random);
        generateTreasury(random);

        int coffinX = -1;
        int coffinY = 0;
        int coffinZ = 6;
        generateCoffin(20, -1, 10, 2, coffinX, coffinY, coffinZ);
        generateCoffin(23, -1, 10, 2, coffinX, coffinY, coffinZ);
        generateCoffin(26, -1, 10, 2, coffinX, coffinY, coffinZ);
        generateCoffin(20, -1, 2, 0, coffinX, coffinY, coffinZ);
    }

    private void generateEntrance(Random random)
    {
        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 11; ++j)
            {
                for (int k = 0; k < 16; ++k)
                {
                    setBlock(Blocks.air, 0, 4 - i, j, 1 + k);
                }
            }
        }

        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 0, 4 - j, 0, 1 + i);

                int k = 0;

                while (k > 0 && worldObj.isAirBlock(xCoord + 4 - j, yCoord - 1 + k, zCoord + 1 + i))
                {
                    --k;
                    BiomeGenBase biome = worldObj.getBiomeGenForCoords(xCoord + 4 - j, zCoord + 1 + i);
                    setBlock(biome.fillerBlock, biome.field_150604_aj, 4 - j, k, 1 + i);
                }
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 0 : (i == 4 ? 1 : 2), 2 - i, 0, 1);
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 0 : (i == 4 ? 1 : 3), 2 - i, 0, 16);
            setBlock(i == 0 || i == 4 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 4 : (i == 4 ? 5 : 1), 2 - i, i == 0 || i == 4 ? 4 : 5, 1);
            setBlock(i == 0 || i == 4 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 4 : (i == 4 ? 5 : 1), 2 - i, 4, 16);
            setBlock(ModBlocks.darkForcestone, i == 0 || i == 4 ? 0 : 1, 2 - i, 4, 2);
            setBlock(ModBlocks.darkForcestoneStairs, 2, 2 - i, 5, 2);
            setBlock(ModBlocks.darkForcestoneStairs, 3, 2 - i, 5, 15);

            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 3 : (i == 4 ? 2 : 1), 4, 0, 3 + i);
            setBlock(i == 0 || i == 4 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 7 : (i == 4 ? 6 : 1), 4, i == 0 || i == 4 ? 4 : 5, 3 + i);
            setBlock(ModBlocks.darkForcestone, i == 0 || i == 4 ? 0 : 1, 3, 4, 3 + i);
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 3 : (i == 4 ? 2 : 1), 4, 0, 10 + i);
            setBlock(i == 0 || i == 4 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 7 : (i == 4 ? 6 : 1), 4, 4, 10 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 1, 3, 5, 3 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 1, 3, 5, 10 + i);
            setBlock(Blocks.stained_glass, 14, 2, 5, 3 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 4, 2, 4, 9 + i);
            setBlock(i == 0 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 3 : 1, 2, 1, 9 + i);

            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 3 : (i == 4 ? 2 : 0), -4, 0, 3 + i);
            setBlock(i == 0 || i == 4 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 7 : (i == 4 ? 6 : 1), -4, i == 0 || i == 4 ? 4 : 5, 3 + i);
            setBlock(ModBlocks.darkForcestone, i == 0 || i == 4 ? 0 : 1, -3, 4, 3 + i);
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 3 : (i == 4 ? 2 : 0), -4, 0, 10 + i);
            setBlock(i == 0 || i == 4 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 7 : (i == 4 ? 6 : 1), -4, 4, 10 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 0, -3, 5, 3 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 0, -3, 5, 10 + i);
            setBlock(Blocks.stained_glass, 14, -2, 5, 3 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 5, -2, 4, 9 + i);
            setBlock(i == 0 ? ModBlocks.darkForcestoneStairs : ModBlocks.forcestoneSlab, i == 0 ? 3 : 1, -2, 1, 9 + i);

            if (i < 3)
            {
                setBlock(ModBlocks.darkActivatedForcestone, 0, 2, 1 + i, 2);
                setBlock(ModBlocks.darkActivatedForcestone, 0, -2, 1 + i, 2);
                setBlock(ModBlocks.darkActivatedForcestone, 0, 2, 6 + i, 10);
                setBlock(ModBlocks.darkActivatedForcestone, 0, -2, 6 + i, 10);
                setBlock(ModBlocks.darkActivatedForcestone, 0, 2, 6 + i, 14);
                setBlock(ModBlocks.darkActivatedForcestone, 0, -2, 6 + i, 14);
                setBlock(Blocks.stained_glass, 14, -1 + i, 5, 3);
                setBlock(ModBlocks.darkForcestoneStairs, 3, -1 + i, 0, 10);
                setBlock(ModBlocks.darkForcestoneStairs, 6, -1 + i, 0, 14);
                setBlock(ModBlocks.darkForcestoneStairs, 2, -1 + i, 6, 10);
                setBlock(ModBlocks.darkForcestoneStairs, 3, -1 + i, 6, 14);
                setBlock(ModBlocks.darkForcestoneStairs, 1, 2, 6, 11 + i);
                setBlock(ModBlocks.darkForcestoneStairs, 0, -2, 6, 11 + i);
                setBlock(ModBlocks.darkForcestoneStairs, 2, -1 + i, 9, 10);
                setBlock(ModBlocks.darkForcestoneStairs, 3, -1 + i, 9, 14);
                setBlock(ModBlocks.darkForcestoneStairs, 7, -1 + i, 9, 11);
                setBlock(ModBlocks.darkForcestoneStairs, 6, -1 + i, 9, 13);
                setBlock(ModBlocks.darkForcestoneStairs, 2, -1 + i, 10, 11);
                setBlock(ModBlocks.darkForcestoneStairs, 3, -1 + i, 10, 13);
            }

            if (i < 4)
            {
                setBlock(ModBlocks.darkActivatedForcestone, 0, 2, 1 + i, 8);
                setBlock(ModBlocks.darkActivatedForcestone, 0, -2, 1 + i, 8);
                setBlock(ModBlocks.darkForcestone, 2, 2, 1 + i, 14);
                setBlock(ModBlocks.darkForcestone, 2, -2, 1 + i, 14);
            }

            setBlock(ModBlocks.forcestoneSlab, 1, 2 - i, 6, 9);
            setBlock(ModBlocks.darkForcestoneStairs, 1, 2, 9, 10 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 0, -2, 9, 10 + i);
        }

        for (int i = 0; i < 2; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                for (int k = 0; k < 2; ++k)
                {
                    setBlock(ModBlocks.darkForcestone, i == 1 && k == 1 ? 0 : 2, 4 - i, 1 + j, 1 + k);
                    setBlock(ModBlocks.darkForcestone, i == 1 && k == 1 ? 0 : 2, -4 + i, 1 + j, 1 + k);
                }
            }
        }

        for (int i = 0; i < 2; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                for (int k = 0; k < 2; ++k)
                {
                    setBlock(ModBlocks.darkForcestone, 2, 4, 1 + j, 8 + k);
                    setBlock(ModBlocks.darkForcestone, 2, -4, 1 + j, 8 + k);
                    setBlock(ModBlocks.darkForcestone, i == 1 && k == 0 ? 0 : 2, 4 - i, 1 + j, 15 + k);
                    setBlock(ModBlocks.darkForcestone, i == 1 && k == 0 ? 0 : 2, -4 + i, 1 + j, 15 + k);
                }
            }
        }

        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                setBlock(ModBlocks.darkForcestone, j > 0 && i == 1 ? 1 : 0, 3, 1 + i, 8 + j);
                setBlock(ModBlocks.darkForcestone, j > 0 && i == 1 ? 1 : 0, -3, 1 + i, 8 + j);
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                setBlock(ModBlocks.darkForcestone, j == 1 ? 1 : 0, -2 + i, 1 + j, 15);
            }
        }

        for (int i = 0; i < 7; ++i)
        {
            for (int j = 0; j < 6; ++j)
            {
                if (j > 3 || i > 1 && i < 5)
                {
                    setBlock(ModBlocks.darkForcestone, 0, 3 - i, 5, 4 + j);
                }
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                if (i == 0 || i == 4 || j == 0 || j == 4)
                {
                    setBlock(ModBlocks.darkForcestone, 0, -2 + i, 5, 10 + j);
                }
            }
        }

        for (int i = 0; i < 2; ++i)
        {
            int j = i == 0 ? 0 : 6;
            setBlock(ModBlocks.darkForcestoneStairs, 3, 3 - j, 1, 3);
            setBlock(ModBlocks.darkForcestoneStairs, 2, 3 - j, 1, 7);
            setBlock(ModBlocks.darkForcestoneStairs, 7, 3 - j, 3, 3);
            setBlock(ModBlocks.darkForcestoneStairs, 6, 3 - j, 3, 7);
        }

        for (int i = 0; i < 4; ++i)
        {
            int x = i % 2 == 0 ? -4 : 3;
            int y = 6;
            int z = 8 + (i / 2) * 7;

            for (int j = 0; j < 4; ++j)
            {
                int k = j == 0 ? 0 : (j == 1 ? 2 : (j == 2 ? 3 : 1));
                setBlock(ModBlocks.darkForcestoneStairs, k, x + j % 2, y, z + j / 2);
            }
        }

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                setBlock(Blocks.air, 0, -1 + i, 0, 11 + j);
            }
        }

        setBlock(ModBlocks.darkForcestoneStairs, 1, 1, 10, 12);
        setBlock(ModBlocks.darkForcestoneStairs, 0, -1, 10, 12);
        setBlock(ModBlocks.darkForcestoneStairs, 4, 1, 9, 12);
        setBlock(ModBlocks.darkForcestoneStairs, 5, -1, 9, 12);
        setBlock(ModBlocks.darkForcestoneStairs, 4, 1, 3, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 5, -1, 3, 2);
        setBlock(Blocks.stained_glass, 14, 0, 10, 12);
        setBlock(ModBlocks.darkForcestone, 0, 3, 5, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 2, 3, 5, 1);
        setBlock(ModBlocks.darkForcestoneStairs, 2, 4, 5, 1);
        setBlock(ModBlocks.darkForcestoneStairs, 1, 4, 5, 2);
        setBlock(ModBlocks.darkForcestone, 0, -3, 5, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 2, -3, 5, 1);
        setBlock(ModBlocks.darkForcestoneStairs, 2, -4, 5, 1);
        setBlock(ModBlocks.darkForcestoneStairs, 0, -4, 5, 2);

        setBlock(Blocks.stained_hardened_clay, 14, 0, 0, 3);
        setBlock(Blocks.stained_hardened_clay, 14, 1, 0, 4);
        setBlock(Blocks.stained_hardened_clay, 14, -1, 0, 4);
        setBlock(Blocks.stained_hardened_clay, 14, 0, 0, 5);
        setBlock(Blocks.stained_hardened_clay, 14, 1, 0, 6);
        setBlock(Blocks.stained_hardened_clay, 14, -1, 0, 6);
        setBlock(Blocks.stained_hardened_clay, 14, 0, 0, 7);
        setBlock(Blocks.stained_hardened_clay, 14, 0, 0, 8);

        generateStatue(0, 1, 14, 2, false);

        zCoord += 12;
    }

    private void generateStairway(Random random, int stairLength)
    {
        int i1 = 0;

        for (int i = 0; i < 6; ++i)
        {
            for (int j = 0; j <= i1; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 0, 2, -1 - 5 + i, -1 + 5 - j);
                setBlock(ModBlocks.darkForcestone, 0, -2, -1 - 5 + i, -1 + 5 - j);

                if (j != i1)
                {
                    for (int k = 0; k < 3; ++k)
                    {
                        setBlock(Blocks.air, 0, 1 - k, -1 - 5 + i, -1 + 5 - j);
                    }
                }
            }

            ++i1;
        }

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 0, 1 - i, -1 - j, -2 + j);

                if (j > 0)
                {
                    setBlock(ModBlocks.darkForcestoneStairs, 3, 1 - i, -j, -2 + j);
                }
            }

            setBlock(ModBlocks.darkForcestone, 0, 1 - i, -1, 4);
            setBlock(ModBlocks.darkForcestoneStairs, 6, 1 - i, -1, 3);
            setBlock(ModBlocks.darkForcestoneStairs, 6, 1 - i, -2, 4);

            for (int j = 0; j < stairLength; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 0, 1 - i, -8 - j, 5 + j);
                setBlock(ModBlocks.darkForcestoneStairs, 3, 1 - i, -7 - j, 5 + j);
                setBlock(ModBlocks.darkForcestone, 0, 1 - i, -2 - j, 5 + j);
                setBlock(ModBlocks.darkForcestoneStairs, 6, 1 - i, -3 - j, 5 + j);
                setBlock(Blocks.air, 6, 1 - i, -4 - j, 5 + j);
                setBlock(Blocks.air, 6, 1 - i, -5 - j, 5 + j);
                setBlock(Blocks.air, 6, 1 - i, -6 - j, 5 + j);
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < stairLength; ++j)
            {
                if ((i + j + 1) % 5 == 0)
                {
                    setBlock(ModBlocks.darkActivatedForcestone, 8, 2, -3 - i - j, 5 + j);
                    setBlock(ModBlocks.darkActivatedForcestone, 8, -2, -3 - i - j, 5 + j);
                }
                else
                {
                    setBlock(ModBlocks.darkForcestone, 0, 2, -3 - i - j, 5 + j);
                    setBlock(ModBlocks.darkForcestone, 0, -2, -3 - i - j, 5 + j);
                }
            }
        }

        zCoord += 5 + stairLength;
        yCoord -= 7 + stairLength;

        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 10; ++j)
            {
                for (int k = 0; k < 10; ++k)
                {
                    setBlock(Blocks.stone, 0, 4 - i, j - 3, k);
                }
            }
        }

        for (int i = 0; i < 7; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                for (int k = 0; k < 8; ++k)
                {
                    setBlock(Blocks.air, 0, 3 - i, j - 2, k + 1);
                }
            }
        }

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                for (int k = 0; k < 11; ++k)
                {
                    setBlock(Blocks.air, 0, 1 - i, j + 1, k);
                }
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 11; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 0, 2 - i, 0, j);

                if (j < 10)
                {
                    setBlock(ModBlocks.darkForcestone, 0, 2 - i, 4, j + 1);
                }

                if (i < 4)
                {
                    setBlock(ModBlocks.darkForcestone, i == 1 ? 1 : 0, 2, 1 + i, j);
                    setBlock(ModBlocks.darkForcestone, i == 1 ? 1 : 0, -2, 1 + i, j);
                    setBlock(ModBlocks.darkForcestoneStairs, 4, 1, 3, 7 + i);
                    setBlock(ModBlocks.darkForcestoneStairs, 5, -1, 3, 7 + i);
                }
            }
        }

        for (int i = 0; i < 2; ++i)
        {
            setBlock(ModBlocks.darkForcestoneStairs, 4, 1, 4, 1 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 5, -1, 4, 1 + i);
        }

        for (int i = 0; i < 3; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 0, 1 - i, 5, 0);
            setBlock(ModBlocks.darkForcestoneStairs, 6, 1 - i, 4, 0);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 2, i + 1, 4);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -2, i + 1, 4);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 2, i + 1, 6);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -2, i + 1, 6);
            setBlock(ModBlocks.darkForcestoneStairs, 6, 1 - i, 3, 4);
            setBlock(ModBlocks.darkForcestoneStairs, 6, 1 - i, 4, 3);
            setBlock(ModBlocks.darkForcestoneStairs, 7, 1 - i, 3, 6);
        }

        // Redstone stuff start
        setBlock(Blocks.sticky_piston, 0, 0, 4, 5);
        setBlock(Blocks.sticky_piston, 1, 0, -1, 5);

        setBlock(ModBlocks.darkForcestone, 2, 0, 3, 5);
        setBlock(ModBlocks.darkForcestone, 2, 0, 0, 5);

        for (int i = 0; i < 2; ++i)
        {
            setBlock(Blocks.sticky_piston, 4, 3, 1 + i, 5);
            setBlock(Blocks.sticky_piston, 5, -3, 1 + i, 5);
            setBlock(ModBlocks.darkForcestone, 3, 2, 1 + i, 5);
            setBlock(ModBlocks.darkForcestone, 3, -2, 1 + i, 5);

            int j = i == 0 ? 3 : -3;
            setBlock(Blocks.stone, 0, j, 2, 3);
            setBlock(Blocks.redstone_wire, 0, j, 3, 3);
            setBlock(Blocks.redstone_torch, 3, j, 2, 4);
        }

        for (int i = 0; i < 3; ++i)
        {
            setBlock(Blocks.wooden_pressure_plate, 0, 1 - i, 1, 3);
            setBlock(Blocks.wooden_pressure_plate, 0, 1 - i, 1, 7);
        }

        for (int i = 0; i < 7; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                if (i == 0 || i == 6 || j == 0 || j == 4)
                {
                    setBlock(Blocks.stone, 0, 3 - i, -2, 3 + j);
                    setBlock(Blocks.redstone_wire, 0, 3 - i, -1, 3 + j);
                }
            }
        }

        setBlock(Blocks.stone, 0, 1, -2, 5);
        setBlock(Blocks.redstone_torch, 2, 0, -2, 5);
        setBlock(Blocks.redstone_wire, 0, 1, -2, 6);
        setBlock(Blocks.redstone_wire, 0, 1, -2, 4);
        setBlock(Blocks.stone_slab, 8, -3, 3, 4);
        setBlock(Blocks.redstone_wire, 0, -3, 4, 4);
        setBlock(Blocks.redstone_torch, 1, -1, 4, 5);

        for (int i = 0; i < 5; ++i)
        {
            setBlock(Blocks.stone_slab, 8, -3, -2 + i, 2 - i % 2);
            setBlock(Blocks.stone_slab, 8, 3, -2 + i, 2 - i % 2);
            setBlock(Blocks.redstone_wire, 0, -3, -1 + i, 2 - i % 2);
            setBlock(Blocks.redstone_wire, 0, 3, -1 + i, 2 - i % 2);

            if (i < 4)
            {
                setBlock(Blocks.stone_slab, 8, -3, -1 + i, 8 - i % 2);
                setBlock(Blocks.redstone_wire, 0, -3, i, 8 - i % 2);
                setBlock(Blocks.redstone_wire, 0, -2, i == 2 ? 5 : 4, 7 - i);
            }
        }

        zCoord += 10;
    }

    private void generateAntechamber(Random random)
    {
        for (int i = 0; i < 23; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                for (int k = 0; k < 11; ++k)
                {
                    setBlock(ModBlocks.darkForcestone, j == 2 ? 1 : 0, 11 - i, j - 1, 1 + k);
                }
            }
        }

        for (int i = 0; i < 21; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                for (int k = 0; k < 9; ++k)
                {
                    setBlock(Blocks.air, 0, 10 - i, j, 2 + k);
                }
            }
        }

        for (int i = 0; i < 21; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                if (i == 0 || i == 20 || j == 0 || j == 8)
                {
                    int k = i == 0 ? 0 : (i == 20 ? 1 : (j == 0 ? 3 : 2));
                    setBlock(ModBlocks.darkForcestoneStairs, k, 10 - i, 0, 2 + j);
                    setBlock(ModBlocks.darkForcestoneStairs, k + 4, 10 - i, 3, 2 + j);
                    setBlock(ModBlocks.darkForcestone, 0, 10 - i, 4, 2 + j);
                }
            }
        }

        for (int i = 0; i < 19; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                if (i == 0 || i == 18 || j == 0 || j == 6)
                {
                    int k = i == 0 ? 0 : (i == 18 ? 1 : (j == 0 ? 3 : 2));
                    setBlock(ModBlocks.darkForcestoneStairs, k + 4, 9 - i, 4, 3 + j);
                }
            }
        }

        for (int i = 0; i < 17; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                if (i == 0 || i == 16 || j == 0 || j == 4)
                {
                    int k = i == 0 ? 0 : (i == 16 ? 1 : (j == 0 ? 3 : 2));
                    setBlock(ModBlocks.darkForcestoneStairs, k, 8 - i, -1, 4 + j);
                }
                else
                {
                    setBlock(ModBlocks.forcestoneSlab, 1, 8 - i, -1, 4 + j);
                }
            }
        }

        for (int i = 0; i < 15; ++i)
        {
            boolean flag = i == 1 || i == 4 || i == 10 || i == 13;

            if (flag || i == 7)
            {
                setBlock(Blocks.stained_hardened_clay, 14, 7 - i, -1, 5);
                setBlock(Blocks.stained_hardened_clay, 14, 7 - i, -1, 7);
            }

            if (!flag || i == 7)
            {
                setBlock(Blocks.stained_hardened_clay, 14, 7 - i, -1, 6);
            }
        }

        for (int i = 0; i < 2; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 2, 8, i, 11);
            setBlock(ModBlocks.darkForcestone, 2, 4, i, 11);
            setBlock(ModBlocks.darkForcestone, 2, -11, i, 8);
            setBlock(ModBlocks.darkForcestone, 2, -11, i, 4);
        }

        for (int i = 0; i < 3; ++i)
        {
            setBlock(ModBlocks.forcestoneSlab, 1, 1 - i, 3, 1);
            setBlock(Blocks.air, 0, 1 - i, 2, 1);
            setBlock(Blocks.air, 0, 1 - i, 1, 1);
            setBlock(Blocks.air, 0, 1 - i, 0, 2);
            setBlock(ModBlocks.darkForcestoneStairs, 3, 1 - i, 0, 1);

            for (int j = 0; j < 3; ++j)
            {
                setBlock(Blocks.air, 0, 5 + i, j, 11);
                setBlock(Blocks.air, 0, -11, j, 7 - i);
            }

            setBlock(Blocks.air, 0, 5 + i, 0, 10);
            setBlock(Blocks.air, 0, -10, 0, 7 - i);
        }

        for (int i = 0; i < 4; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 2, 2, 1 + i, 1);
            setBlock(ModBlocks.darkForcestone, 2, -2, 1 + i, 1);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 2, i, 2);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -2, i, 2);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 8, i, 10);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 4, i, 10);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -10, i, 8);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -10, i, 4);
        }

        generateStatue(10, 0, 6, 1, true);
        generateStatue(-8, 0, 10, 2, true);
        generateStatue(-8, 0, 2, 0, true);

        setBlock(ModBlocks.darkForcestoneStairs, 4, -7, 3, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 5, -9, 3, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 7, -10, 3, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 4, -7, 3, 10);
        setBlock(ModBlocks.darkForcestoneStairs, 5, -9, 3, 10);
        setBlock(ModBlocks.darkForcestoneStairs, 6, -10, 3, 10);
        setBlock(ModBlocks.darkForcestoneStairs, 7, 10, 3, 5);
        setBlock(ModBlocks.darkForcestoneStairs, 6, 10, 3, 7);
        setBlock(ModBlocks.darkForcestoneStairs, 4, 1, 3, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 5, -1, 3, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 1, 3, 0, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 0, -3, 0, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 4, 7, 3, 10);
        setBlock(ModBlocks.darkForcestoneStairs, 5, 5, 3, 10);
        setBlock(ModBlocks.darkForcestoneStairs, 4, 7, 2, 11);
        setBlock(ModBlocks.darkForcestoneStairs, 5, 5, 2, 11);
        setBlock(ModBlocks.darkForcestoneStairs, 6, -10, 3, 7);
        setBlock(ModBlocks.darkForcestoneStairs, 7, -10, 3, 5);
        setBlock(ModBlocks.darkForcestoneStairs, 6, -11, 2, 7);
        setBlock(ModBlocks.darkForcestoneStairs, 7, -11, 2, 5);

        xCoord += 6;
        zCoord += 11;
    }

    private void generateAnnex(Random random)
    {
        for (int i = 0; i < 15; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                for (int k = 0; k < 9; ++k)
                {
                    if (j == 6)
                    {
                        setBlock(ModBlocks.darkForcestone, 0, i - 9, j - 1, 2 + k);
                    }
                    else
                    {
                        setBlock(i == 0 || i == 14 || j == 0 || j == 5 || k == 0 || k == 8 ? ModBlocks.darkForcestone : Blocks.air, j == 2 ? 1 : 0, i - 9, j - 1, 2 + k);
                    }
                }
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                for (int k = 0; k < 2; ++k)
                {
                    setBlock(i == 0 || i == 4 || j == 0 || j == 4 ? ModBlocks.darkForcestone : Blocks.air, 0, 2 - i, j - 1, k + 1);
                }
            }
        }

        for (int i = 0; i < 13; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                if (i == 0 || i == 12 || j == 0 || j == 6)
                {
                    int k = i == 0 ? 1 : (i == 12 ? 0 : (j == 0 ? 3 : 2));
                    setBlock(i == 0 || i == 12 || j == 0 || j == 6 ? ModBlocks.darkForcestoneStairs : Blocks.air, k, i - 8, 0, 3 + j);
                    setBlock(i == 0 || i == 12 || j == 0 || j == 6 ? ModBlocks.darkForcestoneStairs : Blocks.air, k - 4, i - 8, 3, 3 + j);
                }
            }
        }

        for (int i = 0; i < 11; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                if (i == 0 || i == 10 || j == 0 || j == 4)
                {
                    int k = i == 0 ? 1 : (i == 10 ? 0 : (j == 0 ? 3 : 2));
                    setBlock(i == 0 || i == 10 || j == 0 || j == 4 ? ModBlocks.darkForcestoneStairs : Blocks.air, k - 4, i - 7, 4, 4 + j);
                }
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                if (i == 0 || i == 8 || j == 0 || j == 2)
                {
                    int k = i == 0 ? 0 : (i == 8 ? 1 : (j == 0 ? 2 : 3));
                    setBlock(i == 0 || i == 8 || j == 0 || j == 2 ? ModBlocks.darkForcestoneStairs : Blocks.air, k - 4, i - 6, 4, 5 + j);
                }
            }
        }

        for (int i = 0; i < 2; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 2, 2, i, 2);
            setBlock(ModBlocks.darkForcestone, 2, -2, i, 2);
            setBlock(ModBlocks.darkForcestoneStairs, 4, 1, 2, 1 + i);
            setBlock(ModBlocks.darkForcestoneStairs, 5, -1, 2, 1 + i);
        }

        for (int i = 0; i < 3; ++i)
        {
            setBlock(Blocks.air, 0, 1 - i, 0, 3);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 2, i, 3);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -2, i, 3);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 4, i, 5);
            setBlock(ModBlocks.darkActivatedForcestone, 0, 4, i, 7);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -8, i, 5);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -8, i, 7);

            setBlock(ModBlocks.darkForcestone, 1, 4, 3, 5 + i);
            setBlock(ModBlocks.darkForcestone, 1, -8, 3, 5 + i);
        }

        for (int i = 0; i < 5; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 0, 2 - i, 3, 3);
        }

        setBlock(ModBlocks.darkForcestoneStairs, 6, 4, 3, 4);
        setBlock(ModBlocks.darkForcestoneStairs, 7, 4, 3, 8);
        setBlock(ModBlocks.darkForcestoneStairs, 6, -8, 3, 4);
        setBlock(ModBlocks.darkForcestoneStairs, 7, -8, 3, 8);

        generateStructureChestContents(random, 4, 0, 6, ChestGenHooks.getItems(SITH_TOMB_ANNEX, random), ChestGenHooks.getCount(SITH_TOMB_ANNEX, random));
        generateStructureChestContents(random, -8, 0, 6, ChestGenHooks.getItems(SITH_TOMB_ANNEX, random), ChestGenHooks.getCount(SITH_TOMB_ANNEX, random));
    }

    private void generateBurialChamber(Random random)
    {
        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                setBlock(i == 0 || i == 4 || j == 0 || j == 4 ? ModBlocks.darkForcestone : Blocks.air, 0, -1, i - 1, 2 - j);
            }
        }

        for (int i = 0; i < 19; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                for (int k = 0; k < 13; ++k)
                {
                    setBlock(i == 0 || i == 18 || j == 0 || j == 6 || k == 0 || k == 12 ? ModBlocks.darkForcestone : Blocks.air, j == 2 ? 1 : 0, -i - 2, j - 1, 6 - k);
                }
            }
        }

        for (int i = 0; i < 17; ++i)
        {
            for (int j = 0; j < 11; ++j)
            {
                if (i == 0 || i == 16 || j == 0 || j == 10)
                {
                    int k = i == 0 ? 0 : (i == 16 ? 1 : (j == 0 ? 2 : 3));
                    setBlock(i == 0 || i == 16 || j == 0 || j == 10 ? ModBlocks.darkForcestoneStairs : Blocks.air, k, -i - 3, 0, 5 - j);
                    setBlock(i == 0 || i == 16 || j == 0 || j == 10 ? ModBlocks.darkForcestoneStairs : Blocks.air, k + 4, -i - 3, 5, 5 - j);
                }
            }
        }

        for (int i = 0; i < 7; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                if (i == 0 || i == 6 || j == 0 || j == 4)
                {
                    int k = i == 0 ? 1 : (i == 6 ? 0 : (j == 0 ? 3 : 2));
                    setBlock(i == 0 || i == 6 || j == 0 || j == 4 ? ModBlocks.darkForcestoneStairs : Blocks.air, k, -i - 5, -1, 2 - j);
                }
            }
        }

        for (int i = 0; i < 12; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                if (i == 0 || i == 11 || j == 0 || j == 6)
                {
                    int k = i == 0 ? 1 : (i == 11 ? 0 : (j == 0 ? 3 : 2));
                    setBlock(i == 0 || i == 11 || j == 0 || j == 6 ? ModBlocks.darkForcestoneStairs : Blocks.air, k + 4, -i - 6, 5, 3 - j);
                }
            }
        }

        for (int i = 0; i < 3; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 1, -3, 4, 1 - i);
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 6 : (i == 2 ? 7 : 4), -3, 3, 1 - i);
            setBlock(ModBlocks.darkForcestone, 0, -14, 0, 1 - i);
            setBlock(ModBlocks.forcestoneSlab, 1, -18 + i, 0, 4);
            setBlock(ModBlocks.forcestoneSlab, 1, -18 + i, 0, -4);
            setBlock(ModBlocks.forcestoneSlab, 1, -13, 0, 1 - i);

            for (int j = 0; j < 2; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 1, -2, 2 + i, 3 + j);
                setBlock(ModBlocks.darkForcestone, 1, -2, 2 + i, -4 + j);
                setBlock(ModBlocks.darkForcestone, 1, -15 + i, 2 + j, -6);
            }

            for (int j = 0; j < 3; ++j)
            {
                setBlock(Blocks.air, 0, -2, i, 1 - j);
            }

            setBlock(Blocks.air, 0, -3, 0, 1 - i);
        }

        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 7; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 0, -19 + i, 0, 3 - j);
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 0, -3, 5, 2 - i);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -3, i, 2);
            setBlock(ModBlocks.darkActivatedForcestone, 0, -3, i, -2);
            setBlock(ModBlocks.darkForcestoneStairs, 4, -4, 5, 2 - i);
            setBlock(ModBlocks.darkForcestone, 0, -15, 0, 2 - i);
            setBlock(i == 4 ? ModBlocks.darkForcestone : ModBlocks.darkActivatedForcestone, 0, -19, 1 + i, 3);
            setBlock(i == 4 ? ModBlocks.darkForcestone : ModBlocks.darkActivatedForcestone, 0, -19, 1 + i, -3);

            for (int j = 0; j < 3; ++j)
            {
                setBlock(ModBlocks.darkForcestone, 1, -20, 2 + j, 2 - i);
            }
        }

        for (int i = 0; i < 6; ++i)
        {
            setBlock(ModBlocks.darkForcestone, 2, -3, i, 5);
            setBlock(ModBlocks.darkForcestone, 2, -3, i, -5);
            setBlock(ModBlocks.darkForcestone, 2, -19, i, 5);
            setBlock(ModBlocks.darkForcestone, 2, -19, i, -5);
        }

        for (int i = 0; i < 2; ++i)
        {
            setBlock(ModBlocks.darkForcestoneStairs, 6, -1 - i, 2, 1);
            setBlock(ModBlocks.darkForcestoneStairs, 7, -1 - i, 2, -1);
        }

        generateStatue(-6, 0, 5, 2, true);
        generateStatue(-10, 0, 5, 2, true);
        generateStatue(-14, 0, 5, 2, true);
        generateStatue(-6, 0, -5, 0, true);
        generateStatue(-10, 0, -5, 0, true);
        generateStatue(-14, 0, -5, 0, true);
        generateStatue(-19, 1, 0, 3, true);

        setBlock(ModBlocks.darkForcestoneStairs, 6, -19, 5, 2);
        setBlock(ModBlocks.darkForcestoneStairs, 7, -19, 5, -2);
        setBlock(ModBlocks.forcestoneSlab, 1, -15, 0, 3);
        setBlock(ModBlocks.forcestoneSlab, 1, -14, 0, 2);
        setBlock(ModBlocks.forcestoneSlab, 1, -15, 0, -3);
        setBlock(ModBlocks.forcestoneSlab, 1, -14, 0, -2);

        for (int i = 0; i < 5; ++i)
        {
            if (i == 0 || i == 3)
            {
                setBlock(Blocks.stained_hardened_clay, 14, -6 - i, -1, 0);
            }
            else
            {
                setBlock(Blocks.stained_hardened_clay, 14, -6 - i, -1, 1);
                setBlock(Blocks.stained_hardened_clay, 14, -6 - i, -1, -1);
            }
        }

        setBlock(ModBlocks.sithCoffin, 1, -15, 1, 0);
        setBlock(ModBlocks.sithCoffin, 9, -16, 1, 0);
        fillStructureInventory(ModBlocks.sithCoffin, random, -15, 1, 0, ChestGenHooks.getItems(SITH_TOMB_COFFIN, random), ChestGenHooks.getCount(SITH_TOMB_COFFIN, random));

        xCoord -= 14;
        yCoord += 1;
        zCoord -= 6;
    }

    private void generateTreasury(Random random)
    {
        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 5; ++j)
            {
                for (int k = 0; k < 3; ++k)
                {
                    setBlock(i == 0 || i == 4 || j == 0 || j == 4 ? ModBlocks.darkForcestone : Blocks.air, i == 0 || i == 4 ? (k == 1 ? 0 : 2) : 0, 2 - i, j - 1, -k - 1);
                }
            }
        }

        for (int i = 0; i < 11; ++i)
        {
            for (int j = 0; j < 6; ++j)
            {
                for (int k = 0; k < 9; ++k)
                {
                    setBlock(ModBlocks.darkForcestone, 0, 5 - i, j - 1, -4 - k);
                }
            }
        }

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                for (int k = 0; k < 7; ++k)
                {
                    setBlock(Blocks.air, 0, 2 - i, j, -4 - k);
                }
            }
        }

        for (int i = 0; i < 3; ++i)
        {
            setBlock(ModBlocks.darkForcestoneStairs, 1, 1, 0, -6 - i);
            setBlock(ModBlocks.darkForcestoneStairs, 0, -1, 0, -6 - i);
            setBlock(ModBlocks.darkForcestoneStairs, 4, 4, 4, -6 - i);
            setBlock(ModBlocks.darkForcestoneStairs, 5, -4, 4, -6 - i);
            setBlock(ModBlocks.darkForcestoneStairs, 7, 1 - i, 4, -11);

            for (int j = 0; j < 4; ++j)
            {
                setBlock(Blocks.air, 0, 1 - i, j, -11);

                for (int k = 0; k < 2; ++k)
                {
                    setBlock(Blocks.air, 0, 3 + k, j, -6 - i);
                    setBlock(Blocks.air, 0, -3 - k, j, -6 - i);
                }
            }
        }

        for (int i = 0; i < 2; ++i)
        {
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 3 : 2, 3, 0, -6 - i * 2);
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 3 : 2, -3, 0, -6 - i * 2);
            setBlock(ModBlocks.darkForcestoneStairs, i == 0 ? 1 : 0, 1 - i * 2, 0, -10);
        }

        for (int i = 0; i < 4; ++i)
        {
            setBlock(ModBlocks.darkActivatedForcestone, 2, 2, i, -4);
            setBlock(ModBlocks.darkActivatedForcestone, 2, -2, i, -4);
            setBlock(ModBlocks.darkActivatedForcestone, 2, 2, i, -10);
            setBlock(ModBlocks.darkActivatedForcestone, 2, -2, i, -10);
            setBlock(ModBlocks.darkActivatedForcestone, 2, 3, i, -5);
            setBlock(ModBlocks.darkActivatedForcestone, 2, -3, i, -5);
            setBlock(ModBlocks.darkActivatedForcestone, 2, 3, i, -9);
            setBlock(ModBlocks.darkActivatedForcestone, 2, -3, i, -9);
        }

        generateStatue(0, 0, -11, 0, false);
        generateStatue(4, 0, -7, 1, false);
        generateStatue(-4, 0, -7, 3, false);

        setBlock(ModBlocks.darkForcestoneStairs, 3, 0, 0, -6);
        setBlock(ModBlocks.darkForcestoneStairs, 2, 0, 0, -8);
        setBlock(Blocks.redstone_block, 0, 0, 0, -7);

        if (random.nextBoolean())
        {
            setBlock(ModBlocks.holocron, 1, 0, 1, -7);
        }

        generateStructureChestContents(random, 0, 0, -10, ChestGenHooks.getItems(SITH_TOMB_TREASURY, random), ChestGenHooks.getCount(SITH_TOMB_TREASURY, random));
        generateStructureChestContents(random, 3, 0, -7, ChestGenHooks.getItems(SITH_TOMB_TREASURY, random), ChestGenHooks.getCount(SITH_TOMB_TREASURY, random));
        generateStructureChestContents(random, -3, 0, -7, ChestGenHooks.getItems(SITH_TOMB_TREASURY, random), ChestGenHooks.getCount(SITH_TOMB_TREASURY, random));
    }

    private void generateStatue(int x, int y, int z, int rotation, boolean foot)
    {
        if (rotation % 2 == 0)
        {
            setBlock(ModBlocks.darkForcestone, 2, x, y + 1, z);
            setBlock(ModBlocks.darkForcestone, 1, x, y + 2, z);
            setBlock(ModBlocks.darkForcestoneStairs, 5, x + 1, y + 2, z);
            setBlock(ModBlocks.darkForcestoneStairs, 4, x - 1, y + 2, z);
            setBlock(Blocks.redstone_block, 0, x, y + 3, z);

            if (foot)
            {
                setBlock(ModBlocks.darkForcestone, 0, x, y, z);

                if (rotation == 2)
                {
                    setBlock(ModBlocks.darkForcestoneStairs, 2, x, y, z - 1);
                }
                else
                {
                    setBlock(ModBlocks.darkForcestoneStairs, 3, x, y, z + 1);
                }
            }
            else
            {
                setBlock(ModBlocks.darkForcestone, 2, x, y, z);
            }
        }
        else
        {
            setBlock(ModBlocks.darkForcestone, 2, x, y + 1, z);
            setBlock(ModBlocks.darkForcestone, 1, x, y + 2, z);
            setBlock(ModBlocks.darkForcestoneStairs, 7, x, y + 2, z + 1);
            setBlock(ModBlocks.darkForcestoneStairs, 6, x, y + 2, z - 1);
            setBlock(Blocks.redstone_block, 0, x, y + 3, z);

            if (foot)
            {
                setBlock(ModBlocks.darkForcestone, 0, x, y, z);

                if (rotation == 1)
                {
                    setBlock(ModBlocks.darkForcestoneStairs, 0, x - 1, y, z);
                }
                else
                {
                    setBlock(ModBlocks.darkForcestoneStairs, 1, x + 1, y, z);
                }
            }
            else
            {
                setBlock(ModBlocks.darkForcestone, 2, x, y, z);
            }
        }
    }

    private void generateCoffin(int x, int y, int z, int rotation, int coffinX, int coffinY, int coffinZ)
    {
        setBlock(ModBlocks.sithStoneCoffin, rotation, x, y, z);
        setBlock(ModBlocks.sithStoneCoffin, rotation + 8, x, y + 1, z);

        TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z);
        TileEntitySithCoffin tile1 = (TileEntitySithCoffin) worldObj.getTileEntity(xCoord + coffinX, yCoord + coffinY, zCoord + coffinZ);

        if (tile != null && tile1 != null)
        {
            tile.mainCoffin = tile1;
        }
    }
}
