package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
        	boolean active = message.active;
        	
            if (ctx.side.isClient())
            {
            	EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);
                
                if (entity instanceof EntityLivingBase)
                {
                	EntityLivingBase entity1 = (EntityLivingBase)entity;
                    onMessage(entity1, message, ctx);
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
						EntityLivingBase entity1 = (EntityLivingBase)entity;
						
						if (player.worldObj.isRemote)
				        {
				            ALNetworkManager.networkWrapper.sendToServer(new PacketIgniteLightsaber(entity1, active));
				        }
				        else
				        {
				            ALNetworkManager.networkWrapper.sendToDimension(new PacketIgniteLightsaber(entity1, active), player.dimension);
				        }
						
						onMessage(entity1, message, ctx);
					}
                }
            }

            return null;
        }
		
		public void onMessage(EntityLivingBase entity, PacketIgniteLightsaber message, MessageContext ctx)
        {
        	World world = entity.worldObj;
        	ItemStack itemstack = entity.getHeldItem();
    		
    		if (itemstack != null)
    		{
    			ItemLightsaberBase.refreshNBT(itemstack);
    			itemstack.getTagCompound().setBoolean("active", message.active);
    			entity.playSound(Lightsabers.modid + ":lightsaber_" + (message.active ? "on" : "off"), 1.0F, 1.0F);
    		}
        }
	}
}
