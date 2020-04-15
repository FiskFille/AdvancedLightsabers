package com.fiskmods.lightsabers.common.generator;

import java.util.Random;

import com.fiskmods.lightsabers.common.generator.structure.EnumStructure;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;

public enum WorldGeneratorStructures implements IWorldGenerator
{
    INSTANCE;
    
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.dimensionId)
        {
        case 0:
            generateOverworld(world, random, chunkX * 16 + 8, chunkZ * 16 + 8);
            break;
        case -1:
            generateNether(world, random, chunkX * 16 + 8, chunkZ * 16 + 8);
            break;
        }
    }

    public static Random getRandomForCoords(World world, int x, int z)
    {
        return new Random(x * 341873128712L + z * 132897987541L + world.getSeed() + 235785655);
    }

    public static boolean canSpawnStructureAtCoords(World world, int x, int z, EnumStructure structure)
    {
        int xOriginal = x;
        int zOriginal = z;

        if (x < 0)
        {
            x -= structure.maxDistance - 1;
        }

        if (z < 0)
        {
            z -= structure.maxDistance - 1;
        }

        int x2 = x / structure.maxDistance;
        int z2 = z / structure.maxDistance;

//		Random random = world.setRandomSeed(x2, z2, 235785655);
        Random random = getRandomForCoords(world, x2, z2);

        x2 *= structure.maxDistance;
        z2 *= structure.maxDistance;
        x2 += random.nextInt(structure.maxDistance - structure.minDistance); // Add between 0 and diff between min/max dist
        z2 += random.nextInt(structure.maxDistance - structure.minDistance);

        if (xOriginal == x2 && zOriginal == z2)
        {
            return structure.biomePredicate.apply(world.getWorldChunkManager().getBiomeGenAt(xOriginal, zOriginal));
        }

        return false;
    }

    public static void generateStructure(World world, int x, int z, EnumStructure structure) throws Exception
    {
        Random rand = getRandomForCoords(world, x, z);
        int y = Math.max(world.getTopSolidOrLiquidBlock(x, z), world.provider.getAverageGroundLevel());
        
        structure.construct(world, x, y, z, rand).spawnStructure(rand);
    }

    public void generateOverworld(World world, Random random, int x, int z)
    {
        WorldInfo info = world.getWorldInfo();

        if (info.getTerrainType() != WorldType.FLAT && info.isMapFeaturesEnabled())
        {
            for (EnumStructure enumStructure : EnumStructure.values())
            {
                if (canSpawnStructureAtCoords(world, x, z, enumStructure))
                {
                    try
                    {
                        generateStructure(world, x, z, enumStructure);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void generateNether(World world, Random random, int chunkX, int chunkZ)
    {
    }
}
