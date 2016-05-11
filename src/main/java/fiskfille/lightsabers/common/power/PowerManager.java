package fiskfille.lightsabers.common.power;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketUnlockPower;

public class PowerManager
{
	private final EntityPlayer player;

	public PowerManager(EntityPlayer e)
	{
		player = e;
	}

	public int getHierarchy(Power power)
	{
		if (hasPowerUnlocked(power))
		{
			return 0;
		}
		else
		{
			int i = 0;

			for (Power power1 = power.parent; power1 != null && !hasPowerUnlocked(power1); ++i)
			{
				power1 = power1.parent;
			}

			return i;
		}
	}

	public static void unlockPower(EntityPlayer player, Power power)
	{
		if (power != null && !hasPowerUnlocked(player, power) && (power.parent == null || hasPowerUnlocked(player, power.parent)) && ALHelper.getTotalXp(player) >= power.powerStats.xpCost)
		{
			if (player.worldObj.isRemote)
			{
				ALNetworkManager.networkWrapper.sendToServer(new PacketUnlockPower(player, power));
			}
			else
			{
				ALNetworkManager.networkWrapper.sendToAll(new PacketUnlockPower(player, power));
			}

			getPowerData(player, power).unlocked = true;
		}
	}

	public static boolean hasPowerUnlocked(EntityPlayer player, Power power)
	{
		List<PowerData> list = ALPlayerData.getData(player).powers;

		for (PowerData data : list)
		{
			if (data.power.getName().equals(power.getName()) && data.unlocked)
			{
				return true;
			}
		}

		return false;
	}

	public static boolean canUnlockPower(EntityPlayer player, Power power)
	{
		return power.parent == null || hasPowerUnlocked(player, power.parent);
	}
	
	public static PowerData getPowerData(EntityPlayer player, Power power)
	{
		List<PowerData> list = ALPlayerData.getData(player).powers;

		for (PowerData data : list)
		{
			if (data.power.getName().equals(power.getName()))
			{
				return data;
			}
		}

		return null;
	}

	public boolean hasPowerUnlocked(Power power)
	{
		List<PowerData> list = ALPlayerData.getData(player).powers;

		for (PowerData data : list)
		{
			if (data.power.getName().equals(power.getName()) && data.unlocked)
			{
				return true;
			}
		}

		return false;
	}

	public boolean canUnlockPower(Power power)
	{
		return power.parent == null || hasPowerUnlocked(player, power.parent);
	}
	
	public PowerData getPowerData(Power power)
	{
		List<PowerData> list = ALPlayerData.getData(player).powers;

		for (PowerData data : list)
		{
			if (data.power.getName().equals(power.getName()))
			{
				return data;
			}
		}

		return null;
	}
}
