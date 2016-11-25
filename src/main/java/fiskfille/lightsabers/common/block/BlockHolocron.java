package fiskfille.lightsabers.common.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketTileAction;
import fiskfille.lightsabers.common.tileentity.TileEntityHolocron;

public class BlockHolocron extends BlockContainer
{
	private String[][] iconNames = {{"jedi_holocron_side", "jedi_holocron_corner", "jedi_holocron_corner_bottom", "jedi_holocron_corner_side"}, {"sith_holocron_bottom", "sith_holocron_side"}};
	
	@SideOnly(Side.CLIENT)
	private IIcon[][] icons;
	
	protected BlockHolocron()
	{
		super(Material.circuits);
		setResistance(2000.0F);
		setLightLevel(0.5F);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ)
	{
		if (!player.isSneaking())
		{
			TileEntityHolocron tile = (TileEntityHolocron)world.getTileEntity(x, y, z);
			
			if (tile != null)
			{
				if (world.isRemote)
				{
					ALNetworkManager.networkWrapper.sendToServer(new PacketTileAction(player, tile.xCoord, tile.yCoord, tile.zCoord, 0));
				}
				
				player.openGui(Lightsabers.instance, 2, world, x, y, z);
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
		setBlockBoundsBasedOnState(world, x, y, z);
    	super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		float f = 0;
		
		if (world.getTileEntity(x, y, z) instanceof TileEntityHolocron)
		{
			TileEntityHolocron tile = (TileEntityHolocron)world.getTileEntity(x, y, z);
			f = tile.prevOpenTimer + (tile.openTimer - tile.prevOpenTimer) * Lightsabers.proxy.getPartialTicks();
		}
		
		float size = 0.25F;
		float offset = size * f;
		
		if (world.getBlockMetadata(x, y, z) == 0)
		{
			size -= 0.125F * f;
		}
		
    	setBlockBounds(size, offset, size, 1 - size, 1 - size * 2 + offset, 1 - size);
	}
	
	
	
	public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return -1;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subBlocks)
    {
		subBlocks.add(new ItemStack(item, 1, 0));
        subBlocks.add(new ItemStack(item, 1, 1));
    }
	
	@Override
	public int damageDropped(int metadata)
    {
        return metadata;
    }
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		IIcon[] icons1 = icons[MathHelper.clamp_int(meta, 0, icons.length - 1)];
		return icons1[MathHelper.clamp_int(side, 0, icons1.length - 1)];
	}
	
	public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityHolocron();
    }

	@Override
    public void registerBlockIcons(IIconRegister par1IIconRegister)
    {
		icons = new IIcon[iconNames.length][];
		
		for (int i = 0; i < iconNames.length; ++i)
		{
			icons[i] = new IIcon[iconNames[i].length];
			
			for (int j = 0; j < iconNames[i].length; ++j)
			{
				icons[i][j] = par1IIconRegister.registerIcon(Lightsabers.modid + ":" + iconNames[i][j]);
			}
		}
    }
}
