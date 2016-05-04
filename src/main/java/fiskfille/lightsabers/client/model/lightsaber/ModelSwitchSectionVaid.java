package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelSwitchSectionVaid extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer upperButton1;
    public ModelRenderer lowerButton1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer upperButton2;
    public ModelRenderer upperButton3;
    public ModelRenderer upperButton4;
    public ModelRenderer upperButton5;
    public ModelRenderer upperButton6;
    public ModelRenderer upperButton7;
    public ModelRenderer upperButton8;
    public ModelRenderer lowerButton2;
    public ModelRenderer lowerButton3;
    public ModelRenderer lowerButton4;
    public ModelRenderer lowerButton5;
    public ModelRenderer lowerButton6;
    public ModelRenderer lowerButton7;
    public ModelRenderer lowerButton8;

    public ModelSwitchSectionVaid()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.upperButton6 = new ModelRenderer(this, 8, 0);
        this.upperButton6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperButton6.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton6, 0.0F, -2.356194490192345F, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 0);
        this.body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body4.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        this.lowerButton5 = new ModelRenderer(this, 0, 9);
        this.lowerButton5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lowerButton5.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton5, 0.0F, 3.141592653589793F, 0.0F);
        this.body7 = new ModelRenderer(this, 0, 0);
        this.body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body7.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        this.body2 = new ModelRenderer(this, 0, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body2.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        this.upperButton8 = new ModelRenderer(this, 8, 0);
        this.upperButton8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperButton8.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton8, 0.0F, -0.7853981633974483F, 0.0F);
        this.body3 = new ModelRenderer(this, 0, 0);
        this.body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body3.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        this.upperButton2 = new ModelRenderer(this, 8, 0);
        this.upperButton2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperButton2.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton2, 0.0F, 0.7853981633974483F, 0.0F);
        this.upperButton3 = new ModelRenderer(this, 8, 0);
        this.upperButton3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperButton3.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton3, 0.0F, 1.5707963267948966F, 0.0F);
        this.body5 = new ModelRenderer(this, 0, 0);
        this.body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body5.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        this.upperButton7 = new ModelRenderer(this, 8, 0);
        this.upperButton7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperButton7.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton7, 0.0F, -1.5707963267948966F, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 0);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.body8 = new ModelRenderer(this, 0, 0);
        this.body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body8.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        this.lowerButton7 = new ModelRenderer(this, 0, 9);
        this.lowerButton7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lowerButton7.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton7, 0.0F, -1.5707963267948966F, 0.0F);
        this.lowerButton6 = new ModelRenderer(this, 0, 9);
        this.lowerButton6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lowerButton6.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton6, 0.0F, -2.356194490192345F, 0.0F);
        this.body6 = new ModelRenderer(this, 0, 0);
        this.body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body6.addBox(-1.5F, -8.0F, 2.62F, 3, 8, 1, 0.0F);
        this.setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        this.upperButton4 = new ModelRenderer(this, 8, 0);
        this.upperButton4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperButton4.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton4, 0.0F, 2.356194490192345F, 0.0F);
        this.lowerButton8 = new ModelRenderer(this, 0, 9);
        this.lowerButton8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lowerButton8.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton8, 0.0F, -0.7853981633974483F, 0.0F);
        this.lowerButton3 = new ModelRenderer(this, 0, 9);
        this.lowerButton3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lowerButton3.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton3, 0.0F, 1.5707963267948966F, 0.0F);
        this.upperButton5 = new ModelRenderer(this, 8, 0);
        this.upperButton5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.upperButton5.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton5, 0.0F, 3.141592653589793F, 0.0F);
        this.lowerButton2 = new ModelRenderer(this, 0, 9);
        this.lowerButton2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lowerButton2.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton2, 0.0F, 0.7853981633974483F, 0.0F);
        this.upperButton1 = new ModelRenderer(this, 8, 0);
        this.upperButton1.setRotationPoint(-3.8F, -6.2F, 0.0F);
        this.upperButton1.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(upperButton1, 0.0F, 0.0F, -1.5707963267948966F);
        this.lowerButton4 = new ModelRenderer(this, 0, 9);
        this.lowerButton4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lowerButton4.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton4, 0.0F, 2.356194490192345F, 0.0F);
        this.lowerButton1 = new ModelRenderer(this, 0, 9);
        this.lowerButton1.setRotationPoint(-3.8F, -3.0F, 0.0F);
        this.lowerButton1.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(lowerButton1, 0.0F, 0.0F, -1.5707963267948966F);
        this.upperButton1.addChild(this.upperButton6);
        this.body1.addChild(this.body4);
        this.lowerButton1.addChild(this.lowerButton5);
        this.body1.addChild(this.body7);
        this.body1.addChild(this.body2);
        this.upperButton1.addChild(this.upperButton8);
        this.body1.addChild(this.body3);
        this.upperButton1.addChild(this.upperButton2);
        this.upperButton1.addChild(this.upperButton3);
        this.body1.addChild(this.body5);
        this.upperButton1.addChild(this.upperButton7);
        this.body1.addChild(this.body8);
        this.lowerButton1.addChild(this.lowerButton7);
        this.lowerButton1.addChild(this.lowerButton6);
        this.body1.addChild(this.body6);
        this.upperButton1.addChild(this.upperButton4);
        this.lowerButton1.addChild(this.lowerButton8);
        this.lowerButton1.addChild(this.lowerButton3);
        this.upperButton1.addChild(this.upperButton5);
        this.lowerButton1.addChild(this.lowerButton2);
        this.lowerButton1.addChild(this.lowerButton4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        GL11.glPushMatrix();
        GL11.glTranslatef(this.body1.offsetX, this.body1.offsetY, this.body1.offsetZ);
        GL11.glTranslatef(this.body1.rotationPointX * f5, this.body1.rotationPointY * f5, this.body1.rotationPointZ * f5);
        GL11.glScaled(1.15D, 1.15D, 1.15D);
        GL11.glTranslatef(-this.body1.offsetX, -this.body1.offsetY, -this.body1.offsetZ);
        GL11.glTranslatef(-this.body1.rotationPointX * f5, -this.body1.rotationPointY * f5, -this.body1.rotationPointZ * f5);
        this.body1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.upperButton1.offsetX, this.upperButton1.offsetY, this.upperButton1.offsetZ);
        GL11.glTranslatef(this.upperButton1.rotationPointX * f5, this.upperButton1.rotationPointY * f5, this.upperButton1.rotationPointZ * f5);
        GL11.glScaled(0.3D, 0.3D, 0.3D);
        GL11.glTranslatef(-this.upperButton1.offsetX, -this.upperButton1.offsetY, -this.upperButton1.offsetZ);
        GL11.glTranslatef(-this.upperButton1.rotationPointX * f5, -this.upperButton1.rotationPointY * f5, -this.upperButton1.rotationPointZ * f5);
        this.upperButton1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.lowerButton1.offsetX, this.lowerButton1.offsetY, this.lowerButton1.offsetZ);
        GL11.glTranslatef(this.lowerButton1.rotationPointX * f5, this.lowerButton1.rotationPointY * f5, this.lowerButton1.rotationPointZ * f5);
        GL11.glScaled(0.3D, 0.3D, 0.3D);
        GL11.glTranslatef(-this.lowerButton1.offsetX, -this.lowerButton1.offsetY, -this.lowerButton1.offsetZ);
        GL11.glTranslatef(-this.lowerButton1.rotationPointX * f5, -this.lowerButton1.rotationPointY * f5, -this.lowerButton1.rotationPointZ * f5);
        this.lowerButton1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
