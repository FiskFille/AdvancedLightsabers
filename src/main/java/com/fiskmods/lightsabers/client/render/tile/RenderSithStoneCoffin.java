package com.fiskmods.lightsabers.client.render.tile;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.tile.ModelSithStoneCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSithStoneCoffin extends TileEntitySpecialRenderer
{
    private ResourceLocation texture = new ResourceLocation(Lightsabers.MODID, "textures/models/sith_stone_coffin.png");
    private ModelSithStoneCoffin model = new ModelSithStoneCoffin();

    public void render(TileEntitySithStoneCoffin tileentity, double x, double y, double z, float partialTicks)
    {
        int metadata = 0;

        if (tileentity.getWorldObj() != null)
        {
            metadata = tileentity.getBlockMetadata();
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        GL11.glRotatef(metadata * 90, 0.0F, 1.0F, 0.0F);

        if (metadata < 4)
        {
            bindTexture(texture);
            model.render(tileentity);
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
    {
        render((TileEntitySithStoneCoffin) tileentity, d, d1, d2, f);
    }
}
