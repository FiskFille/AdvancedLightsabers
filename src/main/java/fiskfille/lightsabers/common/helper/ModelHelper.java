package fiskfille.lightsabers.common.helper;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.event.ClientEventHandler;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;
import fiskfille.lightsabers.common.lightsaber.LightsaberManager;

public class ModelHelper
{
	public static ModelBiped modelBipedMain;
	
	public static void renderBipedPre(ModelBiped model, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		modelBipedMain = model;
		GL11.glPushMatrix();
		
		if (Lightsabers.isBattlegearLoaded)
		{
			mods.battlegear2.client.utils.BattlegearRenderHelper.moveOffHandArm(entity, model, f5);
		}
		
		setRotationAngles(model, f, f1, f2, f3, f4, f5, entity);
	}
	
	public static void renderBipedPost(ModelBiped model, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glPopMatrix();
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
				if (!isDualWielding(entityliving))
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
