package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public class PowerEffectStatus extends PowerEffectActive
{
    public final Effect effect;

    public PowerEffectStatus(Effect effect, int amplifier)
    {
        super(amplifier);
        this.effect = effect;
    }

    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        float force = ALData.FORCE_POWER.get(player);

        if (effect.getPower(amplifier) != null)
        {
            StatusEffect.add(player, effect, (int) (force / (effect.getPower(amplifier).getUseCost(player) / 20)), amplifier);
        }

        return true;
    }
    
    @Override
    public void stop(EntityPlayer player, Side side)
    {
        super.stop(player, side);
        StatusEffect.clear(player, effect);
    }
}
