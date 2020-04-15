package fiskfille.utils.helper;

import java.util.Random;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class FiskServerUtils
{
    private static final Random rand = new Random();

    public static String getActiveModId()
    {
        ModContainer container = Loader.instance().activeModContainer();

        if (container != null)
        {
            return container.getModId();
        }

        return "minecraft";
    }

    public static String getActiveModName()
    {
        ModContainer container = Loader.instance().activeModContainer();

        if (container != null)
        {
            return container.getName();
        }

        return "Minecraft";
    }

    public static ItemStack getStackFrom(Object obj)
    {
        if (obj instanceof Item)
        {
            return new ItemStack((Item) obj);
        }
        else if (obj instanceof Block)
        {
            return new ItemStack((Block) obj);
        }
        else if (obj instanceof ItemStack)
        {
            return (ItemStack) obj;
        }

        return null;
    }

    public static ItemStack getStackInSlot(EntityPlayer player, int slot)
    {
        if (slot >= 0 && slot < player.inventory.getSizeInventory())
        {
            return player.inventory.getStackInSlot(slot);
        }

        return null;
    }

    public static boolean cure(EntityLivingBase entity, Potion potion)
    {
        if (entity.isPotionActive(potion))
        {
            entity.removePotionEffect(potion.id);
            return true;
        }

        return false;
    }

    public static boolean canEntityEdit(Entity entity, int x, int y, int z, int side, ItemStack heldItem)
    {
        if (entity instanceof EntityPlayer)
        {
            return ((EntityPlayer) entity).canPlayerEdit(x, y, z, side, heldItem);
        }
        else if (entity instanceof EntityLivingBase)
        {
            return entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
        }

        return false;
    }

    public static boolean canEntityEdit(Entity entity, MovingObjectPosition mop, ItemStack heldItem)
    {
        return canEntityEdit(entity, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, heldItem);
    }

    public static boolean isMeleeDamage(DamageSource source)
    {
        return source.getEntity() != null && !source.isMagicDamage() && !source.isExplosion() && !source.isProjectile() && !source.isFireDamage();
    }

    public static boolean isEntityLookingAt(EntityLivingBase observer, Entity entity, double accuracy)
    {
        Vec3 vec3 = observer.getLookVec().normalize();
        Vec3 vec31 = Vec3.createVectorHelper(entity.posX - observer.posX, entity.boundingBox.minY + entity.height / 2.0F - (observer.posY + observer.getEyeHeight()), entity.posZ - observer.posZ);
        double d0 = vec31.lengthVector();
        double d1 = vec3.dotProduct(vec31.normalize());

        return d1 > 1 - accuracy / d0;
    }

    public static boolean isEntityLookingAt(EntityLivingBase observer, Entity entity)
    {
        return isEntityLookingAt(observer, entity, 0.5) && observer.canEntityBeSeen(entity);
    }

    public static double interpolate(double a, double b, double progress)
    {
        return a + (b - a) * progress;
    }

    public static float interpolate(float a, float b, float progress)
    {
        return (float) interpolate(a, b, (double) progress);
    }

    public static <T> T nonNull(T[] iter)
    {
        for (T t : iter)
        {
            if (t != null)
            {
                return t;
            }
        }

        return null;
    }

    public static <T> T nonNull(Iterable<T> iter)
    {
        for (T t : iter)
        {
            if (t != null)
            {
                return t;
            }
        }

        return null;
    }

    public static <T> T nonNull(Iterable iter, Class<T> filter)
    {
        for (Object obj : iter)
        {
            if (obj != null && filter.isInstance(obj))
            {
                return (T) obj;
            }
        }

        return null;
    }

    public static void dropItems(World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof IInventory)
        {
            IInventory inventory = (IInventory) tile;

            for (int i = 0; i < inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = inventory.getStackInSlot(i);

                if (itemstack != null)
                {
                    float f = rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int j = rand.nextInt(21) + 10;

                        if (j > itemstack.stackSize)
                        {
                            j = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j;
                        EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = rand.nextGaussian() * f3;
                        entityitem.motionY = rand.nextGaussian() * f3 + 0.2;
                        entityitem.motionZ = rand.nextGaussian() * f3;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }
    }
}
