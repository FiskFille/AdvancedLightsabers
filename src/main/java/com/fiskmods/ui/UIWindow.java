package com.fiskmods.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UIWindow extends JFrame
{
    public final Map<String, Component> components = new HashMap<>();
    public final Map<Component, Predicate<Component>> enabled = new HashMap<>();
    public final JPanel panel = new JPanel();
    
    private Thread updateThread;

    public UIWindow(String title, int width, int height)
    {
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(width, height));
        updateThread = new Thread(() ->
        {
            try
            {

                while (true)
                {
                    update();
                    updateUI();
                }
            }
            catch (ThreadDeath e)
            {
            }
        });
        
        setTitle(title);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setLocation(getX() - width / 2, getY() - height / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void update()
    {
    }
    
    public void updateUI()
    {
        enabled.forEach((key, value) ->
        {
            key.setEnabled(value.test(key));
        });
    }

    public ComponentWrapper add(Component component, int x, int y, int width, int height)
    {
        add(component);
        component.setBounds(x, y, width, height);
        return new ComponentWrapper(this, component);
    }

    public ComponentWrapper add(AbstractButton button, int x, int y, int width, int height, ActionListener listener)
    {
        button.addActionListener(listener);
        return add(button, x, y, width, height);
    }
    
    public ComponentWrapper add(String text, int x, int y, int width, int height)
    {
        return add(new JLabel(text), x, y, width, height);
    }

    public void open()
    {
        pack();
        setVisible(true);
        setResizable(false);
        updateThread.start();
    }

    public void close()
    {
        dispose();
        updateThread.stop();
    }
}
