package com.fiskmods.lightsabers.common.data;

import java.lang.reflect.Field;

import com.fiskmods.lightsabers.Lightsabers;
import com.google.common.base.Predicate;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.helper.FiskServerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ALDataInterp<T> extends ALData<T>
{
    protected final ALData<T> prevData;

    protected ALDataInterp(T defaultValue)
    {
        super(defaultValue);
        prevData = new ALData(defaultValue).setExempt(SAVE_NBT | SYNC_BYTES);
    }

    protected ALDataInterp(T defaultValue, Predicate<Entity> canSet)
    {
        super(defaultValue, canSet);
        prevData = new ALData(defaultValue, canSet).setExempt(SAVE_NBT | SYNC_BYTES);
    }

    @Override
    protected ALDataInterp setExempt(int exempt)
    {
        prevData.setExempt(exempt);
        return (ALDataInterp) super.setExempt(exempt);
    }

    @Override
    protected ALDataInterp revokePerms(Side side)
    {
        prevData.revokePerms(side);
        return (ALDataInterp) super.revokePerms(side);
    }

    @Override
    public void update(Entity entity)
    {
        prevData.setWithoutNotify(entity, get(entity));
    }

    public ALData<T> getPrevData()
    {
        return prevData;
    }

    public T getPrev(EntityPlayer player)
    {
        return prevData.get(player);
    }

    public T getPrev(Entity entity)
    {
        return prevData.get(entity);
    }

    public T interpolate(Entity entity, float progress)
    {
        if (progress == 1)
        {
            return get(entity);
        }
        else if (ofType(Float.class))
        {
            return (T) Float.valueOf(FiskServerUtils.interpolate((Float) getPrev(entity), (Float) get(entity), progress));
        }
        else if (ofType(Double.class))
        {
            return (T) Double.valueOf(FiskServerUtils.interpolate((Double) getPrev(entity), (Double) get(entity), progress));
        }
        else
        {
            throw new RuntimeException("Cannot interpolate a non-decimal data type!");
        }
    }

    public T interpolate(Entity entity)
    {
        return interpolate(entity, Lightsabers.proxy.getRenderTick());
    }

    @Override
    protected void init(Field field, String name) throws ClassNotFoundException
    {
        super.init(field, name);
        prevData.init(field, "PREV_" + name);
    }
}
