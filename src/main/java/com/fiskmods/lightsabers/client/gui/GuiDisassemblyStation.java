package com.fiskmods.lightsabers.client.gui;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.container.ContainerDisassemblyStation;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiDisassemblyStation extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/disassembly_station.png");

    private TileEntityDisassemblyStation tileentity;

    public GuiDisassemblyStation(InventoryPlayer inventoryPlayer, TileEntityDisassemblyStation tile)
    {
        super(new ContainerDisassemblyStation(inventoryPlayer, tile));
        tileentity = tile;
        ySize = 168;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = I18n.format(tileentity.getInventoryName());
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (tileentity.isBurning())
        {
            int i = tileentity.getBurnTimeRemainingScaled(13);
            drawTexturedModalRect(guiLeft + 56 - 39, guiTop + 37 + 12 - i, 176, 12 - i, 14, i + 2);

            i = tileentity.getCookProgressScaled(24);
            drawTexturedModalRect(guiLeft + 79 - 40, guiTop + 36, 176, 14, i + 1, 16);
        }
    }
}
