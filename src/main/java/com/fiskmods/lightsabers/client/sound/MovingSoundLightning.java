package com.fiskmods.lightsabers.client.sound;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.helper.ALHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MovingSoundLightning extends MovingSound
{
    public final EntityLivingBase caster;

    public MovingSoundLightning(EntityLivingBase entity)
    {
        super(new ResourceLocation(ALSounds.player_force_lightning));
        caster = entity;
        repeat = true;
        field_147665_h = 0;
        volume = 1.0F;
    }

    @Override
    public void update()
    {
        float f = ALData.RIGHT_ARM_TIMER.interpolate(caster);

        if (caster.isDead || f <= 0)
        {
            donePlaying = true;
        }
        else
        {
            EntityLivingBase target = ALHelper.getForceLightningTarget(caster);

            if (Minecraft.getMinecraft().thePlayer == target)
            {
                xPosF = (float) target.posX;
                yPosF = (float) target.posY;
                zPosF = (float) target.posZ;
            }
            else
            {
                xPosF = (float) caster.posX;
                yPosF = (float) caster.posY;
                zPosF = (float) caster.posZ;
            }

            volume = f;
            field_147663_c = 0.75F + (float) Math.random() * 0.25F;
        }
    }
}
