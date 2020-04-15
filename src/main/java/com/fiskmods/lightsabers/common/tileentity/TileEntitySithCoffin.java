package com.fiskmods.lightsabers.common.tileentity;

import java.util.Random;

import com.fiskmods.lightsabers.common.block.BlockSithCoffin;

import net.minecraft.block.BlockDirectional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySithCoffin extends TileEntity implements IInventory
{
    public static final int LID_OPEN_MAX = 60;
    
    private ItemStack[] itemstacks = new ItemStack[28];

    public boolean hasBeenOpened = false;
    public boolean isLidOpen = false;
    public int lidOpenTimer = 0;
    public int prevLidOpenTimer = 0;

    @Override
    public void updateEntity()
    {
        prevLidOpenTimer = lidOpenTimer;

        if (!isLidOpen)
        {
            if (lidOpenTimer > 0)
            {
                --lidOpenTimer;
            }
        }
        else if (lidOpenTimer < LID_OPEN_MAX)
        {
            ++lidOpenTimer;
        }

        int metadata = getBlockMetadata();
        int dir = BlockDirectional.getDirection(metadata);

        if (!BlockSithCoffin.isBlockFrontOfCoffin(metadata))
        {
            if (!hasBeenOpened)
            {
                if (lidOpenTimer < LID_OPEN_MAX)
                {
                    double radius = 0.5D;

                    for (double y = 0; y <= lidOpenTimer / 5F; y += 0.025)
                    {
                        Random rand = new Random();
                        double d = Math.cos(y * 2D);
                        double x = radius * Math.cos(y + lidOpenTimer / 2F) * d;
                        double z = radius * Math.sin(y + lidOpenTimer / 2F) * d;
                        double motionX = (rand.nextFloat() - 0.5F) * 0.5F * d;
                        double motionY = (rand.nextFloat() - 0.5F) * 0.1F;
                        double motionZ = (rand.nextFloat() - 0.5F) * 0.5F * d;
                        worldObj.spawnParticle("smoke", xCoord + 0.5F + x + BlockSithCoffin.DIRECTIONS[dir][0] * 0.5F, yCoord + 0.8F + y, zCoord + 0.5F + z + BlockSithCoffin.DIRECTIONS[dir][1] * 0.5F, motionX, motionY, motionZ);
                    }
                }
                else
                {
                    hasBeenOpened = true;
                }
            }
        }
    }

    public float getLidOpenTimer(float partialTicks)
    {
        float f = lidOpenTimer - prevLidOpenTimer;
        return (prevLidOpenTimer + f * partialTicks) / LID_OPEN_MAX;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(1, 0, 1);
    }

    @Override
    public int getSizeInventory()
    {
        return itemstacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return itemstacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (itemstacks[slot] != null)
        {
            ItemStack itemstack;

            if (itemstacks[slot].stackSize <= amount)
            {
                itemstack = itemstacks[slot];
                itemstacks[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = itemstacks[slot].splitStack(amount);

                if (itemstacks[slot].stackSize == 0)
                {
                    itemstacks[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (itemstacks[slot] != null)
        {
            ItemStack itemstack = itemstacks[slot];
            itemstacks[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        itemstacks[slot] = itemstack;

        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return "gui.sith_coffin";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        itemstacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte slot = nbttagcompound1.getByte("Slot");

            if (slot >= 0 && slot < itemstacks.length)
            {
                itemstacks[slot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        hasBeenOpened = nbt.getBoolean("HasBeenOpened");
        isLidOpen = nbt.getBoolean("IsLidOpen");
        lidOpenTimer = nbt.getInteger("LidOpenTimer");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < itemstacks.length; ++i)
        {
            if (itemstacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                itemstacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbt.setTag("Items", nbttaglist);
        nbt.setBoolean("HasBeenOpened", hasBeenOpened);
        nbt.setBoolean("IsLidOpen", isLidOpen);
        nbt.setInteger("LidOpenTimer", lidOpenTimer);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {
    }

    @Override
    public void closeInventory()
    {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return true;
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
