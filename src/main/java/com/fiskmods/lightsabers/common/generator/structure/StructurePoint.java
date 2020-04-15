package com.fiskmods.lightsabers.common.generator.structure;

import net.minecraft.util.ChunkCoordinates;

public class StructurePoint extends ChunkCoordinates
{
    public StructurePoint(int x, int y, int z)
    {
        super(x, y, z);
    }

    public StructurePoint(StructurePoint p)
    {
        super(p);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof StructurePoint))
        {
            return false;
        }
        else
        {
            StructurePoint chunkcoordinates = (StructurePoint) obj;
            return posX == chunkcoordinates.posX && posZ == chunkcoordinates.posZ;
        }
    }
}
