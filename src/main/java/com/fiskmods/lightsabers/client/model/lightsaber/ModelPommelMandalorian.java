package com.fiskmods.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPommelMandalorian extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer body2;
    public ModelRenderer body4;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer body13;
    public ModelRenderer body3;
    public ModelRenderer body5;
    public ModelRenderer body9;
    public ModelRenderer body11;
    public ModelRenderer body10;
    public ModelRenderer body12;
    public ModelRenderer body14;
    public ModelRenderer body16;
    public ModelRenderer body18;
    public ModelRenderer body19;
    public ModelRenderer body20;
    public ModelRenderer body25;
    public ModelRenderer body15;
    public ModelRenderer body17;
    public ModelRenderer body21;
    public ModelRenderer body23;
    public ModelRenderer body22;
    public ModelRenderer body24;
    public ModelRenderer body26;

    public ModelPommelMandalorian()
    {
        textureWidth = 64;
        textureHeight = 32;
        body18 = new ModelRenderer(this, 0, 14);
        body18.setRotationPoint(0.0F, 0.0F, 0.0F);
        body18.addBox(-2.98F, 0.0F, -2.5F, 3, 1, 5, 0.0F);
        body19 = new ModelRenderer(this, 0, 14);
        body19.mirror = true;
        body19.setRotationPoint(0.0F, 0.0F, 0.0F);
        body19.addBox(-0.02F, 0.0F, -2.5F, 3, 1, 5, 0.0F);
        body1 = new ModelRenderer(this, 0, 9);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, 0.0F, 2.76F, 3, 4, 1, 0.0F);
        body13 = new ModelRenderer(this, 0, 20);
        body13.setRotationPoint(0.0F, 4.0F, 0.0F);
        body13.addBox(-1.5F, 0.0F, 1.76F, 3, 1, 2, 0.0F);
        body20 = new ModelRenderer(this, 0, 20);
        body20.setRotationPoint(0.0F, 0.0F, 0.0F);
        body20.addBox(-1.5F, 0.0F, 1.76F, 3, 1, 2, 0.0F);
        setRotateAngle(body20, 0.0F, 3.141592653589793F, 0.0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.setRotationPoint(-1.0F, 0.0F, 0.0F);
        body3.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body3, 0.0F, -0.4721115626644662F, 0.0F);
        body9 = new ModelRenderer(this, 0, 0);
        body9.setRotationPoint(-1.5F, 0.0F, 3.76F);
        body9.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body9, 0.0F, -0.46949356878647464F, 0.0F);
        body17 = new ModelRenderer(this, 11, 12);
        body17.mirror = true;
        body17.setRotationPoint(1.0F, 0.0F, 0.0F);
        body17.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body17, 0.0F, 0.4721115626644662F, 0.0F);
        body12 = new ModelRenderer(this, 0, 0);
        body12.mirror = true;
        body12.setRotationPoint(1.0F, 0.0F, 0.0F);
        body12.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body12, 0.0F, 0.4721115626644662F, 0.0F);
        body23 = new ModelRenderer(this, 11, 12);
        body23.mirror = true;
        body23.setRotationPoint(1.5F, 0.5F, 3.76F);
        body23.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body23, 0.0F, 0.46949356878647464F, 0.0F);
        body10 = new ModelRenderer(this, 0, 0);
        body10.setRotationPoint(-1.0F, 0.0F, 0.0F);
        body10.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body10, 0.0F, -0.4721115626644662F, 0.0F);
        body22 = new ModelRenderer(this, 11, 12);
        body22.setRotationPoint(-1.0F, 0.0F, 0.0F);
        body22.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body22, 0.0F, -0.4721115626644662F, 0.0F);
        body8 = new ModelRenderer(this, 0, 9);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, 0.0F, 2.76F, 3, 4, 1, 0.0F);
        setRotateAngle(body8, 0.0F, 3.141592653589793F, 0.0F);
        body11 = new ModelRenderer(this, 0, 0);
        body11.mirror = true;
        body11.setRotationPoint(1.5F, 0.0F, 3.76F);
        body11.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body11, 0.0F, 0.46949356878647464F, 0.0F);
        body7 = new ModelRenderer(this, 0, 0);
        body7.mirror = true;
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(1.98F, 0.0F, -2.5F, 1, 4, 5, 0.0F);
        body14 = new ModelRenderer(this, 11, 12);
        body14.setRotationPoint(-1.5F, 0.5F, 3.76F);
        body14.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body14, 0.0F, -0.46949356878647464F, 0.0F);
        body25 = new ModelRenderer(this, 8, 5);
        body25.setRotationPoint(0.0F, 0.45F, -0.75F);
        body25.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 4, 0.0F);
        body26 = new ModelRenderer(this, 7, 0);
        body26.setRotationPoint(0.0F, 3.0F, 2.0F);
        body26.addBox(-1.5F, -2.0F, 0.0F, 3, 2, 3, 0.0F);
        setRotateAngle(body26, 0.9599310885968813F, 0.0F, 0.0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.mirror = true;
        body4.setRotationPoint(1.5F, 0.0F, 3.76F);
        body4.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body4, 0.0F, 0.46949356878647464F, 0.0F);
        body15 = new ModelRenderer(this, 11, 12);
        body15.setRotationPoint(-1.0F, 0.0F, 0.0F);
        body15.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body15, 0.0F, -0.4721115626644662F, 0.0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.setRotationPoint(-1.5F, 0.0F, 3.76F);
        body2.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body2, 0.0F, -0.46949356878647464F, 0.0F);
        body6 = new ModelRenderer(this, 0, 0);
        body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        body6.addBox(-2.98F, 0.0F, -2.5F, 1, 4, 5, 0.0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.mirror = true;
        body5.setRotationPoint(1.0F, 0.0F, 0.0F);
        body5.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        setRotateAngle(body5, 0.0F, 0.4721115626644662F, 0.0F);
        body21 = new ModelRenderer(this, 11, 12);
        body21.setRotationPoint(-1.5F, 0.5F, 3.76F);
        body21.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body21, 0.0F, -0.46949356878647464F, 0.0F);
        body24 = new ModelRenderer(this, 11, 12);
        body24.mirror = true;
        body24.setRotationPoint(1.0F, 0.0F, 0.0F);
        body24.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body24, 0.0F, 0.4721115626644662F, 0.0F);
        body16 = new ModelRenderer(this, 11, 12);
        body16.mirror = true;
        body16.setRotationPoint(1.5F, 0.5F, 3.76F);
        body16.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        setRotateAngle(body16, 0.0F, 0.46949356878647464F, 0.0F);
        body13.addChild(body18);
        body13.addChild(body19);
        body1.addChild(body13);
        body13.addChild(body20);
        body2.addChild(body3);
        body8.addChild(body9);
        body16.addChild(body17);
        body11.addChild(body12);
        body20.addChild(body23);
        body9.addChild(body10);
        body21.addChild(body22);
        body1.addChild(body8);
        body8.addChild(body11);
        body1.addChild(body7);
        body13.addChild(body14);
        body13.addChild(body25);
        body25.addChild(body26);
        body1.addChild(body4);
        body14.addChild(body15);
        body1.addChild(body2);
        body1.addChild(body6);
        body4.addChild(body5);
        body20.addChild(body21);
        body23.addChild(body24);
        body13.addChild(body16);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        body1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
