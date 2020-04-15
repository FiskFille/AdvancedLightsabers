package com.fiskmods.lightsabers.common.network;

import com.fiskmods.lightsabers.Lightsabers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.common.network.FiskNetworkHelper;
import fiskfille.utils.common.network.MessageTileTrigger;

public class ALNetworkManager extends FiskNetworkHelper
{
    public static SimpleNetworkWrapper wrapper;

    public static void register()
    {
        wrapper = getWrapper(Lightsabers.MODID);

        registerMessage(MessageBroadcastState.class);
        registerMessage(MessagePlayerData.class);

        registerMessage(MessagePlayerJoin.class, Side.CLIENT);
        registerMessage(MessageUpdateEffects.class, Side.CLIENT);

        registerPacket(PacketTileAction.Handler.class, PacketTileAction.class);
        registerPacket(PacketThrowLightsaber.Handler.class, PacketThrowLightsaber.class);
        registerPacket(PacketUnlockPower.Handler.class, PacketUnlockPower.class);
        registerPacket(PacketRightClick.Handler.class, PacketRightClick.class);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType)
    {
        wrapper.registerMessage(messageHandler, requestMessageType, getNextId(Lightsabers.MODID), Side.CLIENT);
        wrapper.registerMessage(messageHandler, requestMessageType, getNextId(Lightsabers.MODID), Side.SERVER);
    }
}
