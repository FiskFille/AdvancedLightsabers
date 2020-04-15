package com.fiskmods.lightsabers.client.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerData;
import com.fiskmods.lightsabers.common.force.PowerStats;
import com.fiskmods.lightsabers.common.force.PowerType;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiSelectPowers extends GuiScreen
{
    public static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/force_power_selector.png");
    protected int xSize = 176;
    protected int ySize = 166;

    public int grabbedId = -1;
    public int grabbedOffsetX;
    public int grabbedOffsetY;

    public List<PowerSlot> slots = new ArrayList();
    public Power[] selectedPowers = new Power[3];

    @Override
    public void initGui()
    {
        super.initGui();
        slots.clear();
        selectedPowers = ALData.SELECTED_POWERS.get(mc.thePlayer).toArray(selectedPowers);
        List<PowerData> list = ALHelper.getRelevantPowers(mc.thePlayer);
        Collections.sort(list);
        
        PowerData[] powers = list.toArray(new PowerData[16]);

        for (int x = 0; x < 4; ++x)
        {
            for (int y = 0; y < 4; ++y)
            {
                PowerData data = powers[y + x * 4];
                slots.add(new PowerSlot(data != null ? data.power : null, y + x * 4, 8 + y * 18, 8 + x * 18));
            }
        }
    }

    @Override
    protected void keyTyped(char c, int key)
    {
        super.keyTyped(c, key);

        if (key == mc.gameSettings.keyBindInventory.getKeyCode())
        {
            mc.displayGuiScreen((GuiScreen) null);
            mc.setIngameFocus();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        boolean flag = false;

        if (button == 0 && isShiftKeyDown())
        {
            List<Integer> indexes = Lists.newArrayList();

            for (int i = 0; i < selectedPowers.length; ++i)
            {
                if (selectedPowers[i] == null)
                {
                    indexes.add(i);
                }
            }

            if (!indexes.isEmpty() && grabbedId < 0)
            {
                for (PowerSlot slot : slots)
                {
                    int x1 = x + slot.x;
                    int y1 = y + slot.y;

                    if (slot.power != null && new Rectangle(x1 - 1, y1 - 1, 18, 18).contains(mouseX, mouseY))
                    {
                        selectedPowers[indexes.get(0)] = slot.power;
                        flag = true;
                    }
                }
            }

            if (grabbedId < 0)
            {
                for (int i = 0; i < 3; ++i)
                {
                    if (selectedPowers[i] != null && new Rectangle(x + 62 + i * 18, y + 142, 16, 16).contains(mouseX, mouseY))
                    {
                        selectedPowers[i] = null;
                        flag = true;
                    }
                }
            }
        }

        if (flag)
        {
            ALData.SELECTED_POWERS.setWithoutNotify(mc.thePlayer, Lists.newArrayList(selectedPowers));
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int button, long time)
    {
        super.mouseClickMove(mouseX, mouseY, button, time);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        if (button == 0)
        {
            if (grabbedId < 0)
            {
                for (PowerSlot slot : slots)
                {
                    int x1 = x + slot.x;
                    int y1 = y + slot.y;

                    if (slot.power != null && new Rectangle(x1 - 1, y1 - 1, 18, 18).contains(mouseX, mouseY))
                    {
                        grabbedId = slot.id;
                        grabbedOffsetX = x1 - mouseX;
                        grabbedOffsetY = y1 - mouseY;
                    }
                }
            }
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int signature)
    {
        super.mouseMovedOrUp(mouseX, mouseY, signature);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        if (signature == 0)
        {
            if (grabbedId >= 0)
            {
                for (int i = 0; i < 3; ++i)
                {
                    if (new Rectangle(x + 62 + i * 18, y + 142, 16, 16).contains(mouseX, mouseY))
                    {
                        PowerSlot slot = slots.get(grabbedId);

                        if (slot != null)
                        {
                            selectedPowers[i] = slot.power;
                            ALData.SELECTED_POWERS.setWithoutNotify(mc.thePlayer, Lists.newArrayList(selectedPowers));
                        }
                    }
                }
            }

            grabbedId = -1;
        }
    }

    public PowerSlot getSlotFor(Power power)
    {
        for (PowerSlot slot : slots)
        {
            if (slot.power == power)
            {
                return slot;
            }
        }

        return null;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        mc.getTextureManager().bindTexture(GuiOverlay.ICONS);
        Power grabPower = null;
        Power hoverPower = null;

        for (PowerSlot slot : slots)
        {
            Power power = slot.power;
            int x1 = x + slot.x;
            int y1 = y + slot.y;

            if (power != null)
            {
                if (power.hasIcon())
                {
                    drawTexturedModalRect(x1, y1, power.getIconX() * 16, power.getIconY() * 16, 16, 16);
                }

                if (grabbedId == slot.id)
                {
                    grabPower = power;
                }
            }

            if (new Rectangle(x1 - 1, y1 - 1, 18, 18).contains(mouseX, mouseY))
            {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glColorMask(true, true, true, false);
                drawGradientRect(x1, y1, x1 + 16, y1 + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_BLEND);

                hoverPower = power;
            }
        }

        int grabX = mouseX + grabbedOffsetX;
        int grabY = mouseY + grabbedOffsetY;

        for (int i = 0; i < selectedPowers.length; ++i)
        {
            Power power = selectedPowers[i];

            int x1 = x + 62 + i * 18;
            int y1 = y + 142;
            boolean flag = new Rectangle(x1 - 1, y1 - 1, 18, 18).contains(mouseX, mouseY);

            if (flag)
            {
                grabX = x1;
                grabY = y1;
            }

            if (power != null && power.hasIcon() && (grabX != x1 || grabY != y1 || grabPower == null))
            {
                drawTexturedModalRect(x1, y1, power.getIconX() * 16, power.getIconY() * 16, 16, 16);
            }

            if (flag)
            {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glColorMask(true, true, true, false);
                drawGradientRect(x1, y1, x1 + 16, y1 + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_BLEND);

                hoverPower = power;
            }
        }

        if (hoverPower != null)
        {
            Tessellator tessellator = Tessellator.instance;
            float f = 1F / 256;
            float scale = 70;

            if (hoverPower.hasIcon())
            {
                TextureUtil.func_152777_a(false, false, 1);
                GL11.glPushMatrix();
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glTranslatef(x + 126, y + 60.5F, zLevel + 20);
                GL11.glScalef(scale, -scale, -scale);
                GL11.glTranslatef(-0.5F, -0.25F, -(0.0625F + 0.021875F) * 0.5F);
                GL11.glTranslatef(0, 0, 0.0625F + 0.021875F);
                GL11.glColor4f(1, 1, 1, 1);
                ItemRenderer.renderItemIn2D(tessellator, hoverPower.getIconX() * 16 * f, hoverPower.getIconY() * 16 * f, (hoverPower.getIconX() * 16 + 16) * f, (hoverPower.getIconY() * 16 + 16) * f, 16, 16, 0);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glPopMatrix();
                TextureUtil.func_147945_b();
            }

            String name = hoverPower.getLocalizedName();
            int x1 = x + 8;
            int y1 = y + 84;
            int fieldWidth = Math.max(fontRendererObj.getStringWidth(name), 160);

            List<String> list = Lists.newArrayList();
            PowerStats stats = hoverPower.powerStats;

            if (hoverPower.powerEffect != null)
            {
                String[] astring = hoverPower.powerEffect.getDesc();

                for (String s : astring)
                {
                    list.add(s);
                }
            }

            for (String desc : list)
            {
                fieldWidth = Math.max(fontRendererObj.getStringWidth(desc), fieldWidth);
            }

            drawGradientRect(x1, y1, x1 + fieldWidth, y1 + Math.max((fontRendererObj.FONT_HEIGHT + 2) * list.size() + fontRendererObj.FONT_HEIGHT * 2 + 12, 52), 0xA5222222, 0xA5222222);
            int height = y1 + fontRendererObj.FONT_HEIGHT + 4;

            if (name != null)
            {
                fontRendererObj.drawStringWithShadow(name, x1 + 3, y1 + 3, -1);
            }

            if (stats.useCost > 0)
            {
                fontRendererObj.drawStringWithShadow(I18n.format(stats.powerType == PowerType.PER_USE ? "forcepower.perUse" : "forcepower.perSecond", ItemStack.field_111284_a.format(stats.useCost)), x1 + 3, height + 3, 0xa4a4a4);
                height += 5 + fontRendererObj.FONT_HEIGHT;
            }

            for (String desc : list)
            {
                fontRendererObj.drawStringWithShadow(desc, x1 + 3, height + 3, 0xa4a4a4);
                height += 2 + fontRendererObj.FONT_HEIGHT;
            }

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
        }

        if (grabPower != null && grabPower.hasIcon())
        {
            mc.getTextureManager().bindTexture(GuiOverlay.ICONS);
            drawTexturedModalRect(grabX, grabY, grabPower.getIconX() * 16, grabPower.getIconY() * 16, 16, 16);
        }

        GL11.glDisable(GL11.GL_BLEND);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void onGuiClosed()
    {
        ALData.SELECTED_POWERS.sync(mc.thePlayer);
    }

    private class PowerSlot
    {
        public Power power;
        public int id;
        public int x;
        public int y;

        public PowerSlot(Power power, int id, int x, int y)
        {
            this.power = power;
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
}
