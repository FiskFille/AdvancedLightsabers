package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBodyMandalorian extends ModelBase
{
	public ModelRenderer body1;
	public ModelRenderer body2;
	public ModelRenderer body4;
	public ModelRenderer body6;
	public ModelRenderer body7;
	public ModelRenderer body8;
	public ModelRenderer body3;
	public ModelRenderer body5;
	public ModelRenderer body9;
	public ModelRenderer body11;
	public ModelRenderer body10;
	public ModelRenderer body12;

	public ModelBodyMandalorian()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.body7 = new ModelRenderer(this, 0, 0);
		this.body7.mirror = true;
		this.body7.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body7.addBox(1.98F, 0.0F, -2.5F, 1, 26, 5, 0.0F);
		this.body10 = new ModelRenderer(this, 20, 0);
		this.body10.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.body10.addBox(-1.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body10, 0.0F, -0.4721115626644662F, 0.0F);
		this.body3 = new ModelRenderer(this, 20, 0);
		this.body3.setRotationPoint(-1.0F, 0.0F, 0.0F);
		this.body3.addBox(-1.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body3, 0.0F, -0.4721115626644662F, 0.0F);
		this.body5 = new ModelRenderer(this, 20, 0);
		this.body5.mirror = true;
		this.body5.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.body5.addBox(0.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body5, 0.0F, 0.4721115626644662F, 0.0F);
		this.body11 = new ModelRenderer(this, 20, 0);
		this.body11.mirror = true;
		this.body11.setRotationPoint(1.5F, 0.0F, 3.76F);
		this.body11.addBox(0.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body11, 0.0F, 0.46949356878647464F, 0.0F);
		this.body4 = new ModelRenderer(this, 20, 0);
		this.body4.mirror = true;
		this.body4.setRotationPoint(1.5F, 0.0F, 3.76F);
		this.body4.addBox(0.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body4, 0.0F, 0.46949356878647464F, 0.0F);
		this.body2 = new ModelRenderer(this, 20, 0);
		this.body2.setRotationPoint(-1.5F, 0.0F, 3.76F);
		this.body2.addBox(-1.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body2, 0.0F, -0.46949356878647464F, 0.0F);
		this.body8 = new ModelRenderer(this, 12, 0);
		this.body8.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body8.addBox(-1.5F, 0.0F, 2.76F, 3, 26, 1, 0.0F);
		this.setRotateAngle(body8, 0.0F, 3.141592653589793F, 0.0F);
		this.body1 = new ModelRenderer(this, 12, 0);
		this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body1.addBox(-1.5F, 0.0F, 2.76F, 3, 26, 1, 0.0F);
		this.body6 = new ModelRenderer(this, 0, 0);
		this.body6.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body6.addBox(-2.98F, 0.0F, -2.5F, 1, 26, 5, 0.0F);
		this.body9 = new ModelRenderer(this, 20, 0);
		this.body9.setRotationPoint(-1.5F, 0.0F, 3.76F);
		this.body9.addBox(-1.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body9, 0.0F, -0.46949356878647464F, 0.0F);
		this.body12 = new ModelRenderer(this, 20, 0);
		this.body12.mirror = true;
		this.body12.setRotationPoint(1.0F, 0.0F, 0.0F);
		this.body12.addBox(0.0F, 0.0F, -1.0F, 1, 26, 1, 0.0F);
		this.setRotateAngle(body12, 0.0F, 0.4721115626644662F, 0.0F);
		this.body1.addChild(this.body7);
		this.body9.addChild(this.body10);
		this.body2.addChild(this.body3);
		this.body4.addChild(this.body5);
		this.body8.addChild(this.body11);
		this.body1.addChild(this.body4);
		this.body1.addChild(this.body2);
		this.body1.addChild(this.body8);
		this.body1.addChild(this.body6);
		this.body8.addChild(this.body9);
		this.body11.addChild(this.body12);
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
