package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;
import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdatePowerList implements IMessage
{
	public int id;
	private List<PowerData> powers;

	public PacketUpdatePowerList()
	{

	}

	public PacketUpdatePowerList(EntityPlayer player, List<PowerData> list)
	{
		id = player.getEntityId();
		powers = list;
	}

	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
		powers = Lists.newArrayList();
		int i = buf.readInt();
    	
    	if (i > 0)
    	{
    		for (int j = 0; j < i; ++j)
    		{
    			PowerData data = new PowerData(Power.getPowerFromName(ByteBufUtils.readUTF8String(buf)));
    			data.unlocked = buf.readBoolean();
    			data.xpInvested = buf.readInt();
    			powers.add(data);
    		}
    	}
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeInt(powers.size());
		
		for (PowerData data : powers)
		{
			ByteBufUtils.writeUTF8String(buf, data.power.getName());
			buf.writeBoolean(data.unlocked);
			buf.writeInt(data.xpInvested);
		}
	}

	public static class Handler implements IMessageHandler<PacketUpdatePowerList, IMessage>
	{
		public IMessage onMessage(PacketUpdatePowerList message, MessageContext ctx)
        {
        	List<PowerData> list = message.powers;
        	
            if (ctx.side.isClient())
            {
            	EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);
                
                if (entity instanceof EntityPlayer)
                {
                	EntityPlayer player1 = (EntityPlayer)entity;
                	DataManager.setPowersWithoutNotify(player1, list);
                }
            }
            else
            {
            	EntityPlayer player = ctx.getServerHandler().playerEntity;
                
                if (player != null)
				{
					Entity entity = player.worldObj.getEntityByID(message.id);

					if (entity instanceof EntityPlayer)
					{
						EntityPlayer player1 = (EntityPlayer)entity;
						DataManager.setPowers(player1, list);
					}
                }
            }

            return null;
        }
	}
}
