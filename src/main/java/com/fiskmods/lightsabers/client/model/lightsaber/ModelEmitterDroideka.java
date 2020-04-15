package com.fiskmods.lightsabers.client.model.lightsaber;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEmitterDroideka extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer ring1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer ring2;
    public ModelRenderer ring3;
    public ModelRenderer ring4;
    public ModelRenderer ring5;
    public ModelRenderer ring6;
    public ModelRenderer ring7;
    public ModelRenderer ring8;

    public ModelEmitterDroideka()
    {
        textureWidth = 64;
        textureHeight = 32;
        body6 = new ModelRenderer(this, 0, 0);
        body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        body6.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        body8 = new ModelRenderer(this, 0, 0);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        ring6 = new ModelRenderer(this, 0, 5);
        ring6.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring6.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(ring6, 0.0F, -2.356194490192345F, 0.0F);
        ring3 = new ModelRenderer(this, 0, 5);
        ring3.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring3.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(ring3, 0.0F, 1.5707963267948966F, 0.0F);
        ring8 = new ModelRenderer(this, 0, 5);
        ring8.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring8.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(ring8, 0.0F, -0.7853981633974483F, 0.0F);
        ring7 = new ModelRenderer(this, 0, 5);
        ring7.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring7.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(ring7, 0.0F, -1.5707963267948966F, 0.0F);
        ring5 = new ModelRenderer(this, 0, 5);
        ring5.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring5.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(ring5, 0.0F, 3.141592653589793F, 0.0F);
        body1 = new ModelRenderer(this, 0, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        ring2 = new ModelRenderer(this, 0, 5);
        ring2.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring2.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(ring2, 0.0F, 0.7853981633974483F, 0.0F);
        ring1 = new ModelRenderer(this, 0, 5);
        ring1.setRotationPoint(0.0F, -0.3F, 0.0F);
        ring1.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body3.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        body7 = new ModelRenderer(this, 0, 0);
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        ring4 = new ModelRenderer(this, 0, 5);
        ring4.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring4.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        setRotateAngle(ring4, 0.0F, 2.356194490192345F, 0.0F);
        body1.addChild(body6);
        body1.addChild(body8);
        ring1.addChild(ring6);
        ring1.addChild(ring3);
        ring1.addChild(ring8);
        ring1.addChild(ring7);
        ring1.addChild(ring5);
        body1.addChild(body2);
        ring1.addChild(ring2);
        body1.addChild(body3);
        body1.addChild(body7);
        body1.addChild(body5);
        body1.addChild(body4);
        ring1.addChild(ring4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        body1.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(ring1.offsetX, ring1.offsetY, ring1.offsetZ);
        GL11.glTranslatef(ring1.rotationPointX * f5, ring1.rotationPointY * f5, ring1.rotationPointZ * f5);
        GL11.glScaled(1.2D, 1.2D, 1.2D);
        GL11.glTranslatef(-ring1.offsetX, -ring1.offsetY, -ring1.offsetZ);
        GL11.glTranslatef(-ring1.rotationPointX * f5, -ring1.rotationPointY * f5, -ring1.rotationPointZ * f5);
        ring1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
