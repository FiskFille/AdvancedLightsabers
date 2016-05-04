package fiskfille.lightsabers.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrystal2 extends ModelBase
{
	public ModelRenderer shape1;
	public ModelRenderer shape2;
	public ModelRenderer shape3;
	public ModelRenderer shape4;
	public ModelRenderer shape5;
	public ModelRenderer shape6;

	public ModelCrystal2()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.shape2 = new ModelRenderer(this, 0, 0);
		this.shape2.setRotationPoint(-0.7999999999999998F, 24.3F, -0.8999999999999999F);
		this.shape2.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1);
		this.setRotateAngle(shape2, 0.3490658503988659F, 0.33161255787892263F, -0.06981317007977318F);
		this.shape6 = new ModelRenderer(this, 0, 0);
		this.shape6.setRotationPoint(0.5000000000000001F, 24.0F, -1.5F);
		this.shape6.addBox(-0.5F, -3.0F, -0.5F, 2, 3, 2);
		this.setRotateAngle(shape6, 0.20943951023931953F, -0.8203047484373349F, 0.12217304763960307F);
		this.shape4 = new ModelRenderer(this, 0, 0);
		this.shape4.setRotationPoint(-8.326672684688674E-17F, 24.3F, 1.0F);
		this.shape4.addBox(-0.5F, -4.0F, -1.0F, 1, 4, 2);
		this.setRotateAngle(shape4, -0.24434609527920614F, 1.1693705988362009F, 0.3490658503988659F);
		this.shape1 = new ModelRenderer(this, 0, 0);
		this.shape1.setRotationPoint(-1.0000000000000002F, 24.3F, 2.7755575615628914E-17F);
		this.shape1.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2);
		this.setRotateAngle(shape1, -0.17453292519943295F, 0.0F, -0.17453292519943295F);
		this.shape5 = new ModelRenderer(this, 0, 0);
		this.shape5.setRotationPoint(-8.326672684688674E-17F, 24.3F, 0.5F);
		this.shape5.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1);
		this.setRotateAngle(shape5, -0.5235987755982988F, -0.40142572795869574F, 0.12217304763960307F);
		this.shape3 = new ModelRenderer(this, 0, 0);
		this.shape3.setRotationPoint(-8.326672684688674E-17F, 24.3F, 2.7755575615628914E-17F);
		this.shape3.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2);
		this.setRotateAngle(shape3, 0.08726646259971647F, 0.0F, 0.10471975511965977F);
	}

	public void renderAll()
	{
		float f5 = 0.0625F;
		this.shape2.render(f5);
		this.shape6.render(f5);
		this.shape4.render(f5);
		this.shape1.render(f5);
		this.shape5.render(f5);
		this.shape3.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}