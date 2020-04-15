package fiskfille.utils.common.network;

import java.util.Map;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.helper.FiskServerUtils;

public class FiskNetworkHelper
{
    private static Map<String, SimpleNetworkWrapper> wrappers = Maps.newHashMap();
    private static Map<String, Integer> ids = Maps.newHashMap();

    public static <REQ extends IMessage & IMessageHandler<REQ, IMessage>> void registerMessage(Class<REQ> msg)
    {
        registerMessage(msg, Side.CLIENT);
        registerMessage(msg, Side.SERVER);
    }

    public static <REQ extends IMessage & IMessageHandler<REQ, IMessage>> void registerMessage(Class<REQ> msg, Side side)
    {
        String modid = FiskServerUtils.getActiveModId();
        getWrapper(modid).registerMessage(msg, msg, getNextId(modid), side);
    }

    public static SimpleNetworkWrapper getWrapper(String modid)
    {
        SimpleNetworkWrapper wrapper = wrappers.get(modid);

        if (wrapper == null)
        {
            wrappers.put(modid, wrapper = new SimpleNetworkWrapper(modid));
//            wrappers.put(modid, wrapper = new SimpleNetworkWrapper(modid)
//            {
//                @Override
//                public void sendTo(IMessage message, EntityPlayerMP player)
//                {
//                    log("sendTo", message);
//                    super.sendTo(message, player);
//                }
//
//                @Override
//                public void sendToAll(IMessage message)
//                {
//                    log("sendToAll", message);
//                    super.sendToAll(message);
//                }
//
//                @Override
//                public void sendToAllAround(IMessage message, TargetPoint point)
//                {
//                    log("sendToAllAround", message);
//                    super.sendToAllAround(message, point);
//                }
//
//                @Override
//                public void sendToDimension(IMessage message, int dimensionId)
//                {
//                    log("sendToDimension", message);
//                    super.sendToDimension(message, dimensionId);
//                }
//
//                @Override
//                public void sendToServer(IMessage message)
//                {
//                    log("sendToServer", message);
//                    super.sendToServer(message);
//                }
//
//                public void log(String s, IMessage message)
//                {
//                    System.out.println("---------- " + s + ": " + message.getClass().getSimpleName());
//                }
//            });
        }

        return wrapper;
    }

    protected static int getNextId(String modid)
    {
        Integer id = ids.get(modid) != null ? ids.get(modid) : 0;
        ids.put(modid, id + 1);

        return id;
    }
}
