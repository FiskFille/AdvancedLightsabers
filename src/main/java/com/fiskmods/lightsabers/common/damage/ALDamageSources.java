package com.fiskmods.lightsabers.common.damage;

import com.fiskmods.lightsabers.Lightsabers;

import fiskfille.utils.common.damage.FiskDamageSource;
import fiskfille.utils.common.damage.FiskEntityDamageSource;
import fiskfille.utils.common.damage.IExtendedDamage.DamageType;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public class ALDamageSources
{
    public static final DamageSource INTO_WALL = new FiskDamageSource(Lightsabers.MODID, "intoWall").with(DamageType.KINETIC).setDamageBypassesArmor();

    public static DamageSource causeLightsaberDamage(Entity entity)
    {
        return new FiskEntityDamageSource(Lightsabers.MODID, "lightsaber", entity).with(ALDamageTypes.LIGHTSABER, DamageType.ENERGY);
    }
    
    public static DamageSource causeForceDamage(Entity entity)
    {
        return new FiskEntityDamageSource(Lightsabers.MODID, "force", entity).with(ALDamageTypes.FORCE).setMagicDamage().setDamageBypassesArmor();
    }

    public static DamageSource causeForceLightningDamage(Entity entity)
    {
        return new FiskEntityDamageSource(Lightsabers.MODID, "lightning", entity).with(ALDamageTypes.FORCE, DamageType.LIGHTNING).setMagicDamage();
    }
}
