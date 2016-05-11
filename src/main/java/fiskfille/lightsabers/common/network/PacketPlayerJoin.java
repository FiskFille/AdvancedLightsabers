package fiskfille.lightsabers.common.network;

import io.netty.buffer.ByteBuf;

import java.util.List;

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

public class PacketPlayerJoin extends PacketSyncBase
{
	private List<PowerData> powers;
	
    public PacketPlayerJoin()
    {
    }

    public PacketPlayerJoin(EntityPlayer player)
    {
    	super(player);
    	powers = ALPlayerData.getData(player).powers;
    }

    public void fromBytes(ByteBuf buf)
    {
    	super.fromBytes(buf);
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
        buf.writeInt(powers.size());
		
		for (PowerData data : powers)
		{
			ByteBufUtils.writeUTF8String(buf, data.power.getName());
			buf.writeBoolean(data.unlocked);
			buf.writeInt(data.xpInvested);
		}
    }

    public static class Handler implements IMessageHandler<PacketPlayerJoin, IMessage>
    {
        public IMessage onMessage(PacketPlayerJoin message, MessageContext ctx)
        {
        	Object[] playerData = message.playerData;
        	
            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                
                for (int i = 0; i < playerData.length; ++i)
                {
                	if (ALData.shouldSave[i])
        			{
                		ALData.set(player, i, playerData[i]);
        			}
                }
                
                DataManager.setPowers(player, message.powers);
                
                ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateLightsaber(player, LightsaberHelper.getEquippedLightsaber(player)));
            }

            return null;
        }
    }
}
