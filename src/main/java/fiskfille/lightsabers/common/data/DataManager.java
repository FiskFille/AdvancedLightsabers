package fiskfille.lightsabers.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketPlayerJoin;

public class DataManager
{
	public static void updatePlayerWithServerInfo(EntityPlayer player)
	{
		if (!player.worldObj.isRemote)
        {
            ALNetworkManager.networkWrapper.sendTo(new PacketPlayerJoin(player), (EntityPlayerMP) player);
        }
	}
}
