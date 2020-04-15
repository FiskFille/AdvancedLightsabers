package com.fiskmods.lightsabers;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.utils.reflection.GenericField;
import net.minecraft.nbt.NBTTagList;

public class ALReflection
{
    public static GenericField<NBTTagList, List> tagList;

    @SideOnly(Side.CLIENT)
    public static void client()
    {
    }

    public static void common()
    {
        tagList = new GenericField(NBTTagList.class, List.class, "field_74747_a", "tagList");
    }
}
