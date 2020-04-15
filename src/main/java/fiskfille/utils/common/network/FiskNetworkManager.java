package fiskfille.utils.common.network;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import fiskfille.utils.helper.FiskServerUtils;

public class FiskNetworkManager extends FiskNetworkHelper
{
    public static SimpleNetworkWrapper wrapper;

    public static void register()
    {
        wrapper = getWrapper(FiskServerUtils.getActiveModId());

        registerMessage(MessageInteraction.class);
        registerMessage(MessageTileTrigger.class);
    }
}
