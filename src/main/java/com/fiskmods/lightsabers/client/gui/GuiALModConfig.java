package com.fiskmods.lightsabers.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.config.ModConfig;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class GuiALModConfig extends GuiConfig
{
    public GuiALModConfig(GuiScreen parent)
    {
        super(parent, getConfigElements(), Lightsabers.MODID, false, false, "Advanced Lightsabers Configuration");
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
        return s.toLowerCase(Locale.ROOT).replace(" ", "");
    }

    private static IConfigElement categoryElement(String category, String name, String tooltip_key, IConfigElement... configElements)
    {
        List<IConfigElement> list = new ConfigElement(ModConfig.configFile.getCategory(category.toLowerCase(Locale.ROOT))).getChildElements();

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
