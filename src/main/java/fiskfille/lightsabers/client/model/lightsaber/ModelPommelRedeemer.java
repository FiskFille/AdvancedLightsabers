package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelPommelRedeemer extends ModelBase
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

    public ModelPommelRedeemer()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.top1 = new ModelRenderer(this, 10, 0);
        this.top1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top1.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.body2 = new ModelRenderer(this, 0, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body2.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        this.body8 = new ModelRenderer(this, 0, 0);
        this.body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body8.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        this.top5 = new ModelRenderer(this, 10, 0);
        this.top5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top5.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top5, 0.0F, 3.141592653589793F, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 0);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.body5 = new ModelRenderer(this, 0, 0);
        this.body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body5.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        this.top2 = new ModelRenderer(this, 10, 0);
        this.top2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top2.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top2, 0.0F, 0.7853981633974483F, 0.0F);
        this.body3 = new ModelRenderer(this, 0, 0);
        this.body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body3.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        this.body6 = new ModelRenderer(this, 0, 0);
        this.body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body6.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        this.top7 = new ModelRenderer(this, 10, 0);
        this.top7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top7.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top7, 0.0F, -1.5707963267948966F, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 0);
        this.body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body4.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        this.top3 = new ModelRenderer(this, 10, 0);
        this.top3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top3.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top3, 0.0F, 1.5707963267948966F, 0.0F);
        this.top4 = new ModelRenderer(this, 10, 0);
        this.top4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top4.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top4, 0.0F, 2.356194490192345F, 0.0F);
        this.top8 = new ModelRenderer(this, 10, 0);
        this.top8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top8.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top8, 0.0F, -0.7853981633974483F, 0.0F);
        this.top6 = new ModelRenderer(this, 10, 0);
        this.top6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top6.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top6, 0.0F, -2.356194490192345F, 0.0F);
        this.body7 = new ModelRenderer(this, 0, 0);
        this.body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body7.addBox(-1.5F, 0.0F, -0.38F, 3, 1, 4, 0.0F);
        this.setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        this.body1.addChild(this.body2);
        this.body1.addChild(this.body8);
        this.top1.addChild(this.top5);
        this.body1.addChild(this.body5);
        this.top1.addChild(this.top2);
        this.body1.addChild(this.body3);
        this.body1.addChild(this.body6);
        this.top1.addChild(this.top7);
        this.body1.addChild(this.body4);
        this.top1.addChild(this.top3);
        this.top1.addChild(this.top4);
        this.top1.addChild(this.top8);
        this.top1.addChild(this.top6);
        this.body1.addChild(this.body7);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        GL11.glPushMatrix();
        GL11.glTranslatef(this.top1.offsetX, this.top1.offsetY, this.top1.offsetZ);
        GL11.glTranslatef(this.top1.rotationPointX * f5, this.top1.rotationPointY * f5, this.top1.rotationPointZ * f5);
        GL11.glScaled(0.8D, 1.0D, 0.8D);
        GL11.glTranslatef(-this.top1.offsetX, -this.top1.offsetY, -this.top1.offsetZ);
        GL11.glTranslatef(-this.top1.rotationPointX * f5, -this.top1.rotationPointY * f5, -this.top1.rotationPointZ * f5);
        this.top1.render(f5);
        GL11.glPopMatrix();
        this.body1.render(f5);
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
