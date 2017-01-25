package fiskfille.lightsabers.common.data;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketPlayerJoin;
import fiskfille.lightsabers.common.network.PacketUpdateEffectsList;
import fiskfille.lightsabers.common.network.PacketUpdatePowerList;
import fiskfille.lightsabers.common.network.PacketUpdateSelectedPowers;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;

public class DataManager
{
    public static void setPowersWithoutNotify(EntityPlayer player, List<PowerData> list)
    {
        ALPlayerData.getData(player).powers = list;
    }

    public static void setPowers(EntityPlayer player, List<PowerData> list)
    {
//		if (!list.containsAll(ALPlayerData.getData(player).unlockedPowers))
        {
            if (player.worldObj.isRemote)
            {
                ALNetworkManager.networkWrapper.sendToServer(new PacketUpdatePowerList(player, list));
            }
            else
            {
                ALNetworkManager.networkWrapper.sendToAll(new PacketUpdatePowerList(player, list));
            }

            setPowersWithoutNotify(player, list);
        }
    }

    public static void setSelectedPowersWithoutNotify(EntityPlayer player, List<Power> list)
    {
        ALPlayerData.getData(player).selectedPowers = list;
    }

    public static void setSelectedPowers(EntityPlayer player, List<Power> list)
    {
//		if (!list.containsAll(ALPlayerData.getData(player).unlockedPowers))
        {
            if (player.worldObj.isRemote)
            {
                ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateSelectedPowers(player, list));
            }
            else
            {
                ALNetworkManager.networkWrapper.sendToAll(new PacketUpdateSelectedPowers(player, list));
            }

            setSelectedPowersWithoutNotify(player, list);
        }
    }

    public static void setActiveEffectsWithoutNotify(EntityLivingBase entity, List<StatusEffect> list)
    {
        ALEntityData.getData(entity).activeEffects = list;
    }

    public static void setActiveEffects(EntityLivingBase entity, List<StatusEffect> list)
    {
        if (entity.worldObj.isRemote)
        {
            ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateEffectsList(entity, list));
        }
        else
        {
            ALNetworkManager.networkWrapper.sendToAll(new PacketUpdateEffectsList(entity, list));
        }

        setActiveEffectsWithoutNotify(entity, list);
    }

    public static void addEffect(EntityLivingBase entity, int id, int duration, int amplifier)
    {
        List<StatusEffect> list = ALEntityData.getData(entity).activeEffects;

        for (StatusEffect effect : list)
        {
            if (effect.id == id)
            {
                effect.duration = duration;
                effect.amplifier = amplifier;
                return;
            }
        }

        list.add(new StatusEffect(id, duration, amplifier));
    }

    public static void addEffect(EntityLivingBase entity, EntityLivingBase caster, int id, int duration, int amplifier)
    {
        List<StatusEffect> list = ALEntityData.getData(entity).activeEffects;

        for (StatusEffect effect : list)
        {
            if (effect.id == id)
            {
                effect.duration = duration;
                effect.amplifier = amplifier;
                effect.casterUUID = caster.getUniqueID();
                return;
            }
        }

        list.add(new StatusEffect(id, duration, amplifier, caster));
    }

    public static StatusEffect getEffect(EntityLivingBase entity, int id)
    {
        for (StatusEffect effect : ALEntityData.getData(entity).activeEffects)
        {
            if (effect.id == id)
            {
                return effect;
            }
        }

        return null;
    }

    public static void updatePlayerWithServerInfo(EntityPlayer player)
    {
        if (!player.worldObj.isRemote)
        {
            ALNetworkManager.networkWrapper.sendTo(new PacketPlayerJoin(player), (EntityPlayerMP) player);
        }
    }
}
