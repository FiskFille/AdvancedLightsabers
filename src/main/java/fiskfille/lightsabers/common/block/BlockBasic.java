package fiskfille.lightsabers.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import fiskfille.lightsabers.Lightsabers;

public class BlockBasic extends Block
{
    public BlockBasic(Material material)
    {
        super(material);
    }
    
    public Block setHarvestLvl(String tool, int level)
    {
    	this.setHarvestLevel(tool, level);
    	return this;
    }
    
    @Override
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
    	blockIcon = par1IIconRegister.registerIcon(Lightsabers.modid + ":" + getUnlocalizedName().substring(5));
    }
}
