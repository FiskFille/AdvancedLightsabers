package com.fiskmods.lightsabers.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockForcestone extends Block
{
    public static final String[] NAMES = new String[] {"default", "inscribed", "pillar", "cracked", "mossy"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockForcestone()
    {
        super(Material.rock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata != 2 && metadata != 3 && metadata != 4)
        {
            if (metadata == 1)
            {
                return side == 0 || side == 1 ? icons[0] : icons[1];
            }
            else if (metadata > 4)
            {
                return icons[Math.min(metadata - 1, icons.length - 1)];
            }
            
            return icons[0];
        }
        else
        {
            return metadata == 2 && (side == 1 || side == 0) ? icons[2] : metadata == 3 && (side == 5 || side == 4) ? icons[2] : metadata == 4 && (side == 2 || side == 3) ? icons[2] : icons[3];
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (metadata == 2)
        {
            switch (side)
            {
            case 0:
            case 1:
                return 2;
            case 2:
            case 3:
                return 4;
            case 4:
            case 5:
                return 3;
            }
        }
//        else if (metadata > 2)
//        {
//            return metadata + 2;
//        }

        return metadata;
    }

    @Override
    public int damageDropped(int metadata)
    {
//        return metadata != 3 && metadata != 4 ? metadata > 4 ? metadata - 2 : metadata : 2;
        return metadata != 3 && metadata != 4 ? metadata : 2;
    }

    @Override
    protected ItemStack createStackedBlock(int metadata)
    {
        return super.createStackedBlock(metadata == 3 || metadata == 4 ? 2 : metadata);
    }

    @Override
    public int getRenderType()
    {
        return 39;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
//        for (int i = 0; i < NAMES.length; ++i)
//        {
//            list.add(new ItemStack(item, 1, i));
//        }
        
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        list.add(new ItemStack(item, 1, 5));
        list.add(new ItemStack(item, 1, 6));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[6];
        icons[0] = iconRegister.registerIcon(getTextureName());
        icons[1] = iconRegister.registerIcon(getTextureName() + "_" + "inscribed");
        icons[2] = iconRegister.registerIcon(getTextureName() + "_" + "pillar_top");
        icons[3] = iconRegister.registerIcon(getTextureName() + "_" + "pillar");
        icons[4] = iconRegister.registerIcon(getTextureName() + "_" + "cracked");
        icons[5] = iconRegister.registerIcon(getTextureName() + "_" + "mossy");
    }
}
