//package com.fiskmods.lightsabers.common.event;
//
//import java.util.Random;
//
//import com.fiskmods.lightsabers.common.block.ModBlocks;
//
//import net.minecraft.block.Block;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.MathHelper;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.BiomeGenBase;
//import net.minecraft.world.chunk.Chunk;
//import net.minecraft.world.chunk.IChunkProvider;
//import net.minecraft.world.gen.MapGenBase;
//
//public class MapGenCrystalCave extends MapGenBase
//{
//    public final MapGenBase caveGenerator;
//
//    public MapGenCrystalCave(MapGenBase originalGen)
//    {
//        caveGenerator = originalGen;
//    }
//
//    public static boolean isCrystalCaveChunk(World world, int chunkX, int chunkZ)
//    {
//        return random(world.getSeed(), chunkX, chunkZ).nextInt(33) == 0;
//    }
//
//    public static boolean isCrystalCaveChunk(World world, Chunk chunk)
//    {
//        return isCrystalCaveChunk(world, chunk.xPosition, chunk.zPosition);
//    }
//
//    public static Random random(long seed, int chunkX, int chunkZ)
//    {
//        int x = (chunkX / 3) * 3;
//        int z = (chunkZ / 3) * 3;
//        return new Random(seed + x * x * 0x4c1906 + x * 0x5ac0db + z * z * 0x4307a7L + z * 0x5f24f ^ 0x3ad8025f);
//    }
//
//    protected void generateDeadEnd(long seed, Random crystalCave, int chunkX, int chunkZ, Block[] data, double randX, double randY, double randZ)
//    {
//        generate(seed, crystalCave, chunkX, chunkZ, data, randX, randY, randZ, 1.0F + rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
//    }
//
//    protected void generate(long seed, Random crystalCave, int chunkX, int chunkZ, Block[] data, double randX, double randY, double randZ, float widthFactor, float horizontal, float vertical, int argI1, int argI2, double heightFactor)
//    {
//        Random random = new Random(seed);
//
//        if (argI2 <= 0)
//        {
//            int i = range * 16 - 16;
//            argI2 = i - random.nextInt(i / 4);
//        }
//
//        boolean deadEnd = false;
//
//        if (argI1 == -1)
//        {
//            argI1 = argI2 / 2;
//            deadEnd = true;
//        }
//
//        int num = random.nextInt(argI2 / 2) + argI2 / 4;
//        double centerX = chunkX * 16 + 8;
//        double centerZ = chunkZ * 16 + 8;
//        float horiFactor = 0.0F;
//        float vertFactor = 0.0F;
//
//        for (boolean flag = random.nextInt(6) == 0; argI1 < argI2; ++argI1)
//        {
//            double width = 1.5D + MathHelper.sin(argI1 * (float) Math.PI / argI2) * widthFactor * 1.0F; // TODO: Mess with the 1.0F
//            double height = width * heightFactor;
//            float cosVertical = MathHelper.cos(vertical);
//            float sinVertical = MathHelper.sin(vertical);
//            randX += MathHelper.cos(horizontal) * cosVertical;
//            randY += sinVertical;
//            randZ += MathHelper.sin(horizontal) * cosVertical;
//
//            if (flag)
//            {
//                vertical *= 0.92F;
//            }
//            else
//            {
//                vertical *= 0.7F;
//            }
//
//            vertical += vertFactor * 0.1F;
//            horizontal += horiFactor * 0.1F;
//            vertFactor *= 0.9F;
//            horiFactor *= 0.75F;
//            vertFactor += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2;
//            horiFactor += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4;
//
//            if (!deadEnd && argI1 == num && widthFactor > 1 && argI2 > 0)
//            {
//                generate(random.nextLong(), crystalCave, chunkX, chunkZ, data, randX, randY, randZ, random.nextFloat() * 0.5F + 0.5F, horizontal - (float) Math.PI / 2, vertical / 3, argI1, argI2, 1);
//                generate(random.nextLong(), crystalCave, chunkX, chunkZ, data, randX, randY, randZ, random.nextFloat() * 0.5F + 0.5F, horizontal + (float) Math.PI / 2, vertical / 3, argI1, argI2, 1);
//                return;
//            }
//
//            if (deadEnd || random.nextInt(4) != 0)
//            {
//                double centerDistX = randX - centerX;
//                double centerDistZ = randZ - centerZ;
//                double d10 = argI2 - argI1;
//                double d11 = widthFactor + 2.0F + 16.0F;
//
//                if (centerDistX * centerDistX + centerDistZ * centerDistZ - d10 * d10 > d11 * d11)
//                {
//                    return;
//                }
//
//                if (randX >= centerX - 16 - width * 2 && randZ >= centerZ - 16 - width * 2 && randX <= centerX + 16 + width * 2 && randZ <= centerZ + 16 + width * 2)
//                {
//                    int minX = Math.max(MathHelper.floor_double(randX - width) - chunkX * 16 - 1, 0);
//                    int maxX = Math.min(MathHelper.floor_double(randX + width) - chunkX * 16 + 1, 16);
//                    int minY = Math.max(MathHelper.floor_double(randY - height) - 1, 1);
//                    int maxY = Math.min(MathHelper.floor_double(randY + height) + 1, 248);
//                    int minZ = Math.max(MathHelper.floor_double(randZ - width) - chunkZ * 16 - 1, 0);
//                    int maxZ = Math.min(MathHelper.floor_double(randZ + width) - chunkZ * 16 + 1, 16);
//
//                    boolean isOcean = false;
//
//                    for (int xOffset = minX; !isOcean && xOffset < maxX; ++xOffset)
//                    {
//                        for (int zOffset = minZ; !isOcean && zOffset < maxZ; ++zOffset)
//                        {
//                            for (int yOffset = maxY + 1; !isOcean && yOffset >= minY - 1; --yOffset)
//                            {
//                                if (yOffset >= 0 && yOffset < 256)
//                                {
//                                    int index = (xOffset * 16 + zOffset) * 256 + yOffset;
//                                    Block block = data[index];
//
//                                    if (isOceanBlock(data, index, xOffset, yOffset, zOffset, chunkX, chunkZ))
//                                    {
//                                        isOcean = true;
//                                        break;
//                                    }
//
//                                    if (yOffset != minY - 1 && xOffset != minX && xOffset != maxX - 1 && zOffset != minZ && zOffset != maxZ - 1)
//                                    {
//                                        yOffset = minY;
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    if (!isOcean)
//                    {
//                        for (int xOffset = minX; xOffset < maxX; ++xOffset)
//                        {
//                            double d13 = (xOffset + chunkX * 16 + 0.5D - randX) / width;
//
//                            for (int zOffset = minZ; zOffset < maxZ; ++zOffset)
//                            {
//                                double d14 = (zOffset + chunkZ * 16 + 0.5D - randZ) / width;
//
//                                if (d13 * d13 + d14 * d14 < 1.0D) // Making sure we aren't outside of the chunk we're generating
//                                {
//                                    int index = (xOffset * 16 + zOffset) * 256 + maxY;
//                                    boolean foundTop = false;
//
//                                    for (int yOffset = maxY - 1; yOffset >= minY; --yOffset)
//                                    {
//                                        double d12 = (yOffset + 0.5D - randY) / height;
//
//                                        if (d12 > -0.7D && d13 * d13 + d12 * d12 + d14 * d14 < 1.0D)
//                                        {
//                                            Block block = data[index];
//
//                                            if (!foundTop && isTopBlock(data, index, xOffset, yOffset, zOffset, chunkX, chunkZ))
//                                            {
//                                                foundTop = true;
//                                            }
//
//                                            digBlock(data, index, xOffset, yOffset, zOffset, chunkX, chunkZ, foundTop, crystalCave);
//                                        }
//
//                                        --index;
//                                    }
//                                }
//                            }
//                        }
//
//                        if (deadEnd)
//                        {
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void func_151539_a(IChunkProvider provider, World world, int chunkX, int chunkZ, Block[] data)
//    {
//        Random crystalCave = random(world.getSeed(), chunkX, chunkZ);
//        
//        if (crystalCave.nextInt(10) != 0)
//        {
//            caveGenerator.func_151539_a(provider, world, chunkX, chunkZ, data);
//            return;
//        }
//        
//        int r = range;
//        worldObj = world;
//        rand.setSeed(world.getSeed());
//        long l = rand.nextLong();
//        long i1 = rand.nextLong();
//
//        for (int x = chunkX - r; x <= chunkX + r; ++x)
//        {
//            for (int z = chunkZ - r; z <= chunkZ + r; ++z)
//            {
//                long l1 = x * l;
//                long i2 = z * i1;
//                rand.setSeed(l1 ^ i2 ^ world.getSeed());
//                func_151538_a(world, x, z, chunkX, chunkZ, data);
//            }
//        }
//    }
//
//    @Override
//    protected void func_151538_a(World world, int currChunkX, int currChunkZ, int origChunkX, int origChunkZ, Block[] data)
//    {
//        int attempts = rand.nextInt(rand.nextInt(rand.nextInt(15) + 1) + 1);
//
//        if (rand.nextInt(7) == 0) // Whether to *consider* generating a cave for this chunk
//        {
//            Random crystalCave = random(world.getSeed(), currChunkX, currChunkZ);
//            
//            for (int i = 0; i < attempts; ++i)
//            {
//                double randX = currChunkX * 16 + rand.nextInt(16);
//                double randY = rand.nextInt(rand.nextInt(120) + 8);
//                double randZ = currChunkZ * 16 + rand.nextInt(16);
//                int branches = 1;
//
//                if (rand.nextInt(4) == 0) // Generates 1-7 block long narrow cave. Dead end?
//                {
//                    generateDeadEnd(rand.nextLong(), crystalCave, origChunkX, origChunkZ, data, randX, randY, randZ);
//                    branches += rand.nextInt(4);
//                }
//
//                for (int j = 0; j < branches; ++j)
//                {
//                    float horizontal = rand.nextFloat() * (float) Math.PI * 2.0F; // 0 to 2PI
//                    float vertical = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F; // -0.125 to 0.125
//                    float widthFactor = rand.nextFloat() * 2.0F + rand.nextFloat(); // 2x+y, 0 to 3
//
//                    if (rand.nextInt(10) == 0) // When caves lead into big "chambers"
//                    {
//                        widthFactor *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F; // 1+3sq(x), 1 to 12
//                    }
//
//                    generate(rand.nextLong(), crystalCave, origChunkX, origChunkZ, data, randX, randY, randZ, widthFactor, horizontal, vertical, 0, 0, 1.0D);
//                }
//            }
//        }
//    }
//
//    protected boolean isOceanBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ)
//    {
//        return data[index] == Blocks.flowing_water || data[index] == Blocks.water;
//    }
//
//    //Exception biomes to make sure we generate like vanilla
//    private boolean isExceptionBiome(BiomeGenBase biome)
//    {
//        if (biome == BiomeGenBase.mushroomIsland)
//        {
//            return true;
//        }
//        if (biome == BiomeGenBase.beach)
//        {
//            return true;
//        }
//        if (biome == BiomeGenBase.desert)
//        {
//            return true;
//        }
//        return false;
//    }
//
//    //Determine if the block at the specified location is the top block for the biome, we take into account
//    //Vanilla bugs to make sure that we generate the map the same way vanilla does.
//    private boolean isTopBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ)
//    {
//        BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
//        return isExceptionBiome(biome) ? data[index] == Blocks.grass : data[index] == biome.topBlock;
//    }
//
//    /**
//     * Digs out the current block, default implementation removes stone, filler, and top block
//     * Sets the block to lava if y is less then 10, and air other wise.
//     * If setting to air, it also checks to see if we've broken the surface and if so
//     * tries to make the floor the biome's top block
//     *
//     * @param data Block data array
//     * @param index Pre-calculated index into block data
//     * @param x local X position
//     * @param y local Y position
//     * @param z local Z position
//     * @param chunkX Chunk X position
//     * @param chunkZ Chunk Y position
//     * @param foundTop True if we've encountered the biome's top block. Ideally if we've broken the
//     *            surface.
//     */
//    protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, Random crystalCave)
//    {
//        BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
//        Block filler = isExceptionBiome(biome) ? Blocks.dirt : biome.fillerBlock;
//        Block top = isExceptionBiome(biome) ? Blocks.grass : biome.topBlock;
//        Block block = data[index];
//
//        if (block == Blocks.stone || block == filler || block == top)
//        {
////            if (y < 10)
////            {
////                data[index] = Blocks.lava;
////            }
////            //            else if (y < 60)
////            //            {
////            //                data[index] = Blocks.glass;
////            //            }
////            else
//            {
//                data[index] = null;
//
//                if (foundTop && data[index - 1] == filler)
//                {
//                    data[index - 1] = top;
//                }
//
////                if (crystalCave.nextInt(10) == 0)
//                {
//                    data[index] = ModBlocks.lightsaberCrystalGen;
//                }
//            }
//        }
//    }
//}
