package fiskfille.lightsabers.common.keybinds;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ALKeyBinds
{
    public static ALKeyBinding keyBindingSelectPower = new ALKeyBinding("key.selectPower", Keyboard.KEY_F);

    public static void register()
    {
        ClientRegistry.registerKeyBinding(keyBindingSelectPower);
    }
}
