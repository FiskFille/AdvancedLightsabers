package fiskfille.lightsabers.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDoubleLightsaber extends EntityLightsaber
{
	public EntityDoubleLightsaber(World world, EntityLivingBase entity, ItemStack itemstack)
	{
		super(world, entity, itemstack);
		setSize(3.15F, 0.0625F);
	}
	
	public EntityDoubleLightsaber(World world)
	{
		super(world);
		setSize(3.15F, 0.0625F);
	}
	
	protected void onImpact(MovingObjectPosition mop)
	{
		if (mop.entityHit != null && mop.entityHit.getUniqueID() != getThrower().getUniqueID())
		{
			mop.entityHit.hurtResistantTime = 10;
		}

		super.onImpact(mop);
	}
}
