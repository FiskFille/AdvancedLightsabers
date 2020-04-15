package com.fiskmods.lightsabers.common.block;

import java.util.List;
import java.util.Random;

import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberStand;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLightsaberStand extends BlockContainer
{
    private final Random rand = new Random();

    public BlockLightsaberStand()
    {
        super(Material.iron);
        setHardness(1.5F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 0);
        setStepSound(soundTypeMetal);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntity tileentity = world.getTileEntity(x, y, z);

            if (tileentity instanceof TileEntityLightsaberStand)
            {
                TileEntityLightsaberStand tile = (TileEntityLightsaberStand) tileentity;

                if (player.capabilities.isCreativeMode || tile.isOwner(player))
                {
                    ItemStack stack = tile.getDisplayStack();

                    if (tile.setDisplayStack(player.getHeldItem()))
                    {
                        player.setCurrentItemOrArmor(0, stack);
                    }
                }
                else
                {
                    player.addChatMessage(new ChatComponentTranslation("message.lightsaberStand.notOwner").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                }
            }
        }

        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntityLightsaberStand)
        {
            ItemStack itemstack = ((TileEntityLightsaberStand) tile).getDisplayStack();

            if (itemstack != null)
            {
                float f = rand.nextFloat() * 0.8F + 0.1F;
                float f1 = rand.nextFloat() * 0.8F + 0.1F;
                float f2 = rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0)
                {
                    int j1 = rand.nextInt(21) + 10;

                    if (j1 > itemstack.stackSize)
                    {
                        j1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j1;
                    EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float) rand.nextGaussian() * f3;
                    entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) rand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, metadata);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        return dir != ForgeDirection.DOWN && world.isSideSolid(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir, true) && super.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        ForgeDirection dir = getDirection(world.getBlockMetadata(x, y, z));
        return world.isSideSolid(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir, true);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (side == 1)
        {
            side = 0;
        }

        return side;
    }

    public static ForgeDirection getDirection(int metadata)
    {
        if (metadata == 0)
        {
            metadata = 1;
        }

        return ForgeDirection.getOrientation(metadata);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        updatePlacement(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        updatePlacement(world, x, y, z);
    }

    protected void updatePlacement(World world, int x, int y, int z)
    {
        if (!canBlockStay(world, x, y, z) && world.getBlock(x, y, z) == this)
        {
            dropBlockAsItem(world, x, y, z, 1, 0);
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        float w = 2.75F / 16;
        float l = 5.25F / 16;
        float h = 13F / 16;

        if (metadata == 2)
        {
            setBlockBounds(w, l, h, 1 - w, 1 - l, 1);
        }
        else if (metadata == 3)
        {
            setBlockBounds(w, l, 0, 1 - w, 1 - l, 1 - h);
        }
        else if (metadata == 4)
        {
            setBlockBounds(h, l, w, 1, 1 - l, 1 - w);
        }
        else if (metadata == 5)
        {
            setBlockBounds(0, l, w, 1 - h, 1 - l, 1 - w);
        }
        else if ((metadata & 1) == 0)
        {
            setBlockBounds(w, 0, l, 1 - w, 1 - h, 1 - l);
        }
        else
        {
            setBlockBounds(l, 0, w, 1 - l, 1 - h, 1 - w);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
        TileEntity tile = world.getTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata == 0)
        {
            int i = MathHelper.floor_double(entity.rotationYaw * 4F / 360F + 0.5) & 1;
            world.setBlockMetadataWithNotify(x, y, z, i, 2);
        }

        if (tile instanceof TileEntityLightsaberStand)
        {
            ((TileEntityLightsaberStand) tile).setOwner(entity);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("iron_block");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityLightsaberStand();
    }
}
