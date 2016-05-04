package fiskfille.lightsabers.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.container.ContainerLightsaberForge;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

@SideOnly(Side.CLIENT)
public class GuiLightsaberForge extends GuiContainer
{
    private ResourceLocation texture = new ResourceLocation(Lightsabers.modid, "textures/gui/container/lightsaber_forge.png");

    public GuiLightsaberForge(InventoryPlayer inventoryPlayer, World world, int x, int y, int z)
    {
        super(new ContainerLightsaberForge(inventoryPlayer, world, x, y, z));
        ySize = 204;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        String s = StatCollector.translateToLocal("gui.lightsaber_forge");
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
        
        ItemStack itemstack = ((ContainerLightsaberForge)inventorySlots).craftResult.getStackInSlot(0);
        
        if (itemstack != null)
        {
        	itemstack = itemstack.copy();
        	itemstack.getTagCompound().setBoolean("active", true);
        	
        	float spin = (float)mc.thePlayer.ticksExisted;
        	short short1 = 240;
            short short2 = 240;
        	GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glPushMatrix();
            ALRenderHelper.startGlScissor(guiLeft + 43, guiTop + 17, 113, 47);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glTranslatef(k + 110, l + 40, 50.0F);
            
            GL11.glRotatef((float)Math.sin(spin / 20) * 2.5F, 0, 1, 0);
            GL11.glRotatef((float)Math.sin(spin / 20 + 2) * 2.5F, 0, 0, 1);
            GL11.glRotatef(-90, 0, 0, 1);
            GL11.glRotatef(90 + spin, 0, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            
            float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
    		float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height) + height / 2) * 0.0625F;
            float scale = 50.0F;
            GL11.glScalef(-scale, scale, scale);
            
            GL11.glPushMatrix();
			scale = 0.5F * 0.9F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, f, 0);
			ALRenderHelper.renderLightsaberHilt(itemstack);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			scale = 0.5F * 0.9F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, f, 0);
			GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
			scale = 1.5F * 0.9F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, -(LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
			ALRenderHelper.renderLightsaberBlade(itemstack, false);
			GL11.glPopMatrix();
            ALRenderHelper.endGlScissor();
            GL11.glPopMatrix();
            RenderHelper.disableStandardItemLighting();
        }
    }
}
