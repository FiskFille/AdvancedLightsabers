package com.fiskmods.lightsabers.client.render.item;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemHolocron implements IItemRenderer
{
    private final TileEntity tile = new TileEntityHolocron();
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        if (type == ItemRenderType.ENTITY)
        {
            GL11.glTranslatef(-0.5F, -0.25F, -0.5F);
        }
        else if (type == ItemRenderType.EQUIPPED)
        {
            GL11.glTranslatef(0.25F, 0.25F, 0.25F);
        }
        else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.FIRST_PERSON_MAP)
        {
            GL11.glTranslatef(0.25F, 0.25F, 0.25F);
        }

        tile.blockMetadata = item.getItemDamage();
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0F, 0.0F, 0.0F, 0.0F);
    }
}
