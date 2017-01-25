package fiskfille.lightsabers.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.asm.ASMHooksClient;
import fiskfille.lightsabers.common.entity.EntityLightsaber;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;

@SideOnly(Side.CLIENT)
public class MovingSoundHum extends MovingSound
{
    public final Entity theEntity;

    public MovingSoundHum(Entity entity, String type)
    {
        super(new ResourceLocation(Lightsabers.modid, "hum_" + type));
        theEntity = entity;
        repeat = true;
        field_147665_h = 0;
        volume = 0.25F;
    }

    @Override
    public void update()
    {
        boolean flag = false;

        if (theEntity instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase) theEntity;
            ItemStack itemstack = entity.getHeldItem();
            flag = itemstack == null || !(itemstack.getItem() instanceof ItemLightsaberBase) || !ItemLightsaberBase.isActive(itemstack);
        }
        else if (theEntity instanceof EntityLightsaber)
        {

        }

        if (theEntity.isDead || flag)
        {
            donePlaying = true;
            ASMHooksClient.humSounds.remove(theEntity.getUniqueID().toString());
        }
        else
        {
            xPosF = (float) theEntity.posX;
            yPosF = (float) theEntity.posY;
            zPosF = (float) theEntity.posZ;
        }
    }
}
