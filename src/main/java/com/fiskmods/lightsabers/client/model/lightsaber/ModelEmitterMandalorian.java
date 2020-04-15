package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEmitterMandalorian extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer peg1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body6;
    public ModelRenderer body9;
    public ModelRenderer body14;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer body10;
    public ModelRenderer body13;
    public ModelRenderer body11;
    public ModelRenderer body12;
    public ModelRenderer body15;
    public ModelRenderer body18;
    public ModelRenderer body16;
    public ModelRenderer body17;
    public ModelRenderer peg2;
    public ModelRenderer peg3;
    public ModelRenderer peg4;
    public ModelRenderer peg5;
    public ModelRenderer peg6;
    public ModelRenderer peg7;
    public ModelRenderer peg8;
    public ModelRenderer peg9;
    public ModelRenderer peg10;

    public ModelEmitterMandalorian()
    {
        textureWidth = 64;
        textureHeight = 32;
        peg6 = new ModelRenderer(this, 14, 15);
        peg6.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg6.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        body14 = new ModelRenderer(this, 0, 0);
        body14.mirror = true;
        body14.setRotationPoint(2.5F, -12.72F, 3.5F);
        body14.addBox(-0.5F, -3.0F, -1.5F, 1, 3, 2, 0.0F);
        body15 = new ModelRenderer(this, 12, 0);
        body15.mirror = true;
        body15.setRotationPoint(0.0F, -3.0F, 0.5F);
        body15.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        setRotateAngle(body15, -1.1248647029103453F, 0.0F, 0.0F);
        peg1 = new ModelRenderer(this, 14, 15);
        peg1.setRotationPoint(0.0F, -0.9299999999999997F, -1.1F);
        peg1.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        body13 = new ModelRenderer(this, 8, 2);
        body13.setRotationPoint(0.0F, 0.0F, -2.0F);
        body13.addBox(-0.5F, -1.0F, -1.5F, 1, 1, 2, 0.0F);
        body17 = new ModelRenderer(this, 8, 0);
        body17.mirror = true;
        body17.setRotationPoint(0.0F, 6.39F, 0.0F);
        body17.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
        body10 = new ModelRenderer(this, 12, 0);
        body10.setRotationPoint(0.0F, -3.0F, 0.5F);
        body10.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        setRotateAngle(body10, -1.1248647029103453F, 0.0F, 0.0F);
        body7 = new ModelRenderer(this, 14, 0);
        body7.mirror = true;
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(-0.6F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
        setRotateAngle(body7, 0.0F, -0.7853981633974483F, 0.0F);
        body8 = new ModelRenderer(this, 14, 0);
        body8.mirror = true;
        body8.setRotationPoint(0.0F, 0.0F, -6.0F);
        body8.addBox(-0.6F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
        setRotateAngle(body8, 0.0F, 0.7853981633974483F, 0.0F);
        body16 = new ModelRenderer(this, 0, 19);
        body16.mirror = true;
        body16.setRotationPoint(0.0F, 1.0F, -0.5F);
        body16.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 2, 0.0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.setRotationPoint(-2.0F, 0.0F, 3.0F);
        body3.addBox(-1.0F, -13.0F, -6.0F, 1, 13, 6, 0.0F);
        body5 = new ModelRenderer(this, 14, 0);
        body5.setRotationPoint(0.0F, 0.0F, -6.0F);
        body5.addBox(-0.4F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
        setRotateAngle(body5, 0.0F, -0.7853981633974483F, 0.0F);
        peg2 = new ModelRenderer(this, 14, 15);
        peg2.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg2.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        peg10 = new ModelRenderer(this, 14, 15);
        peg10.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg10.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        body9 = new ModelRenderer(this, 0, 0);
        body9.setRotationPoint(-2.5F, -12.72F, 3.5F);
        body9.addBox(-0.5F, -3.0F, -1.5F, 1, 3, 2, 0.0F);
        peg3 = new ModelRenderer(this, 14, 15);
        peg3.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg3.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        body18 = new ModelRenderer(this, 8, 2);
        body18.mirror = true;
        body18.setRotationPoint(0.0F, 0.0F, -2.0F);
        body18.addBox(-0.5F, -1.0F, -1.5F, 1, 1, 2, 0.0F);
        body11 = new ModelRenderer(this, 0, 19);
        body11.setRotationPoint(0.0F, 1.0F, -0.5F);
        body11.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 2, 0.0F);
        peg8 = new ModelRenderer(this, 14, 15);
        peg8.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg8.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        peg9 = new ModelRenderer(this, 14, 15);
        peg9.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg9.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        peg5 = new ModelRenderer(this, 14, 15);
        peg5.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg5.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        body2 = new ModelRenderer(this, 20, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-3.0F, -13.0F, 3.0F, 6, 13, 1, 0.0F);
        setRotateAngle(body2, 0.0F, 3.141592653589793F, 0.0F);
        peg4 = new ModelRenderer(this, 14, 15);
        peg4.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg4.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        body1 = new ModelRenderer(this, 20, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.2F);
        body1.addBox(-3.0F, -13.0F, 3.0F, 6, 13, 1, 0.0F);
        peg7 = new ModelRenderer(this, 14, 15);
        peg7.setRotationPoint(0.0F, -2.0F, 0.0F);
        peg7.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
        body12 = new ModelRenderer(this, 8, 0);
        body12.setRotationPoint(0.0F, 6.39F, 0.0F);
        body12.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
        body6 = new ModelRenderer(this, 0, 0);
        body6.mirror = true;
        body6.setRotationPoint(2.0F, 0.0F, 3.0F);
        body6.addBox(0.0F, -13.0F, -6.0F, 1, 13, 6, 0.0F);
        body4 = new ModelRenderer(this, 14, 0);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-0.4F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
        setRotateAngle(body4, 0.0F, 0.7853981633974483F, 0.0F);
        peg5.addChild(peg6);
        body1.addChild(body14);
        body14.addChild(body15);
        body9.addChild(body13);
        body16.addChild(body17);
        body9.addChild(body10);
        body6.addChild(body7);
        body6.addChild(body8);
        body15.addChild(body16);
        body1.addChild(body3);
        body3.addChild(body5);
        peg1.addChild(peg2);
        peg9.addChild(peg10);
        body1.addChild(body9);
        peg2.addChild(peg3);
        body14.addChild(body18);
        body10.addChild(body11);
        peg7.addChild(peg8);
        peg8.addChild(peg9);
        peg4.addChild(peg5);
        body1.addChild(body2);
        peg3.addChild(peg4);
        peg6.addChild(peg7);
        body11.addChild(body12);
        body1.addChild(body6);
        body3.addChild(body4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(peg1.offsetX, peg1.offsetY, peg1.offsetZ);
        GL11.glTranslatef(peg1.rotationPointX * f5, peg1.rotationPointY * f5, peg1.rotationPointZ * f5);
        GL11.glScaled(0.9D, 0.5D, 1.0D);
        GL11.glTranslatef(-peg1.offsetX, -peg1.offsetY, -peg1.offsetZ);
        GL11.glTranslatef(-peg1.rotationPointX * f5, -peg1.rotationPointY * f5, -peg1.rotationPointZ * f5);
        peg1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(body1.offsetX, body1.offsetY, body1.offsetZ);
        GL11.glTranslatef(body1.rotationPointX * f5, body1.rotationPointY * f5, body1.rotationPointZ * f5);
        GL11.glScaled(0.92D, 0.85D, 0.9D);
        GL11.glTranslatef(-body1.offsetX, -body1.offsetY, -body1.offsetZ);
        GL11.glTranslatef(-body1.rotationPointX * f5, -body1.rotationPointY * f5, -body1.rotationPointZ * f5);
        body1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
