package com.fiskmods.lightsabers.common.hilt;

import java.util.Arrays;
import java.util.Collection;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;

public class HiltKnighted extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 12.6F).addCrossguard(0, 0.083F, 0.23F);
        parts[1] = new Part(PartType.SWITCH_SECTION, 8.4F);
        parts[2] = new Part(PartType.BODY, 20);
        parts[3] = new Part(PartType.POMMEL, 13.3F);
        
        return parts;
    }

    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.RED;
    }

    @Override
    public Collection<FocusingCrystal> getFocusingCrystals()
    {
        return Arrays.asList(FocusingCrystal.CRACKED);
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
