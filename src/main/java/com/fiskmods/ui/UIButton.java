package com.fiskmods.ui;

import javax.swing.JButton;

public class UIButton extends JButton
{
    public UIButton(String text, int x, int y, int width, int height)
    {
        super(text);
        setBounds(x, y, width, height);
    }
}
