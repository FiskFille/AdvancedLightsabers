package fiskfille.lightsabers.client.render.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.common.tileentity.TileEntityLightsaberForge;

public class RenderItemLightsaberForge implements IItemRenderer
{
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
    	float scale = 0.65F;
        GL11.glScalef(scale, scale, scale);
        
        if (type == type.ENTITY || type == type.INVENTORY)
        {
        	GL11.glRotatef(-90, 0, 1, 0);
        	GL11.glTranslatef(0.0F, -0.75F, -0.5F);
        }
        else if (type == type.EQUIPPED)
        {
        	GL11.glRotatef(180, 0, 1, 0);
        	GL11.glTranslatef(-0.5F, 0.0F, -1.5F);
        }
        else if (type == type.EQUIPPED_FIRST_PERSON || type == type.FIRST_PERSON_MAP)
        {
        	GL11.glTranslatef(0.5F, 0.75F, 0.5F);
        }
        
        TileEntityLightsaberForge tile = new TileEntityLightsaberForge();
        tile.blockType = Block.getBlockFromItem(item.getItem());
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0F, 0.0F, 0.0F, 0.0F);
    }
}
