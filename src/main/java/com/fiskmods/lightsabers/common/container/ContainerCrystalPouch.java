package com.fiskmods.lightsabers.common.container;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemCrystalPouch;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerCrystalPouch extends ContainerBasic
{
    public InventoryCrystalPouch inventory;

    public ContainerCrystalPouch(InventoryPlayer inventoryPlayer, InventoryCrystalPouch inventoryPouch)
    {
        super(null);
        inventory = inventoryPouch;

        for (int i = 0; i < inventoryPouch.getSizeInventory(); ++i)
        {
            final int slot = i;
            addSlotToContainer(new Slot(inventoryPouch, i, 8 + (i % 9) * 18, 18 + i / 9 * 18)
            {
                @Override
                public boolean isItemValid(ItemStack itemstack)
                {
                    return inventory.isItemValidForSlot(slot, itemstack);
                }
            });
        }

        addPlayerInventory(inventoryPlayer, -16);
    }

    @Override
    public Slot makeInventorySlot(InventoryPlayer inventoryPlayer, int index, int x, int y)
    {
        if (index == inventory.itemSlot)
        {
            return new SlotFrozen(inventoryPlayer, index, x, y);
        }

        return new SlotExclusive(inventoryPlayer, index, x, y);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotId);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (itemstack != null && itemstack.getItem() instanceof ItemCrystalPouch)
            {
                return null;
            }

            if (slotId < inventory.getSizeInventory())
            {
                if (!mergeItemStack(itemstack1, inventory.getSizeInventory(), inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (itemstack1.getItem() == Item.getItemFromBlock(ModBlocks.lightsaberCrystal))
            {
                int id = ItemCrystal.getId(itemstack1);
                
                if (!mergeItemStack(itemstack1, id, id + 1, false))
                {
                    return null;
                }
            }
            else
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    private class SlotFrozen extends Slot
    {
        private SlotFrozen(IInventory inventory, int index, int x, int y)
        {
            super(inventory, index, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return false;
        }
        
        @Override
        public boolean canTakeStack(EntityPlayer player)
        {
            return false;
        }
    }

    private class SlotExclusive extends Slot
    {
        private SlotExclusive(IInventory inventory, int index, int x, int y)
        {
            super(inventory, index, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return !ItemCrystalPouch.getUUID(stack).equals(ContainerCrystalPouch.this.inventory.uuid);
        }
    }
}
