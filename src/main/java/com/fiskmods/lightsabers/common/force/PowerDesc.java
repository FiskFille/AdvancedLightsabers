package com.fiskmods.lightsabers.common.force;

import java.util.Locale;

import com.fiskmods.lightsabers.common.data.effect.Effect;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class PowerDesc
{
    public static String create(String action, Object arg1, Object arg2)
    {
        String gray = EnumChatFormatting.GRAY.toString();
        String yellow = EnumChatFormatting.YELLOW.toString();
        return gray + I18n.format("forcepower.stat." + action, yellow + formatObj(arg1) + gray, yellow + formatObj(arg2) + gray);
    }

    public static String translateFormatted(String key, Object... args)
    {
        Object[] args1 = new Object[args.length];

        for (int i = 0; i < args.length; ++i)
        {
            args1[i] = formatObj(args[i]);
        }

        return StatCollector.translateToLocalFormatted(key, args1);
    }

    public static String format(String key, Object... args)
    {
        Object[] args1 = new Object[args.length];

        for (int i = 0; i < args.length; ++i)
        {
            args1[i] = formatObj(args[i]);
        }

        return String.format(key, args1);
    }

    public static String formatObj(Object obj)
    {
        if (obj instanceof Float || obj instanceof Double)
        {
            return String.valueOf(ItemStack.field_111284_a.format(obj));
        }
        else if (obj instanceof Effect)
        {
            return ((Effect) obj).getLocalizedName();
        }
        else if (obj instanceof Target)
        {
            return ((Target) obj).getLocalizedName();
        }
        else if (obj instanceof Unit)
        {
            return ((Unit) obj).getLocalizedName();
        }

        return String.valueOf(obj);
    }

    public static String list(Object... args)
    {
        String s = "";

        for (int i = 0; i < args.length; ++i)
        {
            if (i > 0)
            {
                if (i == args.length - 1)
                {
                    s += " " + StatCollector.translateToLocal("forcepower.list") + " ";
                }
                else if (i > 0)
                {
                    s += ", ";
                }
            }

            s += formatObj(args[i]);
        }

        return s;
    }

    public static enum Unit
    {
        HEALTH,
        DAMAGE,
        ATTACK_DAMAGE,
        FORCE_DAMAGE,
        ENERGY_DAMAGE,
        ABSORPTION,
        SPEED,
        INVISIBILITY,
        IMPACT_RADIUS,
        KNOCKBACK,
        FALL_RESISTANCE;

        public String getUnlocalizedName()
        {
            return "forcepower.stat.unit." + name().toLowerCase(Locale.ROOT);
        }
        
        public String getLocalizedName()
        {
            return StatCollector.translateToLocalFormatted(getUnlocalizedName());
        }
    }

    public static enum Target
    {
        CASTER,
        TARGET,
        ALLIES,
        ENEMIES,
        MOBS,
        PLAYERS,
        INVISIBLE;

        public String getUnlocalizedName()
        {
            return "forcepower.stat.target." + name().toLowerCase(Locale.ROOT);
        }
        
        public String getLocalizedName()
        {
            return StatCollector.translateToLocalFormatted(getUnlocalizedName());
        }
    }
}
