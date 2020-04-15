package com.fiskmods.lightsabers.common.block;

import com.fiskmods.lightsabers.Lightsabers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockPillar extends BlockRotatedPillar
{
    public BlockPillar()
    {
        super(Material.rock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int i)
    {
        return blockIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
        field_150164_N = par1IIconRegister.registerIcon(Lightsabers.MODID + ":" + getUnlocalizedName().substring(5) + "_top");
        blockIcon = par1IIconRegister.registerIcon(Lightsabers.MODID + ":" + getUnlocalizedName().substring(5));
    }
}
