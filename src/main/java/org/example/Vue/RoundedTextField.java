package org.example.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RoundedTextField extends JTextField {
    private int arcWidth = 20;
    private int arcHeight = 20;
    private Color borderColor;
    private Color backgroundColor;
    private Color focusedBackgroundColor;
    private Color hoverBackgroundColor;

    public RoundedTextField(int columns) {
        super(columns);
        init();
    }

    private void init() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackgroundColor(new Color(255, 255, 255)); // White background color
        setHoverBackgroundColor(new Color(230, 230, 230)); // Light gray for hover
        setFocusedBackgroundColor(new Color(200, 200, 200)); // Darker gray for focus
        setBorderColor(new Color(150, 150, 150)); // Default border color

        // Adding hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(hoverBackgroundColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(isFocusOwner() ? focusedBackgroundColor : backgroundColor);
            }
        });

        // Adding focus effect
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(focusedBackgroundColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(backgroundColor);
            }
        });
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        setBackground(color);
    }

    public void setHoverBackgroundColor(Color color) {
        this.hoverBackgroundColor = color;
    }

    public void setFocusedBackgroundColor(Color color) {
        this.focusedBackgroundColor = color;
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        // Border
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        // Text
        super.paintComponent(g2);
        g2.dispose();
    }
}

