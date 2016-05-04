package fiskfille.lightsabers.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import fiskfille.lightsabers.Lightsabers;

public class NEILightsabersConfig implements IConfigureNEI
{
    @Override
    public void loadConfig()
    {
        API.registerRecipeHandler(new LightsaberForgeRecipeHandler());
        API.registerUsageHandler(new LightsaberForgeRecipeHandler());
        API.registerRecipeHandler(new DoubleLightsaberRecipeHandler());
        API.registerUsageHandler(new DoubleLightsaberRecipeHandler());
    }

    @Override
    public String getName()
    {
        return "Advanced Lightsabers NEI Plugin";
    }

    @Override
    public String getVersion()
    {
        return Lightsabers.version;
    }
}
