package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.Lightsabers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

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
        icons[0] = par1IIconRegister.registerIcon(Lightsabers.MODID + ":jedi_holocron");
        icons[1] = par1IIconRegister.registerIcon(Lightsabers.MODID + ":sith_holocron");
    }
}
