package fiskfille.lightsabers.common.generator.structure;

import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomePredicate
{
	public abstract boolean matches(BiomeGenBase biome);
}
