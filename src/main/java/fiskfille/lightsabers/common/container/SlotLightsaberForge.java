package fiskfille.lightsabers.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import fiskfille.lightsabers.common.helper.LightsaberHelper;

public class SlotLightsaberForge extends Slot
{
    private final InventoryLightsaberForge craftMatrix;
    private int amountCrafted;

    public SlotLightsaberForge(EntityPlayer player, InventoryLightsaberForge inventory1, IInventory inventory2, int id, int x, int y)
    {
        super(inventory2, id, x, y);
        craftMatrix = inventory1;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        if (getStack() == null || LightsaberHelper.getLightsaberHeightCm(getStack()) < LightsaberHelper.MIN_LENGTH_CM)
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount)
    {
        if (getHasStack())
        {
            amountCrafted += Math.min(amount, getStack().stackSize);
        }

        return super.decrStackSize(amount);
    }

    @Override
    protected void onCrafting(ItemStack itemstack, int amount)
    {
        amountCrafted += amount;
        onCrafting(itemstack);
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
    }
}
