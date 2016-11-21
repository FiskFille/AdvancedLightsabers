package fiskfille.lightsabers.common.lightsaber;

public abstract class Lightsaber
{
	public abstract String getName();
	
	public abstract Part getEmitter();
	
	public abstract Part getSwitchSection();
	
	public abstract Part getBody();
	
	public abstract Part getPommel();
	
	public abstract int getColor();
	
	public EnumLightsaberType getType()
	{
		return EnumLightsaberType.SINGLE;
	}
	
	public String[] getFocusingCrystals()
	{
		return new String[] {};
	}
	
	public static class Part
	{
		public EnumPartType type;
		public float height;
		public float[] glInstructions;
		
		public Part(EnumPartType type, float height, float... instructions)
		{
			this.type = type;
			this.height = height;
			this.glInstructions = instructions;
		}
	}
	
	public static enum EnumPartType
	{
		EMITTER,
		SWITCH_SECTION,
		BODY,
		POMMEL;
	}
	
	public static enum EnumLightsaberType
	{
		SINGLE,
		DOUBLE;
	}
}
