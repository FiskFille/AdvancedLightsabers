package com.fiskmods.lightsabers.client.render.hilt;

import com.fiskmods.lightsabers.common.lightsaber.PartType;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

public class HiltRendererOneTwelve extends HiltRendererBase
{
    public HiltRendererOneTwelve(ModelBase emitter, ModelBase switchSection, ModelBase body, ModelBase pommel)
    {
        super(emitter, switchSection, body, pommel);
    }
    
    @Override
    public ResourceLocation getTexture(PartType type)
    {
        return new ResourceLocation(getDomain(), String.format("textures/models/lightsaber/%s.png", getRegistryName().getResourcePath()));
    }
}
