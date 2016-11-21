package fiskfille.lightsabers.common.lightsaber;

import fiskfille.lightsabers.common.helper.LightsaberColors;

public class LightsaberVaid extends Lightsaber
{
	public Part[] parts =
	{
		new Part(EnumPartType.EMITTER, 18.5F),
		new Part(EnumPartType.SWITCH_SECTION, 9.2F),
		new Part(EnumPartType.BODY, 22.37F),
		new Part(EnumPartType.POMMEL, 6.6F)
	};
	
	private String type;
	
	public LightsaberVaid(String type)
	{
		this.type = type;
	}
	
	@Override
	public String getName()
	{
		return "Vaid (" + type + ")";
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
