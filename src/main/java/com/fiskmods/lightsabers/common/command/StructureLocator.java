package com.fiskmods.lightsabers.common.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fiskmods.lightsabers.common.generator.WorldGeneratorStructures;
import com.fiskmods.lightsabers.common.generator.structure.EnumStructure;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class StructureLocator implements Runnable
{
    public static WeakHashMap<World, Map<EnumStructure, List<ChunkCoordIntPair>>> cachedStructurePos = new WeakHashMap();
    public static WeakHashMap<World, List<ChunkCoordIntPair>> chunksSearched = new WeakHashMap();

    private final CommandBase command;
    private final ICommandSender sender;
    private final EnumStructure structure;
    private final boolean targetAll;
    private final int maxRange;
    private final int startX;
    private final int startZ;

    public StructureLocator(CommandBase commandBase, ICommandSender commandSender, EnumStructure enumStructure, boolean all, int range, int x, int z)
    {
        command = commandBase;
        sender = commandSender;
        structure = enumStructure;
        targetAll = all;
        maxRange = range;
        startX = x;
        startZ = z;
    }

    @Override
    public void run()
    {
        World world = sender.getEntityWorld();

        List<ChunkCoordIntPair> searched = getChunksSearched(world);
        boolean flag = structure == null || getStructures(world, structure).isEmpty();
        int range = 0;

        searched.clear();

        while (++range < maxRange && flag)
        {
            for (int i = -range; i <= range; ++i)
            {
                for (int j = -range; j <= range; ++j)
                {
                    int chunkX = (startX >> 4) + i;
                    int chunkZ = (startZ >> 4) + j;
                    ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX, chunkZ);

                    if (searched.contains(chunk))
                    {
                        continue;
                    }

                    for (EnumStructure structure1 : EnumStructure.values())
                    {
                        if (WorldGeneratorStructures.canSpawnStructureAtCoords(world, chunk.getCenterXPos(), chunk.getCenterZPosition(), structure1))
                        {
                            getStructures(world, structure1).add(chunk);

                            if (structure == structure1 || targetAll)
                            {
                                flag = false;
                            }
                        }
                    }

                    searched.add(chunk);
                }
            }
        }

        final Vec3 vec = Vec3.createVectorHelper(startX >> 4, 0, startZ >> 4);
        Comparator<ChunkCoordIntPair> coordComparator = new Comparator<ChunkCoordIntPair>()
        {
            @Override
            public int compare(ChunkCoordIntPair arg0, ChunkCoordIntPair arg1)
            {
                return Double.valueOf(Vec3.createVectorHelper(arg0.chunkXPos, 0, arg0.chunkZPos).distanceTo(vec)).compareTo(Vec3.createVectorHelper(arg1.chunkXPos, 0, arg1.chunkZPos).distanceTo(vec));
            }
        };

        for (EnumStructure structure1 : EnumStructure.values())
        {
            Collections.sort(getStructures(world, structure1), coordComparator);
        }

        if (targetAll)
        {
            List<ChunkCoordIntPair> structures = Lists.newArrayList();

            for (EnumStructure structure1 : EnumStructure.values())
            {
                if (!getStructures(world, structure1).isEmpty())
                {
                    structures.add(getStructures(world, structure1).get(0));
                }
            }

            if (structures.isEmpty())
            {
                ChatComponentTranslation message = new ChatComponentTranslation("commands.force.structure.locate.failure");
                message.getChatStyle().setColor(EnumChatFormatting.RED);

                sender.addChatMessage(message);
            }
            else
            {
                Collections.sort(structures, coordComparator);

                for (EnumStructure structure1 : EnumStructure.values())
                {
                    if (!getStructures(world, structure1).isEmpty() && structures.get(0).equals(getStructures(world, structure1).get(0)))
                    {
                        ChunkCoordIntPair chunk = structures.get(0);
                        CommandBase.func_152373_a(sender, command, "commands.force.structure.locate.success", ALHelper.getUnconventionalName(structure1.name()), chunk.getCenterXPos(), chunk.getCenterZPosition());
                        break;
                    }
                }
            }
        }
        else
        {
            List<ChunkCoordIntPair> structures = getStructures(world, structure);

            if (structures.isEmpty())
            {
                ChatComponentTranslation message = new ChatComponentTranslation("commands.force.structure.locate.failure");
                message.getChatStyle().setColor(EnumChatFormatting.RED);

                sender.addChatMessage(message);
            }
            else
            {
                ChunkCoordIntPair chunk = structures.get(0);
                CommandBase.func_152373_a(sender, command, "commands.force.structure.locate.success", ALHelper.getUnconventionalName(structure.name()), chunk.getCenterXPos(), chunk.getCenterZPosition());
            }
        }
    }

    public static List<ChunkCoordIntPair> getChunksSearched(World world)
    {
        if (chunksSearched.get(world) == null)
        {
            chunksSearched.put(world, new ArrayList<ChunkCoordIntPair>());
        }

        return chunksSearched.get(world);
    }

    public static Map<EnumStructure, List<ChunkCoordIntPair>> getStructures(World world)
    {
        if (cachedStructurePos.get(world) == null)
        {
            cachedStructurePos.put(world, new HashMap<EnumStructure, List<ChunkCoordIntPair>>());
        }

        return cachedStructurePos.get(world);
    }

    public static List<ChunkCoordIntPair> getStructures(World world, EnumStructure structure)
    {
        Map<EnumStructure, List<ChunkCoordIntPair>> map = getStructures(world);

        if (map.get(structure) == null)
        {
            map.put(structure, new ArrayList<ChunkCoordIntPair>());
        }

        return map.get(structure);
    }
}
