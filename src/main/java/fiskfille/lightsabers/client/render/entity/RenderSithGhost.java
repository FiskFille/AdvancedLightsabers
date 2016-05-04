package fiskfille.lightsabers.client.render.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.model.ModelSithGhost;
import fiskfille.lightsabers.common.helper.LightsaberColors;

@SideOnly(Side.CLIENT)
public class RenderSithGhost extends RenderBiped
{
    private static final ResourceLocation textures = new ResourceLocation(Lightsabers.modid, "textures/models/sith_ghost.png");

    public RenderSithGhost()
    {
        super(new ModelSithGhost(), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return textures;
    }
}
