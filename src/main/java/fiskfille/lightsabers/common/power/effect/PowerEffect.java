package fiskfille.lightsabers.common.power.effect;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.power.Power;

public class PowerEffect
{
    public static final PowerEffect heal = new PowerEffectHeal();
    public static final PowerEffect fortify = new PowerEffectFortify();
    public static final PowerEffect stun = new PowerEffectStun();

    public static final PowerEffect drain = new PowerEffectDrain();
    public static final PowerEffect lightning = new PowerEffectLightning();
    public static final PowerEffect choke = new PowerEffectChoke();

    public static final PowerEffect stealth = new PowerEffectStealth();
    public static final PowerEffect speed = new PowerEffectSpeed();
    public static final PowerEffect gaze = new PowerEffectGaze();
    public static final PowerEffect meditation = new PowerEffectMeditation();
    public static final PowerEffect bladeThrow = new PowerEffectBladeThrow();
    public static final PowerEffect resist = new PowerEffectResist();
    public static final PowerEffect push = new PowerEffectPush();

    protected Random rand = new Random();

    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        return true;
    }

    public float getUseCost(EntityPlayer player, Power power, Object... args)
    {
        return power.powerStats.forcePowerUseCost;
    }

    public String getCastSound(int type)
    {
        return type >= 0 ? ALSounds.player_force_cast : ALSounds.player_force_dark;
    }

    public float getCastSoundVolume(int type)
    {
        return 1.0F;
    }

    public float getCastSoundPitch(int type)
    {
        return (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F;
    }

    public String[] getDesc(Object... args)
    {
        return new String[] {};
    }
}
