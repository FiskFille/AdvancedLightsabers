package fiskfille.lightsabers.common.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ModItems;

public class RecipesDoubleLightsaber implements IRecipe
{
    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world)
    {
        int width = (int) Math.sqrt(inventoryCrafting.getSizeInventory());
        ItemStack lightsaber1 = null;
        ItemStack lightsaber2 = null;

        int filledSlots = 0;

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inventoryCrafting.getStackInSlot(i);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() == ModItems.lightsaber)
                {
                    if (i + width <= inventoryCrafting.getSizeInventory() && inventoryCrafting.getStackInSlot(i + width) != null && inventoryCrafting.getStackInSlot(i + width).getItem() == ModItems.lightsaber)
                    {
                        lightsaber1 = inventoryCrafting.getStackInSlot(i);
                        lightsaber2 = inventoryCrafting.getStackInSlot(i + width);
                    }
                }

                ++filledSlots;
            }
        }

        if (filledSlots != 2)
        {
            return false;
        }

        if (lightsaber1 != null && lightsaber2 != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting)
    {
        int width = (int) Math.sqrt(inventoryCrafting.getSizeInventory());
        ItemStack lightsaber1 = null;
        ItemStack lightsaber2 = null;

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inventoryCrafting.getStackInSlot(i);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() == ModItems.lightsaber)
                {
                    if (i + width <= inventoryCrafting.getSizeInventory() && inventoryCrafting.getStackInSlot(i + width) != null && inventoryCrafting.getStackInSlot(i + width).getItem() == ModItems.lightsaber)
                    {
                        lightsaber1 = inventoryCrafting.getStackInSlot(i);
                        lightsaber2 = inventoryCrafting.getStackInSlot(i + width);
                    }
                }
            }
        }

        if (lightsaber1 != null && lightsaber2 != null)
        {
            return LightsaberHelper.createDoubleLightsaber(lightsaber1, lightsaber2);
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
        return null;
    }
}
