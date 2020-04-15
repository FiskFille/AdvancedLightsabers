package com.fiskmods.lightsabers.common.hilt;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

public class HiltProdigalSon extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 31);
        parts[1] = new Part(PartType.SWITCH_SECTION, 8.4F);
        parts[2] = new Part(PartType.BODY, 13.3F);
        parts[3] = new Part(PartType.POMMEL, 2);
        
        return parts;
    }
    
    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.GREEN;
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
