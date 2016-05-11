package fiskfille.lightsabers.common.data;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketPlayerJoin;
import fiskfille.lightsabers.common.network.PacketUpdatePowerList;
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
	
	public static void updatePlayerWithServerInfo(EntityPlayer player)
	{
		if (!player.worldObj.isRemote)
        {
            ALNetworkManager.networkWrapper.sendTo(new PacketPlayerJoin(player), (EntityPlayerMP) player);
        }
	}
}
