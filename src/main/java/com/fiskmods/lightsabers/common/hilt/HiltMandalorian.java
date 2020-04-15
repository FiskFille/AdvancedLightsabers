package com.fiskmods.lightsabers.common.hilt;

import java.util.Arrays;
import java.util.Collection;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;

public class HiltMandalorian extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 12.55F);
        parts[1] = new Part(PartType.SWITCH_SECTION, 2.87F);
        parts[2] = new Part(PartType.BODY, 26);
        parts[3] = new Part(PartType.POMMEL, 7.45F);
        
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
        return Arrays.asList(FocusingCrystal.INVERTING, FocusingCrystal.FINE_CUT);
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
