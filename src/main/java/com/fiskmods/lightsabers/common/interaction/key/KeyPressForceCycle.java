package com.fiskmods.lightsabers.common.interaction.key;

import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerManager;
import com.fiskmods.lightsabers.common.keybinds.ALKeyBinds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import fiskfille.utils.common.interaction.key.KeyPressBase;
import fiskfille.utils.common.keybinds.FiskKeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public class KeyPressForceCycle extends KeyPressBase
{
    public KeyPressForceCycle()
    {
        super(InteractionType.KEY_PRESS);
    }
    
    @Override
    public boolean serverRequirements(EntityPlayer player, InteractionType type, int x, int y, int z)
    {
        return PowerManager.hasPowerUnlocked(player, Power.FORCE_SENSITIVITY);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public FiskKeyBinding getKey(EntityPlayer player)
    {
        return ALKeyBinds.SELECT_POWER;
    }
    
    @Override
    public void receive(EntityPlayer sender, EntityPlayer clientPlayer, InteractionType type, Side side, int x, int y, int z, Integer[] args)
    {
        if (side.isClient())
        {
            if (ALData.SELECTED_POWER.get(sender) < 2)
            {
                ALData.SELECTED_POWER.incr(sender, (byte) 1);
            }
            else
            {
                ALData.SELECTED_POWER.set(sender, (byte) 0);
            }
        }
    }
}
