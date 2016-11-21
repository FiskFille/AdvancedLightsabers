package fiskfille.lightsabers.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityDoubleLightsaber extends EntityLightsaber
{
	public EntityDoubleLightsaber(World world, EntityLivingBase entity, ItemStack itemstack, int amplifier)
	{
		super(world, entity, itemstack, amplifier);
		setSize(3.15F / (2 - amplifier), 0.0625F);
	}
	
	public EntityDoubleLightsaber(World world)
	{
		super(world);
		setSize(3.15F, 0.0625F);
	}
}
