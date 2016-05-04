package fiskfille.lightsabers.common.entity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;

public class EntityLightsaber extends EntityThrowable
{
	public ItemStack lightsaber;
	public boolean shouldReturn = false;
	public int soundTick = 3;
	
	public EntityLightsaber(World world, EntityLivingBase entity, ItemStack itemstack)
	{
		super(world, entity);
		lightsaber = itemstack;
		setSize(2, 0.0625F);
	}
	
	public EntityLightsaber(World world)
	{
		super(world);
		setSize(2, 0.0625F);
	}
	
	public ItemStack getLightsaberItem()
	{
		return lightsaber;
	}
	
	public void setLightsaberItem(ItemStack itemstack)
	{
		lightsaber = itemstack;
	}
	
    protected float getGravityVelocity()
    {
    	return 0.01F;
    }
    
    protected float func_70182_d()
    {
        return 2.0F;
    }
    
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
    	super.writeEntityToNBT(nbt);
    	
        if (getLightsaberItem() != null)
        {
            nbt.setTag("Item", getLightsaberItem().writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	super.readEntityFromNBT(nbt);
        NBTTagCompound nbttagcompound1 = nbt.getCompoundTag("LightsaberItem");
        setLightsaberItem(ItemStack.loadItemStackFromNBT(nbttagcompound1));
    }
    
    public void onUpdate()
    {
        super.onUpdate();

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        
        if (getThrower() != null && getThrower().isEntityAlive())
        {
            if (ticksExisted % soundTick == 0)
            {
            	worldObj.playSound(posX, posY, posZ, Lightsabers.modid + ":lightsaber_swing", 1.0F, 1.0F, true);
            }
        }
        else if (!(getThrower() instanceof EntitySithGhost))
        {
			setDead();
			
			if (!worldObj.isRemote)
			{
				EntityItem entityitem = new EntityItem(getThrower().worldObj);
				entityitem.setLocationAndAngles(getThrower().posX, getThrower().posY, getThrower().posZ, 0.0F, 0.0F);
				entityitem.setEntityItemStack(lightsaber);
				worldObj.spawnEntityInWorld(entityitem);
			}
        }
        
        if (ticksExisted > 20)
        {
        	shouldReturn = true;
        }
        
        if (!isDead)
        {
        	if (getThrower() instanceof EntityPlayer)
        	{
    			EntityPlayer player = (EntityPlayer)getThrower();
    			
        		if (shouldReturn)
        		{
        			motionX = (player.posX - posX) / 4;
        			motionY = (player.posY - posY - player.height / 2) / 4;
        			motionZ = (player.posZ - posZ) / 4;
        			
        			if (getDistanceToEntity(player) <= 2.0D)
        			{         
        				setDead();
        				
    					if (!worldObj.isRemote)
        				{
        					if (player.inventory.getCurrentItem() == null)
        					{
        						player.setCurrentItemOrArmor(0, getLightsaberItem());
        					}
        					else
        					{
        						if (!player.inventory.addItemStackToInventory(getLightsaberItem()))
        						{
        							EntityItem entityitem = new EntityItem(getThrower().worldObj);
        							entityitem.setLocationAndAngles(getThrower().posX, getThrower().posY, getThrower().posZ, 0.0F, 0.0F);
        							entityitem.setEntityItemStack(getLightsaberItem());
        							worldObj.spawnEntityInWorld(entityitem);
        						}
        					}
        				}
        			}
        		}
            }
        	else
        	{
        		EntityLivingBase entity = getThrower();
    			
        		if (shouldReturn)
        		{
        			motionX = (entity.posX - posX) / 4;
        			motionY = (entity.posY - posY - entity.height / 2) / 4;
        			motionZ = (entity.posZ - posZ) / 4;
        			
        			if (getDistanceToEntity(entity) <= 2.0D)
        			{         
        				setDead();
        				
    					if (!worldObj.isRemote)
        				{
    						entity.setCurrentItemOrArmor(0, getLightsaberItem());
        				}
        			}
        		}
        	}
        }
    }
	
	protected void onImpact(MovingObjectPosition mop)
	{
		if (getThrower() instanceof EntitySithGhost && mop.entityHit instanceof EntitySithGhost)
		{
			return;
		}
		
        if (mop.entityHit != null && mop.entityHit.getUniqueID() != getThrower().getUniqueID())
        {
        	float damage = (float)((ItemLightsaberBase)getLightsaberItem().getItem()).getAttackDamage() + (ticksExisted > 5 ? 2.5F : ticksExisted / 2);
        	float sharpnessDamage = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, getLightsaberItem()) * 1.25F;
            mop.entityHit.setFire(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, getLightsaberItem()) * 4);
            
            if (getThrower() instanceof EntityPlayer)
            {
            	mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)getThrower()), damage + sharpnessDamage);
            }
            else
            {
            	mop.entityHit.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damage + sharpnessDamage);
            }
        }
        else
        {
        	shouldReturn = true;
            motionY += 0.2D;
        }
	}
}
