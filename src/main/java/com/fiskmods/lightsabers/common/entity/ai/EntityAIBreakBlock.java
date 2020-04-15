package com.fiskmods.lightsabers.common.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakBlock extends EntityAIBlockInteract
{
    private int breakingTime;
    private int field_75358_j = -1;

    public EntityAIBreakBlock(EntityLiving entity)
    {
        super(entity);
    }

    @Override
    public boolean shouldExecute()
    {
        return super.shouldExecute() && theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();
        breakingTime = 0;
    }

    @Override
    public boolean continueExecuting()
    {
        double d0 = theEntity.getDistanceSq(entityPosX, entityPosY, entityPosZ);
        return breakingTime <= 60;
    }

    @Override
    public void resetTask()
    {
        super.resetTask();
        theEntity.worldObj.destroyBlockInWorldPartially(theEntity.getEntityId(), entityPosX, entityPosY, entityPosZ, -1);
    }

    @Override
    public void updateTask()
    {
        super.updateTask();
        ++breakingTime;
        int i = (int) (breakingTime / 60.0F * 10.0F);

        if (i != field_75358_j)
        {
            theEntity.worldObj.destroyBlockInWorldPartially(theEntity.getEntityId(), entityPosX, entityPosY, entityPosZ, i);
            field_75358_j = i;
        }

        if (breakingTime == 60 && theEntity.worldObj.difficultySetting == EnumDifficulty.HARD)
        {
            theEntity.worldObj.setBlockToAir(entityPosX, entityPosY, entityPosZ);
            theEntity.worldObj.playAuxSFX(2001, entityPosX, entityPosY, entityPosZ, Block.getIdFromBlock(field_151504_e));
        }
    }
}
