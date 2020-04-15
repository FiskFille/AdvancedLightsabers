package com.fiskmods.ui;

import java.util.Collection;
import java.util.Vector;

import javax.swing.JList;

public class DynamicJList<E> extends JList<E>
{
    private Vector<E> v = new Vector<>();
    private boolean hasChanged;
    
    public void add(E e)
    {
        v.add(e);
        hasChanged = true;
    }
    
    public void addAll(Collection<? extends E> e)
    {
        v.addAll(e);
        hasChanged = true;
    }
    
    public void clear()
    {
        v.clear();
        hasChanged = true;
    }
    
    public void sync()
    {
        if (hasChanged)
        {
            hasChanged = false;
            setListData(v);
            setSelectedIndex(Math.max(v.size() - 1, 0));
            updateUI();
        }
    }
}
