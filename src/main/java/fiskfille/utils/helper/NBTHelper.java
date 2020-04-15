package fiskfille.utils.helper;

import java.util.List;
import java.util.Map;

import com.fiskmods.lightsabers.ALReflection;
import com.fiskmods.lightsabers.common.data.ALData.ClassType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.network.ByteBufUtils;
import fiskfille.utils.DimensionalCoords;
import fiskfille.utils.registry.FiskRegistryEntry;
import fiskfille.utils.registry.FiskSimpleRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTHelper
{
    private static final Map<Class<? extends ISerializableObject>, ISaveAdapter> ADAPTERS = Maps.newHashMap();

    public static List<NBTBase> getTags(NBTTagList nbttaglist)
    {
        return ALReflection.tagList.get(nbttaglist);
    }

    public static NBTBase writeToNBT(Object obj)
    {
        if (obj instanceof ISerializableObject)
        {
            return ((ISerializableObject) obj).writeToNBT();
        }
        else if (obj instanceof Byte)
        {
            return new NBTTagByte((Byte) obj);
        }
        else if (obj instanceof Short)
        {
            return new NBTTagShort((Short) obj);
        }
        else if (obj instanceof Integer)
        {
            return new NBTTagInt((Integer) obj);
        }
        else if (obj instanceof Long)
        {
            return new NBTTagLong((Long) obj);
        }
        else if (obj instanceof Float)
        {
            return new NBTTagFloat((Float) obj);
        }
        else if (obj instanceof Double)
        {
            return new NBTTagDouble((Double) obj);
        }
        else if (obj instanceof Boolean)
        {
            return new NBTTagByte((byte) ((Boolean) obj ? 1 : 0));
        }
        else if (obj instanceof String)
        {
            String s = (String) obj;

            if (s != null)
            {
                return new NBTTagString(s);
            }
        }
        else if (obj instanceof List)
        {
            List list = (List) obj;
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < list.size(); ++i)
            {
                NBTBase tag = writeToNBT(list.get(i));

                if (tag != null)
                {
                    nbttaglist.appendTag(tag);
                }
            }

            return nbttaglist;
        }
        else if (obj instanceof ItemStack)
        {
            return ((ItemStack) obj).writeToNBT(new NBTTagCompound());
        }
        else if (obj instanceof DimensionalCoords)
        {
            DimensionalCoords coords = (DimensionalCoords) obj;
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("x", coords.posX);
            nbt.setInteger("y", coords.posY);
            nbt.setInteger("z", coords.posZ);
            nbt.setInteger("dim", coords.dimension);

            return nbt;
        }

        return null;
    }

    public static <T> T readFromNBT(NBTBase tag, Class<T> type)
    {
        return readFromNBT(tag, new ClassType<T>(type));
    }

    public static <T> T readFromNBT(NBTBase tag, ClassType<T> typeClass)
    {
        if (ISerializableObject.class.isAssignableFrom(typeClass.getType()))
        {
            if (ADAPTERS.containsKey(typeClass.getType()))
            {
                return (T) ADAPTERS.get(typeClass.getType()).readFromNBT(tag);
            }

            return null;
        }
        else if (tag instanceof NBTPrimitive)
        {
            NBTPrimitive nbt = (NBTPrimitive) tag;

            if (typeClass.getType() == Byte.class)
            {
                return (T) Byte.valueOf(nbt.func_150290_f());
            }
            else if (typeClass.getType() == Short.class)
            {
                return (T) Short.valueOf(nbt.func_150289_e());
            }
            else if (typeClass.getType() == Integer.class)
            {
                return (T) Integer.valueOf(nbt.func_150287_d());
            }
            else if (typeClass.getType() == Long.class)
            {
                return (T) Long.valueOf(nbt.func_150291_c());
            }
            else if (typeClass.getType() == Float.class)
            {
                return (T) Float.valueOf(nbt.func_150288_h());
            }
            else if (typeClass.getType() == Double.class)
            {
                return (T) Double.valueOf(nbt.func_150286_g());
            }
            else if (typeClass.getType() == Boolean.class)
            {
                return (T) Boolean.valueOf(nbt.func_150290_f() != 0);
            }
        }
        else if (typeClass.getType() == String.class && tag instanceof NBTTagString)
        {
            return (T) ((NBTTagString) tag).func_150285_a_();
        }
        else if (typeClass.getType() == List.class && tag instanceof NBTTagList)
        {
            List<NBTBase> tags = getTags((NBTTagList) tag);
            List list = Lists.newArrayList();

            for (int i = 0; i < tags.size(); ++i)
            {
                Object entry = readFromNBT(tags.get(i), typeClass.getParamSafe());

                if (entry != null)
                {
                    list.add(entry);
                }
            }

            return (T) list;
        }
        else if (tag instanceof NBTTagCompound)
        {
            NBTTagCompound nbt = (NBTTagCompound) tag;

            if (typeClass.getType() == ItemStack.class)
            {
                return (T) ItemStack.loadItemStackFromNBT(nbt);
            }
            else if (typeClass.getType() == DimensionalCoords.class)
            {
                return (T) new DimensionalCoords(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"), nbt.getInteger("dim"));
            }
        }

        return null;
    }

    public static void toBytes(ByteBuf buf, Object obj)
    {
        if (obj instanceof Byte)
        {
            buf.writeByte((Byte) obj);
        }
        else if (obj instanceof Short)
        {
            buf.writeShort((Short) obj);
        }
        else if (obj instanceof Integer)
        {
            buf.writeInt((Integer) obj);
        }
        else if (obj instanceof Long)
        {
            buf.writeLong((Long) obj);
        }
        else if (obj instanceof Float)
        {
            buf.writeFloat((Float) obj);
        }
        else if (obj instanceof Double)
        {
            buf.writeDouble((Double) obj);
        }
        else if (obj instanceof Boolean)
        {
            buf.writeBoolean((Boolean) obj);
        }
        else
        {
            buf.writeBoolean(obj != null);

            if (obj instanceof ISerializableObject)
            {
                ((ISerializableObject) obj).toBytes(buf);
            }
            else if (obj instanceof String)
            {
                ByteBufUtils.writeUTF8String(buf, (String) obj);
            }
            else if (obj instanceof List)
            {
                List list = (List) obj;
                buf.writeInt(list.size());

                for (int i = 0; i < list.size(); ++i)
                {
                    toBytes(buf, list.get(i));
                }
            }
            else if (obj instanceof ItemStack)
            {
                ByteBufUtils.writeItemStack(buf, (ItemStack) obj);
            }
            else if (obj instanceof DimensionalCoords)
            {
                DimensionalCoords coords = (DimensionalCoords) obj;
                buf.writeInt(coords.posX);
                buf.writeInt(coords.posY);
                buf.writeInt(coords.posZ);
                buf.writeInt(coords.dimension);
            }
        }
    }

    public static <T> T fromBytes(ByteBuf buf, Class<T> type)
    {
        return fromBytes(buf, new ClassType<T>(type));
    }

    public static <T> T fromBytes(ByteBuf buf, ClassType<T> typeClass)
    {
        if (typeClass.getType() == Byte.class)
        {
            return (T) Byte.valueOf(buf.readByte());
        }
        else if (typeClass.getType() == Short.class)
        {
            return (T) Short.valueOf(buf.readShort());
        }
        else if (typeClass.getType() == Integer.class)
        {
            return (T) Integer.valueOf(buf.readInt());
        }
        else if (typeClass.getType() == Long.class)
        {
            return (T) Long.valueOf(buf.readLong());
        }
        else if (typeClass.getType() == Float.class)
        {
            return (T) Float.valueOf(buf.readFloat());
        }
        else if (typeClass.getType() == Double.class)
        {
            return (T) Double.valueOf(buf.readDouble());
        }
        else if (typeClass.getType() == Boolean.class)
        {
            return (T) Boolean.valueOf(buf.readBoolean());
        }
        else if (buf.readBoolean())
        {
            if (ISerializableObject.class.isAssignableFrom(typeClass.getType()))
            {
                if (ADAPTERS.containsKey(typeClass.getType()))
                {
                    return (T) ADAPTERS.get(typeClass.getType()).fromBytes(buf);
                }
            }
            else if (typeClass.getType() == String.class)
            {
                return (T) ByteBufUtils.readUTF8String(buf);
            }
            else if (typeClass.getType() == List.class)
            {
                List list = Lists.newArrayList();
                int size = buf.readInt();

                for (int i = 0; i < size; ++i)
                {
                    Object entry = fromBytes(buf, typeClass.getParamSafe());

                    if (entry != null)
                    {
                        list.add(entry);
                    }
                }

                return (T) list;
            }
            else if (typeClass.getType() == ItemStack.class)
            {
                return (T) ByteBufUtils.readItemStack(buf);
            }
            else if (typeClass.getType() == DimensionalCoords.class)
            {
                return (T) new DimensionalCoords(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
            }
        }

        return null;
    }

    public static <T extends FiskRegistryEntry<T>> List<T> readNBTList(NBTTagCompound compound, String name, FiskSimpleRegistry<T> registry)
    {
        List<T> list = null;

        if (compound.hasKey(name, NBT.TAG_LIST))
        {
            NBTTagList tagList = compound.getTagList(name, NBT.TAG_STRING);
            list = Lists.newArrayList();

            for (int i = 0; i < tagList.tagCount(); ++i)
            {
                T entry = registry.getObject(tagList.getStringTagAt(i));

                if (entry != null)
                {
                    list.add(entry);
                }
            }
        }

        return list;
    }

    public static NBTTagCompound getCompound(String s)
    {
        try
        {
            NBTBase tag = JsonToNBT.func_150315_a(s);

            if (tag instanceof NBTTagCompound)
            {
                return (NBTTagCompound) tag;
            }
        }
        catch (NBTException e)
        {
        }

        return new NBTTagCompound();
    }

    public static <T extends ISerializableObject<T>> void registerAdapter(Class<? extends T> type, Class<? extends ISaveAdapter<T>> adapter)
    {
        try
        {
            ADAPTERS.put(type, adapter.newInstance());
        }
        catch (Exception e)
        {
            throw new RuntimeException("An unexpected error occurred while registering save adapter:", e);
        }
    }

    public interface ISerializableObject<T extends ISerializableObject<T>>
    {
        NBTBase writeToNBT();

        void toBytes(ByteBuf buf);
    }

    public interface ISaveAdapter<T extends ISerializableObject<T>>
    {
        T readFromNBT(NBTBase tag);

        T fromBytes(ByteBuf buf);
    }
}
