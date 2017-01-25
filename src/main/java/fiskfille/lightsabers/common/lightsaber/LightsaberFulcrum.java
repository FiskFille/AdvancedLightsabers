package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberFulcrum extends Lightsaber
{
    public Part[] parts = {new Part(EnumPartType.EMITTER, 19), new Part(EnumPartType.SWITCH_SECTION, 10), new Part(EnumPartType.BODY, 30, 11.7F, 2, 9.8F, 3, 9), new Part(EnumPartType.POMMEL, 7)};

    @Override
    public String getName()
    {
        return "Fulcrum";
    }

    @Override
    public int getColor()
    {
        return LightsaberColors.WHITE;
    }

    @Override
    public String[] getFocusingCrystals()
    {
        return new String[] {FocusingCrystals.COMPRESSED_FOCUSING_CRYSTAL};
    }

    @Override
    public Part getEmitter()
    {
        return parts[0];
    }

    @Override
    public Part getSwitchSection()
    {
        return parts[1];
    }

    @Override
    public Part getBody()
    {
        return parts[2];
    }

    @Override
    public Part getPommel()
    {
        return parts[3];
    }
}
