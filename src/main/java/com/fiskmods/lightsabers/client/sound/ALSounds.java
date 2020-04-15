package com.fiskmods.lightsabers.client.sound;

import com.fiskmods.lightsabers.Lightsabers;

public class ALSounds
{
    public static final String ambient_fortify = create("ambient.fortify");
    public static final String ambient_stealth = create("ambient.stealth");
    public static final String ambient_lightsaber_hum_heavy = create("ambient.lightsaber.hum_heavy");
    public static final String ambient_lightsaber_hum_light = create("ambient.lightsaber.hum_light");
    public static final String ambient_lightsaber_hum_medium = create("ambient.lightsaber.hum_medium");
    public static final String block_holocron_invest = create("block.holocron.invest");
    public static final String block_holocron_unlock = create("block.holocron.unlock");
    public static final String block_sith_sarcophagus_close = create("block.sith_sarcophagus.close");
    public static final String block_sith_sarcophagus_open = create("block.sith_sarcophagus.open");
    public static final String mob_lightsaber_hit = create("mob.lightsaber.hit");
    public static final String mob_lightsaber_off = create("mob.lightsaber.off");
    public static final String mob_lightsaber_on = create("mob.lightsaber.on");
    public static final String mob_lightsaber_swing = create("mob.lightsaber.swing");
    public static final String mob_sith_ghost_death = create("mob.sith_ghost.death");
    public static final String mob_sith_ghost_idle = create("mob.sith_ghost.idle");
    public static final String player_force_cast = create("player.force.cast");
    public static final String player_force_dark = create("player.force.dark");
    public static final String player_force_fail = create("player.force.fail");
    public static final String player_force_heal = create("player.force.heal");
    public static final String player_force_lightning = create("player.force.lightning");
    public static final String player_force_stealth_off = create("player.force.stealth.off");
    public static final String player_force_stealth_on = create("player.force.stealth.on");
    public static final String player_lightsaber_hit = create("player.lightsaber.hit");
    public static final String player_lightsaber_off = create("player.lightsaber.off");
    public static final String player_lightsaber_on = create("player.lightsaber.on");
    public static final String player_lightsaber_sweet_dreams = create("player.lightsaber.sweet_dreams");
    public static final String player_lightsaber_swing = create("player.lightsaber.swing");

    private static String create(String s)
    {
        return Lightsabers.MODID + ":" + s;
    }
}
