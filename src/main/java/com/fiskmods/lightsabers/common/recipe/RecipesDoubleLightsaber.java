package com.fiskmods.lightsabers.common.recipe;

import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ModItems;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipesDoubleLightsaber implements IRecipe
{
    @Override
    public boolean matches(InventoryCrafting crafting, World world)
    {
        ItemStack upper = null;
        ItemStack lower = null;
        
        int width = (int) Math.sqrt(crafting.getSizeInventory());
        int filledSlots = 0;

        for (int i = 0; i < crafting.getSizeInventory(); ++i)
        {
            ItemStack itemstack = crafting.getStackInSlot(i);

            if (itemstack != null)
            {
                if (itemstack.getItem() == ModItems.lightsaber && i + width <= crafting.getSizeInventory())
                {
                    ItemStack itemstack1 = crafting.getStackInSlot(i + width);
                    
                    if (itemstack1 != null && itemstack1.getItem() == ModItems.lightsaber)
                    {
                        upper = itemstack;
                        lower = itemstack1;
                    }
                }

                ++filledSlots;
            }
        }

        return filledSlots == 2 && upper != null && lower != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting)
    {
        int width = (int) Math.sqrt(crafting.getSizeInventory());
        
        for (int i = 0; i < crafting.getSizeInventory(); ++i)
        {
            ItemStack upper = crafting.getStackInSlot(i);

            if (upper != null)
            {
                return ItemDoubleLightsaber.create(upper, crafting.getStackInSlot(i + width));
            }
        }

        return null;
    }

    @Override
    public int getRecipeSize()
    {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return new ItemStack(ModItems.doubleLightsaber);
    }
}
