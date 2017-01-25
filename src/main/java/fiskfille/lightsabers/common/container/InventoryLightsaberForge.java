package fiskfille.lightsabers.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryLightsaberForge implements IInventory
{
    private ItemStack[] stackList = new ItemStack[8];
    private Container eventHandler;

    public InventoryLightsaberForge(Container container)
    {
        eventHandler = container;
    }

    @Override
    public int getSizeInventory()
    {
        return stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return slot >= getSizeInventory() ? null : stackList[slot];
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
        if (stackList[slot] != null)
        {
            ItemStack itemstack = stackList[slot];
            stackList[slot] = null;
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
        if (stackList[slot] != null)
        {
            ItemStack itemstack;

            if (stackList[slot].stackSize <= amount)
            {
                itemstack = stackList[slot];
                stackList[slot] = null;
                eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
            else
            {
                itemstack = stackList[slot].splitStack(amount);

                if (stackList[slot].stackSize == 0)
                {
                    stackList[slot] = null;
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
        stackList[slot] = itemstack;
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
