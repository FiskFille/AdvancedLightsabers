package com.fiskmods.lightsabers.client.model.tile;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelDisassemblyStation extends ModelBase
{
    public ModelRenderer tabletopThing;
    public ModelRenderer back;
    public ModelRenderer base;
    public ModelRenderer ring1;
    public ModelRenderer theClawwwww;
    public ModelRenderer tabletopThingSlant;
    public ModelRenderer innerSlant;
    public ModelRenderer frontTop;
    public ModelRenderer outerSlant;
    public ModelRenderer ring2;
    public ModelRenderer ring3;
    public ModelRenderer ring4;
    public ModelRenderer ring5;
    public ModelRenderer ring6;
    public ModelRenderer ring7;
    public ModelRenderer ring8;
    public ModelRenderer clawArm2;
    public ModelRenderer clawArm3;
    public ModelRenderer clawArm3Left;
    public ModelRenderer clawArm3Right;
    public ModelRenderer clawArm4Left;
    public ModelRenderer clawArm4Right;

    public ModelDisassemblyStation()
    {
        textureWidth = 128;
        textureHeight = 128;
        base = new ModelRenderer(this, 0, 0);
        base.setRotationPoint(0, 0, 0);
        base.addBox(-16, 10, -8, 32, 14, 16, 0);
        ring6 = new ModelRenderer(this, 81, 10);
        ring6.setRotationPoint(0, 0, 0);
        ring6.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring6, 0, -2.356194490192345F, 0);
        ring4 = new ModelRenderer(this, 81, 10);
        ring4.setRotationPoint(0, 0, 0);
        ring4.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring4, 0, 2.356194490192345F, 0);
        clawArm2 = new ModelRenderer(this, 81, 0);
        clawArm2.setRotationPoint(-0.4F, 9.6F, 0);
        clawArm2.addBox(0, 0.6F, -0.01F, 2, 6, 2, 0);
        setRotateAngle(clawArm2, 0, 0, -0.8726646259971648F);
        ring2 = new ModelRenderer(this, 81, 10);
        ring2.setRotationPoint(0, 0, 0);
        ring2.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring2, 0, 0.7853981633974483F, 0);
        ring5 = new ModelRenderer(this, 81, 10);
        ring5.setRotationPoint(0, 0, 0);
        ring5.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring5, 0, 3.141592653589793F, 0);
        clawArm3 = new ModelRenderer(this, 81, 0);
        clawArm3.setRotationPoint(4.5F, 6.6F, 0);
        clawArm3.addBox(0, 0.6F, -0.01F, 0, 4, 0, 0);
        setRotateAngle(clawArm3, 0, 0, 1.5707963267948966F);
        ring8 = new ModelRenderer(this, 81, 10);
        ring8.setRotationPoint(0, 0, 0);
        ring8.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring8, 0, -0.7853981633974483F, 0);
        clawArm4Left = new ModelRenderer(this, 103, 11);
        clawArm4Left.setRotationPoint(-3.4F, 0.1F, -0.02F);
        clawArm4Left.addBox(0, 0, 0, 2, 5, 2, 0);
        setRotateAngle(clawArm4Left, 0, 0, -1.0995574287564276F);
        tabletopThing = new ModelRenderer(this, 90, 0);
        tabletopThing.setRotationPoint(22, -3, 4);
        tabletopThing.addBox(-19, 10, -8, 12, 3, 7, 0);
        theClawwwww = new ModelRenderer(this, 0, 0);
        theClawwwww.setRotationPoint(-18, -2, 4);
        theClawwwww.addBox(0, 0, 0, 2, 10, 2, 0);
        setRotateAngle(theClawwwww, -0.9075712110370513F, 0.13962634015954636F, 0);
        clawArm3Left = new ModelRenderer(this, 94, 10);
        clawArm3Left.setRotationPoint(1.84F, 5.61F, 0);
        clawArm3Left.addBox(0, 0.6F, -0.01F, 2, 3, 2, 0);
        setRotateAngle(clawArm3Left, 0, 0, 2.356194490192345F);
        clawArm4Right = new ModelRenderer(this, 103, 11);
        clawArm4Right.setRotationPoint(3.71F, 0.86F, -0.02F);
        clawArm4Right.addBox(0, 0, 0, 2, 4, 2, 0);
        setRotateAngle(clawArm4Right, 0, 0, 0.7330382858376184F);
        ring1 = new ModelRenderer(this, 81, 10);
        ring1.setRotationPoint(-15, -1.1F, 4);
        ring1.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring1, 1.5707963267948966F, 1.5707963267948966F, 0);
        tabletopThingSlant = new ModelRenderer(this, 94, 26);
        tabletopThingSlant.setRotationPoint(-19, 13.54F, -11.54F);
        tabletopThingSlant.addBox(0, 0, 0, 12, 3, 5, 0);
        setRotateAngle(tabletopThingSlant, 0.7853981633974483F, 0, 0);
        back = new ModelRenderer(this, 0, 30);
        back.setRotationPoint(0, -7, 11);
        back.addBox(-16, 2, -8, 32, 15, 5, 0);
        ring3 = new ModelRenderer(this, 81, 10);
        ring3.setRotationPoint(0, 0, 0);
        ring3.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring3, 0, 1.5707963267948966F, 0);
        outerSlant = new ModelRenderer(this, 0, 72);
        outerSlant.setRotationPoint(0, -2, -13.47F);
        outerSlant.addBox(-15.99F, 10, -8, 32, 8, 4, 0);
        setRotateAngle(outerSlant, 1.0821041362364843F, 0, 0);
        ring7 = new ModelRenderer(this, 81, 10);
        ring7.setRotationPoint(0, 0, 0);
        ring7.addBox(-1.5F, -2, 0.62F, 3, 2, 3, 0);
        setRotateAngle(ring7, 0, -1.5810937693816633F, 0);
        innerSlant = new ModelRenderer(this, 0, 50);
        innerSlant.setRotationPoint(0, -4, -16);
        innerSlant.addBox(-16.01F, 10, -8, 32, 8, 6, 0);
        setRotateAngle(innerSlant, 0.8726646259971648F, 0, 0);
        frontTop = new ModelRenderer(this, 0, 64);
        frontTop.setRotationPoint(-16, 6.22F, -6.52F);
        frontTop.addBox(0, 0, 0, 32, 4, 4, 0);
        setRotateAngle(frontTop, -0.8726646259971648F, 0, 0);
        clawArm3Right = new ModelRenderer(this, 94, 10);
        clawArm3Right.mirror = true;
        clawArm3Right.setRotationPoint(1.84F, 5.61F, 0);
        clawArm3Right.addBox(3.2F, -0.8F, -0.01F, 2, 3, 2, 0);
        setRotateAngle(clawArm3Right, 0, 0, -2.356194490192345F);
        ring1.addChild(ring6);
        ring1.addChild(ring4);
        theClawwwww.addChild(clawArm2);
        ring1.addChild(ring2);
        ring1.addChild(ring5);
        clawArm2.addChild(clawArm3);
        ring1.addChild(ring8);
        clawArm3Left.addChild(clawArm4Left);
        clawArm3.addChild(clawArm3Left);
        clawArm3Right.addChild(clawArm4Right);
        tabletopThing.addChild(tabletopThingSlant);
        ring1.addChild(ring3);
        innerSlant.addChild(outerSlant);
        ring1.addChild(ring7);
        back.addChild(innerSlant);
        innerSlant.addChild(frontTop);
        clawArm3.addChild(clawArm3Right);
    }

    public void render(float scale)
    {
        base.render(scale);
        tabletopThing.render(scale);
        theClawwwww.render(scale);
        GL11.glPushMatrix();
        GL11.glTranslatef(ring1.offsetX, ring1.offsetY, ring1.offsetZ);
        GL11.glTranslatef(ring1.rotationPointX * scale, ring1.rotationPointY * scale, ring1.rotationPointZ * scale);
        GL11.glScaled(1.0D, 0.8D, 0.8D);
        GL11.glTranslatef(-ring1.offsetX, -ring1.offsetY, -ring1.offsetZ);
        GL11.glTranslatef(-ring1.rotationPointX * scale, -ring1.rotationPointY * scale, -ring1.rotationPointZ * scale);
        ring1.render(scale);
        GL11.glPopMatrix();
        back.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
