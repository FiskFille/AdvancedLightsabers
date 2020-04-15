package com.fiskmods.ui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class PairedCellRenderer implements ListCellRenderer<String>
{
    private final ListCellRenderer<? super String> renderer;
    
    public PairedCellRenderer(JList<String> list)
    {
        renderer = list.getCellRenderer();
    }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends String> parent, String e, int index, boolean selected, boolean hasFocus)
    {
        String[] astring = e.split(",");
        JPanel panel = new JPanel();
        JLabel label = new JLabel(astring[1]);
        Component c = renderer.getListCellRendererComponent(parent, astring[0], index, selected, hasFocus);
        
        c.setBounds(2, 2, 200, 18);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(0, 22));
        panel.add(c);
        label.setBounds(210, 2, 300, 18);
        panel.setBackground(c.getBackground());
        panel.add(label);
        
        return panel;
    }
}
