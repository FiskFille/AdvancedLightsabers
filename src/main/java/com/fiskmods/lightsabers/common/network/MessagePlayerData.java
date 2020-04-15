package com.fiskmods.lightsabers.common.network;

import com.fiskmods.lightsabers.common.data.ALData;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.Log;
import fiskfille.utils.common.network.AbstractMessage;
import fiskfille.utils.helper.NBTHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessagePlayerData extends AbstractMessage<MessagePlayerData>
{
    private int id;
    private ALData type;
    private Object value;

    public MessagePlayerData()
    {
    }

    public MessagePlayerData(EntityPlayer player, ALData data, Object obj)
    {
        id = player.getEntityId();
        type = data;
        value = obj;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        type = ALData.REGISTRY.getObjectById(buf.readShort());
        value = NBTHelper.fromBytes(buf, type.typeClass);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeShort(ALData.REGISTRY.getIDForObject(type));
        NBTHelper.toBytes(buf, value);
    }

    @Override
    public void receive() throws MessageException
    {
        EntityPlayer player = getSender(id);
        Side senderSide = context.side.isClient() ? Side.SERVER : Side.CLIENT;

        if (!type.hasPerms(senderSide))
        {
            Log.warn("Player %s tried to set %s to %s on illegal side %s!", player.getCommandSenderName(), type, value, senderSide);
            return;
        }

        if (context.side.isClient())
        {
            type.setWithoutNotify(player, value);
        }
        else
        {
            type.set(player, value);
        }
    }
}
