package com.fiskmods.lightsabers.common.force.effect;

import java.util.List;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PowerEffectDrain extends PowerEffect
{
    public PowerEffectDrain(int amplifier)
    {
        super(amplifier);
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        World world = player.worldObj;
        List<EntityLivingBase> list = getTargets(player);

        for (EntityLivingBase entity : list)
        {
            StatusEffect.add(entity, player, Effect.DRAIN, 30, amplifier);
        }

        if (list.size() > 0)
        {
            ALData.DRAIN_LIFE_TIMER.setWithoutNotify(player, 1.0F);
            return true;
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("absorb", PowerDesc.format("%s %s", 4 + amplifier * 2, Unit.HEALTH), amplifier < 2 ? Target.TARGET : Target.ENEMIES)};
    }

    public List<EntityLivingBase> getTargets(EntityPlayer player)
    {
        List<EntityLivingBase> list = Lists.newArrayList();
        World world = player.worldObj;
        double range = amplifier < 2 ? 5 : 7;

        if (amplifier < 2)
        {
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
                    list.add(entity);
                }
            }
        }
        else
        {
            AxisAlignedBB aabb = player.boundingBox.copy().expand(range, range, range);
            List<EntityLivingBase> list1 = world.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);

            float force = ALData.FORCE_POWER.get(player) + 15;
            float cost = super.getUseCost(player, Effect.DRAIN.getPower(amplifier));

            for (EntityLivingBase entity : list1)
            {
                if (!ALHelper.isAlly(player, entity) && entity != player)
                {
                    if (force >= cost + list.size() * 15 + 15)
                    {
                        list.add(entity);
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }

        return list;
    }

    @Override
    public float getUseCost(EntityPlayer player, Power power)
    {
        float cost = super.getUseCost(player, power);

        if (amplifier >= 2)
        {
            float force = ALData.FORCE_POWER.get(player);

            if (force >= cost)
            {
                cost += (getTargets(player).size() - 1) * 15;
            }
        }

        return cost;
    }
}
