package fiskfille.lightsabers.common.network;

import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.StatusEffect;

public class PacketUpdateEffectsList implements IMessage
{
	public int id;
	private List<StatusEffect> activeEffects;

	public PacketUpdateEffectsList()
	{

	}

	public PacketUpdateEffectsList(EntityLivingBase entity, List<StatusEffect> list)
	{
		id = entity.getEntityId();
		activeEffects = list;
	}

	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
		activeEffects = Lists.newArrayList();
		int i = buf.readInt();
    	
    	if (i > 0)
    	{
    		for (int j = 0; j < i; ++j)
    		{
    			StatusEffect effect = new StatusEffect(buf.readInt(), buf.readInt(), buf.readInt());
    			
    			if (buf.readBoolean())
    			{
    				UUID uuid = new UUID(buf.readLong(), buf.readLong());
    				effect.casterUUID = uuid;
    			}
    			
    			activeEffects.add(effect);
    		}
    	}
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeInt(activeEffects.size());
		
		for (StatusEffect effect : activeEffects)
		{
			buf.writeInt(effect.id);
			buf.writeInt(effect.duration);
			buf.writeInt(effect.amplifier);
			
			if (effect.casterUUID != null)
			{
				buf.writeBoolean(true);
				buf.writeLong(effect.casterUUID.getMostSignificantBits());
				buf.writeLong(effect.casterUUID.getLeastSignificantBits());
			}
			else
			{
				buf.writeBoolean(false);
			}
		}
	}

	public static class Handler implements IMessageHandler<PacketUpdateEffectsList, IMessage>
	{
		public IMessage onMessage(PacketUpdateEffectsList message, MessageContext ctx)
        {
        	List<StatusEffect> list = message.activeEffects;
        	
            if (ctx.side.isClient())
            {
            	EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);
                
                if (entity instanceof EntityLivingBase)
                {
                	EntityLivingBase entity1 = (EntityLivingBase)entity;
                	DataManager.setActiveEffectsWithoutNotify(entity1, list);
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
						DataManager.setActiveEffects(entity1, list);
					}
                }
            }

            return null;
        }
	}
}
