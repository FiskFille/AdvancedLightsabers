package com.fiskmods.lightsabers.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCircuitry extends Item implements ILightsaberComponent
{
    @Override
    public long getFingerprint(ItemStack stack, int slot)
    {
        return 0;
    }
    
    @Override
    public boolean isCompatibleSlot(ItemStack stack, int slot)
    {
        return slot == 4;
    }
}
