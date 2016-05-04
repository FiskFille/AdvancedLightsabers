package fiskfille.lightsabers.common.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class SlotLightsaberPart extends Slot
{
	public EnumPartType partType;
	
	public SlotLightsaberPart(EnumPartType type, IInventory inventory, int id, int x, int y)
	{
		super(inventory, id, x, y);
		partType = type;
	}
	
	public int getSlotStackLimit()
	{
		return 1;
	}
	
	public boolean isItemValid(ItemStack itemstack)
	{
		Item item = itemstack.getItem();
		return partType == EnumPartType.EMITTER ? item == ModItems.lightsaberEmitter : (partType == EnumPartType.SWITCH_SECTION ? item == ModItems.lightsaberSwitchSection : (partType == EnumPartType.BODY ? item == ModItems.lightsaberBody : item == ModItems.lightsaberPommel));
	}
}
