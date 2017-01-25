package fiskfille.lightsabers.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class ContainerLightsaberForge extends ContainerBasic
{
    public InventoryLightsaberForge craftMatrix = new InventoryLightsaberForge(this);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;

    public ContainerLightsaberForge(InventoryPlayer inventoryPlayer, World world, int x, int y, int z)
    {
        worldObj = world;
        posX = x;
        posY = y;
        posZ = z;
        addSlotToContainer(new SlotLightsaberPart(EnumPartType.EMITTER, craftMatrix, 0, 20, 17));
        addSlotToContainer(new SlotLightsaberPart(EnumPartType.SWITCH_SECTION, craftMatrix, 1, 20, 35));
        addSlotToContainer(new SlotLightsaberPart(EnumPartType.BODY, craftMatrix, 2, 20, 53));
        addSlotToContainer(new SlotLightsaberPart(EnumPartType.POMMEL, craftMatrix, 3, 20, 71));
        addSlotToContainer(new Slot(craftMatrix, 4, 43, 71));

        addSlotToContainer(new SlotFiltered(craftMatrix, 5, 66, 71, Item.getItemFromBlock(ModBlocks.lightsaberCrystal))
        {
            @Override
            @SideOnly(Side.CLIENT)
            public IIcon getBackgroundIconIndex()
            {
                return ItemLightsaberBase.crystalEmptySlotIcon;
            }
        });

        addSlotToContainer(new SlotFiltered(craftMatrix, 6, 89, 71, ModItems.focusingCrystal)
        {
            @Override
            @SideOnly(Side.CLIENT)
            public IIcon getBackgroundIconIndex()
            {
                return ItemLightsaberBase.focusingCrystalEmptySlotIcon;
            }
        });

        addSlotToContainer(new SlotFiltered(craftMatrix, 7, 107, 71, ModItems.focusingCrystal)
        {
            @Override
            @SideOnly(Side.CLIENT)
            public IIcon getBackgroundIconIndex()
            {
                return ItemLightsaberBase.focusingCrystalEmptySlotIcon;
            }
        });

        addSlotToContainer(new SlotLightsaberForge(inventoryPlayer.player, craftMatrix, craftResult, 0, 136, 87));

        addPlayerInventory(inventoryPlayer, 38);
        onCraftMatrixChanged(craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory)
    {
        craftResult.setInventorySlotContents(0, LightsaberHelper.getLightsaberForgeResult(craftMatrix, worldObj));
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!worldObj.isRemote)
        {
            for (int i = 0; i < craftMatrix.getSizeInventory(); ++i)
            {
                ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    player.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return (worldObj.getBlock(posX, posY, posZ) == ModBlocks.lightsaberForgeLight || worldObj.getBlock(posX, posY, posZ) == ModBlocks.lightsaberForgeDark) && player.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotId);
        int EMITTER = 0;
        int SWITCH_SECTION = 1;
        int BODY = 2;
        int POMMEL = 3;
        int LIGHTSABER_BASE = 4;
        int CRYSTAL = 5;
        int FOCUSING_CRYSTAL_1 = 6;
        int FOCUSING_CRYSTAL_2 = 7;
        int OUTPUT = 8;

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotId == OUTPUT)
            {
                if (!mergeItemStack(itemstack1, OUTPUT + 1, OUTPUT + 36 + 1, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotId > OUTPUT)
            {
                if (itemstack1.getItem() == ModItems.lightsaberEmitter)
                {
                    if (!mergeItemStack(itemstack1, EMITTER, EMITTER + 1, false))
                    {
                        return null;
                    }
                }
                else if (itemstack1.getItem() == ModItems.lightsaberSwitchSection)
                {
                    if (!mergeItemStack(itemstack1, SWITCH_SECTION, SWITCH_SECTION + 1, false))
                    {
                        return null;
                    }
                }
                else if (itemstack1.getItem() == ModItems.lightsaberBody)
                {
                    if (!mergeItemStack(itemstack1, BODY, BODY + 1, false))
                    {
                        return null;
                    }
                }
                else if (itemstack1.getItem() == ModItems.lightsaberPommel)
                {
                    if (!mergeItemStack(itemstack1, POMMEL, POMMEL + 1, false))
                    {
                        return null;
                    }
                }
                else if (itemstack1.getItem() == ModItems.lightsaberCircuitry)
                {
                    if (!mergeItemStack(itemstack1, LIGHTSABER_BASE, LIGHTSABER_BASE + 1, false))
                    {
                        return null;
                    }
                }
                else if (itemstack1.getItem() == Item.getItemFromBlock(ModBlocks.lightsaberCrystal))
                {
                    if (!mergeItemStack(itemstack1, CRYSTAL, CRYSTAL + 1, false))
                    {
                        return null;
                    }
                }
                else if (itemstack1.getItem() == ModItems.focusingCrystal)
                {
                    if (!mergeItemStack(itemstack1, FOCUSING_CRYSTAL_1, FOCUSING_CRYSTAL_1 + 1, false))
                    {
                        if (!mergeItemStack(itemstack1, FOCUSING_CRYSTAL_2, FOCUSING_CRYSTAL_2 + 1, false))
                        {
                            return null;
                        }
                    }
                }
                else if (slotId >= OUTPUT + 1 && slotId < OUTPUT + 28)
                {
                    if (!mergeItemStack(itemstack1, OUTPUT + 28, OUTPUT + 37, false))
                    {
                        return null;
                    }
                }
                else if (slotId >= OUTPUT + 28 && slotId < OUTPUT + 37 && !mergeItemStack(itemstack1, OUTPUT + 1, OUTPUT + 28, false))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, OUTPUT + 1, OUTPUT + 37, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean func_94530_a(ItemStack itemstack, Slot slot)
    {
        return slot.inventory != craftResult && super.func_94530_a(itemstack, slot);
    }
}
