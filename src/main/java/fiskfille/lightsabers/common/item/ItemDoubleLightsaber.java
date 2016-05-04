package fiskfille.lightsabers.common.item;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.LightsaberAPI;
import fiskfille.lightsabers.common.entity.EntityDoubleLightsaber;
import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumLightsaberType;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class ItemDoubleLightsaber extends ItemLightsaberBase
{
	public ItemDoubleLightsaber()
	{
		super();
	}
	
	public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		for (Lightsaber lightsaber : LightsaberAPI.getLightsabers())
		{
			if (lightsaber.getType() == EnumLightsaberType.DOUBLE)
			{
				int[] aint = new int[lightsaber.getFocusingCrystals().length];
				
				for (int i = 0; i < aint.length; ++i)
				{
					String name = lightsaber.getFocusingCrystals()[i];
					aint[i] = LightsaberHelper.getFocusingCrystalIdFromName(name);
				}
				
				ItemStack itemstack = LightsaberHelper.createLightsaber(LightsaberHelper.getCrystalIdFromColor(lightsaber.getColor()), lightsaber, aint);
				list.add(LightsaberHelper.createDoubleLightsaber(itemstack, itemstack));
			}
		}
    }
    
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag)
    {
    	ItemStack upper = LightsaberHelper.getDoubleLightsaberUpper(itemstack);
    	ItemStack lower = LightsaberHelper.getDoubleLightsaberLower(itemstack);
    	String s = " ";

    	if (LightsaberHelper.getColorId(upper) == LightsaberHelper.getColorId(lower))
    	{
    		list.add("Color: ");
    		list.add(s + LightsaberColors.getColorName(LightsaberHelper.getColorId(upper)));
    	}
    	else
    	{
    		list.add("Colors: ");
    		list.add(s + LightsaberColors.getColorName(LightsaberHelper.getColorId(upper)));
    		list.add(s + LightsaberColors.getColorName(LightsaberHelper.getColorId(lower)));
    	}

    	List<String> list1 = Lists.newArrayList();
    	list1.add(LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
    	list1.add(LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
    	list1.add(LightsaberHelper.getPartString(upper, EnumPartType.BODY));
    	list1.add(LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));

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

    	List<String> list2 = Lists.newArrayList();
    	list2.add(LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
    	list2.add(LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
    	list2.add(LightsaberHelper.getPartString(upper, EnumPartType.BODY));
    	list2.add(LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));

    	String prevString1 = null;
    	int i1 = 0;

    	for (String s1 : list2)
    	{
    		if (prevString1 != null)
    		{
    			if (prevString1.equals(s1))
    			{
    				++i1;
    			}
    		}
    		else
    		{
    			++i1;
    		}

    		prevString1 = s1;
    	}

    	List<String> list3 = Lists.newArrayList();
    	list3.add(LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
    	list3.add(LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
    	list3.add(LightsaberHelper.getPartString(upper, EnumPartType.BODY));
    	list3.add(LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));
    	list3.add(LightsaberHelper.getPartString(lower, EnumPartType.POMMEL));
    	list3.add(LightsaberHelper.getPartString(lower, EnumPartType.BODY));
    	list3.add(LightsaberHelper.getPartString(lower, EnumPartType.SWITCH_SECTION));
    	list3.add(LightsaberHelper.getPartString(lower, EnumPartType.EMITTER));

    	String prevString2 = null;
    	int i2 = 0;

    	for (String s1 : list3)
    	{
    		if (prevString2 != null)
    		{
    			if (prevString2.equals(s1))
    			{
    				++i2;
    			}
    		}
    		else
    		{
    			++i2;
    		}

    		prevString2 = s1;
    	}

    	list.add(StatCollector.translateToLocal("lightsaber.hilt"));

    	if (i2 == 8)
    	{
    		list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
    	}
    	else if (i == 4 && i1 == 4)
    	{
    		list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
    		list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.EMITTER));
    	}
    	else
    	{
    		list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
    		list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
    		list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.BODY));
    		list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));
    		list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.POMMEL));
    		list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.BODY));
    		list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.SWITCH_SECTION));
    		list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.EMITTER));
    	}
    	
    	int[] aint = LightsaberHelper.getFocusingCrystalIds(upper);
    	int[] aint1 = LightsaberHelper.getFocusingCrystalIds(lower);
    	
    	if (aint.length > 0 || aint1.length > 0)
    	{
    		list.add(StatCollector.translateToLocal("lightsaber.focusingCrystals"));
    	}
    	
    	if (aint.length > 0)
    	{
    		list.add(s + StatCollector.translateToLocal("lightsaber.upper"));
    	}
    	
    	for (int id : aint)
    	{
    		list.add(s + s + FocusingCrystals.getFocusingCrystalName(id));
    	}
    	
    	if (aint1.length > 0)
    	{
    		list.add(s + StatCollector.translateToLocal("lightsaber.lower"));
    	}
    	
    	for (int id : aint1)
    	{
    		list.add(s + s + FocusingCrystals.getFocusingCrystalName(id));
    	}
    }
	
	public Entity getThrownLightsaberEntity(World world, EntityLivingBase entity, ItemStack itemstack)
	{
		return new EntityDoubleLightsaber(world, entity, itemstack);
	}
	
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase enemy, EntityLivingBase entity)
	{
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(-2, 0, -2, 2, 1.5F, 2).offset(entity.posX, entity.posY, entity.posZ);
		List<EntityLivingBase> list = entity.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);
		
		for (EntityLivingBase entity1 : list)
		{
			if (entity1 != entity)
			{
				float damage = (float)getAttackDamage() + 1;
				float sharpnessDamage = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemstack) * 1.25F;
				entity1.setFire(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) * 4);

				if (entity instanceof EntityPlayer)
				{
					entity1.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), damage + sharpnessDamage);
				}
				else
				{
					entity1.attackEntityFrom(DamageSource.causeMobDamage(entity), damage + sharpnessDamage);
				}
			}
		}
		
		return true;
	}
	
	public double getAttackDamage()
	{
		return 8.0D;
	}
}
