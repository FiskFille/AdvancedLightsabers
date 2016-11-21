package fiskfille.lightsabers.common.network;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.tileentity.TileEntityHolocron;
import fiskfille.lightsabers.common.tileentity.TileEntitySithCoffin;
import fiskfille.lightsabers.common.tileentity.TileEntitySithStoneCoffin;
import io.netty.buffer.ByteBuf;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTileAction implements IMessage
{
    private int id;
    private int x;
    private int y;
    private int z;
    private int action;

    public PacketTileAction()
    {

    }

    public PacketTileAction(Entity entity, int x, int y, int z, int action)
    {
        this.id = entity != null ? entity.getEntityId() : 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.action = action;
    }

    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        action = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(action);
    }

    public static class Handler implements IMessageHandler<PacketTileAction, IMessage>
    {
        public IMessage onMessage(PacketTileAction message, MessageContext ctx)
        {
            EntityPlayer clientPlayer = ctx.side.isClient() ? Lightsabers.proxy.getPlayer() : ctx.getServerHandler().playerEntity;
            EntityPlayer player = null;
            World world = clientPlayer.worldObj;
            int action = message.action;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            
            if (world.getTileEntity(x, y, z) instanceof TileEntitySithCoffin)
            {
            	TileEntitySithCoffin tile = (TileEntitySithCoffin)world.getTileEntity(x, y, z);

            	if (tile != null)
            	{
            		int metadata = tile.getBlockMetadata();

            		if (world.getEntityByID(message.id) instanceof EntityPlayer)
            		{
            			player = (EntityPlayer)world.getEntityByID(message.id);

            			if (action == 0)
            			{
            				if (tile.lidOpenTimer == 0)
            				{
            					tile.isLidOpen = true;
            					player.playSound(ALSounds.block_sith_sarcophagus_open, 1.0F, 1.0F);
            				}
            				else if (tile.lidOpenTimer == tile.lidOpenTimerMax)
            				{
            					tile.isLidOpen = false;
            					player.playSound(ALSounds.block_sith_sarcophagus_close, 1.0F, 1.0F);
            				}
            			}
            		}

            		if (ctx.side.isServer())
            		{
            			ALNetworkManager.networkWrapper.sendToAll(new PacketTileAction(player, tile.xCoord, tile.yCoord, tile.zCoord, action));
            		}
            	}
            }
            else if (world.getTileEntity(x, y, z) instanceof TileEntitySithStoneCoffin)
            {
            	TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin)world.getTileEntity(x, y, z);

            	if (tile != null)
            	{
            		int metadata = tile.getBlockMetadata();

            		if (action == 0 || action == 1)
        			{
        				tile.baseplateOnly = action > 0;
        				
        				if (tile.baseplateOnly)
        				{
        					world.setBlockToAir(x, y + 1, z);
        				}
        				else
        				{
        					world.setBlock(x, y + 1, z, ModBlocks.sithStoneCoffin, tile.getBlockMetadata() + 4, 2);
        				}
        				
        				Entity entity = world.getEntityByID(message.id);
        				
        				if (entity != null)
        				{
        					Random rand = new Random();
        					
        					for (int i = 0; i < 128; ++i)
        					{
        						double d0 = (double)(rand.nextFloat() * 2 - 1) * 1.2F;
        						double d1 = (double)(rand.nextFloat() * 2.4F - 1);
        						double d2 = (double)(rand.nextFloat() * 2 - 1) * 1.2F;
        						double d3 = entity.posX + d0 * entity.width;
        						double d4 = entity.boundingBox.minY + d1 * (double)entity.height;
        						double d5 = entity.posZ + d2 * entity.width;
        						world.spawnParticle("largesmoke", d3, d4, d5, 0, 0, 0);
        					}
        				}
        			}

            		if (ctx.side.isServer())
            		{
            			ALNetworkManager.networkWrapper.sendToAll(new PacketTileAction(player, tile.xCoord, tile.yCoord, tile.zCoord, action));
            		}
            	}
            }
            else if (world.getTileEntity(x, y, z) instanceof TileEntityHolocron)
            {
            	TileEntityHolocron tile = (TileEntityHolocron)world.getTileEntity(x, y, z);

            	if (tile != null)
            	{
            		int metadata = tile.getBlockMetadata();

            		if (world.getEntityByID(message.id) instanceof EntityPlayer)
            		{
            			player = (EntityPlayer)world.getEntityByID(message.id);

            			if (action == 0)
            			{
            				++tile.playersUsing;
            			}
            			else if (action == 1)
            			{
            				--tile.playersUsing;
            			}
            		}

            		if (ctx.side.isServer())
            		{
            			ALNetworkManager.networkWrapper.sendToAll(new PacketTileAction(player, tile.xCoord, tile.yCoord, tile.zCoord, action));
            		}
            	}
            }

            return null;
        }
    }
}
