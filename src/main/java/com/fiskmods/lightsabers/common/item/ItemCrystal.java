package com.fiskmods.lightsabers.common.item;

import static com.fiskmods.lightsabers.common.lightsaber.CrystalColor.*;
import static net.minecraft.item.EnumRarity.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.generator.ModChestGen;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.utils.helper.FiskMath;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemCrystal extends ItemBlock implements ILightsaberComponent
{
    public ItemCrystal(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public long getFingerprint(ItemStack stack, int slot)
    {
        return (getId(stack) & 0xFF) << 24;
    }
    
    @Override
    public boolean isCompatibleSlot(ItemStack stack, int slot)
    {
        return slot == 5;
    }

    @Override
    public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
    {
        ItemStack itemstack = original.theItemId;
        String category = "";

        if (itemstack.hasTagCompound())
        {
            category = itemstack.getTagCompound().getString("ChestGenCategory");
        }

        List<CrystalColor> list = Lists.newArrayList();

        for (CrystalColor color : CrystalColor.values())
        {
            if (Arrays.asList(chestMap.get(color)).contains(category))
            {
                for (int i = rarityMap.get(color).ordinal() - 1; i >= 0; --i)
                {
                    list.add(color);
                }
            }
        }

        if (list.isEmpty())
        {
            list.addAll(Arrays.asList(CrystalColor.values()));
        }
        
        return new WeightedRandomChestContent(create(list.get(rand.nextInt(list.size()))), original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
    }
    
    @Override
    public EnumRarity getRarity(ItemStack itemstack)
    {
        return rarityMap.get(get(itemstack));
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean inHand)
    {
        if (!world.isRemote)
        {
            if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("color", NBT.TAG_ANY_NUMERIC))
            {
                getId(itemstack); // Makes sure any pre-1.1.2 data format is also converted server-side
            }
        }
    }
    
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
    {
        list.add(get(itemstack).getLocalizedName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemstack, int metadata)
    {
        return get(itemstack).color;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
    }
    
    public static int getId(ItemStack itemstack)
    {
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("color", NBT.TAG_ANY_NUMERIC))
        {
            itemstack.setItemDamage(itemstack.getTagCompound().getInteger("color"));
            itemstack.getTagCompound().removeTag("color");
            
            if (itemstack.getTagCompound().hasNoTags())
            {
                itemstack.setTagCompound(null);
            }
        }
        
        return itemstack.getItemDamage();
    }

    public static CrystalColor get(ItemStack itemstack)
    {
        return CrystalColor.get(getId(itemstack));
    }

    public static CrystalColor getRandomGen(Random rand)
    {
        return FiskMath.getWeightedI(rand, genRarityMap);
    }

    public static ItemStack create(CrystalColor color, Item item)
    {
        return new ItemStack(item, 1, color.id);
    }

    public static ItemStack create(CrystalColor color)
    {
        return create(color, Item.getItemFromBlock(ModBlocks.lightsaberCrystal));
    }

    public static Map<CrystalColor, EnumRarity> rarityMap = Maps.newHashMap();
    public static Map<CrystalColor, String[]> chestMap = Maps.newHashMap();

    private static Map<CrystalColor, Integer> genRarityMap = Maps.newHashMap();
    private static final int[] GEN_RARITY = {90, 30, 10, 1};

    public static void registerRarity(CrystalColor color, EnumRarity rarity, String... chests)
    {
        rarityMap.put(color, rarity);
        chestMap.put(color, chests);
        
        genRarityMap.put(color, GEN_RARITY[rarity.ordinal()]);
    }

    private static boolean hasInit = false;

    private static void init() throws Exception
    {
        if (hasInit)
        {
            return;
        }

        hasInit = true;

        registerRarity(DEEP_BLUE, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(MEDIUM_BLUE, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(LIGHT_BLUE, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(AMBER, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(YELLOW, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(GOLD, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(LIME_GREEN, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_JUNGLE_CHEST, ChestGenHooks.PYRAMID_DESERT_CHEST);
        registerRarity(GREEN, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_JUNGLE_CHEST);
        registerRarity(MINT_GREEN, common, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_JUNGLE_CHEST);

        registerRarity(MAGENTA, uncommon, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(PINK, uncommon, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.DUNGEON_CHEST);
        registerRarity(RED, uncommon, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.VILLAGE_BLACKSMITH);
        registerRarity(BLOOD_ORANGE, uncommon, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.PYRAMID_DESERT_CHEST);

        registerRarity(INDIGO, rare, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.STRONGHOLD_LIBRARY);
        registerRarity(PURPLE, rare, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.STRONGHOLD_LIBRARY);
        registerRarity(CYAN, rare, ModChestGen.SITH_TOMB_COFFIN, ModChestGen.SITH_TOMB_TREASURY, ModChestGen.SITH_TOMB_ANNEX, ChestGenHooks.STRONGHOLD_LIBRARY);

        registerRarity(ARCTIC_BLUE, epic, ModChestGen.SITH_TOMB_TREASURY, ChestGenHooks.MINESHAFT_CORRIDOR);
        registerRarity(WHITE, epic, ModChestGen.SITH_TOMB_TREASURY, ChestGenHooks.MINESHAFT_CORRIDOR);
    }

    static
    {
        try
        {
            init();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
