package com.fiskmods.lightsabers.client.render.tile;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.tile.ModelSithCoffin;
import com.fiskmods.lightsabers.common.block.BlockSithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSithCoffin extends TileEntitySpecialRenderer
{
    private ResourceLocation texture = new ResourceLocation(Lightsabers.MODID, "textures/models/sith_coffin.png");
    private ModelSithCoffin model = new ModelSithCoffin();

    public void render(TileEntitySithCoffin tileentity, double x, double y, double z, float partialTicks)
    {
        int metadata = 0;

        if (tileentity.getWorldObj() != null)
        {
            metadata = tileentity.getBlockMetadata();
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        GL11.glRotatef(metadata * 90 + 180, 0.0F, 1.0F, 0.0F);

        if (!BlockSithCoffin.isBlockFrontOfCoffin(metadata))
        {
            bindTexture(texture);
            model.render(tileentity);
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks)
    {
        render((TileEntitySithCoffin) tileentity, x, y, z, partialTicks);
    }
}
