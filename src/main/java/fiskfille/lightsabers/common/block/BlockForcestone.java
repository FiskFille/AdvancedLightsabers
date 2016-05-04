package fiskfille.lightsabers.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockForcestone extends Block
{
    public static final String[] nameStrings = new String[] {"default", "inscribed", "pillar"};
    @SideOnly(Side.CLIENT)
    private IIcon iconDefault;
    @SideOnly(Side.CLIENT)
    private IIcon iconInscribed;
    @SideOnly(Side.CLIENT)
    private IIcon iconPillarTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconPillarSide;

    public BlockForcestone()
    {
        super(Material.rock);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata != 2 && metadata != 3 && metadata != 4)
        {
        	if (metadata == 0)
        	{
        		return iconDefault;
        	}
        	else
        	{
        		return side == 0 || side == 1 ? iconDefault : iconInscribed;
        	}
        }
        else
        {
            return metadata == 2 && (side == 1 || side == 0) ? iconPillarTop : (metadata == 3 && (side == 5 || side == 4) ? iconPillarTop : (metadata == 4 && (side == 2 || side == 3) ? iconPillarTop : iconPillarSide));
        }
    }

    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (metadata == 2)
        {
            switch (side)
            {
                case 0:
                case 1:
                    metadata = 2;
                    break;
                case 2:
                case 3:
                    metadata = 4;
                    break;
                case 4:
                case 5:
                    metadata = 3;
            }
        }

        return metadata;
    }

    public int damageDropped(int metadata)
    {
        return metadata != 3 && metadata != 4 ? metadata : 2;
    }

    protected ItemStack createStackedBlock(int metadata)
    {
        return metadata != 3 && metadata != 4 ? super.createStackedBlock(metadata) : new ItemStack(Item.getItemFromBlock(this), 1, 2);
    }

    public int getRenderType()
    {
        return 39;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
        iconDefault = par1IIconRegister.registerIcon(getTextureName());
        iconInscribed = par1IIconRegister.registerIcon(getTextureName() + "_" + "inscribed");
        iconPillarTop = par1IIconRegister.registerIcon(getTextureName() + "_" + "pillar_top");
        iconPillarSide = par1IIconRegister.registerIcon(getTextureName() + "_" + "pillar");
    }
}
