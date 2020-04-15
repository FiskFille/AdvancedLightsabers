package com.fiskmods.lightsabers.common.hilt;

import java.lang.reflect.Field;
import java.util.Locale;

public class HiltManager
{
    public static final Hilt GRAFLEX = new HiltGraflex();
    public static final Hilt REDEEMER = new HiltRedeemer();
    public static final Hilt MAULER = new HiltMauler();
    public static final Hilt PRODIGAL_SON = new HiltProdigalSon();
    public static final Hilt KNIGHTED = new HiltKnighted();
    public static final Hilt VAID_ANCIENT = new HiltVaid();
    public static final Hilt VAID_MODERN = new HiltVaid();
    public static final Hilt DROIDEKA = new HiltDroideka();
    public static final Hilt FULCRUM = new HiltFulcrum();
    public static final Hilt JUGGERNAUT = new HiltJuggernaut();
    public static final Hilt MECHANICAL = new HiltMechanical();
    public static final Hilt MANDALORIAN = new HiltMandalorian();
    public static final Hilt FURY = new HiltFury();
    public static final Hilt REBEL = new HiltRebel();
    public static final Hilt IMPERIAL = new HiltImperial();
    public static final Hilt REBORN = new HiltReborn();

    public static void register()
    {
        for (Field field : HiltManager.class.getFields())
        {
            if (Hilt.class.isAssignableFrom(field.getType()))
            {
                try
                {
                    Hilt.register(field.getName().toLowerCase(Locale.ROOT), (Hilt) field.get(null));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        map(GRAFLEX, "Graflex");
        map(REDEEMER, "Redeemer");
        map(MAULER, "Mauler");
        map(PRODIGAL_SON, "Prodigal Son");
        map(KNIGHTED, "Knighted");
        map(VAID_ANCIENT, "Vaid (Ancient)");
        map(VAID_MODERN, "Vaid (Modern)");
        map(DROIDEKA, "Droideka");
        map(FULCRUM, "Fulcrum");
        map(JUGGERNAUT, "Juggernaut");
        map(MECHANICAL, "Mechanical");
        map(MANDALORIAN, "Mandalorian");
        map(FURY, "Fury");
    }

    private static void map(Hilt value, String legacy)
    {
        Hilt.LEGACY_MAPPINGS.put(legacy, value.delegate.name());
    }
}
