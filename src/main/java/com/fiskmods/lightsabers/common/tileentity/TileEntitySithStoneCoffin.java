package com.fiskmods.lightsabers.common.tileentity;

import java.util.List;

import com.fiskmods.lightsabers.common.block.BlockSithStoneCoffin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySithStoneCoffin extends TileEntity
{
    public TileEntitySithCoffin mainCoffin;
    public ItemStack equipment;
    public boolean baseplateOnly = false;
    public boolean taskFinished = false;
    private int mainCoffinX;
    private int mainCoffinY;
    private int mainCoffinZ;

    @Override
    public void updateEntity()
    {
        if (mainCoffin != null)
        {
            int x = mainCoffin.xCoord;
            int y = mainCoffin.yCoord;
            int z = mainCoffin.zCoord;

            if (!baseplateOnly)
            {
                double radius = 14.0D;
                List<EntityPlayer> list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(radius, 2, radius));

                if (!list.isEmpty())
                {
                    baseplateOnly = true;
                    taskFinished = true;

                    for (int i = 0; i < 2; ++i)
                    {
                        worldObj.playAuxSFX(2001, xCoord, yCoord + i, zCoord, Block.getIdFromBlock(worldObj.getBlock(xCoord, yCoord + i, zCoord)) + (worldObj.getBlockMetadata(xCoord, yCoord + i, zCoord) << 12));
                    }

                    if (!worldObj.isRemote)
                    {
                        BlockSithStoneCoffin.spawnSithGhost(worldObj, xCoord, yCoord, zCoord).setAttackTarget(list.get(0));
                    }
                }
            }
        }
        else
        {
            TileEntity tile = worldObj.getTileEntity(mainCoffinX, mainCoffinY, mainCoffinZ);

            if (tile instanceof TileEntitySithCoffin)
            {
                mainCoffin = (TileEntitySithCoffin) tile;
            }
        }
    }

    public void setMainCoffin(TileEntitySithCoffin tile)
    {
        mainCoffin = tile;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if (mainCoffin != null)
        {
            nbt.setInteger("CoffinX", mainCoffin.xCoord);
            nbt.setInteger("CoffinY", mainCoffin.yCoord);
            nbt.setInteger("CoffinZ", mainCoffin.zCoord);
        }

        if (equipment != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            equipment.writeToNBT(nbttagcompound);
            nbt.setTag("Equipment", nbttagcompound);
        }

        nbt.setBoolean("BaseplateOnly", baseplateOnly);
        nbt.setBoolean("TaskFinished", taskFinished);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        mainCoffinX = nbt.getInteger("CoffinX");
        mainCoffinY = nbt.getInteger("CoffinY");
        mainCoffinZ = nbt.getInteger("CoffinZ");
        baseplateOnly = nbt.getBoolean("BaseplateOnly");
        taskFinished = nbt.getBoolean("TaskFinished");

        if (nbt.hasKey("Equipment"))
        {
            NBTTagCompound nbttagcompound = nbt.getCompoundTag("Equipment");
            equipment = ItemStack.loadItemStackFromNBT(nbttagcompound);
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        writeToNBT(syncData);

        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager netManager, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
    }
}
