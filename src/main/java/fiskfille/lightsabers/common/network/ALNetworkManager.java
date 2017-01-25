package fiskfille.lightsabers.common.network;

import net.ilexiconn.llibrary.server.network.AnimationMessage;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.Lightsabers;

public class ALNetworkManager
{
    public static SimpleNetworkWrapper networkWrapper;
    private static int packetId = 0;

    public static void registerPackets()
    {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Lightsabers.modid);

        registerPacket(PacketPlayerJoin.Handler.class, PacketPlayerJoin.class);
        registerPacket(PacketBroadcastState.Handler.class, PacketBroadcastState.class);
        registerPacket(PacketPlayerData.Handler.class, PacketPlayerData.class);
        registerPacket(PacketUpdateLightsaber.Handler.class, PacketUpdateLightsaber.class);
        registerPacket(PacketTileAction.Handler.class, PacketTileAction.class);
        registerPacket(PacketIgniteLightsaber.Handler.class, PacketIgniteLightsaber.class);
        registerPacket(PacketThrowLightsaber.Handler.class, PacketThrowLightsaber.class);
        registerPacket(PacketUnlockPower.Handler.class, PacketUnlockPower.class);
        registerPacket(PacketUpdatePowerList.Handler.class, PacketUpdatePowerList.class);
        registerPacket(PacketUpdateSelectedPowers.Handler.class, PacketUpdateSelectedPowers.class);
        registerPacket(PacketRightClick.Handler.class, PacketRightClick.class);
        registerPacket(PacketUpdateEffectsList.Handler.class, PacketUpdateEffectsList.class);
        registerPacket(AnimationMessage.class, AnimationMessage.class);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType)
    {
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId++, Side.SERVER);
    }
}
