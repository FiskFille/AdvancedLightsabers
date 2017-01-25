package fiskfille.lightsabers.common.power.effect;

import net.minecraft.util.EnumChatFormatting;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectGaze extends PowerEffectInstant
{
    public PowerEffectGaze()
    {
        super(Effect.gaze);
    }

    @Override
    public String[] getDesc(Object... args)
    {
        Object[][] args1 = { {PowerDesc.MOBS}, {PowerDesc.MOBS, PowerDesc.PLAYERS}, {PowerDesc.MOBS, PowerDesc.PLAYERS, PowerDesc.INVISIBLE}};

        return new String[] {PowerDesc.create("highlight", PowerDesc.list(args1[(Integer) args[1]]), PowerDesc.format("%s%s", EnumChatFormatting.GRAY, args[0]))};
    }
}
