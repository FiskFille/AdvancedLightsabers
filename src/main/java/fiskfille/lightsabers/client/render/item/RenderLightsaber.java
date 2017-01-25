package fiskfille.lightsabers.client.render.item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.helper.ModelHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class RenderLightsaber implements IItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return type == ItemRenderType.ENTITY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        float height = LightsaberHelper.getLightsaberHeight(item);
        float f = (-(LightsaberHelper.getPart(item, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(item, EnumPartType.POMMEL).getPommel().height) + height / 2) * 0.0625F;

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(-100, 0, 1, 0);
            GL11.glRotatef(-150, 1, 0, 0);
            GL11.glRotatef(5, 0, 0, 1);
            GL11.glTranslatef(0, 0.275F, 0.85F);

            GL11.glPushMatrix();
            float scale = 0.5F * 0.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            ALRenderHelper.renderLightsaberHilt(item);
            GL11.glPopMatrix();

            scale = 0.5F * 0.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
            scale = 1.5F * 0.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, -(LightsaberHelper.getPart(item, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(item, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
            ALRenderHelper.renderLightsaberBlade(item, true);
            GL11.glPopMatrix();
        }
        else if (type == ItemRenderType.EQUIPPED)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.7F, 0.25F, 0);
            GL11.glRotatef(-150, 0, 0, 1);
            GL11.glRotatef(-85, 0, 1, 0);

            if (data[1] instanceof Entity)
            {
                ModelHelper.applyLightsaberItemRotation((Entity) data[1], item);
            }

            GL11.glPushMatrix();
            float scale = 0.5F * 0.35F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            ALRenderHelper.renderLightsaberHilt(item);
            GL11.glPopMatrix();

            scale = 0.5F * 0.35F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
            scale = 1.5F * 0.35F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, -(LightsaberHelper.getPart(item, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(item, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
            ALRenderHelper.renderLightsaberBlade(item, true);
            GL11.glPopMatrix();
        }
        else if (type == ItemRenderType.ENTITY)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(180, 0, 1, 0);

            float scale = 0.5F * 0.55F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            ALRenderHelper.renderLightsaberHilt(item);
            GL11.glPopMatrix();
        }
        else if (type == ItemRenderType.INVENTORY)
        {
            GL11.glPushMatrix();
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            float size = 4;
            float[] afloat = ALRenderHelper.getLightsaberColor(item);
            GL11.glColor4f(afloat[0], afloat[1], afloat[2], 1);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertex(size / 2, size / 2, 0);
            tessellator.addVertex(size, 0, 0);
            tessellator.addVertex(0, 0, 0);
            tessellator.addVertex(0, size, 0);
            tessellator.draw();

            if (LightsaberHelper.hasFocusingCrystal(item, FocusingCrystals.INVERTING_FOCUSING_CRYSTAL))
            {
                size = 2;
                GL11.glColor4f(0, 0, 0, 1);
                GL11.glTranslatef(0.625F, 0.625F, 0);
                tessellator.startDrawingQuads();
                tessellator.addVertex(size / 2, size / 2, 0);
                tessellator.addVertex(size, 0, 0);
                tessellator.addVertex(0, 0, 0);
                tessellator.addVertex(0, size, 0);
                tessellator.draw();
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glPopMatrix();

            GL11.glTranslatef(-2, 3, 0);
            GL11.glScalef(10, 10, 10);
            GL11.glTranslatef(1, 0.5F, 1);
            GL11.glScalef(1, 1, -1);
            GL11.glRotatef(210, 1, 0, 0);
            GL11.glRotatef(45, 0, 1, 0);
            GL11.glRotatef(-90, 0, 1, 0);
            GL11.glRotatef(-20, 0, 0, 1);
            GL11.glRotatef(-40, 1, 0, 0);
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(-110, 0, 1, 0);
            GL11.glTranslatef(0, 0.05F, 0);

            float scale = 0.5F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            ALRenderHelper.renderLightsaberHilt(item);
            GL11.glPopMatrix();
        }
    }
}
