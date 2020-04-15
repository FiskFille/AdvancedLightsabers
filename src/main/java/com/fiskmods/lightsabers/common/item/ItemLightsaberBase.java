package com.fiskmods.lightsabers.common.item;

import java.util.List;

import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.entity.EntityLightsaber;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class ItemLightsaberBase extends ItemSword
{
    public ItemLightsaberBase()
    {
        super(ToolMaterial.EMERALD);
        setHasSubtypes(true);
        setMaxStackSize(1);
        setMaxDamage(0);
        setFull3D();
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        return itemstack;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack itemstack)
    {
        if (isActive(itemstack))
        {
            MovingObjectPosition mop = getMovingObjectPosition(entity.worldObj, entity, true);

            if (mop == null || mop.typeOfHit != MovingObjectType.BLOCK)
            {
                entity.playSound(entity instanceof EntityPlayer ? ALSounds.player_lightsaber_swing : ALSounds.mob_lightsaber_swing, 1.0F, 1.0F);
            }
            else
            {
                return onPunchBlock(itemstack, entity, mop);
            }
        }

        return super.onEntitySwing(entity, itemstack);
    }

    private MovingObjectPosition getMovingObjectPosition(World world, EntityLivingBase entity, boolean b)
    {
        if (entity instanceof EntityPlayer)
        {
            double reach = 5.0D;

            if (entity instanceof EntityPlayerMP)
            {
                reach = ((EntityPlayerMP) entity).theItemInWorldManager.getBlockReachDistance();
            }

            MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, (EntityPlayer) entity, b);

            Vec3 position = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
            Vec3 look = entity.getLook(0.0F);
            Vec3 lookPosition = position.addVector(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach);
            float expand = 1.0F;
            List possibleEntities = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach).expand(expand, expand, expand));
            double closestDistance = reach;

            for (Object possibleEntity : possibleEntities)
            {
                Entity selectingEntity = (Entity) possibleEntity;

                if (selectingEntity.canBeCollidedWith())
                {
                    float borderSize = selectingEntity.getCollisionBorderSize();
                    AxisAlignedBB selectionBounds = selectingEntity.boundingBox.expand(borderSize, borderSize, borderSize);
                    MovingObjectPosition entityMOP = selectionBounds.calculateIntercept(position, lookPosition);

                    if (entityMOP != null)
                    {
                        entityMOP.typeOfHit = MovingObjectPosition.MovingObjectType.ENTITY;
                    }

                    if (selectionBounds.isVecInside(position))
                    {
                        if (closestDistance > 0.0D || closestDistance == 0.0D)
                        {
                            mop = entityMOP;
                            closestDistance = 0.0D;
                        }
                    }
                    else if (entityMOP != null)
                    {
                        double distanceToEntity = position.distanceTo(entityMOP.hitVec);

                        if (distanceToEntity < closestDistance || closestDistance == 0.0D)
                        {
                            if (selectingEntity == selectingEntity.ridingEntity && !selectingEntity.canRiderInteract())
                            {
                                if (closestDistance == 0.0D)
                                {
                                    mop = entityMOP;
                                }
                            }
                            else
                            {
                                mop = entityMOP;
                                closestDistance = distanceToEntity;
                            }
                        }
                    }
                }
            }

            return mop;
        }

        return null;
    }

    public boolean onPunchBlock(ItemStack stack, EntityLivingBase entity, MovingObjectPosition mop)
    {
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
    {
        return !isActive(itemstack);
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 8, 0));
        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
    }

    public static boolean isActive(ItemStack itemstack)
    {
        return itemstack != null && itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("active");
    }
    
    public static ItemStack setActive(ItemStack itemstack, boolean state)
    {
        if (!itemstack.hasTagCompound())
        {
            itemstack.setTagCompound(new NBTTagCompound());
        }

        itemstack.getTagCompound().setBoolean("active", state);
        
        return itemstack;
    }

    public static void ignite(EntityLivingBase entity, boolean state)
    {
        ItemStack itemstack = entity.getHeldItem();

        if (itemstack != null && isActive(itemstack) != state)
        {
            String[] sounds = {ALSounds.mob_lightsaber_on, ALSounds.mob_lightsaber_off};

            if (entity instanceof EntityPlayer)
            {
                sounds = new String[] {ALSounds.player_lightsaber_on, ALSounds.player_lightsaber_off};
            }

            entity.worldObj.playSoundAtEntity(entity, sounds[state ? 0 : 1], 1.0F, 1.0F);
            setActive(itemstack, state);
        }
    }

    public static void throwLightsaber(EntityLivingBase entity, ItemStack itemstack, int amplifier)
    {
        entity.worldObj.spawnEntityInWorld(new EntityLightsaber(entity.worldObj, entity, itemstack, amplifier));
        entity.setCurrentItemOrArmor(0, null);
    }
}
