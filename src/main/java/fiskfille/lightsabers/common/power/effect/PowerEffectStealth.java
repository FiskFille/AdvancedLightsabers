package fiskfille.lightsabers.common.power.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.client.sound.MovingSoundStatusEffect;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectStealth extends PowerEffectStatus
{
    public PowerEffectStealth()
    {
        super(Effect.stealth);
    }

    @Override
    public String[] getDesc(Object... args)
    {
        return new String[] {PowerDesc.create("effect2", PowerDesc.INVISIBILITY, PowerDesc.CASTER)};
    }

    @Override
    public void start(EntityPlayer player, Side side, Object... args)
    {
        if (side.isClient() && Lightsabers.proxy.isClientPlayer(player))
        {
            playSound(player);
        }

        player.setInvisible(true);

        if (Lightsabers.proxy.isClientPlayer(player))
        {
            player.playSound(ALSounds.player_force_stealth_on, 1.0F, 1.0F);
        }
    }

    @Override
    public void stop(EntityPlayer player, Side side, Object... args)
    {
        if (!player.isPotionActive(Potion.invisibility))
        {
            player.setInvisible(false);
        }

        if (Lightsabers.proxy.isClientPlayer(player))
        {
            player.playSound(ALSounds.player_force_stealth_off, 1.0F, 1.0F);
        }
    }

    @SideOnly(Side.CLIENT)
    public void playSound(EntityPlayer player)
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundStatusEffect(player, Effect.stealth, ALSounds.ambient_stealth));
    }
}
