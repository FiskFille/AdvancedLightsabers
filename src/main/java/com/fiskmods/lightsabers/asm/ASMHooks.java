package com.fiskmods.lightsabers.asm;

import com.fiskmods.lightsabers.common.damage.ALDamageSources;
import com.fiskmods.lightsabers.common.item.ItemLightsaber;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class ASMHooks
{
    public static boolean attackEntityFrom(Entity entity, DamageSource source, float damage) // TODO: Remove?
    {
        if (source.getEntity() instanceof EntityLivingBase)
        {
            EntityLivingBase attacker = (EntityLivingBase) source.getEntity();

            if (attacker.getHeldItem() != null && attacker.getHeldItem().getItem() instanceof ItemLightsaber)
            {
                return entity.attackEntityFrom(ALDamageSources.causeLightsaberDamage(attacker), damage);
            }
        }

        return entity.attackEntityFrom(source, damage);
    }
}
