package com.fiskmods.lightsabers.common.hilt;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

public class HiltDroideka extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 5);
        parts[1] = new Part(PartType.SWITCH_SECTION, 9.6F);
        parts[2] = new Part(PartType.BODY, 29.4F);
        parts[3] = new Part(PartType.POMMEL, 6.5F);
        
        return parts;
    }
    
    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.AMBER;
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
