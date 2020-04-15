package com.fiskmods.lightsabers.common.lightsaber;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.hilt.Hilt.Part;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ItemFocusingCrystal;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.saberbuilder.AbstractLightsaberData;
import com.google.common.collect.Lists;

import fiskfille.utils.helper.FiskComparators;
import fiskfille.utils.helper.NBTHelper.ISaveAdapter;
import fiskfille.utils.helper.NBTHelper.ISerializableObject;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLong;
import net.minecraftforge.common.util.Constants.NBT;

public class LightsaberData extends AbstractLightsaberData implements ISerializableObject<LightsaberData>
{
    public static final LightsaberData EMPTY = new LightsaberData();
    public static final float MIN_LENGTH_CM = 19;

    public LightsaberData()
    {
        this(0);
    }

    public LightsaberData(long hashCode)
    {
        hash = hashCode;
    }

    @Override
    protected int getIDForObject(Hilt hilt)
    {
        return Hilt.REGISTRY.getIDForObject(hilt);
    }

    @Override
    protected Hilt getObjectById(int id)
    {
        return Hilt.REGISTRY.getObjectById(id);
    }

    @Override
    protected LightsaberData createNew(long hashCode)
    {
        return new LightsaberData(hashCode);
    }

    /**
     * Creates a new LightsaberData object identical to this one.
     *
     * @return this
     */
    @Override
    public LightsaberData copy()
    {
        return (LightsaberData) super.copy();
    }

    /**
     * Strips this object's hashCode of any unused bits.
     *
     * @return this
     */
    @Override
    public LightsaberData strip()
    {
        return (LightsaberData) super.strip();
    }

    /**
     * @return true if this hilt is shorter than allowed.
     */
    public boolean isTooShort()
    {
        return getHeightCm() < MIN_LENGTH_CM;
    }

    /**
     * @param itemstack - the {@link ItemStack} representing this lightsaber
     * @return An array containing the RGB components for the blade of this lightsaber.
     */
    public float[] getRGB(ItemStack itemstack)
    {
        if (itemstack.getDisplayName().equals("jeb_"))
        {
            EntityPlayer player = Lightsabers.proxy.getPlayer();
            int time = 25;

            float[][] rgb = CrystalColor.COLOR_VALUES;
            float f = (player.ticksExisted % time + Lightsabers.proxy.getRenderTick()) / time;
            int i = player.ticksExisted / time;
            int j = i % rgb.length;
            int k = (i + 1) % rgb.length;

            return new float[] {rgb[j][0] * (1 - f) + rgb[k][0] * f, rgb[j][1] * (1 - f) + rgb[k][1] * f, rgb[j][2] * (1 - f) + rgb[k][2] * f};
        }

        return getColor().getRGB();
    }

    /**
     * Sets the {@link Hilt} design of the lightsaber component in the given {@link PartType} slot.
     *
     * @param type - The component slot
     * @param id - The hilt design
     * @return this
     */
    @Override
    public LightsaberData set(PartType type, Hilt hilt)
    {
        return (LightsaberData) super.set(type, hilt);
    }

    /**
     * Sets the hilt design of each component in this lightsaber to the entry in the given array
     * corresponding to that {@link PartType PartType's} index. <br>
     * <br>
     * Index 0 = {@link PartType#EMITTER emitter} <br>
     * Index 1 = {@link PartType#SWITCH_SECTION switch section} <br>
     * Index 2 = {@link PartType#BODY body} <br>
     * Index 3 = {@link PartType#POMMEL pommel} <br>
     *
     * @param hilt - The array of {@link Hilt} designs
     * @return this
     */
    @Override
    public LightsaberData set(Hilt... hilt)
    {
        return (LightsaberData) super.set(hilt);
    }

    /**
     * Sets the design of all components in this lightsaber to the given {@link Hilt}.
     *
     * @param hilt - The new hilt design
     * @return this
     * @see LightsaberData#isHiltUniform()
     */
    @Override
    public LightsaberData set(Hilt hilt)
    {
        return (LightsaberData) super.set(hilt);
    }

    /**
     * Sets the color of this lightsaber's blade.
     *
     * @param color - The {@link CrystalColor} to represent the blade's color
     * @return this
     */
    @Override
    public LightsaberData set(CrystalColor color)
    {
        return (LightsaberData) super.set(color);
    }

    /**
     * Sets the {@link FocusingCrystal focusing crystals} contained within this lightsaber. Removes
     * any previously contained crystals and passes down to
     * {@link LightsaberData#add(FocusingCrystal)}.
     *
     * @param crystals - The new array of crystals
     * @return this
     */
    @Override
    public LightsaberData set(FocusingCrystal... crystals)
    {
        return (LightsaberData) super.set(crystals);
    }

    /**
     * Adds a {@link FocusingCrystal} to the lightsaber.
     *
     * @param crystal - The crystal to be added
     * @return this
     */
    @Override
    public LightsaberData add(FocusingCrystal crystal)
    {
        return (LightsaberData) super.add(crystal);
    }

    /**
     * Removes the specified {@link FocusingCrystal}, if present.
     *
     * @param crystal - The crystal to be removed
     * @return this
     */
    @Override
    public LightsaberData remove(FocusingCrystal crystal)
    {
        return (LightsaberData) super.remove(crystal);
    }

    /**
     * Creates a new {@link ItemStack} representing this lightsaber.
     *
     * @return The new item stack.
     */
    public ItemStack create()
    {
        ItemStack itemstack = new ItemStack(ModItems.lightsaber);
        itemstack.setTagCompound(new NBTTagCompound());
        itemstack.getTagCompound().setLong(ALConstants.TAG_LIGHTSABER, strip().hash);

        return itemstack;
    }

    @Override
    public NBTBase writeToNBT()
    {
        return new NBTTagLong(hash);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(hash);
    }

    public static class Adapter implements ISaveAdapter<LightsaberData>
    {
        @Override
        public LightsaberData readFromNBT(NBTBase tag)
        {
            if (tag instanceof NBTPrimitive)
            {
                return new LightsaberData(((NBTPrimitive) tag).func_150291_c());
            }

            return null;
        }

        @Override
        public LightsaberData fromBytes(ByteBuf buf)
        {
            return new LightsaberData(buf.readLong());
        }
    }

    /**
     * Creates a new LightsaberData object to represent the data contained within the passed
     * {@link NBTTagCompound NBT}.
     *
     * @param nbt - The tag compound containing the data
     * @return A new object, or {@link LightsaberData#EMPTY} if nbt contains no relevant data.
     * @see ItemStack#getTagCompound()
     */
    public static LightsaberData readFromNBT(NBTTagCompound nbt)
    {
        if (nbt.hasKey("Lightsaber", NBT.TAG_COMPOUND))
        {
            NBTTagCompound compound = nbt.getCompoundTag("Lightsaber");
            LightsaberData data = new LightsaberData().set(CrystalColor.get(compound.getInteger("color")));

            if (compound.hasKey("FocusingCrystals", NBT.TAG_INT_ARRAY))
            {
                for (int id : compound.getIntArray("FocusingCrystals"))
                {
                    data.add(ItemFocusingCrystal.get(id));
                }
            }

            for (PartType type : PartType.values())
            {
                data.set(type, Hilt.REGISTRY.getObject(Hilt.LEGACY_MAPPINGS.get(compound.getString(type.name().toLowerCase(Locale.ROOT)))));
            }
            
            nbt.removeTag("Lightsaber");
            nbt.setLong(ALConstants.TAG_LIGHTSABER, data.hash);

            return data;
        }
        else if (nbt.hasKey(ALConstants.TAG_LIGHTSABER, NBT.TAG_ANY_NUMERIC))
        {
            return new LightsaberData(nbt.getLong(ALConstants.TAG_LIGHTSABER)).strip();
        }
        else if (nbt.hasKey(ALConstants.TAG_LIGHTSABER, NBT.TAG_STRING))
        {
            try
            {
                return new LightsaberData(Long.decode(nbt.getString(ALConstants.TAG_LIGHTSABER))).strip();
            }
            catch (NumberFormatException e)
            {
            }
        }

        return EMPTY;
    }

    /**
     * Retrieves a LightsaberData object representing the given {@link ItemStack}.
     *
     * @param itemstack - The item stack to be represented
     * @return The LightsaberData representation.
     * @see LightsaberData#readFromNBT(NBTTagCompound)
     */
    public static LightsaberData get(ItemStack itemstack)
    {
        if (itemstack != null && itemstack.hasTagCompound())
        {
            return readFromNBT(itemstack.getTagCompound());
        }

        return EMPTY;
    }

    /**
     * @see LightsaberData#get(PartType)
     * @see LightsaberData#get(ItemStack)
     */
    public static Hilt get(ItemStack itemstack, PartType type)
    {
        return get(itemstack).get(type);
    }

    /**
     * @see LightsaberData#getHilt(ItemStack)
     * @see LightsaberData#get(ItemStack)
     */
    public static Hilt[] getHilt(ItemStack itemstack)
    {
        return get(itemstack).getHilt();
    }

    /**
     * @see LightsaberData#getPart(PartType)
     * @see LightsaberData#get(ItemStack)
     */
    public static Part getPart(ItemStack itemstack, PartType type)
    {
        return get(itemstack).getPart(type);
    }

    /**
     * @see LightsaberData#getHeight()
     * @see LightsaberData#get(ItemStack)
     */
    public static float getHeight(ItemStack itemstack)
    {
        if (itemstack.getItem() == ModItems.doubleLightsaber)
        {
            LightsaberData[] array = ItemDoubleLightsaber.get(itemstack);
            return array[0].getHeight() + array[1].getHeight();
        }

        return get(itemstack).getHeight();
    }

    /**
     * @see LightsaberData#getHeightCm()
     * @see LightsaberData#get(ItemStack)
     */
    public static float getHeightCm(ItemStack itemstack)
    {
        return getHeight(itemstack) * 0.575F;
    }

    /**
     * @see LightsaberData#getColor()
     * @see LightsaberData#get(ItemStack)
     */
    public static CrystalColor getColor(ItemStack itemstack)
    {
        return get(itemstack).getColor();
    }

    /**
     * @see LightsaberData#getFocusingCrystals()
     * @see LightsaberData#get(ItemStack)
     */
    public static FocusingCrystal[] getFocusingCrystals(ItemStack itemstack)
    {
        return get(itemstack).getFocusingCrystals();
    }

    /**
     * @see LightsaberData#hasFocusingCrystal(FocusingCrystal)
     * @see LightsaberData#get(ItemStack)
     */
    public static boolean hasFocusingCrystal(ItemStack itemstack, FocusingCrystal crystal)
    {
        return get(itemstack).hasFocusingCrystal(crystal);
    }

    /**
     * Creates a new random lightsaber {@link ItemStack} of the given color.
     *
     * @param rand - The random instance
     * @param color - The blade color
     * @return The new item
     */
    public static ItemStack createRandom(Random rand, CrystalColor color)
    {
        Hilt[] hilt = new Hilt[4];

        for (int i = 0; i < 4; ++i)
        {
            hilt[i] = Hilt.REGISTRY.getRandom(rand);
        }

        LightsaberData data = new LightsaberData().set(hilt);

        if (data.isTooShort())
        {
            return createRandom(rand, color);
        }

        if (color == null)
        {
            color = CrystalColor.getRandom(rand);
        }

        data.set(color);

        if (rand.nextInt(10) == 0)
        {
            List<FocusingCrystal> crystals = Lists.newArrayList(FocusingCrystal.values());
            Collections.sort(crystals, FiskComparators.random(rand));

            data.add(crystals.get(0));

            if (rand.nextInt(20) == 0)
            {
                data.add(crystals.get(1));
            }
        }

        return data.create();
    }

    /**
     * Creates a new random lightsaber {@link ItemStack} of a random color.
     *
     * @param rand - The random instance
     * @return The new item
     * @see LightsaberData#createRandom(Random)
     */
    public static ItemStack createRandom(Random rand)
    {
        return createRandom(rand, null);
    }
}
