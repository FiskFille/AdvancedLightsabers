package fiskfille.lightsabers.common.entity;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;
import fiskfille.lightsabers.Lightsabers;

public class ModEntities
{
    private static int nextId = -1;
    
    public static void register()
    {
        registerEntity(EntityLightsaber.class, "Lightsaber", 64, 10, true);
        registerEntity(EntityDoubleLightsaber.class, "DoubleLightsaber", 64, 10, true);
        registerEntityWithEgg(EntitySithGhost.class, "SithGhost", 80, 3, true, 0x212121, 0xE2CECE);
        registerEntityWithEgg(EntityGrievous.class, "Grievous", 80, 3, true, 0xFADDB2, 0xDFBF93);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates)
    {
        name = "al_" + name;

//        int id = EntityRegistry.findGlobalUniqueEntityId();
//        EntityRegistry.registerGlobalEntityID(entityClass, name, id);
        EntityRegistry.registerModEntity(entityClass, name, ++nextId, Lightsabers.instance, trackingRange, updateFrequency, sendVelocityUpdates);
    }

    private static void registerEntityWithEgg(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates, int primary, int secondary)
    {
        name = "al_" + name;

        EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), primary, secondary);
        EntityRegistry.registerModEntity(entityClass, name, ++nextId, Lightsabers.instance, trackingRange, updateFrequency, sendVelocityUpdates);
    }
}
