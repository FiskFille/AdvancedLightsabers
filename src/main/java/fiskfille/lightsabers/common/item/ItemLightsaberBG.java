package fiskfille.lightsabers.common.item;

import mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent;
import mods.battlegear2.api.weapons.IBattlegearWeapon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.relauncher.Side;

public class ItemLightsaberBG extends ItemLightsaber implements IBattlegearWeapon
{
	public ItemLightsaberBG()
	{
		super();
	}
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
    	return super.onItemRightClick(itemstack, world, player);
    }
	
	public boolean allowOffhand(ItemStack main, ItemStack off)
	{
		if (main != null && main.getItem() == ModItems.doubleLightsaber)
		{
			return false;
		}
		
		return true;
	}

	public boolean sheatheOnBack(ItemStack item)
	{
		return false;
	}

	public boolean isOffhandHandDual(ItemStack off)
	{
		return true;
	}

	public boolean offhandAttackEntity(OffhandAttackEvent event, ItemStack mainhandItem, ItemStack offhandItem)
	{
		return false;
	}

	public boolean offhandClickAir(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem)
	{
		return false;
	}

	public boolean offhandClickBlock(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem)
	{
		return false;
	}

	public void performPassiveEffects(Side effectiveSide, ItemStack mainhandItem, ItemStack offhandItem)
	{
		
	}
}
