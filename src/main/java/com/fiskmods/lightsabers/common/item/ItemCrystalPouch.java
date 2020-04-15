package com.fiskmods.lightsabers.common.item;

import java.util.List;
import java.util.UUID;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemCrystalPouch extends Item
{
    public static final UUID NULL_UUID = UUID.randomUUID();
    
    @SideOnly(Side.CLIENT)
    private IIcon overlay;
    
    public ItemCrystalPouch()
    {
        super();
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (!itemstack.hasTagCompound())
        {
            itemstack.setTagCompound(new NBTTagCompound());
        }

        player.openGui(Lightsabers.instance, 3, world, player.inventory.currentItem, 0, 0);
        return itemstack;
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean inHand)
    {
        if (!world.isRemote)
        {
            if (!itemstack.hasTagCompound() || !itemstack.getTagCompound().hasKey(ALConstants.TAG_POUCH_UUID, NBT.TAG_STRING))
            {
                getUUID(itemstack); // Assign UUID
            }
        }
    }
    
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (CrystalColor color : CrystalColor.values())
        {
            list.add(ItemCrystal.create(color, ModItems.crystalPouch));
        }
    }
    
    @Override
    public EnumRarity getRarity(ItemStack itemstack)
    {
        return ItemCrystal.rarityMap.get(ItemCrystal.get(itemstack));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
    {
        list.add(ItemCrystal.get(itemstack).getLocalizedName());
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemstack, int pass)
    {
        return pass == 1 ? ItemCrystal.get(itemstack).color : super.getColorFromItemStack(itemstack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass == 1 ? overlay : itemIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        super.registerIcons(iconRegister);
        overlay = iconRegister.registerIcon(getIconString() + "_overlay");
    }
    
    public static boolean isPouch(ItemStack stack)
    {
        return stack != null && stack.getItem() == ModItems.crystalPouch;
    }
    
    public static UUID getUUID(ItemStack stack)
    {
        if (!isPouch(stack))
        {
            return NULL_UUID;
        }
        
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        
        if (!stack.getTagCompound().hasKey(ALConstants.TAG_POUCH_UUID, NBT.TAG_STRING))
        {
            UUID uuid = UUID.randomUUID();
            stack.getTagCompound().setString(ALConstants.TAG_POUCH_UUID, uuid.toString());
            
            return uuid;
        }
        
        return UUID.fromString(stack.getTagCompound().getString(ALConstants.TAG_POUCH_UUID));
    }
}
