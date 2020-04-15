package com.fiskmods.lightsabers.common.interaction.key;

import com.fiskmods.lightsabers.client.gui.GuiSelectPowers;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerManager;
import com.fiskmods.lightsabers.common.keybinds.ALKeyBinds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import fiskfille.utils.common.interaction.key.KeyPressBase;
import fiskfille.utils.common.keybinds.FiskKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class KeyPressForceSelect extends KeyPressBase
{
    public KeyPressForceSelect()
    {
        super(InteractionType.KEY_HOLD);
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
            receive(sender);
        }
    }
    
    @SideOnly(Side.CLIENT)
    private void receive(EntityPlayer sender)
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiSelectPowers());
        
        if (ALData.SELECTED_POWER.get(sender) > 0)
        {
            ALData.SELECTED_POWER.incr(sender, (byte) -1);
        }
        else
        {
            ALData.SELECTED_POWER.set(sender, (byte) 2);
        }
    }
}
