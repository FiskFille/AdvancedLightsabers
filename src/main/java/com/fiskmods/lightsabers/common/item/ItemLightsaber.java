package com.fiskmods.lightsabers.common.item;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.relauncher.Side;
import mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent;
import mods.battlegear2.api.weapons.IBattlegearWeapon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@InterfaceList(@Interface(modid = "battlegear2", iface = "mods.battlegear2.api.weapons.IBattlegearWeapon"))
public class ItemLightsaber extends ItemLightsaberBase implements IBattlegearWeapon
{
    @Override
    public String getItemStackDisplayName(ItemStack itemstack)
    {
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(ALConstants.TAG_LIGHTSABER_SPECIAL, NBT.TAG_STRING))
        {
            return "FISHSTICKS!!";
        }
        
        return super.getItemStackDisplayName(itemstack);
    }
    
    @Override
    public EnumRarity getRarity(ItemStack itemstack)
    {
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(ALConstants.TAG_LIGHTSABER_SPECIAL, NBT.TAG_STRING))
        {
            return EnumRarity.rare;
        }
        
        return EnumRarity.common;
    }
    
    @Override
    public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
    {
        ItemStack itemstack = original.theItemId;

        if (itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("SithTombLoot"))
        {
            itemstack = LightsaberData.createRandom(rand, rand.nextInt(5) == 0 ? CrystalColor.PURPLE : CrystalColor.RED);

            if (rand.nextInt(4) == 0)
            {
                itemstack = ItemDoubleLightsaber.create(itemstack, itemstack);
            }
        }
        else if (itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("JediTempleLoot"))
        {
            itemstack = LightsaberData.createRandom(rand, rand.nextBoolean() ? CrystalColor.MEDIUM_BLUE : CrystalColor.GREEN);

            if (rand.nextInt(8) == 0)
            {
                itemstack = ItemDoubleLightsaber.create(itemstack, itemstack);
            }
        }
        else
        {
            itemstack = LightsaberData.createRandom(rand);
        }

        return new WeightedRandomChestContent(itemstack, original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean inHand)
    {
        if (!world.isRemote)
        {
            if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("Lightsaber", NBT.TAG_COMPOUND))
            {
                LightsaberData.get(itemstack); // Makes sure any pre-1.2 data format is also converted server-side
            }
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (Hilt hilt : Hilt.REGISTRY)
        {
            if (hilt.getType() == Hilt.Type.SINGLE)
            {
                list.add(hilt.createDefault().create());
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
    {
        LightsaberData data = LightsaberData.get(itemstack);
        Hilt[] hilt = data.getHilt();
        String space = "  ";

        list.add(StatCollector.translateToLocal("lightsaber.color"));
        list.add(space + data.getColor().getLocalizedName());
        list.add(StatCollector.translateToLocal("lightsaber.hilt"));

        if (data.isHiltUniform())
        {
            list.add(space + hilt[0].getLocalizedName());
        }
        else
        {
            for (int i = 0; i < hilt.length; ++i)
            {
                list.add(space + hilt[i].getLocalizedName());
            }
        }

        FocusingCrystal[] crystals = data.getFocusingCrystals();

        if (crystals.length > 0)
        {
            list.add(StatCollector.translateToLocal("lightsaber.focusingCrystals"));

            for (FocusingCrystal crystal : crystals)
            {
                list.add(space + crystal.getLocalizedName());
            }
        }
        
        if (advanced)
        {
            list.add(StatCollector.translateToLocalFormatted("lightsaber.code.single", Long.toHexString(data.hash).toUpperCase(Locale.ROOT)));
        }
    }
    
    @Override
    public boolean allowOffhand(ItemStack main, ItemStack off)
    {
        if (main != null && main.getItem() == ModItems.doubleLightsaber)
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean sheatheOnBack(ItemStack item)
    {
        return false;
    }

    @Override
    public boolean isOffhandHandDual(ItemStack off)
    {
        return true;
    }

    @Override
    public boolean offhandAttackEntity(OffhandAttackEvent event, ItemStack mainhandItem, ItemStack offhandItem)
    {
        return false;
    }

    @Override
    public boolean offhandClickAir(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem)
    {
        return false;
    }

    @Override
    public boolean offhandClickBlock(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem)
    {
        return false;
    }

    @Override
    public void performPassiveEffects(Side effectiveSide, ItemStack mainhandItem, ItemStack offhandItem)
    {
    }
}
