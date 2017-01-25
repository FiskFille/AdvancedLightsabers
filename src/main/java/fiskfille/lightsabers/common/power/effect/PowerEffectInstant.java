package fiskfille.lightsabers.common.power.effect;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;

public class PowerEffectInstant extends PowerEffect
{
    public Effect effect;

    public PowerEffectInstant(Effect effect)
    {
        this.effect = effect;
    }

    @Override
    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        DataManager.addEffect(player, effect.id, (int) ((Float) args[0] * 20), (Integer) args[1]);
        return true;
    }
}
