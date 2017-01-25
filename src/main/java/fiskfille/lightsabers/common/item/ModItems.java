package fiskfille.lightsabers.common.item;

import java.lang.reflect.Constructor;

import net.minecraft.item.Item;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class ModItems
{
    public static Item lightsaberCircuitry;
    public static Item focusingCrystal;

    public static Item lightsaberEmitter;
    public static Item lightsaberSwitchSection;
    public static Item lightsaberBody;
    public static Item lightsaberPommel;

    public static Item lightsaber;
    public static Item doubleLightsaber;

    public static void register()
    {
        lightsaberCircuitry = new ItemBasic();
        focusingCrystal = new ItemFocusingCrystal();

        lightsaberEmitter = new ItemLightsaberPart(EnumPartType.EMITTER);
        lightsaberSwitchSection = new ItemLightsaberPart(EnumPartType.SWITCH_SECTION);
        lightsaberBody = new ItemLightsaberPart(EnumPartType.BODY);
        lightsaberPommel = new ItemLightsaberPart(EnumPartType.POMMEL);

        if (Lightsabers.isBattlegearLoaded)
        {
            try
            {
                Class clazz = Class.forName("fiskfille.lightsabers.common.item.ItemLightsaberBG");
                Constructor c = clazz.getConstructor();
                lightsaber = (ItemLightsaber) c.newInstance();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            lightsaber = new ItemLightsaber();
        }

        doubleLightsaber = new ItemDoubleLightsaber();

        ItemRegistry.registerItem(lightsaberCircuitry, "Lightsaber Circuitry");
        ItemRegistry.registerItem(focusingCrystal, "Focusing Crystal");

        ItemRegistry.registerItem(lightsaberEmitter, "Lightsaber Blade Emitter");
        ItemRegistry.registerItem(lightsaberSwitchSection, "Lightsaber Switch Module");
        ItemRegistry.registerItem(lightsaberBody, "Lightsaber Grip");
        ItemRegistry.registerItem(lightsaberPommel, "Lightsaber Pommel");

        ItemRegistry.registerItem(lightsaber, "Lightsaber");
        ItemRegistry.registerItem(doubleLightsaber, "Double Lightsaber");
    }
}
