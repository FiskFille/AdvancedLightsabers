package fiskfille.lightsabers.common.network;

import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.data.ALData;
import fiskfille.lightsabers.common.data.ALEntityData;
import fiskfille.lightsabers.common.data.ALPlayerData;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.StatusEffect;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.power.Power;
import fiskfille.lightsabers.common.power.PowerData;

public class PacketPlayerJoin extends PacketSyncBase
{
    private List<PowerData> powers;
    private List<Power> selectedPowers;
    private List<StatusEffect> activeEffects;

    public PacketPlayerJoin()
    {
    }

    public PacketPlayerJoin(EntityPlayer player)
    {
        super(player);
        powers = ALPlayerData.getData(player).powers;
        selectedPowers = ALPlayerData.getData(player).selectedPowers;
        activeEffects = ALEntityData.getData(player).activeEffects;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);
        powers = Lists.newArrayList();
        int i = buf.readInt();

        if (i > 0)
        {
            for (int j = 0; j < i; ++j)
            {
                PowerData data = new PowerData(Power.getPowerFromName(ByteBufUtils.readUTF8String(buf)));
                data.unlocked = buf.readBoolean();
                data.xpInvested = buf.readInt();
                powers.add(data);
            }
        }

        selectedPowers = Lists.newArrayList();
        i = buf.readInt();

        if (i > 0)
        {
            for (int j = 0; j < i; ++j)
            {
                selectedPowers.add(Power.getPowerFromName(ByteBufUtils.readUTF8String(buf)));
            }
        }

        activeEffects = Lists.newArrayList();
        i = buf.readInt();

        if (i > 0)
        {
            for (int j = 0; j < i; ++j)
            {
                StatusEffect effect = new StatusEffect(buf.readInt(), buf.readInt(), buf.readInt());

                if (buf.readBoolean())
                {
                    UUID uuid = new UUID(buf.readLong(), buf.readLong());
                    effect.casterUUID = uuid;
                }

                activeEffects.add(effect);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        super.toBytes(buf);
        buf.writeInt(powers.size());

        for (PowerData data : powers)
        {
            ByteBufUtils.writeUTF8String(buf, data.power.getName());
            buf.writeBoolean(data.unlocked);
            buf.writeInt(data.xpInvested);
        }

        buf.writeInt(selectedPowers.size());

        for (Power power : selectedPowers)
        {
            if (power != null)
            {
                ByteBufUtils.writeUTF8String(buf, power.getName());
            }
            else
            {
                ByteBufUtils.writeUTF8String(buf, "");
            }
        }

        buf.writeInt(activeEffects.size());

        for (StatusEffect effect : activeEffects)
        {
            buf.writeInt(effect.id);
            buf.writeInt(effect.duration);
            buf.writeInt(effect.amplifier);

            if (effect.casterUUID != null)
            {
                buf.writeBoolean(true);
                buf.writeLong(effect.casterUUID.getMostSignificantBits());
                buf.writeLong(effect.casterUUID.getLeastSignificantBits());
            }
            else
            {
                buf.writeBoolean(false);
            }
        }
    }

    public static class Handler implements IMessageHandler<PacketPlayerJoin, IMessage>
    {
        @Override
        public IMessage onMessage(PacketPlayerJoin message, MessageContext ctx)
        {
            Object[] playerData = message.playerData;

            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();

                for (int i = 0; i < playerData.length; ++i)
                {
                    if (ALData.shouldSave[i])
                    {
                        ALData.set(player, i, playerData[i]);
                    }
                }

                DataManager.setPowers(player, message.powers);
                DataManager.setSelectedPowers(player, message.selectedPowers);
                DataManager.setActiveEffects(player, message.activeEffects);

                ALNetworkManager.networkWrapper.sendToServer(new PacketUpdateLightsaber(player, LightsaberHelper.getEquippedLightsaber(player)));
            }

            return null;
        }
    }
}
