package com.fiskmods.lightsabers.common.network;

import java.util.Collections;
import java.util.Map.Entry;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALEntityData;

import net.minecraft.entity.player.EntityPlayer;

public class MessagePlayerJoin extends MessageSyncBase<MessagePlayerJoin>
{
    public MessagePlayerJoin()
    {
    }

    public MessagePlayerJoin(EntityPlayer player)
    {
        super(player);
    }

    @Override
    public void receive() throws MessageException
    {
        EntityPlayer player = getPlayer();

        for (Entry<ALData, Object> e : playerData.entrySet())
        {
            e.getKey().setWithoutNotify(player, e.getValue());
        }

        if (!activeEffects.isEmpty())
        {
            Collections.sort(activeEffects);
        }

        ALEntityData.getData(player).activeEffects = activeEffects;
    }
}
