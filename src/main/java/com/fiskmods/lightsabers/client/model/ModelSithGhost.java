package com.fiskmods.lightsabers.client.model;

import com.fiskmods.lightsabers.helper.ModelHelper;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSithGhost extends ModelBiped
{
    public ModelRenderer hood;

    public ModelSithGhost()
    {
        textureWidth = 64;
        textureHeight = 64;
        bipedBody = new ModelRenderer(this, 22, 10);
        bipedBody.mirror = true;
        bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedBody.addBox(-4.0F, 0.0F, -2.5F, 8, 12, 5, 0.0F);
        bipedRightLeg = new ModelRenderer(this, 0, 10);
        bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        bipedRightLeg.addBox(-2.3F, 0.0F, -2.5F, 6, 12, 5, 0.0F);
        setRotateAngle(bipedRightLeg, 0.0F, 0.0F, 0.05235987755982988F);
        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.mirror = true;
        bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHead.addBox(-4.5F, -8.0F, -4.5F, 9, 9, 1, 0.0F);
        bipedRightArm = new ModelRenderer(this, 48, 10);
        bipedRightArm.setRotationPoint(-4.0F, 2.0F, 0.0F);
        bipedRightArm.addBox(-4.0F, -2.0F, -1.9F, 4, 12, 4, 0.0F);
        hood = new ModelRenderer(this, 0, 27);
        hood.mirror = true;
        hood.setRotationPoint(0.0F, 0.0F, 0.0F);
        hood.addBox(-5.5F, -9.4F, -3.6F, 11, 11, 9, 0.0F);
        setRotateAngle(hood, 0.08429940287132612F, 0.0F, 0.0F);
        bipedLeftArm = new ModelRenderer(this, 48, 10);
        bipedLeftArm.mirror = true;
        bipedLeftArm.setRotationPoint(4.0F, 2.0F, 0.0F);
        bipedLeftArm.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        bipedLeftLeg = new ModelRenderer(this, 0, 10);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        bipedLeftLeg.addBox(-3.7F, 0.0F, -2.5F, 6, 12, 5, 0.0F);
        setRotateAngle(bipedLeftLeg, 0.0F, 0.0F, -0.05235987755982988F);
        bipedHead.addChild(hood);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        ModelHelper.renderBipedPre(this, entity, f, f1, f2, f3, f4, f5);
        bipedBody.render(f5);
        bipedRightLeg.render(f5);
        bipedHead.render(f5);
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedLeftLeg.render(f5);
        ModelHelper.renderBipedPost(this, entity, f, f1, f2, f3, f4, f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bipedRightArm.setRotationPoint(-4.0F, 2.0F, 0.0F);
        bipedLeftArm.setRotationPoint(4.0F, 2.0F, 0.0F);
    }
}
