package fiskfille.lightsabers.common.entity;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;

public class EntityGrievous extends EntityMob implements IAnimatedEntity
{
    private ItemStack[] heldItems = new ItemStack[4];
    protected float[] heldItemDropChances = new float[4];
    
    public Animation animationIgnite = Animation.create(0);

    public int animationTick;
    public Animation currentAnimation;

    public EntityGrievous(World world)
    {
        super(world);
        getNavigator().setAvoidsWater(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        tasks.addTask(3, new EntityAIWander(this, 1));
        tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 4F, 10F));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        setSize(0.8F, 2.4F);
        
        stepHeight = 1;

        for (int i = 0; i < heldItemDropChances.length; ++i)
        {
            heldItemDropChances[i] = 0.085F;
        }
        
        addRandomArmor();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
        getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32);
    }

    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        int xp = super.getExperiencePoints(player);

        if (xp > 0)
        {
            ItemStack[] aitemstack = getHeldItems();

            for (int i = 0; i < aitemstack.length; ++i)
            {
                if (aitemstack[i] != null && heldItemDropChances[i] <= 1.0F)
                {
                    xp += 1 + rand.nextInt(3);
                }
            }

            return xp;
        }
        else
        {
            return experienceValue;
        }
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return null;
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        AnimationHandler.INSTANCE.updateAnimations(this);
        
        double d0 = posX - prevPosX;
        double d2 = posZ - prevPosZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float yaw = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        
        
        renderYawOffset = yaw;
//        renderYawOffset = 0;
        
        for (int slot = 0; slot < 4; ++slot)
        {
            ItemStack itemstack = getHeldItem(slot);
            
            if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase)
            {
                ItemLightsaberBase.refreshNBT(itemstack);

                if (!itemstack.getTagCompound().getBoolean("active"))
                {
                    itemstack.getTagCompound().setBoolean("active", true);
                }
            }
            
//            setHeldItem(slot, new ItemStack(Items.diamond_axe));
//            setHeldItem(slot, LightsaberHelper.createLightsaber(LightsaberColors.MEDIUM_BLUE, LightsaberManager.lightsaberVaidAncient));
        }
    }
    
    @Override
    public ItemStack getHeldItem()
    {
        return null;
    }
    
    @Override
    public ItemStack getEquipmentInSlot(int slot)
    {
        return null;
    }

    public ItemStack getHeldItem(int slot)
    {
        return heldItems[slot];
    }

    public ItemStack[] getHeldItems()
    {
        return heldItems;
    }

    public void setHeldItem(int slot, ItemStack itemstack)
    {
        heldItems[slot] = itemstack;
    }

    public void setHeldItemDropChance(int slot, float chance)
    {
        heldItemDropChances[slot] = chance;
    }

    @Override
    protected void dropEquipment(boolean recentlyHit, int looting)
    {
        for (int i = 0; i < getHeldItems().length; ++i)
        {
            ItemStack itemstack = getEquipmentInSlot(i);
            boolean flag = heldItemDropChances[i] > 1.0F;

            if (itemstack != null && (recentlyHit || flag) && rand.nextFloat() - looting * 0.01F < heldItemDropChances[i])
            {
                if (!flag && itemstack.isItemStackDamageable())
                {
                    int j = Math.max(itemstack.getMaxDamage() - 25, 1);
                    int k = itemstack.getMaxDamage() - rand.nextInt(rand.nextInt(j) + 1);

                    if (k > j)
                    {
                        k = j;
                    }

                    if (k < 1)
                    {
                        k = 1;
                    }

                    itemstack.setItemDamage(k);
                }

                entityDropItem(itemstack, 0.0F);
            }
        }
    }
    
    @Override
    protected void addRandomArmor()
    {
        for (int slot = 0; slot < 4; ++slot)
        {
            int colorId = (slot == 0 || slot == 3) ? LightsaberColors.GREEN : LightsaberColors.MEDIUM_BLUE;
            setHeldItem(slot, LightsaberHelper.createRandomLightsaber(rand, LightsaberHelper.getCrystalIdFromColor(colorId)));
        }
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
        return super.onSpawnWithEgg(data);
    }

    @Override
    public int getVerticalFaceSpeed()
    {
        return 400;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);

        if (nbt.hasKey("HeldItems", NBT.TAG_LIST))
        {
            NBTTagList nbttaglist = nbt.getTagList("HeldItems", NBT.TAG_COMPOUND);

            for (int i = 0; i < heldItems.length; ++i)
            {
                heldItems[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
            }
        }

        if (nbt.hasKey("HeldDropChances", NBT.TAG_LIST))
        {
            NBTTagList nbttaglist = nbt.getTagList("HeldDropChances", NBT.TAG_FLOAT);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                heldItemDropChances[i] = nbttaglist.func_150308_e(i);
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < heldItems.length; ++i)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            if (heldItems[i] != null)
            {
                heldItems[i].writeToNBT(nbttagcompound1);
            }

            nbttaglist.appendTag(nbttagcompound1);
        }

        nbt.setTag("HeldItems", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();

        for (int i = 0; i < heldItemDropChances.length; ++i)
        {
            nbttaglist1.appendTag(new NBTTagFloat(heldItemDropChances[i]));
        }

        nbt.setTag("HeldDropChances", nbttaglist1);
    }
    
    @Override
    public boolean canPickUpLoot()
    {
        return false;
    }

    @Override
    public int getAnimationTick()
    {
        return animationTick;
    }

    @Override
    public void setAnimationTick(int tick)
    {
        animationTick = tick;
    }

    @Override
    public Animation getAnimation()
    {
        return currentAnimation;
    }

    @Override
    public void setAnimation(Animation animation)
    {
        currentAnimation = animation;
    }

    @Override
    public Animation[] getAnimations()
    {
        return new Animation[] {};
    }
}
