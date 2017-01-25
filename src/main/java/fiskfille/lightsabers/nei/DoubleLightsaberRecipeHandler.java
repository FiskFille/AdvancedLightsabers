package fiskfille.lightsabers.nei;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ModItems;

public class DoubleLightsaberRecipeHandler extends ShapedRecipeHandler
{
    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        if (result.getItem() == ModItems.doubleLightsaber)
        {
            if (result.getDisplayName().equalsIgnoreCase("sweet dreams"))
            {
                Minecraft.getMinecraft().thePlayer.playSound(ALSounds.player_lightsaber_sweet_dreams, 1.0F, 1.0F);
            }

            Object[] aobject = new Object[10];
            aobject[0] = LightsaberHelper.getDoubleLightsaberUpper(result);
            aobject[3] = LightsaberHelper.getDoubleLightsaberLower(result);

            CachedShapedRecipe recipe = new CachedShapedRecipe(3, 3, aobject, result);
            recipe.computeVisuals();
            arecipes.add(recipe);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        if (ingredient.getItem() == ModItems.lightsaber)
        {
            Object[] aobject = new Object[10];
            aobject[0] = ingredient;
            aobject[3] = ingredient;

            CachedShapedRecipe recipe = new CachedShapedRecipe(3, 3, aobject, LightsaberHelper.createDoubleLightsaber(ingredient, ingredient));
            recipe.computeVisuals();
            arecipes.add(recipe);
        }
    }
}
