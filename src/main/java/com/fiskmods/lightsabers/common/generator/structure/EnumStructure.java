package com.fiskmods.lightsabers.common.generator.structure;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public enum EnumStructure
{
    SITH_TOMB(new Constructor()
    {
        @Override
        public Structure construct(World world, int x, int y, int z, Random rand)
        {
            return new StructureSithTomb(world, x, y, z);
        }
    }, 8, 32, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F),
    JEDI_TEMPLE(new Constructor()
    {
        @Override
        public Structure construct(World world, int x, int y, int z, Random rand)
        {
            return new StructureJediTemple(0, world, x, y, z);
        }
    }, 24, 64, new TemplePredicate());
    
    public final Predicate<BiomeGenBase> biomePredicate;
    private final Constructor constructor;
    public int minDistance;
    public int maxDistance;

    private EnumStructure(Constructor c, int min, int max, Predicate<BiomeGenBase> predicate)
    {
        biomePredicate = predicate;
        minDistance = min;
        maxDistance = max;
        constructor = c;
    }
    
    private EnumStructure(Constructor c, int min, int max, final BiomeGenBase... biomes)
    {
        this(c, min, max, new Predicate<BiomeGenBase>()
        {
            List<BiomeGenBase> biomeList = Lists.newArrayList(biomes);

            @Override
            public boolean apply(BiomeGenBase biome)
            {
                return biomeList.contains(biome);
            }
        });
    }
    
    public Structure construct(World world, int x, int y, int z, Random rand)
    {
        return constructor.construct(world, x, y, z, rand);
    }
    
    private interface Constructor
    {
        Structure construct(World world, int x, int y, int z, Random rand);
    }
    
    private static class TemplePredicate implements Predicate<BiomeGenBase>
    {
        @Override
        public boolean apply(BiomeGenBase biome)
        {
            return biome.rootHeight > 0 && biome.temperature < 1.5F;
        }
    }
}
