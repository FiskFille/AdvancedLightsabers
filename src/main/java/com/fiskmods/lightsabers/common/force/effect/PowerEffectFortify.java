package com.fiskmods.lightsabers.common.force.effect;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.client.sound.MovingSoundStatusEffect;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class PowerEffectFortify extends PowerEffectStatus
{
    public PowerEffectFortify(int amplifier)
    {
        super(Effect.FORTIFY, amplifier);
    }

    @Override
    public String[] getDesc()
    {
        return new String[] {PowerDesc.create("divide", Unit.FORCE_DAMAGE, getModifierAmount(amplifier))};
    }

    @Override
    public void start(EntityPlayer player, Side side)
    {
        if (side.isClient() && Lightsabers.proxy.isClientPlayer(player))
        {
            playSound(player);
        }
    }

    @SideOnly(Side.CLIENT)
    public void playSound(EntityPlayer player)
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundStatusEffect(player, Effect.FORTIFY, ALSounds.ambient_fortify));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void render(EntityPlayer player, float partialTicks)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Lightsabers.MODID, "textures/misc/force_shield.png"));

        GL11.glPushMatrix();
        Tessellator tessellator = Tessellator.instance;
        boolean prevLighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
        float prevWidth = GL11.glGetFloat(GL11.GL_LINE_WIDTH);

        ALRenderHelper.setLighting(61680);
        GL11.glLineWidth(5);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.99F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);

        float f = 0.25F * amplifier;
        float f1 = player.ticksExisted + partialTicks;
        float range = Math.max(player.width, player.height) * 0.75F;
        float angleIncr = 1;
        float coverage = 45;
        int amount = 360;
        float spread = 360F / amount;

        GL11.glTranslatef(0, 0, -range * 0.75F + (range * MathHelper.cos(f1 / 10) * 0.05F));
        GL11.glRotatef(MathHelper.sin(f1 / 10) * 5, 1, 0, 0);
        GL11.glRotatef(MathHelper.sin(f1 / 10 + 10) * 5, 0, 1, 0);
        GL11.glRotatef(MathHelper.sin(f1 / 10 + 20) * 5, 0, 0, 1);
        GL11.glTranslatef(0, 0, range);

        for (int i = 0; i < amount; ++i)
        {
            GL11.glPushMatrix();
            tessellator.startDrawing(3);
            tessellator.setColorRGBA(54 + (int) (146 * f), 84 + (int) (-84 * f), 181 + (int) (19 * f), 50);

            for (int j = 0; j < coverage / angleIncr; ++j)
            {
                Vec3 vec3 = Vec3.createVectorHelper(0, range, 0);
                float pitch = 90 + j * angleIncr;
                float yaw = 180;
                float roll = i * spread;
                vec3.rotateAroundX(-pitch * (float) Math.PI / 180.0F);
                vec3.rotateAroundY(-yaw * (float) Math.PI / 180.0F);
                vec3.rotateAroundZ(-roll * (float) Math.PI / 180.0F);
                tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, 0, j / (coverage / angleIncr));
            }

            tessellator.draw();
            GL11.glPopMatrix();
        }

        GL11.glLineWidth(prevWidth);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);

        if (prevLighting)
        {
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        ALRenderHelper.resetLighting();
        GL11.glPopMatrix();
    }

    public static float getModifierAmount(int amplifier)
    {
        float f = 0.25F;

        for (int i = 0; i < amplifier; ++i)
        {
            f *= 2;
        }

        return 1 + f;
    }
}
