package fiskfille.lightsabers.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.IConfigElement;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.config.ModConfig;

public class GuiALModConfig extends GuiConfig
{
	private static GuiALModConfig instance;
	private static GuiConfigEntries entryListStatic;
	
    public GuiALModConfig(GuiScreen parent)
    {
        super(parent, getConfigElements(), Lightsabers.modid, false, false, "Advanced Lightsabers Configuration");
        instance = this;
        entryListStatic = entryList;
    }

    private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> elements = new ArrayList<IConfigElement>();
        elements.add(categoryElement(ModConfig.CATEGORY_DYNAMIC_LIGHTS));
        elements.add(categoryElement(ModConfig.CATEGORY_RENDERING));
        return elements;
    }
    
    private static String getConventionalName(String s)
    {
    	return s.toLowerCase().replace(" ", "");
    }

    private static IConfigElement categoryElement(String category, String name, String tooltip_key, IConfigElement... configElements)
    {
    	List<IConfigElement> list = new ConfigElement(ModConfig.configFile.getCategory(category.toLowerCase())).getChildElements();
    	
    	for (IConfigElement configElement : configElements)
    	{
    		list.add(configElement);
    	}
    	
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key, list);
    }
    
    private static IConfigElement categoryElement(String category, IConfigElement... configElements)
    {
    	return categoryElement(category, category, getConventionalName(category), configElements);
    }
}
