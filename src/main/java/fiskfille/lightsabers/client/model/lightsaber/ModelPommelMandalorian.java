package fiskfille.lightsabers.client.model.lightsaber;

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
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.body18 = new ModelRenderer(this, 0, 14);
		this.body18.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body18.addBox(-2.98F, 0.0F, -2.5F, 3, 1, 5, 0.0F);
		this.body19 = new ModelRenderer(this, 0, 14);
		this.body19.mirror = true;
		this.body19.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body19.addBox(-0.02F, 0.0F, -2.5F, 3, 1, 5, 0.0F);
		this.body1 = new ModelRenderer(this, 0, 9);
		this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body1.addBox(-1.5F, 0.0F, 2.76F, 3, 4, 1, 0.0F);
		this.body13 = new ModelRenderer(this, 0, 20);
		this.body13.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.body13.addBox(-1.5F, 0.0F, 1.76F, 3, 1, 2, 0.0F);
		this.body20 = new ModelRenderer(this, 0, 20);
		this.body20.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body20.addBox(-1.5F, 0.0F, 1.76F, 3, 1, 2, 0.0F);
		this.setRotateAngle(body20, 0.0F, 3.141592653589793F, 0.0F);
		this.body3 = new ModelRenderer(this, 0, 0);
		this.body3.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.body3.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body3, 0.0F, -0.4721115626644662F, 0.0F);
		this.body9 = new ModelRenderer(this, 0, 0);
		this.body9.setRotationPoint(-1.5F, 0.0F, 3.76F);
		this.body9.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body9, 0.0F, -0.46949356878647464F, 0.0F);
		this.body17 = new ModelRenderer(this, 11, 12);
		this.body17.mirror = true;
		this.body17.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.body17.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body17, 0.0F, 0.4721115626644662F, 0.0F);
		this.body12 = new ModelRenderer(this, 0, 0);
		this.body12.mirror = true;
		this.body12.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.body12.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body12, 0.0F, 0.4721115626644662F, 0.0F);
		this.body23 = new ModelRenderer(this, 11, 12);
		this.body23.mirror = true;
		this.body23.setRotationPoint(1.5F, 0.5F, 3.76F);
		this.body23.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body23, 0.0F, 0.46949356878647464F, 0.0F);
		this.body10 = new ModelRenderer(this, 0, 0);
		this.body10.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.body10.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body10, 0.0F, -0.4721115626644662F, 0.0F);
		this.body22 = new ModelRenderer(this, 11, 12);
		this.body22.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.body22.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body22, 0.0F, -0.4721115626644662F, 0.0F);
		this.body8 = new ModelRenderer(this, 0, 9);
		this.body8.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body8.addBox(-1.5F, 0.0F, 2.76F, 3, 4, 1, 0.0F);
		this.setRotateAngle(body8, 0.0F, 3.141592653589793F, 0.0F);
		this.body11 = new ModelRenderer(this, 0, 0);
		this.body11.mirror = true;
		this.body11.setRotationPoint(1.5F, 0.0F, 3.76F);
		this.body11.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body11, 0.0F, 0.46949356878647464F, 0.0F);
		this.body7 = new ModelRenderer(this, 0, 0);
		this.body7.mirror = true;
		this.body7.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body7.addBox(1.98F, 0.0F, -2.5F, 1, 4, 5, 0.0F);
		this.body14 = new ModelRenderer(this, 11, 12);
		this.body14.setRotationPoint(-1.5F, 0.5F, 3.76F);
		this.body14.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body14, 0.0F, -0.46949356878647464F, 0.0F);
		this.body25 = new ModelRenderer(this, 8, 5);
		this.body25.setRotationPoint(0.0F, 0.45F, -0.75F);
		this.body25.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 4, 0.0F);
		this.body26 = new ModelRenderer(this, 7, 0);
		this.body26.setRotationPoint(0.0F, 3.0F, 2.0F);
		this.body26.addBox(-1.5F, -2.0F, 0.0F, 3, 2, 3, 0.0F);
		this.setRotateAngle(body26, 0.9599310885968813F, 0.0F, 0.0F);
		this.body4 = new ModelRenderer(this, 0, 0);
		this.body4.mirror = true;
		this.body4.setRotationPoint(1.5F, 0.0F, 3.76F);
		this.body4.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body4, 0.0F, 0.46949356878647464F, 0.0F);
		this.body15 = new ModelRenderer(this, 11, 12);
		this.body15.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.body15.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body15, 0.0F, -0.4721115626644662F, 0.0F);
		this.body2 = new ModelRenderer(this, 0, 0);
		this.body2.setRotationPoint(-1.5F, 0.0F, 3.76F);
		this.body2.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body2, 0.0F, -0.46949356878647464F, 0.0F);
		this.body6 = new ModelRenderer(this, 0, 0);
		this.body6.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body6.addBox(-2.98F, 0.0F, -2.5F, 1, 4, 5, 0.0F);
		this.body5 = new ModelRenderer(this, 0, 0);
		this.body5.mirror = true;
		this.body5.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.body5.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(body5, 0.0F, 0.4721115626644662F, 0.0F);
		this.body21 = new ModelRenderer(this, 11, 12);
		this.body21.setRotationPoint(-1.5F, 0.5F, 3.76F);
		this.body21.addBox(-1.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body21, 0.0F, -0.46949356878647464F, 0.0F);
		this.body24 = new ModelRenderer(this, 11, 12);
		this.body24.mirror = true;
		this.body24.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.body24.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body24, 0.0F, 0.4721115626644662F, 0.0F);
		this.body16 = new ModelRenderer(this, 11, 12);
		this.body16.mirror = true;
		this.body16.setRotationPoint(1.5F, 0.5F, 3.76F);
		this.body16.addBox(0.0F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(body16, 0.0F, 0.46949356878647464F, 0.0F);
		this.body13.addChild(this.body18);
		this.body13.addChild(this.body19);
		this.body1.addChild(this.body13);
		this.body13.addChild(this.body20);
		this.body2.addChild(this.body3);
		this.body8.addChild(this.body9);
		this.body16.addChild(this.body17);
		this.body11.addChild(this.body12);
		this.body20.addChild(this.body23);
		this.body9.addChild(this.body10);
		this.body21.addChild(this.body22);
		this.body1.addChild(this.body8);
		this.body8.addChild(this.body11);
		this.body1.addChild(this.body7);
		this.body13.addChild(this.body14);
		this.body13.addChild(this.body25);
		this.body25.addChild(this.body26);
		this.body1.addChild(this.body4);
		this.body14.addChild(this.body15);
		this.body1.addChild(this.body2);
		this.body1.addChild(this.body6);
		this.body4.addChild(this.body5);
		this.body20.addChild(this.body21);
		this.body23.addChild(this.body24);
		this.body13.addChild(this.body16);
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
