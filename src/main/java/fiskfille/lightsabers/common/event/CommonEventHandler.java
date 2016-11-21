package fiskfille.lightsabers.common.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import scala.util.Random;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.damagesource.ForceDamageSource;
import fiskfille.lightsabers.common.damagesource.LightsaberDamageSource;
import fiskfille.lightsabers.common.damagesource.ModDamageSources;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.ALEntityData;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.data.StatusEffect;
import fiskfille.lightsabers.common.entity.EntityForceLightning;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketBroadcastState;
import fiskfille.lightsabers.common.network.PacketRightClick;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;
import fiskfille.lightsabers.common.power.PowerManager;
import fiskfille.lightsabers.common.power.effect.PowerEffect;
import fiskfille.lightsabers.common.power.effect.PowerEffectActive;
import fiskfille.lightsabers.common.power.effect.PowerEffectChoke;
import fiskfille.lightsabers.common.power.effect.PowerEffectFortify;
import fiskfille.lightsabers.common.power.effect.PowerEffectMeditation;
import fiskfille.lightsabers.common.power.effect.PowerEffectResist;
import fiskfille.lightsabers.common.power.effect.PowerEffectStatus;

public class CommonEventHandler
{
	private List<EntityPlayer> playersToSync = new ArrayList<EntityPlayer>();

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
				playersToSync.add(player);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		ALEntityData data = ALEntityData.getData(entity);

		for (int i = 0; i < data.activeEffects.size(); ++i)
		{
			StatusEffect status = data.activeEffects.get(i);
			Effect effect = Effect.getEffect(status.id);
			Power power = effect.getPower(status.amplifier);
			boolean flag = false;

			if (power != null)
			{
				PowerEffect powerEffect = power.powerEffect;
				flag = powerEffect instanceof PowerEffectStatus && ((PowerEffectStatus)powerEffect).effect.id == status.id;
			}

			if (--status.duration <= 0 || flag)
			{
				if (effect == Effect.choke)
				{
					DataManager.addEffect(entity, Effect.stun.id, (int)(PowerEffectChoke.getStunDuration(status.amplifier) * 20), 0);
				}

				data.activeEffects.remove(i);
			}
		}

		IAttributeInstance speedAttribute = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (speedAttribute != null)
		{
			UUID stunModifierUUID = UUID.fromString("B2AB4DE3-8276-4B86-A448-230FA6FDC689");
			UUID speedModifierUUID = UUID.fromString("A1AB4DE3-8276-4B86-A448-480FA6DDB389");
			AttributeModifier stunModifier = (new AttributeModifier(stunModifierUUID, "Stun speed boost", -1, 1)).setSaved(false);
			AttributeModifier speedModifier = (new AttributeModifier(speedModifierUUID, "Force speed boost", 1, 1)).setSaved(false);

			if (speedAttribute.getModifier(stunModifierUUID) != null)
			{
				speedAttribute.removeModifier(stunModifier);
			}

			if (speedAttribute.getModifier(speedModifierUUID) != null)
			{
				speedAttribute.removeModifier(speedModifier);
			}

			if (DataManager.getEffect(entity, Effect.stun.id) != null)
			{
				speedAttribute.applyModifier(stunModifier);
				entity.isAirBorne = false;
				entity.motionY = -0.2F;
				entity.setJumping(false);
			}

			if (DataManager.getEffect(entity, Effect.speed.id) != null)
			{
				speedAttribute.applyModifier(speedModifier);
			}
		}

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			if (!player.worldObj.isRemote)
			{
				if (playersToSync.size() > 0 && playersToSync.contains(player))
				{
					DataManager.updatePlayerWithServerInfo(player);
					playersToSync.remove(player);
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
		event.entity.registerExtendedProperties(ALEntityData.IDENTIFIER, new ALEntityData());

		if (event.entity instanceof EntityPlayer)
		{
			event.entity.registerExtendedProperties(ALPlayerData.IDENTIFIER, new ALPlayerData());
		}
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		ALEntityData.getData(event.entityLiving).activeEffects.clear();

		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ALPlayerData data = ALPlayerData.getData(player);

			if (!player.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
			{
				ALData.set(player, ALData.FORCE_XP, (float)MathHelper.floor_float(ALData.getFloat(player, ALData.FORCE_XP) * 0.7F));
			}

			ALData.set(player, ALData.FORCE_POWER, 0.0F);
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event)
	{
		ALPlayerData.getData(event.entityPlayer).copy(ALPlayerData.getData(event.original));
	}

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;

		ALEntityData.getData(entity).onUpdate();

		if (entity instanceof EntityPlayer)
		{
			ALPlayerData.getData((EntityPlayer)entity).onUpdate();
		}
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		World world = player.worldObj;

		if (event.phase == Phase.END)
		{
			List<Power> list = ALPlayerData.getData(player).selectedPowers;
			Power[] apower = list.toArray(new Power[list.size()]);

			for (int i = 0; i < apower.length; ++i)
			{
				Power power = apower[i];

				if (power != null)
				{
					while (!ALHelper.getUnlockedChildrenPowers(player, power).isEmpty())
					{
						power = ALHelper.getUnlockedChildrenPowers(player, power).get(0);
					}

					while (power != null && !PowerManager.getPowerData(player, power).unlocked)
					{
						power = power.parent;
					}

					apower[i] = power;
				}
			}

			ALPlayerData.getData(player).selectedPowers = Arrays.asList(apower);

			if (ALData.getBoolean(player, ALData.USING_POWER))
			{
				ALData.incrWithoutNotify(player, ALData.TICKS_USING_POWER, 1);
			}
			else
			{
				ALData.setWithoutNotify(player, ALData.TICKS_USING_POWER, 0);
			}

			if (player.isEntityAlive())
			{
				if (ALHelper.getForcePowerMax(player) > 0)
				{
					if (ALData.getInt(player, ALData.USE_POWER_COOLDOWN) > 0)
					{
						ALData.incrWithoutNotify(player, ALData.USE_POWER_COOLDOWN, -1);
					}

					Power power = ALPlayerData.getData(player).selectedPowers.get(ALData.getInt(player, ALData.SELECTED_POWER));

					if (ALData.getBoolean(player, ALData.USING_POWER))
					{
						if (power != null)
						{
							PacketRightClick.onMessage(player, power, event.side);
						}
					}
					else if (ALData.getFloat(player, ALData.FORCE_POWER_DIFF) <= ALData.getFloat(player, ALData.FORCE_POWER))
					{
						ALData.incrWithoutNotify(player, ALData.FORCE_POWER, ALHelper.getForcePowerRegen(player) / 20);
					}

					ALData.capWithoutNotify(player, ALData.FORCE_POWER, 0.0F, (float)ALHelper.getForcePowerMax(player));
				}

				String s = ALData.getString(player, ALData.DRAINING_XP_TO);

				if (!StringUtils.isNullOrEmpty(s))
				{
					Power power = Power.getPowerFromName(s);

					if (power != null)
					{
						PowerData data = PowerManager.getPowerData(player, power);

						if (data != null && !data.unlocked)
						{
							if (data.xpInvested >= power.getActualXpCost(player))
							{
								data.unlocked = true;

								if (power == Power.forceSensitivity)
								{
									PowerManager.getPowerData(player, Power.lightSide).unlocked = true;
									PowerManager.getPowerData(player, Power.darkSide).unlocked = true;
									PowerManager.getPowerData(player, Power.neutral).unlocked = true;
								}

								if (Lightsabers.proxy.isClientPlayer(player))
								{
									player.playSound(ALSounds.block_holocron_unlock, 1.0F, 1.0F);
								}
							}
							else
							{
								int i = power.getActualXpCost(player) / 40;

								if (i > MathHelper.floor_float(ALData.getFloat(player, ALData.FORCE_XP)))
								{
									i = MathHelper.floor_float(ALData.getFloat(player, ALData.FORCE_XP));
								}

								if (i > power.getActualXpCost(player) - data.xpInvested)
								{
									i = power.getActualXpCost(player) - data.xpInvested;
								}

								data.xpInvested += i;
								ALData.incrWithoutNotify(player, ALData.FORCE_XP, -(float)i);

								if (Lightsabers.proxy.isClientPlayer(player))
								{
									Random rand = new Random();
									player.playSound(ALSounds.block_holocron_invest, 1.0F, 1.1F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
								}
							}
						}
					}
				}
			}

			if (ALData.getFloat(player, ALData.FORCE_POWER_DIFF) > ALData.getFloat(player, ALData.FORCE_POWER))
			{
				ALData.incrWithoutNotify(player, ALData.FORCE_POWER_DIFF, -7.5F);
				ALData.capWithoutNotify(player, ALData.FORCE_POWER_DIFF, 0.0F, ALData.getFloat(player, ALData.FORCE_POWER_DIFF));
			}
			else
			{
				ALData.setWithoutNotify(player, ALData.FORCE_POWER_DIFF, ALData.getFloat(player, ALData.FORCE_POWER));
			}

			if (ALData.getFloat(player, ALData.FORCE_PUSHING_TIMER) > 0)
			{
				ALData.incrWithoutNotify(player, ALData.FORCE_PUSHING_TIMER, -0.075F);
				ALData.capWithoutNotify(player, ALData.FORCE_PUSHING_TIMER, 0.0F, 1.0F);
			}
			else
			{
				ALData.setWithoutNotify(player, ALData.FORCE_PUSHING_TIMER, 0.0F);
			}

			StatusEffect effectLightning = DataManager.getEffect(player, Effect.lightning.id);

			if (ALData.getFloat(player, ALData.DRAIN_LIFE_TIMER) > 0 || effectLightning != null)
			{
				if (world.isRemote)
				{
					world.spawnEntityInWorld(new EntityForceLightning(world, player));
				}
			}

			if (ALData.getFloat(player, ALData.DRAIN_LIFE_TIMER) > 0)
			{
				float decr = 1F / 30;
				ALData.incrWithoutNotify(player, ALData.DRAIN_LIFE_TIMER, -decr);
				ALData.capWithoutNotify(player, ALData.DRAIN_LIFE_TIMER, 0.0F, 1.0F);

				List list1 = world.loadedEntityList;

				for (int i = 0; i < list1.size(); ++i)
				{
					if (list1.get(i) instanceof EntityLivingBase)
					{
						EntityLivingBase entity = (EntityLivingBase)list1.get(i);
						StatusEffect effect = DataManager.getEffect(entity, Effect.drain.id);

						if (effect != null && effect.casterUUID != null && effect.casterUUID.equals(player.getUniqueID()))
						{
							float f = decr * (4 + effect.amplifier * 2) * 8;
							float prevHealth = entity.getHealth();

							if (effect.duration % 8 == 1)
							{
								entity.hurtResistantTime = 0;
								entity.attackEntityFrom(ModDamageSources.causeForceLightningDamage(player), f);
								player.heal(Math.max(prevHealth - entity.getHealth(), 0));
							}

							entity.motionX = 0;
							entity.motionY = 0.05F;
							entity.motionZ = 0;
						}
					}
				}
			}
			else
			{
				ALData.setWithoutNotify(player, ALData.DRAIN_LIFE_TIMER, 0.0F);
			}

			if (!ALHelper.getEffectTargets(player, Effect.choke.id).isEmpty())
			{
				float decr = 1F / 60;
				List list1 = world.loadedEntityList;

				for (int i = 0; i < list1.size(); ++i)
				{
					if (list1.get(i) instanceof EntityLivingBase)
					{
						EntityLivingBase entity = (EntityLivingBase)list1.get(i);
						StatusEffect effect = DataManager.getEffect(entity, Effect.choke.id);

						if (effect != null && effect.casterUUID != null && effect.casterUUID.equals(player.getUniqueID()))
						{
							float f = decr * (2 + effect.amplifier * 2) * 8;

							if (effect.duration % 8 == 1)
							{
								entity.hurtResistantTime = 0;
								entity.attackEntityFrom(ModDamageSources.causeForceDamage(player), f);
							}

							entity.motionX = 0;
							entity.motionY = 0.001F * effect.duration;
							entity.motionZ = 0;
						}
					}
				}
			}

			boolean rightArm = effectLightning != null || !ALHelper.getEffectTargets(player, Effect.choke.id).isEmpty();
			boolean leftArm = effectLightning != null || !ALHelper.getEffectTargets(player, Effect.choke.id, 2).isEmpty();

			if (rightArm)
			{
				ALData.incrWithoutNotify(player, ALData.RIGHT_ARM_TIMER, 0.33F);
			}
			else
			{
				ALData.incrWithoutNotify(player, ALData.RIGHT_ARM_TIMER, -0.33F);
			}

			if (leftArm)
			{
				ALData.incrWithoutNotify(player, ALData.LEFT_ARM_TIMER, 0.33F);
			}
			else
			{
				ALData.incrWithoutNotify(player, ALData.LEFT_ARM_TIMER, -0.33F);
			}

			boolean flag = ALData.getBoolean(player, ALData.USING_POWER);
			boolean flag1 = ALData.getBoolean(player, ALData.PREV_USING_POWER);

			if (!ALPlayerData.getData(player).selectedPowers.isEmpty())
			{
				Power power = ALPlayerData.getData(player).selectedPowers.get(ALData.getInt(player, ALData.SELECTED_POWER));

				if (power != null)
				{
					if (power.powerEffect instanceof PowerEffectActive)
					{
						PowerEffectActive effect = (PowerEffectActive)power.powerEffect;

						if (flag && !flag1)
						{
							effect.start(player, event.side, power.powerEffectArgs);
						}

						if (!flag && flag1)
						{
							effect.stop(player, event.side, power.powerEffectArgs);
						}
					}
				}
			}

			if (ALData.getInt(player, ALData.PREV_XP) < ALHelper.getTotalXp(player))
			{
				float f = ALHelper.getTotalXp(player) - ALData.getInt(player, ALData.PREV_XP);
				ALData.incrWithoutNotify(player, ALData.FORCE_XP, f / 2);
			}

			ALData.setWithoutNotify(player, ALData.PREV_XP, ALHelper.getTotalXp(player));
			ALData.setWithoutNotify(player, ALData.PREV_USING_POWER, ALData.get(player, ALData.USING_POWER));
			ALData.capWithoutNotify(player, ALData.RIGHT_ARM_TIMER, 0.0F, 1.0F);
			ALData.capWithoutNotify(player, ALData.LEFT_ARM_TIMER, 0.0F, 1.0F);
		}
		else
		{
			ALData.setWithoutNotify(player, ALData.PREV_FORCE_POWER, ALData.get(player, ALData.FORCE_POWER));
			ALData.setWithoutNotify(player, ALData.PREV_FORCE_POWER_DIFF, ALData.get(player, ALData.FORCE_POWER_DIFF));
			ALData.setWithoutNotify(player, ALData.PREV_FORCE_PUSHING_TIMER, ALData.get(player, ALData.FORCE_PUSHING_TIMER));
			ALData.setWithoutNotify(player, ALData.PREV_DRAIN_LIFE_TIMER, ALData.get(player, ALData.DRAIN_LIFE_TIMER));
			ALData.setWithoutNotify(player, ALData.PREV_RIGHT_ARM_TIMER, ALData.get(player, ALData.RIGHT_ARM_TIMER));
			ALData.setWithoutNotify(player, ALData.PREV_LEFT_ARM_TIMER, ALData.get(player, ALData.LEFT_ARM_TIMER));
		}
	}

	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ALPlayerData data = ALPlayerData.getData(player);
		Entity entity = event.target;

		if (ALHelper.getForcePowerMax(player) > 0 && (player.getHeldItem() == null || player.getHeldItem().getItem() instanceof ItemLightsaberBase && !player.isSneaking()))
		{
			Power power = data.selectedPowers.get(ALData.getInt(player, ALData.SELECTED_POWER));

			if (power != null)
			{
				event.setCanceled(true);
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

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingAttack(LivingAttackEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		Entity damageSource = event.source.getSourceOfDamage();

		if (event.source.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase attacker = (EntityLivingBase)event.source.getEntity();

			if (DataManager.getEffect(attacker, Effect.stun.id) != null)
			{
				event.setCanceled(true);
				return;
			}
		}

		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ItemStack itemstack = player.getHeldItem();

			if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase && player.isUsingItem())
			{
				if (LightsaberHelper.isBlockable(damageSource))
				{
					event.entityLiving.worldObj.playSoundAtEntity(player, ALSounds.player_lightsaber_hit, 1.0F, 1.0F);
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		StatusEffect effectFortify = DataManager.getEffect(entity, Effect.fortify.id);
		StatusEffect effectEnergyResist = DataManager.getEffect(entity, Effect.resist.id);

		if (event.source.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity1 = (EntityLivingBase)event.source.getEntity();
			StatusEffect effectMeditation = DataManager.getEffect(entity1, Effect.meditation.id);

			if (!event.source.isProjectile() && !event.source.isFireDamage() && !event.source.isExplosion() && !event.source.isMagicDamage())
			{
				if (effectMeditation != null)
				{
					event.ammount *= PowerEffectMeditation.getModifierAmount(effectMeditation.amplifier);
				}
			}
		}

		if (effectFortify != null)
		{
			if (event.source instanceof ForceDamageSource)
			{
				event.ammount /= PowerEffectFortify.getModifierAmount(effectFortify.amplifier);
			}
		}

		if (effectEnergyResist != null)
		{
			if (event.source instanceof LightsaberDamageSource)
			{
				event.ammount /= PowerEffectResist.getModifierAmount(effectEnergyResist.amplifier);
			}
		}
	}
}
