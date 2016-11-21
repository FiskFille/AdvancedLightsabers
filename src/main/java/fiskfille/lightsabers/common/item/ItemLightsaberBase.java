package fiskfille.lightsabers.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.sound.ALSounds;
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
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    public double getAttackDamage(ItemStack itemstack)
    {
        return 8.0D;
    }

    public Entity getThrownLightsaberEntity(World world, EntityLivingBase entity, ItemStack itemstack, int amplifier)
    {
        return new EntityLightsaber(world, entity, itemstack, amplifier);
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
                entity.playSound(entity instanceof EntityPlayer ? ALSounds.player_lightsaber_swing : ALSounds.mob_lightsaber_swing, 1.0F, 1.0F);
            }
            else
            {
                return onPunchBlock(itemstack, entity, mop);
            }
        }

        return super.onEntitySwing(entity, itemstack);
    }

    public static void throwLightsaber(EntityLivingBase entity, ItemStack itemstack, int amplifier)
    {
        Entity lightsaber = ((ItemLightsaberBase)itemstack.getItem()).getThrownLightsaberEntity(entity.worldObj, entity, itemstack, amplifier);
        entity.worldObj.spawnEntityInWorld(lightsaber);
        entity.setCurrentItemOrArmor(0, null);
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
            List possibleEntities = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach).expand((double)expand, (double)expand, (double)expand));
            double closestDistance = reach;

            for (Object possibleEntity : possibleEntities)
            {
                Entity selectingEntity = (Entity) possibleEntity;

                if (selectingEntity.canBeCollidedWith())
                {
                    float borderSize = selectingEntity.getCollisionBorderSize();
                    AxisAlignedBB selectionBounds = selectingEntity.boundingBox.expand((double) borderSize, (double) borderSize, (double) borderSize);
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

    public boolean isFull3D()
    {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
    	if (player.isSneaking() && !world.isRemote)
    	{
    		LightsaberHelper.igniteLightsaber(player, !isActive(itemstack));
    	}
    	
    	return itemstack;
    }

    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
    {
        return !isActive(itemstack);
    }

    public EnumAction getItemUseAction(ItemStack itemstack)
    {
        return EnumAction.block;
    }

    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 72000;
    }

    public Multimap getAttributeModifiers(ItemStack stack)
    {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", getAttackDamage(stack), 0));
        return multimap;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IIconRegister)
    {
        crystalEmptySlotIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":lightsaber_crystal_outline");
        focusingCrystalEmptySlotIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":focusing_crystal_outline");
    }
}
