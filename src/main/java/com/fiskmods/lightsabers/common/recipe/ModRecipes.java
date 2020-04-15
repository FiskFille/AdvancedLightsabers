package com.fiskmods.lightsabers.common.recipe;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModRecipes
{
    public static void register()
    {
        GameRegistry.addRecipe(new RecipesDoubleLightsaber());
        
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestone, 4, 0), "###", "#-#", "###", '#', new ItemStack(Blocks.sandstone, 1, 2), '-', ModBlocks.lightsaberCrystal);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestone, 1, 1), "#", "#", '#', new ItemStack(ModBlocks.forcestoneSlab, 1, 0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestone, 2, 2), "#", "#", '#', new ItemStack(ModBlocks.lightForcestone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestone, 4, 0), "###", "#-#", "###", '#', Blocks.hardened_clay, '-', ModBlocks.lightsaberCrystal);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestone, 1, 1), "#", "#", '#', new ItemStack(ModBlocks.forcestoneSlab, 1, 1));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestone, 2, 2), "#", "#", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.forcestoneSlab, 6, 0), "###", '#', new ItemStack(ModBlocks.lightForcestone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.forcestoneSlab, 6, 1), "###", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestoneStairs, 4), "#  ", "## ", "###", '#', new ItemStack(ModBlocks.lightForcestone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestoneStairs, 4), "#  ", "## ", "###", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.lightActivatedForcestone, 1), new ItemStack(ModBlocks.lightForcestone, 1, 2), Items.redstone);
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.darkActivatedForcestone, 1), new ItemStack(ModBlocks.darkForcestone, 1, 2), Items.redstone);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.lightForcestone), new ItemStack(ModBlocks.lightForcestone, 1, 3), 0.1F);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.darkForcestone), new ItemStack(ModBlocks.darkForcestone, 1, 3), 0.1F);
        
        GameRegistry.addRecipe(new ItemStack(ModItems.circuitry, 1), "IDI", "BDS", "TDR", 'I', Items.iron_ingot, 'D', Items.diamond, 'B', Blocks.iron_block, 'S', Blocks.stone_button, 'T', Blocks.redstone_torch, 'R', Items.redstone);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightsaberForgeLight, 1), "*-*", "#O#", '#', new ItemStack(ModBlocks.lightForcestone, 1, 0), 'O', Blocks.iron_block, '*', ModBlocks.lightForcestoneStairs, '-', new ItemStack(ModBlocks.forcestoneSlab, 1, 0));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightsaberForgeDark, 1), "*-*", "#O#", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0), 'O', Blocks.iron_block, '*', ModBlocks.darkForcestoneStairs, '-', new ItemStack(ModBlocks.forcestoneSlab, 1, 1));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightsaberStand, 1), "IFI", 'I', Items.iron_ingot, 'F', Items.item_frame);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.disassemblyStation, 1), "RRR", "BCB", "IAI", 'R', Items.redstone, 'B', Items.blaze_rod, 'C', ModItems.circuitry, 'I', Blocks.iron_block, 'A', Blocks.anvil);
        
        for (CrystalColor color : CrystalColor.values())
        {
            GameRegistry.addRecipe(ItemCrystal.create(color, ModItems.crystalPouch), " LS", "LCL", " L ", 'L', Items.leather, 'S', Items.string, 'C', ItemCrystal.create(color));
        }
    }
}
