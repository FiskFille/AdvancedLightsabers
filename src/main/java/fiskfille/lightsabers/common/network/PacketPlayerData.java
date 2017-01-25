package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.ALData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketPlayerData implements IMessage
{
    public int id;
    private int key;
    private Object value;

    public PacketPlayerData()
    {

    }

    public PacketPlayerData(EntityPlayer player, int i, Object object)
    {
        id = player.getEntityId();
        key = i;
        value = object;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        key = buf.readInt();
        value = ALData.fromBytes(buf, key);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeInt(key);
        ALData.toBytes(buf, key, value);
    }

    public static class Handler implements IMessageHandler<PacketPlayerData, IMessage>
    {
        @Override
        public IMessage onMessage(PacketPlayerData message, MessageContext ctx)
        {
            int key = message.key;
            Object value = message.value;

            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);

                if (entity instanceof EntityPlayer)
                {
                    ALData.setWithoutNotify((EntityPlayer) entity, key, value);
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
                        ALData.set((EntityPlayer) entity, key, value);
                    }
                }
            }

            return null;
        }
    }
}
