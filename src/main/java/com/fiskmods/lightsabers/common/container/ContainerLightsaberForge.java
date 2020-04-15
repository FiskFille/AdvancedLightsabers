package com.fiskmods.lightsabers.common.container;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.common.item.ILightsaberComponent;
import com.fiskmods.lightsabers.common.item.ItemFocusingCrystal;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ContainerLightsaberForge extends ContainerBasic<TileEntityLightsaberForge>
{
    public InventoryLightsaberForge craftMatrix = new InventoryLightsaberForge(this);
    public IInventory craftResult = new InventoryCraftResult();

    public static final int[][] SLOTS = {{20, 17}, {20, 35}, {20, 53}, {20, 71}, {43, 71}, {66, 71}, {89, 71}, {107, 71}};
    
    public ContainerLightsaberForge(InventoryPlayer inventoryPlayer, TileEntityLightsaberForge tile)
    {
        super(tile);
        
        for (int slot = 0; slot < SLOTS.length; ++slot)
        {
            addSlotToContainer(new Input(slot, SLOTS[slot][0], SLOTS[slot][1]));
        }

        addSlotToContainer(new Output(0, 136, 87));
        addPlayerInventory(inventoryPlayer, 30);
        onCraftMatrixChanged(craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory)
    {
        craftMatrix.result = craftMatrix.updateResult();
        ItemStack result = craftMatrix.result == null ? null : ItemLightsaberBase.setActive(craftMatrix.result.create(), true);
        
        if (result != null)
        {
            ItemStack itemstack = craftMatrix.getStackInSlot(5);
            
            if (itemstack != null && itemstack.getItem() == Items.fish)
            {
                result.getTagCompound().setString(ALConstants.TAG_LIGHTSABER_SPECIAL, Item.itemRegistry.getNameForObject(itemstack.getItem()));
            }
        }
        
        craftResult.setInventorySlotContents(0, result);
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!worldObj.isRemote)
        {
            for (int i = 0; i < craftMatrix.getSizeInventory(); ++i)
            {
                ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    player.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotId);
        int OUTPUT = 8;

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotId == OUTPUT)
            {
                if (!mergeItemStack(itemstack1, OUTPUT + 1, OUTPUT + 36 + 1, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotId > OUTPUT)
            {
                boolean flag = true;

                for (int i = 0; i < OUTPUT; ++i)
                {
                    Slot slot1 = ((Slot) inventorySlots.get(i));
                    Item item = itemstack1.getItem();

                    if (item instanceof ILightsaberComponent && ((ILightsaberComponent) item).isCompatibleSlot(itemstack, i))
                    {
                        if (!slot1.getHasStack() && !mergeItemStack(itemstack1, i, i + 1, false, true))
                        {
                            return null;
                        }
                        
                        flag = false;
                    }
                }
                
                if (flag)
                {
                    if (slotId >= OUTPUT + 1 && slotId < OUTPUT + 28)
                    {
                        if (!mergeItemStack(itemstack1, OUTPUT + 28, OUTPUT + 37, false))
                        {
                            return null;
                        }
                    }
                    else if (slotId >= OUTPUT + 28 && slotId < OUTPUT + 37 && !mergeItemStack(itemstack1, OUTPUT + 1, OUTPUT + 28, false))
                    {
                        return null;
                    }
                }
            }
            else if (!mergeItemStack(itemstack1, OUTPUT + 1, OUTPUT + 37, false))
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

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean func_94530_a(ItemStack itemstack, Slot slot)
    {
        return slot.inventory != craftResult && super.func_94530_a(itemstack, slot);
    }
    
    private class Input extends Slot
    {
        public Input(int id, int x, int y)
        {
            super(craftMatrix, id, x, y);
        }

        @Override
        public int getSlotStackLimit()
        {
            return 1;
        }

        @Override
        public boolean isItemValid(ItemStack itemstack)
        {
            Item item = itemstack.getItem();
            
            if (!(item instanceof ILightsaberComponent) || !((ILightsaberComponent) item).isCompatibleSlot(itemstack, getSlotIndex()))
            {
                if (getSlotIndex() != 5 || item != Items.fish)
                {
                    return false;
                }
            }
            
            for (int slot = 0; slot < inventory.getSizeInventory(); ++slot)
            {
                ItemStack stack = inventory.getStackInSlot(slot);
                
                if (stack != null && stack.getItem() == item && (stack.isItemStackDamageable() || stack.getItemDamage() == itemstack.getItemDamage()))
                {
                    return false;
                }
            }
            
            return true;
        }
        
        @Override
        @SideOnly(Side.CLIENT)
        public IIcon getBackgroundIconIndex()
        {
            return getSlotIndex() == 6 || getSlotIndex() == 7 ? ItemFocusingCrystal.outlineIcon : null;
        }
    }
    
    private class Output extends Slot
    {
        public Output(int id, int x, int y)
        {
            super(craftResult, id, x, y);
        }

        @Override
        public boolean canTakeStack(EntityPlayer player)
        {
            return craftMatrix.result != null && !craftMatrix.result.isTooShort();
        }

        @Override
        public boolean isItemValid(ItemStack itemstack)
        {
            return false;
        }

        @Override
        public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
        {
            FMLCommonHandler.instance().firePlayerCraftingEvent(player, itemstack, craftMatrix);
            onCrafting(itemstack);

            for (int i = 0; i < craftMatrix.getSizeInventory(); ++i)
            {
                ItemStack itemstack1 = craftMatrix.getStackInSlot(i);

                if (itemstack1 != null)
                {
                    craftMatrix.decrStackSize(i, 1);
                }
            }
            
            ItemLightsaberBase.setActive(itemstack, false);
        }
    }
}
