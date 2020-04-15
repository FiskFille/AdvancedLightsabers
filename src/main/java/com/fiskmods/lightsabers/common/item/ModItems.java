package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.lightsaber.PartType;

import net.minecraft.item.Item;

public class ModItems
{
    public static Item circuitry;
    public static Item focusingCrystal;
    public static Item crystalPouch;

    public static Item emitter;
    public static Item switchSection;
    public static Item grip;
    public static Item pommel;

    public static Item lightsaber;
    public static Item doubleLightsaber;

    public static void register()
    {
        circuitry = new ItemCircuitry();
        focusingCrystal = new ItemFocusingCrystal();
        crystalPouch = new ItemCrystalPouch();

        emitter = new ItemLightsaberPart(PartType.EMITTER);
        switchSection = new ItemLightsaberPart(PartType.SWITCH_SECTION);
        grip = new ItemLightsaberPart(PartType.BODY);
        pommel = new ItemLightsaberPart(PartType.POMMEL);

        lightsaber = new ItemLightsaber();
        doubleLightsaber = new ItemDoubleLightsaber();
        

        ItemRegistry.registerItem(circuitry, "lightsaber_circuitry");
        ItemRegistry.registerItem(focusingCrystal, "focusing_crystal");
        ItemRegistry.registerItem(crystalPouch, "crystal_pouch");

        ItemRegistry.registerItem(emitter, "lightsaber_blade_emitter");
        ItemRegistry.registerItem(switchSection, "lightsaber_switch_module");
        ItemRegistry.registerItem(grip, "lightsaber_grip");
        ItemRegistry.registerItem(pommel, "lightsaber_pommel");

        ItemRegistry.registerItem(lightsaber, "lightsaber");
        ItemRegistry.registerItem(doubleLightsaber, "double_lightsaber");
    }
}
