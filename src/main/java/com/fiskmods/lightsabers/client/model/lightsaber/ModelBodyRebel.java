package com.fiskmods.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelBodyRebel extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer body9;
    public ModelRenderer switchPart;
    public ModelRenderer body2;
    public ModelRenderer body5;
    public ModelRenderer body8;
    public ModelRenderer body11;
    public ModelRenderer body14;
    public ModelRenderer body17;
    public ModelRenderer body20;
    public ModelRenderer body10;
    public ModelRenderer body10_1;
    public ModelRenderer body10_2;
    public ModelRenderer body10_3;

    public ModelBodyRebel()
    {
        textureWidth = 64;
        textureHeight = 32;
        body10_2 = new ModelRenderer(this, 8, 0);
        body10_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body10_2.addBox(-0.5F, 0.5F, 3.22F, 1, 15, 1, 0.0F);
        setRotateAngle(body10_2, 0.0F, 0.6283185307179586F, 0.0F);
        body11 = new ModelRenderer(this, 13, 0);
        body11.setRotationPoint(0.0F, 0.0F, 0.0F);
        body11.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        setRotateAngle(body11, 0.0F, 3.141592653589793F, 0.0F);
        body17 = new ModelRenderer(this, 0, 0);
        body17.setRotationPoint(0.0F, 0.0F, 0.0F);
        body17.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        setRotateAngle(body17, 0.0F, -1.5707963267948966F, 0.0F);
        body1 = new ModelRenderer(this, 0, 0);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        body14 = new ModelRenderer(this, 22, 0);
        body14.setRotationPoint(0.0F, 0.0F, 0.0F);
        body14.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        setRotateAngle(body14, 0.0F, -2.356194490192345F, 0.0F);
        body20 = new ModelRenderer(this, 0, 0);
        body20.setRotationPoint(0.0F, 0.0F, 0.0F);
        body20.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        setRotateAngle(body20, 0.0F, -0.7853981633974483F, 0.0F);
        body10 = new ModelRenderer(this, 8, 0);
        body10.setRotationPoint(0.0F, 0.0F, 0.0F);
        body10.addBox(-0.5F, 0.5F, 3.22F, 1, 15, 1, 0.0F);
        setRotateAngle(body10, 0.0F, 0.6283185307179586F, 0.0F);
        switchPart = new ModelRenderer(this, 0, 23);
        switchPart.setRotationPoint(-1.0F, 3.3F, -4.5F);
        switchPart.addBox(0.0F, 0.0F, 0.0F, 2, 6, 1, 0.0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        setRotateAngle(body5, 0.0F, 1.5707963267948966F, 0.0F);
        body8 = new ModelRenderer(this, 31, 0);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        setRotateAngle(body8, 0.0F, 2.356194490192345F, 0.0F);
        body9 = new ModelRenderer(this, 8, 0);
        body9.setRotationPoint(0.0F, -0.7F, 0.0F);
        body9.addBox(-0.5F, 0.5F, 3.52F, 1, 15, 1, 0.0F);
        setRotateAngle(body9, 0.0F, -1.2217304763960306F, 0.0F);
        body10_3 = new ModelRenderer(this, 8, 0);
        body10_3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body10_3.addBox(-0.5F, 0.5F, 3.52F, 1, 15, 1, 0.0F);
        setRotateAngle(body10_3, 0.0F, 0.6283185307179586F, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        body10_1 = new ModelRenderer(this, 8, 0);
        body10_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body10_1.addBox(-0.5F, 0.5F, 2.82F, 1, 15, 1, 0.0F);
        setRotateAngle(body10_1, 0.0F, 0.6283185307179586F, 0.0F);
        body10_1.addChild(body10_2);
        body1.addChild(body11);
        body1.addChild(body17);
        body1.addChild(body14);
        body1.addChild(body20);
        body9.addChild(body10);
        body1.addChild(body5);
        body1.addChild(body8);
        body10_2.addChild(body10_3);
        body1.addChild(body2);
        body10.addChild(body10_1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        body1.render(f5);
        switchPart.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(body9.offsetX, body9.offsetY, body9.offsetZ);
        GL11.glTranslatef(body9.rotationPointX * f5, body9.rotationPointY * f5, body9.rotationPointZ * f5);
        GL11.glScaled(0.9D, 1.34D, 1.0D);
        GL11.glTranslatef(-body9.offsetX, -body9.offsetY, -body9.offsetZ);
        GL11.glTranslatef(-body9.rotationPointX * f5, -body9.rotationPointY * f5, -body9.rotationPointZ * f5);
        body9.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
