package fiskfille.lightsabers.common.power.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.client.sound.MovingSoundLightning;
import fiskfille.lightsabers.common.damagesource.ModDamageSources;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectLightning extends PowerEffectStatus
{
    public PowerEffectLightning()
    {
        super(Effect.lightning);
    }

    @Override
    public String[] getDesc(Object... args)
    {
        return new String[] {PowerDesc.create("effect", PowerDesc.format("%s %s%s", 4 + (Integer) args[0] * 2, PowerDesc.DAMAGE, EnumChatFormatting.GRAY + "/"), PowerDesc.TARGET)};
    }

    @Override
    public void start(EntityPlayer player, Side side, Object... args)
    {
        if (side.isClient())
        {
            playSound(player);
        }
    }

    @SideOnly(Side.CLIENT)
    public void playSound(EntityPlayer player)
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundLightning(player));
    }

    @Override
    public boolean execute(EntityPlayer player, Side side, Object... args)
    {
        World world = player.worldObj;
        int amplifier = (Integer) args[0];
        boolean flag = super.execute(player, side, args);

        if (flag)
        {
            EntityLivingBase entity = ALHelper.getForceLightningTarget(player);

            if (entity != null)
            {
                entity.attackEntityFrom(ModDamageSources.causeForceLightningDamage(player), 4 + amplifier * 2);
                entity.motionX = 0;
                entity.motionY = Math.min(entity.motionY, 0);
                entity.motionZ = 0;
            }
        }

        return flag;
    }
}
