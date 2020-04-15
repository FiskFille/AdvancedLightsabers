package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;

import net.minecraft.util.EnumChatFormatting;

public class PowerEffectResist extends PowerEffectInstant
{
    public PowerEffectResist(float duration, int amplifier)
    {
        super(Effect.RESIST, duration, amplifier);
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", PowerDesc.translateFormatted("forcepower.stat.divide", Unit.ENERGY_DAMAGE, getModifierAmount(amplifier)), EnumChatFormatting.GRAY, duration), Target.CASTER)};
    }

    public static float getModifierAmount(int amplifier)
    {
        float f = 0.25F;

        for (int i = 0; i < amplifier; ++i)
        {
            f *= 2;
        }

        return 1 + f;
    }
}
