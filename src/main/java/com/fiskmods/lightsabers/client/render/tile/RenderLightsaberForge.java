package com.fiskmods.lightsabers.client.render.tile;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.tile.ModelLightsaberForge;
import com.fiskmods.lightsabers.common.block.BlockLightsaberForge;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderLightsaberForge extends TileEntitySpecialRenderer
{
    private ResourceLocation textureLight = new ResourceLocation(Lightsabers.MODID, "textures/models/lightsaber_forge_light.png");
    private ResourceLocation textureDark = new ResourceLocation(Lightsabers.MODID, "textures/models/lightsaber_forge_dark.png");
    private ModelLightsaberForge model = new ModelLightsaberForge();

    public void render(TileEntityLightsaberForge tile, double x, double y, double z, float partialTicks)
    {
        int metadata = 0;

        if (tile.getWorldObj() != null)
        {
            metadata = tile.getBlockMetadata();
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glRotatef(BlockLightsaberForge.getDirection(metadata) * 90 + 180, 0.0F, 1.0F, 0.0F);

        if (!BlockLightsaberForge.isBlockSideOfPanel(metadata))
        {
            bindTexture(tile.getBlockType() == ModBlocks.lightsaberForgeDark ? textureDark : textureLight);
            model.render();
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks)
    {
        render((TileEntityLightsaberForge) tileentity, x, y, z, partialTicks);
    }
}
