package fiskfille.lightsabers.common.event;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.sound.MovingSoundHum;
import fiskfille.lightsabers.common.config.ModConfig;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketUpdateLightsaber;

public class ClientEventHandler
{
	private Minecraft mc = Minecraft.getMinecraft();
	public static float RENDER_TICK;
	
	private ItemStack prevLightsaber;
	
	private Map<String, ItemStack> prevLightsaber1 = Maps.newHashMap();
	private Map<String, Boolean> hasPlayedSound = Maps.newHashMap();
	
	@SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equals(Lightsabers.modid))
        {
        	ModConfig.load(ModConfig.configFile);
            ModConfig.configFile.save();
        }
    }
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		ItemStack itemstack = entity.getHeldItem();
		
//		if (prevLightsaber1.containsKey(entity.getUniqueID().toString()))
//		{
//			ItemStack itemstack1 = prevLightsaber1.get(entity.getUniqueID().toString());
//			
////			if (entity.ticksExisted % 2 == 0)
//			{
//				if (itemstack != null && itemstack1 != null && itemstack.getItem() instanceof ItemLightsaberBase && itemstack1.getItem() instanceof ItemLightsaberBase && ItemLightsaberBase.isActive(itemstack) != ItemLightsaberBase.isActive(itemstack1) || itemstack != null && itemstack1 == null && itemstack.getItem() instanceof ItemLightsaberBase || itemstack == null && itemstack1 != null && itemstack1.getItem() instanceof ItemLightsaberBase)
//				{
//					onChangeState(entity, itemstack, itemstack1, ItemLightsaberBase.isActive(itemstack));
//					prevLightsaber1.put(entity.getUniqueID().toString(), itemstack);
//				}
//			}
//		}
//		
//		prevLightsaber1.put(entity.getUniqueID().toString(), itemstack);
		
//		if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase && ItemLightsaberBase.isActive(itemstack))
//		{
//			if (!ASMHooksClient.hasHumSound(entity))
//			{
//				mc.getSoundHandler().playSound(new MovingSoundHum(entity, "heavy"));
//				ASMHooksClient.humSounds.add(entity.getUniqueID().toString());
//			}
//		}
	}
	
	private void onChangeState(EntityLivingBase entity, ItemStack curr, ItemStack prev, boolean state)
	{
		World world = entity.worldObj;
		
		if (!world.isRemote)
		{
			if (state)
			{
				mc.getSoundHandler().playSound(new MovingSoundHum(entity, "medium"));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerUpdate(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		World world = player.worldObj;
		
		if (player == mc.thePlayer)
		{
			if (event.phase == TickEvent.Phase.END)
			{
				ItemStack itemstack = prevLightsaber;
				ItemStack itemstack1 = LightsaberHelper.getEquippedLightsaber(player);

				if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
				{
					ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateLightsaber(event.player, itemstack1));
					prevLightsaber = itemstack1 == null ? null : itemstack1.copy();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack itemstack = LightsaberHelper.getEquippedLightsaber(player);
		
		if (itemstack != null && !(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.lightsaber))
		{
			float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
			float f = -(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height / 2) * 0.0625F;
			
			GL11.glPushMatrix();
			event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glTranslatef(0.2F, -0.55F, 0.15F);
			GL11.glRotatef(15, 0, 0, 1);
			GL11.glRotatef(10, 1, 0, 0);
			
			float scale = 0.15F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, f, 0);
			ALRenderHelper.renderLightsaberHilt(itemstack);
			GL11.glPopMatrix();
		}
	}
	
	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event)
	{
		RENDER_TICK = event.renderTickTime;
	}
}
