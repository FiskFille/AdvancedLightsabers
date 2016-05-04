package fiskfille.lightsabers.client.gui;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.common.container.ContainerSithCoffin;
import fiskfille.lightsabers.common.tileentity.TileEntitySithCoffin;

@SideOnly(Side.CLIENT)
public class GuiSithCoffin extends GuiContainer
{
    private static final ResourceLocation guiTextures = new ResourceLocation("textures/gui/container/generic_54.png");
    private TileEntitySithCoffin tileentity;

    public GuiSithCoffin(InventoryPlayer inventoryPlayer, TileEntitySithCoffin tile)
    {
        super(new ContainerSithCoffin(inventoryPlayer, tile));
        tileentity = tile;
        ySize = 168;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tileentity.hasCustomInventoryName() ? tileentity.getInventoryName() : I18n.format(tileentity.getInventoryName(), new Object[0]);
        fontRendererObj.drawString(s, 8, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 94, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(guiTextures);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, 17);
        drawTexturedModalRect(k, l + 17, 0, 71, xSize, ySize - 17);
    }
}
