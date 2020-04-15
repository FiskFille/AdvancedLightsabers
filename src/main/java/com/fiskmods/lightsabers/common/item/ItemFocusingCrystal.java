package com.fiskmods.lightsabers.common.item;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ItemFocusingCrystal extends Item implements ILightsaberComponent
{
    @SideOnly(Side.CLIENT)
    public IIcon[] icons;
    @SideOnly(Side.CLIENT)
    public static IIcon outlineIcon;

    public ItemFocusingCrystal()
    {
        setHasSubtypes(true);
    }
    
    @Override
    public long getFingerprint(ItemStack stack, int slot)
    {
        return get(stack).getCode() << 32;
    }
    
    @Override
    public boolean isCompatibleSlot(ItemStack stack, int slot)
    {
        return slot == 6 || slot == 7;
    }

    @Override
    public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
    {
        return new WeightedRandomChestContent(create(getRandom(rand)), original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + get(stack).name().toLowerCase(Locale.ROOT);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (FocusingCrystal crystal : FocusingCrystal.values())
        {
            list.add(create(crystal));
        }
    }
    
    @Override
    public EnumRarity getRarity(ItemStack itemstack)
    {
        return EnumRarity.epic;
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        int i = MathHelper.clamp_int(damage, 0, FocusingCrystal.values().length - 1);
        return icons[i];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[FocusingCrystal.values().length];
        
        for (FocusingCrystal crystal : FocusingCrystal.values())
        {
            icons[crystal.ordinal()] = iconRegister.registerIcon(getIconString() + "_" + crystal.name().toLowerCase(Locale.ROOT));
        }
        
        outlineIcon = iconRegister.registerIcon(getIconString() + "_outline");
    }

    public static FocusingCrystal get(ItemStack itemstack)
    {
        return get(itemstack.getItemDamage());
    }

    public static FocusingCrystal get(int id)
    {
        return FocusingCrystal.values()[Math.abs(id) % FocusingCrystal.values().length];
    }

    public static FocusingCrystal getRandom(Random rand)
    {
        return FocusingCrystal.values()[rand.nextInt(FocusingCrystal.values().length)];
    }

    public static FocusingCrystal getRandom()
    {
        return getRandom(new Random());
    }

    public static ItemStack create(FocusingCrystal crystal)
    {
        return new ItemStack(ModItems.focusingCrystal, 1, crystal.ordinal());
    }
}
