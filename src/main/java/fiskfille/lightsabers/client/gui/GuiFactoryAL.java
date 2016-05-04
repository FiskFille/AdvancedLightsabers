package fiskfille.lightsabers.client.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public class GuiFactoryAL implements IModGuiFactory
{
    public void initialize(Minecraft minecraftInstance)
    {

    }

    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return GuiALModConfig.class;
    }

    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
    {
        return null;
    }
}
