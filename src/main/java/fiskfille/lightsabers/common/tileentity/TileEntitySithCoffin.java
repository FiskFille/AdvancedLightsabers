package fiskfille.lightsabers.common.tileentity;

import java.util.Random;

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
import fiskfille.lightsabers.common.block.BlockSithCoffin;

public class TileEntitySithCoffin extends TileEntity implements IInventory
{
    private ItemStack[] itemStacks = new ItemStack[28];
    public static final int lidOpenTimerMax = 60;
	public boolean isLidOpen = false;
	public int lidOpenTimer = 0;
	public int prevLidOpenTimer = 0;
	public boolean hasBeenOpened = false;

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
    	else
    	{
    		if (lidOpenTimer < lidOpenTimerMax)
    		{
    			++lidOpenTimer;
    		}
    	}
    	
    	int metadata = getBlockMetadata();
    	int dir = BlockSithCoffin.getDirection(metadata);
    	
    	if (!BlockSithCoffin.isBlockFrontOfCoffin(metadata))
    	{
    		if (!hasBeenOpened)
    		{
    			if (lidOpenTimer < lidOpenTimerMax)
    			{
    				double radius = 0.5D;

            		for (double y = 0; y <= (double)lidOpenTimer / 5; y += 0.025)
            		{
            			Random rand = new Random();
            			double d = Math.cos(y * 2D);
            			double x = radius * Math.cos(y + ((double)lidOpenTimer / 2)) * d;
            			double z = radius * Math.sin(y + ((double)lidOpenTimer / 2)) * d;
            			double motionX = (rand.nextFloat() - 0.5F) * 0.5F * d;
            			double motionY = (rand.nextFloat() - 0.5F) * 0.1F;
            			double motionZ = (rand.nextFloat() - 0.5F) * 0.5F * d;
            			worldObj.spawnParticle("smoke", xCoord + 0.5F + x + BlockSithCoffin.directions[dir][0] * 0.5F, yCoord + 0.8F + y, zCoord + 0.5F + z + BlockSithCoffin.directions[dir][1] * 0.5F, motionX, motionY, motionZ);
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
        return (float)(prevLidOpenTimer + f * partialTicks) / lidOpenTimerMax;
    }
    
    public AxisAlignedBB getRenderBoundingBox()
    {
    	int metadata = getBlockMetadata();
    	int direction = BlockSithCoffin.getDirection(metadata);
    	return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(1, 0, 1);
    }

    public int getSizeInventory()
    {
        return itemStacks.length;
    }

    public ItemStack getStackInSlot(int slot)
    {
        return itemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int amount)
    {
        if (itemStacks[slot] != null)
        {
            ItemStack itemstack;

            if (itemStacks[slot].stackSize <= amount)
            {
                itemstack = itemStacks[slot];
                itemStacks[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = itemStacks[slot].splitStack(amount);

                if (itemStacks[slot].stackSize == 0)
                {
                    itemStacks[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (itemStacks[slot] != null)
        {
            ItemStack itemstack = itemStacks[slot];
            itemStacks[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        itemStacks[slot] = itemstack;

        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public String getInventoryName()
    {
        return "gui.sith_coffin";
    }

    public boolean hasCustomInventoryName()
    {
        return false;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        itemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte slot = nbttagcompound1.getByte("Slot");

            if (slot >= 0 && slot < itemStacks.length)
            {
                itemStacks[slot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        hasBeenOpened = nbt.getBoolean("HasBeenOpened");
        isLidOpen = nbt.getBoolean("IsLidOpen");
        lidOpenTimer = nbt.getInteger("LidOpenTimer");
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < itemStacks.length; ++i)
        {
            if (itemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                itemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbt.setTag("Items", nbttaglist);
        nbt.setBoolean("HasBeenOpened", hasBeenOpened);
        nbt.setBoolean("IsLidOpen", isLidOpen);
        nbt.setInteger("LidOpenTimer", lidOpenTimer);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory() {}

    public void closeInventory() {}

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
