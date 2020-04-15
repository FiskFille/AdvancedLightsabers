package fiskfille.utils.common.interaction;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.FiskUtils;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import fiskfille.utils.registry.FiskRegistryEntry;
import fiskfille.utils.registry.FiskRegistryNamespaced;
import net.minecraft.entity.player.EntityPlayer;

public abstract class Interaction extends FiskRegistryEntry<Interaction>
{
    public static final int MAX_ID = 255;
    public static final FiskRegistryNamespaced<Interaction> REGISTRY = new FiskRegistryNamespaced(FiskUtils.MODID, null).setMaxId(MAX_ID);

    public static void register(String key, Interaction value)
    {
        REGISTRY.putObject(key, value);
    }

    public static Interaction getFromName(String key)
    {
        return REGISTRY.getObject(key);
    }

    public static String getNameFor(Interaction value)
    {
        return REGISTRY.getNameForObject(value);
    }
    
    public static final TargetPoint TARGET_NONE = new TargetPoint(0, 0, 0, 0, 0);
    public static final TargetPoint TARGET_ALL = null;
    
    public abstract boolean listen(EntityPlayer sender, EntityPlayer clientPlayer, InteractionType type, Side side, int x, int y, int z, List<Integer> args);
    
    public abstract void receive(EntityPlayer sender, EntityPlayer clientPlayer, InteractionType type, Side side, int x, int y, int z, Integer[] args);
    
    public boolean listen(EntityPlayer sender, EntityPlayer clientPlayer, InteractionType type, Side side, int x, int y, int z)
    {
        List<Integer> args = new ArrayList();
        
        if (listen(sender, clientPlayer, type, side, x, y, z, args))
        {
            receive(sender, clientPlayer, type, side, x, y, z, args.toArray(new Integer[args.size()]));
            
            return true;
        }
        
        return false;
    }
    
    public boolean syncWithServer()
    {
        return true;
    }
    
    public TargetPoint getTargetPoint(EntityPlayer player, int x, int y, int z)
    {
        return new TargetPoint(player.dimension, x, y, z, 32);
    }
}
