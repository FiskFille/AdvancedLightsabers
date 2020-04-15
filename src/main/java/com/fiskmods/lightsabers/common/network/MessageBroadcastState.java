package com.fiskmods.lightsabers.common.network;

import java.util.Collections;
import java.util.Map.Entry;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALEntityData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageBroadcastState extends MessageSyncBase<MessageBroadcastState>
{
    private int id;

    public MessageBroadcastState()
    {
    }

    public MessageBroadcastState(EntityPlayer player)
    {
        super(player);
        id = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        super.toBytes(buf);
    }

    @Override
    public void receive() throws MessageException
    {
        EntityPlayer player = getSender(id);

        if (context.side.isClient() && Lightsabers.proxy.isClientPlayer(player))
        {
            return;
        }

        for (Entry<ALData, Object> e : playerData.entrySet())
        {
            e.getKey().setWithoutNotify(player, e.getValue());
        }

        if (!activeEffects.isEmpty())
        {
            Collections.sort(activeEffects);
        }

        ALEntityData.getData(player).activeEffects = activeEffects;

        if (context.side.isServer())
        {
            ALNetworkManager.wrapper.sendToDimension(new MessageBroadcastState(player), player.dimension);
        }
    }
}
