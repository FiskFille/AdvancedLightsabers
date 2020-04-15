package com.fiskmods.lightsabers.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityALHealFX extends EntityALFX
{
    private float flameScale;

    public EntityALHealFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        flameScale = particleScale;
        particleRed = particleGreen = particleBlue = 1.0F;
        particleMaxAge = (int) (10.0D / (Math.random() * 0.25D + 0.75D)) + 10;
        noClip = false;
        setParticleTextureIndex(0);
    }

    @Override
    public void renderParticle(Tessellator tesselator, float partialTicks, float f, float f1, float f2, float f3, float f4)
    {
        float f5 = (particleAge + partialTicks) / particleMaxAge;
        particleScale = flameScale * (1.0F - f5 * f5 * 0.5F);
        super.renderParticle(tesselator, partialTicks, f, f1, f2, f3, f4);
    }

    @Override
    public EntityFX multipleParticleScaleBy(float scale)
    {
        flameScale *= scale;
        return this;
    }

    @Override
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }

    @Override
    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }

    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY += 0.001F;

        if (particleAge++ >= particleMaxAge)
        {
            setDead();
        }

        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9599999785423279D;
        motionY *= 0.9599999785423279D;
        motionZ *= 0.9599999785423279D;

        if (onGround)
        {
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
        }
    }
}
