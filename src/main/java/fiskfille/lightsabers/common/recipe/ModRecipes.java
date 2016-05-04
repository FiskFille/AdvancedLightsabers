package fiskfille.lightsabers.common.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.item.ModItems;

public class ModRecipes
{
	public static void register()
	{
		GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestone, 4, 0), new Object[] {"###", "#-#", "###", '#', new ItemStack(Blocks.sandstone, 1, 2), '-', ModBlocks.lightsaberCrystal});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestone, 1, 1), new Object[] {"#", "#", '#', new ItemStack(ModBlocks.forcestoneSlab, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestone, 2, 2), new Object[] {"#", "#", '#', new ItemStack(ModBlocks.lightForcestone, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.lightActivatedForcestone, 1), new Object[] {new ItemStack(ModBlocks.lightForcestone, 1, 2), Items.redstone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.forcestoneSlab, 3, 0), new Object[] {"###", '#', new ItemStack(ModBlocks.lightForcestone, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.lightForcestoneStairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(ModBlocks.lightForcestone, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestone, 4, 0), new Object[] {"###", "#-#", "###", '#', Blocks.hardened_clay, '-', ModBlocks.lightsaberCrystal});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestone, 1, 1), new Object[] {"#", "#", '#', new ItemStack(ModBlocks.forcestoneSlab, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestone, 2, 2), new Object[] {"#", "#", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.darkActivatedForcestone, 1), new Object[] {new ItemStack(ModBlocks.darkForcestone, 1, 2), Items.redstone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.forcestoneSlab, 3, 1), new Object[] {"###", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.darkForcestoneStairs, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0)});
		
		GameRegistry.addRecipe(new ItemStack(ModItems.lightsaberCircuitry, 1), new Object[] {"IDI", "BDS", "TDR", 'I', Items.iron_ingot, 'D', Items.diamond, 'B', Blocks.iron_block, 'S', Blocks.stone_button, 'T', Blocks.redstone_torch, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.lightsaberForge, 1), new Object[] {"* *", "*-*", "#O#", '#', new ItemStack(ModBlocks.darkForcestone, 1, 0), 'O', Blocks.iron_block, '*', ModBlocks.darkForcestoneStairs, '-', new ItemStack(ModBlocks.forcestoneSlab, 1, 1)});
		GameRegistry.addRecipe(new RecipesDoubleLightsaber());
	}
}
