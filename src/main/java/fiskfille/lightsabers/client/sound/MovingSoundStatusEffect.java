package fiskfille.lightsabers.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;

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
        if (theEntity.isDead || DataManager.getEffect(theEntity, theEffect.id) == null)
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
