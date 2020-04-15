package com.fiskmods.lightsabers.common.keybinds;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import fiskfille.utils.common.keybinds.FiskKeyBinding;

public class ALKeyBinds
{
    public static final FiskKeyBinding ACTIVATE_LIGHTSABER = new FiskKeyBinding("activateLightsaber", Keyboard.KEY_R);
    public static final FiskKeyBinding ACTIVATE_POWER = new FiskKeyBinding("activatePower", Keyboard.KEY_C);
    public static final FiskKeyBinding SELECT_POWER = new FiskKeyBinding("selectPower", Keyboard.KEY_F);

    public static void register()
    {
        ClientRegistry.registerKeyBinding(ACTIVATE_LIGHTSABER);
        ClientRegistry.registerKeyBinding(ACTIVATE_POWER);
        ClientRegistry.registerKeyBinding(SELECT_POWER);
    }
}
