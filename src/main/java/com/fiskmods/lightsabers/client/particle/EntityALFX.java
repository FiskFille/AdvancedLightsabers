package com.fiskmods.lightsabers.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityALFX extends EntityFX
{
    protected EntityALFX(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityALFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    public int getFXLayer()
    {
        return 9;
    }

    @Override
    public void setParticleTextureIndex(int index)
    {
        particleTextureIndexX = index % 16;
        particleTextureIndexY = index / 16;
    }
}
