package fiskfille.lightsabers.client.render.entity;

import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.common.entity.EntityLightsaber;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

@SideOnly(Side.CLIENT)
public class RenderDoubleLightsaberEntity extends RenderLightsaberEntity
{
    @Override
    public void renderLightsaber(EntityLightsaber entity, double x, double y, double z, float f, float partialTicks)
    {
        ItemStack item = entity.getLightsaberItem();
        ItemStack upper = LightsaberHelper.getDoubleLightsaberUpper(item);
        ItemStack lower = LightsaberHelper.getDoubleLightsaberLower(item);

        float scale;

        for (int i = 0; i < 2; ++i)
        {
            ItemStack itemstack = new ItemStack[] {upper, lower}[i];
            float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
            float f1 = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

            GL11.glPushMatrix();
            GL11.glRotatef(180 * i, 0, 0, 1);
            scale = 0.5F * 0.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f1, 0);
            ALRenderHelper.renderLightsaberHilt(itemstack);
            GL11.glPopMatrix();
        }

        for (int i = 0; i < 2; ++i)
        {
            ItemStack itemstack = new ItemStack[] {upper, lower}[i];
            float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
            float f1 = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height)) * 0.0625F;

            GL11.glPushMatrix();
            GL11.glRotatef(180 * i, 0, 0, 1);
            scale = 0.5F * 0.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f1, 0);
            GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
            scale = 1.5F * 0.4F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, -(LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
            ALRenderHelper.renderLightsaberBlade(itemstack, true);
            GL11.glPopMatrix();
        }
    }
}
