package fiskfille.lightsabers.common.power.effect;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectSpeed extends PowerEffectInstant
{
	public PowerEffectSpeed()
	{
		super(Effect.speed);
	}
	
	@Override
	public String[] getDesc(Object... args)
	{
		return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", PowerDesc.translateFormatted("forcepower.stat.multiply", PowerDesc.SPEED, 2), EnumChatFormatting.GRAY, args[0]), PowerDesc.CASTER)};
	}
}
