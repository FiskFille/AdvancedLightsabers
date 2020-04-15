package com.fiskmods.lightsabers.common.tileentity;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrystal extends TileEntity
{
    private CrystalColor crystalColor = CrystalColor.get(0);

    public void setColor(CrystalColor color)
    {
        if (crystalColor != color)
        {
            crystalColor = color;
            markDirty();
        }
    }

    public CrystalColor getColor()
    {
        return crystalColor;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        crystalColor = CrystalColor.get(nbt.getInteger("color"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("color", crystalColor.id);
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
