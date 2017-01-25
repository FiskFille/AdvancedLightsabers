package fiskfille.lightsabers.common.power.effect;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.helper.VectorHelper;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectStun extends PowerEffect
{
    @Override
    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        World world = player.worldObj;
        int amplifier = (Integer) args[0];
        float duration = (Float) args[1];
        double range = 16;

        if (args.length > 2 && (Boolean) args[2])
        {
            AxisAlignedBB aabb = player.boundingBox.copy().expand(10, 10, 10);
            List<EntityLivingBase> list = player.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);

            for (EntityLivingBase entity : list)
            {
                if (!ALHelper.isAlly(player, entity))
                {
                    if (entity != player)
                    {
                        DataManager.addEffect(entity, Effect.stun.id, (int) (duration * 20), amplifier);
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
                    DataManager.addEffect(entity, Effect.stun.id, (int) (duration * 20), amplifier);

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String[] getDesc(Object... args)
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", Effect.stun, EnumChatFormatting.GRAY, args[1]), args.length > 2 && (Boolean) args[2] ? PowerDesc.ENEMIES : PowerDesc.TARGET)};
    }
}
