package fiskfille.lightsabers.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.common.entity.EntityLightsaber;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

@SideOnly(Side.CLIENT)
public class RenderLightsaberEntity extends Render
{
    public void render(EntityLightsaber entity, double x, double y, double z, float f, float partialTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 0.03F, (float)z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        float f1 = entity.ticksExisted - 1 + partialTicks;
        float f2 = entity.getThrower().isDead ? 0 : f1 * 40;
        GL11.glRotatef(90, 0, 0, 1);
        GL11.glRotatef(f2, 1, 0, 0);
        
        renderLightsaber(entity, x, y, z, f2, partialTicks);
        GL11.glPopMatrix();
    }
    
    public void renderLightsaber(EntityLightsaber entity, double x, double y, double z, float f, float partialTicks)
    {
    	ItemStack item = entity.getLightsaberItem();
    	float height = LightsaberHelper.getPart(item, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(item, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(item, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(item, EnumPartType.POMMEL).getPommel().height;
		float f1 = (-(LightsaberHelper.getPart(item, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(item, EnumPartType.POMMEL).getPommel().height) + height / 2) * 0.0625F;
    	
    	GL11.glPushMatrix();
		float scale = 0.5F * 0.4F;
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(0, f1, 0);
		ALRenderHelper.renderLightsaberHilt(item);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		scale = 0.5F * 0.4F;
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(0, f1, 0);
		GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
		scale = 1.5F * 0.4F;
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(0, -(LightsaberHelper.getPart(item, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(item, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
		ALRenderHelper.renderLightsaberBlade(item, true);
		GL11.glPopMatrix();
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
    
    public void doRender(Entity entity, double x, double y, double z, float f, float partialTicks)
    {
        this.render((EntityLightsaber)entity, x, y, z, f, partialTicks);
    }
}
