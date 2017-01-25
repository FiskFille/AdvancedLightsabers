package fiskfille.lightsabers.common.item;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.LightsaberAPI;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class ItemLightsaberPart extends Item
{
    public final EnumPartType partType;

    public ItemLightsaberPart(EnumPartType type)
    {
        super();
        partType = type;
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
    {
        ItemStack itemstack = LightsaberHelper.createLightsaberPart(LightsaberAPI.getLightsabers().get(rand.nextInt(LightsaberAPI.getLightsabers().size())), EnumPartType.values()[rand.nextInt(EnumPartType.values().length)]);
        return new WeightedRandomChestContent(itemstack, original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
    }

    public static void refreshNBT(ItemStack itemstack)
    {
        if (!itemstack.hasTagCompound())
        {
            itemstack.setTagCompound(new NBTTagCompound());
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (Lightsaber lightsaber : LightsaberAPI.getLightsabers())
        {
            list.add(LightsaberHelper.createLightsaberPart(lightsaber, partType));
        }
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag)
    {
        list.add(LightsaberHelper.getLightsaberFromPart(itemstack).getName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IIconRegister)
    {
    }
}
