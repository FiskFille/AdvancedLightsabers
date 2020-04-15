package fiskfille.utils.common.interaction.key;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.utils.common.interaction.InteractionBase;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import fiskfille.utils.common.keybinds.FiskKeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public abstract class KeyPressBase extends InteractionBase
{
    public KeyPressBase(InteractionType... types)
    {
        super(types);
    }
    
    public KeyPressBase()
    {
        super(InteractionType.KEY_PRESS);
    }
    
    @Override
    public boolean syncWithServer()
    {
        return false;
    }
    
    @Override
    public boolean serverRequirements(EntityPlayer player, InteractionType type, int x, int y, int z)
    {
        return true;
    }
    
    @Override
    public boolean clientRequirements(EntityPlayer player, InteractionType type, int x, int y, int z)
    {
        return getKey(player).getIsKeyPressed();
    }
    
    @SideOnly(Side.CLIENT)
    public abstract FiskKeyBinding getKey(EntityPlayer player);
}
