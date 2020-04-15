package com.fiskmods.lightsabers.helper;

import java.awt.Color;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.ModelLightsaberBlade;
import com.fiskmods.lightsabers.client.render.Lightning;
import com.fiskmods.lightsabers.client.render.hilt.HiltRenderer;
import com.fiskmods.lightsabers.common.config.ModConfig;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.event.ClientEventHandler;
import com.fiskmods.lightsabers.common.hilt.Hilt.Part;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.Constants.NBT;

public class ALRenderHelper
{
    private static Minecraft mc = Minecraft.getMinecraft();

    public static final ResourceLocation SHADER_GRAY = new ResourceLocation("shaders/post/desaturate.json");
    public static final ResourceLocation SHADER_BLUE = new ResourceLocation(Lightsabers.MODID, "shaders/post/blue.json");
    public static final ResourceLocation SHADER_BLUR = new ResourceLocation(Lightsabers.MODID, "shaders/post/mild_phosphor.json");

    private static final ModelLightsaberBlade LIGHTSABER_BLADE = new ModelLightsaberBlade(38);
    private static final ModelLightsaberBlade CROSSGUARD_BLADE = new ModelLightsaberBlade(4);
    
    public static boolean overrideColor = false;
    private static float lastBrightnessX;
    private static float lastBrightnessY;
    
    public static final int LIGHTING_LUMINOUS = 0xF0F0;

    public static void setLighting(int lighting)
    {
        storeLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (lighting % 65536) / 255, (lighting / 65536) / 255);
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

    public static void setGlColor(Color color, float alpha)
    {
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, alpha);
    }

    public static void setGlColor(Color color)
    {
        setGlColor(color, (float) color.getAlpha() / 255);
    }

    public static void setVecColor(Vec3 vec)
    {
        GL11.glColor4f((float) vec.xCoord, (float) vec.yCoord, (float) vec.zCoord, 1);
    }

    public static void applyColorFromItemStack(ItemStack itemstack, int pass)
    {
        int color = itemstack.getItem().getColorFromItemStack(itemstack, pass);
        float r = (color >> 16 & 255) / 255F;
        float g = (color >> 8 & 255) / 255F;
        float b = (color & 255) / 255F;
        GL11.glColor4f(r, g, b, 1);
    }

    public static Vec3 getColorFromHex(int hex)
    {
        float r = (hex >> 16 & 255) / 255F;
        float g = (hex >> 8 & 255) / 255F;
        float b = (hex & 255) / 255F;
        return Vec3.createVectorHelper(r, g, b);
    }

    public static int getHex(Vec3 color)
    {
        int r = (int) Math.round(color.xCoord * 255);
        int g = (int) Math.round(color.yCoord * 255);
        int b = (int) Math.round(color.zCoord * 255);
        return r << 16 | g << 8 | b;
    }

    public static void setAlpha(float alpha)
    {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        floatBuffer.rewind();
        GL11.glGetFloat(GL11.GL_CURRENT_COLOR, floatBuffer);
        GL11.glColor4f(floatBuffer.get(), floatBuffer.get(), floatBuffer.get(), alpha);
    }

    public static float getAlpha()
    {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        floatBuffer.rewind();
        GL11.glGetFloat(GL11.GL_CURRENT_COLOR, floatBuffer);

        return floatBuffer.get(3);
    }

    public static void overrideColor(boolean override)
    {
        overrideColor = override;
    }
    
    public static void renderLightsaber(LightsaberData data, ItemStack itemstack, boolean inWorld)
    {
        renderLightsaberHilt(data);
        GL11.glScalef(3, 3, 3);
        GL11.glTranslatef(0, -(data.getPart(PartType.SWITCH_SECTION).height + data.getPart(PartType.EMITTER).height) * 0.0234375F, 0);
        renderLightsaberBlade(data, itemstack, inWorld);
    }
    
    public static void renderLightsaber(LightsaberData[] array, ItemStack itemstack, boolean inWorld)
    {
        for (int i = 0; i < array.length; ++i)
        {
            GL11.glPushMatrix();
            
            if (i == 1)
            {
                GL11.glRotatef(180, 1, 0, 0);
                GL11.glRotatef(180, 0, 1, 0);
            }
            
            GL11.glTranslatef(0, -array[i].getHeight() / 32, 0);
            ALRenderHelper.renderLightsaber(array[i], itemstack, inWorld);
            GL11.glPopMatrix();
        }
    }
    
    public static void renderLightsaber(ItemStack itemstack, boolean inWorld)
    {
        if (itemstack.getItem() == ModItems.doubleLightsaber)
        {
            renderLightsaber(ItemDoubleLightsaber.get(itemstack), itemstack, inWorld);
        }
        else
        {
            renderLightsaber(LightsaberData.get(itemstack), itemstack, inWorld);
        }
    }

    public static void renderLightsaberHilt(LightsaberData data)
    {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glTranslatef(0, -(data.getPart(PartType.BODY).height + data.getPart(PartType.POMMEL).height - data.getHeight() / 2) / 16, 0);
    
        for (PartType type : PartType.values())
        {
            HiltRenderer renderer = HiltRenderer.get(data.get(type));
            ModelBase model = renderer.getModel(type);
            
            mc.getTextureManager().bindTexture(renderer.getTexture(type));
            
            switch (type)
            {
            case EMITTER:
                GL11.glPushMatrix();
                GL11.glTranslatef(0, -data.getPart(PartType.SWITCH_SECTION).height / 16, 0);
                model.render(null, 0, 0, 0, 0, 0, 0.0625F);
                GL11.glPopMatrix();
                break;
            case POMMEL:
                GL11.glPushMatrix();
                Part body = data.getPart(PartType.BODY);
                float[] insn = body.glInstructions;

                if (insn != null && insn.length > 0)
                {
                    for (int i = 0; i < insn.length; ++i)
                    {
                        float f = insn[i];

                        if ((i & 1) == 0)
                        {
                            GL11.glTranslatef(0, f / 16, 0); // TODO: Move to HiltRenderer
                        }
                        else
                        {
                            GL11.glRotatef(f, 1, 0, 0);
                        }
                    }
                }
                else
                {
                    GL11.glTranslatef(0, body.height / 16, 0);
                }

                model.render(null, 0, 0, 0, 0, 0, 0.0625F);
                GL11.glPopMatrix();
                break;
            default:
                model.render(null, 0, 0, 0, 0, 0, 0.0625F);
                break;
            }
        }
    }
    
    public static void renderLightsaberHilt(LightsaberData[] array)
    {
        for (int i = 0; i < array.length; ++i)
        {
            GL11.glPushMatrix();
            
            if (i == 1)
            {
                GL11.glRotatef(180, 1, 0, 0);
                GL11.glRotatef(180, 0, 1, 0);
            }
            
            GL11.glTranslatef(0, -array[i].getHeight() / 32, 0);
            ALRenderHelper.renderLightsaberHilt(array[i]);
            GL11.glPopMatrix();
        }
    }

    public static void renderLightsaberBlade(LightsaberData data, ItemStack itemstack, boolean inWorld)
    {
        if (ItemLightsaberBase.isActive(itemstack))
        {
            if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(ALConstants.TAG_LIGHTSABER_SPECIAL, NBT.TAG_STRING))
            {
                Item item = (Item) Item.itemRegistry.getObject(itemstack.getTagCompound().getString(ALConstants.TAG_LIGHTSABER_SPECIAL));
                
                if (item != null)
                {
                    IIcon icon = item.getIconFromDamage(0);
                    Tessellator tessellator = Tessellator.instance;
                    float f = mc.thePlayer.ticksExisted + ClientEventHandler.renderTick;
                    
                    if (icon == null)
                    {
                        icon = ((TextureMap) mc.getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite("missingno");
                    }

                    mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
                    TextureUtil.func_152777_a(false, false, 1.0F);
                    GL11.glPushMatrix();
                    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                    GL11.glScaled(-2, -2 + MathHelper.cos(f) * 0.1F, 2 + MathHelper.sin(f) * 0.15F);
                    GL11.glRotated(-(1 - 2 * MathHelper.cos(f / 2 + 1)), 0, 0, 1);
                    GL11.glRotated((1 - 2 * MathHelper.sin(f / 2 + 3)), 1, 0, 0);
                    GL11.glRotatef(-90, 0, 1, 0);
                    GL11.glTranslatef(0.705F, 0.4F, 0.0625F / 2);
                    GL11.glRotatef(135, 0, 0, 1);
                    GL11.glColor4f(1, 1, 1, 1);
                    ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
                    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                    GL11.glPopMatrix();
                    TextureUtil.func_147945_b();
                    return;
                }
            }
            
            Part emitter = data.getPart(PartType.EMITTER);
            boolean prevCull = GL11.glGetBoolean(GL11.GL_CULL_FACE);
            float[] crossguard = emitter.crossguard;
            float[] rgb = data.getRGB(itemstack);
            
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            ALRenderHelper.setLighting(MathHelper.ceiling_float_int(LIGHTING_LUMINOUS * ModConfig.renderLightingMultiplier));
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
            GL11.glTranslatef(0, 0.095F, 0);

            GL11.glAlphaFunc(GL11.GL_GREATER, 0.99F);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_CULL_FACE);

            if (emitter.hasCrossguard()) // TODO: Move to HiltRenderer
            {
                for (int i = -1; i <= 1; i += 2)
                {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(crossguard[0], crossguard[1], crossguard[2] * -i);
                    GL11.glRotatef(i * 90, 1, 0, 0);
                    
                    if (i == 1)
                    {
                        GL11.glRotatef(180, 0, 1, 0);
                    }
                    
                    CROSSGUARD_BLADE.renderCrossguardOuter(data, itemstack, rgb, inWorld);
                    GL11.glPopMatrix();
                }
            }

            LIGHTSABER_BLADE.renderOuter(data, itemstack, rgb, inWorld);
            GL11.glDisable(GL11.GL_BLEND);
//			GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDepthMask(true);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

            GL11.glPushMatrix();

            if (emitter.hasCrossguard())
            {
                for (int i = -1; i <= 1; i += 2)
                {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(crossguard[0], crossguard[1], crossguard[2] * -i);
                    GL11.glRotatef(i * 90, 1, 0, 0);
                    
                    if (i == 1)
                    {
                        GL11.glRotatef(180, 0, 1, 0);
                    }
                    
                    CROSSGUARD_BLADE.renderInner(data, itemstack, rgb, inWorld, true);
                    GL11.glPopMatrix();
                }
            }

            LIGHTSABER_BLADE.renderInner(data, itemstack, rgb, inWorld, false);
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

        double scaleW = mc.displayWidth / reso.getScaledWidth_double();
        double scaleH = mc.displayHeight / reso.getScaledHeight_double();

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
        GL11.glScissor((int) Math.floor(x * scaleW), (int) Math.floor(mc.displayHeight - ((y + height) * scaleH)), (int) Math.floor((x + width) * scaleW) - (int) Math.floor(x * scaleW), (int) Math.floor(mc.displayHeight - (y * scaleH)) - (int) Math.floor(mc.displayHeight - ((y + height) * scaleH)));
    }

    public static void endGlScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static float median(double curr, double prev)
    {
        return (float) (prev + (curr - prev) * ClientEventHandler.renderTick);
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
        StatusEffect status = StatusEffect.get(player, Effect.GAZE);

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
            tessellator.setColorRGBA_F((float) color.xCoord, (float) color.yCoord, (float) color.zCoord, alpha);
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

    public static void setupRenderItemIntoGUI()
    {
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        RenderHelper.enableGUIStandardItemLighting();
    }

    public static void finishRenderItemIntoGUI()
    {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void faceVec(Vec3 src, Vec3 dst)
    {
        double d0 = dst.xCoord - src.xCoord;
        double d1 = dst.yCoord - src.yCoord;
        double d2 = dst.zCoord - src.zCoord;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float yaw = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
        GL11.glRotated(-yaw, 0, 1, 0);
        GL11.glRotated(pitch, 1, 0, 0);
    }
}
