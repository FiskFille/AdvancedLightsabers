package fiskfille.lightsabers.common.helper;

import net.minecraft.util.StatCollector;

public class FocusingCrystals
{
    public static final String COMPRESSED_FOCUSING_CRYSTAL = "compressed_focusing_crystal";
    public static final String CRACKED_KYBER_CRYSTAL = "cracked_kyber_crystal";
    public static final String INVERTING_FOCUSING_CRYSTAL = "inverting_focusing_crystal";
    public static final String FINE_CUT_FOCUSING_CRYSTAL = "fine_cut_focusing_crystal";

    public static String[] getFocusingCrystals()
    {
        return new String[] {COMPRESSED_FOCUSING_CRYSTAL, CRACKED_KYBER_CRYSTAL, INVERTING_FOCUSING_CRYSTAL, FINE_CUT_FOCUSING_CRYSTAL};
    }

    public static String getFocusingCrystalName(int id)
    {
        return StatCollector.translateToLocal("item." + getFocusingCrystals()[id] + ".name");
    }
}
