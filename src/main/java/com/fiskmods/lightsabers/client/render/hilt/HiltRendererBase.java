package com.fiskmods.lightsabers.client.render.hilt;

import net.minecraft.client.model.ModelBase;

public class HiltRendererBase extends HiltRenderer
{
    public final ModelBase emitter;
    public final ModelBase switchSection;
    public final ModelBase body;
    public final ModelBase pommel;

    public HiltRendererBase(ModelBase emitter, ModelBase switchSection, ModelBase body, ModelBase pommel)
    {
        this.emitter = emitter;
        this.switchSection = switchSection;
        this.body = body;
        this.pommel = pommel;
    }

    @Override
    public ModelBase getEmitter()
    {
        return emitter;
    }

    @Override
    public ModelBase getSwitchSection()
    {
        return switchSection;
    }

    @Override
    public ModelBase getBody()
    {
        return body;
    }

    @Override
    public ModelBase getPommel()
    {
        return pommel;
    }
}
