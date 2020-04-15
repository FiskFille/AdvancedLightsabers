package com.fiskmods.lightsabers.nei;

import static codechicken.lib.gui.GuiDraw.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.gui.GuiDisassemblyStation;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ItemFocusingCrystal;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.item.ItemLightsaberPart;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class DisassemblyRecipeHandler extends TemplateRecipeHandler
{
    private Minecraft mc = Minecraft.getMinecraft();

    private class ItemEntry implements Comparable<ItemEntry>
    {
        private final Map.Entry<ItemStack, Float> e;

        public ItemEntry(Map.Entry<ItemStack, Float> e)
        {
            this.e = e;
        }

        public ItemStack getKey()
        {
            return e.getKey();
        }

        public float getValue()
        {
            return e.getValue();
        }

        @Override
        public int hashCode()
        {
            int result = 1;
            result = 31 * result + getKey().getItemDamage();
            result = 31 * result + (getKey().getItem() == null ? 0 : getKey().getItem().hashCode());
            result = 31 * result + (getKey().getTagCompound() == null ? 0 : getKey().getTagCompound().hashCode());
            result = 31 * result + Float.floatToIntBits(getValue());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            else if (obj == null)
            {
                return false;
            }

            ItemEntry other = (ItemEntry) obj;

            if (getKey().getItemDamage() != other.getKey().getItemDamage())
            {
                return false;
            }

            if (Float.floatToIntBits(getValue()) != Float.floatToIntBits(other.getValue()))
            {
                return false;
            }

            if (getKey().getItem() == null)
            {
                if (other.getKey().getItem() != null)
                {
                    return false;
                }
            }
            else if (!getKey().getItem().equals(other.getKey().getItem()))
            {
                return false;
            }

            if (getKey().getTagCompound() == null)
            {
                if (other.getKey().getTagCompound() != null)
                {
                    return false;
                }
            }
            else if (!getKey().getTagCompound().equals(other.getKey().getTagCompound()))
            {
                return false;
            }

            return true;
        }

        @Override
        public int compareTo(ItemEntry o)
        {
            return Float.compare(o.getValue(), getValue());
        }
    }

    public class CachedDisassemblyRecipe extends CachedRecipe
    {
        public Map<PositionedStack, Float> results;
        public PositionedStack ingredient;

        public CachedDisassemblyRecipe(ItemStack input, Map<ItemStack, Float> outputs)
        {
            input.stackSize = 1;
            ingredient = new PositionedStack(input, 11, 7);
            results = new HashMap<>();

            List<ItemEntry> list = new ArrayList<>();

            for (Map.Entry<ItemStack, Float> e : outputs.entrySet())
            {
                ItemEntry entry = new ItemEntry(e);

                if (list.contains(entry))
                {
                    list.get(list.indexOf(entry)).getKey().stackSize += e.getKey().stackSize;
                }
                else
                {
                    list.add(entry);
                }
            }

            list.sort(Comparator.naturalOrder());

            for (int i = 0; i < list.size(); ++i)
            {
                addOutput(72 + 18 * (i % 5), 18 + 18 * (i / 5), list.get(i));
            }
        }

        private void addOutput(int x, int y, ItemEntry e)
        {
            results.put(new PositionedStack(e.getKey(), x - 5, y - 11, false), e.getValue());
        }

        @Override
        public PositionedStack getIngredient()
        {
            return ingredient;
        }

        @Override
        public PositionedStack getResult()
        {
            return null;
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> stacks = new ArrayList<>(results.keySet());
            PositionedStack stack = getOtherStack();

            if (stack != null)
            {
                stacks.add(stack);
            }

            return stacks;
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return afuels.get(cycleticks / 48 % afuels.size()).stack;
        }
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(39 - 5, 36 - 11, 24, 17), getOverlayIdentifier()));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiDisassemblyStation.class;
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("recipe.disassembly_station");
    }

    @Override
    public TemplateRecipeHandler newInstance()
    {
        if (afuels == null || afuels.isEmpty())
        {
            findFuels();
        }

        return super.newInstance();
    }

    @Override
    public int recipiesPerPage()
    {
        return 2;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(getOverlayIdentifier()))
        {
            loadCraftingRecipes(new ItemStack(ModItems.circuitry));
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        try
        {
            Item item = result.getItem();

            if (item == ModItems.circuitry)
            {
                for (Hilt hilt : Hilt.REGISTRY)
                {
                    addRecipe(hilt.createDefault().create());
                }
            }
            else if (item instanceof ItemLightsaberPart)
            {
                addRecipe(ItemLightsaberPart.get(result).createDefault().create());
            }
            else if (item instanceof ItemFocusingCrystal)
            {
                addRecipe(new LightsaberData().set(ItemFocusingCrystal.get(result)).create());
            }
            else if (item instanceof ItemCrystal)
            {
                addRecipe(new LightsaberData().set(ItemCrystal.get(result)).create());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void addRecipe(ItemStack stack)
    {
        arecipes.add(new CachedDisassemblyRecipe(stack, TileEntityDisassemblyStation.getOutput(stack)));
        arecipes.add(new CachedDisassemblyRecipe(stack = ItemDoubleLightsaber.create(stack, stack), TileEntityDisassemblyStation.getOutput(stack)));
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        try
        {
            if (ingredient.getItem() instanceof ItemLightsaberBase)
            {
                arecipes.add(new CachedDisassemblyRecipe(ingredient, TileEntityDisassemblyStation.getOutput(ingredient)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getGuiTexture()
    {
        return Lightsabers.MODID + ":textures/gui/container/disassembly_station.png";
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "disassembly_station";
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(12, 26, 176, 0, 14, 14, 48, 7);
        drawProgressBar(34, 25, 176, 14, 24, 16, 48, 0);
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 66);
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe)
    {
        CachedDisassemblyRecipe crecipe = (CachedDisassemblyRecipe) arecipes.get(recipe);

        for (Map.Entry<PositionedStack, Float> e : crecipe.results.entrySet())
        {
            if (gui.isMouseOver(e.getKey(), recipe))
            {
                currenttip.add(String.format("%s%%", ItemStack.field_111284_a.format(e.getValue() * 100)));
                break;
            }
        }

        return currenttip;
    }

    public static ArrayList<FuelPair> afuels;

    private static void findFuels()
    {
        afuels = new ArrayList<>();

        for (Map.Entry<ItemStack, Integer> e : TileEntityDisassemblyStation.getFuels().entrySet())
        {
            afuels.add(new FuelPair(e.getKey().copy(), e.getValue()));
        }
    }

    public static class FuelPair
    {
        public PositionedStack stack;
        public int burnTime;

        public FuelPair(ItemStack ingred, int burnTime)
        {
            stack = new PositionedStack(ingred, 11, 43, false);
            this.burnTime = burnTime;
        }
    }
}
