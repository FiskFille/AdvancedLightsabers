package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketBroadcastState implements IMessage
{
    private int id;
    
    public PacketBroadcastState()
    {

    }

    public PacketBroadcastState(EntityPlayer player)
    {
        id = player.getEntityId();
    }

    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
    }

    public static class Handler implements IMessageHandler<PacketBroadcastState, IMessage>
    {
        public IMessage onMessage(PacketBroadcastState message, MessageContext ctx)
        {
            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity lookupEntity = player.worldObj.getEntityByID(message.id);

                if (lookupEntity instanceof EntityPlayer && player != lookupEntity)
                {
                    EntityPlayer lookupPlayer = (EntityPlayer) lookupEntity;
                    
                    ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateLightsaber(player, LightsaberHelper.getEquippedLightsaber(player)));
                }
            }
            else
            {
                EntityPlayer player = ctx.getServerHandler().playerEntity;
                
                ALNetworkManager.networkWrapper.sendToDimension(new PacketBroadcastState(player), player.dimension);
            }

            return null;
        }
    }
}
