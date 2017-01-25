package fiskfille.lightsabers.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import fiskfille.lightsabers.Lightsabers;

public class ItemBasic extends Item
{
    public ItemBasic()
    {
        super();
    }

    @Override
    public void registerIcons(IIconRegister par1IIconRegister)
    {
        itemIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":" + getUnlocalizedName().substring(5));
    }
}
