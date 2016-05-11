package fiskfille.lightsabers.common.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketBroadcastState;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;
import fiskfille.lightsabers.common.power.PowerManager;
import fiskfille.lightsabers.common.proxy.CommonProxy;

public class CommonEventHandler
{
	private List<EntityPlayer> playersNotSunc = new ArrayList<EntityPlayer>();

	@SubscribeEvent
	public void onSpawn(EntityJoinWorldEvent event)
	{
		Entity entity = event.entity;
		World world = entity.worldObj;

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (!world.isRemote)
			{
				playersNotSunc.add(player);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			if (!player.worldObj.isRemote)
			{
				if (playersNotSunc.size() > 0 && playersNotSunc.contains(player))
				{
					DataManager.updatePlayerWithServerInfo(player);
					playersNotSunc.remove(player);
				}
			}
		}
	}

	@SubscribeEvent
	public void startTracking(StartTracking event)
	{
		EntityPlayer player = event.entityPlayer;

		if (player != null)
		{
			if (!player.worldObj.isRemote)
			{
				if (event.target instanceof EntityPlayer)
				{
					EntityPlayer beingTracked = (EntityPlayer) event.target;

					EntityPlayerMP playerMP = (EntityPlayerMP) player;
					EntityPlayerMP beingTrackedMP = (EntityPlayerMP) beingTracked;

					ALNetworkManager.networkWrapper.sendTo(new PacketBroadcastState(player), beingTrackedMP);
					ALNetworkManager.networkWrapper.sendTo(new PacketBroadcastState(beingTracked), playerMP);
				}
			}
		}
	}
	
	@SubscribeEvent
    public void onEntityConstruct(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer)
        {
        	event.entity.registerExtendedProperties(ALPlayerData.IDENTIFIER, new ALPlayerData());
        }
    }
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			
			DataManager.setPowers(player, new ArrayList<PowerData>()); // TODO: Remove
			
			if (!player.worldObj.isRemote)
			{
				NBTTagCompound playerData = new NBTTagCompound();
				ALPlayerData.getData(player).saveNBTData(playerData);
				CommonProxy.storeEntityData(player.getUniqueID().toString(), playerData);
				ALPlayerData.saveProxyData(player);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		World world = player.worldObj;
		
		if (event.phase == Phase.END)
		{
			String s = ALData.getString(player, ALData.DRAINING_XP_TO);
			
			if (!StringUtils.isNullOrEmpty(s))
			{
				Power power = Power.getPowerFromName(s);
				
				if (power != null)
				{
					PowerData data = PowerManager.getPowerData(player, power);

					if (data != null && !data.unlocked)
					{
						int i = 100;
						
						if (i > ALHelper.getTotalXp(player))
						{
							i = ALHelper.getTotalXp(player);
						}
						
						data.xpInvested += i;
						ALHelper.removeExperience(player, i);
						
						if (data.xpInvested >= power.powerStats.xpCost)
						{
							data.unlocked = true;
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingFall(LivingFallEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ItemStack itemstack = player.getHeldItem();

			if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event)
	{
		Entity damageSource = event.source.getSourceOfDamage();

		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ItemStack itemstack = player.getHeldItem();

			if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase && player.isUsingItem())
			{
				if (LightsaberHelper.isBlockable(damageSource))
				{
					event.entityLiving.worldObj.playSoundAtEntity(player, Lightsabers.modid + ":saberhit", 1.0F, 1.0F);
					event.setCanceled(true);
				}
			}
		}
	}
}
