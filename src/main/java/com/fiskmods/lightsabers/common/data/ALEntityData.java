package com.fiskmods.lightsabers.common.data;

import java.util.List;

import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class ALEntityData implements IExtendedEntityProperties
{
    public static final String IDENTIFIER = "ALEntity";

    public List<StatusEffect> activeEffects = Lists.newArrayList();
    public boolean forcePushed;

    public static ALEntityData getData(EntityLivingBase entity)
    {
        return (ALEntityData) entity.getExtendedProperties(IDENTIFIER);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Saved", true);
        compound.setBoolean("ForcePushed", forcePushed);

        if (!activeEffects.isEmpty())
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (StatusEffect effect : activeEffects)
            {
                nbttaglist.appendTag(effect.writeToNBT(new NBTTagCompound()));
            }

            compound.setTag("Effects", nbttaglist);
        }

        nbt.setTag(IDENTIFIER, compound);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound compound = nbt.getCompoundTag(IDENTIFIER);

        if (compound.getBoolean("Saved"))
        {
            forcePushed = compound.getBoolean("ForcePushed");
            
            if (compound.hasKey("Effects", NBT.TAG_LIST))
            {
                NBTTagList nbttaglist = compound.getTagList("Effects", NBT.TAG_COMPOUND);
                activeEffects.clear();

                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    StatusEffect effect = StatusEffect.readFromNBT(nbttaglist.getCompoundTagAt(i));

                    if (effect != null)
                    {
                        activeEffects.add(effect);
                    }
                }
            }
        }
    }

    @Override
    public void init(Entity entity, World world)
    {
    }
}
