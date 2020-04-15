package com.fiskmods.lightsabers.common.container;

import com.fiskmods.lightsabers.common.item.ILightsaberComponent;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryLightsaberForge implements IInventory
{
    private ItemStack[] inventory = new ItemStack[8];
    private Container eventHandler;

    public LightsaberData result;

    public InventoryLightsaberForge(Container container)
    {
        eventHandler = container;
    }

    public LightsaberData updateResult()
    {
        long hash = 0;
        
        for (int slot = 0; slot < getSizeInventory(); ++slot)
        {
            ItemStack stack = getStackInSlot(slot);
            
            if (stack != null && stack.getItem() instanceof ILightsaberComponent)
            {
                ILightsaberComponent component = (ILightsaberComponent) stack.getItem();
                long fingerprint = component.getFingerprint(stack, slot);
                
                if (!component.isCompatibleSlot(stack, slot) || fingerprint != 0 && hash == (hash |= fingerprint))
                {
                    return null;
                }
                
                continue;
            }
            else if (stack == null && (slot == 6 || slot == 7))
            {
                continue;
            }
            
            if (slot != 5 || stack == null || stack.getItem() != Items.fish)
            {
                return null;
            }
        }

        return new LightsaberData(hash);
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return slot >= getSizeInventory() ? null : inventory[slot];
    }

    @Override
    public String getInventoryName()
    {
        return "container.crafting";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (inventory[slot] != null)
        {
            ItemStack itemstack = inventory[slot];
            inventory[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (inventory[slot] != null)
        {
            ItemStack itemstack;

            if (inventory[slot].stackSize <= amount)
            {
                itemstack = inventory[slot];
                inventory[slot] = null;
                eventHandler.onCraftMatrixChanged(this);

                return itemstack;
            }
            else
            {
                itemstack = inventory[slot].splitStack(amount);

                if (inventory[slot].stackSize == 0)
                {
                    inventory[slot] = null;
                }

                eventHandler.onCraftMatrixChanged(this);

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        inventory[slot] = itemstack;
        eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory()
    {
    }

    @Override
    public void closeInventory()
    {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        return true;
    }
}
