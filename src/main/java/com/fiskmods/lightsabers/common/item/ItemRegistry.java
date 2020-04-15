package com.fiskmods.lightsabers.common.item;

import java.util.List;

import com.fiskmods.lightsabers.Lightsabers;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemRegistry
{
    public static void registerItem(Item item, String unlocalizedName)
    {
        item.setCreativeTab(Lightsabers.CREATIVE_TAB);
        registerItemNoTab(item, unlocalizedName);
    }

    public static void registerIngot(Item item, String unlocalizedName, String oreDictName)
    {
        registerItem(item, unlocalizedName);

        if (item.getHasSubtypes())
        {
            List<ItemStack> list = Lists.newArrayList();
            item.getSubItems(item, item.getCreativeTab(), list);

            for (ItemStack itemstack : list)
            {
                OreDictionary.registerOre(oreDictName, itemstack);
            }
        }
        else
        {
            OreDictionary.registerOre(oreDictName, item);
        }
    }

    public static void registerItemNoTab(Item item, String unlocalizedName)
    {
        item.setUnlocalizedName(unlocalizedName);
        item.setTextureName(Lightsabers.MODID + ":" + unlocalizedName);

        GameRegistry.registerItem(item, unlocalizedName);
    }
}
