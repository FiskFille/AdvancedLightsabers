package com.fiskmods.lightsabers.common.item;

import java.util.List;
import java.util.Random;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemLightsaberPart extends Item implements ILightsaberComponent
{
    public final PartType partType;

    public ItemLightsaberPart(PartType type)
    {
        setMaxStackSize(16);
        setHasSubtypes(true);
        partType = type;
    }

    @Override
    public long getFingerprint(ItemStack stack, int slot)
    {
        return Hilt.REGISTRY.getIDForObject(get(stack)) << partType.ordinal() * 6;
    }

    @Override
    public boolean isCompatibleSlot(ItemStack stack, int slot)
    {
        return slot == partType.ordinal();
    }

    @Override
    public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
    {
        return new WeightedRandomChestContent(create(getRandomType(rand), Hilt.REGISTRY.getRandom(rand)), original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (Hilt hilt : Hilt.REGISTRY)
        {
            list.add(create(partType, hilt));
        }
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag)
    {
        list.add(get(itemstack).getLocalizedName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
    }

    public static Hilt get(ItemStack itemstack)
    {
        int id = 0;

        if (itemstack.hasTagCompound())
        {
            if (itemstack.getTagCompound().hasKey("lightsaber", NBT.TAG_STRING))
            {
                id = Hilt.REGISTRY.getIDForObject(Hilt.REGISTRY.getObject(Hilt.LEGACY_MAPPINGS.get(itemstack.getTagCompound().getString("lightsaber"))));
                itemstack.getTagCompound().removeTag("lightsaber");
                itemstack.getTagCompound().removeTag("type");
                itemstack.getTagCompound().setByte(ALConstants.TAG_PART, (byte) id);
            }
            else if (itemstack.getTagCompound().hasKey(ALConstants.TAG_PART, NBT.TAG_ANY_NUMERIC))
            {
                id = itemstack.getTagCompound().getByte(ALConstants.TAG_PART);
            }
        }

        return Hilt.REGISTRY.getObjectById(id);
    }

    public static Item getItem(PartType type)
    {
        switch (type)
        {
        case EMITTER:
            return ModItems.emitter;
        case SWITCH_SECTION:
            return ModItems.switchSection;
        case BODY:
            return ModItems.grip;
        case POMMEL:
            return ModItems.pommel;
        }

        return null;
    }

    public static ItemStack create(PartType type, Hilt hilt)
    {
        ItemStack itemstack = new ItemStack(getItem(type));
        itemstack.setTagCompound(new NBTTagCompound());
        itemstack.getTagCompound().setByte(ALConstants.TAG_PART, (byte) Hilt.REGISTRY.getIDForObject(hilt));

        return itemstack;
    }

    public static PartType getType(ItemStack itemstack)
    {
        return itemstack.getItem() instanceof ItemLightsaberPart ? ((ItemLightsaberPart) itemstack.getItem()).partType : null;
    }

    public static PartType getRandomType(Random rand)
    {
        return PartType.values()[rand.nextInt(PartType.values().length)];
    }

    public static PartType getRandomType()
    {
        return getRandomType(new Random());
    }
}
