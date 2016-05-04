package fiskfille.lightsabers.common.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

public abstract class EntityAIBlockInteract extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected int entityPosX;
    protected int entityPosY;
    protected int entityPosZ;
    protected Block field_151504_e;

    boolean hasStoppedBlockInteraction;
    float entityPositionX;
    float entityPositionZ;

    public EntityAIBlockInteract(EntityLiving entity)
    {
        theEntity = entity;
    }

    public boolean shouldExecute()
    {
        if (!theEntity.isCollidedHorizontally)
        {
            return false;
        }
        else
        {
            PathNavigate pathnavigate = theEntity.getNavigator();
            PathEntity pathentity = pathnavigate.getPath();

            if (pathentity != null && !pathentity.isFinished() && pathnavigate.getCanBreakDoors())
            {
                for (int i = 0; i < Math.min(pathentity.getCurrentPathIndex() + 2, pathentity.getCurrentPathLength()); ++i)
                {
                    PathPoint pathpoint = pathentity.getPathPointFromIndex(i);
                    entityPosX = pathpoint.xCoord;
                    entityPosY = pathpoint.yCoord + 1;
                    entityPosZ = pathpoint.zCoord;

                    if (theEntity.getDistanceSq((double)entityPosX, theEntity.posY, (double)entityPosZ) <= 2.25D)
                    {
                        field_151504_e = func_151503_a(entityPosX, entityPosY, entityPosZ);

                        if (field_151504_e != null)
                        {
                            return true;
                        }
                    }
                }

                entityPosX = MathHelper.floor_double(theEntity.posX);
                entityPosY = MathHelper.floor_double(theEntity.posY + 1.0D);
                entityPosZ = MathHelper.floor_double(theEntity.posZ);
                field_151504_e = func_151503_a(entityPosX, entityPosY, entityPosZ);
                return field_151504_e != null;
            }
            else
            {
                return false;
            }
        }
    }

    public boolean continueExecuting()
    {
        return !hasStoppedBlockInteraction;
    }

    public void startExecuting()
    {
        hasStoppedBlockInteraction = false;
        entityPositionX = (float)((double)((float)entityPosX + 0.5F) - theEntity.posX);
        entityPositionZ = (float)((double)((float)entityPosZ + 0.5F) - theEntity.posZ);
    }

    public void updateTask()
    {
        float f = (float)((double)((float)entityPosX + 0.5F) - theEntity.posX);
        float f1 = (float)((double)((float)entityPosZ + 0.5F) - theEntity.posZ);
        float f2 = entityPositionX * f + entityPositionZ * f1;

        if (f2 < 0.0F)
        {
            hasStoppedBlockInteraction = true;
        }
    }

    private Block func_151503_a(int x, int y, int z)
    {
        Block block = theEntity.worldObj.getBlock(x, y, z);
        return block.getBlockHardness(theEntity.worldObj, x, y, z) < 0 ? null : block;
    }
}
