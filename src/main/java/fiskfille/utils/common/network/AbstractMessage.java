package fiskfille.utils.common.network;

import com.fiskmods.lightsabers.Lightsabers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public abstract class AbstractMessage<REQ extends AbstractMessage> implements IMessage, IMessageHandler<REQ, IMessage>
{
    protected MessageContext context;

    @Override
    public final IMessage onMessage(REQ message, MessageContext ctx)
    {
        message.context = ctx;

        try
        {
            message.receive();
        }
        catch (MessageException e)
        {
        }

        return null;
    }

    public abstract void receive() throws MessageException;

    public EntityPlayer getPlayer() throws MessageException
    {
        EntityPlayer player = context.side.isClient() ? Lightsabers.proxy.getPlayer() : context.getServerHandler().playerEntity;

        if (player != null)
        {
            return player;
        }

        throw new EntityCastException();
    }

    public <T extends Entity> T getEntity(World world, Class<? extends T> type, int id) throws MessageException
    {
        Entity entity = world.getEntityByID(id);

        if (entity != null && type.isInstance(entity))
        {
            return (T) entity;
        }

        throw new EntityCastException();
    }

    public <T extends Entity> T getEntity(Class<? extends T> type, int id) throws MessageException
    {
        return getEntity(getWorld(), type, id);
    }

    public EntityPlayer getPlayer(World world, int id) throws MessageException
    {
        return getEntity(world, EntityPlayer.class, id);
    }

    public EntityPlayer getPlayer(int id) throws MessageException
    {
        return getPlayer(getWorld(), id);
    }

    public EntityPlayer getSender(World world, int id) throws MessageException
    {
        return context.side.isServer() ? getPlayer() : getPlayer(world, id);
    }

    public EntityPlayer getSender(int id) throws MessageException
    {
        return getSender(getWorld(), id);
    }

    public World getWorld() throws MessageException
    {
        return getPlayer().worldObj;
    }

    public World getWorld(int dimension) throws MessageException
    {
        if (dimension == getWorld().provider.dimensionId)
        {
            return getWorld();
        }
        else if (context.side.isServer())
        {
            return MinecraftServer.getServer().worldServerForDimension(dimension);
        }

        throw new InvalidSideException();
    }

    protected static class MessageException extends Exception
    {
    }

    protected static class EntityCastException extends MessageException
    {
    }

    protected static class InvalidSideException extends MessageException
    {
    }
}
