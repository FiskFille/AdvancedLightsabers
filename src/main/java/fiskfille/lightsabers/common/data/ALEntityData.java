package fiskfille.lightsabers.common.data;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.google.common.collect.Lists;

public class ALEntityData implements IExtendedEntityProperties
{
    public static final String IDENTIFIER = "ALEntity";

    public List<StatusEffect> activeEffects = Lists.newArrayList();

    public EntityLivingBase entityLiving;

    public static ALEntityData getData(EntityLivingBase entity)
    {
        return (ALEntityData) entity.getExtendedProperties(IDENTIFIER);
    }

    public void onUpdate()
    {

    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();

        for (StatusEffect effect : activeEffects)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setInteger("Id", effect.id);
            nbttagcompound1.setInteger("Duration", effect.duration);
            nbttagcompound1.setInteger("Amplifier", effect.amplifier);

            if (effect.casterUUID != null)
            {
                nbttagcompound1.setLong("CasterUUIDMost", effect.casterUUID.getMostSignificantBits());
                nbttagcompound1.setLong("CasterUUIDLeast", effect.casterUUID.getLeastSignificantBits());
            }

            nbttaglist.appendTag(nbttagcompound1);
        }

        nbttagcompound.setTag("ForceEffects", nbttaglist);
        nbt.setTag(IDENTIFIER, nbttagcompound);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound nbttagcompound = nbt.getCompoundTag(IDENTIFIER);
        NBTTagList nbttaglist = nbttagcompound.getTagList("ForceEffects", 10);
        activeEffects.clear();

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            UUID uuid = null;

            if (nbttagcompound1.hasKey("CasterUUIDMost", 4) && nbttagcompound1.hasKey("CasterUUIDLeast", 4))
            {
                uuid = new UUID(nbttagcompound1.getLong("CasterUUIDMost"), nbttagcompound1.getLong("CasterUUIDLeast"));
            }

            activeEffects.add(new StatusEffect(nbttagcompound1.getInteger("Id"), nbttagcompound1.getInteger("Duration"), nbttagcompound1.getInteger("Amplifier"), uuid));
        }
    }

    @Override
    public void init(Entity entity, World world)
    {
        if (entity instanceof EntityLivingBase)
        {
            entityLiving = (EntityLivingBase) entity;
        }
    }
}
