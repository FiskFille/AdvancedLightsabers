package com.fiskmods.lightsabers.client.render.entity;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.ModelSithGhost;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSithGhost extends RenderBiped
{
    private static final ResourceLocation textures = new ResourceLocation(Lightsabers.MODID, "textures/models/sith_ghost.png");

    public RenderSithGhost()
    {
        super(new ModelSithGhost(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return textures;
    }
}
