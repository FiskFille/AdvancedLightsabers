package fiskfille.lightsabers.common.data;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;

public class ALPlayerData implements IExtendedEntityProperties
{
    public static final String IDENTIFIER = "ALPlayer";

    public Object[] data = ALData.init();
    public List<PowerData> powers = createPowerDataList();
    public List<Power> selectedPowers = Arrays.asList(null, null, null);

    public EntityPlayer player;

    public static ALPlayerData getData(EntityPlayer player)
    {
        return (ALPlayerData) player.getExtendedProperties(IDENTIFIER);
    }

    public List<PowerData> createPowerDataList()
    {
        List<PowerData> list = Lists.newArrayList();

        for (Power power : Power.powers)
        {
            list.add(new PowerData(power));
        }

        return list;
    }

    public void onUpdate()
    {

    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();
        NBTTagList nbttaglist1 = new NBTTagList();
        ALData.writeToNBT(nbttagcompound, data);

        for (PowerData data : powers)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setString("Id", data.power.getName());
            nbttagcompound1.setBoolean("Unlocked", data.unlocked);
            nbttagcompound1.setInteger("XpInvested", data.xpInvested);
            nbttaglist.appendTag(nbttagcompound1);
        }

        for (Power power : selectedPowers)
        {
            String s = power != null ? power.getName() : "null";
            nbttaglist1.appendTag(new NBTTagString(s));
        }

        nbttagcompound.setTag("Powers", nbttaglist);
        nbttagcompound.setTag("SelectedPowers", nbttaglist1);
        nbttagcompound.setBoolean("Saved", true);
        nbt.setTag(IDENTIFIER, nbttagcompound);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound nbttagcompound = nbt.getCompoundTag(IDENTIFIER);

        if (nbttagcompound.getBoolean("Saved"))
        {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Powers", 10);
            NBTTagList nbttaglist1 = nbttagcompound.getTagList("SelectedPowers", 8);

            if (nbttagcompound != null)
            {
                data = ALData.readFromNBT(nbttagcompound);
            }

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);

                for (PowerData data : powers)
                {
                    if (data.power.getName().equals(nbttagcompound1.getString("Id")))
                    {
                        data.unlocked = nbttagcompound1.getBoolean("Unlocked");
                        data.xpInvested = nbttagcompound1.getInteger("XpInvested");
                    }
                }
            }

            Power[] powers = new Power[nbttaglist1.tagCount()];

            for (int i = 0; i < nbttaglist1.tagCount(); ++i)
            {
                String s = nbttaglist1.getStringTagAt(i);
                powers[i] = Power.getPowerFromName(s);
            }

            selectedPowers = Arrays.asList(powers);
        }
    }

    @Override
    public void init(Entity entity, World world)
    {
        if (entity instanceof EntityPlayer)
        {
            player = (EntityPlayer) entity;
        }
    }

    public void copy(ALPlayerData props)
    {
        data = props.data;
        powers = props.powers;
        selectedPowers = props.selectedPowers;
    }
}
