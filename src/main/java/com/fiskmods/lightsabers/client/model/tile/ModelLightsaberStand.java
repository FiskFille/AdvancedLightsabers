package com.fiskmods.lightsabers.client.model.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelLightsaberStand extends ModelBase
{
    public ModelRenderer base1;
    public ModelRenderer lStandAngle;
    public ModelRenderer lStandTip;
    public ModelRenderer rStandAngle;
    public ModelRenderer rStandTip;
    public ModelRenderer trimBtm;
    public ModelRenderer trimL;
    public ModelRenderer trimR;
    public ModelRenderer trimTop;

    public ModelLightsaberStand()
    {
        textureWidth = 64;
        textureHeight = 32;
        trimBtm = new ModelRenderer(this, "trimBtm").setTextureOffset(10, 9);
        trimBtm.setRotationPoint(0, 0, 0);
        trimBtm.addBox(0, 0, 0, 15, 3, 1, 0);
        rStandAngle = new ModelRenderer(this, "rStandAngle").setTextureOffset(0, 0);
        rStandAngle.setRotationPoint(0.3F, 0.7F, 0);
        rStandAngle.addBox(0, 0, 0, 1, 1, 1, 0);
        setRotateAngle(rStandAngle, 0, 0, -0.7853981633974483F);
        trimL = new ModelRenderer(this, "trimL").setTextureOffset(0, 9);
        trimL.setRotationPoint(-5.2F, 22.8F, -2.8F);
        trimL.addBox(0, 0, 0, 1, 3, 8, 0);
        rStandTip = new ModelRenderer(this, "rStandTip").setTextureOffset(0, 0);
        rStandTip.setRotationPoint(2.5F, 22.3F, -0.5F);
        rStandTip.addBox(0, 0, 0, 1, 1, 1, 0);
        lStandTip = new ModelRenderer(this, "lStandTip").setTextureOffset(0, 0);
        lStandTip.setRotationPoint(-3.5F, 22.3F, -0.5F);
        lStandTip.addBox(0, 0, 0, 1, 1, 1, 0);
        trimTop = new ModelRenderer(this, "trimTop").setTextureOffset(10, 9);
        trimTop.setRotationPoint(0, 0, 7);
        trimTop.addBox(0, 0, 0, 15, 3, 1, 0);
        lStandAngle = new ModelRenderer(this, "lStandAngle").setTextureOffset(0, 0);
        lStandAngle.setRotationPoint(0, 0, 0);
        lStandAngle.addBox(0, 0, 0, 1, 1, 1, 0);
        setRotateAngle(lStandAngle, 0, 0, 0.7853981633974483F);
        base1 = new ModelRenderer(this, "base1").setTextureOffset(0, 0);
        base1.setRotationPoint(-4.8F, 23, -2.3F);
        base1.addBox(0, 0, 0, 14, 1, 8, 0);
        trimR = new ModelRenderer(this, "trimR").setTextureOffset(0, 9);
        trimR.setRotationPoint(14, 0, 0);
        trimR.addBox(0, 0, 0, 1, 3, 8, 0);
        trimL.addChild(trimBtm);
        rStandTip.addChild(rStandAngle);
        trimL.addChild(trimTop);
        lStandTip.addChild(lStandAngle);
        trimL.addChild(trimR);
    }

    public void render()
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(trimL.offsetX, trimL.offsetY, trimL.offsetZ);
        GL11.glTranslatef(trimL.rotationPointX * 0.0625F, trimL.rotationPointY * 0.0625F, trimL.rotationPointZ * 0.0625F);
        GL11.glScaled(0.7, 0.4, 0.7);
        GL11.glTranslatef(-trimL.offsetX, -trimL.offsetY, -trimL.offsetZ);
        GL11.glTranslatef(-trimL.rotationPointX * 0.0625F, -trimL.rotationPointY * 0.0625F, -trimL.rotationPointZ * 0.0625F);
        trimL.render(0.0625F);
        GL11.glPopMatrix();
        rStandTip.render(0.0625F);
        lStandTip.render(0.0625F);
        GL11.glPushMatrix();
        GL11.glTranslatef(base1.offsetX, base1.offsetY, base1.offsetZ);
        GL11.glTranslatef(base1.rotationPointX * 0.0625F, base1.rotationPointY * 0.0625F, base1.rotationPointZ * 0.0625F);
        GL11.glScaled(0.7, 1.0, 0.6);
        GL11.glTranslatef(-base1.offsetX, -base1.offsetY, -base1.offsetZ);
        GL11.glTranslatef(-base1.rotationPointX * 0.0625F, -base1.rotationPointY * 0.0625F, -base1.rotationPointZ * 0.0625F);
        base1.render(0.0625F);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
