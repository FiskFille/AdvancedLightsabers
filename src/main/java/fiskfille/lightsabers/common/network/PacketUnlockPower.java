package fiskfille.lightsabers.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerManager;

public class PacketUnlockPower implements IMessage
{
	public int id;
	private Power power;

	public PacketUnlockPower()
	{

	}

	public PacketUnlockPower(EntityPlayer player, Power p)
	{
		id = player.getEntityId();
		power = p;
	}

	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
		power = Power.getPowerFromName(ByteBufUtils.readUTF8String(buf));
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		ByteBufUtils.writeUTF8String(buf, power.getName());
	}

	public static class Handler implements IMessageHandler<PacketUnlockPower, IMessage>
	{
		public IMessage onMessage(PacketUnlockPower message, MessageContext ctx)
        {
        	Power power = message.power;
        	
            if (ctx.side.isClient())
            {
            	EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);
                
                if (entity instanceof EntityPlayer)
                {
                	EntityPlayer player1 = (EntityPlayer)entity;
                    onMessage(player1, message, ctx);
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
						
						if (player.worldObj.isRemote)
				        {
				            ALNetworkManager.networkWrapper.sendToServer(new PacketUnlockPower(player1, power));
				        }
				        else
				        {
				            ALNetworkManager.networkWrapper.sendToDimension(new PacketUnlockPower(player1, power), player.dimension);
				        }
						
						onMessage(player1, message, ctx);
					}
                }
            }

            return null;
        }
		
		public void onMessage(EntityPlayer player, PacketUnlockPower message, MessageContext ctx)
        {
			PowerManager.getPowerData(player, message.power).unlocked = true;
			
			if (message.power == Power.forceSensitivity)
			{
				PowerManager.getPowerData(player, Power.lightSide).unlocked = true;
				PowerManager.getPowerData(player, Power.darkSide).unlocked = true;
				PowerManager.getPowerData(player, Power.neutral).unlocked = true;
			}
        }
	}
}
