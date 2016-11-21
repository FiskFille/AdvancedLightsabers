package fiskfille.lightsabers.common.helper;

import java.util.Map;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.data.StatusEffect;
import fiskfille.lightsabers.common.event.ClientEventHandler;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;
import fiskfille.lightsabers.common.lightsaber.LightsaberManager;

public class ModelHelper
{
	public static Map<Class<? extends ModelBiped>, Float> armOffsets = Maps.newHashMap();
	
	public static void renderBipedPre(ModelBiped model, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		if (!armOffsets.containsKey(model.getClass()))
		{
			armOffsets.put(model.getClass(), model.bipedRightArm.rotationPointY);
		}
		
		model.bipedRightArm.rotationPointY = armOffsets.get(model.getClass());
		model.bipedLeftArm.rotationPointY = armOffsets.get(model.getClass());
		
		if (Lightsabers.isBattlegearLoaded)
        {
            mods.battlegear2.client.utils.BattlegearRenderHelper.moveOffHandArm(entity, model, f5);
        }
		
		setRotationAngles(model, f, f1, f2, f3, f4, f5, entity);
	}

	public static void renderBipedPost(ModelBiped model, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{

	}

	public static void setRotationAngles(ModelBiped model, float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityliving = (EntityLivingBase)entity;
			ItemStack itemstack = entityliving.getHeldItem();
			ItemLightsaberBase.refreshNBT(itemstack);

			if (itemstack != null)
			{
				float f6 = model.onGround;
				float f7 = (f6 > 0.5F ? 1 - f6 : f6) * 2;

				if (itemstack.getItem() instanceof ItemLightsaberBase && !entityliving.isSneaking())
				{
					if (itemstack.getItem() == ModItems.doubleLightsaber)
					{
						if (model.heldItemRight != 0)
						{
							float f8 = 1 - f1;
							model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * f1 - (((float)Math.PI / 2.5F) * (float)model.heldItemRight) * f8;
							model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * f1 + (model.bipedRightArm.rotateAngleY * 0.5F - ((float)Math.PI / 15F) * (float)model.heldItemRight) * f8;
						}

						if (itemstack.getTagCompound().getBoolean("active"))
						{
							model.bipedRightArm.rotateAngleX -= f7;
							model.bipedRightArm.rotateAngleY += f7;
						}
					}
					else if (itemstack.getItem() == ModItems.lightsaber)
					{
						if (model.heldItemRight != 0 && !isDualWielding(entityliving))
						{
							float f8 = 1 - f1;
							model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * f1 - (1 * (float)model.heldItemRight) * f8;
							model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * f1 + (model.bipedRightArm.rotateAngleY * 0.5F - 0.5F * (float)model.heldItemRight) * f8;
							model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * f1 + (model.bipedRightArm.rotateAngleZ * 0.5F - 0.2F * (float)model.heldItemRight) * f8;
							model.bipedRightArm.rotationPointX += 0.5F * f8;

							float f9 = f1 > 0.5F ? 1 : f1 * 2;
							float f10 = 1 - f9;
							model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX * f9 - (1 * (float)model.heldItemRight) * f10;
							model.bipedLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY * f9 + (model.bipedLeftArm.rotateAngleY * 0.5F + 0.75F * (float)model.heldItemRight) * f10;
							model.bipedLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ * f9 + (model.bipedLeftArm.rotateAngleZ * 0.5F + 0.0F * (float)model.heldItemRight) * f10;
							model.bipedLeftArm.rotationPointX -= 0.5F * f8;
						}

						if (!isDualWielding(entityliving))
						{
							if (itemstack.getTagCompound().getBoolean("active"))
							{
								model.bipedRightArm.rotateAngleX -= f7;
								model.bipedRightArm.rotateAngleY -= f7 * 0.4F;
								model.bipedLeftArm.rotateAngleX -= f7 * 1.6F;
								model.bipedLeftArm.rotateAngleY -= f7 * 0.9F;
							}
						}
					}
				}
			}
			
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)entity;
				float f6 = ALRenderHelper.median(ALData.getFloat(player, ALData.FORCE_PUSHING_TIMER), ALData.getFloat(player, ALData.PREV_FORCE_PUSHING_TIMER));
				float f7 = MathHelper.clamp_float(MathHelper.sin(f6 * 3) * 1.5F, 0, 1);
				float f8 = ALRenderHelper.median(ALData.getFloat(player, ALData.DRAIN_LIFE_TIMER), ALData.getFloat(player, ALData.PREV_DRAIN_LIFE_TIMER));
				float f9 = MathHelper.clamp_float(MathHelper.sin(f8 * 3) * 4F, 0, 1);
				float f10 = ALRenderHelper.median(ALData.getFloat(player, ALData.RIGHT_ARM_TIMER), ALData.getFloat(player, ALData.PREV_RIGHT_ARM_TIMER));
				float f11 = ALRenderHelper.median(ALData.getFloat(player, ALData.LEFT_ARM_TIMER), ALData.getFloat(player, ALData.PREV_LEFT_ARM_TIMER));
				
				model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * (1 - f7) + (f4 / (180F / (float)Math.PI) - (float)Math.PI / 2) * f7;
				model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * (1 - f7) + (f3 / (180F / (float)Math.PI)) * f7;
				model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * (1 - f7) + 0 * f7;
				
				model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * (1 - f9) + (f4 / (180F / (float)Math.PI) - (float)Math.PI / 2) * f9;
				model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * (1 - f9) + (f3 / (180F / (float)Math.PI)) * f9;
				model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * (1 - f9) + 0 * f9;
				
				model.bipedRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX * (1 - f10) + (f4 / (180F / (float)Math.PI) - (float)Math.PI / 2) * f10;
				model.bipedRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY * (1 - f10) + (f3 / (180F / (float)Math.PI)) * f10;
				model.bipedRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ * (1 - f10) + 0 * f10;
				model.bipedLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX * (1 - f11) + (f4 / (180F / (float)Math.PI) - (float)Math.PI / 2) * f11;
				model.bipedLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY * (1 - f11) + (f3 / (180F / (float)Math.PI)) * f11;
				model.bipedLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ * (1 - f11) + 0 * f11;
			}
			
//			int amplifier = 2;
//			int durationMax = 60;
//			float duration = (float)(entity.ticksExisted + ClientEventHandler.RENDER_TICK) / 2 % durationMax;
//			float fade = 3;
			
			
			
			StatusEffect effectChoke = DataManager.getEffect(entityliving, Effect.choke.id);
			
			if (effectChoke != null)
			{
				int durationMax = 60;
				float duration = effectChoke.duration - ClientEventHandler.RENDER_TICK;
				float fade = 3;
				
				float f6 = duration > fade ? (duration < durationMax - fade ? 1 : Math.min(durationMax - duration, fade) / fade) : Math.min(duration, fade) / fade;
				float f7 = 1 - f6;
				float f8 = (float)(entity.ticksExisted + ClientEventHandler.RENDER_TICK);
				
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
					model.bipedLeftLeg.rotateAngleX = MathHelper.cos(f8 * 0.6662F + (float)Math.PI) * 1.1F * f6;
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
					model.bipedLeftLeg.rotateAngleX = MathHelper.cos(f8 * 0.8662F + (float)Math.PI) * 1.1F * f6;
				}
			}
		}
	}

	public static void applyLightsaberItemRotation(Entity entity, ItemStack itemstack)
	{
		ItemLightsaberBase.refreshNBT(itemstack);

		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityliving = (EntityLivingBase)entity;
			float f = entityliving.prevLimbSwingAmount - (entityliving.prevLimbSwingAmount - entityliving.limbSwingAmount) * ClientEventHandler.RENDER_TICK;
			float f1 = 1 - f;
			float f2 = MathHelper.cos((entityliving.limbSwing - entityliving.limbSwingAmount * (1.0F - ClientEventHandler.RENDER_TICK)) * 0.6662F) * 1.4F * f;
			float f3 = entityliving.getSwingProgress(ClientEventHandler.RENDER_TICK);
			float f4 = (f3 > 0.5F ? 1 - f3 : f3) * 2;

			if (itemstack.getItem() == ModItems.doubleLightsaber)
			{
				GL11.glRotatef(-10 * f2 * f, 1, 0, 0);
				GL11.glTranslatef(0.15F * f1, 0, 0);
				GL11.glRotatef(82 * f1, 0, 0, 1);
				GL11.glRotatef(-5 * f1, 1, 0, 0);

				if (itemstack.getTagCompound().getBoolean("active"))
				{
					GL11.glRotatef(-360 * f3, 0, 0, 1);
				}

				ItemStack upper = LightsaberHelper.getDoubleLightsaberUpper(itemstack);
				ItemStack lower = LightsaberHelper.getDoubleLightsaberLower(itemstack);

				if (LightsaberHelper.getPart(upper, EnumPartType.EMITTER) == LightsaberManager.lightsaberKnighted || LightsaberHelper.getPart(lower, EnumPartType.EMITTER) == LightsaberManager.lightsaberKnighted)
				{
					GL11.glRotatef(60 * f1, 0, 1, 0);
				}
			}
			else if (itemstack.getItem() == ModItems.lightsaber)
			{
				if (!isDualWielding(entityliving) && !entity.isSneaking())
				{
					GL11.glRotatef(-30 * f1, 0, 1, 0);
					GL11.glRotatef(-10 * f1, 0, 0, 1);
					GL11.glTranslatef(0.05F * f1, 0.05F * f1, 0);

					if (itemstack.getTagCompound().getBoolean("active"))
					{
						GL11.glRotatef(-140 * f4, 0, 0, 1);
						GL11.glRotatef(-80 * f4, 1, 0, 0);
					}

					if (LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER) == LightsaberManager.lightsaberKnighted)
					{
						GL11.glRotatef(90 * f1, 0, 1, 0);
					}
				}
			}
		}
	}

	private static boolean isDualWielding(Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;

			if (Lightsabers.isBattlegearLoaded && mods.battlegear2.api.core.BattlegearUtils.isPlayerInBattlemode(player))
			{
				return true;
			}
		}

		return false;
	}
}
