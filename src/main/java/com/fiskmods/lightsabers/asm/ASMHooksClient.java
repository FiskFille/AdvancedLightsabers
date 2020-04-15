package com.fiskmods.lightsabers.asm;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.fiskmods.lightsabers.client.sound.MovingSoundHum;
import com.fiskmods.lightsabers.helper.ALRenderHelper;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.Entity;

public class ASMHooksClient
{
    private static Minecraft mc = Minecraft.getMinecraft();
    public static List<String> humSounds = Lists.newArrayList();

    public static void updateAllSounds(SoundManager soundManager, List<ITickableSound> list)
    {
        Iterator<ITickableSound> iterator = list.iterator();
        humSounds.clear();

        while (iterator.hasNext())
        {
            ITickableSound tickableSound = iterator.next();

            if (tickableSound instanceof MovingSoundHum)
            {
                MovingSoundHum sound = (MovingSoundHum) tickableSound;
                humSounds.add(sound.theEntity.getUniqueID().toString());
            }
        }
    }

    public static boolean hasHumSound(Entity entity)
    {
        return humSounds.contains(entity.getUniqueID().toString());
    }

    public static void glColor3b(byte r, byte g, byte b)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor3b(r, g, b);
        }
    }

    public static void glColor3d(double r, double g, double b)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor3d(r, g, b);
        }
    }

    public static void glColor3f(float r, float g, float b)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor3f(r, g, b);
        }
    }

    public static void glColor3ub(byte r, byte g, byte b)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor3ub(r, g, b);
        }
    }

    public static void glColor4b(byte r, byte g, byte b, byte a)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor4b(r, g, b, a);
        }
    }

    public static void glColor4d(double r, double g, double b, double a)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor4d(r, g, b, a);
        }
    }

    public static void glColor4f(float r, float g, float b, float a)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor4f(r, g, b, a);
        }
    }

    public static void glColor4ub(byte r, byte g, byte b, byte a)
    {
        if (!ALRenderHelper.overrideColor)
        {
            GL11.glColor4ub(r, g, b, a);
        }
    }
}
