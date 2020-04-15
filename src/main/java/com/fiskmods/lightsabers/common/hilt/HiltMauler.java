package com.fiskmods.lightsabers.common.hilt;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

public class HiltMauler extends Hilt
{
    public Part[] parts = makeParts();
    
    private Part[] makeParts()
    {
        Part[] parts = new Part[4];
        parts[0] = new Part(PartType.EMITTER, 18);
        parts[1] = new Part(PartType.SWITCH_SECTION, 12);
        parts[2] = new Part(PartType.BODY, 21.6F);
        parts[3] = new Part(PartType.POMMEL, 0.25F);
        
        return parts;
    }
    
    @Override
    public CrystalColor getColor()
    {
        return CrystalColor.RED;
    }

    @Override
    public Type getType()
    {
        return Type.DOUBLE;
    }

    @Override
    public Part[] getParts()
    {
        return parts;
    }
}
