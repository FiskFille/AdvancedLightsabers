package fiskfille.utils.helper;

import java.util.Map;
import java.util.Random;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class FiskMath
{
    /** 0.8898832947136070 = 180 km/h */
    public static final double KMPH = 0.0049467960817423D;

    public static <T> T getWeightedD(Random rand, Map<T, Double> map)
    {
        double totalWeight = 0.0;

        for (Map.Entry<T, Double> e : map.entrySet())
        {
            totalWeight += e.getValue();
        }

        double random = rand.nextDouble() * totalWeight;

        for (Map.Entry<T, Double> e : map.entrySet())
        {
            random -= e.getValue();

            if (random <= 0)
            {
                return e.getKey();
            }
        }

        return null;
    }

    public static <T> T getWeightedF(Random rand, Map<T, Float> map)
    {
        float totalWeight = 0.0F;

        for (Map.Entry<T, Float> e : map.entrySet())
        {
            totalWeight += e.getValue();
        }

        float random = rand.nextFloat() * totalWeight;

        for (Map.Entry<T, Float> e : map.entrySet())
        {
            random -= e.getValue();

            if (random <= 0)
            {
                return e.getKey();
            }
        }

        return null;
    }

    public static <T> T getWeightedI(Random rand, Map<T, Integer> map)
    {
        int totalWeight = 1;

        for (Map.Entry<T, Integer> e : map.entrySet())
        {
            totalWeight += e.getValue();
        }

        int random = rand.nextInt(totalWeight);

        for (Map.Entry<T, Integer> e : map.entrySet())
        {
            random -= e.getValue();

            if (random <= 0)
            {
                return e.getKey();
            }
        }

        return null;
    }

    public static boolean containsAABB(AxisAlignedBB within, AxisAlignedBB aabb)
    {
        return aabb.minX > within.minX && aabb.minY > within.minY && aabb.minZ > within.minZ && aabb.maxX < within.maxX && aabb.maxY < within.maxY && aabb.maxZ < within.maxZ;
    }

    public static double getScaledDistance(Vec3 src, Vec3 dst, double radius)
    {
        return (radius - Math.min(src.distanceTo(dst), radius)) / radius;
    }

    public static float getScaledDistance(Vec3 src, Vec3 dst, float radius)
    {
        return (float) getScaledDistance(src, dst, (double) radius);
    }

    public static float animate(float frame, float duration, float frameStart)
    {
        return frame >= frameStart && frame <= frameStart + duration ? (frame - frameStart) / duration : 0;
    }

    public static float animate(float frame, float duration, float frameStart, float fadeIn, float fadeOut)
    {
        fadeIn = MathHelper.clamp_float(fadeIn, 0, duration);
        fadeOut = MathHelper.clamp_float(fadeOut, 0, duration - fadeIn);

        if (frame >= frameStart && frame <= frameStart + duration)
        {
            float pos = frame - frameStart;

            if (pos > fadeIn && frame < duration - fadeOut)
            {
                return 1;
            }
            else if (pos > fadeIn)
            {
                frame = frameStart + duration - pos;
                fadeIn = fadeOut;
            }

            return animate(frame, duration, frameStart) * duration / fadeIn;
        }

        return 0;
    }
    
    public static float curveCrests(float f)
    {
        return MathHelper.sin((float) (f * Math.PI / 2));
    }
    
    public static float curve(float f)
    {
        return (curveCrests(f * 2 - 1) + 1) / 2;
    }
}
