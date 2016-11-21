package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketIgniteLightsaber implements IMessage
{
	public int id;
	private boolean active;

	public PacketIgniteLightsaber()
	{

	}

	public PacketIgniteLightsaber(EntityLivingBase entity, boolean b)
	{
		id = entity.getEntityId();
		active = b;
	}

	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
		active = buf.readBoolean();
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeBoolean(active);
	}

	public static class Handler implements IMessageHandler<PacketIgniteLightsaber, IMessage>
	{
		public IMessage onMessage(PacketIgniteLightsaber message, MessageContext ctx)
		{
			if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);

                if (entity instanceof EntityLivingBase)
                {
                    LightsaberHelper.igniteLightsaberWithoutNotify((EntityLivingBase) entity, message.active);
                }
            }
            else
            {
                EntityPlayer player = ctx.getServerHandler().playerEntity;

                if (player != null)
                {
                    Entity entity = player.worldObj.getEntityByID(message.id);

                    if (entity instanceof EntityLivingBase)
                    {
                    	LightsaberHelper.igniteLightsaber((EntityLivingBase) entity, message.active);
                    }
                }
            }
			
			return null;
		}
	}
}
