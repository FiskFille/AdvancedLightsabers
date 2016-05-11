package fiskfille.lightsabers.common.data;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;
import fiskfille.lightsabers.common.proxy.CommonProxy;

public class ALPlayerData implements IExtendedEntityProperties
{
	public static final String IDENTIFIER = "LIGHTSABERSPLAYERDATA";

	public Object[] data = ALData.init();
	public List<PowerData> powers = createPowerDataList();

	public EntityPlayer player;

	public static ALPlayerData getData(EntityPlayer player)
	{
		return (ALPlayerData)player.getExtendedProperties(IDENTIFIER);
	}

	private List<PowerData> createPowerDataList()
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
		ALData.writeToNBT(nbttagcompound, data);

		for (PowerData data : powers)
		{
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("Id", data.power.getName());
			nbttagcompound1.setBoolean("Unlocked", data.unlocked);
			nbttagcompound1.setInteger("XpInvested", data.xpInvested);
			nbttaglist.appendTag(nbttagcompound1);
		}

		nbttagcompound.setTag("Powers", nbttaglist);
		nbt.setTag(IDENTIFIER, nbttagcompound);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound nbttagcompound = nbt.getCompoundTag(IDENTIFIER);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Powers", 10);

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
	}

	@Override
	public void init(Entity entity, World world)
	{
		if (entity instanceof EntityPlayer)
		{
			player = (EntityPlayer)entity;
		}
	}

	public static void saveProxyData(EntityPlayer player)
	{
		ALPlayerData playerData = ALPlayerData.getData(player);
		NBTTagCompound savedData = new NBTTagCompound();

		playerData.saveNBTData(savedData);
		CommonProxy.storeEntityData(player.getUniqueID().toString(), savedData);
	}

	public static void loadProxyData(EntityPlayer player)
	{
		ALPlayerData playerData = ALPlayerData.getData(player);
		NBTTagCompound savedData = CommonProxy.getEntityData(player.getUniqueID().toString());

		if (savedData != null)
		{
			playerData.loadNBTData(savedData);
		}
	}
}
