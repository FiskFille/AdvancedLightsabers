package fiskfille.lightsabers.asm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import fiskfille.lightsabers.common.damagesource.ModDamageSources;
import fiskfille.lightsabers.common.item.ItemLightsaber;

public class ASMHooks
{
    public static boolean attackEntityFrom(Entity entity, DamageSource source, float damage)
    {
        if (source.getEntity() instanceof EntityLivingBase)
        {
            EntityLivingBase attacker = (EntityLivingBase) source.getEntity();

            if (attacker.getHeldItem() != null && attacker.getHeldItem().getItem() instanceof ItemLightsaber)
            {
                return entity.attackEntityFrom(ModDamageSources.causeLightsaberDamage(attacker), damage);
            }
        }

        return entity.attackEntityFrom(source, damage);
    }
}
