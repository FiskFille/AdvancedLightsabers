package com.fiskmods.lightsabers.common.block;

import java.util.Iterator;
import java.util.Locale;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.ItemBlockWithMetadata;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.oredict.OreDictionary;

public class BlockRegistry
{
    public static void registerBlock(Block block, String name)
    {
        String unlocalizedName = name.toLowerCase(Locale.ROOT).replaceAll(" ", "_").replaceAll("'", "");

        block.setBlockName(unlocalizedName);
        block.setBlockTextureName(Lightsabers.MODID + ":" + unlocalizedName);
        block.setCreativeTab(Lightsabers.CREATIVE_TAB);

        GameRegistry.registerBlock(block, unlocalizedName);
    }

    public static void registerItemBlock(Block block, String name, Class clazz)
    {
        String unlocalizedName = name.toLowerCase(Locale.ROOT).replaceAll(" ", "_").replaceAll("'", "");

        block.setBlockName(unlocalizedName);
        block.setBlockTextureName(Lightsabers.MODID + ":" + unlocalizedName);
        block.setCreativeTab(Lightsabers.CREATIVE_TAB);

        GameRegistry.registerBlock(block, clazz, unlocalizedName);
    }

    public static void registerItemBlock(Block block, String name, ItemBlock item)
    {
        String unlocalizedName = name.toLowerCase(Locale.ROOT).replaceAll(" ", "_").replaceAll("'", "");
        registerItemBlock(block, name, (Class) null);

        Iterator iterator = Block.blockRegistry.getKeys().iterator();

        while (iterator.hasNext())
        {
            String s = (String) iterator.next();
            Block block1 = (Block) Block.blockRegistry.getObject(s);

            if (block == block1)
            {
                Item.itemRegistry.addObject(Block.getIdFromBlock(block), s, item.setUnlocalizedName(unlocalizedName));
                break;
            }
        }
    }

    public static void registerItemBlock(Block block, String name)
    {
        registerItemBlock(block, name, ItemBlockWithMetadata.class);
    }

    public static void registerOre(Block block, String name, String oreDictName)
    {
        registerBlock(block, name);
        OreDictionary.registerOre(oreDictName, block);
    }

    public static void registerOreAsTileEntity(Block block, String name, String oreDictName, Class clazz)
    {
        registerOre(block, name, oreDictName);
        GameRegistry.registerTileEntity(clazz, name);
    }

    public static void registerTileEntity(Block block, String name, Class clazz)
    {
        registerBlock(block, name);
        GameRegistry.registerTileEntity(clazz, name);
    }

    public static void registerItemBlockAsTileEntity(Block block, String name, Class clazz, Class clazz1)
    {
        registerItemBlock(block, name, clazz1);
        GameRegistry.registerTileEntity(clazz, name);
    }

    public static void registerItemBlockAsTileEntity(Block block, String name, Class clazz)
    {
        registerItemBlock(block, name, ItemBlockWithMetadata.class);
        GameRegistry.registerTileEntity(clazz, name);
    }
}
