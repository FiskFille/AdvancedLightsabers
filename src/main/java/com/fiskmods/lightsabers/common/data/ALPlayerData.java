package com.fiskmods.lightsabers.common.data;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ALPlayerData implements IExtendedEntityProperties
{
    public static final String IDENTIFIER = "ALPlayer";

    public Map<ALData, Object> data = Maps.newHashMap();

    public static ALPlayerData getData(EntityPlayer player)
    {
        return (ALPlayerData) player.getExtendedProperties(IDENTIFIER);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setBoolean("Saved", true);
        ALData.writeToNBT(compound, data);

        nbt.setTag(IDENTIFIER, compound);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound compound = nbt.getCompoundTag(IDENTIFIER);

        if (compound.getBoolean("Saved"))
        {
            data = ALData.readFromNBT(compound, data);
        }
    }

    @Override
    public void init(Entity entity, World world)
    {
    }

    public void copy(ALPlayerData props)
    {
        data = props.data;
    }

    public <T> void putData(ALData<T> type, T value)
    {
        data.put(type, value);
    }

    public <T> T getData(ALData<T> type)
    {
        if (data.containsKey(type))
        {
            return (T) data.get(type);
        }
        else if (!type.defaultValue.canEqual())
        {
            putData(type, type.getDefault());

            return getData(type);
        }

        return type.getDefault();
    }
}
