package fiskfille.lightsabers.common.item;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;

public class ItemCrystal extends ItemBlock
{
    public ItemCrystal(Block block)
    {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
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

        List<Integer> list = Lists.newArrayList();

        for (int color : LightsaberColors.getColors())
        {
            int colorId = LightsaberHelper.getCrystalIdFromColor(color);
            String[] chests = LightsaberColors.chestMap.get(colorId);
            boolean flag = false;

            for (String s : chests)
            {
                if (s.equals(category))
                {
                    flag = true;
                }
            }

            if (!flag)
            {
                continue;
            }

            for (int i = 0; i < 4 - LightsaberColors.rarityMap.get(colorId); ++i)
            {
                list.add(colorId);
            }
        }

        if (list.isEmpty())
        {
            for (int color : LightsaberColors.getColors())
            {
                list.add(LightsaberHelper.getCrystalIdFromColor(color));
            }
        }

        itemstack = LightsaberHelper.createCrystal(list.get(rand.nextInt(list.size())));
        return new WeightedRandomChestContent(itemstack, original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack)
    {
        return StatCollector.translateToLocalFormatted("tile.lightsaber_crystal.name", LightsaberColors.getColorName(LightsaberHelper.getCrystalColorId(itemstack)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemstack, int metadata)
    {
        return LightsaberColors.getColors()[LightsaberHelper.getCrystalColorId(itemstack) % LightsaberColors.getColors().length];
    }
}
