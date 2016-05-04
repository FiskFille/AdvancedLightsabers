package fiskfille.lightsabers.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import fiskfille.lightsabers.common.entity.EntitySithGhost;

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

    public boolean shouldExecute()
    {
        return continueExecuting();
    }

    public boolean continueExecuting()
    {
        return entity.taskFinished == 2 && entity.hasRestingPlace;
    }

    public void startExecuting()
    {
        entity.getNavigator().tryMoveToXYZ(entity.restingPlaceX, entity.restingPlaceY, entity.restingPlaceZ, speed);
    }
}
