package fiskfille.lightsabers.common.item;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.LightsaberAPI;
import fiskfille.lightsabers.common.entity.EntityLightsaber;
import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumLightsaberType;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class ItemLightsaber extends ItemLightsaberBase
{
	public ItemLightsaber()
	{
		super();
	}
	
	public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rand, WeightedRandomChestContent original)
	{
		ItemStack itemstack = original.theItemId;
		
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("SithTombLoot"))
		{
			itemstack = LightsaberHelper.createRandomLightsaber(rand, LightsaberHelper.getCrystalIdFromColor(rand.nextFloat() < 0.2F ? LightsaberColors.PURPLE : LightsaberColors.RED));
			
			if (rand.nextFloat() < 0.25F)
			{
				itemstack = LightsaberHelper.createDoubleLightsaber(itemstack, itemstack);
			}
		}
		else if (itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("JediTempleLoot"))
		{
			List<Integer> list = Arrays.asList(LightsaberColors.GREEN, LightsaberColors.MEDIUM_BLUE);
			itemstack = LightsaberHelper.createRandomLightsaber(rand, LightsaberHelper.getCrystalIdFromColor(list.get(rand.nextInt(list.size()))));
			
			if (rand.nextFloat() < 0.125F)
			{
				itemstack = LightsaberHelper.createDoubleLightsaber(itemstack, itemstack);
			}
		}
		else
		{
			itemstack = LightsaberHelper.createRandomLightsaber(rand);
		}
		
		return new WeightedRandomChestContent(itemstack, original.theMinimumChanceToGenerateItem, original.theMaximumChanceToGenerateItem, original.itemWeight);
	}
	
	public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		for (Lightsaber lightsaber : LightsaberAPI.getLightsabers())
		{
			if (lightsaber.getType() == EnumLightsaberType.SINGLE)
			{
				int[] aint = new int[lightsaber.getFocusingCrystals().length];
				
				for (int i = 0; i < aint.length; ++i)
				{
					String name = lightsaber.getFocusingCrystals()[i];
					aint[i] = LightsaberHelper.getFocusingCrystalIdFromName(name);
				}
				
				list.add(LightsaberHelper.createLightsaber(LightsaberHelper.getCrystalIdFromColor(lightsaber.getColor()), lightsaber, aint));
			}
		}
    }
    
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag)
    {
    	String s = "  ";
    	list.add(StatCollector.translateToLocal("lightsaber.color"));
    	list.add(s + LightsaberColors.getColorName(LightsaberHelper.getColorId(itemstack)));
    	
    	List<String> list1 = Lists.newArrayList();
		list1.add(LightsaberHelper.getPartString(itemstack, EnumPartType.EMITTER));
    	list1.add(LightsaberHelper.getPartString(itemstack, EnumPartType.SWITCH_SECTION));
    	list1.add(LightsaberHelper.getPartString(itemstack, EnumPartType.BODY));
    	list1.add(LightsaberHelper.getPartString(itemstack, EnumPartType.POMMEL));
		
    	String prevString = null;
    	int i = 0;
    	
    	for (String s1 : list1)
    	{
    		if (prevString != null)
    		{
    			if (prevString.equals(s1))
    			{
    				++i;
    			}
    		}
    		else
    		{
    			++i;
    		}
    		
    		prevString = s1;
    	}
    	
    	list.add(StatCollector.translateToLocal("lightsaber.hilt"));
    	
    	if (i != 4)
    	{
    		list.add(s + LightsaberHelper.getPartString(itemstack, EnumPartType.EMITTER));
    		list.add(s + LightsaberHelper.getPartString(itemstack, EnumPartType.SWITCH_SECTION));
    		list.add(s + LightsaberHelper.getPartString(itemstack, EnumPartType.BODY));
    		list.add(s + LightsaberHelper.getPartString(itemstack, EnumPartType.POMMEL));
    	}
    	else
    	{
    		list.add(s + LightsaberHelper.getPartString(itemstack, EnumPartType.EMITTER));
    	}
    	
    	int[] aint = LightsaberHelper.getFocusingCrystalIds(itemstack);
    	
    	if (aint.length > 0)
    	{
    		list.add(StatCollector.translateToLocal("lightsaber.focusingCrystals"));
    	}
    	
    	for (int id : aint)
    	{
    		list.add(s + FocusingCrystals.getFocusingCrystalName(id));
    	}
    }
	
	public Entity getThrownLightsaberEntity(World world, EntityLivingBase entity, ItemStack itemstack, int amplifier)
	{
		return new EntityLightsaber(world, entity, itemstack, amplifier);
	}
	
	public boolean onPlayerPunchBlock(ItemStack stack, EntityPlayer player, MovingObjectPosition mop)
	{
		return false;
	}
	
	public double getAttackDamage()
	{
		return 8.0D;
	}
}
