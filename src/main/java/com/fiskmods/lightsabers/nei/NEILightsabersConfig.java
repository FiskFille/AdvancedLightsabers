package com.fiskmods.lightsabers.nei;

import com.fiskmods.lightsabers.Lightsabers;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEILightsabersConfig implements IConfigureNEI
{
    @Override
    public void loadConfig()
    {
        register(new LightsaberForgeRecipeHandler());
        register(new DoubleLightsaberRecipeHandler());
        register(new DisassemblyRecipeHandler());
        
//        API.hideItem(new ItemStack(ModBlocks.lightsaberCrystalGen));
    }
    
    private void register(TemplateRecipeHandler handler)
    {
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    @Override
    public String getName()
    {
        return "Advanced Lightsabers NEI Plugin";
    }

    @Override
    public String getVersion()
    {
        return Lightsabers.VERSION;
    }
}
