package fiskfille.lightsabers.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import fiskfille.lightsabers.common.tileentity.TileEntitySithCoffin;

public class ContainerSithCoffin extends ContainerBasic
{
    private TileEntitySithCoffin tileentity;
    private int numRows;

    public ContainerSithCoffin(InventoryPlayer inventoryPlayer, TileEntitySithCoffin tile)
    {
        tileentity = tile;
        numRows = tile.getSizeInventory() / 9;
        tile.openInventory();

        for (int i = 0; i < numRows; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                addSlotToContainer(new Slot(tile, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        addPlayerInventory(inventoryPlayer, 2 + (numRows - 3) * 18);
    }

    public boolean canInteractWith(EntityPlayer player)
    {
        return tileentity.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int id)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(id);

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
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        tileentity.closeInventory();
    }
}
