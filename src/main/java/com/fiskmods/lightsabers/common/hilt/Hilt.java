package com.fiskmods.lightsabers.common.hilt;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

import fiskfille.utils.registry.FiskRegistryEntry;
import fiskfille.utils.registry.FiskRegistryNamespaced;
import net.minecraft.client.resources.I18n;

public abstract class Hilt extends FiskRegistryEntry<Hilt>
{
    public static final int MAX_ID = 0x3F;
    public static final FiskRegistryNamespaced<Hilt> REGISTRY = new FiskRegistryNamespaced(Lightsabers.MODID, "graflex").setMaxId(MAX_ID);

    public static final Map<String, String> LEGACY_MAPPINGS = new HashMap<>();

    public static void register(int id, String key, Hilt value)
    {
        REGISTRY.addObject(id, key, value);
    }

    public static void register(String key, Hilt value)
    {
        REGISTRY.putObject(key, value);
    }

    public static Hilt getHiltFromName(String key)
    {
        return REGISTRY.getObject(key);
    }

    public static String getNameForHilt(Hilt value)
    {
        return REGISTRY.getNameForObject(value);
    }

    public static int getIdFromHilt(Hilt value)
    {
        return value == null ? 0 : REGISTRY.getIDForObject(value);
    }

    public static Hilt getHiltById(int id)
    {
        return REGISTRY.getObjectById(id);
    }

    public abstract Part[] getParts();

    public abstract CrystalColor getColor();

    public Type getType()
    {
        return Type.SINGLE;
    }

    public Collection<FocusingCrystal> getFocusingCrystals()
    {
        return Arrays.asList();
    }

    public String getUnlocalizedName()
    {
        return "hilt." + delegate.name().replace(':', '.') + ".name";
    }

    public String getLocalizedName()
    {
        return I18n.format(getUnlocalizedName()).trim();
    }
    
    public final Part getPart(PartType type)
    {
        return getParts()[type.ordinal()];
    }
    
    public final LightsaberData createDefault()
    {
        return new LightsaberData().set(this).set(getColor()).set(getFocusingCrystals().toArray(new FocusingCrystal[0]));
    }

    public static class Part
    {
        public PartType type;
        public float height;
        public float[] glInstructions;

        public float[] crossguard;
        
        public Part(PartType type, float height, float... instructions)
        {
            this.type = type;
            this.height = height;
            glInstructions = instructions;
        }
        
        public Part addCrossguard(float x, float y, float z)
        {
            crossguard = new float[] {x, y, z};
            return this;
        }
        
        public boolean hasCrossguard()
        {
            return crossguard != null;
        }
    }

    public enum Type
    {
        SINGLE,
        DOUBLE;
    }
}
