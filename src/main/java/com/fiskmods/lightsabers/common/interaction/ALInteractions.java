package com.fiskmods.lightsabers.common.interaction;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.interaction.key.KeyPressForceCycle;
import com.fiskmods.lightsabers.common.interaction.key.KeyPressForceSelect;
import com.fiskmods.lightsabers.common.interaction.key.KeyPressForceUse;
import com.fiskmods.lightsabers.common.interaction.key.KeyPressLightsaber;

import fiskfille.utils.common.interaction.Interaction;

public class ALInteractions
{
    public static void register()
    {
        register("activate_lightsaber", new KeyPressLightsaber());
        register("activate_force_power", new KeyPressForceUse());
        register("select_force_power", new KeyPressForceSelect());
        register("cycle_force_power", new KeyPressForceCycle());
    }
    
    private static void register(String key, Interaction value)
    {
        Interaction.register(Lightsabers.MODID + ":" + key, value);
    }
}
