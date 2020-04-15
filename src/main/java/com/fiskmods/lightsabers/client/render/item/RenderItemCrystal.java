package com.fiskmods.lightsabers.client.render.item;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemCrystal implements IItemRenderer
{
    private final TileEntityCrystal tile = new TileEntityCrystal();
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return type != ItemRenderType.EQUIPPED;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        float scale = 2.5F;

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glTranslatef(-0.5F, 0.5F, -0.5F);
        }
        else if (type == ItemRenderType.EQUIPPED)
        {
            GL11.glRotatef(20, 0, 0, 1);
            GL11.glRotatef(15, 1, 0, 0);
            GL11.glTranslatef(-0.275F, -0.05F, -0.85F);
            scale /= 1.75F;
        }
        else if (type == ItemRenderType.ENTITY)
        {
            GL11.glTranslatef(-1.25F, -0.5F, -1.25F);
        }
        else if (type == ItemRenderType.INVENTORY)
        {
            GL11.glRotatef(180, 0, 1, 0);
            GL11.glTranslatef(-0.5F, -1, -0.5F);
        }

        GL11.glScalef(scale, scale, scale);
        tile.setColor(ItemCrystal.get(item));
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0, 0, 0, 1);
        GL11.glColor4f(1, 1, 1, 1);
    }
}
