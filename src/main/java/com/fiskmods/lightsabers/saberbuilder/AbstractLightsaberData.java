package com.fiskmods.lightsabers.saberbuilder;

import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.hilt.Hilt.Part;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

public abstract class AbstractLightsaberData
{
    public long hash;

    public AbstractLightsaberData()
    {
        this(0);
    }

    public AbstractLightsaberData(long hashCode)
    {
        hash = hashCode;
    }
    
    protected abstract int getIDForObject(Hilt hilt);
    
    protected abstract Hilt getObjectById(int id);
    
    protected abstract AbstractLightsaberData createNew(long hashCode);

    /**
     * Creates a new LightsaberData object identical to this one.
     *
     * @return this
     */
    public AbstractLightsaberData copy()
    {
        return createNew(hash);
    }

    /**
     * Strips this object's hashCode of any unused bits.
     *
     * @return this
     */
    public AbstractLightsaberData strip()
    {
        hash = createNew(0).set(getHilt()).set(getColor()).set(getFocusingCrystals()).hash;
        return this;
    }

    /**
     * @param type - The {@link PartType} slot of the piece
     * @return The {@link Hilt} design of the specified part of this lightsaber.
     */
    public Hilt get(PartType type)
    {
        return getObjectById((int) (hash >> type.ordinal() * 6) & Hilt.MAX_ID);
    }

    /**
     * @return An array containing all {@link Hilt} parts.
     */
    public Hilt[] getHilt()
    {
        Hilt[] hilt = new Hilt[4];

        for (int i = 0; i < hilt.length; ++i)
        {
            hilt[i] = get(PartType.values()[i]);
        }

        return hilt;
    }

    /**
     * @return true if all {@link Hilt} parts belong to the same set.
     * @see AbstractLightsaberData#set(Hilt)
     */
    public boolean isHiltUniform()
    {
        Hilt[] hilt = getHilt();

        for (int i = 0; i < hilt.length; ++i)
        {
            if (i > 0 && hilt[i - 1] != hilt[i])
            {
                return false;
            }
        }

        return true;
    }

    /**
     * @return The bits representing the four hilt pieces.
     */
    public long getHiltBits()
    {
        return hash & 0xFFFFFF;
    }

    /**
     * @return The {@link Part} object representing the specified component of this lightsaber.
     */
    public Part getPart(PartType type)
    {
        return get(type).getPart(type);
    }

    /**
     * @return The height of this hilt in pixels.
     */
    public float getHeight()
    {
        float height = 0;

        for (PartType type : PartType.values())
        {
            height += getPart(type).height;
        }

        return height;
    }

    /**
     * @return The height of this hilt in centimeters.
     */
    public float getHeightCm()
    {
        return getHeight() * 0.575F;
    }

    /**
     * @return The {@link CrystalColor color of crystal} used in this lightsaber.
     */
    public CrystalColor getColor()
    {
        return CrystalColor.get((int) (hash >> 24 & 0xFF));
    }

    /**
     * @return An array containing the {@link FocusingCrystal focusing crystals} present in this
     *         lightsaber.
     */
    public FocusingCrystal[] getFocusingCrystals()
    {
        FocusingCrystal[] crystals = new FocusingCrystal[FocusingCrystal.values().length];
        int i = -1;

        for (FocusingCrystal crystal : FocusingCrystal.values())
        {
            if (hasFocusingCrystal(crystal))
            {
                crystals[++i] = crystal;
            }
        }

        FocusingCrystal[] array = new FocusingCrystal[++i];

        if (array.length > 0)
        {
            System.arraycopy(crystals, 0, array, 0, i);
        }

        return array;
    }

    /**
     * @param crystal - The {@link FocusingCrystal} to be queried
     * @return true if this lightsaber contains the specified focusing crystal.
     */
    public boolean hasFocusingCrystal(FocusingCrystal crystal)
    {
        return (hash >> 32 & crystal.getCode()) == crystal.getCode();
    }

    /**
     * Sets the {@link Hilt} design of the lightsaber component in the given {@link PartType} slot.
     *
     * @param type - The component slot
     * @param id - The hilt design
     * @return this
     */
    public AbstractLightsaberData set(PartType type, Hilt hilt)
    {
        int shift = type.ordinal() * 6;
        hash &= ~(0x3FL << shift);
        hash |= getIDForObject(hilt) << shift;

        return this;
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
    public AbstractLightsaberData set(Hilt... hilt)
    {
        for (int i = 0; i < Math.min(PartType.values().length, hilt.length); ++i)
        {
            set(PartType.values()[i], hilt[i]);
        }

        return this;
    }

    /**
     * Sets the design of all components in this lightsaber to the given {@link Hilt}.
     *
     * @param hilt - The new hilt design
     * @return this
     * @see AbstractLightsaberData#isHiltUniform()
     */
    public AbstractLightsaberData set(Hilt hilt)
    {
        return set(hilt, hilt, hilt, hilt);
    }

    /**
     * Sets the color of this lightsaber's blade.
     *
     * @param color - The {@link CrystalColor} to represent the blade's color
     * @return this
     */
    public AbstractLightsaberData set(CrystalColor color)
    {
        hash &= ~(0xFFL << 24);
        hash |= (color.id & 0xFF) << 24;

        return this;
    }

    /**
     * Sets the {@link FocusingCrystal focusing crystals} contained within this lightsaber. Removes
     * any previously contained crystals and passes down to
     * {@link AbstractLightsaberData#add(FocusingCrystal)}.
     *
     * @param crystals - The new array of crystals
     * @return this
     */
    public AbstractLightsaberData set(FocusingCrystal... crystals)
    {
        hash &= ~(0xFFFFL << 32);

        for (FocusingCrystal crystal : crystals)
        {
            if (crystal != null)
            {
                add(crystal);
            }
        }

        return this;
    }

    /**
     * Adds a {@link FocusingCrystal} to the lightsaber.
     *
     * @param crystal - The crystal to be added
     * @return this
     */
    public AbstractLightsaberData add(FocusingCrystal crystal)
    {
        hash |= crystal.getCode() << 32;
        return this;
    }

    /**
     * Removes the specified {@link FocusingCrystal}, if present.
     *
     * @param crystal - The crystal to be removed
     * @return this
     */
    public AbstractLightsaberData remove(FocusingCrystal crystal)
    {
        hash &= ~(crystal.getCode() << 32);
        return this;
    }

    @Override
    public int hashCode()
    {
        return (int) (hash & 0xFFFFFFF) * 31 + (int) (hash >> 32);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof AbstractLightsaberData && hash == ((AbstractLightsaberData) obj).hash;
    }
}
