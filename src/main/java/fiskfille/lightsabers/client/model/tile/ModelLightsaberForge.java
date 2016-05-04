package fiskfille.lightsabers.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelLightsaberForge extends ModelBase
{
    public ModelRenderer forge1;
    public ModelRenderer forge2;
    public ModelRenderer forge10;
    public ModelRenderer forge11;
    public ModelRenderer forge12;
    public ModelRenderer forge14;
    public ModelRenderer forge3;
    public ModelRenderer forge6;
    public ModelRenderer forge7;
    public ModelRenderer forge8;
    public ModelRenderer forge4;
    public ModelRenderer forge5;
    public ModelRenderer forge9;
    public ModelRenderer forge13;
    public ModelRenderer forge15;

    public ModelLightsaberForge()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.forge3 = new ModelRenderer(this, 51, 28);
        this.forge3.mirror = true;
        this.forge3.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.forge3.addBox(-4.0F, -7.0F, -4.0F, 8, 14, 4, 0.0F);
        this.forge13 = new ModelRenderer(this, 1, 30);
        this.forge13.setRotationPoint(3.0F, 15.5F, 0.0F);
        this.forge13.addBox(0.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.forge15 = new ModelRenderer(this, 1, 30);
        this.forge15.mirror = true;
        this.forge15.setRotationPoint(-3.0F, 15.5F, 0.0F);
        this.forge15.addBox(-4.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.forge14 = new ModelRenderer(this, 0, 82);
        this.forge14.mirror = true;
        this.forge14.setRotationPoint(13.4F, -31.0F, -0.1F);
        this.forge14.addBox(-3.0F, 0.0F, -8.0F, 6, 30, 16, 0.0F);
        this.forge2 = new ModelRenderer(this, 0, 0);
        this.forge2.setRotationPoint(0.0F, -2.0F, -8.0F);
        this.forge2.addBox(-20.0F, -12.0F, 0.0F, 40, 12, 16, 0.0F);
        this.forge11 = new ModelRenderer(this, 84, 82);
        this.forge11.mirror = true;
        this.forge11.setRotationPoint(24.0F, -1.98F, 0.01F);
        this.forge11.addBox(-6.0F, -30.0F, -8.0F, 6, 30, 16, 0.0F);
        this.setRotateAngle(forge11, 0.0F, 0.0F, -0.2558652683423687F);
        this.forge12 = new ModelRenderer(this, 0, 82);
        this.forge12.setRotationPoint(-13.4F, -31.0F, -0.1F);
        this.forge12.addBox(-3.0F, 0.0F, -8.0F, 6, 30, 16, 0.0F);
        this.forge8 = new ModelRenderer(this, 114, 6);
        this.forge8.setRotationPoint(0.2F, -12.5F, 0.0F);
        this.forge8.addBox(0.0F, -0.5F, -0.5F, 5, 1, 1, 0.0F);
        this.setRotateAngle(forge8, 0.0F, -0.5462880558742251F, 0.0F);
        this.forge5 = new ModelRenderer(this, 78, 28);
        this.forge5.mirror = true;
        this.forge5.setRotationPoint(0.54F, 0.02F, -2.0F);
        this.forge5.addBox(-5.0F, -7.0F, 0.0F, 5, 14, 4, 0.0F);
        this.setRotateAngle(forge5, 0.0F, 2.0943951023931953F, 0.0F);
        this.forge7 = new ModelRenderer(this, 0, 0);
        this.forge7.setRotationPoint(-1.1F, -12.5F, -2.0F);
        this.forge7.addBox(-7.0F, -0.5F, -0.5F, 7, 1, 1, 0.0F);
        this.setRotateAngle(forge7, 0.0F, 0.5462880558742251F, 0.0F);
        this.forge9 = new ModelRenderer(this, 3, 7);
        this.forge9.setRotationPoint(5.0F, 0.0F, 0.0F);
        this.forge9.addBox(-1.5F, -0.5F, 0.0F, 3, 1, 1, 0.0F);
        this.setRotateAngle(forge9, 0.0F, 1.5707963267948966F, 0.0F);
        this.forge1 = new ModelRenderer(this, 0, 46);
        this.forge1.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.forge1.addBox(-24.0F, -2.0F, -8.0F, 48, 2, 16, 0.0F);
        this.forge4 = new ModelRenderer(this, 30, 28);
        this.forge4.mirror = true;
        this.forge4.setRotationPoint(-0.54F, 0.02F, -2.0F);
        this.forge4.addBox(0.0F, -7.0F, 0.0F, 5, 14, 4, 0.0F);
        this.setRotateAngle(forge4, 0.0F, -2.0943951023931953F, 0.0F);
        this.forge6 = new ModelRenderer(this, 2, 11);
        this.forge6.setRotationPoint(0.0F, -12.0F, 8.0F);
        this.forge6.addBox(-1.5F, -1.0F, -1.5F, 3, 1, 3, 0.0F);
        this.forge10 = new ModelRenderer(this, 84, 82);
        this.forge10.setRotationPoint(-24.0F, -1.98F, 0.01F);
        this.forge10.addBox(0.0F, -30.0F, -8.0F, 6, 30, 16, 0.0F);
        this.setRotateAngle(forge10, 0.0F, 0.0F, 0.2558652683423687F);
        this.forge2.addChild(this.forge3);
        this.forge12.addChild(this.forge13);
        this.forge14.addChild(this.forge15);
        this.forge1.addChild(this.forge14);
        this.forge1.addChild(this.forge2);
        this.forge1.addChild(this.forge11);
        this.forge1.addChild(this.forge12);
        this.forge2.addChild(this.forge8);
        this.forge3.addChild(this.forge5);
        this.forge2.addChild(this.forge7);
        this.forge8.addChild(this.forge9);
        this.forge3.addChild(this.forge4);
        this.forge2.addChild(this.forge6);
        this.forge1.addChild(this.forge10);
    }

    public void render()
    { 
        this.forge1.render(0.0625F);
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
