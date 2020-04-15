package com.fiskmods.lightsabers.common.force.effect;

import java.util.List;

import com.fiskmods.lightsabers.client.particle.ALParticleType;
import com.fiskmods.lightsabers.client.particle.ALParticles;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.force.ForceSide;
import com.fiskmods.lightsabers.common.force.PowerDesc;
import com.fiskmods.lightsabers.common.force.PowerDesc.Target;
import com.fiskmods.lightsabers.common.force.PowerDesc.Unit;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class PowerEffectHeal extends PowerEffect
{
    public final float heal;
    public final float aoeHeal;
    
    public PowerEffectHeal(float heal, float aoeHeal)
    {
        super(0);
        this.heal = heal;
        this.aoeHeal = aoeHeal;
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        player.heal(amplifier);

        if (aoeHeal > 0)
        {
            AxisAlignedBB aabb = player.boundingBox.copy().expand(6, 6, 6);
            List<EntityLivingBase> list = player.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);

            for (EntityLivingBase entity : list)
            {
                if (ALHelper.isAlly(player, entity) || entity == player)
                {
                    if (entity != player)
                    {
                        entity.heal(aoeHeal);
                    }

                    if (side.isClient())
                    {
                        doParticles(entity);
                    }
                }
            }
        }
        else
        {
            if (side.isClient())
            {
                doParticles(player);
            }
        }

        return true;
    }

    @Override
    public String[] getDesc()
    {
        List<String> list = Lists.newArrayList();
        list.add(PowerDesc.create("to", PowerDesc.format("+%s %s", heal, Unit.HEALTH), Target.CASTER));

        if (aoeHeal > 0)
        {
            list.add(PowerDesc.create("to", PowerDesc.format("+%s %s", aoeHeal, Unit.HEALTH), Target.ALLIES));
        }

        return list.toArray(new String[list.size()]);
    }

    @Override
    public String getCastSound(ForceSide side)
    {
        return ALSounds.player_force_heal;
    }

    @Override
    public float getCastSoundPitch(ForceSide side)
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public void doParticles(EntityLivingBase entity)
    {
        for (int i = 0; i < 16; ++i)
        {
            ALParticles.spawnParticle(ALParticleType.FORCE_HEAL, entity.posX + (rand.nextFloat() * 2 - 1) * entity.width, entity.boundingBox.minY + entity.height / 3 + (entity.height / 3) * 2 * rand.nextFloat(), entity.posZ + (rand.nextFloat() * 2 - 1) * entity.width, 0, 0, 0);
        }
    }
}
