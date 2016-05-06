package fiskfille.lightsabers.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.entity.EntitySithGhost;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketBroadcastState;

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
	public void onLivingAttack(LivingAttackEvent event) {
		Entity damageSource = event.source.getSourceOfDamage();
		
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemLightsaberBase && player.isSneaking() && player.isUsingItem()) {
				if (LightsaberHelper.isBlockable(damageSource)) {
					Random rand = new Random();
					String soundName = "lightsabers:saberhit" + (rand.nextInt(7) + 1);
					event.entityLiving.worldObj.playSoundAtEntity(player, soundName, 1.0F, 1.0F);
					event.setCanceled(true);
				}
			}
		}
	}
}
