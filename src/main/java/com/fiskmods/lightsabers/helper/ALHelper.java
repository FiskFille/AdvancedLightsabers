package com.fiskmods.lightsabers.helper;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import com.fiskmods.lightsabers.client.render.Lightning;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.ForceSide;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerData;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import fiskfille.utils.helper.VectorHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ALHelper
{
    public static int getXpBarCap(int level)
    {
        return level >= 30 ? 62 + (level - 30) * 7 : level >= 15 ? 17 + (level - 15) * 3 : 17;
    }

    public static int getTotalXp(EntityPlayer player)
    {
        int totalXp = Math.round(player.xpBarCap() * player.experience);

        for (int i = 0; i < player.experienceLevel; ++i)
        {
            totalXp += getXpBarCap(i);
        }

        return totalXp;
    }

    public static int getTotalXpToReachLevel(EntityPlayer player, int level)
    {
        int i = 0;

        for (int j = 0; j < level; ++j)
        {
            i += getXpBarCap(j + 1);
        }

        return i;
    }

    public static void removeExperience(EntityPlayer player, int amount)
    {
        player.experienceTotal = getTotalXp(player);

        if (player.experience > 0 && player.experienceLevel >= 0)
        {
            --player.experienceTotal;
            player.experience -= 1F / player.xpBarCap();
        }
        else if (player.experienceLevel > 0)
        {
            --player.experienceTotal;
            --player.experienceLevel;
            player.experience = (float) (player.xpBarCap() - 2) / player.xpBarCap();
        }
    }

    public static String getConventionalName(String s)
    {
        String s1 = getUnconventionalName(s);
        return s1.substring(0, 1).toLowerCase(Locale.ROOT) + s1.substring(1);
    }

    public static String getUnconventionalName(String s)
    {
        s = s.toLowerCase(Locale.ROOT);

        for (int i = 0; i < s.length(); ++i)
        {
            if (i > 0 && s.charAt(i - 1) == '_' && i < s.length())
            {
                s = s.substring(0, i) + s.substring(i, i + 1).toUpperCase() + s.substring(i + 1);
            }
        }

        s = s.replace(" ", "").replace("'", "").replace("/", "").replace("\\", "").replace("_", "").replace("-", "").replace("(", "").replace(")", "");
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static List<Power> getPowers(ForceSide side)
    {
        List<Power> list = Lists.newArrayList();

        for (Power power : Power.POWERS)
        {
            if (side == power.getSide())
            {
                list.add(power);
            }
        }

        return list;
    }

    public static float getCompletion(EntityPlayer player, ForceSide side)
    {
        int powers = getPowers(side).size();
        float f = 0;

        for (PowerData data : ALData.POWERS.get(player))
        {
            if (data.isUnlocked() && data.power != side.getPower() && side == data.power.getSide())
            {
                f += 1F / (powers - 1);
            }
        }

        return f;
    }

    public static float getForceBalance(EntityPlayer player)
    {
        float light = getCompletion(player, ForceSide.LIGHT);
        float dark = getCompletion(player, ForceSide.DARK);
        
        if (light != 0 || dark != 0)
        {
            if (light > dark)
            {
                return 1 - dark / light;
            }
            else if (dark > light)
            {
                return -1 + light / dark;
            }
        }
        
        return 0;
    }

    public static byte getBasePower(EntityPlayer player)
    {
        return ALData.POWERS.get(player).getBasePower();
    }

    public static int getForcePowerMax(EntityPlayer player)
    {
        return ALData.POWERS.get(player).getForceMax();
    }

    public static float getForcePowerRegen(EntityPlayer player)
    {
        return ALData.POWERS.get(player).getRegen();
    }

    public static List<PowerData> getRelevantPowers(EntityPlayer player)
    {
        return Lists.newArrayList(Iterables.filter(ALData.POWERS.get(player), ALPredicates.isRelevant(player)));
    }
    
    public static Set<Power> getUnlockedChildren(EntityPlayer player, Power power)
    {
        return Sets.filter(power.children, ALPredicates.isUnlocked(player));
    }

    public static boolean isAlly(EntityLivingBase to, EntityLivingBase entity)
    {
        if (entity instanceof EntityTameable && ((EntityTameable) entity).getOwner() == to)
        {
            return true;
        }
        else if (entity instanceof EntityMob || entity.getAITarget() == to)
        {
            return false;
        }
        else if (entity.getTeam() != null && entity.getTeam().isSameTeam(to.getTeam()))
        {
            return true;
        }
        else if (to.getLastAttacker() == entity)
        {
            return to.getLastAttackerTime() > 1200;
        }

        return to.ridingEntity == entity;
    }

    public static EntityLivingBase getForceLightningTarget(EntityLivingBase caster)
    {
        StatusEffect effect = StatusEffect.get(caster, Effect.LIGHTNING);

        if (effect != null)
        {
            Vec3 src = VectorHelper.getOffsetCoords(caster, 0, 0, 0);
            Vec3 dst = VectorHelper.getOffsetCoords(caster, 0, 0, 7);
            Vec3 hitVec = null;
            MovingObjectPosition rayTrace = caster.worldObj.rayTraceBlocks(VectorHelper.copy(src), VectorHelper.copy(dst));

            if (rayTrace == null)
            {
                hitVec = dst;
            }
            else
            {
                hitVec = rayTrace.hitVec;
            }

            double distance = caster.getDistance(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);

            for (double point = 0; point <= distance; point += 0.15D)
            {
                Vec3 particleVec = VectorHelper.getOffsetCoords(caster, 0, 0, point);

                for (EntityLivingBase entity : VectorHelper.getEntitiesNear(EntityLivingBase.class, caster.worldObj, particleVec, 1))
                {
                    if (entity != null && entity != caster && caster.ridingEntity != entity)
                    {
                        hitVec.xCoord = entity.posX;
                        hitVec.yCoord = entity.posY;
                        hitVec.zCoord = entity.posZ;
                        rayTrace = new MovingObjectPosition(entity, hitVec);
                        distance = caster.getDistance(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);
                        break;
                    }
                }
            }

            if (rayTrace != null)
            {
                if (rayTrace.typeOfHit == MovingObjectType.ENTITY && rayTrace.entityHit instanceof EntityLivingBase)
                {
                    EntityLivingBase entity = (EntityLivingBase) rayTrace.entityHit;

                    return entity;
                }
            }
        }

        return null;
    }

    public static Lightning createLightning(Vec3 color, long seed, float length)
    {
        Random rand = new Random();

        if (seed != 0)
        {
            rand.setSeed(seed);
        }

        Lightning lightning = new Lightning(rand.nextFloat() * length, color).setRotation(rand.nextFloat() * 360, rand.nextFloat() * 360, rand.nextFloat() * 360);
        branchLightning(lightning, rand, length, 0);

        return lightning;
    }

    private static void branchLightning(Lightning lightning, Random rand, float length, int branch)
    {
        Lightning lightning1 = new Lightning(rand.nextFloat() * length).setRotation(rand.nextFloat() * rand.nextFloat() * 90, rand.nextFloat() * rand.nextFloat() * 90, rand.nextFloat() * rand.nextFloat() * 90);
        lightning.addChild(lightning1);

        if (branch < 10)
        {
            if (rand.nextDouble() < 1 - branch * 0.1D)
            {
                branchLightning(lightning1, rand, length, ++branch);
            }
        }

        if (rand.nextDouble() < 0.1D)
        {
            branchLightning(lightning, rand, length, 7);
        }
    }
    
    public static void dropItem(World world, int x, int y, int z, ItemStack stack, Random rand)
    {
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;

        while (stack.stackSize > 0)
        {
            int i = rand.nextInt(21) + 10;

            if (i > stack.stackSize)
            {
                i = stack.stackSize;
            }

            stack.stackSize -= i;
            EntityItem entity = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), i, stack.getItemDamage()));

            if (stack.hasTagCompound())
            {
                entity.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
            }

            float f3 = 0.05F;
            entity.motionX = (float) rand.nextGaussian() * f3;
            entity.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
            entity.motionZ = (float) rand.nextGaussian() * f3;
            world.spawnEntityInWorld(entity);
        }
    }
}
