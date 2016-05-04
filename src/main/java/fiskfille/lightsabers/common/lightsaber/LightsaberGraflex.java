package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberGraflex extends Lightsaber
{
	public Part[] parts =
	{
		new Part(EnumPartType.EMITTER, 15),
		new Part(EnumPartType.SWITCH_SECTION, 8.8F),
		new Part(EnumPartType.BODY, 16),
		new Part(EnumPartType.POMMEL, 1)
	};
	
	@Override
	public String getName()
	{
		return "Graflex";
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
