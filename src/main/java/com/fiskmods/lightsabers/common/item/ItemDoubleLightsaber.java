package com.fiskmods.lightsabers.common.item;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.ALReflection;
import com.fiskmods.lightsabers.common.damage.ALDamageSources;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import fiskfille.utils.helper.NBTHelper;
import fiskfille.utils.helper.VectorHelper;
import mods.battlegear2.api.IAllowItem;
import mods.battlegear2.api.IOffhandWield;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

@InterfaceList({@Interface(modid = "battlegear2", iface = "mods.battlegear2.api.IOffhandWield"), @Interface(modid = "battlegear2", iface = "mods.battlegear2.api.IAllowItem")})
public class ItemDoubleLightsaber extends ItemLightsaberBase implements IOffhandWield, IAllowItem
{
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean inHand)
    {
        if (!world.isRemote)
        {
            if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("UpperLightsaber", NBT.TAG_COMPOUND) && itemstack.getTagCompound().hasKey("LowerLightsaber", NBT.TAG_COMPOUND))
            {
                get(itemstack); // Makes sure the pre-1.2 data format is also converted server-side
            }
        }
    }
    
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        LightsaberData[] array = new LightsaberData[2];
        
        for (Hilt hilt : Hilt.REGISTRY)
        {
            if (hilt.getType() == Hilt.Type.DOUBLE)
            {
                for (int i = 0; i < array.length; ++i)
                {
                    array[i] = new LightsaberData().set(hilt).set(hilt.getColor()).set(hilt.getFocusingCrystals().toArray(new FocusingCrystal[2]));
                }
                
                list.add(create(array));
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced)
    {
        LightsaberData[] array = get(itemstack);
        Hilt[][] hilt = {array[0].getHilt(), array[1].getHilt()};
        String space = "  ";

        list.add(StatCollector.translateToLocal("lightsaber.color"));
        list.add(space + array[0].getColor().getLocalizedName());
        
        if (array[0].getColor() != array[1].getColor())
        {
            list.add(space + array[1].getColor().getLocalizedName());
        }
        
        list.add(StatCollector.translateToLocal("lightsaber.hilt"));
        
        if (array[0].isHiltUniform() && array[1].isHiltUniform())
        {
            list.add(space + hilt[0][0].getLocalizedName());
            
            if (hilt[0][0] != hilt[1][0])
            {
                list.add(space + hilt[1][0].getLocalizedName());
            }
        }
        else
        {
            for (int i = 0; i < (array[0].getHiltBits() == array[1].getHiltBits() ? 1 : hilt.length); ++i)
            {
                List<Hilt> list1 = Lists.newArrayList(hilt[i]);
                
                if (i == 1)
                {
                    Collections.reverse(list1);
                }
                
                for (int j = 0; j < list1.size(); ++j)
                {
                    list.add(space + list1.get(j).getLocalizedName());
                }
            }
        }
        
        FocusingCrystal[][] crystals = {array[0].getFocusingCrystals(), array[1].getFocusingCrystals()};
        String[] astring = {"upper", "lower"};
        
        if (crystals[0].length > 0 || crystals[1].length > 0)
        {
            list.add(StatCollector.translateToLocal("lightsaber.focusingCrystals"));
        }
        
        for (int i = 0; i < astring.length; ++i)
        {
            if (crystals[i].length > 0)
            {
                list.add(space + StatCollector.translateToLocal("lightsaber." + astring[i]));
                
                for (FocusingCrystal crystal : crystals[i])
                {
                    list.add(space + space + crystal.getLocalizedName());
                }
            }
        }

        if (advanced)
        {
            list.add(StatCollector.translateToLocalFormatted("lightsaber.code.double", Long.toHexString(array[0].hash).toUpperCase(Locale.ROOT), Long.toHexString(array[1].hash).toUpperCase(Locale.ROOT)));
        }
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

                            boolean success = entity.attackEntityFrom(ALDamageSources.causeLightsaberDamage(attacker), attackDamage);

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
    public boolean isOffhandWieldable(ItemStack itemstack, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean allowOffhand(ItemStack main, ItemStack off)
    {
        return false;
    }

    public static LightsaberData[] readFromNBT(NBTTagCompound nbt)
    {
        LightsaberData[] array = new LightsaberData[] {LightsaberData.EMPTY, LightsaberData.EMPTY};
        
        if (nbt.hasKey("UpperLightsaber", NBT.TAG_COMPOUND) && nbt.hasKey("LowerLightsaber", NBT.TAG_COMPOUND))
        {
            NBTTagList list = new NBTTagList();
            String[] astring = {"UpperLightsaber", "LowerLightsaber"};
            
            for (int i = 0; i < astring.length; ++i)
            {
                NBTTagCompound compound = nbt.getCompoundTag(astring[i]);
                compound.setTag("Lightsaber", compound.copy());
                
                array[i] = LightsaberData.readFromNBT(compound);
                list.appendTag(new NBTTagLong(array[i].hash));
                nbt.removeTag(astring[i]);
            }

            nbt.setTag(ALConstants.TAG_LIGHTSABER, list);
        }
        else if (nbt.hasKey(ALConstants.TAG_LIGHTSABER, NBT.TAG_LIST))
        {
            NBTTagList list = nbt.getTagList(ALConstants.TAG_LIGHTSABER, NBT.TAG_LONG);
            List<NBTBase> tags = ALReflection.tagList.get(list);
            
            for (int i = 0; i < Math.min(tags.size(), array.length); ++i)
            {
                LightsaberData data = NBTHelper.readFromNBT(tags.get(i), LightsaberData.class);
                
                if (data != null)
                {
                    array[i] = data.strip();
                }
            }
        }

        return array;
    }

    public static LightsaberData[] get(ItemStack itemstack)
    {
        if (itemstack != null && itemstack.hasTagCompound())
        {
            return readFromNBT(itemstack.getTagCompound());
        }

        return new LightsaberData[] {LightsaberData.EMPTY, LightsaberData.EMPTY};
    }

    public static ItemStack create(LightsaberData[] array)
    {
        ItemStack itemstack = new ItemStack(ModItems.doubleLightsaber);
        NBTTagList list = new NBTTagList();
        
        for (int i = 0; i < array.length; ++i)
        {
            list.appendTag(new NBTTagLong(array[i].hash));
        }
        
        itemstack.setTagCompound(new NBTTagCompound());
        itemstack.getTagCompound().setTag(ALConstants.TAG_LIGHTSABER, list);
        
        return itemstack;
    }
    
    public static ItemStack create(ItemStack upper, ItemStack lower)
    {
        return create(new LightsaberData[] {LightsaberData.get(upper), LightsaberData.get(lower)});
    }
}
