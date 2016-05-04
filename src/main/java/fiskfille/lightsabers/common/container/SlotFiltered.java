package fiskfille.lightsabers.common.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFiltered extends Slot
{
	public Item filteredItem;
	
	public SlotFiltered(IInventory inventory, int id, int x, int y, Item item)
	{
		super(inventory, id, x, y);
		filteredItem = item;
	}
	
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() == filteredItem;
	}
}
