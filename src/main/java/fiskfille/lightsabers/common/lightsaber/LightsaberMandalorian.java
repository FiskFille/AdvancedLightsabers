package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberMandalorian extends Lightsaber
{
    public Part[] parts = {new Part(EnumPartType.EMITTER, 12.55F), new Part(EnumPartType.SWITCH_SECTION, 2.87F), new Part(EnumPartType.BODY, 26), new Part(EnumPartType.POMMEL, 7.45F)};

    @Override
    public String getName()
    {
        return "Mandalorian";
    }

    @Override
    public int getColor()
    {
        return LightsaberColors.WHITE;
    }

    @Override
    public String[] getFocusingCrystals()
    {
        return new String[] {FocusingCrystals.INVERTING_FOCUSING_CRYSTAL, FocusingCrystals.FINE_CUT_FOCUSING_CRYSTAL};
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
