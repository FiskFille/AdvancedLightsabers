package com.fiskmods.lightsabers.common.hilt;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

public class HiltGraflex extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 16);
        parts[1] = new Part(PartType.SWITCH_SECTION, 8.8F);
        parts[2] = new Part(PartType.BODY, 16);
        parts[3] = new Part(PartType.POMMEL, 1);
        
        return parts;
    }
    
    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.DEEP_BLUE;
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
