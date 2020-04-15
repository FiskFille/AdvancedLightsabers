package com.fiskmods.lightsabers.client.render.item;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.event.ClientEventHandler;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.helper.ALRenderHelper;
import com.fiskmods.lightsabers.helper.ModelHelper;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemDoubleLightsaber implements IItemRenderer
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
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... args)
    {
        LightsaberData[] array = ItemDoubleLightsaber.get(itemstack);

        GL11.glPushMatrix();

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glRotatef(-100, 0, 1, 0);
            GL11.glRotatef(-150, 1, 0, 0);
            GL11.glRotatef(95, 0, 0, 1);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(-0.1F, -0.2F, 1.1F);

            if (args.length > 1 && args[1] instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) args[1];
                float f = player.prevLimbSwingAmount - (player.prevLimbSwingAmount - player.limbSwingAmount) * ClientEventHandler.renderTick;
                float f1 = MathHelper.cos((player.limbSwing - player.limbSwingAmount * (1 - ClientEventHandler.renderTick)) * 0.6662F) * 1.4F * f;
                float f2 = player.getSwingProgress(ClientEventHandler.renderTick);
                float f3 = (f2 > 0.5F ? 1 - f2 : f2) * 2;
                GL11.glRotatef(90 * f, 0, 0, 1);
                GL11.glTranslatef(0.2F * f3 + 0.8F * f, 0.5F * f3, 0.4F * f);
                GL11.glRotatef(30 * f3, 1, 0, 0);
                GL11.glRotatef(360 * f2, 0, 0, 1);
            }

            float scale = 0.2F;
            GL11.glScalef(scale, scale, scale);
            ALRenderHelper.renderLightsaber(array, itemstack, true);
        }
        else if (type == ItemRenderType.EQUIPPED)
        {
            GL11.glRotatef(-90, 0, 1, 0);
            GL11.glRotatef(-150, 1, 0, 0);
            GL11.glTranslatef(-0.15F + LightsaberData.getHeight(itemstack) * 0.0015F, 0.14F, 0.75F);

            if (args[1] instanceof EntityLivingBase)
            {
                ModelHelper.applyLightsaberItemRotation((EntityLivingBase) args[1], itemstack);
            }

            float scale = 0.175F;
            GL11.glScalef(scale, scale, scale);
            ALRenderHelper.renderLightsaber(array, itemstack, true);
        }
        else if (type == ItemRenderType.ENTITY)
        {
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(180, 0, 1, 0);

            if (((EntityItem) args[1]).hoverStart != 0)
            {
                GL11.glRotatef(90, 0, 0, 1);
            }

            float scale = 0.3F;
            GL11.glScalef(scale, scale, scale);
            ALRenderHelper.renderLightsaberHilt(array);
        }
        else if (type == ItemRenderType.INVENTORY)
        {
            Tessellator tessellator = Tessellator.instance;
            float[] rgb = array[0].getRGB(itemstack);
            float triangle = 4;

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1);
            tessellator.startDrawing(GL11.GL_TRIANGLES);
            tessellator.addVertex(triangle / 2, triangle / 2, 0);
            tessellator.addVertex(triangle, 0, 0);
            tessellator.addVertex(0, 0, 0);
            tessellator.draw();
            rgb = array[1].getRGB(itemstack);
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1);
            tessellator.startDrawing(GL11.GL_TRIANGLES);
            tessellator.addVertex(0, triangle, 0);
            tessellator.addVertex(triangle / 2, triangle / 2, 0);
            tessellator.addVertex(0, 0, 0);
            tessellator.draw();
            triangle /= 1.5F;
            GL11.glTranslatef(triangle / 8, triangle / 8, 0);
            GL11.glColor4f(0, 0, 0, 1);

            if (array[0].hasFocusingCrystal(FocusingCrystal.INVERTING))
            {
                tessellator.startDrawing(GL11.GL_TRIANGLES);
                tessellator.addVertex(triangle / 2, triangle / 2, 0);
                tessellator.addVertex(triangle, 0, 0);
                tessellator.addVertex(0, 0, 0);
                tessellator.draw();
            }

            if (array[1].hasFocusingCrystal(FocusingCrystal.INVERTING))
            {
                tessellator.startDrawing(GL11.GL_TRIANGLES);
                tessellator.addVertex(0, triangle, 0);
                tessellator.addVertex(triangle / 2, triangle / 2, 0);
                tessellator.addVertex(0, 0, 0);
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
            GL11.glRotatef(-45, 1, 0, 0);
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(-110, 0, 1, 0);
            GL11.glTranslatef(0, 0.05F, 0.05F);

            float scale = 0.3F;
            GL11.glScalef(scale, scale, scale);
            ALRenderHelper.renderLightsaberHilt(array);
        }

        GL11.glPopMatrix();
    }
}
