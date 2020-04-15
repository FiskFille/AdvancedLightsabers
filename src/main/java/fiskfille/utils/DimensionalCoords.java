package fiskfille.utils;

import com.fiskmods.lightsabers.common.data.ALData.DataFactory;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class DimensionalCoords extends ChunkCoordinates
{
    public int dimension;

    public DimensionalCoords()
    {
    }

    public DimensionalCoords(int x, int y, int z, int dim)
    {
        super(x, y, z);
        dimension = dim;
    }

    public DimensionalCoords(ChunkCoordinates coords, int dim)
    {
        super(coords);
        dimension = dim;
    }

    public DimensionalCoords(TileEntity tile)
    {
        set(tile);
    }

    public static DimensionalCoords copy(DimensionalCoords coords)
    {
        if (coords != null)
        {
            return new DimensionalCoords().set(coords);
        }

        return null;
    }

    public DimensionalCoords set(int x, int y, int z, int dim)
    {
        posX = x;
        posY = y;
        posZ = z;
        dimension = dim;

        return this;
    }

    public DimensionalCoords set(TileEntity tile)
    {
        if (tile.getWorldObj() != null)
        {
            return set(tile.xCoord, tile.yCoord, tile.zCoord, tile.getWorldObj().provider.dimensionId);
        }

        return this;
    }

    public DimensionalCoords set(DimensionalCoords coords)
    {
        return set(coords.toArray());
    }

    public DimensionalCoords set(int... args)
    {
        int[] aint = toArray();

        for (int i = 0; i < Math.min(args.length, aint.length); ++i)
        {
            aint[i] = args[i];
        }

        return set(aint[0], aint[1], aint[2], aint[3]);
    }

    public int[] toArray()
    {
        return new int[] {posX, posY, posZ, dimension};
    }

    public static DimensionalCoords fromArray(int[] aint)
    {
        int[] aint1 = new int[4];

        for (int i = 0; i < Math.min(aint.length, aint1.length); ++i)
        {
            aint1[i] = aint[i];
        }

        return new DimensionalCoords(aint1[0], aint1[1], aint1[2], aint1[3]);
    }

    public static DataFactory<DimensionalCoords> factory()
    {
        return new DataFactory<DimensionalCoords>()
        {
            @Override
            public DimensionalCoords construct()
            {
                return new DimensionalCoords();
            }
        };
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof DimensionalCoords))
        {
            return false;
        }
        else
        {
            DimensionalCoords coords = (DimensionalCoords) obj;
            return posX == coords.posX && posY == coords.posY && posZ == coords.posZ && dimension == coords.dimension;
        }
    }

    @Override
    public int hashCode()
    {
        return posX + posZ << 8 + posY << 16 + dimension << 32;
    }

    @Override
    public String toString()
    {
        return "Pos{x=" + posX + ", y=" + posY + ", z=" + posZ + ", dim=" + dimension + '}';
    }

    @Override
    public int compareTo(Object obj)
    {
        return compareTo((DimensionalCoords) obj);
    }

    public int compareTo(DimensionalCoords coords)
    {
        return dimension == coords.dimension ? posY == coords.posY ? posZ == coords.posZ ? posX - coords.posX : posZ - coords.posZ : posY - coords.posY : dimension - coords.dimension;
    }
}
