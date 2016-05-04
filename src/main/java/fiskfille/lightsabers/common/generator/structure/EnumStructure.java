package fiskfille.lightsabers.common.generator.structure;

import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;

import com.google.common.collect.Lists;

public enum EnumStructure
{
	SITH_TOMB(StructureSithTomb.class, 8, 32, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F);
	
	public final Class<? extends Structure> structureClass;
	public final int minDistance;
	public final int maxDistance;
	public final List<BiomeGenBase> biomeList;
    
	private EnumStructure(Class<? extends Structure> clazz, int min, int max, BiomeGenBase... biomes)
	{
		structureClass = clazz;
		minDistance = min;
		maxDistance = max;
		biomeList = Lists.newArrayList();
		
		for (BiomeGenBase biome : biomes)
		{
			biomeList.add(biome);
		}
	}
}
