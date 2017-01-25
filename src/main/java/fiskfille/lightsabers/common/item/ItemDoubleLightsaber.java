package fiskfille.lightsabers.common.item;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.LightsaberAPI;
import fiskfille.lightsabers.common.damagesource.ModDamageSources;
import fiskfille.lightsabers.common.entity.EntityDoubleLightsaber;
import fiskfille.lightsabers.common.helper.FocusingCrystals;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.helper.VectorHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumLightsaberType;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class ItemDoubleLightsaber extends ItemLightsaberBase
{
    public ItemDoubleLightsaber()
    {
        super();
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (Lightsaber lightsaber : LightsaberAPI.getLightsabers())
        {
            if (lightsaber.getType() == EnumLightsaberType.DOUBLE)
            {
                int[] aint = new int[lightsaber.getFocusingCrystals().length];

                for (int i = 0; i < aint.length; ++i)
                {
                    String name = lightsaber.getFocusingCrystals()[i];
                    aint[i] = LightsaberHelper.getFocusingCrystalIdFromName(name);
                }

                ItemStack itemstack = LightsaberHelper.createLightsaber(LightsaberHelper.getCrystalIdFromColor(lightsaber.getColor()), lightsaber, aint);
                list.add(LightsaberHelper.createDoubleLightsaber(itemstack, itemstack));
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag)
    {
        ItemStack upper = LightsaberHelper.getDoubleLightsaberUpper(itemstack);
        ItemStack lower = LightsaberHelper.getDoubleLightsaberLower(itemstack);
        String s = "  ";

        if (LightsaberHelper.getColorId(upper) == LightsaberHelper.getColorId(lower))
        {
            list.add(StatCollector.translateToLocal("lightsaber.color"));
            list.add(s + LightsaberColors.getColorName(LightsaberHelper.getColorId(upper)));
        }
        else
        {
            list.add(StatCollector.translateToLocal("lightsaber.color"));
            list.add(s + LightsaberColors.getColorName(LightsaberHelper.getColorId(upper)));
            list.add(s + LightsaberColors.getColorName(LightsaberHelper.getColorId(lower)));
        }

        List<String> list1 = Lists.newArrayList();
        list1.add(LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
        list1.add(LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
        list1.add(LightsaberHelper.getPartString(upper, EnumPartType.BODY));
        list1.add(LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));

        String prevString = null;
        int i = 0;

        for (String s1 : list1)
        {
            if (prevString != null)
            {
                if (prevString.equals(s1))
                {
                    ++i;
                }
            }
            else
            {
                ++i;
            }

            prevString = s1;
        }

        List<String> list2 = Lists.newArrayList();
        list2.add(LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
        list2.add(LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
        list2.add(LightsaberHelper.getPartString(upper, EnumPartType.BODY));
        list2.add(LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));

        String prevString1 = null;
        int i1 = 0;

        for (String s1 : list2)
        {
            if (prevString1 != null)
            {
                if (prevString1.equals(s1))
                {
                    ++i1;
                }
            }
            else
            {
                ++i1;
            }

            prevString1 = s1;
        }

        List<String> list3 = Lists.newArrayList();
        list3.add(LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
        list3.add(LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
        list3.add(LightsaberHelper.getPartString(upper, EnumPartType.BODY));
        list3.add(LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));
        list3.add(LightsaberHelper.getPartString(lower, EnumPartType.POMMEL));
        list3.add(LightsaberHelper.getPartString(lower, EnumPartType.BODY));
        list3.add(LightsaberHelper.getPartString(lower, EnumPartType.SWITCH_SECTION));
        list3.add(LightsaberHelper.getPartString(lower, EnumPartType.EMITTER));

        String prevString2 = null;
        int i2 = 0;

        for (String s1 : list3)
        {
            if (prevString2 != null)
            {
                if (prevString2.equals(s1))
                {
                    ++i2;
                }
            }
            else
            {
                ++i2;
            }

            prevString2 = s1;
        }

        list.add(StatCollector.translateToLocal("lightsaber.hilt"));

        if (i2 == 8)
        {
            list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
        }
        else if (i == 4 && i1 == 4)
        {
            list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
            list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.EMITTER));
        }
        else
        {
            list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.EMITTER));
            list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.SWITCH_SECTION));
            list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.BODY));
            list.add(s + LightsaberHelper.getPartString(upper, EnumPartType.POMMEL));
            list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.POMMEL));
            list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.BODY));
            list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.SWITCH_SECTION));
            list.add(s + LightsaberHelper.getPartString(lower, EnumPartType.EMITTER));
        }

        int[] aint = LightsaberHelper.getFocusingCrystalIds(upper);
        int[] aint1 = LightsaberHelper.getFocusingCrystalIds(lower);

        if (aint.length > 0 || aint1.length > 0)
        {
            list.add(StatCollector.translateToLocal("lightsaber.focusingCrystals"));
        }

        if (aint.length > 0)
        {
            list.add(s + StatCollector.translateToLocal("lightsaber.upper"));
        }

        for (int id : aint)
        {
            list.add(s + s + FocusingCrystals.getFocusingCrystalName(id));
        }

        if (aint1.length > 0)
        {
            list.add(s + StatCollector.translateToLocal("lightsaber.lower"));
        }

        for (int id : aint1)
        {
            list.add(s + s + FocusingCrystals.getFocusingCrystalName(id));
        }
    }

    @Override
    public Entity getThrownLightsaberEntity(World world, EntityLivingBase entity, ItemStack itemstack, int amplifier)
    {
        return new EntityDoubleLightsaber(world, entity, itemstack, amplifier);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase target, EntityLivingBase attacker)
    {
        float width = attacker.width * 2;
        Vec3 vec3 = VectorHelper.getOffsetCoords(attacker, 0, 0, -0.2F);
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(-width, 0, -width, width, attacker.height, width).offset(vec3.xCoord, attacker.boundingBox.minY, vec3.zCoord);
        List<EntityLivingBase> list = attacker.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);

        for (EntityLivingBase entity : list)
        {
            if (entity != attacker)
            {
                ItemStack stack = attacker.getHeldItem();

                if (attacker instanceof EntityPlayer && stack != null && stack.getItem().onLeftClickEntity(stack, (EntityPlayer) attacker, entity))
                {
                    continue;
                }

                if (entity.canAttackWithItem())
                {
                    if (!entity.hitByEntity(attacker))
                    {
                        float attackDamage = (float) attacker.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                        float livingModifier = EnchantmentHelper.getEnchantmentModifierLiving(attacker, entity);
                        int knockback = EnchantmentHelper.getKnockbackModifier(attacker, entity);

                        if (attacker.isSprinting())
                        {
                            ++knockback;
                        }

                        if (attackDamage > 0.0F || livingModifier > 0.0F)
                        {
                            boolean crit = attacker.fallDistance > 0.0F && !attacker.onGround && !attacker.isOnLadder() && !attacker.isInWater() && !attacker.isPotionActive(Potion.blindness) && attacker.ridingEntity == null;

                            if (crit && attackDamage > 0.0F)
                            {
                                attackDamage *= 1.5F;
                            }

                            attackDamage += livingModifier;

                            boolean onFire = false;
                            int fire = EnchantmentHelper.getFireAspectModifier(attacker);

                            if (fire > 0 && !entity.isBurning())
                            {
                                onFire = true;
                                entity.setFire(1);
                            }

                            boolean success = entity.attackEntityFrom(ModDamageSources.causeLightsaberDamage(attacker), attackDamage);

                            if (success)
                            {
                                if (knockback > 0)
                                {
//									entity.addVelocity
//									(
//										-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)knockback * 0.5F,
//										0.1D,
//										MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)knockback * 0.5F
//									);

                                    double d = knockback * 0.25F;
                                    double x = attacker.posX - entity.posX;
                                    double z = attacker.posZ - entity.posZ;

                                    while (Math.sqrt(x * x + z * z) > d)
                                    {
                                        double d1 = 0.95D;
                                        x *= d1;
                                        z *= d1;
                                    }

                                    while (Math.sqrt(x * x + z * z) < d)
                                    {
                                        double d1 = 1.05D;
                                        x *= d1;
                                        z *= d1;
                                    }

                                    entity.addVelocity(-x, 0.1D, -z);

                                    attacker.motionX *= 0.6D;
                                    attacker.motionZ *= 0.6D;
                                    attacker.setSprinting(false);
                                }

                                if (attacker instanceof EntityPlayer)
                                {
                                    EntityPlayer player = (EntityPlayer) attacker;

                                    if (crit)
                                    {
                                        player.onCriticalHit(entity);
                                    }

                                    if (livingModifier > 0.0F)
                                    {
                                        player.onEnchantmentCritical(entity);
                                    }

                                    if (attackDamage >= 18.0F)
                                    {
                                        player.triggerAchievement(AchievementList.overkill);
                                    }

                                    player.setLastAttacker(entity);
                                    EnchantmentHelper.func_151384_a(entity, player);
                                    EnchantmentHelper.func_151385_b(player, entity);

                                    player.addStat(StatList.damageDealtStat, Math.round(attackDamage * 10.0F));

                                    if (fire > 0)
                                    {
                                        entity.setFire(fire * 4);
                                    }

                                    player.addExhaustion(0.3F);
                                }
                            }
                            else if (onFire)
                            {
                                entity.extinguish();
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public double getAttackDamage(ItemStack itemstack)
    {
        return 8.0D;
    }
}
