package com.fiskmods.lightsabers.client.gui;

import java.awt.Rectangle;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALEntityData;
import com.fiskmods.lightsabers.common.data.ALPlayerData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.effect.PowerEffect;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectActive;
import com.fiskmods.lightsabers.helper.ALHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mods.battlegear2.utils.BattlegearConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class GuiOverlay extends Gui
{
    private Minecraft mc = Minecraft.getMinecraft();

    public static final ResourceLocation ICONS = new ResourceLocation(Lightsabers.MODID, "textures/gui/icons.png");
    public static final ResourceLocation WIDGETS = new ResourceLocation(Lightsabers.MODID, "textures/gui/widgets.png");

    @SubscribeEvent
    public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre event)
    {
        ScaledResolution res = event.resolution;
        EntityPlayer player = mc.thePlayer;
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();

        if (event.type == ElementType.EXPERIENCE || event.type == ElementType.JUMPBAR)
        {
            if (ALHelper.getForcePowerMax(player) > 0)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(0, -6, 0);
            }
        }
        else if (event.type == ElementType.ALL)
        {
            if (mc.gameSettings.thirdPersonView == 0)
            {
                GL11.glPushMatrix();
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glTranslatef(0, 0, -0.5F);
                GL11.glRotatef(180, 0, 0, 1);
                GL11.glRotatef(0, 1, 0, 0);
                GL11.glRotatef(0, 0, 1, 0);

                for (StatusEffect status : ALEntityData.getData(player).activeEffects)
                {
                    Power power = status.effect.getPower(status.amplifier);
                    PowerEffect powerEffect = power.powerEffect;

                    if (powerEffect instanceof PowerEffectActive)
                    {
                        ((PowerEffectActive) powerEffect).render(player, event.partialTicks);
                    }
                }

                GL11.glPopMatrix();
            }

            if (ALHelper.getForcePowerMax(player) > 0 && GuiIngameForge.renderExperiance)
            {
                GuiIngameForge.left_height += 6;
                GuiIngameForge.right_height += 6;
            }
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event)
    {
        ScaledResolution res = event.resolution;
        EntityPlayer player = mc.thePlayer;
        int width = res.getScaledWidth();
        int height = res.getScaledHeight();

        if (event.type == ElementType.EXPERIENCE || event.type == ElementType.JUMPBAR)
        {
            if (ALHelper.getForcePowerMax(player) > 0)
            {
                GL11.glPopMatrix();
            }
        }
        else if (event.type == ElementType.HOTBAR)
        {
            GL11.glColor4f(1, 1, 1, 1);
            renderForceBar(event, width, height, player);
            renderPowerSelector(event, width, height, player);
            renderStatusEffects(event, width, height, player);
        }
    }

    public void renderForceBar(RenderGameOverlayEvent.Post event, int width, int height, EntityPlayer player)
    {
        mc.getTextureManager().bindTexture(ICONS);
        int cap = ALHelper.getForcePowerMax(player);
        int left = width / 2 - 91;
        int top = height - 32 + 3;

        if (cap > 0)
        {
            short barWidth = 182;
            int filled = (int) (ALData.FORCE_POWER.interpolate(player) / cap * barWidth);
            int filledDiff = (int) (ALData.FORCE_POWER_DIFF.interpolate(player) / cap * barWidth);

            GL11.glEnable(GL11.GL_BLEND);
            drawTexturedModalRect(left, top, 0, 74, barWidth, 5);

            if (filledDiff > filled && filledDiff > 0)
            {
                drawTexturedModalRect(left, top, 0, 79, filledDiff, 5);
            }

            if (filled > 0)
            {
                drawTexturedModalRect(left, top, 0, 84, filled, 5);
            }

            String s = MathHelper.floor_float(ALData.FORCE_POWER.get(player)) + "/" + cap;

            boolean prevUnicode = mc.fontRenderer.getUnicodeFlag();
            mc.fontRenderer.setUnicodeFlag(true);
            GL11.glPushMatrix();
            GL11.glTranslatef(left + barWidth / 2 - mc.fontRenderer.getStringWidth(s) / 2, top - 2, 0);

            mc.fontRenderer.drawString(s, 1, 0, 0);
            mc.fontRenderer.drawString(s, -1, 0, 0);
            mc.fontRenderer.drawString(s, 0, 1, 0);
            mc.fontRenderer.drawString(s, 0, -1, 0);

            for (int i = 0; i < 4; ++i)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(i == 0 ? 0.5F : i == 1 ? -0.5F : 0, i == 2 ? 0.5F : i == 3 ? -0.5F : 0, 0);
                mc.fontRenderer.drawString(s, 0, 0, 0x404040);
                GL11.glPopMatrix();
            }

            mc.fontRenderer.drawString(s, 0, 0, -1);
            mc.fontRenderer.setUnicodeFlag(prevUnicode);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public void renderPowerSelector(RenderGameOverlayEvent.Post event, int width, int height, EntityPlayer player)
    {
        mc.getTextureManager().bindTexture(WIDGETS);
        int left = width / 2 - 184;
        int top = height - 22;

        if (ALHelper.getForcePowerMax(player) > 0)
        {
            ALPlayerData data = ALPlayerData.getData(player);
            int selected = ALData.SELECTED_POWER.get(player);

            if (Lightsabers.isBattlegearLoaded)
            {
                mods.battlegear2.utils.BattlegearConfig config = new mods.battlegear2.utils.BattlegearConfig();

                if (new Rectangle(left + BattlegearConfig.battleBarOffset[0], top + BattlegearConfig.battleBarOffset[1], 62, 22).intersects(left, top, 62, 22))
                {
                    left += BattlegearConfig.battleBarOffset[0];
                    top += BattlegearConfig.battleBarOffset[1] - 25;
                }
            }

            GL11.glEnable(GL11.GL_BLEND);
            drawTexturedModalRect(left, top, 0, 0, 62, 22);
            mc.getTextureManager().bindTexture(ICONS);

            List<Power> selectedPowers = ALData.SELECTED_POWERS.get(player);

            for (int i = 0; i < selectedPowers.size(); ++i)
            {
                if (i <= 2)
                {
                    Power power = selectedPowers.get(i);

                    if (power != null && power.hasIcon())
                    {
                        drawTexturedModalRect(left + 3 + i * 20, top + 3, power.getIconX() * 16, power.getIconY() * 16, 16, 16);
                    }
                }
            }

            mc.getTextureManager().bindTexture(WIDGETS);
            drawTexturedModalRect(left - 1 + selected * 20, top - 1, 0, 22, 24, 24);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    public void renderStatusEffects(RenderGameOverlayEvent.Post event, int width, int height, EntityPlayer player)
    {
        GL11.glEnable(GL11.GL_BLEND);
        ALEntityData data = ALEntityData.getData(player);
        int left = width - 3;
        int top = height - 28;

        boolean prevUnicodeFlag = mc.fontRenderer.getUnicodeFlag();
        mc.fontRenderer.setUnicodeFlag(true);

        for (int i = 0; i < data.activeEffects.size(); ++i)
        {
            StatusEffect status = data.activeEffects.get(i);
            Effect e = status.effect;

            if (status.duration < 0)
            {
                continue;
            }

            mc.getTextureManager().bindTexture(ICONS);
            drawTexturedModalRect(left - 26, top - 26 - 28 * i, 0, 48, 26, 26);

            if (e != null)
            {
                Power power = e.getPower(status.amplifier);

                if (power == null)
                {
                    continue;
                }

                if (power.hasIcon())
                {
                    drawTexturedModalRect(left - 21, top - 21 - 28 * i, power.getIconX() * 16, power.getIconY() * 16, 16, 16);
                }

                String s = e.getLocalizedName();
                String s1 = StringUtils.ticksToElapsedTime(status.duration);

                if (status.amplifier > 0 && status.amplifier < 10)
                {
                    s += " " + StatCollector.translateToLocal("enchantment.level." + (status.amplifier + 1));
                }

                drawString(mc.fontRenderer, s, left - 30 - mc.fontRenderer.getStringWidth(s), top - 22 - 28 * i, -1);
                drawString(mc.fontRenderer, s1, left - 30 - mc.fontRenderer.getStringWidth(s1), top - 13 - 28 * i, -1);
            }
        }

        mc.fontRenderer.setUnicodeFlag(prevUnicodeFlag);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
