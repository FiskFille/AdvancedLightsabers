package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.block.BlockForcestone;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemForcestone extends ItemMultiTexture
{
    public ItemForcestone(Block block)
    {
        super(block, block, BlockForcestone.NAMES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int metadata)
    {
//        return super.getIconFromDamage(metadata > 2 ? metadata + 3 : metadata);
        return super.getIconFromDamage(metadata);
    }

    @Override
    public int getMetadata(int metadata)
    {
//        return metadata > 2 ? metadata + 2 : metadata;
        return metadata;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        int i = itemstack.getItemDamage();
        
        if (i > 2)
        {
            i -= 2;
        }

        if (i < 0 || i >= field_150942_c.length)
        {
            i = 0;
        }

        return super.getUnlocalizedName() + "." + field_150942_c[i];
    }
}
