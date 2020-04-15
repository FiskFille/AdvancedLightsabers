package com.fiskmods.ui;

import java.awt.Component;
import java.util.function.Predicate;

public class ComponentWrapper
{
    private final UIWindow window;
    private final Component component;
    
    public ComponentWrapper(UIWindow ui, Component c)
    {
        window = ui;
        component = c;
    }
    
    public ComponentWrapper map(String id)
    {
        window.components.put(id, component);
        return this;
    }
    
    public ComponentWrapper setEnabled(Predicate<Component> p)
    {
        window.enabled.put(component, window.enabled.containsKey(component) ? p.and(window.enabled.get(component)) : p);
        return this;
    }
    
    public UIWindow window()
    {
        return window;
    }
    
    public Component get()
    {
        return component;
    }
}
