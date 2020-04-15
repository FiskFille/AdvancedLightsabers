package com.fiskmods.lightsabers.client.render.item;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberStand;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public enum RenderItemLightsaberStand implements IItemRenderer
{
    INSTANCE;
    
    private final TileEntityLightsaberStand tile = new TileEntityLightsaberStand();
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        float scale = 2.0F;
        
        if (type == ItemRenderType.INVENTORY)
        {
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glTranslatef(-0.5F, -0.125F, -0.5F);
        }
        else if (type == ItemRenderType.ENTITY)
        {
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(-0.5F, 0, -0.5F);
        }
        else if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glTranslatef(-0.5F, 0.25F, -0.5F);
            GL11.glScalef(scale, scale, scale);
        }
        else
        {
            GL11.glScalef(scale, scale, scale);
        }

        try
        {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0, 1, 0, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
