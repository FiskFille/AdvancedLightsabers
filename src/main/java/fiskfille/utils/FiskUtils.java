package fiskfille.utils;

import java.util.Map;

import com.fiskmods.lightsabers.Lightsabers;
import com.google.common.collect.Maps;

import fiskfille.utils.common.interaction.InteractionHandler;
import fiskfille.utils.common.keybinds.FiskKeyHandler;
import fiskfille.utils.common.network.FiskNetworkManager;
import fiskfille.utils.helper.FiskServerUtils;

public class FiskUtils
{
    public static final String MODID = "fiskutils";
    
    private static final Map<Hook, String> HOOK_OWNERS = Maps.newHashMap();
    
    public static void hook(Hook hook)
    {
        if (HOOK_OWNERS.containsKey(hook))
        {
            Log.debug("%s hook ignored, already handled by %s", hook, HOOK_OWNERS.get(hook));
            return;
        }
        else
        {
            Log.debug("Adding hook %s", hook);
            HOOK_OWNERS.put(hook, FiskServerUtils.getActiveModId());
        }
        
        switch (hook)
        {
        case PREINIT:
        {
            if (Lightsabers.proxy.getSide().isClient())
            {
                FiskKeyHandler.register();
            }
            
            FiskNetworkManager.register();
            InteractionHandler.register();
        }
        default:
            break;
        }
    }
    
    public static enum Hook
    {
        PREINIT,
        INIT,
        POSTINIT
    }
}
