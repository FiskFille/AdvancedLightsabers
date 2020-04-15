package com.fiskmods.lightsabers.common.force.effect;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.helper.VectorHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PowerEffectChoke extends PowerEffect
{
    public PowerEffectChoke(int amplifier)
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

        if (rayTrace != null && rayTrace.typeOfHit == MovingObjectType.ENTITY && rayTrace.entityHit instanceof EntityLivingBase)
        {
            StatusEffect.add((EntityLivingBase) rayTrace.entityHit, player, Effect.CHOKE, 60, amplifier);
            return true;
        }

        return false;
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", 2 + amplifier * 2, Unit.DAMAGE, EnumChatFormatting.GRAY + "/"), Target.TARGET), PowerDesc.create("effect", PowerDesc.format("%s %s%s", Effect.STUN, EnumChatFormatting.GRAY, getStunDuration(amplifier)), Target.TARGET)};
    }

    public static float getStunDuration(int amplifier)
    {
        float f = 1.5F;

        if (amplifier > 0)
        {
            f += 1 + (amplifier - 1) * 0.5F;
        }

        return f;
    }
}
