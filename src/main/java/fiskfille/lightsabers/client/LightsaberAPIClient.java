package fiskfille.lightsabers.client;

import java.util.List;
import java.util.Map;

import net.minecraft.client.model.ModelBase;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

/**
 * @author FiskFille
 */
public class LightsaberAPIClient
{
    private static Map<List<Object>, ModelBase> lightsaberModels = Maps.newHashMap();

    public static void registerLightsaberModel(Lightsaber lightsaber, EnumPartType type, ModelBase model)
    {
        List list = Lists.newArrayList();
        list.add(lightsaber);
        list.add(type);
        lightsaberModels.put(list, model);
    }

    public static ModelBase getModelFor(Lightsaber lightsaber, EnumPartType type)
    {
        for (Map.Entry<List<Object>, ModelBase> e : lightsaberModels.entrySet())
        {
            if (e.getKey().get(0) == lightsaber && e.getKey().get(1) == type)
            {
                return e.getValue();
            }
        }

        return null;
    }
}
