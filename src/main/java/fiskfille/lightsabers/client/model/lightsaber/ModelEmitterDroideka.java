package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelEmitterDroideka extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer ring1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer ring2;
    public ModelRenderer ring3;
    public ModelRenderer ring4;
    public ModelRenderer ring5;
    public ModelRenderer ring6;
    public ModelRenderer ring7;
    public ModelRenderer ring8;

    public ModelEmitterDroideka()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body6 = new ModelRenderer(this, 0, 0);
        this.body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body6.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        this.body8 = new ModelRenderer(this, 0, 0);
        this.body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body8.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        this.ring6 = new ModelRenderer(this, 0, 5);
        this.ring6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring6.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.setRotateAngle(ring6, 0.0F, -2.356194490192345F, 0.0F);
        this.ring3 = new ModelRenderer(this, 0, 5);
        this.ring3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring3.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.setRotateAngle(ring3, 0.0F, 1.5707963267948966F, 0.0F);
        this.ring8 = new ModelRenderer(this, 0, 5);
        this.ring8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring8.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.setRotateAngle(ring8, 0.0F, -0.7853981633974483F, 0.0F);
        this.ring7 = new ModelRenderer(this, 0, 5);
        this.ring7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring7.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.setRotateAngle(ring7, 0.0F, -1.5707963267948966F, 0.0F);
        this.ring5 = new ModelRenderer(this, 0, 5);
        this.ring5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring5.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.setRotateAngle(ring5, 0.0F, 3.141592653589793F, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 0);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.body2 = new ModelRenderer(this, 0, 0);
        this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body2.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        this.ring2 = new ModelRenderer(this, 0, 5);
        this.ring2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring2.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.setRotateAngle(ring2, 0.0F, 0.7853981633974483F, 0.0F);
        this.ring1 = new ModelRenderer(this, 0, 5);
        this.ring1.setRotationPoint(0.0F, -0.3F, 0.0F);
        this.ring1.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.body3 = new ModelRenderer(this, 0, 0);
        this.body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body3.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        this.body7 = new ModelRenderer(this, 0, 0);
        this.body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body7.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        this.body5 = new ModelRenderer(this, 0, 0);
        this.body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body5.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 0);
        this.body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body4.addBox(-1.5F, -3.0F, 1.62F, 3, 3, 2, 0.0F);
        this.setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        this.ring4 = new ModelRenderer(this, 0, 5);
        this.ring4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ring4.addBox(-1.5F, -5.0F, 2.62F, 3, 5, 1, 0.0F);
        this.setRotateAngle(ring4, 0.0F, 2.356194490192345F, 0.0F);
        this.body1.addChild(this.body6);
        this.body1.addChild(this.body8);
        this.ring1.addChild(this.ring6);
        this.ring1.addChild(this.ring3);
        this.ring1.addChild(this.ring8);
        this.ring1.addChild(this.ring7);
        this.ring1.addChild(this.ring5);
        this.body1.addChild(this.body2);
        this.ring1.addChild(this.ring2);
        this.body1.addChild(this.body3);
        this.body1.addChild(this.body7);
        this.body1.addChild(this.body5);
        this.body1.addChild(this.body4);
        this.ring1.addChild(this.ring4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        this.body1.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.ring1.offsetX, this.ring1.offsetY, this.ring1.offsetZ);
        GL11.glTranslatef(this.ring1.rotationPointX * f5, this.ring1.rotationPointY * f5, this.ring1.rotationPointZ * f5);
        GL11.glScaled(1.2D, 1.2D, 1.2D);
        GL11.glTranslatef(-this.ring1.offsetX, -this.ring1.offsetY, -this.ring1.offsetZ);
        GL11.glTranslatef(-this.ring1.rotationPointX * f5, -this.ring1.rotationPointY * f5, -this.ring1.rotationPointZ * f5);
        this.ring1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
