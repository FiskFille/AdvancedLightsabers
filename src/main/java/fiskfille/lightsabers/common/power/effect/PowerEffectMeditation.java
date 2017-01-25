package fiskfille.lightsabers.common.power.effect;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectMeditation extends PowerEffect
{
    @Override
    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        AxisAlignedBB aabb = player.boundingBox.copy().expand(6, 6, 6);
        List<EntityLivingBase> list = player.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, IEntitySelector.selectAnything);

        for (EntityLivingBase entity : list)
        {
            if (ALHelper.isAlly(player, entity) || entity == player)
            {
                DataManager.addEffect(entity, Effect.meditation.id, 1800, (Integer) args[0]);
                entity.setAbsorptionAmount(4 + (Integer) args[0] * 2);
            }
        }

        return true;
    }

    @Override
    public String[] getDesc(Object... args)
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", PowerDesc.translateFormatted("forcepower.stat.multiply", PowerDesc.ATTACK_DAMAGE, getModifierAmount((Integer) args[0])), EnumChatFormatting.GRAY, 90), PowerDesc.CASTER), PowerDesc.create("to", PowerDesc.format("+%s %s", 4 + (Integer) args[0] * 2, PowerDesc.ABSORPTION), PowerDesc.CASTER)};
    }

    public static float getModifierAmount(int amplifier)
    {
        float f = 0.25F;

        for (int i = 0; i < amplifier; ++i)
        {
            f *= 2;
        }

        return 1 + f;
    }
}
