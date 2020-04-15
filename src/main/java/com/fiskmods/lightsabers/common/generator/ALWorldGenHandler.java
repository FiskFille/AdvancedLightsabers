package com.fiskmods.lightsabers.common.generator;
//package com.fiskmods.lightsabers.common.generator;
//
//import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.*;
//
//import java.util.Random;
//
//import cpw.mods.fml.common.eventhandler.SubscribeEvent;
//import net.minecraft.init.Blocks;
//import net.minecraft.world.World;
//import net.minecraft.world.gen.feature.WorldGenMinable;
//import net.minecraft.world.gen.feature.WorldGenerator;
//import net.minecraftforge.event.terraingen.OreGenEvent;
//import net.minecraftforge.event.terraingen.PopulateChunkEvent;
//import net.minecraftforge.event.terraingen.TerrainGen;
//
//public class ALWorldGenHandler
//{
//    private World world;
//    private Random rand;
//    private int xCoord;
//    private int zCoord;
//    
//    public WorldGenerator diamondGen;
//    public WorldGenerator goldGen;
////    public WorldGenerator crystalGen;
//
//    public ALWorldGenHandler()
//    {
//        diamondGen = new WorldGenMinable(Blocks.diamond_block, 8);
//        goldGen = new WorldGenMinable(Blocks.gold_block, 6);
////        crystalGen = new WorldGenCrystal(TFBlocks.energonCrystal, Material.rock);
//    }
//
//    @SubscribeEvent
//    public void onOreGenPost(OreGenEvent.Post event)
//    {
//        world = event.world;
//        rand = event.rand;
//        xCoord = event.worldX;
//        zCoord = event.worldZ;
//
//        genStandardOre(2, diamondGen, 16);
//        genStandardOre(4, goldGen, 32);
//    }
//    
//    @SubscribeEvent
//    public void onPopulateChunkPost(PopulateChunkEvent.Post event)
//    {
//        world = event.world;
//        rand = event.rand;
//        xCoord = event.chunkX * 16;
//        zCoord = event.chunkZ * 16;
//        
////        genStandardOre(100, crystalGen, 48);
//    }
//
//    protected void genStandardOre(int veins, WorldGenerator generator, int maxHeight)
//    {
//        if (TerrainGen.generateOre(world, rand, generator, xCoord, zCoord, CUSTOM))
//        {
//            for (int i = 0; i < veins; ++i)
//            {
//                int x = xCoord + rand.nextInt(16);
//                int y = rand.nextInt(maxHeight);
//                int z = zCoord + rand.nextInt(16);
//                
//                generator.generate(world, rand, x, y, z);
//            }
//        }
//    }
//}
