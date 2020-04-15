package com.fiskmods.lightsabers.common.container;

import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSithCoffin extends ContainerBasic
{
    private int numRows;

    public ContainerSithCoffin(InventoryPlayer inventoryPlayer, TileEntitySithCoffin tile)
    {
        super(tile);
        numRows = tile.getSizeInventory() / 9;

        for (int i = 0; i < numRows; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                addSlotToContainer(new Slot(tile, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        addPlayerInventory(inventoryPlayer, 2 + (numRows - 3) * 18);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int id)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(id);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (id < numRows * 9)
            {
                if (!mergeItemStack(itemstack1, numRows * 9, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, numRows * 9, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
