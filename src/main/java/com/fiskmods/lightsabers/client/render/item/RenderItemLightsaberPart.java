package com.fiskmods.lightsabers.client.render.item;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.render.hilt.HiltRenderer;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.item.ItemLightsaberPart;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemLightsaberPart implements IItemRenderer
{
    public PartType partType;

    public RenderItemLightsaberPart(PartType type)
    {
        partType = type;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return type == ItemRenderType.ENTITY || type == ItemRenderType.INVENTORY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data)
    {
        Hilt hilt = ItemLightsaberPart.get(itemstack);
        HiltRenderer renderer = HiltRenderer.get(hilt);
        
        float scale = 0.4F;
        float height = hilt.getPart(partType).height;
        float f = height * (partType.isLowerPart() ? -1 : 1) / 2 * 0.0625F;
        
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(renderer.getTexture(partType));

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glRotatef(-100, 0, 1, 0);
            GL11.glRotatef(-150, 1, 0, 0);
            GL11.glRotatef(5, 0, 0, 1);
            GL11.glTranslatef(0, 0.15F, 0.9F);
        }
        else if (type == ItemRenderType.EQUIPPED)
        {
            GL11.glRotatef(-90, 0, 1, 0);
            GL11.glRotatef(-150, 1, 0, 0);
            GL11.glRotatef(0, 0, 0, 1);
            GL11.glTranslatef(0.1F, 0.15F, 0.475F);
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glRotatef(-20, 0, 1, 1);
            GL11.glRotatef(-10, 1, 0, 0);
        }
        else if (type == ItemRenderType.ENTITY)
        {
            GL11.glRotatef(180, 1, 0, 0);
        }
        else if (type == ItemRenderType.INVENTORY)
        {
            GL11.glRotatef(-20, 0, 0, 1);
            GL11.glRotatef(-45, 1, 0, 0);
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glTranslatef(0, 0.05F, 0);
            GL11.glRotatef(-110, 0, 1, 0);

            if (partType == PartType.POMMEL && height <= 4)
            {
                scale = 2;
            }
            else
            {
                scale = 1;
            }
            
            if (height * scale > 20)
            {
                scale = 20 / height;
            }
        }

        if (scale != 1)
        {
            GL11.glScalef(scale, scale, scale);
        }
        
        GL11.glTranslatef(0, f, 0);
        renderer.getModel(partType).render(null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }
}
