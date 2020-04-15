package com.fiskmods.lightsabers.common.container;

import java.util.UUID;
import java.util.concurrent.Callable;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemCrystalPouch;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;

import fiskfille.utils.helper.FiskServerUtils;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.util.Constants.NBT;

public class InventoryCrystalPouch implements IInventory
{
    public final EntityPlayer thePlayer;
    public final UUID uuid;
    
    public final int itemSlot;

    private ItemStack[] itemstacks = new ItemStack[CrystalColor.values().length];

    public InventoryCrystalPouch(EntityPlayer player, int slot)
    {
        thePlayer = player;
        itemSlot = slot;
        
        ItemStack stack = getPouchStack();
        uuid = ItemCrystalPouch.getUUID(stack);

        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        readFromNBT(stack.getTagCompound());
    }
    
    public ItemStack getPouchStack()
    {
        return FiskServerUtils.getStackInSlot(thePlayer, itemSlot);
    }
    
    public boolean addItemStackToInventory(ItemStack itemstack)
    {
        if (addItemStackToInventoryTemp(itemstack))
        {
            markDirty();
            return true;
        }

        return false;
    }

    private boolean addItemStackToInventoryTemp(final ItemStack itemstack)
    {
        if (itemstack != null && itemstack.stackSize != 0 && itemstack.getItem() != null)
        {
            try
            {
                if (itemstack.isItemDamaged())
                {
                    int slot = getFirstEmptyStack(itemstack);

                    if (slot >= 0)
                    {
                        itemstacks[slot] = ItemStack.copyItemStack(itemstack);
                        itemstacks[slot].animationsToGo = 5;
                        itemstack.stackSize = 0;
                        return true;
                    }

                    return false;
                }

                int stackSize;

                do
                {
                    stackSize = itemstack.stackSize;
                    itemstack.stackSize = storePartialItemStack(itemstack);
                }
                while (itemstack.stackSize > 0 && itemstack.stackSize < stackSize);

                return itemstack.stackSize < stackSize;
            }
            catch (Throwable throwable)
            {
                CrashReport crash = CrashReport.makeCrashReport(throwable, "Adding item to quiver");
                CrashReportCategory category = crash.makeCategory("Item being added");
                category.addCrashSection("Item ID", Item.getIdFromItem(itemstack.getItem()));
                category.addCrashSection("Item data", itemstack.getItemDamage());
                category.addCrashSectionCallable("Item name", new Callable()
                {
                    @Override
                    public String call()
                    {
                        return itemstack.getDisplayName();
                    }
                });
                throw new ReportedException(crash);
            }
        }

        return false;
    }

    public int getFirstEmptyStack(ItemStack itemstack)
    {
        for (int i = 0; i < itemstacks.length; ++i)
        {
            if (itemstacks[i] == null && isItemValidForSlot(i, itemstack))
            {
                return i;
            }
        }

        return -1;
    }

    private int storePartialItemStack(ItemStack itemstack)
    {
        Item item = itemstack.getItem();
        int toAdd = itemstack.stackSize;
        int slot;

        if (itemstack.getMaxStackSize() == 1)
        {
            slot = getFirstEmptyStack(itemstack);

            if (slot < 0)
            {
                return toAdd;
            }

            if (itemstacks[slot] == null)
            {
                itemstacks[slot] = ItemStack.copyItemStack(itemstack);
            }

            return 0;
        }

        slot = storeItemStack(itemstack);

        if (slot < 0)
        {
            slot = getFirstEmptyStack(itemstack);
        }

        if (slot < 0)
        {
            return toAdd;
        }

        if (itemstacks[slot] == null)
        {
            itemstacks[slot] = new ItemStack(item, 0, itemstack.getItemDamage());

            if (itemstack.hasTagCompound())
            {
                itemstacks[slot].setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
            }
        }

        int i = toAdd;

        if (toAdd > itemstacks[slot].getMaxStackSize() - itemstacks[slot].stackSize)
        {
            i = itemstacks[slot].getMaxStackSize() - itemstacks[slot].stackSize;
        }

        if (i > getInventoryStackLimit() - itemstacks[slot].stackSize)
        {
            i = getInventoryStackLimit() - itemstacks[slot].stackSize;
        }

        if (i == 0)
        {
            return toAdd;
        }

        itemstacks[slot].stackSize += i;
        itemstacks[slot].animationsToGo = 5;

        return toAdd - i;
    }

    private int storeItemStack(ItemStack itemstack)
    {
        for (int i = 0; i < itemstacks.length; ++i)
        {
            if (itemstacks[i] != null && itemstacks[i].getItem() == itemstack.getItem() && itemstacks[i].isStackable() && itemstacks[i].stackSize < itemstacks[i].getMaxStackSize() && itemstacks[i].stackSize < getInventoryStackLimit() && (!itemstacks[i].getHasSubtypes() || itemstacks[i].getItemDamage() == itemstack.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemstacks[i], itemstack))
            {
                return i;
            }
        }

        return -1;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        NBTTagList list = nbt.getTagList("Slots", NBT.TAG_COMPOUND);
        itemstacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < Math.min(list.tagCount(), getSizeInventory()); ++i)
        {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            itemstacks[tag.getByte("Slot")] = ItemStack.loadItemStackFromNBT(tag);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i++)
        {
            if (itemstacks[i] != null)
            {
                NBTTagCompound tag = itemstacks[i].writeToNBT(new NBTTagCompound());
                tag.setByte("Slot", (byte) i);

                list.appendTag(tag);
            }
        }

        nbt.setTag("Slots", list);

        return nbt;
    }

    @Override
    public int getSizeInventory()
    {
        return itemstacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return itemstacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack stack = getStackInSlot(slot);

        if (stack != null)
        {
            if (stack.stackSize > amount)
            {
                stack = stack.splitStack(amount);
                markDirty();
            }
            else
            {
                setInventorySlotContents(slot, null);
            }
        }

        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);

        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        itemstacks[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }

        markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return "Crystal Pouch";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 16;
    }

    @Override
    public void markDirty()
    {
        if (!thePlayer.worldObj.isRemote && isUseableByPlayer(thePlayer))
        {
            writeToNBT(getPouchStack().getTagCompound());
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return ItemCrystalPouch.getUUID(getPouchStack()).equals(uuid);
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
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return stack.getItem() == Item.getItemFromBlock(ModBlocks.lightsaberCrystal) && slot == ItemCrystal.getId(stack);
    }
}
