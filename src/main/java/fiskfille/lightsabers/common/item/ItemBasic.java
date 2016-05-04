package fiskfille.lightsabers.common.item;

import fiskfille.lightsabers.Lightsabers;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemBasic extends Item
{	
	public ItemBasic()
	{
		super();
	}

	public void registerIcons(IIconRegister par1IIconRegister)
	{
		itemIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":" + getUnlocalizedName().substring(5));
	}
}
