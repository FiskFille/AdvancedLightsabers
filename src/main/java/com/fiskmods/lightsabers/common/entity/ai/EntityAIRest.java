package com.fiskmods.lightsabers.common.entity.ai;

import com.fiskmods.lightsabers.common.entity.EntitySithGhost;

import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIRest extends EntityAIBase
{
    private EntitySithGhost entity;
    private double speed;

    public EntityAIRest(EntitySithGhost entity, double speed)
    {
        this.entity = entity;
        this.speed = speed;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        return continueExecuting();
    }

    @Override
    public boolean continueExecuting()
    {
        return entity.taskFinished == 2 && entity.hasRestingPlace;
    }

    @Override
    public void startExecuting()
    {
        entity.getNavigator().tryMoveToXYZ(entity.restingPlaceX, entity.restingPlaceY, entity.restingPlaceZ, speed);
    }
}
