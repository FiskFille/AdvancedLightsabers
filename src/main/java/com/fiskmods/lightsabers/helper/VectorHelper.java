package com.fiskmods.lightsabers.helper;
//package com.fiskmods.lightsabers.common.helper;
//
//import java.util.List;
//
//import net.minecraft.command.IEntitySelector;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.util.AxisAlignedBB;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.MovingObjectPosition;
//import net.minecraft.util.Vec3;
//import net.minecraft.world.World;
//import com.fiskmods.lightsabers.Lightsabers;
//
//public class VectorHelper
//{
//	public static Vec3 getOffsetCoords(EntityLivingBase entity, double xOffset, double yOffset, double zOffset, float partialTicks)
//    {
//        Vec3 vec3 = Vec3.createVectorHelper(xOffset, yOffset, zOffset);
//        vec3.rotateAroundX(-(entity.rotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks) * (float) Math.PI / 180.0F);
//        vec3.rotateAroundY(-(entity.rotationYaw - (entity.rotationYaw - entity.prevRotationYaw) * partialTicks) * (float) Math.PI / 180.0F);
//
//        return Vec3.createVectorHelper(entity.posX + vec3.xCoord, getAccuratePlayerY(entity) + vec3.yCoord, entity.posZ + vec3.zCoord);
//    }
//
//    public static Vec3 getOffsetCoords(EntityLivingBase entity, double xOffset, double yOffset, double zOffset)
//    {
//        return getOffsetCoords(entity, xOffset, yOffset, zOffset, 1.0F);
//    }
//
//    public static Vec3 copy(Vec3 vec3)
//    {
//        return Vec3.createVectorHelper(vec3.xCoord, vec3.yCoord, vec3.zCoord);
//    }
//
//    public static Vec3 add(Vec3 vec3, Vec3 vec31)
//    {
//        return vec3.addVector(vec31.xCoord, vec31.yCoord, vec31.zCoord);
//    }
//
//    public static Vec3 getBackSideCoords(EntityLivingBase entity, double amount, boolean side, double backAmount, boolean pitch)
//    {
//        Vec3 front = getFrontCoords(entity, backAmount, pitch).addVector(-entity.posX, -getAccuratePlayerY(entity), -entity.posZ);
//        return getSideCoords(entity, amount, side).addVector(front.xCoord, front.yCoord, front.zCoord);
//    }
//
//    public static Vec3 getAboveBackSideCoords(EntityLivingBase entity, double amount, boolean side, double backAmount, boolean pitch, double aboveAmount)
//    {
//        Vec3 front = getBackSideCoords(entity, amount, side, backAmount, pitch).addVector(-entity.posX, -(getAccuratePlayerY(entity)), -entity.posZ);
//        return getAboveCoords(entity, -90, aboveAmount).addVector(front.xCoord, front.yCoord, front.zCoord);
//    }
//
//    public static Vec3 getSideCoords(EntityLivingBase entity, double amount, int side)
//    {
//        return getSideCoords(entity, amount, side, true);
//    }
//
//    public static Vec3 getSideCoords(EntityLivingBase entity, double amount, boolean side)
//    {
//        return getSideCoords(entity, amount, side ? -90 : 90);
//    }
//
//    public static Vec3 getSideCoords(EntityLivingBase entity, double amount, int side, boolean applyPitch)
//    {
//        float pitch = applyPitch ? entity.rotationPitch : 0;
//        float yaw = entity.rotationYaw + side;
//        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
//        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
//        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
//        float yScale = MathHelper.sin(-pitch * 0.017453292F);
//        float xScale = f4 * f5;
//        float zScale = f3 * f5;
//        return Vec3.createVectorHelper(entity.posX, getAccuratePlayerY(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
//    }
//
//    public static Vec3 getFrontCoords(EntityLivingBase entity, double amount, boolean applyPitch)
//    {
//        return getFrontCoords(entity, applyPitch ? entity.rotationPitch : 0, amount);
//    }
//
//    public static Vec3 getFrontCoords(EntityLivingBase entity, float pitch, double amount)
//    {
//        float yaw = entity.rotationYaw;
//        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
//        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
//        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
//        float yScale = MathHelper.sin(-pitch * 0.017453292F);
//        float xScale = f4 * f5;
//        float zScale = f3 * f5;
//        return Vec3.createVectorHelper(entity.posX, getAccuratePlayerY(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
//    }
//
//    public static Vec3 getAboveCoords(EntityLivingBase entity, float pitch, double amount)
//    {
//        float yaw = entity.rotationYaw;
//        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
//        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
//        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
//        float yScale = MathHelper.sin(-pitch * 0.017453292F);
//        float xScale = f4 * f5;
//        float zScale = f3 * f5;
//        return Vec3.createVectorHelper(entity.posX, getAccuratePlayerY(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
//    }
//
//    public static Vec3 getVerticalCoords(EntityLivingBase entity, float pitchOffset, double amount)
//    {
//        float pitch = entity.rotationPitch - pitchOffset;
//        float f3 = MathHelper.cos(-pitch * 0.017453292F - (float) Math.PI);
//        float f4 = MathHelper.sin(-pitch * 0.017453292F - (float) Math.PI);
//        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
//        float yScale = MathHelper.sin(-pitch * 0.017453292F);
//        float xScale = f4 * f5;
//        float zScale = f3 * f5;
//        return Vec3.createVectorHelper(entity.posX, getAccuratePlayerY(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
//    }
//
//    public static MovingObjectPosition rayTraceBlocks(World world, EntityLivingBase entity, double distance, boolean flag)
//    {
//        float pitch = entity.rotationPitch;
//        float yaw = entity.rotationYaw;
//        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
//        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
//        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
//        float yScale = MathHelper.sin(-pitch * 0.017453292F);
//        float xScale = f4 * f5;
//        float zScale = f3 * f5;
//        Vec3 entityPos = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
//        Vec3 vec31 = entityPos.addVector(xScale * distance, yScale * distance, zScale * distance);
//        return world.func_147447_a(entityPos, vec31, flag, !flag, false);
//    }
//
//    public static Vec3 getBackSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean side, double backAmount, boolean pitch)
//    {
//        Vec3 front = getFrontCoordsRenderYawOffset(entity, backAmount, pitch).addVector(-entity.posX, -getAccuratePlayerY(entity), -entity.posZ);
//        return getSideCoordsRenderYawOffset(entity, amount, side).addVector(front.xCoord, front.yCoord, front.zCoord);
//    }
//
//    public static Vec3 getSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean side)
//    {
//        return getSideCoordsRenderYawOffset(entity, amount, side ? -90 : 90);
//    }
//
//    public static Vec3 getSideCoordsRenderYawOffset(EntityLivingBase entity, double amount, int side)
//    {
//        float pitch = 0;
//        float yaw = entity.renderYawOffset + side;
//        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
//        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
//        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
//        float yScale = MathHelper.sin(-pitch * 0.017453292F);
//        float xScale = f4 * f5;
//        float zScale = f3 * f5;
//        return Vec3.createVectorHelper(entity.posX, getAccuratePlayerY(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
//    }
//
//    public static Vec3 getFrontCoordsRenderYawOffset(EntityLivingBase entity, double amount, boolean applyPitch)
//    {
//        float pitch = applyPitch ? entity.rotationPitch : 0;
//        float yaw = entity.renderYawOffset;
//
//        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
//        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
//        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
//        float yScale = MathHelper.sin(-pitch * 0.017453292F);
//        float xScale = f4 * f5;
//        float zScale = f3 * f5;
//        return Vec3.createVectorHelper(entity.posX, getAccuratePlayerY(entity), entity.posZ).addVector(xScale * amount, yScale * amount, zScale * amount);
//    }
//
//    public static double getAccuratePlayerY(EntityLivingBase entity)
//    {
//        return (entity.posY + (entity instanceof EntityPlayer && Lightsabers.proxy.isClientPlayer((EntityPlayer)entity) ? 0 : -0.2)) + entity.getEyeHeight();
//    }
//
//    public static List<EntityLivingBase> getEntitiesNear(World world, double x, double y, double z, double amount)
//    {
//        return world.selectEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - amount, y - amount, z - amount, x + amount, y + amount, z + amount), IEntitySelector.selectAnything);
//    }
//}
