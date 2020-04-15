package com.fiskmods.lightsabers.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.tile.ModelLightsaberStand;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ItemLightsaber;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberStand;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderLightsaberStand extends TileEntitySpecialRenderer
{
    private ResourceLocation texture = new ResourceLocation(Lightsabers.MODID, "textures/models/lightsaber_stand.png");
    private ModelLightsaberStand model = new ModelLightsaberStand();

    public void render(TileEntityLightsaberStand tile, double x, double y, double z, float partialTicks)
    {
        ItemStack stack = tile.getDisplayStack();
        float scale = 0.15F;
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        GL11.glScalef(1, -1, -1);
        model.rStandTip.rotationPointX = 2.5F;
        model.lStandTip.rotationPointX = -3.5F;
        
        if (tile.getWorldObj() != null)
        {
            adjustRotation(tile, tile.getBlockMetadata());
            
            if (stack != null && stack.getItem() instanceof ItemLightsaber)
            {
                LightsaberData data = LightsaberData.get(stack);
                float f = Math.min(data.getHeightCm() - 25, 0) * 0.2F;
                
                model.rStandTip.rotationPointX += f;
                model.lStandTip.rotationPointX -= f;
            }
        }
        
        bindTexture(texture);
        model.render();
        
        if (tile.getWorldObj() != null && stack != null)
        {
            GL11.glTranslatef(0, 1.36F, 0);
            GL11.glRotatef(90, 0, 0, 1);
            
            GL11.glScalef(scale, scale, scale);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            
            if (stack.getItem() instanceof ItemDoubleLightsaber)
            {
                LightsaberData[] data = ItemDoubleLightsaber.get(stack);
                ALRenderHelper.renderLightsaberHilt(data);
            }
            else
            {
                LightsaberData data = LightsaberData.get(stack);
                ALRenderHelper.renderLightsaberHilt(data);
            }
            
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        
        GL11.glPopMatrix();
    }

    public void adjustRotation(TileEntityLightsaberStand tile, int metadata)
    {
        if (metadata > 1)
        {
            int[] matrix = {2, 0, 1, 3};
            GL11.glRotatef(90 * matrix[Math.min(metadata - 2, 3)], 0, 1, 0);
            GL11.glRotatef(90, 1, 0, 0);
        }
        else if (metadata == 1)
        {
            GL11.glRotatef(90, 0, 1, 0);
        }
        
        GL11.glTranslatef(0, -1, 0);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks)
    {
        render((TileEntityLightsaberStand) tile, x, y, z, partialTicks);
    }
}
