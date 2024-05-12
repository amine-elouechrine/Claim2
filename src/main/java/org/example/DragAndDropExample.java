package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DragAndDropExample extends JFrame {
    private JPanel panel;
    private int startX, startY; // Starting position of the dragged item
    private int currentX, currentY; // Current position of the dragged item
    private int shapeWidth = 50; // Width of the shape
    private int shapeHeight = 50; // Height of the shape
    private boolean dragging = false;

    public DragAndDropExample() {
        setTitle("Drag and Drop Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the shape at the current position
                g.setColor(Color.RED);
                g.fillRect(currentX, currentY, shapeWidth, shapeHeight);
            }
        };
        panel.setPreferredSize(new Dimension(400, 300));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                if (startX >= currentX && startX <= currentX + shapeWidth &&
                        startY >= currentY && startY <= currentY + shapeHeight) {
                    dragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    int dx = e.getX() - startX;
                    int dy = e.getY() - startY;
                    currentX += dx;
                    currentY += dy;
                    startX += dx;
                    startY += dy;
                    panel.repaint();
                }
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DragAndDropExample());
    }
}
