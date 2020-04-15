package com.fiskmods.lightsabers.common.entity;

import com.fiskmods.lightsabers.Lightsabers;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

public class ModEntities
{
    private static int nextID = -1;
    
    public static void register()
    {
        registerEntity(EntityLightsaber.class, "Lightsaber", 64, 10, true);
        registerEntityWithEgg(EntitySithGhost.class, "SithGhost", 80, 1, true, 0x212121, 0xE2CECE);
    }
    
    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates)
    {
        EntityRegistry.registerModEntity(entityClass, name, ++nextID, Lightsabers.instance, trackingRange, updateFrequency, sendVelocityUpdates);
    }

    private static void registerEntityWithEgg(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates, int primary, int secondary)
    {
        EntityRegistry.registerGlobalEntityID(entityClass, Lightsabers.MODID + "." + name, EntityRegistry.findGlobalUniqueEntityId(), primary, secondary);
        registerEntity(entityClass, name, trackingRange, updateFrequency, sendVelocityUpdates);
    }
}
