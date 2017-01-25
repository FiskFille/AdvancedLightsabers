package fiskfille.lightsabers.common.power.effect;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.helper.VectorHelper;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectDrain extends PowerEffect
{
    @Override
    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        World world = player.worldObj;
        List<EntityLivingBase> list = getTargets(player, args);
        int amplifier = (Integer) args[0];

        for (EntityLivingBase entity : list)
        {
            DataManager.addEffect(entity, player, Effect.drain.id, 30, amplifier);
        }

        if (list.size() > 0)
        {
            ALData.setWithoutNotify(player, ALData.DRAIN_LIFE_TIMER, 1.0F);
            return true;
        }

        return false;
    }

    @Override
    public String[] getDesc(Object... args)
    {
        return new String[] {PowerDesc.create("absorb", PowerDesc.format("%s %s", 4 + (Integer) args[0] * 2, PowerDesc.HEALTH), (Integer) args[0] < 2 ? PowerDesc.TARGET : PowerDesc.ENEMIES)};
    }

    public List<EntityLivingBase> getTargets(EntityPlayer player, Object... args)
    {
        List<EntityLivingBase> list = Lists.newArrayList();
        World world = player.worldObj;
        int amplifier = (Integer) args[0];
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

                for (EntityLivingBase entity : VectorHelper.getEntitiesNear(world, particleVec.xCoord, particleVec.yCoord, particleVec.zCoord, 0.5F))
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

            float force = ALData.getFloat(player, ALData.FORCE_POWER) + 15;
            float cost = super.getUseCost(player, Effect.drain.getPower(amplifier), args);

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
    public float getUseCost(EntityPlayer player, Power power, Object... args)
    {
        int amplifier = (Integer) args[0];
        float cost = super.getUseCost(player, power, args);

        if (amplifier >= 2)
        {
            float force = ALData.getFloat(player, ALData.FORCE_POWER);

            if (force >= cost)
            {
                cost += (getTargets(player, args).size() - 1) * 15;
            }
        }

        return cost;
    }
}
