package com.fiskmods.lightsabers.common.lightsaber;

import java.util.Locale;

import net.minecraft.util.StatCollector;

public enum FocusingCrystal
{
    COMPRESSED,
    CRACKED,
    INVERTING,
    FINE_CUT,
//    CHARGED,
    PRISMATIC;
    
    public long getCode()
    {
        return (1 << ordinal()) & 0xFFFF;
    }

    public String getUnlocalizedName()
    {
        return "lightsaber.focusingCrystal." + name().toLowerCase(Locale.ROOT);
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName()).trim();
    }
}
