package fiskfille.lightsabers.client.gui;

import java.awt.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.ALEntityData;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.data.StatusEffect;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.effect.PowerEffect;
import fiskfille.lightsabers.common.power.effect.PowerEffectActive;

public class GuiOverlay extends Gui
{
	private Minecraft mc = Minecraft.getMinecraft();
	private RenderItem itemRenderer = new RenderItem();
	public static final ResourceLocation icons = new ResourceLocation(Lightsabers.modid, "textures/gui/icons.png");
	public static final ResourceLocation widgets = new ResourceLocation(Lightsabers.modid, "textures/gui/widgets.png");

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
					Effect effect = Effect.getEffect(status.id);
					Power power = effect.getPower(status.amplifier);
					PowerEffect powerEffect = power.powerEffect;
					
					if (powerEffect instanceof PowerEffectActive)
					{
						((PowerEffectActive)powerEffect).render(player, event.partialTicks, power.powerEffectArgs);
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
		mc.getTextureManager().bindTexture(icons);
		int cap = ALHelper.getForcePowerMax(player);
        int left = width / 2 - 91;
        int top = height - 32 + 3;
        
        if (cap > 0)
        {
        	GL11.glEnable(GL11.GL_BLEND);
            short barWidth = 182;
            int filled = (int)((ALRenderHelper.median(ALData.getFloat(player, ALData.FORCE_POWER), ALData.getFloat(player, ALData.PREV_FORCE_POWER)) / cap) * barWidth);
            int filledDiff = (int)((ALRenderHelper.median(ALData.getFloat(player, ALData.FORCE_POWER_DIFF), ALData.getFloat(player, ALData.PREV_FORCE_POWER_DIFF)) / cap) * barWidth);
            drawTexturedModalRect(left, top, 0, 74, barWidth, 5);
            
            if (filledDiff > filled && filledDiff > 0)
        	{
        		drawTexturedModalRect(left, top, 0, 79, filledDiff, 5);
        	}
            
            if (filled > 0)
            {
                drawTexturedModalRect(left, top, 0, 84, filled, 5);
            }
            
            String s = MathHelper.floor_float(ALData.getFloat(player, ALData.FORCE_POWER)) + "/" + cap;
            
            boolean prevUnicodeFlag = mc.fontRenderer.getUnicodeFlag();
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
                GL11.glTranslatef(i == 0 ? 0.5F : (i == 1 ? -0.5F : 0), i == 2 ? 0.5F : (i == 3 ? -0.5F : 0), 0);
                mc.fontRenderer.drawString(s, 0, 0, 0x404040);
                GL11.glPopMatrix();
            }
            
            mc.fontRenderer.drawString(s, 0, 0, -1);
            mc.fontRenderer.setUnicodeFlag(prevUnicodeFlag);
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_BLEND);
        }
	}
	
	public void renderPowerSelector(RenderGameOverlayEvent.Post event, int width, int height, EntityPlayer player)
	{
		mc.getTextureManager().bindTexture(widgets);
        int left = width / 2 - 184;
        int top = height - 22;
        
        if (ALHelper.getForcePowerMax(player) > 0)
        {
        	ALPlayerData data = ALPlayerData.getData(player);
        	int selected = ALData.getInt(player, ALData.SELECTED_POWER);
        	
        	if (Lightsabers.isBattlegearLoaded)
        	{
        		mods.battlegear2.utils.BattlegearConfig config = new mods.battlegear2.utils.BattlegearConfig();
        		
        		if (new Rectangle(left + config.battleBarOffset[0], top + config.battleBarOffset[1], 62, 22).intersects(left, top, 62, 22))
        		{
        			left += config.battleBarOffset[0];
        			top += config.battleBarOffset[1] - 25;
        		}
        	}
        	
        	GL11.glEnable(GL11.GL_BLEND);
        	drawTexturedModalRect(left, top, 0, 0, 62, 22);
        	mc.getTextureManager().bindTexture(icons);
        	
        	for (int i = 0; i < data.selectedPowers.size(); ++i)
        	{
        		Power power = data.selectedPowers.get(i);
        		
        		if (power != null)
        		{
        			drawTexturedModalRect(left + 3 + i * 20, top + 3, power.iconX * 16, power.iconY * 16, 16, 16);
        		}
        	}
        	
        	mc.getTextureManager().bindTexture(widgets);
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
        	StatusEffect effect = data.activeEffects.get(i);
        	Effect e = Effect.getEffect(effect.id);
        	
        	if (effect.duration < 0)
        	{
        		continue;
        	}
        	
        	mc.getTextureManager().bindTexture(icons);
        	drawTexturedModalRect(left - 26, top - 26 - 28 * i, 0, 48, 26, 26);
        	
        	if (e != null)
        	{
        		Power power = e.getPower(effect.amplifier);
        		
        		if (power == null)
        		{
        			continue;
        		}
        		
        		drawTexturedModalRect(left - 21, top - 21 - 28 * i, power.iconX * 16, power.iconY * 16, 16, 16);
        		
        		String s = e.getTranslatedName();
            	String s1 = StringUtils.ticksToElapsedTime(effect.duration);
            	
            	if (effect.amplifier > 0 && effect.amplifier < 4)
            	{
            		s += " " + StatCollector.translateToLocal("enchantment.level." + (effect.amplifier + 1));
            	}
            	
            	drawString(mc.fontRenderer, s, left - 30 - mc.fontRenderer.getStringWidth(s), top - 22 - 28 * i, -1);
            	drawString(mc.fontRenderer, s1, left - 30 - mc.fontRenderer.getStringWidth(s1), top - 13 - 28 * i, -1);
        	}
        }
        
        mc.fontRenderer.setUnicodeFlag(prevUnicodeFlag);
        GL11.glDisable(GL11.GL_BLEND);
	}
}

