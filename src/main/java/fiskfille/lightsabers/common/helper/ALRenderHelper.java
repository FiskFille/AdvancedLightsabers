package fiskfille.lightsabers.common.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.LightsaberAPIClient;
import fiskfille.lightsabers.client.model.ModelLightsaberBlade;
import fiskfille.lightsabers.client.render.Lightning;
import fiskfille.lightsabers.common.config.ModConfig;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.data.StatusEffect;
import fiskfille.lightsabers.common.event.ClientEventHandler;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.Part;
import fiskfille.lightsabers.common.lightsaber.LightsaberManager;

public class ALRenderHelper
{
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static ResourceLocation shaderDesaturate = new ResourceLocation("shaders/post/desaturate.json");
	public static ResourceLocation shaderBlueTint = new ResourceLocation(Lightsabers.modid, "shaders/post/blue.json");
	public static ResourceLocation shaderMildPhosphor = new ResourceLocation(Lightsabers.modid, "shaders/post/mild_phosphor.json");
	
	private static ModelLightsaberBlade modelLightsaberBlade = new ModelLightsaberBlade(38);
	private static ModelLightsaberBlade modelCrossguardBlade = new ModelLightsaberBlade(4);
	private static float lastBrightnessX;
    private static float lastBrightnessY;
    public static boolean overrideColor = false;
    
    public static void setLighting(int lighting)
    {
        storeLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (lighting % 65536) / 255.0F, (lighting / 65536) / 255.0F);
    }

    public static void storeLighting()
    {
        lastBrightnessX = OpenGlHelper.lastBrightnessX;
        lastBrightnessY = OpenGlHelper.lastBrightnessY;
    }

    public static void resetLighting()
    {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    }
    
    public static void overrideColor(boolean override)
    {
    	overrideColor = override;
    }

	public static void renderLightsaberHilt(ItemStack itemstack)
	{
		for (EnumPartType type : EnumPartType.values())
		{
			Lightsaber lightsaber = LightsaberHelper.getPart(itemstack, type);
			ModelBase model = LightsaberAPIClient.getModelFor(lightsaber, type);
			mc.getTextureManager().bindTexture(new ResourceLocation(Lightsabers.modid, "textures/models/lightsaber/" + type.name().toLowerCase() + "_" + lightsaber.getName().toLowerCase().replace(' ', '_').replace("(", "").replace(")", "") + ".png"));

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
			boolean prevCull = GL11.glGetBoolean(GL11.GL_CULL_FACE);
			float[] color = getLightsaberColor(itemstack);
			Lightsaber emitter = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER);

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			ALRenderHelper.setLighting((int)(61680 * ModConfig.renderLightingMultiplier));
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
			GL11.glTranslatef(0, 0.095F, 0);

			GL11.glAlphaFunc(GL11.GL_GREATER, 0.99F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_CULL_FACE);

			if (emitter == LightsaberManager.lightsaberKnighted)
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(0, 0.083F, -0.23F);
				GL11.glRotatef(90, 1, 0, 0);
				GL11.glRotatef(180, 0, 1, 0);
				modelCrossguardBlade.renderCrossguardOuter(itemstack, color[0], color[1], color[2], flag);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef(0, 0.083F, 0.23F);
				GL11.glRotatef(-90, 1, 0, 0);
				modelCrossguardBlade.renderCrossguardOuter(itemstack, color[0], color[1], color[2], flag);
				GL11.glPopMatrix();
			}

			modelLightsaberBlade.renderOuter(itemstack, color[0], color[1], color[2], flag);
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
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			if (prevCull)
			{
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
			
			ALRenderHelper.resetLighting();
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
	
	public static float median(double curr, double prev)
    {
        return (float) (prev + (curr - prev) * ClientEventHandler.RENDER_TICK);
    }

	public static void startShaders(ResourceLocation resourcelocation)
	{
		if (OpenGlHelper.shadersSupported && (mc.entityRenderer.theShaderGroup == null || !mc.entityRenderer.theShaderGroup.getShaderGroupName().equals(resourcelocation.toString())))
        {
			if (mc.entityRenderer.theShaderGroup != null)
            {
                mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
			
            try
            {
                mc.entityRenderer.theShaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), resourcelocation);
                mc.entityRenderer.theShaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            }
            catch (Exception e)
            {
            	e.printStackTrace();
            }
        }
	}

	public static void stopShaders()
	{
		mc.entityRenderer.deactivateShader();
	}
	
	public static boolean canGazeEntity(EntityPlayer player, EntityLivingBase entity, int amplifier)
	{
		StatusEffect status = DataManager.getEffect(player, Effect.gaze.id);
		
		if (amplifier == 0)
		{
			return !entity.isInvisibleToPlayer(player) && !(entity instanceof EntityPlayer);
		}
		else if (amplifier == 1)
		{
			return !entity.isInvisibleToPlayer(player);
		}
		
		return true;
	}
	
	public static void renderLightning(Lightning lightning, float opacity)
    {
        if (lightning.rotateAngleZ != 0.0F)
        {
            GL11.glRotatef(lightning.rotateAngleZ, 0.0F, 0.0F, 1.0F);
        }

        if (lightning.rotateAngleY != 0.0F)
        {
            GL11.glRotatef(lightning.rotateAngleY, 0.0F, 1.0F, 0.0F);
        }

        if (lightning.rotateAngleX != 0.0F)
        {
            GL11.glRotatef(lightning.rotateAngleX, 1.0F, 0.0F, 0.0F);
        }

        drawLightningLine(Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(0, lightning.length, 0), 5, 1, lightning.lightningColor, opacity);
        GL11.glTranslatef(0, lightning.length, 0);

        for (Lightning lightning1 : lightning.children)
        {
            GL11.glPushMatrix();
            renderLightning(lightning1, opacity);
            GL11.glPopMatrix();
        }
    }
	
	public static void drawLightningLine(Vec3 start, Vec3 end, float lineWidth, float innerLineWidth, Vec3 color, float alpha)
	{
		if (start == null || end == null)
		{
			return;
		}
		
		Tessellator tessellator = Tessellator.instance;
		float prevWidth = GL11.glGetFloat(GL11.GL_LINE_WIDTH);
		
		if (lineWidth > 0.0F)
		{
			GL11.glLineWidth(lineWidth);
			tessellator.startDrawing(3);
			tessellator.setColorRGBA_F((float)color.xCoord, (float)color.yCoord, (float)color.zCoord, alpha);
			tessellator.addVertex(start.xCoord, start.yCoord, start.zCoord);
			tessellator.addVertex(end.xCoord, end.yCoord, end.zCoord);
			tessellator.draw();
		}
		
		if (innerLineWidth > 0.0F)
		{
			GL11.glLineWidth(innerLineWidth);
			tessellator.startDrawing(3);
			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, MathHelper.clamp_float(alpha - 0.2F, 0.0F, 1.0F));
			tessellator.addVertex(start.xCoord, start.yCoord, start.zCoord);
			tessellator.addVertex(end.xCoord, end.yCoord, end.zCoord);
			tessellator.draw();
		}
		
		GL11.glLineWidth(prevWidth);
	}
	
	public static void setupRenderLightning()
    {
//    	if (SHConfig.vibrantLightningColors)
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
        }
//        else
//        {
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        }
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, 1);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
		GL11.glDepthMask(false);
		ALRenderHelper.setLighting(61680);
    }
    
    public static void finishRenderLightning()
    {
    	ALRenderHelper.resetLighting();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		
//		if (SHConfig.vibrantLightningColors)
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }
    
    public static void faceVec(Vec3 src, Vec3 dst)
    {
        double d0 = dst.xCoord - src.xCoord;
        double d1 = dst.yCoord - src.yCoord;
        double d2 = dst.zCoord - src.zCoord;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float yaw = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
        GL11.glRotated(-yaw, 0, 1, 0);
		GL11.glRotated(pitch, 1, 0, 0);
    }
    
    public static float[] getLightsaberColor(ItemStack itemstack)
    {
    	EntityPlayer player = mc.thePlayer;
    	
    	if (itemstack.getDisplayName().equals("jeb_"))
    	{
//    		int k = player.ticksExisted / 25 + player.getEntityId();
//    		int l = k % EntitySheep.fleeceColorTable.length;
//    		int i1 = (k + 1) % EntitySheep.fleeceColorTable.length;
//    		float f1 = ((float)(player.ticksExisted % 25) + ClientEventHandler.RENDER_TICK) / 25.0F;
//    		return new float[] {EntitySheep.fleeceColorTable[l][0] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][0] * f1, EntitySheep.fleeceColorTable[l][1] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][1] * f1, EntitySheep.fleeceColorTable[l][2] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][2] * f1};
    		
    		int[] colors = LightsaberColors.getColors();
    		float[][] afloat = new float[colors.length][3];
    		
    		for (int i = 0; i < colors.length; ++i)
    		{
    			afloat[i] = LightsaberColors.getRGB(colors[i]);
    		}
    		
    		int time = 25;
    		int i = player.ticksExisted / time + player.getEntityId();
    		int j = i % afloat.length;
    		int k = (i + 1) % afloat.length;
    		float f = ((float)(player.ticksExisted % time) + ClientEventHandler.RENDER_TICK) / time;
    		return new float[] {afloat[j][0] * (1.0F - f) + afloat[k][0] * f, afloat[j][1] * (1.0F - f) + afloat[k][1] * f, afloat[j][2] * (1.0F - f) + afloat[k][2] * f};
    	}
    	
    	return LightsaberColors.getRGB(LightsaberColors.getColors()[LightsaberHelper.getColorId(itemstack)]);
    }
}
