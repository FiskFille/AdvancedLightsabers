package com.fiskmods.lightsabers.common.hilt;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

public class HiltVaid extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 18.5F);
        parts[1] = new Part(PartType.SWITCH_SECTION, 9.2F);
        parts[2] = new Part(PartType.BODY, 22.37F);
        parts[3] = new Part(PartType.POMMEL, 6.6F);
        
        return parts;
    }
    
    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.PURPLE;
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
