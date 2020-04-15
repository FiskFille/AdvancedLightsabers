package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBodyJuggernaut extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer body9;
    public ModelRenderer body10;
    public ModelRenderer body11;
    public ModelRenderer body12;
    public ModelRenderer body13;
    public ModelRenderer body14;
    public ModelRenderer body15;

    public ModelBodyJuggernaut()
    {
        textureWidth = 64;
        textureHeight = 32;
        body15 = new ModelRenderer(this, 8, 0);
        body15.setRotationPoint(0.0F, 0.0F, 0.0F);
        body15.addBox(-0.5F, 0.0F, 3.52F, 1, 16, 1, 0.0F);
        setRotateAngle(body15, 0.0F, 0.8976228343006837F, 0.0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body3.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        body11 = new ModelRenderer(this, 8, 0);
        body11.setRotationPoint(0.0F, 0.0F, 0.0F);
        body11.addBox(-0.5F, 0.0F, 3.52F, 1, 16, 1, 0.0F);
        setRotateAngle(body11, 0.0F, 0.8976228343006837F, 0.0F);
        body6 = new ModelRenderer(this, 0, 0);
        body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        body6.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        body8 = new ModelRenderer(this, 0, 0);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        body10 = new ModelRenderer(this, 8, 0);
        body10.setRotationPoint(0.0F, 0.0F, 0.0F);
        body10.addBox(-0.5F, 0.0F, 3.52F, 1, 16, 1, 0.0F);
        setRotateAngle(body10, 0.0F, 0.8976228343006837F, 0.0F);
        body7 = new ModelRenderer(this, 0, 0);
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        body1 = new ModelRenderer(this, 0, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        body9 = new ModelRenderer(this, 8, 0);
        body9.setRotationPoint(0.0F, 0.0F, 0.0F);
        body9.addBox(-0.5F, 0.0F, 3.52F, 1, 16, 1, 0.0F);
        body12 = new ModelRenderer(this, 8, 0);
        body12.setRotationPoint(0.0F, 0.0F, 0.0F);
        body12.addBox(-0.5F, 0.0F, 3.52F, 1, 16, 1, 0.0F);
        setRotateAngle(body12, 0.0F, 0.8976228343006837F, 0.0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, 0.0F, 2.62F, 3, 16, 1, 0.0F);
        setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        body14 = new ModelRenderer(this, 8, 0);
        body14.setRotationPoint(0.0F, 0.0F, 0.0F);
        body14.addBox(-0.5F, 0.0F, 3.52F, 1, 16, 1, 0.0F);
        setRotateAngle(body14, 0.0F, 0.8976228343006837F, 0.0F);
        body13 = new ModelRenderer(this, 8, 0);
        body13.setRotationPoint(0.0F, 0.0F, 0.0F);
        body13.addBox(-0.5F, 0.0F, 3.52F, 1, 16, 1, 0.0F);
        setRotateAngle(body13, 0.0F, 0.8976228343006837F, 0.0F);
        body14.addChild(body15);
        body1.addChild(body4);
        body1.addChild(body2);
        body1.addChild(body3);
        body10.addChild(body11);
        body1.addChild(body6);
        body1.addChild(body8);
        body9.addChild(body10);
        body1.addChild(body7);
        body1.addChild(body9);
        body11.addChild(body12);
        body1.addChild(body5);
        body13.addChild(body14);
        body12.addChild(body13);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glTranslatef(body1.offsetX, body1.offsetY, body1.offsetZ);
        GL11.glTranslatef(body1.rotationPointX * f5, body1.rotationPointY * f5, body1.rotationPointZ * f5);
        GL11.glScaled(0.8D, 1.0D, 0.8D);
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
