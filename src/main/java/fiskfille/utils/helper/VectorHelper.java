package fiskfille.utils.helper;

import java.util.List;

import com.fiskmods.lightsabers.Lightsabers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class VectorHelper
{
    public static Vec3 getOffsetCoords(EntityLivingBase entity, double xOffset, double yOffset, double zOffset, float partialTicks)
    {
        Vec3 vec3 = Vec3.createVectorHelper(xOffset, yOffset, zOffset);
        vec3.rotateAroundX(-(entity.rotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks) * (float) Math.PI / 180.0F);
        vec3.rotateAroundY(-(entity.rotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks) * (float) Math.PI / 180.0F);

        return Vec3.createVectorHelper(entity.posX + vec3.xCoord, entity.posY + getOffset(entity) + vec3.yCoord, entity.posZ + vec3.zCoord);
    }

    public static Vec3 getOffsetCoords(EntityLivingBase entity, double xOffset, double yOffset, double zOffset)
    {
        return getOffsetCoords(entity, xOffset, yOffset, zOffset, 1.0F);
    }

    public static Vec3 copy(Vec3 vec3)
    {
        return Vec3.createVectorHelper(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public static Vec3 add(Vec3 vec3, Vec3 vec31)
    {
        return Vec3.createVectorHelper(vec3.xCoord + vec31.xCoord, vec3.yCoord + vec31.yCoord, vec3.zCoord + vec31.zCoord);
    }

    public static Vec3 multiply(Vec3 vec3, Vec3 vec31)
    {
        return Vec3.createVectorHelper(vec3.xCoord * vec31.xCoord, vec3.yCoord * vec31.yCoord, vec3.zCoord * vec31.zCoord);
    }

    public static Vec3 multiply(Vec3 vec3, double factor)
    {
        return multiply(vec3, Vec3.createVectorHelper(factor, factor, factor));
    }

    public static Vec3 interpolate(Vec3 vec3, Vec3 vec31, double distance)
    {
        double d = distance / vec3.distanceTo(vec31);
        return add(multiply(vec3, d), multiply(vec31, 1 - d));
    }

    public static Vec3 centerOf(Entity entity)
    {
        return Vec3.createVectorHelper(entity.posX, entity.boundingBox.minY + entity.height / 2, entity.posZ);
    }

    public static Vec3 getPosition(Entity entity, float partialTicks)
    {
        if (partialTicks == 1.0F)
        {
            return Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
        }
        else
        {
            double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
            double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
            double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
            return Vec3.createVectorHelper(d0, d1, d2);
        }
    }

    public static double getOffset(EntityLivingBase entity)
    {
        double yOffset = entity.getEyeHeight();

        if (entity instanceof EntityPlayer && entity.worldObj.isRemote)
        {
            yOffset -= ((EntityPlayer) entity).getDefaultEyeHeight();

            if (!Lightsabers.proxy.isClientPlayer(entity))
            {
                yOffset += 1.62F;
            }
        }

        return yOffset;
    }

    public static Vec3 getBackSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean side, double backAmount, boolean pitch)
    {
        Vec3 front = getFrontCoordsRenderYawOffset(entity, backAmount, pitch).addVector(-entity.posX, -(entity.posY + getOffset(entity)), -entity.posZ);
        return getSideCoordsRenderYawOffset(entity, amount, side).addVector(front.xCoord, front.yCoord, front.zCoord);
    }

    public static Vec3 getSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean side)
    {
        return getSideCoordsRenderYawOffset(entity, amount, side ? -90 : 90);
    }

    public static Vec3 getSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, int side)
    {
        float pitch = 0;
        float yaw = entity.renderYawOffset + side;
        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float yScale = MathHelper.sin(-pitch * 0.017453292F);
        float xScale = f4 * f5;
        float zScale = f3 * f5;
        return Vec3.createVectorHelper(entity.posX, entity.posY + getOffset(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
    }

    public static Vec3 getFrontCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean applyPitch)
    {
        float pitch = applyPitch ? entity.rotationPitch : 0;
        float yaw = entity.renderYawOffset;

        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float yScale = MathHelper.sin(-pitch * 0.017453292F);
        float xScale = f4 * f5;
        float zScale = f3 * f5;
        return Vec3.createVectorHelper(entity.posX, entity.posY + getOffset(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
    }

    public static <T extends Entity> List<T> getEntitiesNear(Class<T> type, World world, double x, double y, double z, double radius)
    {
        return world.getEntitiesWithinAABB(type, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
    }

    public static <T extends Entity> List<T> getEntitiesNear(Class<T> type, World world, Vec3 vec3, double radius)
    {
        return getEntitiesNear(type, world, vec3.xCoord, vec3.yCoord, vec3.zCoord, radius);
    }
}
