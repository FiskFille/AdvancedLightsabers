package com.fiskmods.lightsabers.client.render.tile;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class RenderHolocron extends TileEntitySpecialRenderer
{
    public void render(TileEntityHolocron tile, double x, double y, double z, float partialTicks)
    {
        int metadata = 0;

        if (tile.getWorldObj() != null || tile.blockMetadata != -1)
        {
            metadata = tile.getBlockMetadata();
        }

        Tessellator tessellator = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
//		GL11.glScalef(1, -1, -1);
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
//		GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        tessellator.setNormal(0, 10, 10);
        bindTexture(TextureMap.locationBlocksTexture);
        GL11.glColor4f(1, 1, 1, 1);

        if (metadata == 0)
        {
            renderJediHolocron(tile, metadata, tessellator, partialTicks);
        }
        else if (metadata == 1)
        {
            renderSithHolocron(tile, metadata, tessellator, partialTicks);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public void renderJediHolocron(TileEntityHolocron tile, int metadata, Tessellator tessellator, float partialTicks)
    {
        IIcon iconSide = ModBlocks.holocron.getIcon(0, metadata);
        IIcon iconCorner = ModBlocks.holocron.getIcon(1, metadata);
        IIcon iconCornerBottom = ModBlocks.holocron.getIcon(2, metadata);
        IIcon iconCornerSide = ModBlocks.holocron.getIcon(3, metadata);

        float minU = iconSide.getInterpolatedU(0);
        float maxU = iconSide.getInterpolatedU(16);
        float minV = iconSide.getInterpolatedV(0);
        float maxV = iconSide.getInterpolatedV(16);
        float size = 0.5F;
        float width = 0.707F * size;
        float width1 = MathHelper.sqrt_float(width * width - (width / 2) * (width / 2));
        float height = size * 0.2875F;
        float t = ALRenderHelper.median(tile.openTicks, tile.prevOpenTicks);
        float f = ALRenderHelper.median(tile.openTimer, tile.prevOpenTimer);
        float f1 = MathHelper.sin(t / 10) / 20;
        float offset = size * (0.5775F + f * (0.3F + f1));
        float rot = 180 * f;

        GL11.glTranslatef(0, size / 2 + (size / 2 + f1) * f, 0);

        for (int i = 0; i < 6; ++i)
        {
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-size / 2, 0, size / 2, maxU, maxV);
            tessellator.addVertexWithUV(0, -size / 2, size / 2, maxU, minV);
            tessellator.addVertexWithUV(size / 2, 0, size / 2, minU, minV);
            tessellator.addVertexWithUV(0, size / 2, size / 2, minU, maxV);

            GL11.glPushMatrix();

            if (i < 4)
            {
                GL11.glRotatef(i * 90, 0, 1, 0);
            }
            else
            {
                GL11.glRotatef((i - 4) * 180 + 90, 1, 0, 0);
            }

            tessellator.draw();
            GL11.glPopMatrix();
        }

        for (int i = 0; i < 2; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                tessellator.startDrawing(GL11.GL_TRIANGLES);
                minU = iconCorner.getInterpolatedU(0);
                maxU = iconCorner.getInterpolatedU(8);
                minV = iconCorner.getInterpolatedV(0);
                maxV = iconCorner.getInterpolatedV(16);
                tessellator.addVertexWithUV(-size / 4, size / 2, size / 4, maxU, minV);
                tessellator.addVertexWithUV(-size / 2, size / 2, 0, minU, minV);
                tessellator.addVertexWithUV(-size / 2, 0, size / 2, minU, maxV);
                minU = iconCorner.getInterpolatedU(16);
                maxU = iconCorner.getInterpolatedU(8);
                tessellator.addVertexWithUV(-size / 2, 0, size / 2, minU, maxV);
                tessellator.addVertexWithUV(0, size / 2, size / 2, minU, minV);
                tessellator.addVertexWithUV(-size / 4, size / 2, size / 4, maxU, minV);

                GL11.glPushMatrix();
                GL11.glRotatef(j * 90, 0, 1, 0);

                if (i == 1)
                {
                    GL11.glRotatef(180, 1, 0, 0);
                }

                tessellator.draw();
                GL11.glPopMatrix();

                tessellator.startDrawing(GL11.GL_TRIANGLES);
                minU = iconCornerBottom.getInterpolatedU(0);
                maxU = iconCornerBottom.getInterpolatedU(8);
                minV = iconCornerBottom.getInterpolatedV(0);
                maxV = iconCornerBottom.getInterpolatedV(16);
                tessellator.addVertexWithUV(0, (width1 + width1) / 3, 0, minU, maxV);
                tessellator.addVertexWithUV(width / 2, -width1 / 3, 0, minU, minV);
                tessellator.addVertexWithUV(0, -width1 / 3, 0, maxU, minV);
                minU = iconCornerBottom.getInterpolatedU(8);
                maxU = iconCornerBottom.getInterpolatedU(16);
                tessellator.addVertexWithUV(0, -width1 / 3, 0, minU, minV);
                tessellator.addVertexWithUV(-width / 2, -width1 / 3, 0, maxU, minV);
                tessellator.addVertexWithUV(0, (width1 + width1) / 3, 0, maxU, maxV);

                GL11.glPushMatrix();
                GL11.glRotatef(45 + j * 90, 0, 1, 0);
                GL11.glRotatef(35, 1, 0, 0);
                GL11.glRotatef(rot, 0, 0, 1);

                if (i == 1)
                {
                    GL11.glRotatef(180, 1, 0, 0);
                }

                GL11.glTranslatef(0, 0, offset);
                tessellator.draw();

                for (int k = 0; k < 3; ++k)
                {
                    tessellator.startDrawing(GL11.GL_TRIANGLES);
                    minU = iconCornerSide.getInterpolatedU(8);
                    maxU = iconCornerSide.getInterpolatedU(16);
                    minV = iconCornerSide.getInterpolatedV(0);
                    maxV = iconCornerSide.getInterpolatedV(16);
                    tessellator.addVertexWithUV(0, -width1 / 3, 0, minU, maxV);
                    tessellator.addVertexWithUV(0, 0, height, maxU, minV);
                    tessellator.addVertexWithUV(-width / 2, -width1 / 3, 0, maxU, maxV);
                    minU = iconCornerSide.getInterpolatedU(0);
                    maxU = iconCornerSide.getInterpolatedU(8);
                    tessellator.addVertexWithUV(0, -width1 / 3, 0, maxU, maxV);
                    tessellator.addVertexWithUV(width / 2, -width1 / 3, 0, minU, maxV);
                    tessellator.addVertexWithUV(0, 0, height, minU, minV);
                    GL11.glRotatef(120, 0, 0, 1);
                    tessellator.draw();
                }

                GL11.glPopMatrix();
            }
        }
    }

    public void renderSithHolocron(TileEntityHolocron tile, int metadata, Tessellator tessellator, float partialTicks)
    {
        IIcon iconBottom = ModBlocks.holocron.getIcon(0, metadata);
        IIcon iconSide = ModBlocks.holocron.getIcon(1, metadata);

        float minU = iconBottom.getInterpolatedU(0);
        float maxU = iconBottom.getInterpolatedU(16);
        float minV = iconBottom.getInterpolatedV(0);
        float maxV = iconBottom.getInterpolatedV(16);
        float size = 0.5F;
        float t = ALRenderHelper.median(tile.openTicks, tile.prevOpenTicks);
        float f = ALRenderHelper.median(tile.openTimer, tile.prevOpenTimer);
        float f1 = MathHelper.sin(t / 10) / 20;
        float offset = size * (0.5775F + f * (0.3F + f1));
        float rot = 180 * f;
        float height = size;

        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glTranslatef(0, size / 2 + (size / 2 + f1) * f + 0.0001F, 0);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(size / 2, -size / 2, -size / 2, maxU, minV);
        tessellator.addVertexWithUV(size / 2, -size / 2, size / 2, maxU, maxV);
        tessellator.addVertexWithUV(-size / 2, -size / 2, size / 2, minU, maxV);
        tessellator.addVertexWithUV(-size / 2, -size / 2, -size / 2, minU, minV);
        tessellator.draw();

        for (int i = 0; i < 4; ++i)
        {
            tessellator.startDrawing(GL11.GL_TRIANGLES);
            minU = iconSide.getInterpolatedU(0);
            maxU = iconSide.getInterpolatedU(8);
            minV = iconSide.getInterpolatedV(0);
            maxV = iconSide.getInterpolatedV(16);
            tessellator.addVertexWithUV(size / 2, -size / 2, -size / 2, minU, maxV);
            tessellator.addVertexWithUV(0, -size / 2, -size / 2, maxU, maxV);
            tessellator.addVertexWithUV(0, size / 2, 0, minU, minV);
            minU = iconSide.getInterpolatedU(8);
            maxU = iconSide.getInterpolatedU(16);
            tessellator.addVertexWithUV(0, -size / 2, -size / 2, minU, maxV);
            tessellator.addVertexWithUV(-size / 2, -size / 2, -size / 2, maxU, maxV);
            tessellator.addVertexWithUV(0, size / 2, 0, maxU, minV);
            GL11.glRotatef(90, 0, 1, 0);
            tessellator.draw();
        }

        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks)
    {
        render((TileEntityHolocron) tileentity, x, y, z, partialTicks);
    }
}
