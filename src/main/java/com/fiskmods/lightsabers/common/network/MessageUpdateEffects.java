package com.fiskmods.lightsabers.common.network;

import java.util.Collections;
import java.util.List;

import com.fiskmods.lightsabers.common.data.ALEntityData;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.google.common.collect.Lists;

import fiskfille.utils.common.network.AbstractMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;

public class MessageUpdateEffects extends AbstractMessage<MessageUpdateEffects>
{
    private int id;
    private List<StatusEffect> activeEffects;

    public MessageUpdateEffects()
    {
    }

    public MessageUpdateEffects(EntityLivingBase entity, List<StatusEffect> list)
    {
        id = entity.getEntityId();
        activeEffects = list;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        activeEffects = Lists.newArrayList();
        int length = buf.readInt();

        for (int i = 0; i < length; ++i)
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
        buf.writeInt(id);
        buf.writeInt(activeEffects.size());

        for (StatusEffect effect : activeEffects)
        {
            effect.toBytes(buf);
        }
    }

    @Override
    public void receive() throws MessageException
    {
        ALEntityData.getData(getEntity(EntityLivingBase.class, id)).activeEffects = activeEffects;

        if (!activeEffects.isEmpty())
        {
            Collections.sort(activeEffects);
        }
    }
}
