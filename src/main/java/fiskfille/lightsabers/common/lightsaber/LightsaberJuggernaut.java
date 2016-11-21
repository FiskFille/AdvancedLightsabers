package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberJuggernaut extends Lightsaber
{
	public Part[] parts =
	{
		new Part(EnumPartType.EMITTER, 14.7F),
		new Part(EnumPartType.SWITCH_SECTION, 12.4F),
		new Part(EnumPartType.BODY, 16),
		new Part(EnumPartType.POMMEL, 7)
	};
	
	@Override
	public String getName()
	{
		return "Juggernaut";
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
