package fiskfille.lightsabers.nei;

import static codechicken.lib.gui.GuiDraw.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.LightsaberAPI;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.gui.GuiLightsaberForge;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.event.ClientEventHandler;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ItemLightsaberPart;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class LightsaberForgeRecipeHandler extends TemplateRecipeHandler
{
    public class CachedLightsaberForgeRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedLightsaberForgeRecipe(ItemStack out, Object[] items)
        {
            result = new PositionedStack(out, 131, 76);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(items);
        }

        public void setIngredients(Object[] items)
        {
            addSlotToContainer(0, 20, 17, items);
            addSlotToContainer(1, 20, 35, items);
            addSlotToContainer(2, 20, 53, items);
            addSlotToContainer(3, 20, 71, items);
            addSlotToContainer(4, 43, 71, items);
            addSlotToContainer(5, 66, 71, items);

            addSlotToContainer(6, 89, 71, items);
            addSlotToContainer(7, 107, 71, items);
        }

        private void addSlotToContainer(int id, int x, int y, Object[] items)
        {
            if (items[id] != null)
            {
                PositionedStack stack = new PositionedStack(items[id], x - 5, y - 11, false);
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
        super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        if (result.getItem() == ModItems.lightsaber)
        {
            if (result.getDisplayName().equalsIgnoreCase("sweet dreams"))
            {
                Minecraft.getMinecraft().thePlayer.playSound(ALSounds.player_lightsaber_sweet_dreams, 1.0F, 1.0F);
            }

            Object[] aobject = new Object[8];
            EnumPartType[] types = {EnumPartType.EMITTER, EnumPartType.SWITCH_SECTION, EnumPartType.BODY, EnumPartType.POMMEL};

            for (int i = 0; i < types.length; ++i)
            {
                EnumPartType type = types[i];
                aobject[i] = LightsaberHelper.createLightsaberPart(LightsaberHelper.getPart(result, type), type);
            }

            aobject[4] = new ItemStack(ModItems.lightsaberCircuitry);
            aobject[5] = LightsaberHelper.createCrystal(LightsaberHelper.getColorId(result));
            int[] aint = LightsaberHelper.getFocusingCrystalIds(result);

            for (int i = 0; i < aint.length; ++i)
            {
                int id = aint[i];
                aobject[i + 6] = LightsaberHelper.createFocusingCrystal(id);
            }

            CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(result, aobject);
            recipe.computeVisuals();
            arecipes.add(recipe);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        ItemStack itemstack = ingredient.copy();
        itemstack.stackSize = 1;
        Item item = ingredient.getItem();

        if (item instanceof ItemLightsaberPart)
        {
            EnumPartType type = LightsaberHelper.getPartType(ingredient);
            EnumPartType[] types = {EnumPartType.EMITTER, EnumPartType.SWITCH_SECTION, EnumPartType.BODY, EnumPartType.POMMEL};

            for (int i = 0; i < LightsaberColors.getColors().length; ++i)
            {
                Object[] aobject = new Object[8];

                for (int j = 0; j < types.length; ++j)
                {
                    EnumPartType type1 = types[j];
                    aobject[j] = LightsaberHelper.createLightsaberPart(LightsaberHelper.getLightsaberFromPart(ingredient), type1);
                }

                aobject[4] = new ItemStack(ModItems.lightsaberCircuitry);
                aobject[5] = LightsaberHelper.createCrystal(i);

                CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(LightsaberHelper.createLightsaber(i, LightsaberHelper.getLightsaberFromPart(ingredient), null), aobject);
                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
        else if (item == Item.getItemFromBlock(ModBlocks.lightsaberCrystal))
        {
            for (Lightsaber lightsaber : LightsaberAPI.getLightsabers())
            {
                Object[] aobject = new Object[8];
                EnumPartType[] types = {EnumPartType.EMITTER, EnumPartType.SWITCH_SECTION, EnumPartType.BODY, EnumPartType.POMMEL};

                for (int i = 0; i < types.length; ++i)
                {
                    EnumPartType type1 = types[i];
                    aobject[i] = LightsaberHelper.createLightsaberPart(lightsaber, type1);
                }

                aobject[4] = new ItemStack(ModItems.lightsaberCircuitry);
                aobject[5] = itemstack;

                CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(LightsaberHelper.createLightsaber(LightsaberHelper.getCrystalColorId(ingredient), lightsaber, null), aobject);
                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
        else if (item == ModItems.focusingCrystal)
        {
            for (Lightsaber lightsaber : LightsaberAPI.getLightsabers())
            {
                Object[] aobject = new Object[8];
                EnumPartType[] types = {EnumPartType.EMITTER, EnumPartType.SWITCH_SECTION, EnumPartType.BODY, EnumPartType.POMMEL};

                for (int i = 0; i < types.length; ++i)
                {
                    EnumPartType type1 = types[i];
                    aobject[i] = LightsaberHelper.createLightsaberPart(lightsaber, type1);
                }

                aobject[4] = new ItemStack(ModItems.lightsaberCircuitry);
                aobject[5] = LightsaberHelper.createCrystal(0);
                aobject[6] = itemstack;

                CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(LightsaberHelper.createLightsaber(LightsaberHelper.getCrystalColorId(ingredient), lightsaber, LightsaberHelper.getFocusingCrystalId(ingredient)), aobject);
                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
        else if (item == ModItems.lightsaberCircuitry)
        {
            for (Lightsaber lightsaber : LightsaberAPI.getLightsabers())
            {
                Object[] aobject = new Object[8];
                EnumPartType[] types = {EnumPartType.EMITTER, EnumPartType.SWITCH_SECTION, EnumPartType.BODY, EnumPartType.POMMEL};

                for (int i = 0; i < types.length; ++i)
                {
                    EnumPartType type1 = types[i];
                    aobject[i] = LightsaberHelper.createLightsaberPart(lightsaber, type1);
                }

                aobject[4] = itemstack;
                aobject[5] = LightsaberHelper.createCrystal(0);

                CachedLightsaberForgeRecipe recipe = new CachedLightsaberForgeRecipe(LightsaberHelper.createLightsaber(LightsaberHelper.getCrystalColorId(ingredient), lightsaber), aobject);
                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public String getGuiTexture()
    {
        return Lightsabers.modid + ":textures/gui/container/lightsaber_forge.png";
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "lightsaber_forge";
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 102);

        Minecraft mc = Minecraft.getMinecraft();
        int k = -5;
        int l = -11;
        int guiLeft = (mc.currentScreen.width - 176) / 2;
        int guiTop = (mc.currentScreen.height - 166) / 2;

        CachedLightsaberForgeRecipe recipe1 = getForgeRecipes(null).get(recipe);
        ItemStack itemstack = recipe1.result.item;

        if (itemstack != null)
        {
            itemstack = itemstack.copy();
            itemstack.getTagCompound().setBoolean("active", true);

            GuiDraw.drawString(StatCollector.translateToLocalFormatted("%s cm", ItemStack.field_111284_a.format(LightsaberHelper.getLightsaberHeightCm(itemstack))), k + 45, l + 64 - GuiDraw.fontRenderer.FONT_HEIGHT, -1);

            float spin = mc.thePlayer.ticksExisted + ClientEventHandler.RENDER_TICK;
            short short1 = 240;
            short short2 = 240;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1 / 1.0F, short2 / 1.0F);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glPushMatrix();
            ALRenderHelper.startGlScissor(guiLeft + 43, guiTop + 22, 113, 47);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glTranslatef(k + 110, l + 40, 50.0F);

            GL11.glRotatef((float) Math.sin(spin / 20) * 2.5F, 0, 1, 0);
            GL11.glRotatef((float) Math.sin(spin / 20 + 2) * 2.5F, 0, 0, 1);
            GL11.glRotatef(-90, 0, 0, 1);
            GL11.glRotatef(90 + spin, 0, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();

            float height = LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
            float f = (-(LightsaberHelper.getPart(itemstack, EnumPartType.BODY).getBody().height + LightsaberHelper.getPart(itemstack, EnumPartType.POMMEL).getPommel().height) + height / 2) * 0.0625F;
            float scale = 50.0F;
            GL11.glScalef(-scale, scale, scale);

            GL11.glPushMatrix();
            scale = 0.5F * 0.9F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            ALRenderHelper.renderLightsaberHilt(itemstack);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            scale = 0.5F * 0.9F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, f, 0);
            GL11.glScalef(1 / scale, 1 / scale, 1 / scale);
            scale = 1.5F * 0.9F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, -(LightsaberHelper.getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + LightsaberHelper.getPart(itemstack, EnumPartType.EMITTER).getEmitter().height) * 0.03125F * 0.75F, 0);
            ALRenderHelper.renderLightsaberBlade(itemstack, false);
            GL11.glPopMatrix();
            ALRenderHelper.endGlScissor();
            GL11.glPopMatrix();
            RenderHelper.disableStandardItemLighting();
        }
    }

    public List<CachedLightsaberForgeRecipe> getForgeRecipes(LightsaberForgeRecipeHandler handler)
    {
        List<CachedLightsaberForgeRecipe> list = Lists.newArrayList();

        for (CachedRecipe recipe : handler == null ? arecipes : handler.arecipes)
        {
            list.add((CachedLightsaberForgeRecipe) recipe);
        }

        return list;
    }
}
