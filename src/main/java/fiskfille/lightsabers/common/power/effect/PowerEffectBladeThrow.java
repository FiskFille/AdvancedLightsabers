package fiskfille.lightsabers.common.power.effect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectBladeThrow extends PowerEffect
{
	@Override
	public boolean execute(EntityPlayer player, Side side, Object... args)
	{
		ItemStack itemstack = player.getHeldItem();
		
		if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase)
		{
			if (ItemLightsaberBase.isActive(itemstack))
			{
				ItemLightsaberBase.throwLightsaber(player, itemstack, (Integer)args[0]);
				player.swingItem();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String[] getDesc(Object... args)
	{
		return new String[][]
		{
			{},
			{PowerDesc.create("multiply", PowerDesc.IMPACT_RADIUS, PowerDesc.format("%s%s", EnumChatFormatting.GRAY, 2))},
		}
		[(Integer)args[0]];
	}

	@Override
	public String getCastSound(int type)
	{
		return ALSounds.player_lightsaber_swing;
	}

	@Override
	public float getCastSoundPitch(int type)
	{
		return 1.0F;
	}
}
