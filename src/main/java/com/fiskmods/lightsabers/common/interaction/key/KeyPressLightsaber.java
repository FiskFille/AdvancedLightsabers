package com.fiskmods.lightsabers.common.interaction.key;

import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;
import com.fiskmods.lightsabers.common.keybinds.ALKeyBinds;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import fiskfille.utils.common.interaction.key.KeyPressBase;
import fiskfille.utils.common.keybinds.FiskKeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public class KeyPressLightsaber extends KeyPressBase
{
    @Override
    public boolean serverRequirements(EntityPlayer player, InteractionType type, int x, int y, int z)
    {
        return player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemLightsaberBase;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public FiskKeyBinding getKey(EntityPlayer player)
    {
        return ALKeyBinds.ACTIVATE_LIGHTSABER;
    }

    @Override
    public void receive(EntityPlayer sender, EntityPlayer clientPlayer, InteractionType type, Side side, int x, int y, int z, Integer[] args)
    {
        if (side.isServer())
        {
            ItemLightsaberBase.ignite(sender, !ItemLightsaberBase.isActive(sender.getHeldItem()));
        }
    }
    
    @Override
    public boolean syncWithServer()
    {
        return true;
    }
    
    @Override
    public TargetPoint getTargetPoint(EntityPlayer player, int x, int y, int z)
    {
        return TARGET_NONE;
    }
}
