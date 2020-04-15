package com.fiskmods.lightsabers.client.render.entity;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.entity.EntityLightsaber;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderLightsaber extends Render
{
    public void render(EntityLightsaber entity, double x, double y, double z, float f, float partialTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y + 0.03F, (float) z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90, 0, 1, 0);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0, 0, 1);
        
        float scale = 0.2F;
        float spin = (entity.ticksExisted + partialTicks) * 40;
        
        GL11.glRotatef(90, 0, 0, 1);
        GL11.glRotatef(spin, 1, 0, 0);
        GL11.glScalef(scale, scale, scale);
        renderLightsaber(entity, x, y, z, spin, partialTicks);
        GL11.glPopMatrix();
    }

    public void renderLightsaber(EntityLightsaber entity, double x, double y, double z, float spin, float partialTicks)
    {
        ItemStack itemstack = entity.getItem();
        
        if (itemstack != null)
        {
            if (itemstack.getItem() == ModItems.doubleLightsaber)
            {
                if (entity.data == null)
                {
                    entity.data = ItemDoubleLightsaber.get(itemstack);
                }
                
                ALRenderHelper.renderLightsaber((LightsaberData[]) entity.data, itemstack, true);
            }
            else
            {
                if (entity.data == null)
                {
                    entity.data = LightsaberData.get(itemstack);
                }
                
                ALRenderHelper.renderLightsaber((LightsaberData) entity.data, itemstack, true);
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float partialTicks)
    {
        render((EntityLightsaber) entity, x, y, z, f, partialTicks);
    }
}
