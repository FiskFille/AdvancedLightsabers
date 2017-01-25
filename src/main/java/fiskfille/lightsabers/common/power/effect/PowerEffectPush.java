package fiskfille.lightsabers.common.power.effect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.common.damagesource.ModDamageSources;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.helper.VectorHelper;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectPush extends PowerEffect
{
    @Override
    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        World world = player.worldObj;
        int amplifier = (Integer) args[0];
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

                entity.attackEntityFrom(ModDamageSources.causeForceDamage(player), getDamage(amplifier));
                ALData.setWithoutNotify(player, ALData.FORCE_PUSHING_TIMER, 1.0F);

                Vec3 vec3 = VectorHelper.getOffsetCoords(player, 0, 0, 0.5F * getKnockback(amplifier));
                entity.motionX += (vec3.xCoord - src.xCoord);
                entity.motionY += (vec3.yCoord - src.yCoord);
                entity.motionZ += (vec3.zCoord - src.zCoord);

                return true;
            }
        }

        return false;
    }

    @Override
    public String[] getDesc(Object... args)
    {
        return new String[] {PowerDesc.create("effect2", PowerDesc.format("+%s %s", getKnockback((Integer) args[0]), PowerDesc.KNOCKBACK), PowerDesc.TARGET), PowerDesc.create("effect2", PowerDesc.format("%s %s", getDamage((Integer) args[0]), PowerDesc.DAMAGE), PowerDesc.TARGET)};
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
