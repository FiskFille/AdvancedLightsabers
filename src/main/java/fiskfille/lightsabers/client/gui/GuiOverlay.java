package fiskfille.lightsabers.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fiskfille.lightsabers.Lightsabers;

public class GuiOverlay extends Gui
{
	private Minecraft mc = Minecraft.getMinecraft();
	private RenderItem itemRenderer = new RenderItem();
	public static final ResourceLocation texture = new ResourceLocation(Lightsabers.modid, "textures/gui/mod_icons.png");

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event)
	{
		
	}
	
	public void renderForceBar(RenderGameOverlayEvent.Pre event, int width, int height, EntityPlayer player)
	{
		
	}
}
