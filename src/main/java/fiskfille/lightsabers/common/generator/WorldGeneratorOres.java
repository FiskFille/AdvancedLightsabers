package fiskfille.lightsabers.common.generator;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.IWorldGenerator;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.tileentity.TileEntityCrystal;

public class WorldGeneratorOres implements IWorldGenerator 
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch (world.provider.dimensionId)
		{
		case 0:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
			break;
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}
	
	public boolean isCrystalCaveChunk(World world, int x, int z)
	{
		Random rand = new Random(world.getSeed() + (long)(x * x * 0x4c1906) + (long)(x * 0x5ac0db) + (long)(z * z) * 0x4307a7L + (long)(z * 0x5f24f) ^ 0x3ad8025f);
		return rand.nextInt(33) == 0;
	}
	
	public boolean isCrystalCaveChunk(World world, Chunk chunk)
	{
		return isCrystalCaveChunk(world, chunk.xPosition, chunk.zPosition);
	}
	
	public void generateOverworld(World world, Random random, int chunkX, int chunkZ)
	{
		if (world.getWorldInfo().getTerrainType() != WorldType.FLAT)
		{
			Chunk chunk = world.getChunkFromBlockCoords(chunkX, chunkZ);
			
			if (isCrystalCaveChunk(world, chunk) && world.getBiomeGenForCoords(chunkX, chunkZ) != BiomeGenBase.ocean)
			{
				List<int[]> airBlocks = Lists.newArrayList();
				
				for (int i = 0; i < 16; ++i)
				{
					for (int j = 0; j < 16; ++j)
					{
						int topBlock = 256;
						
						while (world.getBlock(chunkX + i, topBlock, chunkZ + j) == Blocks.air && topBlock > 0)
						{
							--topBlock;
						}
						
						for (int y = 0; y < topBlock - 10; ++y)
						{
							if (world.getBlock(chunkX + i, y, chunkZ + j) == Blocks.air)
							{
								airBlocks.add(new int[] {chunkX + i, y, chunkZ + j});
							}
						}
					}
				}
				
				if (airBlocks.size() > 1024)
				{
					int[] aint = airBlocks.get(random.nextInt(airBlocks.size()));
					int x = aint[0];
					int y = aint[1];
					int z = aint[2];
					int count = 0;
					
					while (y < world.getTopSolidOrLiquidBlock(x, z))
					{
						if (count < 10 + random.nextInt(10))
						{
							y += 1;
						}
						else
						{
							if (random.nextInt(3) == 0)
							{
								x += (random.nextInt(3) - 1) * 2;
							}
							
							if (random.nextInt(9) == 0)
							{
								y += 1;
							}
							
							if (random.nextInt(3) == 0)
							{
								z += (random.nextInt(3) - 1) * 2;
							}
						}
						
						new WorldGenCrystalCaveEntrance(32).generate(world, random, x, y, z);
						++count;
					}
					
					int radius = 1;
					
					for (int i = -radius; i <= radius; ++i)
					{
						for (int j = -radius; j <= radius; ++j)
						{
							generateCrystal(20, ModBlocks.lightsaberCrystal, 64, world, random, chunkX + i * 16, chunkZ + j * 16);
						}
					}
				}
			}
		}
	}
	
	public void generateNether(World world, Random random, int chunkX, int chunkZ)
	{
		
	}
	
	public void generateOre(int veinsPerChunk, Block block, int veinSize, int minY, World world, Random random, int chunkX, int chunkZ)
	{
		for (int i = 0; i < veinsPerChunk; i++)
		{
			int randPosX = chunkX + random.nextInt(16);
			int randPosY = random.nextInt(minY);
			int randPosZ = chunkZ + random.nextInt(16);
			new WorldGenMinable(block, veinSize).generate(world, random, randPosX, randPosY, randPosZ);
		}
	}
	
	public void generateCrystal(int veinsPerChunk, Block block, int minY, World world, Random random, int chunkX, int chunkZ)
	{
		for (int i = 0; i < veinsPerChunk; i++)
		{
			int randPosX = chunkX + random.nextInt(16);
			int randPosY = random.nextInt(minY);
			int randPosZ = chunkZ + random.nextInt(16);
	        
			if (world.getBlock(randPosX, randPosY, randPosZ) == Blocks.air)
			{   				
				if (world.getBlock(randPosX + 1, randPosY, randPosZ).getMaterial() == Material.rock)
		    	{
					setCrystal(world, block, randPosX, randPosY, randPosZ, 2, random);
		    	}
		    	else if (world.getBlock(randPosX, randPosY, randPosZ + 1).getMaterial() == Material.rock)
		    	{
					setCrystal(world, block, randPosX, randPosY, randPosZ, 4, random);
		    	}
		    	else if (world.getBlock(randPosX - 1, randPosY, randPosZ).getMaterial() == Material.rock)
		    	{
		    		setCrystal(world, block, randPosX, randPosY, randPosZ, 1, random);
		    	}
		    	else if (world.getBlock(randPosX, randPosY, randPosZ - 1).getMaterial() == Material.rock)
		    	{
		    		setCrystal(world, block, randPosX, randPosY, randPosZ, 3, random);
		    	}
		    	if (world.getBlock(randPosX, randPosY - 1, randPosZ).getMaterial() == Material.rock)
		    	{
					setCrystal(world, block, randPosX, randPosY, randPosZ, 5, random);
		    	}
		    	if (world.getBlock(randPosX, randPosY + 1, randPosZ).getMaterial() == Material.rock)
		    	{
					setCrystal(world, block, randPosX, randPosY, randPosZ, 6, random);
		    	}
			}
		}
	}
	
	public void setCrystal(World world, Block block, int x, int y, int z, int metadata, Random rand)
	{
		List<Integer> list = Lists.newArrayList();
		
		for (Map.Entry<Integer, Integer> e : LightsaberColors.rarityMap.entrySet())
		{
			if (e.getValue() != LightsaberColors.RARITY_EPIC)
			{
				for (int i = 0; i <= 2 - e.getValue(); ++i)
				{
					list.add(e.getKey());
				}
			}
		}
		
		world.setBlock(x, y, z, block, metadata, 2);
		
		TileEntityCrystal tile = (TileEntityCrystal)world.getTileEntity(x, y, z);
		int colorId = list.get(rand.nextInt(list.size()));
		
		if (tile != null)
		{
			tile.colorId = colorId;
		}
	}
}
