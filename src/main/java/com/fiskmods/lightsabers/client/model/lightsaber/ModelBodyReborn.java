package com.fiskmods.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBodyReborn extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer sideBlade1point3;
    public ModelRenderer sideBlade1point2;
    public ModelRenderer sideBlade1point1;
    public ModelRenderer body2;
    public ModelRenderer body5;
    public ModelRenderer body8;
    public ModelRenderer body11;
    public ModelRenderer body14;
    public ModelRenderer body17;
    public ModelRenderer body20;
    public ModelRenderer sideBlade2;
    public ModelRenderer sideBlade3;
    public ModelRenderer sideBlade4;
    public ModelRenderer sideBlade5;
    public ModelRenderer sideBlade2_1;
    public ModelRenderer sideBlade3_1;
    public ModelRenderer sideBlade4_1;
    public ModelRenderer sideBlade5_1;
    public ModelRenderer sideBlade2_2;
    public ModelRenderer sideBlade3_2;
    public ModelRenderer sideBlade4_2;
    public ModelRenderer sideBlade5_2;

    public ModelBodyReborn()
    {
        textureWidth = 32;
        textureHeight = 32;
        sideBlade1point1 = new ModelRenderer(this, 0, 11);
        sideBlade1point1.setRotationPoint(-2.8F, 0.0F, -2.2F);
        sideBlade1point1.addBox(0.0F, 0.0F, 0.0F, 1, 17, 1, 0.0F);
        setRotateAngle(sideBlade1point1, 0.0F, 2.356194490192345F, 0.0F);
        sideBlade5 = new ModelRenderer(this, 0, 11);
        sideBlade5.setRotationPoint(-1.34F, 17.1F, 0.0F);
        sideBlade5.addBox(0.0F, 0.3F, 0.0F, 3, 1, 1, 0.0F);
        setRotateAngle(sideBlade5, 0.0F, 0.0F, -0.6108652381980153F);
        sideBlade3_2 = new ModelRenderer(this, 0, 11);
        sideBlade3_2.setRotationPoint(0.96F, 0.5F, 0.0F);
        sideBlade3_2.addBox(0.0F, 0.3F, 0.0F, 1, 5, 1, 0.0F);
        setRotateAngle(sideBlade3_2, 0.0F, 0.0F, 0.17453292519943295F);
        body17 = new ModelRenderer(this, -7, 11);
        body17.setRotationPoint(0.0F, 0.0F, 0.0F);
        body17.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        setRotateAngle(body17, 0.0F, -1.5707963267948966F, 0.0F);
        sideBlade1point3 = new ModelRenderer(this, 0, 11);
        sideBlade1point3.setRotationPoint(0.5F, 0.0F, 3.6F);
        sideBlade1point3.addBox(0.0F, 0.0F, 0.0F, 1, 17, 1, 0.0F);
        setRotateAngle(sideBlade1point3, 0.0F, -1.5707963267948966F, 0.0F);
        sideBlade2 = new ModelRenderer(this, 0, 11);
        sideBlade2.setRotationPoint(0.9F, -5.0F, 0.0F);
        sideBlade2.addBox(0.0F, 5.0F, 0.0F, 1, 1, 1, 0.0F);
        sideBlade2_2 = new ModelRenderer(this, 0, 11);
        sideBlade2_2.setRotationPoint(0.9F, -5.0F, 0.0F);
        sideBlade2_2.addBox(0.0F, 5.0F, 0.0F, 1, 1, 1, 0.0F);
        sideBlade3_1 = new ModelRenderer(this, 0, 11);
        sideBlade3_1.setRotationPoint(0.96F, 0.5F, 0.0F);
        sideBlade3_1.addBox(0.0F, 0.3F, 0.0F, 1, 5, 1, 0.0F);
        setRotateAngle(sideBlade3_1, 0.0F, 0.0F, 0.17453292519943295F);
        body20 = new ModelRenderer(this, 23, 11);
        body20.setRotationPoint(0.0F, 0.0F, 0.0F);
        body20.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        setRotateAngle(body20, 0.0F, -0.7853981633974483F, 0.0F);
        sideBlade4 = new ModelRenderer(this, 0, 11);
        sideBlade4.setRotationPoint(-0.04F, 11.4F, 0.0F);
        sideBlade4.addBox(0.0F, 0.3F, 0.0F, 1, 5, 1, 0.0F);
        setRotateAngle(sideBlade4, 0.0F, 0.0F, -0.17453292519943295F);
        sideBlade2_1 = new ModelRenderer(this, 0, 11);
        sideBlade2_1.setRotationPoint(0.9F, -5.0F, 0.0F);
        sideBlade2_1.addBox(0.0F, 5.0F, 0.0F, 1, 1, 1, 0.0F);
        sideBlade4_2 = new ModelRenderer(this, 0, 11);
        sideBlade4_2.setRotationPoint(-0.04F, 11.4F, 0.0F);
        sideBlade4_2.addBox(0.0F, 0.3F, 0.0F, 1, 5, 1, 0.0F);
        setRotateAngle(sideBlade4_2, 0.0F, 0.0F, -0.17453292519943295F);
        body2 = new ModelRenderer(this, 25, 11);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        sideBlade3 = new ModelRenderer(this, 0, 11);
        sideBlade3.setRotationPoint(0.96F, 0.5F, 0.0F);
        sideBlade3.addBox(0.0F, 0.3F, 0.0F, 1, 5, 1, 0.0F);
        setRotateAngle(sideBlade3, 0.0F, 0.0F, 0.17453292519943295F);
        body5 = new ModelRenderer(this, -9, 11);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        setRotateAngle(body5, 0.0F, 1.5707963267948966F, 0.0F);
        body1 = new ModelRenderer(this, -2, 11);
        body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body1.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        body8 = new ModelRenderer(this, -2, 11);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        setRotateAngle(body8, 0.0F, 2.356194490192345F, 0.0F);
        sideBlade1point2 = new ModelRenderer(this, 0, 11);
        sideBlade1point2.setRotationPoint(2.2F, 0.0F, -2.8F);
        sideBlade1point2.addBox(0.0F, 0.0F, 0.0F, 1, 17, 1, 0.0F);
        setRotateAngle(sideBlade1point2, 0.0F, 0.7853981633974483F, 0.0F);
        sideBlade5_2 = new ModelRenderer(this, 0, 11);
        sideBlade5_2.setRotationPoint(-1.34F, 17.1F, 0.0F);
        sideBlade5_2.addBox(0.0F, 0.3F, 0.0F, 3, 1, 1, 0.0F);
        setRotateAngle(sideBlade5_2, 0.0F, 0.0F, -0.6108652381980153F);
        sideBlade5_1 = new ModelRenderer(this, 0, 11);
        sideBlade5_1.setRotationPoint(-1.34F, 17.1F, 0.0F);
        sideBlade5_1.addBox(0.0F, 0.3F, 0.0F, 3, 1, 1, 0.0F);
        setRotateAngle(sideBlade5_1, 0.0F, 0.0F, -0.6108652381980153F);
        body14 = new ModelRenderer(this, -2, 11);
        body14.setRotationPoint(0.0F, 0.0F, 0.0F);
        body14.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        setRotateAngle(body14, 0.0F, -2.356194490192345F, 0.0F);
        sideBlade4_1 = new ModelRenderer(this, 0, 11);
        sideBlade4_1.setRotationPoint(-0.04F, 11.4F, 0.0F);
        sideBlade4_1.addBox(0.0F, 0.3F, 0.0F, 1, 5, 1, 0.0F);
        setRotateAngle(sideBlade4_1, 0.0F, 0.0F, -0.17453292519943295F);
        body11 = new ModelRenderer(this, 24, 11);
        body11.setRotationPoint(0.0F, 0.0F, 0.0F);
        body11.addBox(-1.5F, 0.0F, 2.62F, 3, 19, 1, 0.0F);
        setRotateAngle(body11, 0.0F, 3.141592653589793F, 0.0F);
        sideBlade1point3.addChild(sideBlade5);
        sideBlade1point1.addChild(sideBlade3_2);
        body1.addChild(body17);
        sideBlade1point3.addChild(sideBlade2);
        sideBlade1point1.addChild(sideBlade2_2);
        sideBlade1point2.addChild(sideBlade3_1);
        body1.addChild(body20);
        sideBlade1point3.addChild(sideBlade4);
        sideBlade1point2.addChild(sideBlade2_1);
        sideBlade1point1.addChild(sideBlade4_2);
        body1.addChild(body2);
        sideBlade1point3.addChild(sideBlade3);
        body1.addChild(body5);
        body1.addChild(body8);
        sideBlade1point1.addChild(sideBlade5_2);
        sideBlade1point2.addChild(sideBlade5_1);
        body1.addChild(body14);
        sideBlade1point2.addChild(sideBlade4_1);
        body1.addChild(body11);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        sideBlade1point1.render(f5);
        sideBlade1point3.render(f5);
        body1.render(f5);
        sideBlade1point2.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
