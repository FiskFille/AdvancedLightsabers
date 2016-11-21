package fiskfille.lightsabers.common.generator;

import java.lang.reflect.Constructor;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;
import cpw.mods.fml.common.IWorldGenerator;
import fiskfille.lightsabers.common.generator.structure.EnumStructure;
import fiskfille.lightsabers.common.generator.structure.Structure;

public class WorldGeneratorStructures implements IWorldGenerator 
{
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

	protected boolean canSpawnStructureAtCoords(World world, int x, int z, EnumStructure structure)
	{
		int k = x;
		int l = z;

		if (x < 0)
		{
			x -= structure.maxDistance - 1;
		}

		if (z < 0)
		{
			z -= structure.maxDistance - 1;
		}

		int i1 = x / structure.maxDistance;
		int j1 = z / structure.maxDistance;
		Random random = world.setRandomSeed(i1, j1, 235785655);
		i1 *= structure.maxDistance;
		j1 *= structure.maxDistance;
		i1 += random.nextInt(structure.maxDistance - structure.minDistance);
		j1 += random.nextInt(structure.maxDistance - structure.minDistance);

		if (k == i1 && l == j1)
		{
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(k, l);

			return structure.biomePredicate.matches(biome);
		}

		return false;
	}

	public void generateOverworld(World world, Random random, int x, int z)
	{
		WorldInfo info = world.getWorldInfo();

		if (info.getTerrainType() != WorldType.FLAT && info.isMapFeaturesEnabled())
		{
			Chunk chunk = world.getChunkFromBlockCoords(x, z);
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

			for (EnumStructure enumStructure : EnumStructure.values())
			{
				if (canSpawnStructureAtCoords(world, x, z, enumStructure))
				{
					int y = Math.max(world.getTopSolidOrLiquidBlock(x, z), world.provider.getAverageGroundLevel());

					try
					{
						Constructor c = enumStructure.structureClass.getConstructor(World.class, int.class, int.class, int.class);
						Structure structure = (Structure)c.newInstance(world, x, y, z);
						structure.spawnStructure(random);
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
