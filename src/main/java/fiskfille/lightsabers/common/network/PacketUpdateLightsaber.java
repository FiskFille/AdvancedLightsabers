package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateLightsaber implements IMessage
{
	public int id;
	private ItemStack itemstack;

	public PacketUpdateLightsaber()
	{

	}

	public PacketUpdateLightsaber(Entity entity, ItemStack lightsaber)
	{
		id = entity.getEntityId();
		itemstack = lightsaber;
	}

	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
		itemstack = ByteBufUtils.readItemStack(buf);
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		ByteBufUtils.writeItemStack(buf, itemstack);
	}

	public static class Handler implements IMessageHandler<PacketUpdateLightsaber, IMessage>
	{
		public IMessage onMessage(PacketUpdateLightsaber message, MessageContext ctx)
		{
			if (ctx.side.isClient())
			{
				EntityPlayer player = Lightsabers.proxy.getPlayer();
				Entity entity = player.worldObj.getEntityByID(message.id);

				if (entity instanceof EntityPlayer)
				{
					if (entity != player)
					{
						EntityPlayer player1 = (EntityPlayer)entity;
						player1.inventory.mainInventory[10] = message.itemstack;
					}
				}
			}
			else
			{
				ALNetworkManager.networkWrapper.sendToAll(message);
			}
			
			return null;
		}
	}
}
