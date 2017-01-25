package fiskfille.lightsabers.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrystal1 extends ModelBase
{
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;

    public ModelCrystal1()
    {
        textureWidth = 64;
        textureHeight = 64;

        Shape3 = new ModelRenderer(this, 0, -2);
        Shape3.setRotationPoint(0.6F, 24.0F, -0.10000000000000026F);
        Shape3.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2);
        setRotation(Shape3, 0.4886921942234039F, 0.0F, 0.3839724063873291F);
        Shape5 = new ModelRenderer(this, 0, -2);
        Shape5.setRotationPoint(0.6F, 24.0F, 0.8999999999999999F);
        Shape5.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2);
        setRotation(Shape5, -0.06981319934129714F, 0.0F, 0.5235987901687622F);
        Shape2 = new ModelRenderer(this, 0, -2);
        Shape2.setRotationPoint(-0.40000000000000013F, 24.0F, -0.10000000000000026F);
        Shape2.addBox(-1.0F, -9.0F, -1.0F, 2, 9, 2);
        setRotation(Shape2, -0.052359901368618005F, 0.0F, 0.10471980273723601F);
        Shape4 = new ModelRenderer(this, 0, -2);
        Shape4.setRotationPoint(-0.40000000000000013F, 24.0F, -0.10000000000000026F);
        Shape4.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2);
        setRotation(Shape4, -0.3141593039035797F, 0.0F, -0.4014256894588471F);
        Shape6 = new ModelRenderer(this, 0, -2);
        Shape6.setRotationPoint(0.6F, 24.0F, 0.8999999999999999F);
        Shape6.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2);
        setRotation(Shape6, -0.6806784272193909F, 0.0F, -0.22689279913902283F);
        Shape1 = new ModelRenderer(this, 0, -2);
        Shape1.setRotationPoint(-0.40000000000000013F, 24.0F, -1.0999999999999994F);
        Shape1.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2);
        setRotation(Shape1, 0.24434609711170194F, 0.0F, -0.24434609711170194F);
    }

    public void renderAll()
    {
        float f5 = 0.0625F;
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
        Shape4.render(f5);
        Shape5.render(f5);
        Shape6.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }
}
