package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberDroideka extends Lightsaber
{
	public Part[] parts =
	{
		new Part(EnumPartType.EMITTER, 5),
		new Part(EnumPartType.SWITCH_SECTION, 9.6F),
		new Part(EnumPartType.BODY, 29.4F),
		new Part(EnumPartType.POMMEL, 6.5F)
	};
	
	@Override
	public String getName()
	{
		return "Droideka";
	}
	
	@Override
	public int getColor()
	{
		return LightsaberColors.AMBER;
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
