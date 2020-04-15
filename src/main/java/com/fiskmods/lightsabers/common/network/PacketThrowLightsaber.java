package com.fiskmods.lightsabers.common.network;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.ItemLightsaberBase;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PacketThrowLightsaber implements IMessage
{
    public int id;
    private ItemStack lightsaber;

    public PacketThrowLightsaber()
    {

    }

    public PacketThrowLightsaber(EntityLivingBase entity, ItemStack itemstack)
    {
        id = entity.getEntityId();
        lightsaber = itemstack;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        lightsaber = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        ByteBufUtils.writeItemStack(buf, lightsaber);
    }

    public static class Handler implements IMessageHandler<PacketThrowLightsaber, IMessage>
    {
        @Override
        public IMessage onMessage(PacketThrowLightsaber message, MessageContext ctx)
        {
            ItemStack lightsaber = message.lightsaber;

            if (ctx.side.isClient())
            {
                EntityPlayer player = Lightsabers.proxy.getPlayer();
                Entity entity = player.worldObj.getEntityByID(message.id);

                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entity1 = (EntityLivingBase) entity;
                    ItemLightsaberBase.throwLightsaber(entity1, lightsaber, 1);
                }
            }
            else
            {
                EntityPlayer player = ctx.getServerHandler().playerEntity;

                if (player != null)
                {
                    Entity entity = player.worldObj.getEntityByID(message.id);

                    if (entity instanceof EntityLivingBase)
                    {
                        EntityLivingBase entity1 = (EntityLivingBase) entity;

                        if (player.worldObj.isRemote)
                        {
                            ALNetworkManager.wrapper.sendToServer(new PacketThrowLightsaber(entity1, lightsaber));
                        }
                        else
                        {
                            ALNetworkManager.wrapper.sendToDimension(new PacketThrowLightsaber(entity1, lightsaber), player.dimension);
                        }

                        ItemLightsaberBase.throwLightsaber(entity1, lightsaber, 1);
                    }
                }
            }

            return null;
        }
    }
}
