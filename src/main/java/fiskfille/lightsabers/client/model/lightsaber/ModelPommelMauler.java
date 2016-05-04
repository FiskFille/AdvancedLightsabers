package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

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
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.outerRing1 = new ModelRenderer(this, 0, 0);
        this.outerRing1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.top7 = new ModelRenderer(this, 8, 0);
        this.top7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top7.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top7, 0.0F, -1.5707963267948966F, 0.0F);
        this.outerRing3 = new ModelRenderer(this, 0, 0);
        this.outerRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(outerRing3, 0.0F, 1.5707963267948966F, 0.0F);
        this.outerRing6 = new ModelRenderer(this, 0, 0);
        this.outerRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(outerRing6, 0.0F, -2.356194490192345F, 0.0F);
        this.innerRing8 = new ModelRenderer(this, 0, 2);
        this.innerRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(innerRing8, 0.0F, -0.7853981633974483F, 0.0F);
        this.innerRing3 = new ModelRenderer(this, 0, 2);
        this.innerRing3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerRing3.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(innerRing3, 0.0F, 1.5707963267948966F, 0.0F);
        this.outerRing8 = new ModelRenderer(this, 0, 0);
        this.outerRing8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing8.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(outerRing8, 0.0F, -0.7853981633974483F, 0.0F);
        this.top1 = new ModelRenderer(this, 8, 0);
        this.top1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top1.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.top3 = new ModelRenderer(this, 8, 0);
        this.top3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top3.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top3, 0.0F, 1.5707963267948966F, 0.0F);
        this.outerRing2 = new ModelRenderer(this, 0, 0);
        this.outerRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(outerRing2, 0.0F, 0.7853981633974483F, 0.0F);
        this.top8 = new ModelRenderer(this, 8, 0);
        this.top8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top8.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top8, 0.0F, -0.7853981633974483F, 0.0F);
        this.outerRing7 = new ModelRenderer(this, 0, 0);
        this.outerRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(outerRing7, 0.0F, -1.5707963267948966F, 0.0F);
        this.innerRing5 = new ModelRenderer(this, 0, 2);
        this.innerRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(innerRing5, 0.0F, 3.141592653589793F, 0.0F);
        this.innerRing2 = new ModelRenderer(this, 0, 2);
        this.innerRing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerRing2.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(innerRing2, 0.0F, 0.7853981633974483F, 0.0F);
        this.top4 = new ModelRenderer(this, 8, 0);
        this.top4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top4.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top4, 0.0F, 2.356194490192345F, 0.0F);
        this.top2 = new ModelRenderer(this, 8, 0);
        this.top2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top2.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top2, 0.0F, 0.7853981633974483F, 0.0F);
        this.top6 = new ModelRenderer(this, 8, 0);
        this.top6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top6.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top6, 0.0F, -2.356194490192345F, 0.0F);
        this.innerRing6 = new ModelRenderer(this, 0, 2);
        this.innerRing6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerRing6.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(innerRing6, 0.0F, -2.356194490192345F, 0.0F);
        this.innerRing4 = new ModelRenderer(this, 0, 2);
        this.innerRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(innerRing4, 0.0F, 2.356194490192345F, 0.0F);
        this.outerRing4 = new ModelRenderer(this, 0, 0);
        this.outerRing4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing4.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(outerRing4, 0.0F, 2.356194490192345F, 0.0F);
        this.innerRing1 = new ModelRenderer(this, 0, 2);
        this.innerRing1.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.innerRing1.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.innerRing7 = new ModelRenderer(this, 0, 2);
        this.innerRing7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerRing7.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(innerRing7, 0.0F, -1.5707963267948966F, 0.0F);
        this.outerRing5 = new ModelRenderer(this, 0, 0);
        this.outerRing5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerRing5.addBox(-1.5F, 0.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(outerRing5, 0.0F, 3.141592653589793F, 0.0F);
        this.top5 = new ModelRenderer(this, 8, 0);
        this.top5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.top5.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        this.setRotateAngle(top5, 0.0F, 3.141592653589793F, 0.0F);
        this.top1.addChild(this.top7);
        this.outerRing1.addChild(this.outerRing3);
        this.outerRing1.addChild(this.outerRing6);
        this.innerRing1.addChild(this.innerRing8);
        this.innerRing1.addChild(this.innerRing3);
        this.outerRing1.addChild(this.outerRing8);
        this.top1.addChild(this.top3);
        this.outerRing1.addChild(this.outerRing2);
        this.top1.addChild(this.top8);
        this.outerRing1.addChild(this.outerRing7);
        this.innerRing1.addChild(this.innerRing5);
        this.innerRing1.addChild(this.innerRing2);
        this.top1.addChild(this.top4);
        this.top1.addChild(this.top2);
        this.top1.addChild(this.top6);
        this.innerRing1.addChild(this.innerRing6);
        this.innerRing1.addChild(this.innerRing4);
        this.outerRing1.addChild(this.outerRing4);
        this.innerRing1.addChild(this.innerRing7);
        this.outerRing1.addChild(this.outerRing5);
        this.top1.addChild(this.top5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        GL11.glPushMatrix();
        GL11.glTranslatef(this.outerRing1.offsetX, this.outerRing1.offsetY, this.outerRing1.offsetZ);
        GL11.glTranslatef(this.outerRing1.rotationPointX * f5, this.outerRing1.rotationPointY * f5, this.outerRing1.rotationPointZ * f5);
        GL11.glScaled(1.0D, 0.25D, 1.0D);
        GL11.glTranslatef(-this.outerRing1.offsetX, -this.outerRing1.offsetY, -this.outerRing1.offsetZ);
        GL11.glTranslatef(-this.outerRing1.rotationPointX * f5, -this.outerRing1.rotationPointY * f5, -this.outerRing1.rotationPointZ * f5);
        this.outerRing1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.top1.offsetX, this.top1.offsetY, this.top1.offsetZ);
        GL11.glTranslatef(this.top1.rotationPointX * f5, this.top1.rotationPointY * f5, this.top1.rotationPointZ * f5);
        GL11.glScaled(0.8D, 1.0D, 0.8D);
        GL11.glTranslatef(-this.top1.offsetX, -this.top1.offsetY, -this.top1.offsetZ);
        GL11.glTranslatef(-this.top1.rotationPointX * f5, -this.top1.rotationPointY * f5, -this.top1.rotationPointZ * f5);
        this.top1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.innerRing1.offsetX, this.innerRing1.offsetY, this.innerRing1.offsetZ);
        GL11.glTranslatef(this.innerRing1.rotationPointX * f5, this.innerRing1.rotationPointY * f5, this.innerRing1.rotationPointZ * f5);
        GL11.glScaled(0.65D, 1.3D, 0.65D);
        GL11.glTranslatef(-this.innerRing1.offsetX, -this.innerRing1.offsetY, -this.innerRing1.offsetZ);
        GL11.glTranslatef(-this.innerRing1.rotationPointX * f5, -this.innerRing1.rotationPointY * f5, -this.innerRing1.rotationPointZ * f5);
        this.innerRing1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
