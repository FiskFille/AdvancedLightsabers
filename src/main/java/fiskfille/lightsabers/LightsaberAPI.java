package fiskfille.lightsabers;

import java.util.List;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.common.lightsaber.Lightsaber;

/**
 * @author FiskFille
 */
public class LightsaberAPI
{
    private static List<Lightsaber> lightsabers = Lists.newArrayList();

    /**
     * Used to register the specified Lightsaber.
     *
     * @param lightsaber The Lightsaber registered.
     */
    public static void registerLightsaber(Lightsaber lightsaber)
    {
        if (!lightsabers.contains(lightsaber))
        {
            lightsabers.add(lightsaber);
        }
        else
        {
            System.err.println("[LightsaberAPI] " + lightsaber.getName() + " has already been registered!");
        }
    }

    /**
     * @returns a list of registered Lightsaber.
     */
    public static List<Lightsaber> getLightsabers()
    {
        return lightsabers;
    }

    /**
     * Gets an instance of a Lightsaber by name.
     *
     * @param name The name of the Lightsaber
     * @return the Lightsaber with the specified name, or null if there is none.
     */
    public static Lightsaber getLightsaberByName(String name)
    {
        for (Lightsaber lightsaber : lightsabers)
        {
            if (lightsaber.getName().equals(name))
            {
                return lightsaber;
            }
        }

        return null;
    }
}
