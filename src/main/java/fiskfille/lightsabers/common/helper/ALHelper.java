package fiskfille.lightsabers.common.helper;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.client.render.Lightning;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.data.StatusEffect;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;
import fiskfille.lightsabers.common.power.PowerManager;
import fiskfille.lightsabers.common.power.PowerStats;

public class ALHelper
{
    public static int getXpBarCap(int level)
    {
        return level >= 30 ? 62 + (level - 30) * 7 : (level >= 15 ? 17 + (level - 15) * 3 : 17);
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
        return s1.substring(0, 1).toLowerCase() + s1.substring(1);
    }

    public static String getUnconventionalName(String s)
    {
        s = s.toLowerCase();

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

    public static boolean isLightPower(Power power)
    {
        if (power == Power.lightSide)
        {
            return true;
        }

        if (power.parent != null)
        {
            return isLightPower(power.parent);
        }

        return false;
    }

    public static boolean isDarkPower(Power power)
    {
        if (power == Power.darkSide)
        {
            return true;
        }

        if (power.parent != null)
        {
            return isDarkPower(power.parent);
        }

        return false;
    }

    public static boolean isNeutralPower(Power power)
    {
        if (power == Power.neutral)
        {
            return true;
        }

        if (power.parent != null)
        {
            return isNeutralPower(power.parent);
        }

        return false;
    }

    public static List<Power> getLightPowers()
    {
        List<Power> list = Lists.newArrayList();

        for (Power power : Power.powers)
        {
            if (isLightPower(power))
            {
                list.add(power);
            }
        }

        return list;
    }

    public static List<Power> getDarkPowers()
    {
        List<Power> list = Lists.newArrayList();

        for (Power power : Power.powers)
        {
            if (isDarkPower(power))
            {
                list.add(power);
            }
        }

        return list;
    }

    public static List<Power> getNeutralPowers()
    {
        List<Power> list = Lists.newArrayList();

        for (Power power : Power.powers)
        {
            if (isNeutralPower(power))
            {
                list.add(power);
            }
        }

        return list;
    }

    public static float getLightPercentage(EntityPlayer player)
    {
        List<PowerData> powers = ALPlayerData.getData(player).powers;
        float f = 0;

        for (PowerData data : powers)
        {
            if (data.unlocked)
            {
                if (isLightPower(data.power) && data.power != Power.lightSide)
                {
                    f += 1F / (getLightPowers().size() - 1);
                }
            }
        }

        return f;
    }

    public static float getDarkPercentage(EntityPlayer player)
    {
        List<PowerData> powers = ALPlayerData.getData(player).powers;
        float f = 0;

        for (PowerData data : powers)
        {
            if (data.unlocked)
            {
                if (isDarkPower(data.power) && data.power != Power.darkSide)
                {
                    f += 1F / (getDarkPowers().size() - 1);
                }
            }
        }

        return f;
    }

    public static float getForceBias(EntityPlayer player)
    {
        return getLightPercentage(player) - getDarkPercentage(player);
    }

    public static int getBasePower(EntityPlayer player)
    {
        List<PowerData> powers = ALPlayerData.getData(player).powers;
        int i = 0;

        for (PowerData data : powers)
        {
            if (data.unlocked)
            {
                i += data.power.powerStats.basePowerBonus;
                i -= data.power.powerStats.basePowerRequirement;
            }
        }

        return i;
    }

    public static int getForcePowerMax(EntityPlayer player)
    {
        List<PowerData> powers = ALPlayerData.getData(player).powers;
        int i = 0;

        for (PowerData data : powers)
        {
            if (data.unlocked)
            {
                i += data.power.powerStats.forcePowerBonus;
            }
        }

        return i;
    }

    public static float getForcePowerRegen(EntityPlayer player)
    {
        List<PowerData> powers = ALPlayerData.getData(player).powers;
        float f = 0;

        for (PowerData data : powers)
        {
            if (data.unlocked)
            {
                PowerStats stats = data.power.powerStats;

                if (stats.forcePowerRegenOperation == 0)
                {
                    f += stats.forcePowerRegen;
                }
                else
                {
                    f += f * ((float) stats.forcePowerRegen / 100);
                }
            }
        }

        return f;
    }

    public static List<Power> getChildrenPowers(Power power)
    {
        List<Power> list = Lists.newArrayList();

        for (Power power1 : Power.powers)
        {
            if (power1.parent == power)
            {
                list.add(power1);
            }
        }

        return list;
    }

    public static List<Power> getUnlockedChildrenPowers(EntityPlayer player, Power power)
    {
        List<Power> list = Lists.newArrayList();

        for (Power power1 : Power.powers)
        {
            if (power1.parent == power && PowerManager.getPowerData(player, power1).unlocked)
            {
                list.add(power1);
            }
        }

        return list;
    }

    public static List<Power> getRelevantPowers(EntityPlayer player)
    {
        List<PowerData> powers = ALPlayerData.getData(player).powers;
        List<Power> list = Lists.newArrayList();

        for (PowerData data : powers)
        {
            Power power = data.power;

            if (power != Power.lightSide && isLightPower(power) || power != Power.darkSide && isDarkPower(power) || power != Power.neutral && isNeutralPower(power))
            {
                if (getUnlockedChildrenPowers(player, power).isEmpty() && PowerManager.getPowerData(player, power).unlocked)
                {
                    list.add(power);
                }
            }
        }

        return list;
    }

    public static boolean isAlly(EntityLivingBase entity, EntityLivingBase entity1)
    {
        if (entity1 instanceof EntityTameable && ((EntityTameable) entity1).getOwner() == entity)
        {
            return true;
        }
        else if (entity1 instanceof EntityMob || entity1.getAITarget() == entity)
        {
            return false;
        }
        else if (entity1.getTeam() != null && entity1.getTeam().isSameTeam(entity.getTeam()))
        {
            return true;
        }
        else if (entity.getLastAttacker() == entity1)
        {
            return entity.getLastAttackerTime() > 1200;
        }

        return entity.ridingEntity == entity1;
    }

    public static int getPowerType(Power power)
    {
        return isLightPower(power) ? 1 : (isDarkPower(power) ? -1 : 0);
    }

    public static List<EntityLivingBase> getEffectTargets(EntityLivingBase caster, int effectId)
    {
        List<EntityLivingBase> targets = Lists.newArrayList();

        for (Entity entity1 : (List<Entity>) caster.worldObj.loadedEntityList)
        {
            if (entity1 instanceof EntityLivingBase)
            {
                EntityLivingBase entity = (EntityLivingBase) entity1;
                StatusEffect effect = DataManager.getEffect(entity, effectId);

                if (effect != null && effect.casterUUID != null && effect.casterUUID.equals(caster.getUniqueID()))
                {
                    targets.add(entity);
                }
            }
        }

        return targets;
    }

    public static List<EntityLivingBase> getEffectTargets(EntityLivingBase caster, int effectId, int amplifier)
    {
        List<EntityLivingBase> targets = Lists.newArrayList();

        for (Entity entity1 : (List<Entity>) caster.worldObj.loadedEntityList)
        {
            if (entity1 instanceof EntityLivingBase)
            {
                EntityLivingBase entity = (EntityLivingBase) entity1;
                StatusEffect effect = DataManager.getEffect(entity, effectId);

                if (effect != null && effect.amplifier == amplifier && effect.casterUUID != null && effect.casterUUID.equals(caster.getUniqueID()))
                {
                    targets.add(entity);
                }
            }
        }

        return targets;
    }

    public static EntityLivingBase getForceLightningTarget(EntityLivingBase caster)
    {
        StatusEffect effect = DataManager.getEffect(caster, Effect.lightning.id);

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

                for (EntityLivingBase entity : VectorHelper.getEntitiesNear(caster.worldObj, particleVec.xCoord, particleVec.yCoord, particleVec.zCoord, 1))
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
}
