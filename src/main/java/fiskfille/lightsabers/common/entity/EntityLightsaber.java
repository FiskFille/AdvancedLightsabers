package fiskfille.lightsabers.common.entity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.damagesource.ModDamageSources;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;

public class EntityLightsaber extends EntityThrowable
{
    public ItemStack lightsaber;
    public boolean shouldReturn = false;

    public EntityLightsaber(World world, EntityLivingBase entity, ItemStack itemstack, int amplifier)
    {
        super(world, entity);
        lightsaber = itemstack;
        setSize(2.0F / (2 - amplifier), 0.0625F);
    }

    public EntityLightsaber(World world)
    {
        super(world);
        setSize(2.0F, 0.0625F);
    }

    public ItemStack getLightsaberItem()
    {
        return lightsaber;
    }

    public void setLightsaberItem(ItemStack itemstack)
    {
        lightsaber = itemstack;
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.01F;
    }

    @Override
    protected float func_70182_d()
    {
        return 2.0F;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        if (getLightsaberItem() != null)
        {
            nbt.setTag("Item", getLightsaberItem().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        NBTTagCompound nbttagcompound1 = nbt.getCompoundTag("LightsaberItem");
        setLightsaberItem(ItemStack.loadItemStackFromNBT(nbttagcompound1));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (getThrower() != null && getThrower().isEntityAlive())
        {
            if (ticksExisted % 3 == 0)
            {
                worldObj.playSound(posX, posY, posZ, getThrower() instanceof EntityPlayer ? ALSounds.player_lightsaber_swing : ALSounds.mob_lightsaber_swing, 1.0F, 1.0F, true);
            }
        }
        else if (!(getThrower() instanceof EntitySithGhost))
        {
            setDead();

            if (!worldObj.isRemote)
            {
                EntityItem entityitem = new EntityItem(worldObj);
                entityitem.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
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
                EntityPlayer player = (EntityPlayer) getThrower();

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
                                    EntityItem entityitem = new EntityItem(worldObj);
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

                if (shouldReturn && entity != null)
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

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        if (getThrower() instanceof EntitySithGhost && mop.entityHit instanceof EntitySithGhost)
        {
            return;
        }

        if (mop.entityHit != null && mop.entityHit != getThrower())
        {
            float damage = (float) ((ItemLightsaberBase) getLightsaberItem().getItem()).getAttackDamage(getLightsaberItem()) + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, getLightsaberItem()) * 1.25F;

            mop.entityHit.setFire(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, getLightsaberItem()) * 4);
            mop.entityHit.attackEntityFrom(ModDamageSources.causeLightsaberDamage(getThrower()), shouldReturn ? damage * 0.25F : damage * (1 - ((float) ticksExisted / 20) * 0.75F));
        }
        else
        {
            shouldReturn = true;
            motionY += 0.2D;
        }
    }
}
