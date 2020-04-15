package com.fiskmods.lightsabers.common.command;

import java.util.List;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerData;
import com.fiskmods.lightsabers.common.force.PowerManager;
import com.fiskmods.lightsabers.common.generator.WorldGeneratorStructures;
import com.fiskmods.lightsabers.common.generator.structure.EnumStructure;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;

public class CommandForce extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "force";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.force.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        World world = sender.getEntityWorld();
        String usage = getCommandUsage(sender);

        if (args.length > 0)
        {
            String func = args[0];
            String arg = "";

            if (args.length > 1)
            {
                arg = args[1];
            }

            if (func.equals("xp") || func.equals("base"))
            {
                if (func.equals("base") && arg.equals("reset") && (args.length == 2 || args.length == 3))
                {
                    EntityPlayerMP player;

                    if (args.length == 3)
                    {
                        player = getPlayer(sender, args[2]);
                    }
                    else
                    {
                        player = getCommandSenderAsPlayer(sender);
                    }

                    if (ALData.BASE_POWER.set(player, ALHelper.getBasePower(player)))
                    {
                        func_152373_a(sender, this, String.format("commands.force.%s.%s.success", func, arg), player.getCommandSenderName());
                        return;
                    }
                }

                if (args.length == 3 || args.length == 4)
                {
                    EntityPlayerMP player;

                    if (args.length == 4)
                    {
                        player = getPlayer(sender, args[3]);
                    }
                    else
                    {
                        player = getCommandSenderAsPlayer(sender);
                    }

                    float amount = parseIntWithMin(sender, args[2], 0);
                    float f = func.equals("xp") ? ALData.FORCE_XP.get(player) : 0;

                    if (arg.equals("give"))
                    {
                        f += amount;
                    }
                    else if (arg.equals("take"))
                    {
                        f -= amount;
                    }
                    else if (arg.equals("set"))
                    {
                        f = amount;
                    }

                    f = Math.max(f, 0);

                    List translationKeys = arg.equals("set") ? Lists.newArrayList(player.getCommandSenderName(), MathHelper.floor_float(amount)) : Lists.newArrayList(MathHelper.floor_float(amount), player.getCommandSenderName());

                    if (func.equals("xp"))
                    {
                        ALData.FORCE_XP.set(player, f);
                    }
                    else
                    {
                        ALData.BASE_POWER.set(player, (byte) MathHelper.floor_float(f));
                    }

                    func_152373_a(sender, this, String.format("commands.force.%s.%s.success", func, arg), translationKeys.toArray());
                    return;
                }

                throw new WrongUsageException(String.format("commands.force.%s.usage", func));
            }
            else if (func.equals("power"))
            {
                if (args.length == 3 || args.length == 4)
                {
                    EntityPlayerMP player;

                    if (args.length == 4)
                    {
                        player = getPlayer(sender, args[3]);
                    }
                    else
                    {
                        player = getCommandSenderAsPlayer(sender);
                    }

                    Power power = Power.getPowerFromName(args[2]);
                    boolean targetAll = args[2].equals("*");

                    if (power == null && !targetAll)
                    {
                        throw new CommandException("commands.force.power.unknown", args[2]);
                    }

                    if (arg.equals("give"))
                    {
                        if (targetAll)
                        {
                            for (PowerData data : ALData.POWERS.get(player))
                            {
                                if (!data.isUnlocked())
                                {
                                    PowerManager.unlockPower(player, data.power);
                                }
                            }

                            func_152373_a(sender, this, "commands.force.power.give.success.all", player.getCommandSenderName());
                        }
                        else
                        {
                            if (!PowerManager.unlockPower(player, power))
                            {
                                throw new CommandException("commands.force.power.alreadyHave", player.getCommandSenderName(), args[2]);
                            }
                            else
                            {
                                func_152373_a(sender, this, "commands.force.power.give.success.one", player.getCommandSenderName(), power.getChatFormattedName());
                            }
                        }

                        ALData.POWERS.sync(player);
                        ALData.BASE_POWER.sync(player);
                        return;
                    }
                    else if (arg.equals("take"))
                    {
                        if (targetAll)
                        {
                            for (PowerData data : ALData.POWERS.get(player))
                            {
                                if (data.isUnlocked())
                                {
                                    PowerManager.removePower(player, data.power);
                                }
                            }

                            func_152373_a(sender, this, "commands.force.power.take.success.all", player.getCommandSenderName());
                        }
                        else
                        {
                            if (!PowerManager.removePower(player, power))
                            {
                                throw new CommandException("commands.force.power.dontHave", player.getCommandSenderName(), args[2]);
                            }
                            else
                            {
                                func_152373_a(sender, this, "commands.force.power.take.success.one", power.getChatFormattedName(), player.getCommandSenderName());
                            }
                        }

                        ALData.POWERS.sync(player);
                        ALData.BASE_POWER.sync(player);
                        return;
                    }
                }

                throw new WrongUsageException("commands.force.power.usage");
            }
            else if (func.equals("structure"))
            {
                if (sender.canCommandSenderUseCommand(3, getCommandName()))
                {
                    if (arg.equals("locate") ? (args.length == 4 || args.length == 6) : (args.length == 3 || args.length == 5))
                    {
                        EnumStructure structure = null;

                        for (EnumStructure structure1 : EnumStructure.values())
                        {
                            if (ALHelper.getUnconventionalName(structure1.name()).equals(args[2]))
                            {
                                structure = structure1;
                                break;
                            }
                        }

                        boolean targetAll = arg.equals("locate") && args[2].equals("*");
                        int x = sender.getPlayerCoordinates().posX;
                        int z = sender.getPlayerCoordinates().posZ;

                        if (arg.equals("locate") ? args.length == 6 : args.length == 5)
                        {
                            x = MathHelper.floor_double(func_110666_a(sender, x, arg.equals("locate") ? args[4] : args[3]));
                            z = MathHelper.floor_double(func_110666_a(sender, z, arg.equals("locate") ? args[5] : args[4]));
                        }

                        if (structure == null && !targetAll)
                        {
                            throw new CommandException("commands.force.structure.unknown", args[2]);
                        }

                        if (arg.equals("locate"))
                        {
                            WorldInfo info = world.getWorldInfo();

                            if (info.getTerrainType() == WorldType.FLAT || !info.isMapFeaturesEnabled() || world.provider.dimensionId != 0)
                            {
                                throw new CommandException("commands.force.structure.locate.doesNotGenerate", args[2]);
                            }

                            new Thread(new StructureLocator(this, sender, structure, targetAll, parseIntWithMin(sender, args[3], 0), x, z)).start();
                            return;
                        }
                        else if (arg.equals("generate"))
                        {
                            if (!world.blockExists(x, 64, z))
                            {
                                throw new CommandException("commands.force.structure.generate.outOfWorld", arg);
                            }

                            try
                            {
                                WorldGeneratorStructures.generateStructure(world, x, z, structure);
                                func_152373_a(sender, this, "commands.force.structure.generate.success", ALHelper.getUnconventionalName(structure.name()), x, z);

                                return;
                            }
                            catch (Exception e)
                            {
                                throw new CommandException("commands.force.structure.generate.failure");
                            }
                        }
                    }

                    if (arg.equals("locate") || arg.equals("generate"))
                    {
                        throw new WrongUsageException(String.format("commands.force.structure.%s.usage", arg));
                    }

                    throw new WrongUsageException("commands.force.structure.usage");
                }
                else
                {
                    throw new CommandException("commands.generic.permission");
                }
            }
        }

        throw new WrongUsageException(usage);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        World world = sender.getEntityWorld();

        if (args.length > 0)
        {
            if (args.length == 1)
            {
                return getListOfStringsMatchingLastWord(args, "xp", "base", "power", "structure");
            }
            else
            {
                if (args[0].equals("xp"))
                {
                    if (args.length == 2)
                    {
                        return getListOfStringsMatchingLastWord(args, "give", "take", "set");
                    }
                    else if (args.length == 4)
                    {
                        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
                    }
                }
                else if (args[0].equals("base"))
                {
                    if (args.length == 2)
                    {
                        return getListOfStringsMatchingLastWord(args, "give", "take", "set", "reset");
                    }
                    else if (args.length == 4 || args.length == 3 && args[1].equals("reset"))
                    {
                        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
                    }
                }
                else if (args[0].equals("power"))
                {
                    if (args.length == 2)
                    {
                        return getListOfStringsMatchingLastWord(args, "give", "take");
                    }
                    else if (args.length == 3)
                    {
                        List<String> list = Lists.newArrayList();

                        for (Power power : Power.POWERS)
                        {
                            list.add(power.getName());
                        }

                        return getListOfStringsMatchingLastWord(args, list.toArray(new String[list.size()]));
                    }
                    else if (args.length == 4)
                    {
                        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
                    }
                }
                else if (args[0].equals("structure"))
                {
                    if (args.length == 2)
                    {
                        return getListOfStringsMatchingLastWord(args, "locate", "generate");
                    }
                    else if (args.length == 3)
                    {
                        List<String> list = Lists.newArrayList();

                        for (EnumStructure structure : EnumStructure.values())
                        {
                            list.add(ALHelper.getUnconventionalName(structure.name()));
                        }

                        return getListOfStringsMatchingLastWord(args, list.toArray(new String[list.size()]));
                    }
                }
            }
        }

        return null;
    }
}
