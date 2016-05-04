package fiskfille.lightsabers.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;

public class SlotLightsaberForge extends Slot
{
    private final InventoryLightsaberForge craftMatrix;
    private EntityPlayer thePlayer;
    private int amountCrafted;

    public SlotLightsaberForge(EntityPlayer player, InventoryLightsaberForge inventory1, IInventory inventory2, int id, int x, int y)
    {
        super(inventory2, id, x, y);
        this.thePlayer = player;
        this.craftMatrix = inventory1;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }

    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        {
            this.amountCrafted += Math.min(amount, this.getStack().stackSize);
        }

        return super.decrStackSize(amount);
    }

    protected void onCrafting(ItemStack itemstack, int amount)
    {
        this.amountCrafted += amount;
        this.onCrafting(itemstack);
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
    {
        FMLCommonHandler.instance().firePlayerCraftingEvent(player, itemstack, craftMatrix);
        this.onCrafting(itemstack);

        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);

            if (itemstack1 != null)
            {
                this.craftMatrix.decrStackSize(i, 1);
            }
        }
    }
}
