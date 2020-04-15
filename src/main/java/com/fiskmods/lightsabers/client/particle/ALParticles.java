package com.fiskmods.lightsabers.client.particle;

import java.lang.reflect.Constructor;
import java.util.List;

import com.fiskmods.lightsabers.Lightsabers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ALParticles
{
    private static final ResourceLocation texture = new ResourceLocation(Lightsabers.MODID, "textures/particle/particles.png");
    private static Minecraft mc = Minecraft.getMinecraft();

    public static final int fxLayersSize = 16;

    public static EntityFX spawnParticle(ALParticleType particleType, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null && mc.theWorld != null)
        {
            if (mc.theWorld.isRemote)
            {
                int particleSetting = mc.gameSettings.particleSetting;

                if (particleSetting == 1 && mc.theWorld.rand.nextInt(3) == 0)
                {
                    particleSetting = 2;
                }

                double diffX = mc.renderViewEntity.posX - x;
                double diffY = mc.renderViewEntity.posY - y;
                double diffZ = mc.renderViewEntity.posZ - z;

                EntityFX particle = null;
                double maxRenderDistance = 16.0D;
                boolean flag = false;

                if (diffX * diffX + diffY * diffY + diffZ * diffZ > maxRenderDistance * maxRenderDistance && flag)
                {
                    return null;
                }
                else if (particleSetting > 1)
                {
                    return null;
                }
                else
                {
                    try
                    {
                        Constructor c = particleType.particleClass.getConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class);
                        particle = (EntityFX) c.newInstance(mc.theWorld, x, y, z, motionX, motionY, motionZ);

                        mc.effectRenderer.addEffect(particle);

                        return particle;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    return null;
                }
            }
        }

        return null;
    }

    public static int getParticlesInWorld(List[] list)
    {
        int i = 0;

        for (int j = 0; j < list.length - 1; ++j)
        {
            i += list[j].size();
        }

        return i;
    }

    public static void bindParticleTextures(int layer)
    {
        if (layer == 9)
        {
            mc.getTextureManager().bindTexture(texture);
        }
    }
}
