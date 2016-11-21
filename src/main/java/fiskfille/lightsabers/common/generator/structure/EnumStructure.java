package fiskfille.lightsabers.common.generator.structure;

import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;

public enum EnumStructure
{
	SITH_TOMB(StructureSithTomb.class, 8, 32, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F),
	JEDI_TEMPLE(StructureJediTemple.class, 16, 48, new BiomePredicate()
	{
		@Override
		public boolean matches(BiomeGenBase biome)
		{
			return biome.rootHeight > 0 && biome.temperature < 1.5F;
		}
	});
	
	public final Class<? extends Structure> structureClass;
	public int minDistance;
	public int maxDistance;
	public final BiomePredicate biomePredicate;
    
	private EnumStructure(Class<? extends Structure> clazz, int min, int max, BiomePredicate predicate)
	{
		structureClass = clazz;
		minDistance = min;
		maxDistance = max;
		biomePredicate = predicate;
	}
	
	private EnumStructure(Class<? extends Structure> clazz, int min, int max, final BiomeGenBase... biomes)
	{
		this(clazz, min, max, new BiomePredicate()
		{
			@Override
			public boolean matches(BiomeGenBase biome)
			{
				return Arrays.asList(biomes).contains(biome);
			}
		});
	}
}
