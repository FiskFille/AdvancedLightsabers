package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPommelProdigalSon extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer top1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer top2;
    public ModelRenderer top3;
    public ModelRenderer top4;
    public ModelRenderer top5;
    public ModelRenderer top6;
    public ModelRenderer top7;
    public ModelRenderer top8;

    public ModelPommelProdigalSon()
    {
        textureWidth = 64;
        textureHeight = 32;
        top6 = new ModelRenderer(this, 10, 0);
        top6.setRotationPoint(0.0F, 0.0F, 0.0F);
        top6.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top6, 0.0F, -2.356194490192345F, 0.0F);
        top5 = new ModelRenderer(this, 10, 0);
        top5.setRotationPoint(0.0F, 0.0F, 0.0F);
        top5.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top5, 0.0F, 3.141592653589793F, 0.0F);
        top2 = new ModelRenderer(this, 10, 0);
        top2.setRotationPoint(0.0F, 0.0F, 0.0F);
        top2.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top2, 0.0F, 0.7853981633974483F, 0.0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        body7 = new ModelRenderer(this, 0, 0);
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        body6 = new ModelRenderer(this, 0, 0);
        body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        body6.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        top3 = new ModelRenderer(this, 10, 0);
        top3.setRotationPoint(0.0F, 0.0F, 0.0F);
        top3.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top3, 0.0F, 1.5707963267948966F, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        top1 = new ModelRenderer(this, 10, 0);
        top1.setRotationPoint(0.0F, 0.0F, 0.0F);
        top1.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        body8 = new ModelRenderer(this, 0, 0);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        top7 = new ModelRenderer(this, 10, 0);
        top7.setRotationPoint(0.0F, 0.0F, 0.0F);
        top7.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top7, 0.0F, -1.5707963267948966F, 0.0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body3.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        top4 = new ModelRenderer(this, 10, 0);
        top4.setRotationPoint(0.0F, 0.0F, 0.0F);
        top4.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top4, 0.0F, 2.356194490192345F, 0.0F);
        top8 = new ModelRenderer(this, 10, 0);
        top8.setRotationPoint(0.0F, 0.0F, 0.0F);
        top8.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top8, 0.0F, -0.7853981633974483F, 0.0F);
        body1 = new ModelRenderer(this, 0, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, 0.0F, -0.38F, 3, 2, 4, 0.0F);
        setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        top1.addChild(top6);
        top1.addChild(top5);
        top1.addChild(top2);
        body1.addChild(body4);
        body1.addChild(body7);
        body1.addChild(body6);
        top1.addChild(top3);
        body1.addChild(body2);
        body1.addChild(body8);
        top1.addChild(top7);
        body1.addChild(body3);
        top1.addChild(top4);
        top1.addChild(top8);
        body1.addChild(body5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(top1.offsetX, top1.offsetY, top1.offsetZ);
        GL11.glTranslatef(top1.rotationPointX * f5, top1.rotationPointY * f5, top1.rotationPointZ * f5);
        GL11.glScaled(0.8D, 1.0D, 0.8D);
        GL11.glTranslatef(-top1.offsetX, -top1.offsetY, -top1.offsetZ);
        GL11.glTranslatef(-top1.rotationPointX * f5, -top1.rotationPointY * f5, -top1.rotationPointZ * f5);
        top1.render(f5);
        GL11.glPopMatrix();
        body1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
