package com.fiskmods.lightsabers.common.force.effect;

import static com.fiskmods.lightsabers.common.force.PowerDesc.*;
import static com.fiskmods.lightsabers.common.force.PowerDesc.Target.*;
import static com.fiskmods.lightsabers.common.force.PowerDesc.Unit.*;

import java.util.List;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.helper.ALHelper;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class PowerEffectMeditation extends PowerEffectInstant
{
    public PowerEffectMeditation(int amplifier)
    {
        super(Effect.MEDITATION, 90, amplifier);
    }
    
    @Override
    public boolean execute(EntityPlayer player, Side side)
    {
        player.setAbsorptionAmount(getAbsorption(amplifier));
        return super.execute(player, side);
    }

    @Override
    public String[] getDesc()
    {
        return new String[]
        {
            create("effect", format("%s %s%s", translateFormatted("forcepower.stat.multiply", ATTACK_DAMAGE, getModifierAmount(amplifier)), EnumChatFormatting.GRAY, 90), CASTER),
            create("to", format("+%s %s", getAbsorption(amplifier), ABSORPTION), CASTER)
        };
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
    
    public static float getAbsorption(int amplifier)
    {
        return 4 + amplifier * 2;
    }
}
