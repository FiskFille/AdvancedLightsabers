package com.fiskmods.lightsabers.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockModStairs extends BlockStairs
{
    public BlockModStairs(Block p_i45428_1_, int p_i45428_2_)
    {
        super(p_i45428_1_, p_i45428_2_);
        useNeighborBrightness = true;
    }
}
