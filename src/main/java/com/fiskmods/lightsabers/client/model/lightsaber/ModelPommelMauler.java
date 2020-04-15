package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPommelMauler extends ModelBase
{
    public ModelRenderer top1;
    public ModelRenderer outerRing1;
    public ModelRenderer innerRing1;
    public ModelRenderer top2;
    public ModelRenderer top3;
    public ModelRenderer top4;
    public ModelRenderer top5;
    public ModelRenderer top6;
    public ModelRenderer top7;
    public ModelRenderer top8;
    public ModelRenderer outerRing2;
    public ModelRenderer outerRing3;
    public ModelRenderer outerRing4;
    public ModelRenderer outerRing5;
    public ModelRenderer outerRing6;
    public ModelRenderer outerRing7;
    public ModelRenderer outerRing8;
    public ModelRenderer innerRing2;
    public ModelRenderer innerRing3;
    public ModelRenderer innerRing4;
    public ModelRenderer innerRing5;
    public ModelRenderer innerRing6;
    public ModelRenderer innerRing7;
    public ModelRenderer innerRing8;

    public ModelPommelMauler()
    {
        textureWidth = 64;
        textureHeight = 32;
        outerRing1 = new ModelRenderer(this, 0, 0);
        outerRing1.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        top7 = new ModelRenderer(this, 8, 0);
        top7.setRotationPoint(0.0F, 0.0F, 0.0F);
        top7.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top7, 0.0F, -1.5707963267948966F, 0.0F);
        outerRing3 = new ModelRenderer(this, 0, 0);
        outerRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(outerRing3, 0.0F, 1.5707963267948966F, 0.0F);
        outerRing6 = new ModelRenderer(this, 0, 0);
        outerRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(outerRing6, 0.0F, -2.356194490192345F, 0.0F);
        innerRing8 = new ModelRenderer(this, 0, 2);
        innerRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        innerRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(innerRing8, 0.0F, -0.7853981633974483F, 0.0F);
        innerRing3 = new ModelRenderer(this, 0, 2);
        innerRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        innerRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(innerRing3, 0.0F, 1.5707963267948966F, 0.0F);
        outerRing8 = new ModelRenderer(this, 0, 0);
        outerRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(outerRing8, 0.0F, -0.7853981633974483F, 0.0F);
        top1 = new ModelRenderer(this, 8, 0);
        top1.setRotationPoint(0.0F, 0.0F, 0.0F);
        top1.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        top3 = new ModelRenderer(this, 8, 0);
        top3.setRotationPoint(0.0F, 0.0F, 0.0F);
        top3.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top3, 0.0F, 1.5707963267948966F, 0.0F);
        outerRing2 = new ModelRenderer(this, 0, 0);
        outerRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(outerRing2, 0.0F, 0.7853981633974483F, 0.0F);
        top8 = new ModelRenderer(this, 8, 0);
        top8.setRotationPoint(0.0F, 0.0F, 0.0F);
        top8.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top8, 0.0F, -0.7853981633974483F, 0.0F);
        outerRing7 = new ModelRenderer(this, 0, 0);
        outerRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(outerRing7, 0.0F, -1.5707963267948966F, 0.0F);
        innerRing5 = new ModelRenderer(this, 0, 2);
        innerRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        innerRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(innerRing5, 0.0F, 3.141592653589793F, 0.0F);
        innerRing2 = new ModelRenderer(this, 0, 2);
        innerRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        innerRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(innerRing2, 0.0F, 0.7853981633974483F, 0.0F);
        top4 = new ModelRenderer(this, 8, 0);
        top4.setRotationPoint(0.0F, 0.0F, 0.0F);
        top4.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top4, 0.0F, 2.356194490192345F, 0.0F);
        top2 = new ModelRenderer(this, 8, 0);
        top2.setRotationPoint(0.0F, 0.0F, 0.0F);
        top2.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top2, 0.0F, 0.7853981633974483F, 0.0F);
        top6 = new ModelRenderer(this, 8, 0);
        top6.setRotationPoint(0.0F, 0.0F, 0.0F);
        top6.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top6, 0.0F, -2.356194490192345F, 0.0F);
        innerRing6 = new ModelRenderer(this, 0, 2);
        innerRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        innerRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(innerRing6, 0.0F, -2.356194490192345F, 0.0F);
        innerRing4 = new ModelRenderer(this, 0, 2);
        innerRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        innerRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(innerRing4, 0.0F, 2.356194490192345F, 0.0F);
        outerRing4 = new ModelRenderer(this, 0, 0);
        outerRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(outerRing4, 0.0F, 2.356194490192345F, 0.0F);
        innerRing1 = new ModelRenderer(this, 0, 2);
        innerRing1.setRotationPoint(0.0F, -1.0F, 0.0F);
        innerRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        innerRing7 = new ModelRenderer(this, 0, 2);
        innerRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        innerRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(innerRing7, 0.0F, -1.5707963267948966F, 0.0F);
        outerRing5 = new ModelRenderer(this, 0, 0);
        outerRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        outerRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(outerRing5, 0.0F, 3.141592653589793F, 0.0F);
        top5 = new ModelRenderer(this, 8, 0);
        top5.setRotationPoint(0.0F, 0.0F, 0.0F);
        top5.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(top5, 0.0F, 3.141592653589793F, 0.0F);
        top1.addChild(top7);
        outerRing1.addChild(outerRing3);
        outerRing1.addChild(outerRing6);
        innerRing1.addChild(innerRing8);
        innerRing1.addChild(innerRing3);
        outerRing1.addChild(outerRing8);
        top1.addChild(top3);
        outerRing1.addChild(outerRing2);
        top1.addChild(top8);
        outerRing1.addChild(outerRing7);
        innerRing1.addChild(innerRing5);
        innerRing1.addChild(innerRing2);
        top1.addChild(top4);
        top1.addChild(top2);
        top1.addChild(top6);
        innerRing1.addChild(innerRing6);
        innerRing1.addChild(innerRing4);
        outerRing1.addChild(outerRing4);
        innerRing1.addChild(innerRing7);
        outerRing1.addChild(outerRing5);
        top1.addChild(top5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(outerRing1.offsetX, outerRing1.offsetY, outerRing1.offsetZ);
        GL11.glTranslatef(outerRing1.rotationPointX * f5, outerRing1.rotationPointY * f5, outerRing1.rotationPointZ * f5);
        GL11.glScaled(1.0D, 0.25D, 1.0D);
        GL11.glTranslatef(-outerRing1.offsetX, -outerRing1.offsetY, -outerRing1.offsetZ);
        GL11.glTranslatef(-outerRing1.rotationPointX * f5, -outerRing1.rotationPointY * f5, -outerRing1.rotationPointZ * f5);
        outerRing1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(top1.offsetX, top1.offsetY, top1.offsetZ);
        GL11.glTranslatef(top1.rotationPointX * f5, top1.rotationPointY * f5, top1.rotationPointZ * f5);
        GL11.glScaled(0.8D, 1.0D, 0.8D);
        GL11.glTranslatef(-top1.offsetX, -top1.offsetY, -top1.offsetZ);
        GL11.glTranslatef(-top1.rotationPointX * f5, -top1.rotationPointY * f5, -top1.rotationPointZ * f5);
        top1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(innerRing1.offsetX, innerRing1.offsetY, innerRing1.offsetZ);
        GL11.glTranslatef(innerRing1.rotationPointX * f5, innerRing1.rotationPointY * f5, innerRing1.rotationPointZ * f5);
        GL11.glScaled(0.65D, 1.3D, 0.65D);
        GL11.glTranslatef(-innerRing1.offsetX, -innerRing1.offsetY, -innerRing1.offsetZ);
        GL11.glTranslatef(-innerRing1.rotationPointX * f5, -innerRing1.rotationPointY * f5, -innerRing1.rotationPointZ * f5);
        innerRing1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
