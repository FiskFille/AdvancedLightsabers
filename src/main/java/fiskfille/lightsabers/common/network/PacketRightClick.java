package fiskfille.lightsabers.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.power.EnumPowerType;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerStats;

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
        float force = ALData.getFloat(player, ALData.FORCE_POWER);

        if (force >= power.getUseCost(player))
        {
            if (power.powerStats.powerType == EnumPowerType.PER_USE)
            {
                if (power.powerEffect.execute(player, side, power.powerEffectArgs))
                {
                    int type = ALHelper.getPowerType(power);
                    String sound = power.powerEffect.getCastSound(type);

                    if (!StringUtils.isNullOrEmpty(sound))
                    {
                        player.playSound(sound, power.powerEffect.getCastSoundVolume(type), power.powerEffect.getCastSoundPitch(type));
                    }

                    ALData.incrWithoutNotify(player, ALData.FORCE_POWER, -power.getUseCost(player));
                    ALData.setWithoutNotify(player, ALData.USE_POWER_COOLDOWN, 20);
                }
                else if (side.isClient())
                {
                    player.playSound(ALSounds.player_force_fail, 1.0F, 1.0F);
                }
            }
            else
            {
                if (power.powerEffect.execute(player, side, power.powerEffectArgs))
                {
                    ALData.incrWithoutNotify(player, ALData.FORCE_POWER, -power.getUseCost(player) / 20);
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
                            ALNetworkManager.networkWrapper.sendToDimension(new PacketRightClick(player, message.power), clientPlayer.dimension);
                        }
                    }
                }
            }

            return null;
        }
    }
}
