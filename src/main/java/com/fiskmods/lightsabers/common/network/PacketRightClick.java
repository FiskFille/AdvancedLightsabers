package com.fiskmods.lightsabers.common.network;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALPlayerData;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerStats;
import com.fiskmods.lightsabers.common.force.PowerType;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

public class PacketRightClick implements IMessage
{
    public int id;
    private Power power;

    public PacketRightClick()
    {

    }

    public PacketRightClick(EntityPlayer player, Power p)
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

    public static void onMessage(EntityPlayer player, Power power, Side side)
    {
        ALPlayerData data = ALPlayerData.getData(player);
        PowerStats stats = power.powerStats;
        float force = ALData.FORCE_POWER.get(player);

        if (force >= power.getUseCost(player))
        {
            if (power.powerStats.powerType == PowerType.PER_USE)
            {
                if (power.powerEffect.execute(player, side))
                {
                    String sound = power.powerEffect.getCastSound(power.getSide());

                    if (!StringUtils.isNullOrEmpty(sound))
                    {
                        player.playSound(sound, power.powerEffect.getCastSoundVolume(power.getSide()), power.powerEffect.getCastSoundPitch(power.getSide()));
                    }

                    ALData.FORCE_POWER.incrWithoutNotify(player, -power.getUseCost(player));
                    ALData.USE_POWER_COOLDOWN.setWithoutNotify(player, 20);
                }
                else if (side.isClient())
                {
                    player.playSound(ALSounds.player_force_fail, 1.0F, 1.0F);
                }
            }
            else
            {
                if (power.powerEffect.execute(player, side))
                {
                    ALData.FORCE_POWER.incrWithoutNotify(player, -power.getUseCost(player) / 20);
                }
            }
        }
    }

    public static class Handler implements IMessageHandler<PacketRightClick, IMessage>
    {
        @Override
        public IMessage onMessage(PacketRightClick message, MessageContext ctx)
        {
            EntityPlayer clientPlayer = ctx.side.isClient() ? Lightsabers.proxy.getPlayer() : ctx.getServerHandler().playerEntity;

            if (clientPlayer != null)
            {
                if (clientPlayer.worldObj.getEntityByID(message.id) instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) clientPlayer.worldObj.getEntityByID(message.id);

                    if (ctx.side.isServer() || player != clientPlayer)
                    {
                        PacketRightClick.onMessage(player, message.power, ctx.side);

                        if (ctx.side.isServer())
                        {
                            ALNetworkManager.wrapper.sendToDimension(new PacketRightClick(player, message.power), clientPlayer.dimension);
                        }
                    }
                }
            }

            return null;
        }
    }
}
