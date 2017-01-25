package fiskfille.lightsabers.common.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;

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
        field_150164_N = par1IIconRegister.registerIcon(Lightsabers.modid + ":" + getUnlocalizedName().substring(5) + "_top");
        blockIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":" + getUnlocalizedName().substring(5));
    }
}
