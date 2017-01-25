package fiskfille.lightsabers.common.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public class ModDamageSources
{
    public static DamageSource causeForceDamage(Entity entity)
    {
        return new ForceDamageSource("player", entity).setMagicDamage().setDamageBypassesArmor();
    }

    public static DamageSource causeForceLightningDamage(Entity entity)
    {
        return new ForceDamageSource("forceLightning", entity).setMagicDamage();
    }

    public static DamageSource causeLightsaberDamage(Entity entity)
    {
        return new LightsaberDamageSource("lightsaber", entity);
    }
}
