package fiskfille.lightsabers.common.data;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketPlayerData;

public class ALData
{
	/* ################################################# */
	/* #                   IMPORTANT                   # */
	/* # Do NOT change any of these values EVER, under # */
	/* # ANY circumstances! It will corrupt all worlds # */
	/* #    previously loaded with the old values!     # */
	/* ################################################# */

	public static final int FORCE_XP = 0;
	public static final int DRAINING_XP_TO = 1;
	public static final int PREV_XP = 2;
	public static final int FORCE_POWER = 3;
	public static final int SELECTED_POWER = 4;
	public static final int USING_POWER = 5;
	public static final int TICKS_USING_POWER = 6;
	public static final int USE_POWER_COOLDOWN = 7;
	public static final int PREV_FORCE_POWER = 8;
	public static final int FORCE_POWER_DIFF = 9;
	public static final int PREV_FORCE_POWER_DIFF = 10;
	public static final int PREV_USING_POWER = 11;
	public static final int FORCE_PUSHING_TIMER = 12;
	public static final int PREV_FORCE_PUSHING_TIMER = 13;
	public static final int DRAIN_LIFE_TIMER = 14;
	public static final int PREV_DRAIN_LIFE_TIMER = 15;
	public static final int RIGHT_ARM_TIMER = 16;
	public static final int PREV_RIGHT_ARM_TIMER = 17;
	public static final int LEFT_ARM_TIMER = 18;
	public static final int PREV_LEFT_ARM_TIMER = 19;

	public static final int MAX_VALUE = PREV_LEFT_ARM_TIMER + 1;
	public static final boolean[] shouldSave = new boolean[MAX_VALUE];
	public static final Class[] dataTypes = new Class[MAX_VALUE];
	public static final String[] keys = new String[MAX_VALUE];

	public static void setWithoutNotify(EntityPlayer player, int i, Object object)
	{
		Class dataType = dataTypes[i];
		ALPlayerData.getData(player).data[i] = dataType.cast(object);
	}

	public static void set(EntityPlayer player, int i, Object object)
	{
		if (!(get(player, i) != null && get(player, i).equals(object) || object != null && object.equals(get(player, i)) || object == get(player, i)))
		{
			if (player.worldObj.isRemote)
			{
				ALNetworkManager.networkWrapper.sendToServer(new PacketPlayerData(player, i, object));
			}
			else
			{
				ALNetworkManager.networkWrapper.sendToDimension(new PacketPlayerData(player, i, object), player.dimension);
			}
		}

		setWithoutNotify(player, i, object);
	}

	public static void incr(EntityPlayer player, int i, Object amount)
	{
		if (amount instanceof Integer)
		{
			set(player, i, (Integer)get(player, i) + (Integer)amount);
		}
		else if (amount instanceof Float)
		{
			set(player, i, (Float)get(player, i) + (Float)amount);
		}
		else if (amount instanceof Double)
		{
			set(player, i, (Double)get(player, i) + (Double)amount);
		}
		else if (amount instanceof String)
		{
			set(player, i, (String)get(player, i) + amount);
		}
		else
		{
			throw new RuntimeException("Cannot increment a non-numerical data type unless a String!");
		}
	}

	public static void incrWithoutNotify(EntityPlayer player, int i, Object amount)
	{
		if (amount instanceof Integer)
		{
			setWithoutNotify(player, i, (Integer)get(player, i) + (Integer)amount);
		}
		else if (amount instanceof Float)
		{
			setWithoutNotify(player, i, (Float)get(player, i) + (Float)amount);
		}
		else if (amount instanceof Double)
		{
			setWithoutNotify(player, i, (Double)get(player, i) + (Double)amount);
		}
		else if (amount instanceof String)
		{
			setWithoutNotify(player, i, (String)get(player, i) + amount);
		}
		else
		{
			throw new RuntimeException("Cannot increment a non-numerical data type unless a String!");
		}
	}

	public static void cap(EntityPlayer player, int i, Object min, Object max)
	{
		if (min instanceof Integer)
		{
			if ((Integer)ALData.get(player, i) < (Integer)min)
			{
				ALData.set(player, i, min);
			}

			if ((Integer)ALData.get(player, i) > (Integer)max)
			{
				ALData.set(player, i, max);
			}
		}
		else if (min instanceof Float)
		{
			if ((Float)ALData.get(player, i) < (Float)min)
			{
				ALData.set(player, i, min);
			}

			if ((Float)ALData.get(player, i) > (Float)max)
			{
				ALData.set(player, i, max);
			}
		}
		else if (min instanceof Double)
		{
			if ((Double)ALData.get(player, i) < (Double)min)
			{
				ALData.set(player, i, min);
			}

			if ((Double)ALData.get(player, i) > (Double)max)
			{
				ALData.set(player, i, max);
			}
		}
		else
		{
			throw new RuntimeException("Cannot cap a non-numerical data type!");
		}
	}

	public static void capWithoutNotify(EntityPlayer player, int i, Object min, Object max)
	{
		if (min instanceof Integer && max instanceof Integer)
		{
			if ((Integer)ALData.get(player, i) < (Integer)min)
			{
				ALData.setWithoutNotify(player, i, min);
			}

			if ((Integer)ALData.get(player, i) > (Integer)max)
			{
				ALData.setWithoutNotify(player, i, max);
			}
		}
		else if (min instanceof Float && max instanceof Float)
		{
			if ((Float)ALData.get(player, i) < (Float)min)
			{
				ALData.setWithoutNotify(player, i, min);
			}

			if ((Float)ALData.get(player, i) > (Float)max)
			{
				ALData.setWithoutNotify(player, i, max);
			}
		}
		else if (min instanceof Double && max instanceof Double)
		{
			if ((Double)ALData.get(player, i) < (Double)min)
			{
				ALData.setWithoutNotify(player, i, min);
			}

			if ((Double)ALData.get(player, i) > (Double)max)
			{
				ALData.setWithoutNotify(player, i, max);
			}
		}
		else
		{
			throw new RuntimeException("Cannot cap a non-numerical data type!");
		}
	}

	public static Object get(EntityPlayer player, int i)
	{
		return ALPlayerData.getData(player).data[i];
	}

	public static int getInt(EntityPlayer player, int i)
	{
		return (Integer)get(player, i);
	}

	public static float getFloat(EntityPlayer player, int i)
	{
		return (Float)get(player, i);
	}

	public static double getDouble(EntityPlayer player, int i)
	{
		return (Double)get(player, i);
	}

	public static boolean getBoolean(EntityPlayer player, int i)
	{
		return (Boolean)get(player, i);
	}

	public static String getString(EntityPlayer player, int i)
	{
		return (String)get(player, i);
	}

	public static Object fromBytes(ByteBuf buf, int i)
	{
		Class dataType = dataTypes[i];

		if (dataType == Integer.class)
		{
			return buf.readInt();
		}
		else if (dataType == Float.class)
		{
			return buf.readFloat();
		}
		else if (dataType == Double.class)
		{
			return buf.readDouble();
		}
		else if (dataType == Boolean.class)
		{
			return buf.readBoolean();
		}
		else if (dataType == String.class)
		{
			String s = ByteBufUtils.readUTF8String(buf);

			if (s != null && s.isEmpty())
			{
				s = null;
			}

			return s;
		}

		return null;
	}

	public static void toBytes(ByteBuf buf, int i, Object object)
	{
		Class dataType = dataTypes[i];

		if (dataType == Integer.class)
		{
			buf.writeInt((Integer)object);
		}
		else if (dataType == Float.class)
		{
			buf.writeFloat((Float)object);
		}
		else if (dataType == Double.class)
		{
			buf.writeDouble((Double)object);
		}
		else if (dataType == Boolean.class)
		{
			buf.writeBoolean((Boolean)object);
		}
		else if (dataType == String.class)
		{
			if (object != null)
			{
				ByteBufUtils.writeUTF8String(buf, (String)object);
			}
			else
			{
				ByteBufUtils.writeUTF8String(buf, "");
			}
		}
	}

	public static void writeToNBT(NBTTagCompound nbt, Object[] objects)
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();

		for (int i = 0; i < objects.length; ++i)
		{
			if (ALData.shouldSave[i])
			{
				Object object = objects[i];
				Class dataType = dataTypes[i];
				String key = keys[i];

				if (dataType == Integer.class)
				{
					nbttagcompound.setInteger(key, (Integer)object);
				}
				else if (dataType == Float.class)
				{
					nbttagcompound.setFloat(key, (Float)object);
				}
				else if (dataType == Double.class)
				{
					nbttagcompound.setDouble(key, (Double)object);
				}
				else if (dataType == Boolean.class)
				{
					nbttagcompound.setBoolean(key, (Boolean)object);
				}
				else if (dataType == String.class)
				{
					String s = (String)object;

					if (s != null && !s.isEmpty())
					{
						nbttagcompound.setString(key, s);
					}
				}
			}
		}

		nbt.setTag("DataArray", nbttagcompound);
	}

	public static Object[] readFromNBT(NBTTagCompound nbt)
	{
		NBTTagCompound nbttagcompound = nbt.getCompoundTag("DataArray");
		Object[] objects = new Object[MAX_VALUE];

		for (int i = 0; i < objects.length; ++i)
		{
			if (ALData.shouldSave[i])
			{
				Class dataType = dataTypes[i];
				String key = keys[i];

				if (dataType == Integer.class)
				{
					objects[i] = nbttagcompound.getInteger(key);
				}
				else if (dataType == Float.class)
				{
					objects[i] = nbttagcompound.getFloat(key);
				}
				else if (dataType == Double.class)
				{
					objects[i] = nbttagcompound.getDouble(key);
				}
				else if (dataType == Boolean.class)
				{
					objects[i] = nbttagcompound.getBoolean(key);
				}
				else if (dataType == String.class)
				{
					String s = nbttagcompound.getString(key);

					if (s != null && (s.isEmpty() || s.equals("null")))
					{
						s = null;
					}

					objects[i] = s;
				}
			}
		}

		return objects;
	}

	public static Object[] init()
	{
		Object[] objects = new Object[MAX_VALUE];
		init(objects, FORCE_XP, Float.class, 0.0F);
		init(objects, DRAINING_XP_TO, false, String.class, "");
		init(objects, PREV_XP, Integer.class, 0);
		init(objects, FORCE_POWER, Float.class, 0.0F);
		init(objects, SELECTED_POWER, Integer.class, 0);
		init(objects, USING_POWER, Boolean.class, false);
		init(objects, TICKS_USING_POWER, Integer.class, 0);
		init(objects, USE_POWER_COOLDOWN, Integer.class, 0);
		init(objects, PREV_FORCE_POWER, false, Float.class, 0.0F);
		init(objects, FORCE_POWER_DIFF, Float.class, 0.0F);
		init(objects, PREV_FORCE_POWER_DIFF, false, Float.class, 0.0F);
		init(objects, PREV_USING_POWER, Boolean.class, false);
		init(objects, FORCE_PUSHING_TIMER, Float.class, 0.0F);
		init(objects, PREV_FORCE_PUSHING_TIMER, false, Float.class, 0.0F);
		init(objects, DRAIN_LIFE_TIMER, Float.class, 0.0F);
		init(objects, PREV_DRAIN_LIFE_TIMER, false, Float.class, 0.0F);
		init(objects, RIGHT_ARM_TIMER, Float.class, 0.0F);
		init(objects, PREV_RIGHT_ARM_TIMER, false, Float.class, 0.0F);
		init(objects, LEFT_ARM_TIMER, Float.class, 0.0F);
		init(objects, PREV_LEFT_ARM_TIMER, false, Float.class, 0.0F);
		return objects;
	}

	private static void init(Object[] objects, int i, boolean save, Class dataType, Object defaultValue)
	{
		shouldSave[i] = save;
		dataTypes[i] = dataType;
		objects[i] = defaultValue;
	}

	private static void init(Object[] objects, int i, Class dataType, Object defaultValue)
	{
		init(objects, i, true, dataType, defaultValue);
	}

	static
	{
		for (Field field : ALData.class.getFields())
		{
			String s = field.getType().getName();

			if (s.equals("int") && !field.getName().equals("MAX_VALUE"))
			{
				try
				{
					Integer i = (Integer)field.get(null);
					keys[i] = ALHelper.getUnconventionalName(field.getName());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
