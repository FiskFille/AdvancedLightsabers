package fiskfille.lightsabers.asm;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.Entity;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.client.sound.MovingSoundHum;

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
				MovingSoundHum sound = (MovingSoundHum)tickableSound;
				humSounds.add(sound.theEntity.getUniqueID().toString());
			}
		}
	}
	
	public static boolean hasHumSound(Entity entity)
	{
		return humSounds.contains(entity.getUniqueID().toString());
	}
}
