package fiskfille.lightsabers.client.render.item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.common.event.ClientEventHandler;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.helper.ModelHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class RenderDoubleLightsaber implements IItemRenderer
{
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		return true;
	}

	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) 
	{
		return type == type.ENTITY;
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		GL11.glPushMatrix();
		ItemStack upper = LightsaberHelper.getDoubleLightsaberUpper(item);
		ItemStack lower = LightsaberHelper.getDoubleLightsaberLower(item);
		float height1 = LightsaberHelper.getPart(upper, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(upper, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(upper, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(upper, EnumPartType.POMMEL).getPommel().height;
		float height2 = LightsaberHelper.getPart(lower, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(lower, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(lower, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(lower, EnumPartType.POMMEL).getPommel().height;
		float height3 = height1 + height2;
		
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(-100, 0, 1, 0);
			GL11.glRotatef(-150, 1, 0, 0);
			GL11.glRotatef(95, 0, 0, 1);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glTranslatef(-0.1F, -0.2F, 1.1F);
			
			if (data.length > 1 && data[1] instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)data[1];
				float f = player.prevLimbSwingAmount - (player.prevLimbSwingAmount - player.limbSwingAmount) * ClientEventHandler.RENDER_TICK;
				float f1 = MathHelper.cos((player.limbSwing - player.limbSwingAmount * (1.0F - ClientEventHandler.RENDER_TICK)) * 0.6662F) * 1.4F * f;
				float f2 = player.getSwingProgress(ClientEventHandler.RENDER_TICK);
				float f3 = (f2 > 0.5F ? 1 - f2 : f2) * 2;
				GL11.glRotatef(90 * f, 0, 0, 1);
				GL11.glTranslatef(0.2F * f3 + 0.8F * f, 0.5F * f3, 0.4F * f);
				GL11.glRotatef(30 * f3, 1, 0, 0);
				GL11.glRotatef(360 * f2, 0, 0, 1);
			}

			float scale;

			for (int i = 0; i < 2; ++i)
			{
				ItemStack itemstack = new ItemStack[] {upper, lower}[i];
				float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
				float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

				GL11.glPushMatrix();
				GL11.glRotatef(180 * i, 0, 0, 1);
				scale = 0.5F * 0.4F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, f, 0);
				ALRenderHelper.renderLightsaberHilt(itemstack);
				GL11.glPopMatrix();
			}

			for (int i = 0; i < 2; ++i)
			{
				ItemStack itemstack = new ItemStack[] {upper, lower}[i];
				float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
				float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

				GL11.glPushMatrix();
				GL11.glRotatef(180 * i, 0, 0, 1);
				scale = 0.5F * 0.4F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, f, 0);
				GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
				scale = 1.5F * 0.4F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, -(LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
				ALRenderHelper.renderLightsaberBlade(itemstack, true);
				GL11.glPopMatrix();
			}
			
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.EQUIPPED)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(-90, 0, 1, 0);
			GL11.glRotatef(-150, 1, 0, 0);
			GL11.glTranslatef(-0.15F + height3 * 0.0015F, 0.14F, 0.75F);
			
			if (data[1] instanceof Entity)
			{
				ModelHelper.applyLightsaberItemRotation((Entity)data[1], item);
			}

			float scale;

			for (int i = 0; i < 2; ++i)
			{
				ItemStack itemstack = new ItemStack[] {upper, lower}[i];
				float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
				float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

				GL11.glPushMatrix();
				GL11.glRotatef(180 * i, 0, 0, 1);
				scale = 0.5F * 0.35F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, f, 0);
				ALRenderHelper.renderLightsaberHilt(itemstack);
				GL11.glPopMatrix();
			}

			for (int i = 0; i < 2; ++i)
			{
				ItemStack itemstack = new ItemStack[] {upper, lower}[i];
				float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
				float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

				GL11.glPushMatrix();
				GL11.glRotatef(180 * i, 0, 0, 1);
				scale = 0.5F * 0.35F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, f, 0);
				GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
				scale = 1.5F * 0.35F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, -(LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
				ALRenderHelper.renderLightsaberBlade(itemstack, true);
				GL11.glPopMatrix();
			}

			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.ENTITY)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(180, 1, 0, 0);
			
			float scale;

			for (int i = 0; i < 2; ++i)
			{
				ItemStack itemstack = new ItemStack[] {upper, lower}[i];
				float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
				float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

				GL11.glPushMatrix();
				GL11.glRotatef(180 * i, 0, 0, 1);
				scale = 0.5F * 0.55F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, f, 0);
				ALRenderHelper.renderLightsaberHilt(itemstack);
				GL11.glPopMatrix();
			}
			
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.INVENTORY)
		{
			GL11.glPushMatrix();
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			float size = 4;
			float[] afloat = LightsaberColors.getRGB(LightsaberColors.getColors()[LightsaberHelper.getColorId(lower)]);
			GL11.glColor4f(afloat[0], afloat[1], afloat[2], 1);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertex(size / 2, size / 2, 0);
			tessellator.addVertex(size, 0F, 0);
			tessellator.addVertex(0F, 0F, 0);
			tessellator.addVertex(0F, size, 0);
			tessellator.draw();
			afloat = LightsaberColors.getRGB(LightsaberColors.getColors()[LightsaberHelper.getColorId(upper)]);
			GL11.glColor4f(afloat[0], afloat[1], afloat[2], 1);
			tessellator.startDrawingQuads();
			tessellator.addVertex(size / 2, size / 2, 0);
			tessellator.addVertex(size, 0F, 0);
			tessellator.addVertex(0F, 0F, 0);
			tessellator.addVertex(size / 2, size / 2, 0);
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glColor4f(1, 1, 1, 1);
			GL11.glPopMatrix();

			GL11.glTranslatef(-2, 3, 0);
			GL11.glScalef(10F, 10F, 10F);
			GL11.glTranslatef(1.0F, 0.5F, 1.0F);
			GL11.glScalef(1.0F, 1.0F, -1F);
			GL11.glRotatef(210F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-20, 0, 0, 1);
			GL11.glRotatef(-40, 1, 0, 0);
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glRotatef(-110, 0, 1, 0);
			GL11.glTranslatef(0.0F, 0.05F, 0.0F);

			float scale;

			for (int i = 0; i < 2; ++i)
			{
				ItemStack itemstack = new ItemStack[] {upper, lower}[i];
				float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
				float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

				GL11.glPushMatrix();
				GL11.glRotatef(180 * i, 0, 0, 1);
				scale = 0.5F * 0.6F;
				GL11.glScalef(scale, scale, scale);
				GL11.glTranslatef(0, f, 0);
				ALRenderHelper.renderLightsaberHilt(itemstack);
				GL11.glPopMatrix();
			}
			
			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
	}
}
