package fiskfille.lightsabers.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.entity.EntityLightsaber;
import fiskfille.lightsabers.common.helper.LightsaberHelper;

public abstract class ItemLightsaberBase extends ItemSword
{
	@SideOnly(Side.CLIENT)
    public static IIcon crystalEmptySlotIcon;
	
	@SideOnly(Side.CLIENT)
    public static IIcon focusingCrystalEmptySlotIcon;
	
    public ItemLightsaberBase()
	{
		super(ToolMaterial.EMERALD);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}
    
    public double getAttackDamage()
	{
		return 8.0D;
	}
    
    public Entity getThrownLightsaberEntity(World world, EntityLivingBase entity, ItemStack itemstack)
	{
		return new EntityLightsaber(world, entity, itemstack);
	}
    
    public static void refreshNBT(ItemStack itemstack)
    {
    	if (itemstack != null && !itemstack.hasTagCompound())
    	{
    		itemstack.setTagCompound(new NBTTagCompound());
    	}
    }
    
    public static boolean isActive(ItemStack itemstack)
    {
    	if (itemstack == null)
    	{
    		return false;
    	}
    	
    	refreshNBT(itemstack);
    	return itemstack.getTagCompound().getBoolean("active");
    }
	
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack itemstack)
	{
		if (isActive(itemstack))
		{
			MovingObjectPosition mop = getMovingObjectPosition(entity.worldObj, entity, true);
			
			if (mop == null || mop.typeOfHit != mop.typeOfHit.BLOCK)
			{
				entity.playSound(Lightsabers.modid + ":lightsaber_swing", 1.0F, 1.0F);
			}
			else
			{
				return onPunchBlock(itemstack, entity, mop);
			}
			
			if (entity.isSneaking())
			{
				throwLightsaber(entity, itemstack);
			}
		}
		
		return super.onEntitySwing(entity, itemstack);
	}
	
	public void throwLightsaber(EntityLivingBase entity, ItemStack itemstack)
	{
		Entity lightsaber = getThrownLightsaberEntity(entity.worldObj, entity, itemstack);
		entity.worldObj.spawnEntityInWorld(lightsaber);
		entity.setCurrentItemOrArmor(0, null);
	}
	
	private MovingObjectPosition getMovingObjectPosition(World world, EntityLivingBase entity, boolean b)
	{
		if (entity instanceof EntityPlayer)
		{
			return getMovingObjectPositionFromPlayer(world, (EntityPlayer)entity, b);
		}
		
		return null;
	}

	public boolean onPunchBlock(ItemStack stack, EntityLivingBase entity, MovingObjectPosition mop)
	{
		return false;
	}
	
    public boolean isFull3D()
    {
        return true;
    }
    
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
    	LightsaberHelper.igniteLightsaber(player, !isActive(itemstack));
    	return itemstack;
    }
    
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
    {
    	return !isActive(itemstack);
    }
    
    public EnumAction getItemUseAction(ItemStack itemstack)
    {
        return EnumAction.bow;
    }

    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 72000;
    }
	
	public Multimap getItemAttributeModifiers()
    {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", getAttackDamage(), 0));
        return multimap;
    }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IIconRegister)
	{
		crystalEmptySlotIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":lightsaber_crystal_outline");
		focusingCrystalEmptySlotIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":focusing_crystal_outline");
	}
}
