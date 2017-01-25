package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberMauler extends Lightsaber
{
    public Part[] parts = {new Part(EnumPartType.EMITTER, 18), new Part(EnumPartType.SWITCH_SECTION, 12), new Part(EnumPartType.BODY, 21.6F), new Part(EnumPartType.POMMEL, 0.25F)};

    @Override
    public String getName()
    {
        return "Mauler";
    }

    @Override
    public int getColor()
    {
        return LightsaberColors.RED;
    }

    @Override
    public EnumLightsaberType getType()
    {
        return EnumLightsaberType.DOUBLE;
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
