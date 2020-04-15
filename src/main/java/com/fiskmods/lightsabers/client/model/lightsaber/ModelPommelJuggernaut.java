package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPommelJuggernaut extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer cap1;
    public ModelRenderer body2;
    public ModelRenderer body4;
    public ModelRenderer body8;
    public ModelRenderer body10;
    public ModelRenderer body14;
    public ModelRenderer body16;
    public ModelRenderer body20;
    public ModelRenderer body22;
    public ModelRenderer body23;
    public ModelRenderer body3;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body9;
    public ModelRenderer body11;
    public ModelRenderer body12;
    public ModelRenderer body13;
    public ModelRenderer body15;
    public ModelRenderer body17;
    public ModelRenderer body18;
    public ModelRenderer body19;
    public ModelRenderer body21;
    public ModelRenderer body24;
    public ModelRenderer cap2;
    public ModelRenderer cap3;
    public ModelRenderer cap4;

    public ModelPommelJuggernaut()
    {
        textureWidth = 64;
        textureHeight = 32;
        body1 = new ModelRenderer(this, 0, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        body16 = new ModelRenderer(this, 0, 0);
        body16.setRotationPoint(0.0F, 0.0F, 0.0F);
        body16.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        setRotateAngle(body16, 0.0F, -1.5707963267948966F, 0.0F);
        body3 = new ModelRenderer(this, 0, 9);
        body3.setRotationPoint(0.0F, 5.0F, 3.62F);
        body3.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body3, -1.0471975511965976F, 0.0F, 0.0F);
        cap2 = new ModelRenderer(this, 0, 18);
        cap2.setRotationPoint(0.0F, 1.3F, 0.0F);
        cap2.addBox(-4.0F, 0.0F, -0.5F, 8, 1, 1, 0.0F);
        body12 = new ModelRenderer(this, 14, 0);
        body12.setRotationPoint(0.0F, 3.0F, 3.12F);
        body12.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        setRotateAngle(body4, 0.0F, 1.5707963267948966F, 0.0F);
        body22 = new ModelRenderer(this, 0, 9);
        body22.setRotationPoint(0.0F, 5.0F, 3.62F);
        body22.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body22, -1.0471975511965976F, 0.0F, 0.0F);
        cap3 = new ModelRenderer(this, 0, 14);
        cap3.setRotationPoint(0.0F, 0.0F, 0.0F);
        cap3.addBox(-3.5F, 0.0F, -1.0F, 7, 2, 2, 0.0F);
        setRotateAngle(cap3, 0.0F, 1.5707963267948966F, 0.0F);
        body19 = new ModelRenderer(this, 14, 7);
        body19.setRotationPoint(0.0F, 0.0F, 0.5F);
        body19.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        body9 = new ModelRenderer(this, 0, 9);
        body9.setRotationPoint(0.0F, 5.0F, 3.62F);
        body9.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body9, -1.0471975511965976F, 0.0F, 0.0F);
        body6 = new ModelRenderer(this, 14, 0);
        body6.setRotationPoint(0.0F, 3.0F, 3.12F);
        body6.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        body15 = new ModelRenderer(this, 0, 9);
        body15.setRotationPoint(0.0F, 5.0F, 3.62F);
        body15.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body15, -1.0471975511965976F, 0.0F, 0.0F);
        body11 = new ModelRenderer(this, 0, 9);
        body11.setRotationPoint(0.0F, 5.0F, 3.62F);
        body11.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body11, -1.0471975511965976F, 0.0F, 0.0F);
        body18 = new ModelRenderer(this, 14, 0);
        body18.setRotationPoint(0.0F, 3.0F, 3.12F);
        body18.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        body14 = new ModelRenderer(this, 0, 0);
        body14.setRotationPoint(0.0F, 0.0F, 0.0F);
        body14.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        setRotateAngle(body14, 0.0F, -2.356194490192345F, 0.0F);
        body13 = new ModelRenderer(this, 14, 7);
        body13.setRotationPoint(0.0F, 0.0F, 0.5F);
        body13.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        body23 = new ModelRenderer(this, 14, 0);
        body23.setRotationPoint(0.0F, 3.0F, 3.12F);
        body23.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        cap4 = new ModelRenderer(this, 0, 18);
        cap4.setRotationPoint(0.0F, 1.3F, 0.0F);
        cap4.addBox(-4.0F, 0.0F, -0.5F, 8, 1, 1, 0.0F);
        body8 = new ModelRenderer(this, 0, 0);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        setRotateAngle(body8, 0.0F, 2.356194490192345F, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        body17 = new ModelRenderer(this, 0, 9);
        body17.setRotationPoint(0.0F, 5.0F, 3.62F);
        body17.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body17, -1.0471975511965976F, 0.0F, 0.0F);
        cap1 = new ModelRenderer(this, 0, 14);
        cap1.setRotationPoint(0.0F, 4.7F, 0.0F);
        cap1.addBox(-3.5F, 0.0F, -1.0F, 7, 2, 2, 0.0F);
        body7 = new ModelRenderer(this, 14, 7);
        body7.setRotationPoint(0.0F, 0.0F, 0.5F);
        body7.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        body21 = new ModelRenderer(this, 0, 9);
        body21.setRotationPoint(0.0F, 5.0F, 3.62F);
        body21.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body21, -1.0471975511965976F, 0.0F, 0.0F);
        body10 = new ModelRenderer(this, 0, 0);
        body10.setRotationPoint(0.0F, 0.0F, 0.0F);
        body10.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        setRotateAngle(body10, 0.0F, 3.141592653589793F, 0.0F);
        body24 = new ModelRenderer(this, 14, 7);
        body24.setRotationPoint(0.0F, 0.0F, 0.5F);
        body24.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        body5 = new ModelRenderer(this, 0, 9);
        body5.setRotationPoint(0.0F, 5.0F, 3.62F);
        body5.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        setRotateAngle(body5, -1.0471975511965976F, 0.0F, 0.0F);
        body20 = new ModelRenderer(this, 0, 0);
        body20.setRotationPoint(0.0F, 0.0F, 0.0F);
        body20.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        setRotateAngle(body20, 0.0F, -0.7853981633974483F, 0.0F);
        body1.addChild(body16);
        body2.addChild(body3);
        cap1.addChild(cap2);
        body10.addChild(body12);
        body1.addChild(body4);
        body1.addChild(body22);
        cap1.addChild(cap3);
        body18.addChild(body19);
        body8.addChild(body9);
        body4.addChild(body6);
        body14.addChild(body15);
        body10.addChild(body11);
        body16.addChild(body18);
        body1.addChild(body14);
        body12.addChild(body13);
        body1.addChild(body23);
        cap3.addChild(cap4);
        body1.addChild(body8);
        body1.addChild(body2);
        body16.addChild(body17);
        body6.addChild(body7);
        body20.addChild(body21);
        body1.addChild(body10);
        body23.addChild(body24);
        body4.addChild(body5);
        body1.addChild(body20);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(90, 0, 1, 0);
        body1.render(f5);
        cap1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
