package com.fiskmods.lightsabers.nei;

import static codechicken.lib.gui.GuiDraw.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.gui.GuiLightsaberForge;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.event.ClientEventHandler;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemFocusingCrystal;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.item.ItemLightsaberPart;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.helper.ALRenderHelper;
import com.google.common.collect.Iterables;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LightsaberForgeRecipeHandler extends TemplateRecipeHandler
{
    private Minecraft mc = Minecraft.getMinecraft();

    public class CachedLightsaberForgeRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public final LightsaberData data;

        public CachedLightsaberForgeRecipe(LightsaberData output, Object[] inputs)
        {
            result = new PositionedStack(ItemLightsaberBase.setActive(output.create(), true), 131, 76);
            ingredients = new ArrayList<PositionedStack>();
            data = output;

            addSlotToContainer(0, 20, 17, inputs);
            addSlotToContainer(1, 20, 35, inputs);
            addSlotToContainer(2, 20, 53, inputs);
            addSlotToContainer(3, 20, 71, inputs);
            addSlotToContainer(4, 43, 71, inputs);
            addSlotToContainer(5, 66, 71, inputs);
            addSlotToContainer(6, 89, 71, inputs);
            addSlotToContainer(7, 107, 71, inputs);
        }

        private void addSlotToContainer(int id, int x, int y, Object[] inputs)
        {
            if (inputs[id] != null)
            {
                PositionedStack stack = new PositionedStack(inputs[id], x - 5, y - 11, false);
                ingredients.add(stack);
            }
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }

        public void computeVisuals()
        {
            for (PositionedStack p : ingredients)
            {
                p.generatePermutations();
            }
        }
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(131 - 5, 65 - 11, 26, 17), getOverlayIdentifier()));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiLightsaberForge.class;
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("recipe.lightsaber_forge");
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(getOverlayIdentifier()))
        {
            loadUsageRecipes(new ItemStack(ModItems.circuitry));
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
            if (result.getItem() == ModItems.lightsaber)
            {
                if (result.getDisplayName().equalsIgnoreCase("sweet dreams"))
                {
                    mc.thePlayer.playSound(ALSounds.player_lightsaber_sweet_dreams, 1, 1);
                }

                LightsaberData data = LightsaberData.get(result);
                Object[] inputs = new Object[8];
                inputs[4] = new ItemStack(ModItems.circuitry);
                inputs[5] = ItemCrystal.create(data.getColor());

                FocusingCrystal[] crystals = data.getFocusingCrystals();
                Hilt[] hilt = data.getHilt();

                for (PartType type : PartType.values())
                {
                    inputs[type.ordinal()] = ItemLightsaberPart.create(type, hilt[type.ordinal()]);
                }

                for (int i = 0; i < Math.min(crystals.length, 2); ++i)
                {
                    inputs[i + 6] = ItemFocusingCrystal.create(crystals[i]);
                }

                CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(LightsaberData.get(result), inputs);
                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        try
        {
            Item item = ingredient.getItem();
            ingredient = ingredient.copy();
            ingredient.stackSize = 1;

            if (item instanceof ItemLightsaberPart)
            {
                Hilt hilt = ItemLightsaberPart.get(ingredient);
                Object[] inputs = new Object[8];

                for (PartType type : PartType.values())
                {
                    inputs[type.ordinal()] = ItemLightsaberPart.create(type, hilt);
                }

                inputs[4] = new ItemStack(ModItems.circuitry);

                for (CrystalColor color : CrystalColor.values())
                {
                    inputs[5] = ItemCrystal.create(color);

                    CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(new LightsaberData().set(hilt).set(color), inputs);
                    recipe.computeVisuals();
                    arecipes.add(recipe);
                }
            }
            else if (item == Item.getItemFromBlock(ModBlocks.lightsaberCrystal))
            {
                CrystalColor color = ItemCrystal.get(ingredient);
                Object[] inputs = new Object[8];

                inputs[4] = new ItemStack(ModItems.circuitry);
                inputs[5] = ingredient;

                for (Hilt hilt : Hilt.REGISTRY)
                {
                    for (PartType type : PartType.values())
                    {
                        inputs[type.ordinal()] = ItemLightsaberPart.create(type, hilt);
                    }

                    CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(new LightsaberData().set(hilt).set(color), inputs);
                    recipe.computeVisuals();
                    arecipes.add(recipe);
                }
            }
            else if (item == ModItems.focusingCrystal)
            {
                FocusingCrystal crystal = ItemFocusingCrystal.get(ingredient);
                Object[] inputs = new Object[8];

                inputs[4] = new ItemStack(ModItems.circuitry);
                inputs[5] = ItemCrystal.create(CrystalColor.get(0));
                inputs[6] = ingredient;

                for (Hilt hilt : Hilt.REGISTRY)
                {
                    for (PartType type : PartType.values())
                    {
                        inputs[type.ordinal()] = ItemLightsaberPart.create(type, hilt);
                    }

                    CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(new LightsaberData().set(hilt).add(crystal), inputs);
                    recipe.computeVisuals();
                    arecipes.add(recipe);
                }
            }
            else if (item == ModItems.circuitry)
            {
                for (Hilt hilt : Hilt.REGISTRY)
                {
                    Object[] inputs = new Object[8];
                    inputs[4] = ingredient;

                    for (PartType type : PartType.values())
                    {
                        inputs[type.ordinal()] = ItemLightsaberPart.create(type, hilt);
                    }

                    LightsaberData data = new LightsaberData().set(hilt).set(hilt.getColor());
                    Collection<FocusingCrystal> crystals = hilt.getFocusingCrystals();

                    for (int i = 0; i < Math.min(crystals.size(), 2); ++i)
                    {
                        FocusingCrystal crystal = Iterables.get(crystals, i);
                        inputs[i + 6] = ItemFocusingCrystal.create(crystal);
                        data.add(crystal);
                    }

                    inputs[5] = ItemCrystal.create(hilt.getColor());

                    CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(data, inputs);
                    recipe.computeVisuals();
                    arecipes.add(recipe);
                }
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
        return Lightsabers.MODID + ":textures/gui/container/lightsaber_forge.png";
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "lightsaber_forge";
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedLightsaberForgeRecipe recipe1 = (CachedLightsaberForgeRecipe) arecipes.get(recipe);
        GuiDraw.drawString(StatCollector.translateToLocalFormatted("%s cm", ItemStack.field_111284_a.format(recipe1.data.getHeightCm())), 40, 54 - GuiDraw.fontRenderer.FONT_HEIGHT, -1);
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 102);

        int guiLeft = (mc.currentScreen.width - 176) / 2;
        int guiTop = (mc.currentScreen.height - 166) / 2;
        float spin = mc.thePlayer.ticksExisted + ClientEventHandler.renderTick;
        CachedLightsaberForgeRecipe recipe1 = (CachedLightsaberForgeRecipe) arecipes.get(recipe);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glTranslatef(105, 29, 20);
        GL11.glRotatef((float) Math.sin(spin / 20) * 2.5F, 0, 1, 0);
        GL11.glRotatef((float) Math.sin(spin / 20 + 2) * 2.5F, 0, 0, 1);
        GL11.glRotatef(-90, 0, 0, 1);
        GL11.glRotatef(90 + spin, 0, 1, 0);
        GL11.glScalef(-20, 20, 20);
        RenderHelper.enableGUIStandardItemLighting();
        ALRenderHelper.startGlScissor(guiLeft + 43, guiTop + 22, 113, 47);
        ALRenderHelper.renderLightsaber(recipe1.data, recipe1.result.item, false);
        ALRenderHelper.endGlScissor();
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }
}
