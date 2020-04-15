package com.fiskmods.lightsabers.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityForceLightning extends Entity
{
    public EntityLivingBase entity;

    public EntityForceLightning(World world)
    {
        super(world);
        setSize(0.1F, 0.1F);
        ignoreFrustumCheck = true;
        renderDistanceWeight = 100D;
    }

    public EntityForceLightning(World world, EntityLivingBase entity)
    {
        this(world);
        this.entity = entity;
        setLocationAndAngles(entity.posX, entity.posY + entity.height / 2, entity.posZ, entity.rotationYaw, entity.rotationPitch);
    }

    @Override
    public void onUpdate()
    {
        if (++ticksExisted > 2)
        {
            setDead();
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
    }

    @Override
    protected void entityInit()
    {
    }
}
