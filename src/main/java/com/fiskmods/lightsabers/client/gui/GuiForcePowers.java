package com.fiskmods.lightsabers.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerData;
import com.fiskmods.lightsabers.common.force.PowerManager;
import com.fiskmods.lightsabers.common.force.PowerStats;
import com.fiskmods.lightsabers.common.force.PowerType;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.network.PacketTileAction;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiForcePowers extends GuiScreen
{
    private static final int field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
    private static final int field_146571_z = AchievementList.minDisplayRow * 24 - 112;
    private static final int field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
    private static final int field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    protected GuiScreen prevScreen;
    protected int xSize = 256;
    protected int ySize = 202;
    protected int prevMouseX;
    protected int prevMouseY;
    protected float zoom = 1;
    protected double field_146569_s;
    protected double field_146568_t;
    protected double field_146567_u;
    protected double field_146566_v;
    protected double field_146565_w;
    protected double field_146573_x;
    private int field_146554_D;
    private PowerManager powerManager;

    private LinkedList<Power> powers = new LinkedList<Power>();

    public final TileEntityHolocron tile;

    public GuiForcePowers(GuiScreen gui, EntityPlayer player, TileEntityHolocron tileentity)
    {
        prevScreen = gui;
        tile = tileentity;
        powerManager = new PowerManager(player);
        short short1 = 141;
        short short2 = 141;
        field_146569_s = field_146567_u = field_146565_w = AchievementList.openInventory.displayColumn * 24 - short1 / 2 - 12;
        field_146568_t = field_146566_v = field_146573_x = AchievementList.openInventory.displayRow * 24 - short2 / 2;
        powers.clear();

        for (Power power : Power.POWERS)
        {
            powers.add(power);
        }
    }

    @Override
    public void initGui()
    {
        buttonList.add(new GuiOptionButton(0, width / 2 + 24, height / 2 + 74, 80, 20, I18n.format("gui.done")));
    }

    @Override
    public void onGuiClosed()
    {
        if (tile != null)
        {
            ALNetworkManager.wrapper.sendToServer(new PacketTileAction(mc.thePlayer, tile.xCoord, tile.yCoord, tile.zCoord, 1));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            mc.displayGuiScreen(prevScreen);
        }
    }

    @Override
    protected void keyTyped(char c, int key)
    {
        if (key == mc.gameSettings.keyBindInventory.getKeyCode())
        {
            mc.displayGuiScreen((GuiScreen) null);
            mc.setIngameFocus();
        }
        else
        {
            super.keyTyped(c, key);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (Mouse.isButtonDown(0))
        {
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            int i1 = x + 8;
            int j1 = y + 17;

            if ((field_146554_D == 0 || field_146554_D == 1) && mouseX >= i1 && mouseX < i1 + 224 && mouseY >= j1 && mouseY < j1 + 155)
            {
                if (field_146554_D == 0)
                {
                    field_146554_D = 1;
                }
                else
                {
                    field_146567_u -= (mouseX - prevMouseX) * zoom;
                    field_146566_v -= (mouseY - prevMouseY) * zoom;
                    field_146565_w = field_146569_s = field_146567_u;
                    field_146573_x = field_146568_t = field_146566_v;
                }

                prevMouseX = mouseX;
                prevMouseY = mouseY;
            }
        }
        else
        {
            field_146554_D = 0;
        }

        int i = Mouse.getDWheel();
        float prevZoom = zoom;

        if (i < 0)
        {
            zoom += 0.25F;
        }
        else if (i > 0)
        {
            zoom -= 0.25F;
        }

        zoom = MathHelper.clamp_float(zoom, 1, 2);

        if (zoom != prevZoom)
        {
            float f6 = prevZoom - zoom;
            float f5 = prevZoom * xSize;
            float f1 = prevZoom * ySize;
            float f2 = zoom * xSize;
            float f3 = zoom * ySize;
            field_146567_u -= (f2 - f5) * 0.5F;
            field_146566_v -= (f3 - f1) * 0.5F;
            field_146565_w = field_146569_s = field_146567_u;
            field_146573_x = field_146568_t = field_146566_v;
        }

        if (field_146565_w < field_146572_y)
        {
            field_146565_w = field_146572_y;
        }

        if (field_146573_x < field_146571_z)
        {
            field_146573_x = field_146571_z;
        }

        if (field_146565_w >= field_146559_A)
        {
            field_146565_w = field_146559_A - 1;
        }

        if (field_146573_x >= field_146560_B)
        {
            field_146573_x = field_146560_B - 1;
        }

        drawDefaultBackground();
        drawBackground(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen()
    {
        field_146569_s = field_146567_u;
        field_146568_t = field_146566_v;
        double d0 = field_146565_w - field_146567_u;
        double d1 = field_146573_x - field_146566_v;

        if (d0 * d0 + d1 * d1 < 4.0D)
        {
            field_146567_u += d0;
            field_146566_v += d1;
        }
        else
        {
            field_146567_u += d0 * 0.85D;
            field_146566_v += d1 * 0.85D;
        }
    }

    protected void drawForeground()
    {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        fontRendererObj.drawString(I18n.format("gui.forcePowers"), x + 15, y + 5, 4210752);

        x += 15;
        y += ySize - 25;
        drawOutlinedString(StatCollector.translateToLocalFormatted("gui.forcePowers.xp", MathHelper.floor_float(ALData.FORCE_XP.get(mc.thePlayer))), x, y, MathHelper.floor_float(ALData.FORCE_XP.get(mc.thePlayer)) > 0 ? 8453920 : 0xD74848);
        drawOutlinedString(StatCollector.translateToLocalFormatted("gui.forcePowers.basePower", ALData.BASE_POWER.get(mc.thePlayer)), x, y + 10, ALData.BASE_POWER.get(mc.thePlayer) > 0 ? 8453920 : 0xD74848);
    }

    public void drawOutlinedString(String s, int x, int y, int color)
    {
        fontRendererObj.drawString(s, x + 1, y, 0);
        fontRendererObj.drawString(s, x - 1, y, 0);
        fontRendererObj.drawString(s, x, y + 1, 0);
        fontRendererObj.drawString(s, x, y - 1, 0);
        fontRendererObj.drawString(s, x, y, color);
    }

    protected void drawBackground(int mouseX, int mouseY, float partialTicks)
    {
        int k = MathHelper.floor_double(field_146569_s + (field_146567_u - field_146569_s) * partialTicks);
        int l = MathHelper.floor_double(field_146568_t + (field_146566_v - field_146568_t) * partialTicks);

        if (k < field_146572_y)
        {
            k = field_146572_y;
        }

        if (l < field_146571_z)
        {
            l = field_146571_z;
        }

        if (k >= field_146559_A)
        {
            k = field_146559_A - 1;
        }

        if (l >= field_146560_B)
        {
            l = field_146560_B - 1;
        }

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        int k1 = x + 16;
        int l1 = y + 17;
        zLevel = 0;
        GL11.glDepthFunc(GL11.GL_GEQUAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(k1, l1, -200);
        // FIXES models rendering weirdly in the acheivements pane
        // see https://github.com/MinecraftForge/MinecraftForge/commit/1b7ce7592caafb760ec93066184182ae0711e793#commitcomment-10512284
        GL11.glScalef(1 / zoom, 1 / zoom, 1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        int i2 = k + 288 >> 4;
        int j2 = l + 288 >> 4;
        int k2 = (k + 288) % 16;
        int l2 = (l + 288) % 16;
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        Random random = new Random();
        float f1 = 16 / zoom;
        float f2 = 16 / zoom;
        int i3;
        int j3;
        int k3;

        for (i3 = 0; i3 * f1 - l2 < 155; ++i3)
        {
            float f3 = 0.6F - (float) (j2 + i3) / 25 * 0.3F;
            GL11.glColor4f(f3, f3, f3, 1);

            for (j3 = 0; j3 * f2 - k2 < 224; ++j3)
            {
                Block block = ModBlocks.lightForcestone;
                random.setSeed(mc.getSession().getPlayerID().hashCode() + i2 + j3 + (j2 + i3) * 16);
                k3 = random.nextInt(Math.max(1 + (i2 + j3 + 10) / 6, 0)) + (i2 + j3 + 10) / 1 - 11;

                if (k3 < 20)
                {
                    block = ModBlocks.darkForcestone;
                }

                k3 = random.nextInt(50);
                IIcon iicon = block.getIcon(random.nextInt(6), k3 > 32 ? (k3 > 40 ? 2 : 1) : 0);

                mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                drawTexturedModelRectFromIcon(j3 * 16 - k2, i3 * 16 - l2, iicon, 16, 16);
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int hierarchy;
        int maxWidth;
        int l4;

        for (i3 = 0; i3 < powers.size(); ++i3)
        {
            Power power1 = powers.get(i3);

            if (power1.parent != null && powers.contains(power1.parent))
            {
                j3 = power1.getXOffset() * 24 - k + 11;
                k3 = power1.getYOffset() * 24 - l + 11;
                l4 = power1.parent.getXOffset() * 24 - k + 11;
                int l3 = power1.parent.getYOffset() * 24 - l + 11;
                boolean flag5 = powerManager.hasPowerUnlocked(power1);
                boolean flag6 = powerManager.canUnlockPower(power1);
                hierarchy = powerManager.getHierarchy(power1);

                if (hierarchy <= 4)
                {
                    maxWidth = -16777216;

                    if (flag5)
                    {
                        maxWidth = -6250336;
                    }
                    else if (flag6)
                    {
                        maxWidth = -16711936;
                    }

                    Tessellator tessellator = Tessellator.instance;
                    float prevLineWidth = GL11.glGetFloat(GL11.GL_LINE_WIDTH);
                    GL11.glLineWidth(2 / zoom);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    float f5 = (maxWidth >> 24 & 255) / 255.0F;
                    float f = (maxWidth >> 16 & 255) / 255.0F;
                    float f3 = (maxWidth >> 8 & 255) / 255.0F;
                    float f4 = (maxWidth & 255) / 255.0F;
                    GL11.glColor4f(f, f3, f4, f5);
                    tessellator.startDrawing(3);
                    tessellator.addVertex(j3, k3, 0);
                    tessellator.addVertex(l4, l3, 0);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glLineWidth(prevLineWidth);
                }
            }
        }

        Power power = null;
        float f4 = (mouseX - k1) * zoom;
        float f5 = (mouseY - l1) * zoom;
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        int hoverX;
        int hoverY;

        for (l4 = 0; l4 < powers.size(); ++l4)
        {
            Power power1 = powers.get(l4);
            hoverX = power1.getXOffset() * 24 - k;
            hoverY = power1.getYOffset() * 24 - l;

            if (hoverX >= -24 && hoverY >= -24 && hoverX <= 224 * zoom && hoverY <= 155 * zoom)
            {
                hierarchy = powerManager.getHierarchy(power1);
                float f6;

                if (powerManager.hasPowerUnlocked(power1))
                {
                    f6 = 0.75F;
                    GL11.glColor4f(f6, f6, f6, 1);
                }
                else if (powerManager.canUnlockPower(power1))
                {
                    f6 = 1;
                    GL11.glColor4f(f6, f6, f6, 1);
                }
                else if (hierarchy < 3)
                {
                    f6 = 0.3F;
                    GL11.glColor4f(f6, f6, f6, 1);
                }
                else if (hierarchy == 3)
                {
                    f6 = 0.2F;
                    GL11.glColor4f(f6, f6, f6, 1);
                }
                else
                {
                    if (hierarchy != 4)
                    {
                        continue;
                    }

                    f6 = 0.1F;
                    GL11.glColor4f(f6, f6, f6, 1);
                }

                mc.getTextureManager().bindTexture(GUI_TEXTURES);

                GL11.glEnable(GL11.GL_BLEND);// Forge: Specifically enable blend because it is needed here. And we fix Generic RenderItem's leakage of it.
                drawTexturedModalRect(hoverX - 2, hoverY - 2, 0, 202, 26, 26);

                if (!powerManager.canUnlockPower(power1))
                {
                    f6 = 0.1F;
                    GL11.glColor4f(f6, f6, f6, 1);
                }

                GL11.glDisable(GL11.GL_LIGHTING); // Forge: Make sure Lighting is disabled. Fixes MC-33065
                GL11.glEnable(GL11.GL_CULL_FACE);
                
                if (power1.hasIcon())
                {
                    mc.getTextureManager().bindTexture(GuiOverlay.ICONS);
                    drawTexturedModalRect(hoverX + 3, hoverY + 3, power1.getIconX() * 16, power1.getIconY() * 16, 16, 16);
                }
                
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND); // Forge: Cleanup states we set.
                GL11.glColor4f(1, 1, 1, 1);

                if (f4 >= hoverX && f4 <= hoverX + 22 && f5 >= hoverY && f5 <= hoverY + 22)
                {
                    power = power1;
                }
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        zLevel = 0;
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        super.drawScreen(mouseX, mouseY, partialTicks);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        drawForeground();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Tooltip
        if (power != null)
        {
            String title = power.getLocalizedName();
            hoverX = mouseX + 12;
            hoverY = mouseY - 4;
            hierarchy = powerManager.getHierarchy(power);

            if (!powerManager.canUnlockPower(power))
            {
                String s;
                int k4;

                if (hierarchy == 3)
                {
                    title = I18n.format("achievement.unknown");
                    maxWidth = Math.max(fontRendererObj.getStringWidth(title), 120);
                    s = new ChatComponentTranslation("achievement.requires", new Object[] {power.parent.getLocalizedName()}).getUnformattedText();
                    k4 = fontRendererObj.splitStringWidth(s, maxWidth);
                    drawGradientRect(hoverX - 3, hoverY - 3, hoverX + maxWidth + 3, hoverY + k4 + 12 + 3, -1073741824, -1073741824);
                    fontRendererObj.drawSplitString(s, hoverX, hoverY + 12, maxWidth, -9416624);
                }
                else if (hierarchy < 3)
                {
                    maxWidth = Math.max(fontRendererObj.getStringWidth(title), 120);
                    s = new ChatComponentTranslation("achievement.requires", new Object[] {power.parent.getLocalizedName()}).getUnformattedText();
                    k4 = fontRendererObj.splitStringWidth(s, maxWidth);
                    drawGradientRect(hoverX - 3, hoverY - 3, hoverX + maxWidth + 3, hoverY + k4 + 12 + 3, -1073741824, -1073741824);
                    fontRendererObj.drawSplitString(s, hoverX, hoverY + 12, maxWidth, -9416624);
                }
                else
                {
                    title = null;
                }
            }
            else
            {
                maxWidth = Math.max(fontRendererObj.getStringWidth(title), 120);

                List<String> desc = new ArrayList();
                PowerStats stats = power.powerStats;

                if (power.getActualXpCost(mc.thePlayer) != 0)
                {
                    desc.add((!powerManager.hasPowerUnlocked(power) ? EnumChatFormatting.RED : "") + I18n.format("forcepower.cost", power.getActualXpCost(mc.thePlayer)));
                }

                if (stats.baseRequirement != 0)
                {
                    desc.add((!powerManager.hasPowerUnlocked(power) && ALData.BASE_POWER.get(mc.thePlayer) < stats.baseRequirement ? EnumChatFormatting.RED : "") + I18n.format("forcepower.basePowerReq", stats.baseRequirement));
                }

                if (stats.useCost != 0)
                {
                    desc.add(I18n.format(stats.powerType == PowerType.PER_USE ? "forcepower.perUse" : stats.powerType == PowerType.PER_SECOND ? "forcepower.perSecond" : "forcepower.passive", ItemStack.field_111284_a.format(stats.useCost)));
                }

                if (stats.baseBonus != 0)
                {
                    desc.add(I18n.format("forcepower.basePower", (stats.baseBonus < 0 ? "-" : "+") + stats.baseBonus));
                }

                if (stats.forceBonus != 0)
                {
                    desc.add(I18n.format("forcepower.forcePower", (stats.forceBonus < 0 ? "-" : "+") + stats.forceBonus));
                }

                if (stats.regen != 0)
                {
                    desc.add(I18n.format("forcepower.forceRegen", (stats.regen < 0 ? "-" : "+") + stats.regen + (stats.regenOperation == 1 ? "%" : "")));
                }

                if (power.powerEffect != null)
                {
                    String[] astring = power.powerEffect.getDesc();

                    if (astring.length > 0)
                    {
                        desc.add("");
                        desc.addAll(Arrays.asList(astring));
                    }
                }

                for (String s : desc)
                {
                    maxWidth = Math.max(fontRendererObj.getStringWidth(s), maxWidth);
                }

                PowerData data = PowerManager.getPowerData(mc.thePlayer, power);
                String xpLeft = I18n.format("forcepower.xpLeft", power.getActualXpCost(mc.thePlayer) - data.xpInvested);
                maxWidth = Math.max(fontRendererObj.getStringWidth(xpLeft), maxWidth);

                drawGradientRect(hoverX - 3, hoverY - 3, hoverX + maxWidth + 3, hoverY + 6 + (2 + fontRendererObj.FONT_HEIGHT) * (desc.size() + 1) + 12, -1073741824, -1073741824);
                int height = hoverY + fontRendererObj.FONT_HEIGHT + 4;

                for (String s2 : desc)
                {
                    fontRendererObj.drawStringWithShadow(s2, hoverX, height, 0xA4A4A4);
                    height += 2 + fontRendererObj.FONT_HEIGHT;
                }

                if (powerManager.hasPowerUnlocked(power))
                {
                    fontRendererObj.drawStringWithShadow(I18n.format("forcepower.unlocked"), hoverX, height + 3, -7302913);
                }
                else
                {
                    fontRendererObj.drawStringWithShadow(xpLeft, hoverX, height + 3, -7302913);
                }
            }

            if (title != null)
            {
                fontRendererObj.drawStringWithShadow(title, hoverX, hoverY, powerManager.canUnlockPower(power) ? -1 : -8355712);
            }

            if (Mouse.isButtonDown(0) && !powerManager.hasPowerUnlocked(power) && powerManager.canUnlockPower(power) && (MathHelper.floor_double(ALData.FORCE_XP.get(mc.thePlayer)) > 0 || power.getActualXpCost(mc.thePlayer) == 0) && (ALData.BASE_POWER.get(mc.thePlayer) >= power.powerStats.baseRequirement || power.powerStats.baseRequirement == 0))
            {
                ALData.DRAINING_XP_TO.set(mc.thePlayer, power.getName());
            }
            else
            {
                ALData.DRAINING_XP_TO.set(mc.thePlayer, "");
            }
        }
        else
        {
            ALData.DRAINING_XP_TO.set(mc.thePlayer, "");
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
