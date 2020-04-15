package com.fiskmods.lightsabers.client.model;

import java.util.Random;
import java.util.function.Supplier;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.config.ModConfig;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

public class ModelLightsaberBlade extends ModelBase
{
    public ModelRenderer blade;
    public int bladeLength;

    public ModelLightsaberBlade(int length)
    {
        textureWidth = 64;
        textureHeight = 32;
        blade = new ModelRenderer(this, 0, 0);
        blade.addBox(-0.5F, -length, -0.5F, 1, length, 1);
        bladeLength = length;
    }

    public void renderInner(LightsaberData data, ItemStack stack, float[] rgb, boolean inWorld, boolean isCrossguard)
    {
        boolean fineCut = data.hasFocusingCrystal(FocusingCrystal.FINE_CUT);
        
        if (isCrossguard && fineCut)
        {
            GL11.glScalef(1, 1.2F, 1);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.INVERTING))
        {
            GL11.glColor4f(0, 0, 0, 1);
        }
        else if (data.hasFocusingCrystal(FocusingCrystal.PRISMATIC))
        {
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1);
        }
        else
        {
            GL11.glColor4f(1, 1, 1, 1);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.COMPRESSED))
        {
            GL11.glScalef(0.6F, 1, 0.6F);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.CRACKED))
        {
            float divider = 60;
            int ticks = Minecraft.getMinecraft().thePlayer.ticksExisted;
            Random rand = new Random(ticks % 100 * 1000);
            Random prev = new Random((ticks - 1) % 100 * 1000);

            Supplier<Float> nextFloat = () -> ALRenderHelper.median(rand.nextFloat(), prev.nextFloat());

            for (int i = 0; i < 4; ++i)
            {
                GL11.glPushMatrix();

                if (i != 0)
                {
                    GL11.glTranslatef((nextFloat.get() - 0.5F) / divider, 0, (nextFloat.get() - 0.5F) / divider);

                    for (int j = 0; j < bladeLength; ++j)
                    {
                        GL11.glPushMatrix();
                        GL11.glRotatef(nextFloat.get() * 360, 0, 1, 0);
                        GL11.glRotatef(90, 1, 0, 0);
                        GL11.glTranslatef(0, 0.05F - (1 - nextFloat.get() * 0.2F) / 16, (1 + nextFloat.get() * bladeLength) / 16);
                        ALRenderHelper.drawTip(0.04F, 0);
                        GL11.glPopMatrix();
                    }
                }

                if (!fineCut)
                {
                    blade.render(0.0625F);
                    GL11.glTranslatef(0, -(0.5F + bladeLength) / 16, 1F / 32);
                    ALRenderHelper.drawTip(0.03125F, 0.125F);
                }

                GL11.glPopMatrix();
            }
        }

        if (fineCut)
        {
            Tessellator tessellator = Tessellator.instance;
            float f = 0.0625F;
            float length = f * bladeLength * 0.7F;
            float edge = f * 1.5F;
            float edgeAngle = -f * 1.5F;
            float length1 = f * bladeLength * 0.3F;
            float edge1 = f / 2;
            float tip = f * 1.5F;

            tessellator.startDrawingQuads();
            tessellator.addVertex(-f / 2, -length, f / 2);
            tessellator.addVertex(0, -length, edge);
            tessellator.addVertex(0, edgeAngle, edge);
            tessellator.addVertex(-f / 2, -f, f / 2);
            tessellator.addVertex(f / 2, -length, f / 2);
            tessellator.addVertex(0, -length, edge);
            tessellator.addVertex(0, edgeAngle, edge);
            tessellator.addVertex(f / 2, -f, f / 2);
            tessellator.addVertex(f / 2, -f, f / 2);
            tessellator.addVertex(0, edgeAngle, edge);
            tessellator.addVertex(0, edgeAngle, edge);
            tessellator.addVertex(-f / 2, -f, f / 2);
            tessellator.addVertex(-f / 2, 0 - length, f / 2);
            tessellator.addVertex(-f / 2, -length1 - length, edge1);
            tessellator.addVertex(0, -length1 - length, edge1);
            tessellator.addVertex(0, 0 - length, edge);
            tessellator.addVertex(f / 2, 0 - length, f / 2);
            tessellator.addVertex(f / 2, -length1 - length, edge1);
            tessellator.addVertex(0, -length1 - length, edge1);
            tessellator.addVertex(0, 0 - length, edge);
            tessellator.addVertex(-f / 2, 0 - f * bladeLength, f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(-f / 2, 0 - f * bladeLength, -f / 2);
            tessellator.addVertex(f / 2, 0 - f * bladeLength, f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(f / 2, 0 - f * bladeLength, -f / 2);
            tessellator.addVertex(-f / 2, 0 - f * bladeLength, -f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(f / 2, 0 - f * bladeLength, -f / 2);
            tessellator.addVertex(-f / 2, 0 - f * bladeLength, f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(0, -tip - f * bladeLength, -f / 2);
            tessellator.addVertex(f / 2, 0 - f * bladeLength, f / 2);
            tessellator.draw();

            blade.render(0.0625F);
        }
        else
        {
            blade.render(0.0625F);
            GL11.glTranslatef(0, -0.0625F * (0.5F + bladeLength), 0.0625F / 2);
            ALRenderHelper.drawTip(0.03125F, 0.125F);
        }

        GL11.glColor4f(1, 1, 1, 1);
    }

    public void renderOuter(LightsaberData data, ItemStack itemstack, float[] rgb, boolean inWorld)
    {
        boolean fineCut = data.hasFocusingCrystal(FocusingCrystal.FINE_CUT);
        int smooth = 10;
        float width = 0.6F;
        float f = 1;
        float f1 = 1;
        float f2 = 1;
        float f3 = 0.1F;

        if (data.hasFocusingCrystal(FocusingCrystal.COMPRESSED))
        {
            width = 0.4F;
            smooth = 7;
            f3 = 0.07F;
        }
        
        if (data.hasFocusingCrystal(FocusingCrystal.INVERTING) && data.hasFocusingCrystal(FocusingCrystal.PRISMATIC))
        {
            rgb = new float[3];
            f3 *= 1.5F;
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        if (fineCut)
        {
            f *= 0.55F;
            f1 *= 0.925F;
            f2 *= 1.1F;
        }

        if (inWorld)
        {
            width *= ModConfig.renderGlobalMultiplier * ModConfig.renderWidthMultiplier;
            smooth *= ModConfig.renderGlobalMultiplier * ModConfig.renderSmoothingMultiplier;
        }

        if (itemstack.getDisplayName().equals("jeb_"))
        {
            smooth *= 0.25F;
        }

        int layerCount = 5 * smooth;
        float opacityMultiplier = inWorld ? ModConfig.renderGlobalMultiplier * ModConfig.renderOpacityMultiplier : 1;

        for (int i = 0; i < layerCount; ++i)
        {
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], f3 / smooth * opacityMultiplier);
            float scale = 1 + i * (width / smooth);
            float f4 = (float) i / layerCount * 50;

            GL11.glPushMatrix();
            GL11.glScaled(scale * f, (1 - f4 * (fineCut ? 0.003F : 0.005F) + 0.2F) * f1, scale * f2);
            GL11.glTranslatef(0, -f4 / 400 + 0.06F, 0);

            if (fineCut)
            {
                GL11.glTranslatef(0, 0, 0.005F + f4 * 0.00001F);
            }

            blade.render(0.0625F);
            GL11.glPopMatrix();
        }
        
//        if (data.hasFocusingCrystal(FocusingCrystal.CHARGED))
//        {
//            renderLightning(data, itemstack, rgb, inWorld, true);
//        }

        GL11.glColor4f(1, 1, 1, 1);
    }

    public void renderCrossguardOuter(LightsaberData data, ItemStack itemstack, float[] rgb, boolean inWorld)
    {
        boolean fineCut = data.hasFocusingCrystal(FocusingCrystal.FINE_CUT);
        int smooth = 10;
        float width = 0.4F;
        float f = 1;
        float f1 = 1;
        float f2 = 1;
        float f3 = 0.1F;
        
        if (data.hasFocusingCrystal(FocusingCrystal.INVERTING) && data.hasFocusingCrystal(FocusingCrystal.PRISMATIC))
        {
            rgb = new float[3];
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.COMPRESSED))
        {
            width = 0.2F;
            smooth = 7;
            f1 = 0.9F;
            f3 = 0.07F;
        }

        if (fineCut)
        {
            f *= 0.55F;
            f1 *= 0.925F;
            f2 *= 1.3F;
        }

        if (inWorld)
        {
            width *= ModConfig.renderGlobalMultiplier * ModConfig.renderWidthMultiplier;
            smooth *= ModConfig.renderGlobalMultiplier * ModConfig.renderSmoothingMultiplier;
        }

        if (itemstack.getDisplayName().equals("jeb_"))
        {
            smooth *= 0.25F;
        }

        int layerCount = 5 * smooth;

        for (int i = 0; i < layerCount; ++i)
        {
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], f3 / smooth * (inWorld ? ModConfig.renderGlobalMultiplier * ModConfig.renderOpacityMultiplier : 1));
            float scale = 1 + i * (width / smooth);
            float f4 = (float) i / layerCount * 50;

            GL11.glPushMatrix();
            GL11.glScaled(scale * f, (1 - f4 * 0.05F + 2F) * f1, scale * f2);
            GL11.glTranslatef(0, -f4 / 400 + 0.06F, 0);

            if (fineCut)
            {
                GL11.glTranslatef(0, 0, 0.005F + f4 * 0.00001F);
            }

            blade.render(0.0625F);
            GL11.glPopMatrix();
        }
        
//        if (data.hasFocusingCrystal(FocusingCrystal.CHARGED))
//        {
//            renderLightning(data, itemstack, rgb, inWorld, true);
//        }

        GL11.glColor4f(1, 1, 1, 1);
    }
    
//    private void renderLightning(LightsaberData data, ItemStack itemstack, float[] rgb, boolean inWorld, boolean isCrossguard)
//    {
//        float divider = 60;
//        int ticks = Minecraft.getMinecraft().thePlayer.ticksExisted;
//        Random rand = new Random(ticks % 100 * 1000);
//        Random prev = new Random((ticks - 1) % 100 * 1000);
//
//        GL11.glColor4f(rgb[0], rgb[1], rgb[2], 0.5F * (inWorld ? ModConfig.renderGlobalMultiplier * ModConfig.renderOpacityMultiplier : 1));
//        Supplier<Float> nextFloat = () -> ALRenderHelper.median(rand.nextFloat(), prev.nextFloat());
//
//        for (int i = 0; i < 4; ++i)
//        {
//            GL11.glPushMatrix();
//
//            if (i != 0)
//            {
//                GL11.glTranslatef((nextFloat.get() - 0.5F) / divider, 0, (nextFloat.get() - 0.5F) / divider);
//
//                for (int j = 0; j < bladeLength; ++j)
//                {
//                    GL11.glPushMatrix();
//                    GL11.glRotatef(nextFloat.get() * 360, 0, 1, 0);
//                    GL11.glRotatef(90, 1, 0, 0);
//                    GL11.glTranslatef(0, 0.05F + (1 + nextFloat.get() * 0.2F) / 16, (1 + nextFloat.get() * bladeLength) / 16);
//                    ALRenderHelper.drawTip(0.04F, 0);
//                    GL11.glPopMatrix();
//                }
//            }
//
////            if (!fineCut)
////            {
////                blade.render(0.0625F);
////                GL11.glTranslatef(0, -0.0625F * (0.5F + bladeLength), 0.0625F / 2);
////                ALRenderHelper.drawTip(0.03125F, 0.125F);
////            }
//
//            GL11.glPopMatrix();
//        }
//    }
}
