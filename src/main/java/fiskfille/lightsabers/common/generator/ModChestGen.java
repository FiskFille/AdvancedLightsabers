package fiskfille.lightsabers.common.generator;

import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.item.ModItems;

public class ModChestGen
{
	public static final String SITH_TOMB_ANNEX = "sithTombAnnex";
	public static final String SITH_TOMB_TREASURY = "sithTombTreasury";
	public static final String SITH_TOMB_COFFIN = "sithTombCoffin";
	
	public static void register()
	{
		addInfo(SITH_TOMB_ANNEX, new WeightedRandomChestContent[]
		{
			new WeightedRandomChestContent(Items.bone, 0, 4, 6, 4),
			new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 3),
			new WeightedRandomChestContent(Items.iron_ingot, 0, 2, 7, 3),
			new WeightedRandomChestContent(ModItems.lightsaberCircuitry, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 1),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_ANNEX),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_ANNEX),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_ANNEX)
		}, 2, 7);
		
		addInfo(SITH_TOMB_TREASURY, new WeightedRandomChestContent[]
		{
			new WeightedRandomChestContent(Items.bone, 0, 4, 6, 14),
			new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 12),
			new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 12),
			new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 5),
			new WeightedRandomChestContent(Items.enchanted_book, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 4, SITH_TOMB_TREASURY),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 4, SITH_TOMB_TREASURY),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 4, SITH_TOMB_TREASURY)
		}, 3, 7);
		
		addInfo(SITH_TOMB_COFFIN, new WeightedRandomChestContent[]
		{
			new WeightedRandomChestContent(Items.bone, 0, 4, 6, 14),
			new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 12),
			new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 12),
			new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 5),
			new WeightedRandomChestContent(Items.enchanted_book, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.lightsaberCircuitry, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.focusingCrystal, 0, 1, 1, 1),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_COFFIN),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_COFFIN),
			createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, SITH_TOMB_COFFIN),
			createLightsaber(ModItems.lightsaber, 0, 1, 1, 9)
		}, 6, 14);
		
		
		for (Map.Entry<Integer, String[]> e : LightsaberColors.chestMap.entrySet())
		{
			for (String chest : e.getValue())
			{
				if (!chest.equals(SITH_TOMB_ANNEX) && !chest.equals(SITH_TOMB_TREASURY) && !chest.equals(SITH_TOMB_COFFIN))
				{
					ChestGenHooks.addItem(chest, createColorCrystal(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), 0, 1, 1, 1, chest));
				}
			}
		}
		
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(ModItems.lightsaberEmitter, 0, 1, 1, 3));
	}
	
	private static void addInfo(String category, WeightedRandomChestContent[] items, int min, int max)
    {
		ChestGenHooks chest = ChestGenHooks.getInfo(category);
		
		for (WeightedRandomChestContent item : items)
        {
			chest.addItem(item);
        }
		
		chest.setMin(min);
		chest.setMax(max);
    }
	
	private static WeightedRandomChestContent createLightsaber(Item item, int metadata, int min, int max, int weight)
	{
		WeightedRandomChestContent chest = new WeightedRandomChestContent(item, metadata, min, max, weight);
		chest.theItemId.setTagCompound(new NBTTagCompound());
		chest.theItemId.getTagCompound().setBoolean("SithTombLoot", true);
		return chest;
	}
	
	private static WeightedRandomChestContent createColorCrystal(Item item, int metadata, int min, int max, int weight, String category)
	{
		WeightedRandomChestContent chest = new WeightedRandomChestContent(item, metadata, min, max, weight);
		chest.theItemId.setTagCompound(new NBTTagCompound());
		chest.theItemId.getTagCompound().setString("ChestGenCategory", category);
		return chest;
	}
}
