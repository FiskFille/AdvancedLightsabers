package fiskfille.lightsabers.common.power;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.helper.ALHelper;

public enum PowerDesc
{
    HEALTH("unit"),
    DAMAGE("unit"),
    ATTACK_DAMAGE("unit"),
    FORCE_DAMAGE("unit"),
    ENERGY_DAMAGE("unit"),
    ABSORPTION("unit"),
    SPEED("unit"),
    INVISIBILITY("unit"),
    IMPACT_RADIUS("unit"),
    KNOCKBACK("unit"),

    CASTER("target"),
    TARGET("target"),
    ALLIES("target"),
    ENEMIES("target"),
    MOBS("target"),
    PLAYERS("target"),
    INVISIBLE("target");

    private String type;

    private PowerDesc(String s)
    {
        type = s;
    }

    @Override
    public String toString()
    {
        return StatCollector.translateToLocal("forcepower.stat." + (type != null ? type + "." : "") + ALHelper.getConventionalName(name()));
    }

    public static String create(String action, Object arg1, Object arg2)
    {
        String s = EnumChatFormatting.GRAY.toString();
        String s1 = EnumChatFormatting.YELLOW.toString();
        return s + StatCollector.translateToLocalFormatted("forcepower.stat." + action, s1 + format(arg1) + s, s1 + format(arg2) + s);
    }

    public static String translateFormatted(String key, Object... args)
    {
        Object[] args1 = new Object[args.length];

        for (int i = 0; i < args.length; ++i)
        {
            args1[i] = format(args[i]);
        }

        return StatCollector.translateToLocalFormatted(key, args1);
    }

    public static String format(String key, Object... args)
    {
        Object[] args1 = new Object[args.length];

        for (int i = 0; i < args.length; ++i)
        {
            args1[i] = format(args[i]);
        }

        return String.format(key, args1);
    }

    public static Object format(Object obj)
    {
        if (obj instanceof Float || obj instanceof Double)
        {
            return ItemStack.field_111284_a.format(obj);
        }
        else if (obj instanceof Effect)
        {
            return ((Effect) obj).getTranslatedName();
        }

        return obj;
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

            s += format(args[i]);
        }

        return s;
    }
}
