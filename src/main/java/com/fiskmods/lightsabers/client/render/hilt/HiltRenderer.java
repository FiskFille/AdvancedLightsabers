package com.fiskmods.lightsabers.client.render.hilt;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.lightsaber.PartType;

import fiskfille.utils.registry.FiskRegistryEntry;
import fiskfille.utils.registry.FiskSimpleRegistry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

public abstract class HiltRenderer extends FiskRegistryEntry<HiltRenderer>
{
    public static final FiskSimpleRegistry<HiltRenderer> REGISTRY = new FiskSimpleRegistry(Lightsabers.MODID, "graflex");
    
    public static void register(String key, HiltRenderer value)
    {
        REGISTRY.putObject(key, value);
    }
    
    public static void register(Hilt key, HiltRenderer value)
    {
        register(key.delegate.name(), value);
    }
    
    public static HiltRenderer get(String key)
    {
        return REGISTRY.getObject(key);
    }
    
    public static HiltRenderer get(Hilt key)
    {
        return key == null ? null : get(key.delegate.name());
    }
    
    public abstract ModelBase getEmitter();
    
    public abstract ModelBase getSwitchSection();
    
    public abstract ModelBase getBody();
    
    public abstract ModelBase getPommel();
    
    public ModelBase getModel(PartType type)
    {
        switch (type)
        {
        case EMITTER:
            return getEmitter();
        case SWITCH_SECTION:
            return getSwitchSection();
        case BODY:
            return getBody();
        default:
            return getPommel();
        }
    }
    
    public ResourceLocation getTexture(PartType type)
    {
        return new ResourceLocation(getDomain(), String.format("textures/models/lightsaber/%s_%s.png", type.textureName, getRegistryName().getResourcePath()));
    }
    
    public final Hilt getHilt()
    {
        return Hilt.REGISTRY.getObject(delegate.name());
    }
}
