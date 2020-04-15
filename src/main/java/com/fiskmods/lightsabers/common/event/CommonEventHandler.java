package com.fiskmods.lightsabers.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.container.ContainerCrystalPouch;
import com.fiskmods.lightsabers.common.container.InventoryCrystalPouch;
import com.fiskmods.lightsabers.common.damage.ALDamageSources;
import com.fiskmods.lightsabers.common.damage.ALDamageTypes;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALEntityData;
import com.fiskmods.lightsabers.common.data.ALPlayerData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.entity.EntityForceLightning;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerData;
import com.fiskmods.lightsabers.common.force.PowerManager;
import com.fiskmods.lightsabers.common.force.PowerType;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectActive;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectChoke;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectFortify;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectMeditation;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectResist;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.network.MessageBroadcastState;
import com.fiskmods.lightsabers.common.network.MessagePlayerJoin;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Iterables;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import fiskfille.utils.helper.FiskServerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
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
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;

public class CommonEventHandler
{
    private List<EntityPlayer> playersToSync = new ArrayList<EntityPlayer>();

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
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
    public void onEntityConstruct(EntityConstructing event)
    {
        event.entity.registerExtendedProperties(ALEntityData.IDENTIFIER, new ALEntityData());

        if (event.entity instanceof EntityPlayer)
        {
            event.entity.registerExtendedProperties(ALPlayerData.IDENTIFIER, new ALPlayerData());
        }
    }

    @SubscribeEvent
    public void onStartTracking(StartTracking event)
    {
        EntityPlayer player = event.entityPlayer;

        if (player != null && !player.worldObj.isRemote)
        {
            if (event.target instanceof EntityPlayer)
            {
                EntityPlayerMP tracked = (EntityPlayerMP) event.target;
                ALNetworkManager.wrapper.sendTo(new MessageBroadcastState(player), tracked);
                ALNetworkManager.wrapper.sendTo(new MessageBroadcastState(tracked), (EntityPlayerMP) player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        ALPlayerData.getData(event.entityPlayer).copy(ALPlayerData.getData(event.original));
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {
        EntityPlayer player = event.player;
        World world = player.worldObj;

        if (event.phase == Phase.END)
        {
            List<Power> selectedPowers = ALData.SELECTED_POWERS.get(player);
            
            for (int i = 0; i < selectedPowers.size(); ++i)
            {
                Power power = selectedPowers.get(i);
                
                if (power != null)
                {
                    Set<Power> set;
                    
                    while (!(set = ALHelper.getUnlockedChildren(player, power)).isEmpty())
                    {
                        power = Iterables.get(set, 0);
                    }

                    while (power != null && !PowerManager.hasPowerUnlocked(player, power))
                    {
                        power = power.parent;
                    }

                    selectedPowers.set(i, power);
                }
            }

            if (ALData.USING_POWER.get(player))
            {
                ALData.TICKS_USING_POWER.incrWithoutNotify(player, 1);
            }
            else
            {
                ALData.TICKS_USING_POWER.setWithoutNotify(player, 0);
            }

            Power power = PowerManager.getSelectedPower(player);

            if (player.isEntityAlive())
            {
                int max = ALHelper.getForcePowerMax(player);
                
                if (max > 0)
                {
                    if (ALData.USE_POWER_COOLDOWN.get(player) > 0)
                    {
                        ALData.USE_POWER_COOLDOWN.incrWithoutNotify(player, -1);
                    }

                    if (power != null && ALData.USING_POWER.get(player))
                    {
                        if (power.powerStats.powerType == PowerType.PER_SECOND && ALData.FORCE_POWER.get(player) >= power.getUseCost(player) && power.powerEffect.execute(player, event.side))
                        {
                            ALData.FORCE_POWER.incrWithoutNotify(player, -power.getUseCost(player) / 20);
                        }
                    }
                    else if (ALData.FORCE_POWER_DIFF.get(player) <= ALData.FORCE_POWER.get(player))
                    {
                        ALData.FORCE_POWER.incrWithoutNotify(player, ALHelper.getForcePowerRegen(player) / 20);
                    }

                    ALData.FORCE_POWER.clampWithoutNotify(player, 0.0F, (float) max);
                }

                String drainingTo = ALData.DRAINING_XP_TO.get(player);

                if (!StringUtils.isNullOrEmpty(drainingTo))
                {
                    Power power1 = Power.getPowerFromName(drainingTo);

                    if (power1 != null)
                    {
                        PowerData data = PowerManager.getPowerData(player, power1);

                        if (data != null && !data.isUnlocked())
                        {
                            if (data.xpInvested >= power1.getActualXpCost(player))
                            {
                                data.setUnlocked(player, true);
                                ALData.BASE_POWER.incrWithoutNotify(player, (byte) (power1.powerStats.baseBonus - power1.powerStats.baseRequirement));

                                if (Lightsabers.proxy.isClientPlayer(player))
                                {
                                    player.playSound(ALSounds.block_holocron_unlock, 1.0F, 1.0F);
                                }
                            }
                            else
                            {
                                int i = power1.getActualXpCost(player) / 40;

                                if (i > MathHelper.floor_float(ALData.FORCE_XP.get(player)))
                                {
                                    i = MathHelper.floor_float(ALData.FORCE_XP.get(player));
                                }

                                if (i > power1.getActualXpCost(player) - data.xpInvested)
                                {
                                    i = power1.getActualXpCost(player) - data.xpInvested;
                                }

                                data.xpInvested += i;
                                ALData.FORCE_XP.incrWithoutNotify(player, -(float) i);

                                if (Lightsabers.proxy.isClientPlayer(player))
                                {
                                    Random rand = new Random();
                                    player.playSound(ALSounds.block_holocron_invest, 1, 1.1F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                                }
                            }
                        }
                    }
                }
            }

            if (ALData.FORCE_POWER_DIFF.get(player) > ALData.FORCE_POWER.get(player))
            {
                ALData.FORCE_POWER_DIFF.incrWithoutNotify(player, -7.5F);
                ALData.FORCE_POWER_DIFF.clampWithoutNotify(player, 0.0F, ALData.FORCE_POWER_DIFF.get(player));
            }
            else
            {
                ALData.FORCE_POWER_DIFF.setWithoutNotify(player, ALData.FORCE_POWER.get(player));
            }

            if (ALData.FORCE_PUSHING_TIMER.get(player) > 0)
            {
                ALData.FORCE_PUSHING_TIMER.incrWithoutNotify(player, -0.075F);
                ALData.FORCE_PUSHING_TIMER.clampWithoutNotify(player, 0.0F, 1.0F);
            }
            else
            {
                ALData.FORCE_PUSHING_TIMER.setWithoutNotify(player, 0.0F);
            }

            StatusEffect effectLightning = StatusEffect.get(player, Effect.LIGHTNING);

            if (ALData.DRAIN_LIFE_TIMER.get(player) > 0 || effectLightning != null)
            {
                if (world.isRemote)
                {
                    world.spawnEntityInWorld(new EntityForceLightning(world, player));
                }
            }

            if (ALData.DRAIN_LIFE_TIMER.get(player) > 0)
            {
                float decr = 1F / 30;
                ALData.DRAIN_LIFE_TIMER.incrWithoutNotify(player, -decr);
                ALData.DRAIN_LIFE_TIMER.clampWithoutNotify(player, 0.0F, 1.0F);

                List list1 = world.loadedEntityList;

                for (int i = 0; i < list1.size(); ++i)
                {
                    if (list1.get(i) instanceof EntityLivingBase)
                    {
                        EntityLivingBase entity = (EntityLivingBase) list1.get(i);
                        StatusEffect effect = StatusEffect.get(entity, Effect.DRAIN);

                        if (effect != null && effect.casterUUID != null && effect.casterUUID.equals(player.getUniqueID()))
                        {
                            float f = decr * (4 + effect.amplifier * 2) * 5;
                            float prevHealth = entity.getHealth();

                            if (effect.duration % 5 == 0)
                            {
                                entity.hurtResistantTime = 0;
                                entity.attackEntityFrom(ALDamageSources.causeForceLightningDamage(player), f);
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
                ALData.DRAIN_LIFE_TIMER.setWithoutNotify(player, 0.0F);
            }

            if (!StatusEffect.getTargets(player, Effect.CHOKE).isEmpty())
            {
                float decr = 1F / 60;
                List list1 = world.loadedEntityList;

                for (int i = 0; i < list1.size(); ++i)
                {
                    if (list1.get(i) instanceof EntityLivingBase)
                    {
                        EntityLivingBase entity = (EntityLivingBase) list1.get(i);
                        StatusEffect effect = StatusEffect.get(entity, Effect.CHOKE);

                        if (effect != null && effect.casterUUID != null && effect.casterUUID.equals(player.getUniqueID()))
                        {
                            float f = decr * (2 + effect.amplifier * 2) * 5;

                            if (effect.duration % 5 == 0)
                            {
                                entity.hurtResistantTime = 0;
                                entity.attackEntityFrom(ALDamageSources.causeForceDamage(player), f);
                            }

                            entity.motionX = 0;
                            entity.motionY = 0.001F * effect.duration;
                            entity.motionZ = 0;
                        }
                    }
                }
            }

            boolean rightArm = effectLightning != null || !StatusEffect.getTargets(player, Effect.CHOKE).isEmpty();
            boolean leftArm = effectLightning != null || !StatusEffect.getTargets(player, Effect.CHOKE, 2).isEmpty();

            if (rightArm)
            {
                ALData.RIGHT_ARM_TIMER.incrWithoutNotify(player, 0.33F);
            }
            else
            {
                ALData.RIGHT_ARM_TIMER.incrWithoutNotify(player, -0.33F);
            }

            if (leftArm)
            {
                ALData.LEFT_ARM_TIMER.incrWithoutNotify(player, 0.33F);
            }
            else
            {
                ALData.LEFT_ARM_TIMER.incrWithoutNotify(player, -0.33F);
            }

            boolean flag = ALData.USING_POWER.get(player);
            boolean flag1 = ALData.PREV_USING_POWER.get(player);

            if (power != null)
            {
                if (power.powerEffect instanceof PowerEffectActive)
                {
                    PowerEffectActive effect = (PowerEffectActive) power.powerEffect;

                    if (flag && !flag1)
                    {
                        effect.start(player, event.side);
                    }

                    if (!flag && flag1)
                    {
                        effect.stop(player, event.side);
                    }
                }
            }

            if (ALData.PREV_XP.get(player) < ALHelper.getTotalXp(player))
            {
                float f = ALHelper.getTotalXp(player) - ALData.PREV_XP.get(player);
                ALData.FORCE_XP.incrWithoutNotify(player, f / 2);
            }

            if (ALData.BASE_POWER.get(player) < 0)
            {
                ALData.BASE_POWER.setWithoutNotify(player, (byte) Math.max(ALHelper.getBasePower(player), 0));
            }

            if (PowerManager.hasPowerUnlocked(player, Power.FORCE_SENSITIVITY))
            {
                PowerManager.getPowerData(player, Power.LIGHT_SIDE).setUnlocked(player, true);
                PowerManager.getPowerData(player, Power.DARK_SIDE).setUnlocked(player, true);
                PowerManager.getPowerData(player, Power.NEUTRAL).setUnlocked(player, true);
            }

            ALData.FORCE_POWER.clampWithoutNotify(player, 0.0F, (float) ALHelper.getForcePowerMax(player));
            ALData.FORCE_POWER_DIFF.clampWithoutNotify(player, 0.0F, (float) ALHelper.getForcePowerMax(player));

            ALData.PREV_XP.setWithoutNotify(player, ALHelper.getTotalXp(player));
            ALData.PREV_USING_POWER.setWithoutNotify(player, ALData.USING_POWER.get(player));
            ALData.RIGHT_ARM_TIMER.clampWithoutNotify(player, 0.0F, 1.0F);
            ALData.LEFT_ARM_TIMER.clampWithoutNotify(player, 0.0F, 1.0F);
        }
        else
        {
            ALData.onUpdate(player);
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.entityLiving;
        ALEntityData data = ALEntityData.getData(entity);
        
        if (data.forcePushed && entity.isCollided && !entity.onGround)
        {
            data.forcePushed = false;
            entity.hurtResistantTime = 0;
            
            double motX = entity.prevPosX - entity.posX;
            double motY = entity.prevPosY - entity.posY;
            double motZ = entity.prevPosZ - entity.posZ;
            double vel = MathHelper.sqrt_double(motX * motX + motY * motY + motZ * motZ);
            
            float damage = (float) Math.max(vel * 5 - 3, 0);
            
            if (damage > 0)
            {
                entity.attackEntityFrom(ALDamageSources.INTO_WALL, damage);
            }
        }
        
        for (int i = 0; i < data.activeEffects.size(); ++i)
        {
            StatusEffect status = data.activeEffects.get(i);

            if (--status.duration <= 0)
            {
                data.activeEffects.remove(i);

                if (status.effect == Effect.CHOKE)
                {
                    StatusEffect.add(entity, Effect.STUN, (int) (PowerEffectChoke.getStunDuration(status.amplifier) * 20), 0);
                }
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

            if (StatusEffect.get(entity, Effect.STUN) != null)
            {
                speedAttribute.applyModifier(stunModifier);
                entity.isAirBorne = false;
                entity.motionY = -0.2F;
                entity.setJumping(false);
            }

            if (StatusEffect.get(entity, Effect.SPEED) != null)
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
                    ALNetworkManager.wrapper.sendTo(new MessagePlayerJoin(player), (EntityPlayerMP) player);
                    playersToSync.remove(player);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingFall(LivingFallEvent event)
    {
        if (event.entity instanceof EntityPlayer && event.distance > 3)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            float force = ALData.FORCE_POWER.get(player);
            
            if (force > 0 && PowerManager.hasPowerUnlocked(player, Power.REBOUND))
            {
                float cost = Power.REBOUND.getUseCost(player);
                float amount = Math.min(Math.max(event.distance - 3, 0), MathHelper.floor_float(force / cost));
                
                if (amount > 0)
                {
                    event.distance -= amount;
                    
                    if (!event.entity.worldObj.isRemote)
                    {
                        ALData.FORCE_POWER.incr(player, -cost * amount);
                    }
                    
                    amount -= 3;
                    player.playSound(ALSounds.player_force_cast, Math.min(amount / 10 + 0.2F, 1), 1);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingAttack(LivingAttackEvent event)
    {
        if (event.source.getEntity() instanceof EntityLivingBase)
        {
            EntityLivingBase attacker = (EntityLivingBase) event.source.getEntity();

            if (StatusEffect.has(attacker, Effect.STUN))
            {
                event.setCanceled(true);
                return;
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.entityLiving;
        StatusEffect effectFortify = StatusEffect.get(entity, Effect.FORTIFY);
        StatusEffect effectEnergyResist = StatusEffect.get(entity, Effect.RESIST);

        if (event.source.getEntity() instanceof EntityLivingBase)
        {
            EntityLivingBase attacker = (EntityLivingBase) event.source.getEntity();
            StatusEffect effectMeditation = StatusEffect.get(attacker, Effect.MEDITATION);

            if (effectMeditation != null && FiskServerUtils.isMeleeDamage(event.source))
            {
                event.ammount *= PowerEffectMeditation.getModifierAmount(effectMeditation.amplifier);
            }
        }

        if (effectFortify != null && ALDamageTypes.FORCE.isPresent(event.source))
        {
            event.ammount /= PowerEffectFortify.getModifierAmount(effectFortify.amplifier);
        }

        if (effectEnergyResist != null && ALDamageTypes.LIGHTSABER.isPresent(event.source))
        {
            event.ammount /= PowerEffectResist.getModifierAmount(effectEnergyResist.amplifier);
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        ALEntityData.getData(event.entityLiving).activeEffects.clear();

        if (event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;

            if (!player.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
            {
                ALData.FORCE_XP.set(player, (float) MathHelper.floor_float(ALData.FORCE_XP.get(player) * 0.7F));
            }
            else
            {
                ALData.FORCE_XP.set(player, 0.0F);
            }

            ALData.onDeath(player);
        }
    }
    
    @SubscribeEvent
    public void onEntityItemPickup(EntityItemPickupEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        ItemStack item = event.item.getEntityItem();
        
        if (item != null && item.getItem() == Item.getItemFromBlock(ModBlocks.lightsaberCrystal))
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                
                if (itemstack != null && itemstack.getItem() == ModItems.crystalPouch)
                {
                    InventoryCrystalPouch pouch = new InventoryCrystalPouch(player, i);
                    
                    if (player.openContainer instanceof ContainerCrystalPouch)
                    {
                        InventoryCrystalPouch inventory = ((ContainerCrystalPouch) player.openContainer).inventory;
                        
                        if (inventory.itemSlot == i)
                        {
                            pouch = inventory;
                        }
                    }
                    
                    pouch.addItemStackToInventory(item);
                    pouch.markDirty();
                    
                    if (item.stackSize <= 0)
                    {
                        break;
                    }
                }
            }
        }
    }
    
//    @SubscribeEvent
//    public void onInitMapGen(InitMapGenEvent event)
//    {
//        if (event.type == EventType.CAVE)
//        {
//            event.newGen = new MapGenCrystalCave(event.originalGen);
//        }
//    }
}
