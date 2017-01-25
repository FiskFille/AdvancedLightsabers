package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberRedeemer extends Lightsaber
{
    public Part[] parts = {new Part(EnumPartType.EMITTER, 30), new Part(EnumPartType.SWITCH_SECTION, 8), new Part(EnumPartType.BODY, 12.3F), new Part(EnumPartType.POMMEL, 1)};

    @Override
    public String getName()
    {
        return "Redeemer";
    }

    @Override
    public int getColor()
    {
        return LightsaberColors.DEEP_BLUE;
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
