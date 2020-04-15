package com.fiskmods.lightsabers.common.container;

import static com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation.*;

import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDisassemblyStation extends ContainerBasic<TileEntityDisassemblyStation>
{
    private int lastProgress;
    private int lastFuelTicks;
    private int lastMaxFuelTicks;

    public ContainerDisassemblyStation(InventoryPlayer inventoryPlayer, TileEntityDisassemblyStation tile)
    {
        super(tile);
        addSlotToContainer(new ValidityChecked(INPUT, 16, 18));
        addSlotToContainer(new ValidityChecked(FUEL, 16, 54));

        for (int i = 0; i < 15; ++i)
        {
            addSlotToContainer(new ValidityChecked(i + 2, 72 + 18 * (i % 5), 18 + 18 * (i / 5)));
        }

        addPlayerInventory(inventoryPlayer, 2);
    }

    @Override
    public void addCraftingToCrafters(ICrafting icrafting)
    {
        super.addCraftingToCrafters(icrafting);
        icrafting.sendProgressBarUpdate(this, 0, tileentity.progress);
        icrafting.sendProgressBarUpdate(this, 1, tileentity.fuelTicks);
        icrafting.sendProgressBarUpdate(this, 2, tileentity.maxFuelTicks);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting) crafters.get(i);

            if (lastProgress != tileentity.progress)
            {
                icrafting.sendProgressBarUpdate(this, 0, tileentity.progress);
            }

            if (lastFuelTicks != tileentity.fuelTicks)
            {
                icrafting.sendProgressBarUpdate(this, 1, tileentity.fuelTicks);
            }

            if (lastMaxFuelTicks != tileentity.maxFuelTicks)
            {
                icrafting.sendProgressBarUpdate(this, 2, tileentity.maxFuelTicks);
            }
        }

        lastProgress = tileentity.progress;
        lastFuelTicks = tileentity.fuelTicks;
        lastMaxFuelTicks = tileentity.maxFuelTicks;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int index, int value)
    {
        if (index == 0)
        {
            tileentity.progress = value;
        }
        else if (index == 1)
        {
            tileentity.fuelTicks = value;
        }
        else if (index == 2)
        {
            tileentity.maxFuelTicks = value;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotId);
        int OUTPUT_MIN = 2;
        int OUTPUT_MAX = 16;

        if (slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (slotId >= OUTPUT_MIN && slotId <= OUTPUT_MAX)
            {
                if (!mergeItemStack(stack, OUTPUT_MAX + 1, OUTPUT_MAX + 36 + 1, true))
                {
                    return null;
                }

                slot.onSlotChange(stack, itemstack);
            }
            else if (slotId > OUTPUT_MAX)
            {
                if (TileEntityDisassemblyStation.canDisassemble(stack))
                {
                    if (!mergeItemStack(stack, INPUT, INPUT + 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityDisassemblyStation.isItemFuel(stack))
                {
                    if (!mergeItemStack(stack, FUEL, FUEL + 1, false))
                    {
                        return null;
                    }
                }
                else if (slotId >= OUTPUT_MAX + 1 && slotId < OUTPUT_MAX + 28)
                {
                    if (!mergeItemStack(stack, OUTPUT_MAX + 28, OUTPUT_MAX + 37, false))
                    {
                        return null;
                    }
                }
                else if (slotId >= OUTPUT_MAX + 28 && slotId < OUTPUT_MAX + 37 && !mergeItemStack(stack, OUTPUT_MAX + 1, OUTPUT_MAX + 28, false))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(stack, OUTPUT_MAX + 1, OUTPUT_MAX + 37, false))
            {
                return null;
            }

            if (stack.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stack.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, stack);
        }

        return itemstack;
    }

    private class ValidityChecked extends Slot
    {
        public ValidityChecked(int id, int x, int y)
        {
            super(tileentity, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack itemstack)
        {
            return tileentity.isItemValidForSlot(getSlotIndex(), itemstack);
        }
    }
}
