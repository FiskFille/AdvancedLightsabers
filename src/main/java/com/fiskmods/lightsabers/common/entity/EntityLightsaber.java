package com.fiskmods.lightsabers.common.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.damage.ALDamageSources;
import com.fiskmods.lightsabers.common.item.ItemDoubleLightsaber;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import fiskfille.utils.common.entity.attribute.AttributeWrapper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityLightsaber extends EntityThrowable implements IEntityAdditionalSpawnData
{
    private ItemStack lightsaber;
    private int amplifier;

    public Object data;

    public EntityLightsaber(World world, EntityLivingBase entity, ItemStack itemstack, int amplifier)
    {
        super(world, entity);
        this.amplifier = amplifier;
        setItem(itemstack);
    }

    public EntityLightsaber(World world)
    {
        super(world);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(2, (byte) 0);
    }

    public boolean getShouldReturn()
    {
        return dataWatcher.getWatchableObjectByte(2) > 0;
    }

    public void setShouldReturn(boolean flag)
    {
        dataWatcher.updateObject(2, (byte) (flag ? 1 : 0));
    }

    public ItemStack getItem()
    {
        return lightsaber;
    }

    public void setItem(ItemStack itemstack)
    {
        lightsaber = itemstack;
        data = null;

        if (itemstack != null)
        {
            if (itemstack.getItem() == ModItems.doubleLightsaber)
            {
                setSize(3.15F / (2 - amplifier), 0.0625F);
                data = ItemDoubleLightsaber.get(itemstack);
            }
            else
            {
                setSize(2F / (2 - amplifier), 0.0625F);
                data = LightsaberData.get(itemstack);
            }
        }
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.01F;
    }

    @Override
    protected float func_70182_d()
    {
        return 2;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        if (getItem() != null)
        {
            nbt.setTag("Item", getItem().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        setItem(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("LightsaberItem")));
    }

    @Override
    public void onUpdate()
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (!worldObj.isRemote)
        {
            EntityLivingBase thrower = getThrower();

            if (thrower == null || !thrower.isEntityAlive())
            {
                if (!(thrower instanceof EntitySithGhost))
                {
                    dropItem(this);
                }

                setDead();
                return;
            }

            if (ticksExisted % 3 == 0)
            {
                worldObj.playSoundAtEntity(this, thrower instanceof EntityPlayer ? ALSounds.player_lightsaber_swing : ALSounds.mob_lightsaber_swing, 1, 1);
            }

            if (ticksExisted > 20)
            {
                setShouldReturn(true);
            }

            if (thrower != null && getShouldReturn())
            {
                if (getDistanceToEntity(thrower) <= 2)
                {
                    if (thrower instanceof EntityPlayer)
                    {
                        EntityPlayer player = (EntityPlayer) thrower;

                        if (player.inventory.getCurrentItem() == null)
                        {
                            player.setCurrentItemOrArmor(0, getItem());
                        }
                        else if (!player.inventory.addItemStackToInventory(getItem()))
                        {
                            dropItem(player);
                        }
                    }
                    else
                    {
                        thrower.setCurrentItemOrArmor(0, getItem());
                    }

                    setDead();
                }
                else
                {
                    Vec3 vec3 = Vec3.createVectorHelper(thrower.posX - posX, thrower.posY - posY + thrower.height * 0.6, thrower.posZ - posZ).normalize();
                    double d = 2;

                    motionX = vec3.xCoord * d;
                    motionY = vec3.yCoord * d;
                    motionZ = vec3.zCoord * d;
                }
            }
        }

        if (!getShouldReturn())
        {
            super.onUpdate();
        }
        else
        {
            super.onEntityUpdate();

            Vec3 pos = Vec3.createVectorHelper(posX, posY, posZ);
            Vec3 nextPos = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
            MovingObjectPosition mop = worldObj.rayTraceBlocks(pos, nextPos);
            
            pos = Vec3.createVectorHelper(posX, posY, posZ);
            nextPos = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

            if (mop != null)
            {
                nextPos = Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
            }

            if (!worldObj.isRemote)
            {
                Entity entity = null;
                List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1, 1, 1));
                double minDist = 0;

                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity1 = (Entity) list.get(i);

                    if (entity1.canBeCollidedWith() && (entity1 != getThrower() || ticksExisted >= 5))
                    {
                        AxisAlignedBB aabb = entity1.boundingBox.expand(0.3F, 0.3F, 0.3F);
                        MovingObjectPosition mop1 = aabb.calculateIntercept(pos, nextPos);

                        if (mop1 != null)
                        {
                            double dist = pos.distanceTo(mop1.hitVec);

                            if (dist < minDist || minDist == 0)
                            {
                                entity = entity1;
                                minDist = dist;
                            }
                        }
                    }
                }

                if (entity != null)
                {
                    mop = new MovingObjectPosition(entity);
                }
            }

            if (mop != null)
            {
                if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ) == Blocks.portal)
                {
                    setInPortal();
                }
                else
                {
                    onImpact(mop);
                }
            }

            float motion = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180 / Math.PI);

            for (rotationPitch = (float) (Math.atan2(motionY, motion) * 180 / Math.PI); rotationPitch - prevRotationPitch < -180; prevRotationPitch -= 360)
            {
                ;
            }

            while (rotationPitch - prevRotationPitch >= 180)
            {
                prevRotationPitch += 360;
            }

            while (rotationYaw - prevRotationYaw < -180)
            {
                prevRotationYaw -= 360;
            }

            while (rotationYaw - prevRotationYaw >= 180)
            {
                prevRotationYaw += 360;
            }

            rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
            rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
            posX += motionX;
            posY += motionY;
            posZ += motionZ;

            if (isInWater())
            {
                for (int i = 0; i < 4; ++i)
                {
                    float f = 0.25F;
                    worldObj.spawnParticle("bubble", posX - motionX * f, posY - motionY * f, posZ - motionZ * f, motionX, motionY, motionZ);
                }
            }

            setPosition(posX, posY, posZ);
        }
    }

    public void dropItem(Entity location)
    {
        EntityItem item = new EntityItem(worldObj);
        item.setLocationAndAngles(location.posX, location.posY, location.posZ, 0, 0);
        item.setEntityItemStack(lightsaber);
        worldObj.spawnEntityInWorld(item);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        EntityLivingBase thrower = getThrower();

        if (thrower != null)
        {
            if (thrower instanceof EntitySithGhost && mop.entityHit instanceof EntitySithGhost)
            {
                return;
            }

            if (mop.entityHit != null && mop.entityHit != thrower)
            {
                if (lightsaber != null)
                {
                    Collection<AttributeModifier> c = lightsaber.getAttributeModifiers().get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
                    AttributeWrapper wrapper = new AttributeWrapper(SharedMonsterAttributes.attackDamage);

                    if (!c.isEmpty())
                    {
                        Iterator<AttributeModifier> iterator = c.iterator();

                        while (iterator.hasNext())
                        {
                            AttributeModifier modifier = iterator.next();
                            wrapper.apply(modifier.getAmount(), modifier.getOperation());
                        }
                    }

                    float damage = wrapper.getValue(1);

                    if (damage > 0)
                    {
                        mop.entityHit.attackEntityFrom(ALDamageSources.causeLightsaberDamage(thrower), damage);
                    }
                }
            }
            else
            {
                setShouldReturn(true);
                motionY += 0.2D;
            }
        }
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        ByteBufUtils.writeItemStack(buffer, getItem());
    }

    @Override
    public void readSpawnData(ByteBuf buffer)
    {
        setItem(ByteBufUtils.readItemStack(buffer));
    }
}
