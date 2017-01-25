package fiskfille.lightsabers.common.power.effect;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.client.particle.ALParticleType;
import fiskfille.lightsabers.client.particle.ALParticles;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectHeal extends PowerEffect
{
    @Override
    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        player.heal((Integer) args[0]);

        if (args.length > 1)
        {
            AxisAlignedBB aabb = player.boundingBox.copy().expand(6, 6, 6);
            List<EntityLivingBase> list = player.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);

            for (EntityLivingBase entity : list)
            {
                if (ALHelper.isAlly(player, entity) || entity == player)
                {
                    if (entity != player)
                    {
                        entity.heal((Integer) args[1]);
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
    public String[] getDesc(Object... args)
    {
        List<String> list = Lists.newArrayList();
        list.add(PowerDesc.create("to", PowerDesc.format("+%s %s", args[0], PowerDesc.HEALTH), PowerDesc.CASTER));

        if (args.length > 1)
        {
            list.add(PowerDesc.create("to", PowerDesc.format("+%s %s", args[1], PowerDesc.HEALTH), PowerDesc.ALLIES));
        }

        return list.toArray(new String[list.size()]);
    }

    @Override
    public String getCastSound(int type)
    {
        return ALSounds.player_force_heal;
    }

    @Override
    public float getCastSoundPitch(int type)
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
