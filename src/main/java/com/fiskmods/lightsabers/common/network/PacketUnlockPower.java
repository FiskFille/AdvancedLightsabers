package com.fiskmods.lightsabers.common.network;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerManager;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketUnlockPower implements IMessage
{
    public int id;
    private Power power;

    public PacketUnlockPower()
    {

    }

    public PacketUnlockPower(EntityPlayer player, Power p)
    {
        id = player.getEntityId();
        power = p;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        power = Power.getPowerFromName(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        ByteBufUtils.writeUTF8String(buf, power.getName());
    }

    public static class Handler implements IMessageHandler<PacketUnlockPower, IMessage>
    {
        @Override
        public IMessage onMessage(PacketUnlockPower message, MessageContext ctx)
        {
            Power power = message.power;

            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);

                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player1 = (EntityPlayer) entity;
                    onMessage(player1, message, ctx);
                }
            }
            else
            {
                EntityPlayer player = ctx.getServerHandler().playerEntity;

                if (player != null)
                {
                    Entity entity = player.worldObj.getEntityByID(message.id);

                    if (entity instanceof EntityPlayer)
                    {
                        EntityPlayer player1 = (EntityPlayer) entity;

                        if (player.worldObj.isRemote)
                        {
                            ALNetworkManager.wrapper.sendToServer(new PacketUnlockPower(player1, power));
                        }
                        else
                        {
                            ALNetworkManager.wrapper.sendToDimension(new PacketUnlockPower(player1, power), player.dimension);
                        }

                        onMessage(player1, message, ctx);
                    }
                }
            }

            return null;
        }

        public void onMessage(EntityPlayer player, PacketUnlockPower message, MessageContext ctx)
        {
            PowerManager.getPowerData(player, message.power).setUnlocked(player, true);

            if (message.power == Power.FORCE_SENSITIVITY)
            {
                PowerManager.getPowerData(player, Power.LIGHT_SIDE).setUnlocked(player, true);
                PowerManager.getPowerData(player, Power.DARK_SIDE).setUnlocked(player, true);
                PowerManager.getPowerData(player, Power.NEUTRAL).setUnlocked(player, true);
            }
        }
    }
}
