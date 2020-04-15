package com.fiskmods.lightsabers.common.lightsaber;

import java.util.Locale;
import java.util.Random;

import net.minecraft.util.StatCollector;

public enum CrystalColor
{
    DEEP_BLUE(0, 0x0000FF),
    MEDIUM_BLUE(1, 0x006BFF),
    LIGHT_BLUE(2, 0x59B9FF),
    ARCTIC_BLUE(3, 0xDDF6FF),
    WHITE(4, 0xFFFFFF),
    INDIGO(5, 0x5D00FF),
    PURPLE(6, 0xAD00AD),
    MAGENTA(7, 0xFF00FF),
    PINK(8, 0xFF8FBA),
    RED(9, 0xFF0000),
    BLOOD_ORANGE(10, 0xFF8000),
    AMBER(11, 0xFFB600),
    YELLOW(12, 0xFFFF00),
    GOLD(13, 0xFFFF3A),
    LIME_GREEN(14, 0xBFFF00),
    GREEN(15, 0x00FF00),
    MINT_GREEN(16, 0x00FF9B),
    CYAN(17, 0x00FFFF);
    
    public static final float[][] COLOR_VALUES = new float[values().length][3];
    
    public static final CrystalColor[] GROUP_BLUE = {DEEP_BLUE, MEDIUM_BLUE, LIGHT_BLUE, ARCTIC_BLUE, CYAN, INDIGO};
    public static final CrystalColor[] GROUP_PURPLE = {INDIGO, PURPLE, MAGENTA, PINK};
    public static final CrystalColor[] GROUP_RED = {PINK, RED, BLOOD_ORANGE};
    public static final CrystalColor[] GROUP_ORANGE = {BLOOD_ORANGE, AMBER};
    public static final CrystalColor[] GROUP_YELLOW = {AMBER, YELLOW, GOLD};
    public static final CrystalColor[] GROUP_GREEN = {LIME_GREEN, GREEN, MINT_GREEN, CYAN};
    
    public static final CrystalColor[] GROUP_COLD = {DEEP_BLUE, MEDIUM_BLUE, LIGHT_BLUE, ARCTIC_BLUE, WHITE, INDIGO, PURPLE, CYAN};
    public static final CrystalColor[] GROUP_HOT = {MAGENTA, PINK, RED, BLOOD_ORANGE, AMBER, YELLOW, GOLD};
    public static final CrystalColor[] GROUP_NEUTRAL = {PINK, LIME_GREEN, GREEN, MINT_GREEN, CYAN};

    public final int id;
    public final int color;

    CrystalColor(int id, int color)
    {
        this.id = id;
        this.color = color;
    }

    public String getUnlocalizedName()
    {
        return "lightsaber.color." + name().toLowerCase(Locale.ROOT);
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(getUnlocalizedName()).trim();
    }
    
    public float[] getRGB()
    {
        return COLOR_VALUES[ordinal()];
    }

    private static float[] getRGB(int hex)
    {
        float r = (float) ((hex & 0xFF0000) >> 16) / 255;
        float g = (float) ((hex & 0xFF00) >> 8) / 255;
        float b = (float) (hex & 0xFF) / 255;
        return new float[] {r, g, b};
    }

    public static CrystalColor get(int id)
    {
        return values()[Math.abs(id) % values().length];
    }

    public static CrystalColor getRandom(Random rand)
    {
        return values()[rand.nextInt(values().length)];
    }

    public static CrystalColor getRandom()
    {
        return getRandom(new Random());
    }
    
    static
    {
        for (CrystalColor color : values())
        {
            COLOR_VALUES[color.ordinal()] = getRGB(color.color);
        }
    }
}
