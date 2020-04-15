package com.fiskmods.lightsabers.common.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALPlayerData;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.google.common.collect.Lists;

import fiskfille.utils.common.network.AbstractMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public abstract class MessageSyncBase<REQ extends MessageSyncBase> extends AbstractMessage<REQ>
{
    protected Map<ALData, Object> playerData;
    protected List<StatusEffect> activeEffects;

    public MessageSyncBase()
    {
    }

    protected MessageSyncBase(EntityPlayer player)
    {
        playerData = ALPlayerData.getData(player).data;
        activeEffects = StatusEffect.get(player);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerData = ALData.fromBytes(buf, new HashMap<ALData, Object>());
        activeEffects = Lists.newArrayList();
        int length = buf.readInt();

        for (int j = 0; j < length; ++j)
        {
            StatusEffect effect = StatusEffect.fromBytes(buf);

            if (effect != null)
            {
                activeEffects.add(effect);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ALData.toBytes(buf, playerData);
        buf.writeInt(activeEffects.size());

        for (StatusEffect effect : activeEffects)
        {
            effect.toBytes(buf);
        }
    }
}
