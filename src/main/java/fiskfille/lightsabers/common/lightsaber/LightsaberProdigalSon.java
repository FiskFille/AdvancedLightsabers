package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberProdigalSon extends Lightsaber
{
	public Part[] parts =
	{
		new Part(EnumPartType.EMITTER, 27),
		new Part(EnumPartType.SWITCH_SECTION, 8.4F),
		new Part(EnumPartType.BODY, 13.3F),
		new Part(EnumPartType.POMMEL, 2)
	};
	
	@Override
	public String getName()
	{
		return "Prodigal Son";
	}
	
	@Override
	public int getColor()
	{
		return LightsaberColors.GREEN;
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
