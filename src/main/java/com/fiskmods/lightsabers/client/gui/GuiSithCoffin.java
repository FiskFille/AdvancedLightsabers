package com.fiskmods.lightsabers.client.gui;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.common.container.ContainerSithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiSithCoffin extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("textures/gui/container/generic_54.png");
    private TileEntitySithCoffin tileentity;

    public GuiSithCoffin(InventoryPlayer inventoryPlayer, TileEntitySithCoffin tile)
    {
        super(new ContainerSithCoffin(inventoryPlayer, tile));
        tileentity = tile;
        ySize = 168;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tileentity.hasCustomInventoryName() ? tileentity.getInventoryName() : I18n.format(tileentity.getInventoryName(), new Object[0]);
        fontRendererObj.drawString(s, 8, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, 17);
        drawTexturedModalRect(k, l + 17, 0, 71, xSize, ySize - 17);
    }
}
