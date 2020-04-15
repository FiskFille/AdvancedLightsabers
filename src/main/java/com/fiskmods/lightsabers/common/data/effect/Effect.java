package com.fiskmods.lightsabers.common.data.effect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.force.Power;
import com.google.common.collect.Lists;

import fiskfille.utils.registry.FiskRegistryEntry;
import fiskfille.utils.registry.FiskRegistryNamespaced;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

public class Effect extends FiskRegistryEntry<Effect>
{
    public static final FiskRegistryNamespaced<Effect> REGISTRY = new FiskRegistryNamespaced(Lightsabers.MODID, null);

    public static void register(int id, String key, Effect value)
    {
        REGISTRY.addObject(id, key, value);
    }

    public static void register(String key, Effect value)
    {
        REGISTRY.putObject(key, value);
    }

    public static Effect getEffectFromName(String key)
    {
        return REGISTRY.getObject(key);
    }

    public static String getNameForEffect(Effect value)
    {
        return REGISTRY.getNameForObject(value);
    }

    public static int getIdFromEffect(Effect value)
    {
        return value == null ? 0 : REGISTRY.getIDForObject(value);
    }

    public static Effect getEffectById(int id)
    {
        return REGISTRY.getObjectById(id);
    }

    public static final Effect FORTIFY = new Effect(false);
    public static final Effect STUN = new Effect(false);

    public static final Effect DRAIN = new Effect(true);
    public static final Effect LIGHTNING = new Effect(false);
    public static final Effect CHOKE = new Effect(true);

    public static final Effect STEALTH = new Effect(false);
    public static final Effect SPEED = new Effect(false);
    public static final Effect GAZE = new Effect(false);
    public static final Effect MEDITATION = new Effect(false);
    public static final Effect RESIST = new Effect(false);

    private final boolean negativeEffect;
    public List<Power> powers = Lists.newArrayList();

    public Effect(boolean negative)
    {
        negativeEffect = negative;
    }

    public boolean isNegative()
    {
        return negativeEffect;
    }

    public String getUnlocalizedName()
    {
        return "statusEffect." + delegate.name().replace(':', '.');
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    public String getFormattedString(StatusEffect effect)
    {
        String s = getLocalizedName();

        if (effect.amplifier > 0 && effect.amplifier < 10)
        {
            s += " " + StatCollector.translateToLocal("enchantment.level." + (effect.amplifier + 1));
        }

        return s;
    }

    public String getDurationString(StatusEffect effect)
    {
        if (effect.isMaxDuration())
        {
            return "**:**";
        }

        return StringUtils.ticksToElapsedTime(effect.duration);
    }

    public Power getPower(int i)
    {
        return !powers.isEmpty() ? powers.get(MathHelper.clamp_int(i, 0, powers.size() - 1)) : null;
    }

    public static void register()
    {
        for (Field field : Effect.class.getFields())
        {
            if (Effect.class.isAssignableFrom(field.getType()))
            {
                try
                {
                    register(field.getName().toLowerCase(Locale.ROOT), (Effect) field.get(null));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Effect> getEffectsForPower(Power power)
    {
        List<Effect> list = Lists.newArrayList();

        for (Effect effect : REGISTRY)
        {
            if (effect.powers.contains(power))
            {
                list.add(effect);
            }
        }

        return list;
    }
}
