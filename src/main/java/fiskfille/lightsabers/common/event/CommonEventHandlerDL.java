package fiskfille.lightsabers.common.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import atomicstryker.dynamiclights.client.DynamicLights;
import atomicstryker.dynamiclights.client.IDynamicLightSource;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import fiskfille.lightsabers.common.config.ModConfig;
import fiskfille.lightsabers.common.entity.EntityLightsaber;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;

public class CommonEventHandlerDL
{
	private Minecraft mc;
	private long nextUpdate;
	private ArrayList<EntityLightAdapter> trackedEntities;
	private Thread thread;
	private boolean threadRunning;

	public void load()
	{
		mc = FMLClientHandler.instance().getClient();
		nextUpdate = System.currentTimeMillis();
		trackedEntities = new ArrayList<EntityLightAdapter>();
		threadRunning = false;
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent tick)
	{
		if (mc.theWorld != null && System.currentTimeMillis() > nextUpdate && !DynamicLights.globalLightsOff() && ModConfig.dynamicLightsEnabled)
		{
			nextUpdate = System.currentTimeMillis() + ModConfig.dynamicLightsUpdateInterval;

			if (!threadRunning)
			{
				thread = new EntityListChecker(mc.theWorld.loadedEntityList);
				thread.setPriority(Thread.MIN_PRIORITY);
				thread.start();
				threadRunning = true;
			}
		}
	}

	private int getEntityEquipmentMaxLight(Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			int light = getLightFromItemStack(((EntityLivingBase)entity).getEquipmentInSlot(0));
			
			for (int i = 1; i < entity.getLastActiveItems().length; i++)
			{
				light = DynamicLights.maxLight(light, getLightFromItemStack(entity.getLastActiveItems()[i]));
			}
			
			return light;
		}
		else if (entity instanceof EntityLightsaber)
		{
			return 15;
		}
		
		return 0;
	}

	private int getLightFromItemStack(ItemStack itemstack)
	{
		if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase && ItemLightsaberBase.isActive(itemstack))
		{
			return 15;
		}
		
		return 0;
	}

	private class EntityListChecker extends Thread
	{
		private final Object[] list;

		public EntityListChecker(List<Entity> input)
		{
			list = input.toArray();
		}

		@Override
		public void run()
		{
			ArrayList<EntityLightAdapter> newList = Lists.newArrayList();
			Entity entity;
			
			for (Object object : list)
			{
				entity = (Entity)object;
				
				if (entity.isEntityAlive() && getEntityEquipmentMaxLight(entity) > 0)
				{
					boolean found = false;
					Iterator<EntityLightAdapter> iter = trackedEntities.iterator();
					EntityLightAdapter adapter = null;
					
					while (iter.hasNext())
					{
						adapter = iter.next();
						
						if (adapter.getAttachmentEntity().equals(entity))
						{
							adapter.onTick();
							newList.add(adapter);
							found = true;
							iter.remove();
							break;
						}
					}

					if (!found)
					{
						adapter = new EntityLightAdapter(entity);
						adapter.onTick();
						newList.add(adapter);
					}
				}
			}
			
			for (EntityLightAdapter adapter : trackedEntities)
			{
				adapter.onTick();
			}

			trackedEntities = newList;
			threadRunning = false;
		}
	}

	private class EntityLightAdapter implements IDynamicLightSource
	{
		private Entity entity;
		private int lightLevel;
		private boolean enabled;

		public EntityLightAdapter(Entity e)
		{
			lightLevel = 0;
			enabled = false;
			entity = e;
		}

		public void onTick()
		{
			lightLevel = getEntityEquipmentMaxLight(entity);

			if (!enabled && lightLevel > 0)
			{
				enableLight();
			}
			else if (enabled && lightLevel < 1)
			{
				disableLight();
			}
		}

		private void enableLight()
		{
			DynamicLights.addLightSource(this);
			enabled = true;
		}

		private void disableLight()
		{
			DynamicLights.removeLightSource(this);
			enabled = false;
		}

		@Override
		public Entity getAttachmentEntity()
		{
			return entity;
		}

		@Override
		public int getLightLevel()
		{
			return lightLevel;
		}
	}
}
