package fiskfille.lightsabers.common.item;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberHelper;

public class ItemFocusingCrystal extends ItemBasic
{
	public IIcon[] icons = new IIcon[64];
	
	public ItemFocusingCrystal()
	{
		super();
		setHasSubtypes(true);
	}
	
	public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
	{
		ItemStack itemstack = LightsaberHelper.createFocusingCrystal(rand.nextInt(FocusingCrystals.getFocusingCrystals().length));
		return new WeightedRandomChestContent(itemstack, original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
	}
	
	public String getItemStackDisplayName(ItemStack itemstack)
    {
    	return FocusingCrystals.getFocusingCrystalName(LightsaberHelper.getFocusingCrystalId(itemstack));
    }
    
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	for (int i = 0; i < FocusingCrystals.getFocusingCrystals().length; ++i)
    	{
    		list.add(LightsaberHelper.createFocusingCrystal(i));
    	}
    }

    public IIcon getIconFromDamage(int damage)
    {
        int i = MathHelper.clamp_int(damage, 0, FocusingCrystals.getFocusingCrystals().length);
        return icons[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IIconRegister)
	{
    	for (int i = 0; i < FocusingCrystals.getFocusingCrystals().length; ++i)
    	{
    		icons[i] = par1IIconRegister.registerIcon(Lightsabers.modid + ":" + FocusingCrystals.getFocusingCrystals()[i]);
    	}
	}
}
