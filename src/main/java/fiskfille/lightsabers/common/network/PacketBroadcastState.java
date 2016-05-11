package fiskfille.lightsabers.common.network;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;

public class PacketBroadcastState extends PacketSyncBase
{
    private int id;
    private List<PowerData> powers;
    
    public PacketBroadcastState()
    {

    }

    public PacketBroadcastState(EntityPlayer player)
    {
    	super(player);
        id = player.getEntityId();
        powers = ALPlayerData.getData(player).powers;
    }

    public void fromBytes(ByteBuf buf)
    {
    	super.fromBytes(buf);
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
    	super.toBytes(buf);
        buf.writeInt(id);
        buf.writeInt(powers.size());
		
		for (PowerData data : powers)
		{
			ByteBufUtils.writeUTF8String(buf, data.power.getName());
			buf.writeBoolean(data.unlocked);
			buf.writeInt(data.xpInvested);
		}
    }

    public static class Handler implements IMessageHandler<PacketBroadcastState, IMessage>
    {
        public IMessage onMessage(PacketBroadcastState message, MessageContext ctx)
        {
        	Object[] playerData = message.playerData;
        	
            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity lookupEntity = player.worldObj.getEntityByID(message.id);

                if (lookupEntity instanceof EntityPlayer && player != lookupEntity)
                {
                    EntityPlayer lookupPlayer = (EntityPlayer) lookupEntity;
                    
                    for (int i = 0; i < playerData.length; ++i)
                    {
                    	if (ALData.shouldSave[i])
            			{
                    		ALData.set(lookupPlayer, i, playerData[i]);
            			}
                    }
                    
                    DataManager.setPowers(lookupPlayer, message.powers);
                    
                    ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateLightsaber(player, LightsaberHelper.getEquippedLightsaber(player)));
                }
            }
            else
            {
                EntityPlayer player = ctx.getServerHandler().playerEntity;
                
                for (int i = 0; i < playerData.length; ++i)
                {
                	if (ALData.shouldSave[i])
        			{
                		ALData.setWithoutNotify(player, i, playerData[i]);
        			}
                }
                
                DataManager.setPowers(player, message.powers);
                
                ALNetworkManager.networkWrapper.sendToDimension(new PacketBroadcastState(player), player.dimension);
            }

            return null;
        }
    }
}
