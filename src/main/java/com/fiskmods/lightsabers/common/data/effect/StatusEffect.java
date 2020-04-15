package com.fiskmods.lightsabers.common.data.effect;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.fiskmods.lightsabers.common.data.ALEntityData;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.network.MessageUpdateEffects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.Constants.NBT;

public class StatusEffect implements Comparable<StatusEffect>
{
    public Effect effect;
    public short duration;
    public byte amplifier;
    public UUID casterUUID;

    public StatusEffect(Effect effect, int duration, int amplifier)
    {
        this.effect = effect;
        this.duration = (short) duration;
        this.amplifier = (byte) amplifier;
    }

    public StatusEffect(Effect effect, int duration, int amplifier, UUID uuid)
    {
        this(effect, duration, amplifier);
        casterUUID = uuid;
    }

    public StatusEffect(Effect effect, int duration, int amplifier, EntityLivingBase entity)
    {
        this(effect, duration, amplifier, entity != null ? entity.getUniqueID() : null);
    }

    public static StatusEffect fromBytes(ByteBuf buf)
    {
        Effect e = Effect.getEffectById(buf.readByte());

        if (e != null)
        {
            StatusEffect effect = new StatusEffect(e, buf.readShort(), buf.readByte());

            if (buf.readBoolean())
            {
                UUID uuid = new UUID(buf.readLong(), buf.readLong());
                effect.casterUUID = uuid;
            }

            return effect;
        }

        return null;
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeByte((byte) Effect.getIdFromEffect(effect));
        buf.writeShort(duration);
        buf.writeByte(amplifier);

        if (casterUUID != null)
        {
            buf.writeBoolean(true);
            buf.writeLong(casterUUID.getMostSignificantBits());
            buf.writeLong(casterUUID.getLeastSignificantBits());
        }
        else
        {
            buf.writeBoolean(false);
        }
    }

    public static StatusEffect readFromNBT(NBTTagCompound tag)
    {
        Effect effect = Effect.getEffectFromName(tag.getString("Id"));

        if (effect != null)
        {
            UUID uuid = null;

            if (tag.hasKey("CasterUUIDMost", NBT.TAG_ANY_NUMERIC) && tag.hasKey("CasterUUIDLeast", NBT.TAG_ANY_NUMERIC))
            {
                uuid = new UUID(tag.getLong("CasterUUIDMost"), tag.getLong("CasterUUIDLeast"));
            }

            return new StatusEffect(effect, tag.getInteger("Duration"), tag.getInteger("Amplifier"), uuid);
        }

        return null;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag.setString("Id", effect.delegate.name());
        tag.setInteger("Duration", duration);
        tag.setInteger("Amplifier", amplifier);

        if (casterUUID != null)
        {
            tag.setLong("CasterUUIDMost", casterUUID.getMostSignificantBits());
            tag.setLong("CasterUUIDLeast", casterUUID.getLeastSignificantBits());
        }

        return tag;
    }

    public boolean isMaxDuration()
    {
        return duration >= Short.MAX_VALUE;
    }

    public static void add(EntityLivingBase entity, Effect effect, int duration, int amplifier)
    {
        add(entity, null, effect, duration, amplifier);
    }

    public static void add(EntityLivingBase entity, Entity caster, Effect effect, int duration, int amplifier)
    {
        add(entity, caster instanceof EntityLivingBase ? (EntityLivingBase) caster : null, effect, duration, amplifier);
    }

    public static void add(EntityLivingBase entity, EntityLivingBase caster, Effect effect, int duration, int amplifier)
    {
        List<StatusEffect> list = get(entity);
        duration = MathHelper.clamp_int(duration, Short.MIN_VALUE, Short.MAX_VALUE);

        for (StatusEffect status : list)
        {
            if (status.effect == effect)
            {
                short prevDuration = status.duration;
                byte prevAmplifier = status.amplifier;

                status.duration = (short) Math.max(status.duration, duration);
                status.amplifier = (byte) Math.max(status.amplifier, amplifier);

                if (caster != null)
                {
                    status.casterUUID = caster.getUniqueID();
                }

                if (prevDuration != status.duration || prevAmplifier != status.amplifier)
                {
                    if (!entity.worldObj.isRemote)
                    {
                        ALNetworkManager.wrapper.sendToDimension(new MessageUpdateEffects(entity, list), entity.dimension);
                    }
                    
                    Collections.sort(list);
                }

                return;
            }
        }

        list.add(new StatusEffect(effect, duration, amplifier, caster));
        Collections.sort(list);

        if (!entity.worldObj.isRemote)
        {
            ALNetworkManager.wrapper.sendToDimension(new MessageUpdateEffects(entity, list), entity.dimension);
        }
    }

    public static List<StatusEffect> get(EntityLivingBase entity)
    {
        return ALEntityData.getData(entity).activeEffects;
    }

    public static StatusEffect get(EntityLivingBase entity, Effect effect)
    {
        for (StatusEffect status : get(entity))
        {
            if (status.effect == effect)
            {
                return status;
            }
        }

        return null;
    }

    public static boolean has(EntityLivingBase entity, Effect effect)
    {
        return get(entity, effect) != null;
    }

    public static void clear(EntityLivingBase entity)
    {
        if (!entity.worldObj.isRemote)
        {
            List<StatusEffect> list = get(entity);

            if (!list.isEmpty())
            {
                list.clear();
                ALNetworkManager.wrapper.sendToDimension(new MessageUpdateEffects(entity, list), entity.dimension);
            }
        }
    }

    public static void clear(EntityLivingBase entity, Effect effect)
    {
        if (!entity.worldObj.isRemote && has(entity, effect))
        {
            List<StatusEffect> list = get(entity);
            Iterator<StatusEffect> iter = list.iterator();

            while (iter.hasNext())
            {
                if (iter.next().effect == effect)
                {
                    iter.remove();
                }
            }

            ALNetworkManager.wrapper.sendToDimension(new MessageUpdateEffects(entity, list), entity.dimension);

            if (!list.isEmpty())
            {
                Collections.sort(list);
            }
        }
    }

    public static List<EntityLivingBase> getTargets(EntityLivingBase caster, Effect effect, int amplifier)
    {
        List<EntityLivingBase> targets = Lists.newArrayList();

        for (EntityLivingBase entity : Iterables.filter(caster.worldObj.loadedEntityList, EntityLivingBase.class))
        {
            StatusEffect status = StatusEffect.get(entity, effect);

            if (status != null && (amplifier < 0 || amplifier == status.amplifier) && status.casterUUID != null && status.casterUUID.equals(caster.getUniqueID()))
            {
                targets.add(entity);
            }
        }

        return targets;
    }

    public static List<EntityLivingBase> getTargets(EntityLivingBase caster, Effect effect)
    {
        return getTargets(caster, effect, -1);
    }
    
    @Override
    public String toString()
    {
        return String.format("Effect[id=%s,d=%s,a=%s]", effect, duration, amplifier);
    }

    @Override
    public int compareTo(StatusEffect o)
    {
        return Doubles.compare(o.duration, duration);
    }
}
