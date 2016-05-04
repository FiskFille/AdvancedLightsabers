package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBodyKnighted extends ModelBase
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
    public ModelRenderer body16;
    public ModelRenderer body17;
    public ModelRenderer body18;
    public ModelRenderer body19;
    public ModelRenderer body20;
    public ModelRenderer body21;
    public ModelRenderer body22;

    public ModelBodyKnighted()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body8 = new ModelRenderer(this, 0, 0);
        this.body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body8.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        this.body13 = new ModelRenderer(this, 8, 0);
        this.body13.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body13.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body13, 0.0F, 2.5132741228718345F, 0.0F);
        this.body10 = new ModelRenderer(this, 8, 0);
        this.body10.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body10.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body10, 0.0F, 0.6283185307179586F, 0.0F);
        this.body20 = new ModelRenderer(this, 0, 21);
        this.body20.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.body20.addBox(-0.5F, -3.0F, 3.4F, 1, 6, 1, 0.0F);
        this.setRotateAngle(body20, 0.0F, 1.5707963267948966F, 0.0F);
        this.body12 = new ModelRenderer(this, 8, 0);
        this.body12.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body12.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body12, 0.0F, 1.8849555921538759F, 0.0F);
        this.body17 = new ModelRenderer(this, 8, 0);
        this.body17.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body17.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body17, 0.0F, -1.2566370614359172F, 0.0F);
        this.body21 = new ModelRenderer(this, 0, 21);
        this.body21.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.body21.addBox(-0.5F, -3.0F, 3.4F, 1, 6, 1, 0.0F);
        this.setRotateAngle(body21, 0.0F, 3.141592653589793F, 0.0F);
        this.body22 = new ModelRenderer(this, 0, 21);
        this.body22.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.body22.addBox(-0.5F, -3.0F, 3.4F, 1, 6, 1, 0.0F);
        this.setRotateAngle(body22, 0.0F, -1.5707963267948966F, 0.0F);
        this.body18 = new ModelRenderer(this, 8, 0);
        this.body18.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body18.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body18, 0.0F, -0.6283185307179586F, 0.0F);
        this.body2 = new ModelRenderer(this, 0, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body2.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        this.body7 = new ModelRenderer(this, 0, 0);
        this.body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body7.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        this.body14 = new ModelRenderer(this, 8, 0);
        this.body14.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body14.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body14, 0.0F, 3.141592653589793F, 0.0F);
        this.body3 = new ModelRenderer(this, 0, 0);
        this.body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body3.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        this.body15 = new ModelRenderer(this, 8, 0);
        this.body15.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body15.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body15, 0.0F, -2.5132741228718345F, 0.0F);
        this.body11 = new ModelRenderer(this, 8, 0);
        this.body11.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body11.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body11, 0.0F, 1.2566370614359172F, 0.0F);
        this.body19 = new ModelRenderer(this, 0, 21);
        this.body19.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.body19.addBox(-0.5F, -3.0F, 3.4F, 1, 6, 1, 0.0F);
        this.body16 = new ModelRenderer(this, 8, 0);
        this.body16.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body16.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.setRotateAngle(body16, 0.0F, -1.8849555921538759F, 0.0F);
        this.body5 = new ModelRenderer(this, 0, 0);
        this.body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body5.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 0);
        this.body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body4.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 0);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.body6 = new ModelRenderer(this, 0, 0);
        this.body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body6.addBox(-1.5F, 0.0F, 2.62F, 3, 20, 1, 0.0F);
        this.setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        this.body9 = new ModelRenderer(this, 8, 0);
        this.body9.setRotationPoint(0.0F, 9.6F, 0.0F);
        this.body9.addBox(-1.0F, -9.5F, 2.95F, 2, 19, 1, 0.0F);
        this.body1.addChild(this.body8);
        this.body1.addChild(this.body13);
        this.body1.addChild(this.body10);
        this.body1.addChild(this.body20);
        this.body1.addChild(this.body12);
        this.body1.addChild(this.body17);
        this.body1.addChild(this.body21);
        this.body1.addChild(this.body22);
        this.body1.addChild(this.body18);
        this.body1.addChild(this.body2);
        this.body1.addChild(this.body7);
        this.body1.addChild(this.body14);
        this.body1.addChild(this.body3);
        this.body1.addChild(this.body15);
        this.body1.addChild(this.body11);
        this.body1.addChild(this.body19);
        this.body1.addChild(this.body16);
        this.body1.addChild(this.body5);
        this.body1.addChild(this.body4);
        this.body1.addChild(this.body6);
        this.body1.addChild(this.body9);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        this.body1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
