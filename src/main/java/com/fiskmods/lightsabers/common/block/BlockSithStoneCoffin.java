package com.fiskmods.lightsabers.common.block;

import java.util.List;
import java.util.Random;

import com.fiskmods.lightsabers.common.entity.EntitySithGhost;
import com.fiskmods.lightsabers.common.network.ALNetworkManager;
import com.fiskmods.lightsabers.common.network.PacketTileAction;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

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
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSithStoneCoffin extends BlockContainer
{
    private final Random rand = new Random();

    public BlockSithStoneCoffin()
    {
        super(Material.rock);
        setHardness(50.0F);
        setResistance(2000.0F);
    }

    public static EntitySithGhost spawnSithGhost(World world, int x, int y, int z)
    {
        EntitySithGhost entity = new EntitySithGhost(world);
        entity.setLocationAndAngles(x + 0.5F, y + 0.0625F * 3, z + 0.5F, world.getBlockMetadata(x, y, z) * 90, 0);
        entity.onSpawnWithEgg(null);
        entity.ticksExisted = -entity.getRNG().nextInt(20);
        entity.hasRestingPlace = true;
        entity.restingPlaceX = x;
        entity.restingPlaceY = y;
        entity.restingPlaceZ = z;

        if (world.getTileEntity(x, y, z) instanceof TileEntitySithStoneCoffin)
        {
            TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(x, y, z);

            if (tile.equipment != null)
            {
                if (tile.equipment.hasTagCompound())
                {
                    tile.equipment.getTagCompound().setBoolean("active", false);
                }

                entity.setCurrentItemOrArmor(0, tile.equipment);
            }

            tile.baseplateOnly = true;
        }

        world.spawnEntityInWorld(entity);
        return entity;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            activate(world, x, y, z);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata >= 4)
        {
            if (world.getBlock(x, y - 1, z) != ModBlocks.sithStoneCoffin)
            {
                world.setBlockToAir(x, y, z);
                breakBlock(world, x, y, z, block, metadata);
            }
        }
        else
        {
            TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(x, y, z);

            if ((tile == null || !tile.baseplateOnly) && world.getBlock(x, y + 1, z) != ModBlocks.sithStoneCoffin)
            {
                world.setBlockToAir(x, y, z);
                breakBlock(world, x, y, z, block, metadata);
            }
        }

        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            activate(world, x, y, z);
        }
    }

    public void activate(World world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) >= 4)
        {
            --y;
        }

        TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(x, y, z);

        if (tile != null)
        {
            if (!tile.baseplateOnly)
            {
                if (!world.isRemote)
                {
                    spawnSithGhost(world, x, y, z);
                    ALNetworkManager.wrapper.sendToServer(new PacketTileAction(null, x, y, z, 1));
                }
            }
        }
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode && !world.isRemote)
        {
            if (world.getBlockMetadata(x, y, z) >= 4)
            {
                --y;
            }

            TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(x, y, z);

            if (tile != null)
            {
                if (!tile.baseplateOnly && tile.taskFinished)
                {
                    world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(world.getBlock(x, y, z)) + (world.getBlockMetadata(x, y, z) << 12));
                    world.setBlockToAir(x, y, z);

                    ItemStack itemstack = new ItemStack(this);
                    itemstack.setTagCompound(new NBTTagCompound());

                    if (tile.equipment != null)
                    {
                        NBTTagCompound nbttagcompound = new NBTTagCompound();
                        tile.equipment.writeToNBT(nbttagcompound);
                        itemstack.getTagCompound().setTag("Equipment", nbttagcompound);
                    }

                    float f = rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = rand.nextFloat() * 0.8F + 0.1F;
                    float f3 = 0.05F;
                    EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, itemstack);
                    entityitem.motionX = (float) rand.nextGaussian() * f3;
                    entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) rand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);

                    breakBlock(world, x, y, z, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
                }
            }
        }

        super.onBlockClicked(world, x, y, z, player);
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
        return false;
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
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;

        if (metadata < 4)
        {
            addBox(0, 0, 0, 1, 3 * f, 1, world, x, y, z, aabb, list, entity);

            TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(x, y, z);

            if (tile != null && !tile.baseplateOnly)
            {
                if (metadata == 0)
                {
                    addBox(1.5F * f, 3 * f, 1.5F * f, 14.5F * f, 31 * f, 10.5F * f, world, x, y, z, aabb, list, entity);
                }
                else if (metadata == 1)
                {
                    addBox(5.5F * f, 3 * f, 1.5F * f, 14.5F * f, 31 * f, 14.5F * f, world, x, y, z, aabb, list, entity);
                }
                else if (metadata == 2)
                {
                    addBox(1.5F * f, 3 * f, 5.5F * f, 14.5F * f, 31 * f, 14.5F * f, world, x, y, z, aabb, list, entity);
                }
                else if (metadata == 3)
                {
                    addBox(1.5F * f, 3 * f, 1.5F * f, 10.5F * f, 31 * f, 14.5F * f, world, x, y, z, aabb, list, entity);
                }
            }
        }

        setBlockBoundsBasedOnState(world, x, y, z);
    }

    public void addBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata >= 4)
        {
            --y;
        }

        TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(x, y, z);
        boolean flag = false;

        if (tile != null)
        {
            flag = tile.baseplateOnly;
        }

        if (metadata >= 4)
        {
            if (!flag)
            {
                setBlockBounds(0, -1, 0, 1, 1, 1);
            }
            else
            {
                setBlockBounds(0, 0, 0, 0, 0, 0);
            }
        }
        else if (!flag)
        {
            setBlockBounds(0, 0, 0, 1, 2, 1);
        }
        else
        {
            setBlockBounds(0, 0, 0, 1, 0.0625F * 3, 1);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return y < world.getHeight() - 1 && super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
        int rotation = MathHelper.floor_double(entity.rotationYaw * 4F / 360F + 2.5) & 3;

        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
        world.setBlock(x, y + 1, z, this, rotation + 4, 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntitySithStoneCoffin();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("hardened_clay_stained_black");
    }
}
