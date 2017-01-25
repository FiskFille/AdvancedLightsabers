package fiskfille.lightsabers.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;

public class ItemHolocron extends ItemBlockWithMetadata
{
    private IIcon[] icons;

    public ItemHolocron(Block block)
    {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return "tile." + (itemstack.getItemDamage() == 0 ? "jedi" : "sith") + "_holocron";
    }

    @Override
    public IIcon getIconFromDamage(int damage)
    {
        return icons[MathHelper.clamp_int(damage, 0, 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber()
    {
        return 1;
    }

    @Override
    public void registerIcons(IIconRegister par1IIconRegister)
    {
        icons = new IIcon[2];
        icons[0] = par1IIconRegister.registerIcon(Lightsabers.modid + ":jedi_holocron");
        icons[1] = par1IIconRegister.registerIcon(Lightsabers.modid + ":sith_holocron");
    }
}
