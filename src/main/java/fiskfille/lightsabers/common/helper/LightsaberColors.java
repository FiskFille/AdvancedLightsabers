package fiskfille.lightsabers.common.helper;

import java.util.Map;

import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ChestGenHooks;

import com.google.common.collect.Maps;

import fiskfille.lightsabers.common.generator.ModChestGen;

public class LightsaberColors
{
    public static final int DEEP_BLUE = 0x0000FF;
    public static final int MEDIUM_BLUE = 0x006BFF;
    public static final int LIGHT_BLUE = 0x59B9FF;
    public static final int ARCTIC_BLUE = 0xDDF6FF;
    public static final int WHITE = 0xFFFFFF;
    public static final int INDIGO = 0x5D00FF;
    public static final int PURPLE = 0xAD00AD;
    public static final int MAGENTA = 0xFF00FF;
    public static final int PINK = 0xFF8FBA;
    public static final int RED = 0xFF0000;
    public static final int BLOOD_ORANGE = 0xFF8000;
    public static final int AMBER = 0xFFB600;
    public static final int YELLOW = 0xFFFF00;
    public static final int GOLD = 0xFFFF3A;
    public static final int LIME_GREEN = 0xBFFF00;
    public static final int GREEN = 0x00FF00;
    public static final int MINT_GREEN = 0x00FF9B;
    public static final int CYAN = 0x00FFFF;

    public static int[] getColors()
    {
        return new int[] {DEEP_BLUE, MEDIUM_BLUE, LIGHT_BLUE, ARCTIC_BLUE, WHITE, INDIGO, PURPLE, MAGENTA, PINK, RED, BLOOD_ORANGE, AMBER, YELLOW, GOLD, LIME_GREEN, GREEN, MINT_GREEN, CYAN};
    }

    public static String getColorName(int id)
    {
        return StatCollector.translateToLocal("color." + id);
    }

    public static float[] getRGB(int hex)
    {
        float r = (float) ((hex & 0xFF0000) >> 16) / 255;
        float g = (float) ((hex & 0xFF00) >> 8) / 255;
        float b = (float) (hex & 0xFF) / 255;
        return new float[] {r, g, b};
    }

    public static Map<Integer, Integer> rarityMap = Maps.newHashMap();
    public static Map<Integer, String[]> chestMap = Maps.newHashMap();

    public static final int RARITY_COMMON = 0;
    public static final int RARITY_UNCOMMON = 1;
    public static final int RARITY_RARE = 2;
    public static final int RARITY_EPIC = 3;

    public static void registerRarity(int color, int rarity, String... chests)
    {
        int index = LightsaberHelper.getCrystalIdFromColor(color);

        if (index != -1)
        {
            rarityMap.put(index, rarity);
            chestMap.put(index, chests);
        }
    }

    private static boolean hasInit = false;

    private static void init()
    {
        if (hasInit)
        {
            return;
        }

        hasInit = true;

        registerRarity(DEEP_BLUE, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(MEDIUM_BLUE, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(LIGHT_BLUE, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(AMBER, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(YELLOW, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(GOLD, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(LIME_GREEN, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_JUNGLE_CHEST, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(GREEN, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_JUNGLE_CHEST);
        registerRarity(MINT_GREEN, RARITY_COMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_JUNGLE_CHEST);

        registerRarity(MAGENTA, RARITY_UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(PINK, RARITY_UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(RED, RARITY_UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.VILLAGE_BLACKSMITH);
        registerRarity(BLOOD_ORANGE, RARITY_UNCOMMON, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);

        registerRarity(INDIGO, RARITY_RARE, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.STRONGHOLD_LIBRARY);
        registerRarity(PURPLE, RARITY_RARE, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.STRONGHOLD_LIBRARY);
        registerRarity(CYAN, RARITY_RARE, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.STRONGHOLD_LIBRARY);

        registerRarity(ARCTIC_BLUE, RARITY_EPIC, ModChestGen.SITH_TOMB_TREASURY, ChestGenHooks.MINESHAFT_CORRIDOR);
        registerRarity(WHITE, RARITY_EPIC, ModChestGen.SITH_TOMB_TREASURY, ChestGenHooks.MINESHAFT_CORRIDOR);
    }

    static
    {
        init();
    }
}
