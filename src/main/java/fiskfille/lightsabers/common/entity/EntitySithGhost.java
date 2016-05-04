package fiskfille.lightsabers.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fiskfille.lightsabers.common.entity.ai.EntityAIBreakBlock;
import fiskfille.lightsabers.common.entity.ai.EntityAIRest;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketSithCoffin;
import fiskfille.lightsabers.common.network.PacketThrowLightsaber;
import fiskfille.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

public class EntitySithGhost extends EntityMob
{
	public boolean hasRestingPlace;
	public int restingPlaceX;
	public int restingPlaceY;
	public int restingPlaceZ;
	
	public int throwLightsaberCooldown;
	public int swingItemCooldown;
	public int strafeTimer;
	public int strafe = 1;
	public int taskFinished = 0;

	public EntitySithGhost(World world)
	{
		super(world);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIBreakBlock(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, 0.5D, true));
		tasks.addTask(2, new EntityAIRest(this, 0.4D));
		tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 4F, 10F));
		tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        tasks.addTask(4, new EntityAIOpenDoor(this, true));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
	}

	public boolean attackEntityAsMob(Entity entity)
	{
		if (entity instanceof EntitySithGhost)
		{
			return false;
		}
		
		boolean flag = super.attackEntityAsMob(entity);

		if (flag)
		{
			swingItem();
		}

		return flag;
	}

	public boolean attackEntityFrom(DamageSource damageSource, float damage)
	{
		return super.attackEntityFrom(damageSource, Math.min(damage, 10));
	}

	public void swingItem()
	{
		if (swingItemCooldown == 0)
		{
			swingItemCooldown = 5;
			super.swingItem();
		}
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected float getSoundPitch()
	{
		return super.getSoundPitch() * 0.7F;
	}

	protected String getLivingSound()
	{
		return null;
	}

	protected String getHurtSound()
	{
		return null;
	}

	protected String getDeathSound()
	{
		return "mob.wither.death";
	}

	public void onUpdate()
	{
		super.onUpdate();
		EntityLivingBase entity = getAttackTarget();
		ItemStack itemstack = getHeldItem();

		if (throwLightsaberCooldown > 0)
		{
			--throwLightsaberCooldown;
		}
		
		if (swingItemCooldown > 0)
		{
			--swingItemCooldown;
		}
		
		++strafeTimer;
		
		if (strafeTimer > 100 + rand.nextInt(1000))
		{
			strafe *= -1;
			strafeTimer = 0;
		}

		if (itemstack != null)
		{
			if (ticksExisted > 5)
			{
				LightsaberHelper.igniteLightsaber(this, true);
			}

			if (entity != null)
			{
				faceEntity(entity, 100.0F, 100.0F);

				if (ItemLightsaberBase.isActive(itemstack) && getDistanceToEntity(entity) > 5 && canEntityBeSeen(entity) && throwLightsaberCooldown == 0)
				{
					throwLightsaberCooldown = 20;
					swingItem();
					ALNetworkManager.networkWrapper.sendToServer(new PacketThrowLightsaber(this, itemstack));
				}
				
				moveEntityWithHeading(0.3F * strafe, 0);
			}
		}

		if (getHealth() <= 0)
		{
			for (int i = 0; i < 128; ++i)
			{
				double d0 = (double)(rand.nextFloat() * 2 - 1) * 1.2F;
				double d1 = (double)(rand.nextFloat() * 2.4F - 1);
				double d2 = (double)(rand.nextFloat() * 2 - 1) * 1.2F;
				double d3 = posX + d0 * width;
				double d4 = boundingBox.minY + d1 * (double)height;
				double d5 = posZ + d2 * width;
				worldObj.spawnParticle("largesmoke", d3, d4, d5, 0, 0, 0);
			}

			setDead();
		}
		
		if (entity instanceof EntitySithGhost)
		{
			setAttackTarget(worldObj.getClosestVulnerablePlayerToEntity(this, 32));
		}
		
		if (entity != null)
		{
			taskFinished = 1;
		}
		else if (taskFinished == 1)
		{
			taskFinished = 2;
		}
		
		if (taskFinished == 2)
		{
			int x = MathHelper.floor_double(posX);
	    	int y = MathHelper.floor_double(posY);
	    	int z = MathHelper.floor_double(posZ);
	    	
	    	if (getDistance(restingPlaceX, restingPlaceY, restingPlaceZ) <= 2D)
			{
				taskFinished = 3;
				
				if (worldObj.getTileEntity(restingPlaceX, restingPlaceY, restingPlaceZ) instanceof TileEntitySithStoneCoffin)
				{
					TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin)worldObj.getTileEntity(restingPlaceX, restingPlaceY, restingPlaceZ);
					tile.equipment = getEquipmentInSlot(0);
					worldObj.playAuxSFXAtEntity(null, 1017, x, y, z, 0);
					setDead();
					ALNetworkManager.networkWrapper.sendToServer(new PacketSithCoffin(this, restingPlaceX, restingPlaceY, restingPlaceZ, 0));
				}
			}
		}
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		hasRestingPlace = nbt.getBoolean("HasRestingPlace");
		restingPlaceX = nbt.getInteger("RestX");
		restingPlaceY = nbt.getInteger("RestY");
		restingPlaceZ = nbt.getInteger("RestZ");
		throwLightsaberCooldown = nbt.getInteger("ThrowCooldown");
		swingItemCooldown = nbt.getInteger("SwingCooldown");
		strafeTimer = nbt.getInteger("StrafeTimer");
		strafe = nbt.getInteger("Strafe");
		taskFinished = nbt.getInteger("TaskFinished");
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("HasRestingPlace", hasRestingPlace);
		nbt.setInteger("RestX", restingPlaceX);
		nbt.setInteger("RestY", restingPlaceY);
		nbt.setInteger("RestZ", restingPlaceZ);
		nbt.setInteger("ThrowCooldown", throwLightsaberCooldown);
		nbt.setInteger("SwingCooldown", swingItemCooldown);
		nbt.setInteger("StrafeTimer", strafeTimer);
		nbt.setInteger("Strafe", strafe);
		nbt.setInteger("TaskFinished", taskFinished);
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData entityData)
	{
		if (getEquipmentInSlot(0) == null)
		{
			setCurrentItemOrArmor(0, LightsaberHelper.createRandomLightsaber(rand, LightsaberHelper.getCrystalIdFromColor(LightsaberColors.RED)));
		}

		for (int i = 0; i < equipmentDropChances.length; ++i)
		{
			equipmentDropChances[i] = 0;
		}

		return super.onSpawnWithEgg(entityData);
	}
}
