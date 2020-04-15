package fiskfille.utils.helper;

import java.util.Comparator;
import java.util.Random;

public class FiskComparators
{
    public static <T> Comparator<T> random(final Random rand)
    {
        return new Comparator<T>()
        {
            @Override
            public int compare(Object arg0, Object arg1)
            {
                return rand.nextBoolean() ? 1 : -1;
            }
        };
    }
}
