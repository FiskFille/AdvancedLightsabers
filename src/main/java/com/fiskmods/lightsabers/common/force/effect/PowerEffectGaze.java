package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;

import net.minecraft.util.EnumChatFormatting;

public class PowerEffectGaze extends PowerEffectInstant
{
    public PowerEffectGaze(float duration, int amplifier)
    {
        super(Effect.GAZE, duration, amplifier);
    }

    @Override
    public String[] getDesc()
    {
        Object[][] args = {{Target.MOBS}, {Target.MOBS, Target.PLAYERS}, {Target.MOBS, Target.PLAYERS, Target.INVISIBLE}};

        return new String[] {PowerDesc.create("highlight", PowerDesc.list(args[amplifier]), PowerDesc.format("%s%s", EnumChatFormatting.GRAY, duration))};
    }
}
