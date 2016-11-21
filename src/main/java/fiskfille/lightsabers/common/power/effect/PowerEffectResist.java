package fiskfille.lightsabers.common.power.effect;

import net.minecraft.util.EnumChatFormatting;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectResist extends PowerEffectInstant
{
	public PowerEffectResist()
	{
		super(Effect.resist);
	}
	
	@Override
	public String[] getDesc(Object... args)
	{
		return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", PowerDesc.translateFormatted("forcepower.stat.divide", PowerDesc.ENERGY_DAMAGE, getModifierAmount((Integer)args[1])), EnumChatFormatting.GRAY, args[0]), PowerDesc.CASTER)};
	}
	
	public static float getModifierAmount(int amplifier)
	{
		float f = 0.25F;
		
		for (int i = 0; i < amplifier; ++i)
		{
			f *= 2;
		}
		
		return 1 + f;
	}
}
