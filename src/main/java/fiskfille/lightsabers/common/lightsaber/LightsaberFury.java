package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberFury extends Lightsaber
{
	public Part[] parts =
	{
		new Part(EnumPartType.EMITTER, 19),
		new Part(EnumPartType.SWITCH_SECTION, 5.6F),
		new Part(EnumPartType.BODY, 16),
		new Part(EnumPartType.POMMEL, 8.3F)
	};
	
	@Override
	public String getName()
	{
		return "Fury";
	}
	
	@Override
	public int getColor()
	{
		return LightsaberColors.PURPLE;
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
