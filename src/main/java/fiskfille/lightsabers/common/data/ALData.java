package fiskfille.lightsabers.common.data;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
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
	
	public static final int DRAINING_XP_TO = 0;
	
	public static final int MAX_VALUE = DRAINING_XP_TO + 1;
	public static final Class[] dataTypes = new Class[MAX_VALUE];
	public static final boolean[] shouldSave = new boolean[MAX_VALUE];
	
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
	
	public static List getList(EntityPlayer player, int i)
	{
		return (List)get(player, i);
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
				
				if (dataType == Integer.class)
				{
					nbttagcompound.setInteger(i + "", (Integer)object);
				}
				else if (dataType == Float.class)
				{
					nbttagcompound.setFloat(i + "", (Float)object);
				}
				else if (dataType == Double.class)
				{
					nbttagcompound.setDouble(i + "", (Double)object);
				}
				else if (dataType == Boolean.class)
				{
					nbttagcompound.setBoolean(i + "", (Boolean)object);
				}
				else if (dataType == String.class)
				{
					String s = (String)object;
					
					if (s != null && !s.isEmpty())
					{
						nbttagcompound.setString(i + "", s);
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
				
				if (dataType == Integer.class)
				{
					objects[i] = nbttagcompound.getInteger(i + "");
				}
				else if (dataType == Float.class)
				{
					objects[i] = nbttagcompound.getFloat(i + "");
				}
				else if (dataType == Double.class)
				{
					objects[i] = nbttagcompound.getDouble(i + "");
				}
				else if (dataType == Boolean.class)
				{
					objects[i] = nbttagcompound.getBoolean(i + "");
				}
				else if (dataType == String.class)
				{
					String s = nbttagcompound.getString(i + "");
					
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
		init(objects, DRAINING_XP_TO, String.class, "");
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
}
