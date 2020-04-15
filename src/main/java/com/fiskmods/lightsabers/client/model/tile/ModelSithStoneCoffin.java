package com.fiskmods.lightsabers.client.model.tile;

import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSithStoneCoffin extends ModelBase
{
    public ModelRenderer base;
    public ModelRenderer coffin;

    public ModelSithStoneCoffin()
    {
        textureWidth = 128;
        textureHeight = 128;
        base = new ModelRenderer(this, 0, 0);
        base.setRotationPoint(0.0F, 24.0F, 0.0F);
        base.addBox(-8.0F, -3.0F, -8.0F, 16, 3, 16, 0.0F);
        coffin = new ModelRenderer(this, 0, 20);
        coffin.setRotationPoint(0.0F, -3.0F, 2.0F);
        coffin.addBox(-6.5F, -4.5F, 0.0F, 13, 9, 28, 0.0F);
        setRotateAngle(coffin, 1.5707963267948966F, 0.0F, 0.0F);
        base.addChild(coffin);
    }

    public void render(TileEntitySithStoneCoffin tile)
    {
        coffin.isHidden = tile.baseplateOnly;
        base.render(0.0625F);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
