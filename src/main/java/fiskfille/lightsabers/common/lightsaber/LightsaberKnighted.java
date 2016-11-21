package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberKnighted extends Lightsaber
{
	public Part[] parts =
	{
		new Part(EnumPartType.EMITTER, 12.6F),
		new Part(EnumPartType.SWITCH_SECTION, 8.4F),
		new Part(EnumPartType.BODY, 20),
		new Part(EnumPartType.POMMEL, 13.3F)
	};
	
	@Override
	public String getName()
	{
		return "Knighted";
	}
	
	@Override
	public int getColor()
	{
		return LightsaberColors.RED;
	}
	
	@Override
	public String[] getFocusingCrystals()
	{
		return new String[] {FocusingCrystals.CRACKED_KYBER_CRYSTAL};
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
