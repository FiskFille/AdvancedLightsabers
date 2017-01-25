package fiskfille.lightsabers.common.data;

import java.util.List;

import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.common.power.Power;

public class Effect
{
    public static List<Effect> effects = Lists.newArrayList();

    public static final Effect fortify = new Effect(0, "fortify");
    public static final Effect stun = new Effect(1, "stun");

    public static final Effect drain = new Effect(2, "drain");
    public static final Effect lightning = new Effect(3, "lightning");
    public static final Effect choke = new Effect(4, "choke");

    public static final Effect stealth = new Effect(5, "stealth");
    public static final Effect speed = new Effect(6, "speed");
    public static final Effect gaze = new Effect(7, "gaze");
    public static final Effect meditation = new Effect(8, "meditation");
    public static final Effect resist = new Effect(9, "resist");

    public int id;
    public String name;
    public List<Power> powers = Lists.newArrayList();

    public Effect(int id, String name, Power... powers)
    {
        this.id = id;
        this.name = name;
        effects.add(this);
    }

    public Power getPower(int i)
    {
        return !powers.isEmpty() ? powers.get(MathHelper.clamp_int(i, 0, powers.size() - 1)) : null;
    }

    public String getTranslatedName()
    {
        return StatCollector.translateToLocal("statusEffect." + name);
    }

    public static Effect getEffect(int id)
    {
        for (Effect effect : effects)
        {
            if (effect.id == id)
            {
                return effect;
            }
        }

        return null;
    }

    public static List<Effect> getEffectsForPower(Power power)
    {
        List<Effect> list = Lists.newArrayList();

        for (Effect effect : effects)
        {
            for (Power power1 : effect.powers)
            {
                if (power == power1)
                {
                    list.add(effect);
                }
            }
        }

        return list;
    }
}
