package com.fiskmods.lightsabers.common.hilt;

import java.util.Arrays;
import java.util.Collection;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;

public class HiltFulcrum extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 19);
        parts[1] = new Part(PartType.SWITCH_SECTION, 10);
        parts[2] = new Part(PartType.BODY, 30, 11.7F, 2, 9.8F, 3, 9);
        parts[3] = new Part(PartType.POMMEL, 7);
        
        return parts;
    }
    
    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.WHITE;
    }

    @Override
    public Collection<FocusingCrystal> getFocusingCrystals()
    {
        return Arrays.asList(FocusingCrystal.COMPRESSED);
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
