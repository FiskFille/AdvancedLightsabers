package com.fiskmods.lightsabers.client.model.tile;

import com.fiskmods.lightsabers.common.event.ClientEventHandler;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSithCoffin extends ModelBase
{
    public ModelRenderer base1;
    public ModelRenderer lid1;
    public ModelRenderer base2;
    public ModelRenderer base3;
    public ModelRenderer base4;
    public ModelRenderer base7;
    public ModelRenderer base10;
    public ModelRenderer base15;
    public ModelRenderer base5;
    public ModelRenderer base6;
    public ModelRenderer base8;
    public ModelRenderer base9;
    public ModelRenderer base11;
    public ModelRenderer base12;
    public ModelRenderer base13;
    public ModelRenderer base14;
    public ModelRenderer lid2;
    public ModelRenderer lid3;
    public ModelRenderer lid4;
    public ModelRenderer lid5;

    public ModelSithCoffin()
    {
        textureWidth = 256;
        textureHeight = 128;
        base10 = new ModelRenderer(this, 107, 0);
        base10.setRotationPoint(0.0F, -2.0F, -13.5F);
        base10.addBox(-6.0F, -7.0F, -0.5F, 12, 7, 1, 0.0F);
        base15 = new ModelRenderer(this, 107, 0);
        base15.setRotationPoint(0.0F, -2.0F, 13.5F);
        base15.addBox(-6.0F, -7.0F, -0.5F, 12, 7, 1, 0.0F);
        setRotateAngle(base15, 0.0F, 3.141592653589793F, 0.0F);
        lid3 = new ModelRenderer(this, 64, 28);
        lid3.setRotationPoint(1.5F, -2.3F, 0.0F);
        lid3.addBox(0.0F, 0.0F, -13.0F, 1, 3, 26, 0.0F);
        setRotateAngle(lid3, 0.0F, 0.0F, 0.6981317007977318F);
        base3 = new ModelRenderer(this, 0, 90);
        base3.setRotationPoint(0.0F, -2.0F, 0.0F);
        base3.addBox(-5.5F, -6.0F, -12.0F, 11, 6, 24, 0.0F);
        base2 = new ModelRenderer(this, 110, 0);
        base2.setRotationPoint(0.0F, -3.0F, 8.0F);
        base2.addBox(-6.0F, -2.0F, -14.0F, 12, 2, 28, 0.0F);
        base8 = new ModelRenderer(this, 172, 0);
        base8.mirror = true;
        base8.setRotationPoint(3.0F, -1.0F, -13.98F);
        base8.addBox(0.0F, -2.0F, 0.0F, 3, 5, 1, 0.0F);
        setRotateAngle(base8, 0.0F, 0.0F, -1.5707963267948966F);
        base11 = new ModelRenderer(this, 58, 0);
        base11.setRotationPoint(0.0F, -5.5F, 0.0F);
        base11.addBox(-10.0F, -2.0F, -1.0F, 20, 2, 2, 0.0F);
        base6 = new ModelRenderer(this, 172, 0);
        base6.setRotationPoint(-3.0F, -1.0F, 13.98F);
        base6.addBox(-3.0F, -2.0F, -1.0F, 3, 5, 1, 0.0F);
        setRotateAngle(base6, 0.0F, 0.0F, 1.5707963267948966F);
        lid1 = new ModelRenderer(this, 0, 60);
        lid1.setRotationPoint(-9.0F, 11.5F, 8.0F);
        lid1.addBox(1.5F, -2.3F, -13.0F, 15, 1, 26, 0.0F);
        base1 = new ModelRenderer(this, 160, 0);
        base1.setRotationPoint(0.0F, 24.0F, 0.0F);
        base1.addBox(-8.0F, -3.0F, -8.0F, 16, 3, 32, 0.0F);
        base12 = new ModelRenderer(this, 0, 30);
        base12.mirror = true;
        base12.setRotationPoint(9.0F, -1.0F, 1.0F);
        base12.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 25, 0.0F);
        lid4 = new ModelRenderer(this, 0, 0);
        lid4.setRotationPoint(9.0F, -1.3F, -12.5F);
        lid4.addBox(-8.0F, 0.0F, -0.5F, 16, 2, 1, 0.0F);
        base4 = new ModelRenderer(this, 183, 36);
        base4.setRotationPoint(-6.0F, -2.0F, 0.0F);
        base4.addBox(-8.0F, -1.0F, -14.0F, 8, 1, 28, 0.0F);
        setRotateAngle(base4, 0.0F, 0.0F, 1.0471975511965976F);
        base7 = new ModelRenderer(this, 183, 36);
        base7.mirror = true;
        base7.setRotationPoint(6.0F, -2.0F, 0.0F);
        base7.addBox(0.0F, -1.0F, -14.0F, 8, 1, 28, 0.0F);
        setRotateAngle(base7, 0.0F, 0.0F, -1.0471975511965976F);
        lid5 = new ModelRenderer(this, 0, 0);
        lid5.setRotationPoint(9.0F, -1.3F, 12.5F);
        lid5.addBox(-8.0F, 0.0F, -0.5F, 16, 2, 1, 0.0F);
        setRotateAngle(lid5, 0.0F, 3.141592653589793F, 0.0F);
        base13 = new ModelRenderer(this, 0, 30);
        base13.setRotationPoint(-9.0F, -1.0F, 1.0F);
        base13.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 25, 0.0F);
        base5 = new ModelRenderer(this, 172, 0);
        base5.setRotationPoint(-3.0F, -1.0F, -13.98F);
        base5.addBox(-3.0F, -2.0F, 0.0F, 3, 5, 1, 0.0F);
        setRotateAngle(base5, 0.0F, 0.0F, 1.5707963267948966F);
        lid2 = new ModelRenderer(this, 64, 28);
        lid2.mirror = true;
        lid2.setRotationPoint(16.5F, -2.3F, 0.0F);
        lid2.addBox(-1.0F, 0.0F, -13.0F, 1, 3, 26, 0.0F);
        setRotateAngle(lid2, 0.0F, 0.0F, -0.6981317007977318F);
        base9 = new ModelRenderer(this, 172, 0);
        base9.mirror = true;
        base9.setRotationPoint(3.0F, -1.0F, 13.98F);
        base9.addBox(0.0F, -2.0F, -1.0F, 3, 5, 1, 0.0F);
        setRotateAngle(base9, 0.0F, 0.0F, -1.5707963267948966F);
        base14 = new ModelRenderer(this, 58, 0);
        base14.setRotationPoint(0.0F, 0.0F, 27.0F);
        base14.addBox(-10.0F, -2.0F, -1.0F, 20, 2, 2, 0.0F);
        setRotateAngle(base14, 0.0F, 3.141592653589793F, 0.0F);
        base2.addChild(base10);
        base2.addChild(base15);
        lid1.addChild(lid3);
        base2.addChild(base3);
        base1.addChild(base2);
        base7.addChild(base8);
        base10.addChild(base11);
        base4.addChild(base6);
        base11.addChild(base12);
        lid1.addChild(lid4);
        base2.addChild(base4);
        base2.addChild(base7);
        lid1.addChild(lid5);
        base11.addChild(base13);
        base4.addChild(base5);
        lid1.addChild(lid2);
        base7.addChild(base9);
        base11.addChild(base14);
    }

    public void render(TileEntitySithCoffin tile)
    {
        float f = tile.getLidOpenTimer(ClientEventHandler.renderTick);
        float f1 = f > 0.5F ? (1 - f) * 2 : 1;
        float f2 = 1 - f1;
        lid1.setRotationPoint(-9.0F + 16 * f, 11.5F - 3 * f2, 8.0F);
        lid1.rotateAngleZ = f2;

        lid1.render(0.0625F);
        base1.render(0.0625F);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
