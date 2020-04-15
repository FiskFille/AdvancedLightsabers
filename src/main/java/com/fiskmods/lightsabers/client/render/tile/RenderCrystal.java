package com.fiskmods.lightsabers.client.render.tile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.client.model.tile.ModelCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCrystal extends TileEntitySpecialRenderer
{
    private final ModelCrystal model = new ModelCrystal();

    public void render(TileEntityCrystal tile, double x, double y, double z, float partialTicks)
    {
        float alpha = 0.6F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1, -1, -1);
        
        if (tile.getWorldObj() != null)
        {
            adjustRotation(tile, tile.getBlockMetadata());
        }
        else
        {
            alpha *= ALRenderHelper.getAlpha();
        }
        
        float[] rgb = tile.getColor().getRGB();
        GL11.glColor4f(rgb[0], rgb[1], rgb[2], alpha);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ALRenderHelper.setLighting(ALRenderHelper.LIGHTING_LUMINOUS);
        model.render();
        ALRenderHelper.resetLighting();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public void adjustRotation(TileEntityCrystal tile, int metadata)
    {
        if (metadata > 0 && metadata < 5)
        {
            int[] matrix = {0, 2, 1, 3};
            GL11.glRotatef(matrix[metadata - 1] * 90, 0, 1, 0);
        }

        if (metadata == 6)
        {
            GL11.glTranslatef(0, 2, 0);
            GL11.glRotatef(180, 0, 0, 1);
        }

        if (metadata != 5 && metadata != 6)
        {
            GL11.glTranslatef(1, 1, 0);
            GL11.glRotatef(90, 0, 0, 1);
        }

        Random rand = new Random(tile.xCoord + tile.yCoord + tile.zCoord);
        GL11.glRotatef(rand.nextInt(360), 0, 1, 0);
        GL11.glTranslatef(0, (float) rand.nextInt(10) / 40, 0);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks)
    {
        render((TileEntityCrystal) tile, x, y, z, partialTicks);
    }
}
