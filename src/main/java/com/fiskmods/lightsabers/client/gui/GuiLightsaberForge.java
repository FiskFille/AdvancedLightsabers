package com.fiskmods.lightsabers.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.container.ContainerLightsaberForge;
import com.fiskmods.lightsabers.common.container.InventoryLightsaberForge;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemLightsaberPart;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;
import com.fiskmods.lightsabers.helper.ALRenderHelper;
import com.google.common.collect.Iterables;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiLightsaberForge extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/lightsaber_forge.png");

    public GuiLightsaberForge(InventoryPlayer inventoryPlayer, TileEntityLightsaberForge tile)
    {
        super(new ContainerLightsaberForge(inventoryPlayer, tile));
        ySize = 196;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = I18n.format("gui.lightsaber_forge");
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);
        
        ContainerLightsaberForge container = (ContainerLightsaberForge) inventorySlots;
        InventoryLightsaberForge inventory = container.craftMatrix;
        LightsaberData data = inventory.result;

        if (data != null)
        {
            if (data.isTooShort())
            {
                GL11.glColor4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(GUI_TEXTURES);
                drawTexturedModalRect(131, 65, 176, 0, 26, 17);
                
                GL11.glTranslatef(0, 0, 300);
                drawString(fontRendererObj, I18n.format("gui.lightsaber_forge.too_short"), 45, 64 - fontRendererObj.FONT_HEIGHT, 0xD74848);
            }
            else
            {
                GL11.glTranslatef(0, 0, 300);
                drawString(fontRendererObj, I18n.format("%s cm", ItemStack.field_111284_a.format(data.getHeightCm())), 45, 64 - fontRendererObj.FONT_HEIGHT, -1);
            }
            
            GL11.glTranslatef(0, 0, -300);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        ContainerLightsaberForge container = (ContainerLightsaberForge) inventorySlots;
        InventoryLightsaberForge inventory = container.craftMatrix;
        LightsaberData data = inventory.result;

        if (data != null)
        {
            float spin = mc.thePlayer.ticksExisted + partialTicks;

            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glTranslatef(guiLeft + 110, guiTop + 40, 20);
            GL11.glRotatef((float) Math.sin(spin / 20) * 2.5F, 0, 1, 0);
            GL11.glRotatef((float) Math.sin(spin / 20 + 2) * 2.5F, 0, 0, 1);
            GL11.glRotatef(-90, 0, 0, 1);
            GL11.glRotatef(90 + spin, 0, 1, 0);
            GL11.glScalef(-20, 20, 20);
            RenderHelper.enableGUIStandardItemLighting();
            ALRenderHelper.startGlScissor(guiLeft + 43, guiTop + 17, 113, 47);
            ALRenderHelper.renderLightsaber(data, container.craftResult.getStackInSlot(0), false);
            ALRenderHelper.endGlScissor();
            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();
            RenderHelper.disableStandardItemLighting();
        }
        else
        {
            Hilt hilt = null;
            
            for (int slot = 0; slot < 4; ++slot)
            {
                ItemStack stack = inventory.getStackInSlot(slot);

                if (stack != null)
                {
                    if (hilt == null || hilt == ItemLightsaberPart.get(stack))
                    {
                        hilt = ItemLightsaberPart.get(stack);
                    }
                    else
                    {
                        hilt = null;
                        break;
                    }
                }
            }
            
            if (hilt == null)
            {
                hilt = Iterables.get(Hilt.REGISTRY, (mc.thePlayer.ticksExisted / 20) % Hilt.REGISTRY.getKeys().size());
            }
            
            ALRenderHelper.setupRenderItemIntoGUI();
            GL11.glColor4f(0.6F, 0.6F, 0.6F, 0.125F);
            boolean prevColor = itemRender.renderWithColor;
            itemRender.renderWithColor = false;
            
            if (hilt != null)
            {
                for (int slot = 0; slot < 4; ++slot)
                {
                    if (inventory.getStackInSlot(slot) == null)
                    {
                        int[] pos = ContainerLightsaberForge.SLOTS[slot];
                        itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), ItemLightsaberPart.create(PartType.values()[slot], hilt), guiLeft + pos[0], guiTop + pos[1]);
                    }
                }
                
                if (inventory.getStackInSlot(5) == null)
                {
                    int[] pos = ContainerLightsaberForge.SLOTS[5];
                    
                    GL11.glColor4f(1, 1, 1, 0.25F);
                    itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), ItemCrystal.create(hilt.getColor()), guiLeft + pos[0], guiTop + pos[1]);
                }
            }
            
            itemRender.renderWithColor = prevColor;
            GL11.glColor4f(1, 1, 1, 1);
            ALRenderHelper.finishRenderItemIntoGUI();
        }
    }
}
