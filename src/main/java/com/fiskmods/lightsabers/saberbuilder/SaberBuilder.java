package com.fiskmods.lightsabers.saberbuilder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.hilt.Hilt;
import com.fiskmods.lightsabers.common.hilt.HiltManager;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.ui.UIWindow;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

public class SaberBuilder extends UIWindow
{
    private static final Map<String, Hilt> HILTS = new LinkedHashMap<>();
    private static final BiMap<Hilt, Integer> HILT_IDS = HashBiMap.create();

    private final int margin = 5;
    private final int width;
    private AbstractLightsaberData data = new LData(0);

    private final String[] partNames = {"Emitter", "Switch Section", "Grip", "Pommel"};
    private final JComboBox[] hiltParts = new JComboBox[partNames.length];
    private final JComboBox[] focusingCrystals = new JComboBox[2];

    private final JComboBox colorCrystal = new JComboBox(CrystalColor.values());
    private final JButton colorPreview = new JButton();

    private final JTextField hexField = new JTextField("#0");
    private final JTextField commandField = new JTextField();

    private SaberBuilder(String title, int width, int height)
    {
        super(title, width, height);
        this.width = width;
        {
            Vector v = new Vector<>(HILTS.keySet());

            for (int i = 0; i < partNames.length; ++i)
            {
                JComboBox box = hiltParts[i] = new JComboBox<>(v);
                add(i % 2 + 1, 2, partNames[i], box, i / 2);

                int j = i;
                box.addActionListener(e ->
                {
                    data.set(PartType.values()[j], HILTS.get(box.getSelectedItem()));
                    updateScreen();
                });
            }
        }
        {
            List list = Lists.newArrayList(FocusingCrystal.values());
            list.add(0, "-");
            Vector v = new Vector<>(list);
            
            add(1, 2, "Focusing Crystals", focusingCrystals[0] = new JComboBox<>(v), 2);
            add(2, 2, "", focusingCrystals[1] = new JComboBox<>(v), 2);
            
            for (int i = 0; i < 2; ++i)
            {
                focusingCrystals[i].addActionListener(e ->
                {
                    data.set(getFocusingCrystal(0), getFocusingCrystal(1));
                    updateScreen();
                });
            }
        }
        {
            colorPreview.setFocusable(false);
            colorPreview.setBorderPainted(true);
            colorCrystal.addActionListener(e ->
            {
                data.set(getColor());
                updateScreen();
            });
            add(1, 2, "Color", colorCrystal, 3);
            add(2, 2, "", colorPreview, 3);
        }
        {
            Font f = hexField.getFont();
            hexField.setFont(new Font(f.getName(), Font.BOLD, f.getSize() * 2));
            hexField.addActionListener(e -> loadHex());
            hexField.addFocusListener(new FocusListener()
            {
                @Override
                public void focusLost(FocusEvent e)
                {
                    updateScreen();
                }

                @Override
                public void focusGained(FocusEvent e)
                {
                    String s = hexField.getText();

                    if (s.startsWith("#"))
                    {
                        hexField.setText(s.substring(1));
                    }
                }
            });

            add(hexField, margin, height - 40 - margin, (width - margin) / 2, 40);
        }
        {
            Font f = commandField.getFont();
            commandField.setEditable(false);
            commandField.setFont(new Font(f.getName(), Font.BOLD, f.getSize()));
            add(1, 1, "Command", commandField, 4);
        }

        updateScreen();
    }

    private void loadHex()
    {
        String s = hexField.getText();

        if (s.isEmpty())
        {
            s = "0";
        }
        else if (!s.startsWith("#"))
        {
            s = "#" + s;
        }

        setHash(Long.decode(s));
        updateScreen();
    }

    private void setHash(long hash)
    {
        data = new LData(hash).strip();
        colorCrystal.setSelectedItem(data.getColor());

        for (int i = 0; i < partNames.length; ++i)
        {
            int id = HILT_IDS.getOrDefault(data.get(PartType.values()[i]), 0);

            if (id >= HILTS.size())
            {
                id = 0;
            }

            hiltParts[i].setSelectedIndex(id);
        }
        
        FocusingCrystal[] crystals = data.getFocusingCrystals();
        focusingCrystals[0].setSelectedIndex(0);
        focusingCrystals[1].setSelectedIndex(0);

        for (int i = 0; i < crystals.length; ++i)
        {
            focusingCrystals[i].setSelectedIndex(crystals[i].ordinal() + 1);
        }
    }

    private void updateScreen()
    {
        String s = Long.toHexString(data.hash).toUpperCase(Locale.ROOT);
        
        if (!hexField.hasFocus())
        {
            s = "#" + s;
        }

        hexField.setText(s);
        colorPreview.setBackground(new Color(getColor().color));
        
        if (!s.startsWith("#"))
        {
            s = "#" + s;
        }
        
        commandField.setText(String.format("/give @p %s:lightsaber 1 0 {%s:\"%s\"}", Lightsabers.MODID, ALConstants.TAG_LIGHTSABER, s));
    }
    
    private FocusingCrystal getFocusingCrystal(int slot)
    {
        Object obj = focusingCrystals[slot].getSelectedItem();
        
        if (obj instanceof FocusingCrystal)
        {
            return (FocusingCrystal) obj;
        }
        
        return null;
    }
    
    private CrystalColor getColor()
    {
        return (CrystalColor) colorCrystal.getSelectedItem();
    }

    private void add(int index, int stack, String text, JComponent comp, int row)
    {
        int w = width - margin * (stack - 1);
        add(text, comp, margin * index + w / stack * (index - 1), margin + row * 45, w / stack);
    }

    private void add(String text, JComponent comp, int x, int y, int width)
    {
        if (!text.isEmpty())
        {
            add(text, x, y, width - 5, 20);
        }

        add(comp, x, y + 20, width, 20);
    }

    public static void main(String[] args)
    {
        try
        {
            int nextId = -1;

            for (Field field : HiltManager.class.getFields())
            {
                if (Hilt.class.isAssignableFrom(field.getType()))
                {
                    Hilt hilt = (Hilt) field.get(null);
                    HILTS.put(field.getName().toLowerCase(Locale.ROOT), hilt);
                    HILT_IDS.put(hilt, ++nextId);
                }
            }

            new SaberBuilder("Lightsaber Builder", 400, 300).open();
        }
        catch (Exception e)
        {
            int width = 400;
            int height = 200;
            UIWindow window = new UIWindow("Lightsaber Builder", width, height);
            StringWriter w = new StringWriter();
            JTextArea log = new JTextArea();

            e.printStackTrace(new PrintWriter(w));
            log.setText(w.getBuffer().toString());
            log.setForeground(Color.RED);
            log.setEditable(false);

            window.add("A critical error occurred:", 5, 5, width - 10, 20);
            window.add(new JButton("Close"), width / 4, height - 25, width / 2, 20, event -> System.exit(-1));
            window.add(log, 5, 30, width - 10, height - 65);
            window.open();
        }
    }

    private static class LData extends AbstractLightsaberData
    {
        private LData(long hashCode)
        {
            super(hashCode);
        }

        @Override
        protected int getIDForObject(Hilt hilt)
        {
            return HILT_IDS.getOrDefault(hilt, 0);
        }

        @Override
        protected Hilt getObjectById(int id)
        {
            return HILT_IDS.inverse().getOrDefault(id, HILT_IDS.inverse().get(0));
        }

        @Override
        protected AbstractLightsaberData createNew(long hashCode)
        {
            return new LData(hashCode);
        }
    }
}
