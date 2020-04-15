package com.fiskmods.lightsabers.client.particle;

import net.minecraft.client.particle.EntityFX;

public enum ALParticleType
{
    FORCE_HEAL(EntityALHealFX.class);

    public Class<? extends EntityFX> particleClass;

    private ALParticleType(Class<? extends EntityFX> clazz)
    {
        particleClass = clazz;
    }
}
