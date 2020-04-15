package com.fiskmods.lightsabers.common.force.effect;

import java.util.List;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.helper.ALHelper;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PowerEffectStun extends PowerEffect
{
    public final float duration;
    public final int durationInt;
    
    public final boolean aoe;
    
    public PowerEffectStun(int amplifier, float duration, boolean aoe)
    {
        super(amplifier);
        this.duration = duration;
        this.durationInt = (int) duration * 20;
        this.aoe = aoe;
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        World world = player.worldObj;
        double range = 16;

        if (aoe)
        {
            AxisAlignedBB aabb = player.boundingBox.copy().expand(10, 10, 10);
            List<EntityLivingBase> list = player.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);

            for (EntityLivingBase entity : list)
            {
                if (!ALHelper.isAlly(player, entity))
                {
                    if (entity != player)
                    {
                        StatusEffect.add(entity, Effect.STUN, durationInt, amplifier);
                    }
                }
            }

            return true;
        }
        else
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
                    StatusEffect.add(entity, Effect.STUN, durationInt, amplifier);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", Effect.STUN, EnumChatFormatting.GRAY, duration), aoe ? Target.ENEMIES : Target.TARGET)};
    }
}
