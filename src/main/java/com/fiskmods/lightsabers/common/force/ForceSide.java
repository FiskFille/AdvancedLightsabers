package com.fiskmods.lightsabers.common.force;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.util.EnumChatFormatting;

public enum ForceSide
{
    NONE(EnumChatFormatting.WHITE),
    LIGHT(EnumChatFormatting.AQUA),
    DARK(EnumChatFormatting.RED),
    NEUTRAL(EnumChatFormatting.YELLOW);

    public final EnumChatFormatting theme;
    public final Set<Power> powers = Sets.newHashSet();

    private ForceSide(EnumChatFormatting formatting)
    {
        theme = formatting;
    }
    
    public Power getPower()
    {
        switch (this)
        {
        case LIGHT:
            return Power.LIGHT_SIDE;
        case DARK:
            return Power.DARK_SIDE;
        case NEUTRAL:
            return Power.NEUTRAL;
        default:
            return null;
        }
    }

    public ForceSide getOpposite()
    {
        switch (this)
        {
        case LIGHT:
            return DARK;
        case DARK:
            return LIGHT;
        default:
            return this;
        }
    }

    public boolean isPolar()
    {
        return this == LIGHT || this == DARK;
    }
}
