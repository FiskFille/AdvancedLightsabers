package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberMechanical extends Lightsaber
{
    public Part[] parts = {new Part(EnumPartType.EMITTER, 16), new Part(EnumPartType.SWITCH_SECTION, 8.8F), new Part(EnumPartType.BODY, 16), new Part(EnumPartType.POMMEL, 2.5F)};

    @Override
    public String getName()
    {
        return "Mechanical";
    }

    @Override
    public int getColor()
    {
        return LightsaberColors.RED;
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
