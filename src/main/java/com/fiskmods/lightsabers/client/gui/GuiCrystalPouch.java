package com.fiskmods.lightsabers.client.gui;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.container.ContainerCrystalPouch;
import com.fiskmods.lightsabers.common.container.InventoryCrystalPouch;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.helper.ALRenderHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiCrystalPouch extends GuiContainer
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/crystal_pouch.png");

    public GuiCrystalPouch(InventoryPlayer inventoryPlayer, InventoryCrystalPouch inventory)
    {
        super(new ContainerCrystalPouch(inventoryPlayer, inventory));
        ySize = 150;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        fontRendererObj.drawString(I18n.format("gui.crystal_pouch"), 8, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 93, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        ALRenderHelper.setupRenderItemIntoGUI();
        GL11.glColor4f(0.6F, 0.6F, 0.6F, 0.125F);
        boolean prevColor = itemRender.renderWithColor;
        itemRender.renderWithColor = false;
        
        ContainerCrystalPouch container = (ContainerCrystalPouch) inventorySlots;
        InventoryCrystalPouch inventory = container.inventory;
        
        for (int i = 0; i < CrystalColor.values().length; ++i)
        {
            if (inventory.getStackInSlot(i) == null)
            {
                int[] pos = {8 + (i % 9) * 18, 18 + i / 9 * 18};
                
                GL11.glColor4f(1, 1, 1, 0.25F);
                itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), ItemCrystal.create(CrystalColor.values()[i]), guiLeft + pos[0], guiTop + pos[1]);
            }
        }
        
        itemRender.renderWithColor = prevColor;
        ALRenderHelper.finishRenderItemIntoGUI();
        GL11.glColor4f(1, 1, 1, 1);
    }
}
