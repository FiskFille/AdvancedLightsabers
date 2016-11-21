package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelEmitterMandalorian extends ModelBase
{
	public ModelRenderer body1;
	public ModelRenderer peg1;
	public ModelRenderer body2;
	public ModelRenderer body3;
	public ModelRenderer body6;
	public ModelRenderer body9;
	public ModelRenderer body14;
	public ModelRenderer body4;
	public ModelRenderer body5;
	public ModelRenderer body7;
	public ModelRenderer body8;
	public ModelRenderer body10;
	public ModelRenderer body13;
	public ModelRenderer body11;
	public ModelRenderer body12;
	public ModelRenderer body15;
	public ModelRenderer body18;
	public ModelRenderer body16;
	public ModelRenderer body17;
	public ModelRenderer peg2;
	public ModelRenderer peg3;
	public ModelRenderer peg4;
	public ModelRenderer peg5;
	public ModelRenderer peg6;
	public ModelRenderer peg7;
	public ModelRenderer peg8;
	public ModelRenderer peg9;
	public ModelRenderer peg10;

	public ModelEmitterMandalorian()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.peg6 = new ModelRenderer(this, 14, 15);
		this.peg6.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg6.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.body14 = new ModelRenderer(this, 0, 0);
		this.body14.mirror = true;
		this.body14.setRotationPoint(2.5F, -12.72F, 3.5F);
		this.body14.addBox(-0.5F, -3.0F, -1.5F, 1, 3, 2, 0.0F);
		this.body15 = new ModelRenderer(this, 12, 0);
		this.body15.mirror = true;
		this.body15.setRotationPoint(0.0F, -3.0F, 0.5F);
		this.body15.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(body15, -1.1248647029103453F, 0.0F, 0.0F);
		this.peg1 = new ModelRenderer(this, 14, 15);
		this.peg1.setRotationPoint(0.0F, -0.9299999999999997F, -1.1F);
		this.peg1.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.body13 = new ModelRenderer(this, 8, 2);
		this.body13.setRotationPoint(0.0F, 0.0F, -2.0F);
		this.body13.addBox(-0.5F, -1.0F, -1.5F, 1, 1, 2, 0.0F);
		this.body17 = new ModelRenderer(this, 8, 0);
		this.body17.mirror = true;
		this.body17.setRotationPoint(0.0F, 6.39F, 0.0F);
		this.body17.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
		this.body10 = new ModelRenderer(this, 12, 0);
		this.body10.setRotationPoint(0.0F, -3.0F, 0.5F);
		this.body10.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(body10, -1.1248647029103453F, 0.0F, 0.0F);
		this.body7 = new ModelRenderer(this, 14, 0);
		this.body7.mirror = true;
		this.body7.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body7.addBox(-0.6F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
		this.setRotateAngle(body7, 0.0F, -0.7853981633974483F, 0.0F);
		this.body8 = new ModelRenderer(this, 14, 0);
		this.body8.mirror = true;
		this.body8.setRotationPoint(0.0F, 0.0F, -6.0F);
		this.body8.addBox(-0.6F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
		this.setRotateAngle(body8, 0.0F, 0.7853981633974483F, 0.0F);
		this.body16 = new ModelRenderer(this, 0, 19);
		this.body16.mirror = true;
		this.body16.setRotationPoint(0.0F, 1.0F, -0.5F);
		this.body16.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 2, 0.0F);
		this.body3 = new ModelRenderer(this, 0, 0);
		this.body3.setRotationPoint(-2.0F, 0.0F, 3.0F);
		this.body3.addBox(-1.0F, -13.0F, -6.0F, 1, 13, 6, 0.0F);
		this.body5 = new ModelRenderer(this, 14, 0);
		this.body5.setRotationPoint(0.0F, 0.0F, -6.0F);
		this.body5.addBox(-0.4F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
		this.setRotateAngle(body5, 0.0F, -0.7853981633974483F, 0.0F);
		this.peg2 = new ModelRenderer(this, 14, 15);
		this.peg2.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg2.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.peg10 = new ModelRenderer(this, 14, 15);
		this.peg10.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg10.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.body9 = new ModelRenderer(this, 0, 0);
		this.body9.setRotationPoint(-2.5F, -12.72F, 3.5F);
		this.body9.addBox(-0.5F, -3.0F, -1.5F, 1, 3, 2, 0.0F);
		this.peg3 = new ModelRenderer(this, 14, 15);
		this.peg3.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg3.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.body18 = new ModelRenderer(this, 8, 2);
		this.body18.mirror = true;
		this.body18.setRotationPoint(0.0F, 0.0F, -2.0F);
		this.body18.addBox(-0.5F, -1.0F, -1.5F, 1, 1, 2, 0.0F);
		this.body11 = new ModelRenderer(this, 0, 19);
		this.body11.setRotationPoint(0.0F, 1.0F, -0.5F);
		this.body11.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 2, 0.0F);
		this.peg8 = new ModelRenderer(this, 14, 15);
		this.peg8.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg8.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.peg9 = new ModelRenderer(this, 14, 15);
		this.peg9.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg9.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.peg5 = new ModelRenderer(this, 14, 15);
		this.peg5.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg5.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.body2 = new ModelRenderer(this, 20, 0);
		this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body2.addBox(-3.0F, -13.0F, 3.0F, 6, 13, 1, 0.0F);
		this.setRotateAngle(body2, 0.0F, 3.141592653589793F, 0.0F);
		this.peg4 = new ModelRenderer(this, 14, 15);
		this.peg4.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg4.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.body1 = new ModelRenderer(this, 20, 0);
		this.body1.setRotationPoint(0.0F, 0.0F, 0.2F);
		this.body1.addBox(-3.0F, -13.0F, 3.0F, 6, 13, 1, 0.0F);
		this.peg7 = new ModelRenderer(this, 14, 15);
		this.peg7.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.peg7.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 3, 0.0F);
		this.body12 = new ModelRenderer(this, 8, 0);
		this.body12.setRotationPoint(0.0F, 6.39F, 0.0F);
		this.body12.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
		this.body6 = new ModelRenderer(this, 0, 0);
		this.body6.mirror = true;
		this.body6.setRotationPoint(2.0F, 0.0F, 3.0F);
		this.body6.addBox(0.0F, -13.0F, -6.0F, 1, 13, 6, 0.0F);
		this.body4 = new ModelRenderer(this, 14, 0);
		this.body4.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body4.addBox(-0.4F, -13.0F, -1.0F, 1, 13, 2, 0.0F);
		this.setRotateAngle(body4, 0.0F, 0.7853981633974483F, 0.0F);
		this.peg5.addChild(this.peg6);
		this.body1.addChild(this.body14);
		this.body14.addChild(this.body15);
		this.body9.addChild(this.body13);
		this.body16.addChild(this.body17);
		this.body9.addChild(this.body10);
		this.body6.addChild(this.body7);
		this.body6.addChild(this.body8);
		this.body15.addChild(this.body16);
		this.body1.addChild(this.body3);
		this.body3.addChild(this.body5);
		this.peg1.addChild(this.peg2);
		this.peg9.addChild(this.peg10);
		this.body1.addChild(this.body9);
		this.peg2.addChild(this.peg3);
		this.body14.addChild(this.body18);
		this.body10.addChild(this.body11);
		this.peg7.addChild(this.peg8);
		this.peg8.addChild(this.peg9);
		this.peg4.addChild(this.peg5);
		this.body1.addChild(this.body2);
		this.peg3.addChild(this.peg4);
		this.peg6.addChild(this.peg7);
		this.body11.addChild(this.body12);
		this.body1.addChild(this.body6);
		this.body3.addChild(this.body4);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{ 
		GL11.glPushMatrix();
		GL11.glTranslatef(this.peg1.offsetX, this.peg1.offsetY, this.peg1.offsetZ);
		GL11.glTranslatef(this.peg1.rotationPointX * f5, this.peg1.rotationPointY * f5, this.peg1.rotationPointZ * f5);
		GL11.glScaled(0.9D, 0.5D, 1.0D);
		GL11.glTranslatef(-this.peg1.offsetX, -this.peg1.offsetY, -this.peg1.offsetZ);
		GL11.glTranslatef(-this.peg1.rotationPointX * f5, -this.peg1.rotationPointY * f5, -this.peg1.rotationPointZ * f5);
		this.peg1.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.body1.offsetX, this.body1.offsetY, this.body1.offsetZ);
		GL11.glTranslatef(this.body1.rotationPointX * f5, this.body1.rotationPointY * f5, this.body1.rotationPointZ * f5);
		GL11.glScaled(0.92D, 0.85D, 0.9D);
		GL11.glTranslatef(-this.body1.offsetX, -this.body1.offsetY, -this.body1.offsetZ);
		GL11.glTranslatef(-this.body1.rotationPointX * f5, -this.body1.rotationPointY * f5, -this.body1.rotationPointZ * f5);
		this.body1.render(f5);
		GL11.glPopMatrix();
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
