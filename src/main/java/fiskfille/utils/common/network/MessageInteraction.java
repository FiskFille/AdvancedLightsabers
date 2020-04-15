package fiskfille.utils.common.network;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.common.interaction.Interaction;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageInteraction extends AbstractMessage<MessageInteraction>
{
    private int id, x, y, z;
    private Interaction interaction;
    private InteractionType type;
    
    public MessageInteraction()
    {
    }
    
    public MessageInteraction(EntityPlayer player, Interaction interaction, InteractionType type, int x, int y, int z)
    {
        this.id = player.getEntityId();
        this.interaction = interaction;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        interaction = Interaction.REGISTRY.getObjectById(buf.readByte());
        type = InteractionType.values()[Math.abs(buf.readByte()) % InteractionType.values().length];
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeByte(Interaction.REGISTRY.getIDForObject(interaction));
        buf.writeByte(type.ordinal());
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void receive() throws MessageException
    {
        EntityPlayer sender = getSender(id);
        EntityPlayer clientPlayer = getPlayer();
        
        if (context.side.isClient())
        {
            if (sender != clientPlayer)
            {
                interaction.listen(sender, clientPlayer, type, Side.CLIENT, x, y, z);
            }
        }
        else if (interaction.listen(sender, clientPlayer, type, Side.SERVER, x, y, z))
        {
            TargetPoint target = interaction.getTargetPoint(sender, x, y, z);
            
            if (target == null)
            {
                FiskNetworkManager.wrapper.sendToDimension(this, sender.dimension);
            }
            else if (target.range > 0)
            {
                FiskNetworkManager.wrapper.sendToAllAround(this, target);
            }
        }
    }
}
