package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.damage.ALDamageSources;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALEntityData;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PowerEffectPush extends PowerEffect
{
    public PowerEffectPush(int amplifier)
    {
        super(amplifier);
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        World world = player.worldObj;
        double range = 16;

        Vec3 src = VectorHelper.getOffsetCoords(player, 0, 0, 0);
        Vec3 dest = VectorHelper.getOffsetCoords(player, 0, 0, range);
        Vec3 hitVec = null;
        MovingObjectPosition rayTrace = world.rayTraceBlocks(VectorHelper.copy(src), VectorHelper.copy(dest));

        if (rayTrace == null)
        {
            hitVec = dest;
        }
        else
        {
            hitVec = rayTrace.hitVec;
        }

        double distance = player.getDistance(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);

        for (double point = 0; point <= distance; point += 0.15D)
        {
            Vec3 particleVec = VectorHelper.getOffsetCoords(player, 0, 0, point);

            for (EntityLivingBase entity : VectorHelper.getEntitiesNear(EntityLivingBase.class, world, particleVec, 0.5F))
            {
                if (entity != null && entity != player && player.ridingEntity != entity)
                {
                    hitVec.xCoord = entity.posX;
                    hitVec.yCoord = entity.posY;
                    hitVec.zCoord = entity.posZ;
                    rayTrace = new MovingObjectPosition(entity, hitVec);
                    distance = player.getDistance(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);
                    break;
                }
            }
        }

        if (rayTrace != null)
        {
            if (rayTrace.typeOfHit == MovingObjectType.ENTITY && rayTrace.entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase entity = (EntityLivingBase) rayTrace.entityHit;

                if (!entity.worldObj.isRemote)
                {
                    entity.attackEntityFrom(ALDamageSources.causeForceDamage(player), getDamage(amplifier));
                    ALEntityData.getData(entity).forcePushed = true;
                }

                Vec3 vec3 = VectorHelper.getOffsetCoords(player, 0, 0, 0.5F * getKnockback(amplifier));
                entity.motionX += (vec3.xCoord - src.xCoord);
                entity.motionY += (vec3.yCoord - src.yCoord);
                entity.motionZ += (vec3.zCoord - src.zCoord);
                ALData.FORCE_PUSHING_TIMER.setWithoutNotify(player, 1.0F);

                return true;
            }
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[]
        {
            PowerDesc.create("effect2", PowerDesc.format("+%s %s", getKnockback(amplifier), Unit.KNOCKBACK), Target.TARGET),
            PowerDesc.create("effect2", PowerDesc.format("%s %s", getDamage(amplifier), Unit.DAMAGE), Target.TARGET)
        };
    }

    public static float getKnockback(int amplifier)
    {
        int i = 1;

        for (int j = 0; j < amplifier; ++j)
        {
            i *= 2;
        }

        return 3 + i;
    }

    public static float getDamage(int amplifier)
    {
        float f = 1;

        for (int j = 0; j < amplifier; ++j)
        {
            f *= f + 0.5F;
        }

        return f;
    }
}
