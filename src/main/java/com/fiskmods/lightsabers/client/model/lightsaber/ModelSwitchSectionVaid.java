package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSwitchSectionVaid extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer upperButton1;
    public ModelRenderer lowerButton1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer upperButton2;
    public ModelRenderer upperButton3;
    public ModelRenderer upperButton4;
    public ModelRenderer upperButton5;
    public ModelRenderer upperButton6;
    public ModelRenderer upperButton7;
    public ModelRenderer upperButton8;
    public ModelRenderer lowerButton2;
    public ModelRenderer lowerButton3;
    public ModelRenderer lowerButton4;
    public ModelRenderer lowerButton5;
    public ModelRenderer lowerButton6;
    public ModelRenderer lowerButton7;
    public ModelRenderer lowerButton8;

    public ModelSwitchSectionVaid()
    {
        textureWidth = 64;
        textureHeight = 32;
        upperButton6 = new ModelRenderer(this, 8, 0);
        upperButton6.setRotationPoint(0.0F, 0.0F, 0.0F);
        upperButton6.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton6, 0.0F, -2.356194490192345F, 0.0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        lowerButton5 = new ModelRenderer(this, 0, 9);
        lowerButton5.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerButton5.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton5, 0.0F, 3.141592653589793F, 0.0F);
        body7 = new ModelRenderer(this, 0, 0);
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        upperButton8 = new ModelRenderer(this, 8, 0);
        upperButton8.setRotationPoint(0.0F, 0.0F, 0.0F);
        upperButton8.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton8, 0.0F, -0.7853981633974483F, 0.0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body3.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        upperButton2 = new ModelRenderer(this, 8, 0);
        upperButton2.setRotationPoint(0.0F, 0.0F, 0.0F);
        upperButton2.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton2, 0.0F, 0.7853981633974483F, 0.0F);
        upperButton3 = new ModelRenderer(this, 8, 0);
        upperButton3.setRotationPoint(0.0F, 0.0F, 0.0F);
        upperButton3.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton3, 0.0F, 1.5707963267948966F, 0.0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        upperButton7 = new ModelRenderer(this, 8, 0);
        upperButton7.setRotationPoint(0.0F, 0.0F, 0.0F);
        upperButton7.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton7, 0.0F, -1.5707963267948966F, 0.0F);
        body1 = new ModelRenderer(this, 0, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        body8 = new ModelRenderer(this, 0, 0);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        lowerButton7 = new ModelRenderer(this, 0, 9);
        lowerButton7.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerButton7.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton7, 0.0F, -1.5707963267948966F, 0.0F);
        lowerButton6 = new ModelRenderer(this, 0, 9);
        lowerButton6.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerButton6.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton6, 0.0F, -2.356194490192345F, 0.0F);
        body6 = new ModelRenderer(this, 0, 0);
        body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        body6.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        upperButton4 = new ModelRenderer(this, 8, 0);
        upperButton4.setRotationPoint(0.0F, 0.0F, 0.0F);
        upperButton4.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton4, 0.0F, 2.356194490192345F, 0.0F);
        lowerButton8 = new ModelRenderer(this, 0, 9);
        lowerButton8.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerButton8.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton8, 0.0F, -0.7853981633974483F, 0.0F);
        lowerButton3 = new ModelRenderer(this, 0, 9);
        lowerButton3.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerButton3.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton3, 0.0F, 1.5707963267948966F, 0.0F);
        upperButton5 = new ModelRenderer(this, 8, 0);
        upperButton5.setRotationPoint(0.0F, 0.0F, 0.0F);
        upperButton5.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton5, 0.0F, 3.141592653589793F, 0.0F);
        lowerButton2 = new ModelRenderer(this, 0, 9);
        lowerButton2.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerButton2.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton2, 0.0F, 0.7853981633974483F, 0.0F);
        upperButton1 = new ModelRenderer(this, 8, 0);
        upperButton1.setRotationPoint(-3.8F, -6.2F, 0.0F);
        upperButton1.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(upperButton1, 0.0F, 0.0F, -1.5707963267948966F);
        lowerButton4 = new ModelRenderer(this, 0, 9);
        lowerButton4.setRotationPoint(0.0F, 0.0F, 0.0F);
        lowerButton4.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton4, 0.0F, 2.356194490192345F, 0.0F);
        lowerButton1 = new ModelRenderer(this, 0, 9);
        lowerButton1.setRotationPoint(-3.8F, -3.0F, 0.0F);
        lowerButton1.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        setRotateAngle(lowerButton1, 0.0F, 0.0F, -1.5707963267948966F);
        upperButton1.addChild(upperButton6);
        body1.addChild(body4);
        lowerButton1.addChild(lowerButton5);
        body1.addChild(body7);
        body1.addChild(body2);
        upperButton1.addChild(upperButton8);
        body1.addChild(body3);
        upperButton1.addChild(upperButton2);
        upperButton1.addChild(upperButton3);
        body1.addChild(body5);
        upperButton1.addChild(upperButton7);
        body1.addChild(body8);
        lowerButton1.addChild(lowerButton7);
        lowerButton1.addChild(lowerButton6);
        body1.addChild(body6);
        upperButton1.addChild(upperButton4);
        lowerButton1.addChild(lowerButton8);
        lowerButton1.addChild(lowerButton3);
        upperButton1.addChild(upperButton5);
        lowerButton1.addChild(lowerButton2);
        lowerButton1.addChild(lowerButton4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(body1.offsetX, body1.offsetY, body1.offsetZ);
        GL11.glTranslatef(body1.rotationPointX * f5, body1.rotationPointY * f5, body1.rotationPointZ * f5);
        GL11.glScaled(1.15D, 1.15D, 1.15D);
        GL11.glTranslatef(-body1.offsetX, -body1.offsetY, -body1.offsetZ);
        GL11.glTranslatef(-body1.rotationPointX * f5, -body1.rotationPointY * f5, -body1.rotationPointZ * f5);
        body1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(upperButton1.offsetX, upperButton1.offsetY, upperButton1.offsetZ);
        GL11.glTranslatef(upperButton1.rotationPointX * f5, upperButton1.rotationPointY * f5, upperButton1.rotationPointZ * f5);
        GL11.glScaled(0.3D, 0.3D, 0.3D);
        GL11.glTranslatef(-upperButton1.offsetX, -upperButton1.offsetY, -upperButton1.offsetZ);
        GL11.glTranslatef(-upperButton1.rotationPointX * f5, -upperButton1.rotationPointY * f5, -upperButton1.rotationPointZ * f5);
        upperButton1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(lowerButton1.offsetX, lowerButton1.offsetY, lowerButton1.offsetZ);
        GL11.glTranslatef(lowerButton1.rotationPointX * f5, lowerButton1.rotationPointY * f5, lowerButton1.rotationPointZ * f5);
        GL11.glScaled(0.3D, 0.3D, 0.3D);
        GL11.glTranslatef(-lowerButton1.offsetX, -lowerButton1.offsetY, -lowerButton1.offsetZ);
        GL11.glTranslatef(-lowerButton1.rotationPointX * f5, -lowerButton1.rotationPointY * f5, -lowerButton1.rotationPointZ * f5);
        lowerButton1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
