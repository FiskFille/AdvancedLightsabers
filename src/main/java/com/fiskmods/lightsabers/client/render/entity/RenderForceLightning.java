package com.fiskmods.lightsabers.client.render.entity;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.entity.EntityForceLightning;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import fiskfille.utils.helper.VectorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderForceLightning extends Render
{
    public RenderForceLightning()
    {
        shadowSize = 0.0F;
    }

    public void render(EntityForceLightning forceLightning, double x, double y, double z, float f, float partialTicks)
    {
        EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;
        EntityLivingBase caster = forceLightning.entity;
        
        Vec3 src = caster.getPosition(partialTicks).addVector(0, VectorHelper.getOffset(caster), 0);

        if (forceLightning.isEntityAlive()) // TODO: Verify on servers
        {
            x = src.xCoord - RenderManager.renderPosX;
            y = src.yCoord - RenderManager.renderPosY;
            z = src.zCoord - RenderManager.renderPosZ;

            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            ALRenderHelper.setupRenderLightning();
            renderLightning(caster, StatusEffect.getTargets(caster, Effect.DRAIN), Vec3.createVectorHelper(1, 0.4F, 0), 2, partialTicks);

            StatusEffect effect = StatusEffect.get(caster, Effect.LIGHTNING);

            if (effect != null)
            {
                Random rand = new Random(caster.ticksExisted * 100000);
                EntityLivingBase target = ALHelper.getForceLightningTarget(caster);
                Vec3 color = Vec3.createVectorHelper(0, 0, 1);

                for (int hand = 0; hand < 2; ++hand)
                {
                    for (int j = 0; j < 2 + effect.amplifier; ++j)
                    {
                        Vec3 dst = Vec3.createVectorHelper(0, 0, 7);
                        dst.rotateAroundX(-(caster.rotationPitch + (caster.rotationPitch - caster.prevRotationPitch) * partialTicks) * (float) Math.PI / 180.0F);
                        dst.rotateAroundY(-(caster.rotationYawHead + (caster.rotationYawHead - caster.prevRotationYawHead) * partialTicks) * (float) Math.PI / 180.0F);
                        dst = VectorHelper.add(caster.getPosition(partialTicks), dst).addVector(0, 0, 0);

                        Vec3 targetVec = null;
                        MovingObjectPosition rayTrace = caster.worldObj.rayTraceBlocks(src, VectorHelper.copy(dst));

                        if (rayTrace == null)
                        {
                            targetVec = dst;
                        }
                        else
                        {
                            targetVec = rayTrace.hitVec;
                        }

                        if (target != null)
                        {
                            targetVec = target.getPosition(partialTicks).addVector(0, target.height / 2, 0);
                        }

                        renderLightning(caster, targetVec, color, rand, 1.5F + effect.amplifier * 0.5F, hand == 0, partialTicks);
                    }
                }
            }

            ALRenderHelper.finishRenderLightning();
            GL11.glPopMatrix();
        }
    }

    public void renderLightning(EntityLivingBase caster, List<EntityLivingBase> targets, Vec3 color, int lightningAmount, float partialTicks)
    {
        Random rand = new Random(caster.ticksExisted * 100000);
        EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;

        for (EntityLivingBase target : targets)
        {
            Vec3 targetVec = target.getPosition(partialTicks).addVector(0, VectorHelper.getOffset(target), 0);

            for (int j = 0; j < lightningAmount; ++j)
            {
                renderLightning(caster, targetVec, color, rand, 1, partialTicks);
            }
        }
    }

    public void renderLightning(EntityLivingBase caster, Vec3 targetVec, Vec3 color, Random rand, float spreadFactor, float partialTicks)
    {
        renderLightning(caster, targetVec, color, rand, spreadFactor, true, partialTicks);
    }

    public void renderLightning(EntityLivingBase caster, Vec3 targetVec, Vec3 color, Random rand, float spreadFactor, boolean hand, float partialTicks)
    {
        Vec3 src = Vec3.createVectorHelper(-0.275F * (hand ? 1 : -1), -0.25F, 0.8F);
        Vec3 dst = caster.getPosition(partialTicks).addVector(0, VectorHelper.getOffset(caster), 0).subtract(targetVec);
        Vec3 dst1 = VectorHelper.copy(dst);
        Vec3 dst2 = VectorHelper.copy(dst);
        
        boolean firstPerson = caster == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
        double amount = Math.min(src.distanceTo(dst) * 0.05D, 1) * (firstPerson ? 0.75D : 1);
        double srcSpread = Math.min(firstPerson ? 0.05D : 0.15D, amount);
        double dstSpread = Math.min(0.2D, amount) * spreadFactor;
        double d = 0.33D;
        double d1 = 0.66D;

        if (firstPerson)
        {
            src = Vec3.createVectorHelper(-0.45F * (hand ? 1 : -1), -0.25F, 0.6F);
        }
        
        Vec3[] asrc = {src, VectorHelper.copy(src)};
        Vec3[] adst = {dst, VectorHelper.copy(dst)};
        Vec3[] adst1 = {dst1, VectorHelper.copy(dst1)};
        Vec3[] adst2 = {dst2, VectorHelper.copy(dst2)};

        for (int i = 0; i < 2; ++i)
        {
            asrc[i].rotateAroundX(-ALRenderHelper.median(caster.rotationPitch, caster.prevRotationPitch) * (float) Math.PI / 180.0F);
            asrc[i].rotateAroundY(-ALRenderHelper.median(caster.rotationYaw, caster.prevRotationYaw) * (float) Math.PI / 180.0F);
            asrc[i].xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * srcSpread;
            asrc[i].yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * srcSpread;
            asrc[i].zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * srcSpread;
            adst1[i].xCoord = adst[i].xCoord * d + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
            adst1[i].yCoord = adst[i].yCoord * d + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
            adst1[i].zCoord = adst[i].zCoord * d + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
            adst2[i].xCoord = adst[i].xCoord * d1 + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
            adst2[i].yCoord = adst[i].yCoord * d1 + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
            adst2[i].zCoord = adst[i].zCoord * d1 + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
            adst[i].xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * 0.125F;
            adst[i].yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * 0.125F;
            adst[i].zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * 0.125F;
            adst1[i].xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d;
            adst1[i].yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d;
            adst1[i].zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d;
            adst2[i].xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d1;
            adst2[i].yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d1;
            adst2[i].zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d1;
            
            rand.setSeed((caster.ticksExisted - 1) * 100000);
        }
        
        src = VectorHelper.add(asrc[1], VectorHelper.multiply(asrc[1].subtract(asrc[0]), partialTicks));
        dst = VectorHelper.add(adst[1], VectorHelper.multiply(adst[1].subtract(adst[0]), partialTicks));
        dst1 = VectorHelper.add(adst1[1], VectorHelper.multiply(adst1[1].subtract(adst1[0]), partialTicks));
        dst2 = VectorHelper.add(adst2[1], VectorHelper.multiply(adst2[1].subtract(adst2[0]), partialTicks));

        float opacity = 1;
        float lineWidth = 5 * (firstPerson ? 2 : 1);
        float innerLineWidth = 1 * (firstPerson ? 2 : 1);
        ALRenderHelper.drawLightningLine(src, dst1, lineWidth, innerLineWidth, color, opacity);
        ALRenderHelper.drawLightningLine(dst1, dst2, lineWidth, innerLineWidth, color, opacity);
        ALRenderHelper.drawLightningLine(dst2, dst, lineWidth, innerLineWidth, color, opacity);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float partialTicks)
    {
        render((EntityForceLightning) entity, x, y, z, f, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
}
