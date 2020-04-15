package com.fiskmods.lightsabers.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class TileEntityHolocron extends TileEntity
{
    public int playersUsing;
    public float openTimer;
    public float prevOpenTimer;
    public int openTicks;
    public int prevOpenTicks;

    @Override
    public void updateEntity()
    {
        prevOpenTimer = openTimer;
        prevOpenTicks = openTicks;

        if (playersUsing <= 0)
        {
            openTimer *= 0.85F;
        }
        else if (openTimer < 1)
        {
            openTimer += 0.05F;
            openTimer *= 1.05F;
        }

        if (openTimer < 1E-6)
        {
            openTimer = 0;
        }

        if (openTimer == 0)
        {
            openTicks = 0;
        }
        else if (openTimer >= 1)
        {
            ++openTicks;
        }

        openTimer = MathHelper.clamp_float(openTimer, 0, 1);
        playersUsing = Math.max(playersUsing, 0);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
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
