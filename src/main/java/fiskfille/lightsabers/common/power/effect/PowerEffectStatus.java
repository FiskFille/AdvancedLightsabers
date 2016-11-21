package fiskfille.lightsabers.common.power.effect;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;

public class PowerEffectStatus extends PowerEffectActive
{
	public final Effect effect;
	
	public PowerEffectStatus(Effect effect)
	{
		this.effect = effect;
	}
	
	@Override
	public boolean execute(EntityPlayer player, Side side, Object... args)
	{
		int amplifier = args.length > 0 ? (Integer)args[0] : 0;
		float force = ALData.getFloat(player, ALData.FORCE_POWER);
		
		if (effect.getPower(amplifier) != null)
		{
			DataManager.addEffect(player, effect.id, (int)(force / (effect.getPower(amplifier).getUseCost(player) / 20)), amplifier);
		}
		
		return true;
	}
}
