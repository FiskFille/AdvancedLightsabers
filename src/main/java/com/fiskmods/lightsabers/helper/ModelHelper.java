package com.fiskmods.lightsabers.helper;

import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.event.ClientEventHandler;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.google.common.collect.Maps;

import mods.battlegear2.api.core.BattlegearUtils;
import mods.battlegear2.client.utils.BattlegearRenderHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ModelHelper
{
    public static final Map<Class<? extends ModelBiped>, Float> ARMS = Maps.newHashMap();

    public static void renderBipedPre(ModelBiped model, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        if (!ARMS.containsKey(model.getClass()))
        {
            ARMS.put(model.getClass(), model.bipedRightArm.rotationPointY);
        }

        model.bipedRightArm.rotationPointY = ARMS.get(model.getClass());
        model.bipedLeftArm.rotationPointY = ARMS.get(model.getClass());

        if (Lightsabers.isBattlegearLoaded)
        {
            BattlegearRenderHelper.moveOffHandArm(entity, model, f5);
        }

        setRotationAngles(model, f, f1, f2, f3, f4, f5, entity);
    }

    public static void renderBipedPost(ModelBiped model, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    public static void setRotationAngles(ModelBiped model, float f, float f1, float f2, float f3, float f4, float f5, Entity entity1)
    {
        if (entity1 instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase) entity1;
            ItemStack heldItem = entity.getHeldItem();

            if (heldItem != null && heldItem.getItem() instanceof ItemLightsaberBase && !entity.isSneaking())
            {
                float f6 = model.onGround;
                float f7 = (f6 > 0.5F ? 1 - f6 : f6) * 2;
                
                if (heldItem.getItem() == ModItems.doubleLightsaber)
                {
                    if (model.heldItemRight != 0)
                    {
                        float f8 = 1 - f1;
                        model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * f1 - (((float) Math.PI / 2.5F) * model.heldItemRight) * f8;
                        model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * f1 + (model.bipedRightArm.rotateAngleY * 0.5F - ((float) Math.PI / 15F) * model.heldItemRight) * f8;
                    }

                    model.bipedRightArm.rotateAngleX -= f7;
                    model.bipedRightArm.rotateAngleY += f7;
                }
                else if (heldItem.getItem() == ModItems.lightsaber)
                {
                    if (model.heldItemRight != 0 && !isDualWielding(entity))
                    {
                        float f8 = 1 - f1;
                        model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * f1 - (1 * (float) model.heldItemRight) * f8;
                        model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * f1 + (model.bipedRightArm.rotateAngleY * 0.5F - 0.5F * model.heldItemRight) * f8;
                        model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * f1 + (model.bipedRightArm.rotateAngleZ * 0.5F - 0.2F * model.heldItemRight) * f8;
                        model.bipedRightArm.rotationPointX += 0.5F * f8;

                        float f9 = f1 > 0.5F ? 1 : f1 * 2;
                        float f10 = 1 - f9;
                        model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX * f9 - (1 * (float) model.heldItemRight) * f10;
                        model.bipedLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY * f9 + (model.bipedLeftArm.rotateAngleY * 0.5F + 0.75F * model.heldItemRight) * f10;
                        model.bipedLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ * f9 + (model.bipedLeftArm.rotateAngleZ * 0.5F + 0.0F * model.heldItemRight) * f10;
                        model.bipedLeftArm.rotationPointX -= 0.5F * f8;
                    }

                    if (!isDualWielding(entity))
                    {
                        model.bipedRightArm.rotateAngleX -= f7;
                        model.bipedRightArm.rotateAngleY -= f7 * 0.4F;
                        model.bipedLeftArm.rotateAngleX -= f7 * 1.6F;
                        model.bipedLeftArm.rotateAngleY -= f7 * 0.9F;
                    }
                }
            }

            float push = MathHelper.clamp_float(MathHelper.sin(ALData.FORCE_PUSHING_TIMER.interpolate(entity) * 3) * 1.5F, 0, 1);
            float drain = MathHelper.clamp_float(MathHelper.sin(ALData.DRAIN_LIFE_TIMER.interpolate(entity) * 3) * 4F, 0, 1);
            float right = ALData.RIGHT_ARM_TIMER.interpolate(entity);
            float left = ALData.LEFT_ARM_TIMER.interpolate(entity);

            // TODO: Use one of the interpolate methods instead
            model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * (1 - push) + (f4 / (180F / (float) Math.PI) - (float) Math.PI / 2) * push;
            model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * (1 - push) + (f3 / (180F / (float) Math.PI)) * push;
            model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * (1 - push) + 0 * push;

            model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * (1 - drain) + (f4 / (180F / (float) Math.PI) - (float) Math.PI / 2) * drain;
            model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * (1 - drain) + (f3 / (180F / (float) Math.PI)) * drain;
            model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * (1 - drain) + 0 * drain;

            model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * (1 - right) + (f4 / (180F / (float) Math.PI) - (float) Math.PI / 2) * right;
            model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * (1 - right) + (f3 / (180F / (float) Math.PI)) * right;
            model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * (1 - right) + 0 * right;
            model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX * (1 - left) + (f4 / (180F / (float) Math.PI) - (float) Math.PI / 2) * left;
            model.bipedLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY * (1 - left) + (f3 / (180F / (float) Math.PI)) * left;
            model.bipedLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ * (1 - left) + 0 * left;

            StatusEffect effectChoke = StatusEffect.get(entity, Effect.CHOKE);

            if (effectChoke != null)
            {
                int durationMax = 60;
                float duration = effectChoke.duration - ClientEventHandler.renderTick;
                float fade = 3;

                float f6 = duration > fade ? (duration < durationMax - fade ? 1 : Math.min(durationMax - duration, fade) / fade) : Math.min(duration, fade) / fade;
                float f7 = 1 - f6;
                float f8 = entity1.ticksExisted + ClientEventHandler.renderTick;

                if (effectChoke.amplifier > 0)
                {
                    model.bipedHead.rotationPointX *= f7;
                    model.bipedHead.rotationPointY *= f7;
                    model.bipedHead.rotationPointZ *= f7;
                    model.bipedHeadwear.rotationPointX *= f7;
                    model.bipedHeadwear.rotationPointY *= f7;
                    model.bipedHeadwear.rotationPointZ *= f7;
                    model.bipedBody.rotationPointX *= f7;
                    model.bipedBody.rotationPointY *= f7;
                    model.bipedBody.rotationPointZ *= f7;
                    model.bipedBody.rotateAngleX *= f7;
                    model.bipedBody.rotateAngleY *= f7;
                    model.bipedBody.rotateAngleZ *= f7;
                    model.bipedRightLeg.rotationPointY *= f7;
                    model.bipedRightLeg.rotationPointZ *= f7;
                    model.bipedRightLeg.rotateAngleX *= f7;
                    model.bipedRightLeg.rotateAngleY *= f7;
                    model.bipedRightLeg.rotateAngleZ *= f7;
                    model.bipedLeftLeg.rotationPointY *= f7;
                    model.bipedLeftLeg.rotationPointZ *= f7;
                    model.bipedLeftLeg.rotateAngleX *= f7;
                    model.bipedLeftLeg.rotateAngleY *= f7;
                    model.bipedLeftLeg.rotateAngleZ *= f7;
                    model.bipedRightArm.rotationPointX *= f7;
                    model.bipedRightArm.rotationPointY *= f7;
                    model.bipedRightArm.rotationPointZ *= f7;
                    model.bipedRightArm.rotateAngleX *= f7;
                    model.bipedRightArm.rotateAngleY *= f7;
                    model.bipedRightArm.rotateAngleZ *= f7;
                }

                model.bipedLeftArm.rotationPointX *= f7;
                model.bipedLeftArm.rotationPointY *= f7;
                model.bipedLeftArm.rotationPointZ *= f7;
                model.bipedLeftArm.rotateAngleX *= f7;
                model.bipedLeftArm.rotateAngleY *= f7;
                model.bipedLeftArm.rotateAngleZ *= f7;

                if (effectChoke.amplifier == 0)
                {
                    model.bipedLeftArm.rotateAngleX -= 1.2F * f6;
                    model.bipedLeftArm.rotateAngleY += 1.3F * f6;
                    model.bipedLeftArm.rotationPointX += 5 * f6;
                    model.bipedLeftArm.rotationPointY += 2 * f6;
                }
                else if (effectChoke.amplifier == 1)
                {
                    model.bipedHead.rotateAngleX -= 0.5F * f6;
                    model.bipedHeadwear.rotateAngleX -= 0.5F * f6;

                    model.bipedRightArm.rotateAngleX -= 2.1F * f6;
                    model.bipedRightArm.rotateAngleY -= 1.3F * f6;
                    model.bipedRightArm.rotationPointX -= 6 * f6;
                    model.bipedRightArm.rotationPointY += 3.5F * f6;
                    model.bipedRightArm.rotationPointZ += 1 * f6;

                    model.bipedLeftArm.rotateAngleX -= 2.1F * f6;
                    model.bipedLeftArm.rotateAngleY += 1.3F * f6;
                    model.bipedLeftArm.rotationPointX += 6 * f6;
                    model.bipedLeftArm.rotationPointY += 3.5F * f6;
                    model.bipedLeftArm.rotationPointZ += 1 * f6;

                    model.bipedRightLeg.rotationPointY += 12 * f6;
                    model.bipedRightLeg.rotateAngleX = MathHelper.cos(f8 * 0.6662F) * 1.1F * f6;
                    model.bipedLeftLeg.rotationPointY += 12 * f6;
                    model.bipedLeftLeg.rotateAngleX = MathHelper.cos(f8 * 0.6662F + (float) Math.PI) * 1.1F * f6;
                }
                else if (effectChoke.amplifier == 2)
                {
                    model.bipedHead.rotateAngleX = -0.8F * f6;
                    model.bipedHeadwear.rotateAngleX = -0.8F * f6;
                    model.bipedHead.rotateAngleY = MathHelper.cos(f8 * 3) * 0.2F * f6;
                    model.bipedHeadwear.rotateAngleY = MathHelper.cos(f8 * 3) * 0.2F * f6;

                    model.bipedRightArm.rotateAngleX -= 3.5F * f6;
                    model.bipedRightArm.rotateAngleY -= 0.1F * f6;
                    model.bipedRightArm.rotateAngleZ += 0.3F * f6;
                    model.bipedRightArm.rotationPointX -= 5.25F * f6;
                    model.bipedRightArm.rotationPointY += 3F * f6;

                    model.bipedLeftArm.rotateAngleX -= 3.5F * f6;
                    model.bipedLeftArm.rotateAngleY += 0.1F * f6;
                    model.bipedLeftArm.rotateAngleZ -= 0.3F * f6;
                    model.bipedLeftArm.rotationPointX += 5.25F * f6;
                    model.bipedLeftArm.rotationPointY += 3F * f6;

                    model.bipedRightLeg.rotationPointY += 12 * f6;
                    model.bipedRightLeg.rotateAngleX = MathHelper.cos(f8 * 0.8662F) * 1.1F * f6;
                    model.bipedLeftLeg.rotationPointY += 12 * f6;
                    model.bipedLeftLeg.rotateAngleX = MathHelper.cos(f8 * 0.8662F + (float) Math.PI) * 1.1F * f6;
                }
            }
        }
    }

    public static void applyLightsaberItemRotation(EntityLivingBase entity, ItemStack heldItem)
    {
        if (heldItem.getItem() instanceof ItemLightsaberBase)
        {
            float f = entity.prevLimbSwingAmount - (entity.prevLimbSwingAmount - entity.limbSwingAmount) * ClientEventHandler.renderTick;
            float f1 = MathHelper.cos((entity.limbSwing - entity.limbSwingAmount * (1.0F - ClientEventHandler.renderTick)) * 0.6662F) * 1.4F * f;
            float f2 = entity.getSwingProgress(ClientEventHandler.renderTick);
            float f3 = (f2 > 0.5F ? 1 - f2 : f2) * 2;
            float f4 = 1 - f;

            if (heldItem.getItem() == ModItems.doubleLightsaber)
            {
                GL11.glRotatef(-10 * f1 * f, 1, 0, 0);
                GL11.glTranslatef(0.15F * f4, 0, 0);
                GL11.glRotatef(82 * f4, 0, 0, 1);
                GL11.glRotatef(-5 * f4, 1, 0, 0);
                
                if (f2 > 0)
                {
                    GL11.glRotatef(-360 * f2, 0, 0, 1);
                }
                
                LightsaberData[] array = ItemDoubleLightsaber.get(heldItem);
                
                if (array[0].getPart(PartType.EMITTER).hasCrossguard() || array[1].getPart(PartType.EMITTER).hasCrossguard())
                {
                    GL11.glRotatef(60 * f4, 0, 1, 0);
                }
            }
            else if (heldItem.getItem() == ModItems.lightsaber)
            {
                if (!isDualWielding(entity))
                {
                    if (!entity.isSneaking())
                    {
                        GL11.glRotatef(-30 * f4, 0, 1, 0);
                        GL11.glRotatef(-10 * f4, 0, 0, 1);
                        GL11.glTranslatef(0.05F * f4, 0.05F * f4, 0);
                        GL11.glRotatef(-140 * f3, 0, 0, 1);
                        GL11.glRotatef(-80 * f3, 1, 0, 0);

                        if (LightsaberData.getPart(heldItem, PartType.EMITTER).hasCrossguard())
                        {
                            GL11.glRotatef(90 * f4, 0, 1, 0);
                        }
                    }
                    
                    if (heldItem.hasDisplayName() && (heldItem.getDisplayName().equals("Dinnerbone") || heldItem.getDisplayName().equals("Grumm")))
                    {
                        GL11.glRotatef(-180 * f * (1 - f3), 0, 0, 1);
                    }
                }
            }
        }
    }

    private static boolean isDualWielding(Entity entity)
    {
        return Lightsabers.isBattlegearLoaded && entity instanceof EntityPlayer && BattlegearUtils.isPlayerInBattlemode((EntityPlayer) entity);
    }
}
