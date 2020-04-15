package com.fiskmods.lightsabers.client.render.tile;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.tile.ModelDisassemblyStation;
import com.fiskmods.lightsabers.common.block.BlockDisassemblyStation;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderDisassemblyStation extends TileEntitySpecialRenderer
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Lightsabers.MODID, "textures/models/disassembly_station.png");
    private static final ResourceLocation TEXTURE_LIGHTS = new ResourceLocation(Lightsabers.MODID, "textures/models/disassembly_station_lights.png");
    private static final ModelDisassemblyStation MODEL = new ModelDisassemblyStation();

    public void render(TileEntityDisassemblyStation tile, double x, double y, double z, float partialTicks)
    {
        int metadata = 0;

        if (tile.getWorldObj() != null)
        {
            metadata = tile.getBlockMetadata();
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glRotatef(BlockDisassemblyStation.getRotation(metadata) * 90 + 180, 0, 1, 0);
        GL11.glTranslatef(0.5F, 0, 0);
        bindTexture(TEXTURE);
        MODEL.render(0.0625F);
        ALRenderHelper.setLighting(ALRenderHelper.LIGHTING_LUMINOUS);
        bindTexture(TEXTURE_LIGHTS);
        MODEL.render(0.0625F);
        ALRenderHelper.resetLighting();
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks)
    {
        render((TileEntityDisassemblyStation) tile, x, y, z, partialTicks);
    }
}
