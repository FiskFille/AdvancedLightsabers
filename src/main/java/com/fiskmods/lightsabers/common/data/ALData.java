package com.fiskmods.lightsabers.common.data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerData;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.network.MessagePlayerData;
import com.fiskmods.lightsabers.helper.ALFormatHelper;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.helper.NBTHelper;
import fiskfille.utils.registry.FiskRegistryEntry;
import fiskfille.utils.registry.FiskRegistryNamespaced;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class ALData<T> extends FiskRegistryEntry<ALData<?>>
{
    public static final FiskRegistryNamespaced<ALData<?>> REGISTRY = new FiskRegistryNamespaced(Lightsabers.MODID, null);

    public static void register(String key, ALData value)
    {
        REGISTRY.putObject(key, value);
    }

    public static ALData getDataFromName(String key)
    {
        return REGISTRY.getObject(key);
    }

    public static String getNameForData(ALData value)
    {
        return REGISTRY.getNameForObject(value);
    }

    protected static final int SAVE_NBT = 0x1;
    protected static final int SYNC_BYTES = 0x2;
    protected static final int RESET_ON_DEATH = 0x4;
    protected static final int RESET_WO_PRED = 0x8;

    public static final ALData<Float> FORCE_XP = new ALData<Float>(0.0F).setExempt(RESET_ON_DEATH);
    public static final ALData<Byte> BASE_POWER = new ALData<Byte>((byte) -1).setExempt(RESET_ON_DEATH);
    public static final ALData<String> DRAINING_XP_TO = new ALData<String>("").setExempt(SAVE_NBT);
    public static final ALData<Integer> PREV_XP = new ALData<Integer>(0).setExempt(RESET_ON_DEATH);
    public static final ALData<Byte> SELECTED_POWER = new ALData<Byte>((byte) 0).setExempt(RESET_ON_DEATH);
    public static final ALData<Boolean> USING_POWER = new ALData<Boolean>(false);
    public static final ALData<Boolean> PREV_USING_POWER = new ALData<Boolean>(false);
    public static final ALData<Integer> TICKS_USING_POWER = new ALData<Integer>(0);
    public static final ALData<Integer> USE_POWER_COOLDOWN = new ALData<Integer>(0);
    public static final ALData<PowerData.Container> POWERS = new ALDataPowers().setExempt(RESET_ON_DEATH);
    public static final ALData<List<Power>> SELECTED_POWERS = new ALData<List<Power>>(Power.factory()).setExempt(RESET_ON_DEATH);
    public static final ALData<LightsaberData> LIGHTSABER = new ALData<LightsaberData>().setExempt(SAVE_NBT);
    
    public static final ALDataInterp<Float> FORCE_POWER = new ALDataInterp<Float>(0.0F);
    public static final ALDataInterp<Float> FORCE_POWER_DIFF = new ALDataInterp<Float>(0.0F).setExempt(SAVE_NBT);
    public static final ALDataInterp<Float> FORCE_PUSHING_TIMER = new ALDataInterp<Float>(0.0F);
    public static final ALDataInterp<Float> DRAIN_LIFE_TIMER = new ALDataInterp<Float>(0.0F);
    public static final ALDataInterp<Float> RIGHT_ARM_TIMER = new ALDataInterp<Float>(0.0F);
    public static final ALDataInterp<Float> LEFT_ARM_TIMER = new ALDataInterp<Float>(0.0F);

    final DataFactory<T> defaultValue;
    public final Predicate<Entity> canSet;

    public String id;
    public ClassType<T> typeClass;
    protected int flags = -1;
    protected int permissions = -1;

    protected ALData(DataFactory<T> defaultVal, Predicate<Entity> p)
    {
        defaultValue = defaultVal;
        canSet = p;
    }

    protected ALData(final T defaultVal, Predicate<Entity> p)
    {
        this(new DataFactory<T>()
        {
            @Override
            public T construct()
            {
                return defaultVal;
            }
        }, p);
    }

    protected ALData(DataFactory<T> defaultVal)
    {
        this(defaultVal, null);
    }

    protected ALData(T defaultVal)
    {
        this(defaultVal, null);
    }

    protected ALData(Predicate<Entity> p)
    {
        this((T) null, p);
    }

    protected ALData()
    {
        this((T) null);
    }

    protected ALData setExempt(int exempt)
    {
        flags &= ~exempt;
        return this;
    }

    protected ALData revokePerms(Side side)
    {
        permissions &= ~(1 << side.ordinal());
        return this;
    }

    protected ALData revokePerms()
    {
        revokePerms(Side.CLIENT);
        revokePerms(Side.SERVER);
        return this;
    }

    protected boolean hasFlag(int flags, int flag)
    {
        return (flags & flag) == flag;
    }

    public boolean shouldSaveNBT()
    {
        return hasFlag(flags, SAVE_NBT);
    }

    public boolean shouldSyncBytes()
    {
        return hasFlag(flags, SYNC_BYTES);
    }

    public boolean shouldResetOnDeath()
    {
        return hasFlag(flags, RESET_ON_DEATH);
    }

    public boolean shouldResetWithoutPredicate()
    {
        return hasFlag(flags, RESET_WO_PRED);
    }

    public boolean hasPerms(Side side)
    {
        return hasFlag(permissions, 1 << side.ordinal());
    }

    public Predicate<Entity> isValue(final T value)
    {
        return new Predicate<Entity>()
        {
            @Override
            public boolean apply(Entity input)
            {
                return Objects.equal(value, get(input));
            }
        };
    }

    public boolean ofType(Class clazz)
    {
        return typeClass.getType() == clazz;
    }

    protected boolean validUpdate(Entity entity, T value)
    {
        if (ofType(ItemStack.class))
        {
            return !ItemStack.areItemStacksEqual((ItemStack) get(entity), (ItemStack) value);
        }

        return !Objects.equal(value, get(entity));
    }

    protected boolean legalUpdate(Entity entity)
    {
        return canSet == null || canSet.apply(entity);
    }

    public T getDefault()
    {
        return defaultValue.construct();
    }

    public boolean set(Entity entity, T value)
    {
        if (entity instanceof IDataHolder)
        {
            return setWithoutNotify(entity, value);
        }
        else if (entity instanceof EntityPlayer)
        {
            return set((EntityPlayer) entity, value);
        }

        return false;
    }

    public boolean setWithoutNotify(Entity entity, T value)
    {
        return legalUpdate(entity) && setWithBypass(entity, value);
    }

    private boolean setWithBypass(Entity entity, T value)
    {
        if (validUpdate(entity, value))
        {
            if (entity instanceof IDataHolder)
            {
                ((IDataHolder) entity).set(this, value);
                onValueChanged(entity, value);
            }
            else if (entity instanceof EntityPlayer)
            {
                ALPlayerData.getData((EntityPlayer) entity).putData(this, value);
                onValueChanged(entity, value);
            }

            return true;
        }

        return false;
    }

    private boolean reset(Entity entity)
    {
        return setWithBypass(entity, getDefault());
    }

    public boolean incr(Entity entity, T value)
    {
        if (entity instanceof IDataHolder)
        {
            return incrWithoutNotify(entity, value);
        }
        else if (entity instanceof EntityPlayer)
        {
            return incr((EntityPlayer) entity, value);
        }

        return false;
    }

    public boolean incrWithoutNotify(Entity entity, T value)
    {
        if (value instanceof Byte)
        {
            return setWithoutNotify(entity, (T) Byte.valueOf((byte) ((Byte) get(entity) + (Byte) value)));
        }
        else if (value instanceof Short)
        {
            return setWithoutNotify(entity, (T) Short.valueOf((short) ((Short) get(entity) + (Short) value)));
        }
        else if (value instanceof Integer)
        {
            return setWithoutNotify(entity, (T) Integer.valueOf((Integer) get(entity) + (Integer) value));
        }
        else if (value instanceof Long)
        {
            return setWithoutNotify(entity, (T) Long.valueOf((Long) get(entity) + (Long) value));
        }
        else if (value instanceof Float)
        {
            return setWithoutNotify(entity, (T) Float.valueOf((Float) get(entity) + (Float) value));
        }
        else if (value instanceof Double)
        {
            return setWithoutNotify(entity, (T) Double.valueOf((Double) get(entity) + (Double) value));
        }
        else if (value instanceof String)
        {
            return setWithoutNotify(entity, (T) String.valueOf((String) get(entity) + (String) value));
        }

        throw new RuntimeException("Cannot increment a non-numerical data type unless a String!");
    }

    public boolean clamp(Entity entity, T min, T max)
    {
        if (entity instanceof IDataHolder)
        {
            return clampWithoutNotify(entity, min, max);
        }
        else if (entity instanceof EntityPlayer)
        {
            return clamp((EntityPlayer) entity, min, max);
        }

        return false;
    }

    public boolean clampWithoutNotify(Entity entity, T min, T max)
    {
        if (min instanceof Byte)
        {
            return setWithoutNotify(entity, (T) Byte.valueOf((byte) MathHelper.clamp_int((Byte) get(entity), (Byte) min, (Byte) max)));
        }
        else if (min instanceof Short)
        {
            return setWithoutNotify(entity, (T) Short.valueOf((short) MathHelper.clamp_int((Short) get(entity), (Short) min, (Short) max)));
        }
        else if (min instanceof Integer)
        {
            return setWithoutNotify(entity, (T) Integer.valueOf(MathHelper.clamp_int((Integer) get(entity), (Integer) min, (Integer) max)));
        }
        else if (min instanceof Long)
        {
            long l = (Long) get(entity);
            return setWithoutNotify(entity, (T) Long.valueOf(l < (Long) min ? (Long) min : l > (Long) max ? (Long) max : l));
        }
        else if (min instanceof Float)
        {
            return setWithoutNotify(entity, (T) Float.valueOf(MathHelper.clamp_float((Float) get(entity), (Float) min, (Float) max)));
        }
        else if (min instanceof Double)
        {
            return setWithoutNotify(entity, (T) Double.valueOf(MathHelper.clamp_double((Double) get(entity), (Double) min, (Double) max)));
        }

        throw new RuntimeException("Cannot clamp a non-numerical data type!");
    }

    public T get(Entity entity)
    {
        if (entity instanceof IDataHolder)
        {
            return ((IDataHolder) entity).get(this);
        }
        else if (entity instanceof EntityPlayer)
        {
            return ALPlayerData.getData((EntityPlayer) entity).getData(this);
        }

        return getDefault();
    }

    public boolean sync(EntityPlayer player)
    {
        if (hasPerms(player.worldObj.isRemote ? Side.CLIENT : Side.SERVER) && legalUpdate(player))
        {
            if (player.worldObj.isRemote)
            {
                ALNetworkManager.wrapper.sendToServer(new MessagePlayerData(player, this, get(player)));
            }
            else
            {
                ALNetworkManager.wrapper.sendToDimension(new MessagePlayerData(player, this, get(player)), player.dimension);
            }

            return true;
        }

        return false;
    }

    public boolean set(EntityPlayer player, T value)
    {
        return setWithoutNotify(player, value) && sync(player);
    }

    public boolean incr(EntityPlayer player, T value)
    {
        return incrWithoutNotify(player, value) && sync(player);
    }

    public boolean clamp(EntityPlayer player, T min, T max)
    {
        return clampWithoutNotify(player, min, max) && sync(player);
    }

    public static NBTTagCompound writeToNBT(NBTTagCompound nbt, Map<ALData, Object> data)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        for (Map.Entry<ALData, Object> e : data.entrySet())
        {
            if (e.getKey().shouldSaveNBT())
            {
                NBTBase nbtbase = NBTHelper.writeToNBT(e.getValue());

                if (nbtbase != null)
                {
                    nbttagcompound.setTag(e.getKey().id, nbtbase);
                }
            }
        }

        nbt.setTag("DataArray", nbttagcompound);

        return nbt;
    }

    public static Map<ALData, Object> readFromNBT(NBTTagCompound nbt, Map<ALData, Object> data)
    {
        NBTTagCompound nbttagcompound = nbt.getCompoundTag("DataArray");

        for (ALData type : ALData.REGISTRY)
        {
            if (type.shouldSaveNBT())
            {
                NBTBase tag = nbttagcompound.getTag(type.id);
                Object obj = null;

                if (tag != null)
                {
                    obj = NBTHelper.readFromNBT(tag, type.typeClass);
                }
                else
                {
                    tag = nbt.getTag(type.id);

                    if (tag != null)
                    {
                        obj = NBTHelper.readFromNBT(tag, type.typeClass);
                    }
                    else
                    {
                        continue;
                    }
                }

                data.put(type, obj);
            }
        }

        return data;
    }

    public static void toBytes(ByteBuf buf, Map<ALData, Object> data)
    {
        buf.writeInt(ALData.REGISTRY.getKeys().size());

        for (ALData type : ALData.REGISTRY)
        {
            if (type.shouldSyncBytes())
            {
                boolean flag = data.containsKey(type);

                if (flag && type.defaultValue.canEqual())
                {
                    Object a = type.getDefault();
                    Object b = data.get(type);

                    flag = !Objects.equal(a, b);
                }

                buf.writeBoolean(flag);

                if (flag)
                {
                    NBTHelper.toBytes(buf, data.get(type));
                }
            }
        }
    }

    public static Map<ALData, Object> fromBytes(ByteBuf buf, Map<ALData, Object> data)
    {
        int size = buf.readInt();

        if (size != ALData.REGISTRY.getKeys().size())
        {
            throw new RuntimeException(String.format("Incompatible data registries - this is really bad! Received %s, expected: %s", size, ALData.REGISTRY.getKeys().size()));
        }

        for (ALData type : ALData.REGISTRY)
        {
            if (type.shouldSyncBytes() && buf.readBoolean())
            {
                data.put(type, NBTHelper.fromBytes(buf, type.typeClass));
            }
        }

        return data;
    }

    public static void onUpdate(Entity entity)
    {
        for (ALData data : ALData.REGISTRY)
        {
            data.update(entity);
            
            if (data.shouldResetWithoutPredicate() && !data.legalUpdate(entity))
            {
                data.reset(entity);
            }
        }
    }

    public static void onDeath(Entity entity)
    {
        for (ALData data : ALData.REGISTRY)
        {
            if (data.shouldResetOnDeath())
            {
                data.reset(entity);
            }
        }
    }

    protected void init(Field field, String name) throws ClassNotFoundException
    {
        id = ALFormatHelper.getUnconventionalName(name);
        typeClass = ClassType.construct(field.getGenericType().getTypeName()).getParam();

        register(name.toLowerCase(Locale.ROOT), this);
    }
    
    protected void update(Entity entity)
    {
    }

    protected void onValueChanged(Entity entity, T value)
    {
    }

    @Override
    public String toString()
    {
        return id;
    }

    public static void init()
    {
        for (Field field : ALData.class.getFields())
        {
            if (ALData.class.isAssignableFrom(field.getType()))
            {
                try
                {
                    ((ALData) field.get(null)).init(field, field.getName());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ClassType<C>
    {
        private final Class<C> type;
        private ClassType param;

        public ClassType(Class<C> c)
        {
            type = c;
        }

        private ClassType<C> setParam(ClassType type)
        {
            param = type;
            return this;
        }

        public ClassType getParam()
        {
            return param;
        }

        public ClassType getParamSafe()
        {
            return param == null ? new ClassType(Object.class) : param;
        }

        public Class<C> getType()
        {
            return type;
        }

        @Override
        public String toString()
        {
            String s = getType().getCanonicalName();

            if (getParam() != null)
            {
                return s + "<" + getParam() + ">";
            }

            return s;
        }

        private static ClassType construct(String typeName) throws ClassNotFoundException
        {
            if (typeName.contains("<"))
            {
                ClassType type = new ClassType(Class.forName(typeName.substring(0, typeName.indexOf('<'))));
                ClassType type1 = construct(typeName.substring(typeName.indexOf('<') + 1, typeName.length() - 1));

                return type.setParam(type1);
            }

            return new ClassType(Class.forName(typeName));
        }
    }

    public static abstract class DataFactory<T>
    {
        public abstract T construct();

        public boolean canEqual()
        {
            return true;
        }
    }
}
