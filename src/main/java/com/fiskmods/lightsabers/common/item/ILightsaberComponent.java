package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Implement this on your {@link Item} to be used in a Lightsaber Forge.
 * 
 * @author FiskFille
 */
public interface ILightsaberComponent
{
    /**
     * @param stack - the item in question
     * @param slot - the Lightsaber Forge slot the item is placed in
     * @return The bits that this item will add to a {@link LightsaberData} object.
     */
    long getFingerprint(ItemStack stack, int slot);
    
    /**
     * @return true if this item can be placed in the specified slot.
     */
    boolean isCompatibleSlot(ItemStack stack, int slot);
}
