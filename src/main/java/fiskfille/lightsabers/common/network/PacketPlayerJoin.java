package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketPlayerJoin implements IMessage
{
    public PacketPlayerJoin()
    {
    }

    public PacketPlayerJoin(EntityPlayer player)
    {
    }

    public void fromBytes(ByteBuf buf)
    {

    }

    public void toBytes(ByteBuf buf)
    {

    }

    public static class Handler implements IMessageHandler<PacketPlayerJoin, IMessage>
    {
        public IMessage onMessage(PacketPlayerJoin message, MessageContext ctx)
        {
            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                
                ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateLightsaber(player, LightsaberHelper.getEquippedLightsaber(player)));
            }

            return null;
        }
    }
}
