package com.fiskmods.lightsabers.client.sound;

import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MovingSoundStatusEffect extends MovingSound
{
    public final EntityLivingBase theEntity;
    public final Effect theEffect;

    public MovingSoundStatusEffect(EntityLivingBase entity, Effect effect, String name)
    {
        super(new ResourceLocation(name));
        theEntity = entity;
        theEffect = effect;
        repeat = true;
        field_147665_h = 0;
        volume = 1.0F;
    }

    @Override
    public void update()
    {
        if (theEntity.isDead || StatusEffect.get(theEntity, theEffect) == null)
        {
            donePlaying = true;
        }
        else
        {
            xPosF = (float) theEntity.posX;
            yPosF = (float) theEntity.posY;
            zPosF = (float) theEntity.posZ;
        }
    }
}
