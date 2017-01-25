package fiskfille.lightsabers.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.ALPlayerData;

public abstract class PacketSyncBase implements IMessage
{
    public Object[] playerData;

    public PacketSyncBase()
    {

    }

    public PacketSyncBase(EntityPlayer player)
    {
        playerData = ALPlayerData.getData(player).data;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerData = new Object[buf.readInt()];

        for (int i = 0; i < playerData.length; ++i)
        {
            if (ALData.shouldSave[i])
            {
                playerData[i] = ALData.fromBytes(buf, i);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(playerData.length);

        for (int i = 0; i < playerData.length; ++i)
        {
            if (ALData.shouldSave[i])
            {
                ALData.toBytes(buf, i, playerData[i]);
            }
        }
    }
}
