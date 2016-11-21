package fiskfille.lightsabers.common.generator.structure;

public class StructurePoint extends net.minecraft.util.ChunkCoordinates
{
	public StructurePoint(int x, int y, int z)
	{
		super(x, y, z);
	}

	public StructurePoint(StructurePoint p)
	{
		super(p);
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof StructurePoint))
		{
			return false;
		}
		else
		{
			StructurePoint chunkcoordinates = (StructurePoint)obj;
			return posX == chunkcoordinates.posX && posZ == chunkcoordinates.posZ;
		}
	}
}
