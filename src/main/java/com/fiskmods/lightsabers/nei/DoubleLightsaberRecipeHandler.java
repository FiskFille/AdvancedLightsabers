package com.fiskmods.lightsabers.nei;

import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;

import codechicken.nei.recipe.ShapedRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

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

            LightsaberData[] array = ItemDoubleLightsaber.get(result);
            Object[] inputs = new Object[10];
            inputs[0] = array[0].create();
            inputs[3] = array[1].create();

            CachedShapedRecipe recipe = new CachedShapedRecipe(3, 3, inputs, result);
            recipe.computeVisuals();
            arecipes.add(recipe);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        if (ingredient.getItem() == ModItems.lightsaber)
        {
            Object[] inputs = new Object[10];
            inputs[0] = ingredient;
            inputs[3] = ingredient;

            CachedShapedRecipe recipe = new CachedShapedRecipe(3, 3, inputs, ItemDoubleLightsaber.create(ingredient, ingredient));
            recipe.computeVisuals();
            arecipes.add(recipe);
        }
    }
}
