package fiskfille.lightsabers.common.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.LightsaberAPIClient;
import fiskfille.lightsabers.client.model.ModelLightsaberBlade;
import fiskfille.lightsabers.common.config.ModConfig;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.Part;
import fiskfille.lightsabers.common.lightsaber.LightsaberManager;

public class ALRenderHelper
{
	private static ModelLightsaberBlade modelLightsaberBlade = new ModelLightsaberBlade(34);
	private static ModelLightsaberBlade modelCrossguardBlade = new ModelLightsaberBlade(4);

	public static void setLighting(int c0)
	{
		int j = c0 % 65536;
		int k = c0 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
	}

	public static void renderLightsaberHilt(ItemStack itemstack)
	{		
		for (EnumPartType type : EnumPartType.values())
		{
			Lightsaber lightsaber = LightsaberHelper.getPart(itemstack, type);
			ModelBase model = LightsaberAPIClient.getModelFor(lightsaber, type);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Lightsabers.modid, "textures/models/lightsaber/" + type.name().toLowerCase() + "_" + lightsaber.getName().toLowerCase().replace(' ', '_').replace("(", "").replace(")", "") + ".png"));

			if (type == EnumPartType.EMITTER)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0, -LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height * 0.0625F, 0);
				model.render(null, 0, 0, 0, 0, 0, 0.0625F);
				GL11.glPopMatrix();
			}
			else if (type == EnumPartType.POMMEL)
			{
				GL11.glPushMatrix();
				Part body = LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody();
				float[] afloat = body.glInstructions;
				
				if (afloat != null && afloat.length > 0)
				{
					for (int i = 0; i < afloat.length; ++i)
					{
						float f = afloat[i];

						if (i % 2 == 0)
						{
							GL11.glTranslatef(0, f * 0.0625F, 0);
						}
						else
						{
							GL11.glRotatef(f, 1, 0, 0);
						}
					}
				}
				else
				{
					GL11.glTranslatef(0, body.height * 0.0625F, 0);
				}
				
				model.render(null, 0, 0, 0, 0, 0, 0.0625F);
				GL11.glPopMatrix();
			}
			else
			{
				model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			}
		}
	}

	public static void renderLightsaberBlade(ItemStack itemstack, boolean flag)
	{
		if (ItemLightsaberBase.isActive(itemstack))
		{
			float lastBrightnessX = OpenGlHelper.lastBrightnessX;
			float lastBrightnessY = OpenGlHelper.lastBrightnessY;

			float[] afloat = LightsaberColors.getRGB(LightsaberColors.getColors()[LightsaberHelper.getColorId(itemstack)]);
			Lightsaber emitter = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER);

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			ALRenderHelper.setLighting((int)(61680 * ModConfig.renderLightingMultiplier));
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
//			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);

			GL11.glAlphaFunc(GL11.GL_GREATER, 0.99F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_CULL_FACE);

			if (emitter == LightsaberManager.lightsaberKnighted)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0, 0.083F, -0.23F);
				GL11.glRotatef(90, 1, 0, 0);
				GL11.glRotatef(180, 0, 1, 0);
				modelCrossguardBlade.renderCrossguardOuter(itemstack, afloat[0], afloat[1], afloat[2], flag);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef(0, 0.083F, 0.23F);
				GL11.glRotatef(-90, 1, 0, 0);
				modelCrossguardBlade.renderCrossguardOuter(itemstack, afloat[0], afloat[1], afloat[2], flag);
				GL11.glPopMatrix();
			}

			modelLightsaberBlade.renderOuter(itemstack, afloat[0], afloat[1], afloat[2], flag);
			GL11.glDisable(GL11.GL_BLEND);
//			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDepthMask(true);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

			GL11.glPushMatrix();

			if (emitter == LightsaberManager.lightsaberKnighted)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0, 0.083F, -0.23F);
				GL11.glRotatef(90, 1, 0, 0);
				GL11.glRotatef(180, 0, 1, 0);
				modelCrossguardBlade.renderCrossguardInner(itemstack);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef(0, 0.083F, 0.23F);
				GL11.glRotatef(-90, 1, 0, 0);
				modelCrossguardBlade.renderCrossguardInner(itemstack);
				GL11.glPopMatrix();
			}

			modelLightsaberBlade.renderInner(itemstack);
			GL11.glPopMatrix();

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
			GL11.glPopMatrix();
		}
	}

	public static void drawTip(float size, float tip)
	{
		float f = 0.0625F;
		float f1 = f / 2;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(size, size, 0);
		tessellator.addVertex(-size, size, 0);
		tessellator.addVertex(-size + f1, -size - tip, -f1);
		tessellator.addVertex(size - f1, -size - tip, -f1);
		tessellator.addVertex(size, size, -f);
		tessellator.addVertex(-size, size, -f);
		tessellator.addVertex(-size + f1, -size - tip, -f + f1);
		tessellator.addVertex(size - f1, -size - tip, -f + f1);
		tessellator.addVertex(-f1, size, size - f1);
		tessellator.addVertex(-f1, size, -size - f1);
		tessellator.addVertex(0, -size - tip, -size);
		tessellator.addVertex(0, -size - tip, size - f);
		tessellator.addVertex(f1, size, size - f1);
		tessellator.addVertex(f1, size, -size - f1);
		tessellator.addVertex(0, -size - tip, -size);
		tessellator.addVertex(0, -size - tip, size - f);
		tessellator.draw();
	}

	public static void startGlScissor(int x, int y, int width, int height)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution reso = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		double scaleW = (double) mc.displayWidth / reso.getScaledWidth_double();
		double scaleH = (double) mc.displayHeight / reso.getScaledHeight_double();

		if (width <= 0 || height <= 0)
		{
			return;
		}
		if (x < 0)
		{
			x = 0;
		}
		if (y < 0)
		{
			y = 0;
		}

		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor((int) Math.floor((double) x * scaleW), (int) Math.floor((double) mc.displayHeight - ((double) (y + height) * scaleH)), (int) Math.floor((double) (x + width) * scaleW) - (int) Math.floor((double) x * scaleW), (int) Math.floor((double) mc.displayHeight - ((double) y * scaleH)) - (int) Math.floor((double) mc.displayHeight - ((double) (y + height) * scaleH)));
	}

	public static void endGlScissor()
	{
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}
