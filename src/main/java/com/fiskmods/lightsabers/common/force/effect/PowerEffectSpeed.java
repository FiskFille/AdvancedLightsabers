package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;

import net.minecraft.util.EnumChatFormatting;

public class PowerEffectSpeed extends PowerEffectInstant
{
    public PowerEffectSpeed(float duration, int amplifier)
    {
        super(Effect.SPEED, duration, amplifier);
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", PowerDesc.translateFormatted("forcepower.stat.multiply", Unit.SPEED, 2), EnumChatFormatting.GRAY, duration), Target.CASTER)};
    }
}
