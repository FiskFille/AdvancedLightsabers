package fiskfille.utils.common.interaction;

import java.util.List;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import net.minecraft.entity.player.EntityPlayer;

public abstract class InteractionBase extends Interaction
{
    protected final ImmutableList<InteractionType> reqTypes;

    public InteractionBase(InteractionType... types)
    {
        reqTypes = ImmutableList.<InteractionType> builder().add(types).build();
    }

    public InteractionBase()
    {
        this(InteractionType.RIGHT_CLICK_AIR);
    }

    @Override
    public boolean listen(EntityPlayer sender, EntityPlayer clientPlayer, InteractionType type, Side side, int x, int y, int z, List<Integer> args)
    {
        if (!reqTypes.contains(type))
        {
            return false;
        }

        if (side.isClient() && sender == clientPlayer)
        {
            if (!clientRequirements(sender, type, x, y, z))
            {
                return false;
            }
        }

        return serverRequirements(sender, type, x, y, z);
    }
    
    public abstract boolean serverRequirements(EntityPlayer player, InteractionType type, int x, int y, int z);

    public abstract boolean clientRequirements(EntityPlayer player, InteractionType type, int x, int y, int z);
}
