package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelPommelJuggernaut extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer cap1;
    public ModelRenderer body2;
    public ModelRenderer body4;
    public ModelRenderer body8;
    public ModelRenderer body10;
    public ModelRenderer body14;
    public ModelRenderer body16;
    public ModelRenderer body20;
    public ModelRenderer body22;
    public ModelRenderer body23;
    public ModelRenderer body3;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body9;
    public ModelRenderer body11;
    public ModelRenderer body12;
    public ModelRenderer body13;
    public ModelRenderer body15;
    public ModelRenderer body17;
    public ModelRenderer body18;
    public ModelRenderer body19;
    public ModelRenderer body21;
    public ModelRenderer body24;
    public ModelRenderer cap2;
    public ModelRenderer cap3;
    public ModelRenderer cap4;

    public ModelPommelJuggernaut()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body1 = new ModelRenderer(this, 0, 0);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.body16 = new ModelRenderer(this, 0, 0);
        this.body16.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body16.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.setRotateAngle(body16, 0.0F, -1.5707963267948966F, 0.0F);
        this.body3 = new ModelRenderer(this, 0, 9);
        this.body3.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body3.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body3, -1.0471975511965976F, 0.0F, 0.0F);
        this.cap2 = new ModelRenderer(this, 0, 18);
        this.cap2.setRotationPoint(0.0F, 1.3F, 0.0F);
        this.cap2.addBox(-4.0F, 0.0F, -0.5F, 8, 1, 1, 0.0F);
        this.body12 = new ModelRenderer(this, 14, 0);
        this.body12.setRotationPoint(0.0F, 3.0F, 3.12F);
        this.body12.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 0);
        this.body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body4.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.setRotateAngle(body4, 0.0F, 1.5707963267948966F, 0.0F);
        this.body22 = new ModelRenderer(this, 0, 9);
        this.body22.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body22.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body22, -1.0471975511965976F, 0.0F, 0.0F);
        this.cap3 = new ModelRenderer(this, 0, 14);
        this.cap3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cap3.addBox(-3.5F, 0.0F, -1.0F, 7, 2, 2, 0.0F);
        this.setRotateAngle(cap3, 0.0F, 1.5707963267948966F, 0.0F);
        this.body19 = new ModelRenderer(this, 14, 7);
        this.body19.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body19.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        this.body9 = new ModelRenderer(this, 0, 9);
        this.body9.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body9.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body9, -1.0471975511965976F, 0.0F, 0.0F);
        this.body6 = new ModelRenderer(this, 14, 0);
        this.body6.setRotationPoint(0.0F, 3.0F, 3.12F);
        this.body6.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        this.body15 = new ModelRenderer(this, 0, 9);
        this.body15.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body15.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body15, -1.0471975511965976F, 0.0F, 0.0F);
        this.body11 = new ModelRenderer(this, 0, 9);
        this.body11.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body11.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body11, -1.0471975511965976F, 0.0F, 0.0F);
        this.body18 = new ModelRenderer(this, 14, 0);
        this.body18.setRotationPoint(0.0F, 3.0F, 3.12F);
        this.body18.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        this.body14 = new ModelRenderer(this, 0, 0);
        this.body14.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body14.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.setRotateAngle(body14, 0.0F, -2.356194490192345F, 0.0F);
        this.body13 = new ModelRenderer(this, 14, 7);
        this.body13.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body13.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        this.body23 = new ModelRenderer(this, 14, 0);
        this.body23.setRotationPoint(0.0F, 3.0F, 3.12F);
        this.body23.addBox(-1.0F, -3.0F, 0.0F, 2, 6, 1, 0.0F);
        this.cap4 = new ModelRenderer(this, 0, 18);
        this.cap4.setRotationPoint(0.0F, 1.3F, 0.0F);
        this.cap4.addBox(-4.0F, 0.0F, -0.5F, 8, 1, 1, 0.0F);
        this.body8 = new ModelRenderer(this, 0, 0);
        this.body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body8.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.setRotateAngle(body8, 0.0F, 2.356194490192345F, 0.0F);
        this.body2 = new ModelRenderer(this, 0, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body2.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        this.body17 = new ModelRenderer(this, 0, 9);
        this.body17.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body17.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body17, -1.0471975511965976F, 0.0F, 0.0F);
        this.cap1 = new ModelRenderer(this, 0, 14);
        this.cap1.setRotationPoint(0.0F, 4.7F, 0.0F);
        this.cap1.addBox(-3.5F, 0.0F, -1.0F, 7, 2, 2, 0.0F);
        this.body7 = new ModelRenderer(this, 14, 7);
        this.body7.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body7.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        this.body21 = new ModelRenderer(this, 0, 9);
        this.body21.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body21.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body21, -1.0471975511965976F, 0.0F, 0.0F);
        this.body10 = new ModelRenderer(this, 0, 0);
        this.body10.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body10.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.setRotateAngle(body10, 0.0F, 3.141592653589793F, 0.0F);
        this.body24 = new ModelRenderer(this, 14, 7);
        this.body24.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.body24.addBox(-0.5F, -3.0F, 0.0F, 1, 6, 1, 0.0F);
        this.body5 = new ModelRenderer(this, 0, 9);
        this.body5.setRotationPoint(0.0F, 5.0F, 3.62F);
        this.body5.addBox(-1.5F, 0.0F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body5, -1.0471975511965976F, 0.0F, 0.0F);
        this.body20 = new ModelRenderer(this, 0, 0);
        this.body20.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body20.addBox(-1.5F, 0.0F, -0.38F, 3, 5, 4, 0.0F);
        this.setRotateAngle(body20, 0.0F, -0.7853981633974483F, 0.0F);
        this.body1.addChild(this.body16);
        this.body2.addChild(this.body3);
        this.cap1.addChild(this.cap2);
        this.body10.addChild(this.body12);
        this.body1.addChild(this.body4);
        this.body1.addChild(this.body22);
        this.cap1.addChild(this.cap3);
        this.body18.addChild(this.body19);
        this.body8.addChild(this.body9);
        this.body4.addChild(this.body6);
        this.body14.addChild(this.body15);
        this.body10.addChild(this.body11);
        this.body16.addChild(this.body18);
        this.body1.addChild(this.body14);
        this.body12.addChild(this.body13);
        this.body1.addChild(this.body23);
        this.cap3.addChild(this.cap4);
        this.body1.addChild(this.body8);
        this.body1.addChild(this.body2);
        this.body16.addChild(this.body17);
        this.body6.addChild(this.body7);
        this.body20.addChild(this.body21);
        this.body1.addChild(this.body10);
        this.body23.addChild(this.body24);
        this.body4.addChild(this.body5);
        this.body1.addChild(this.body20);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	GL11.glPushMatrix();
    	GL11.glRotatef(90, 0, 1, 0);
        this.body1.render(f5);
        this.cap1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
