package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class DissolvingIntro extends JPanel {
    private List<String> lines;
    private int currentLineIndex = 0;
    private float alpha = 0f;
    private Font font;
    private JFrame frame;

    public DissolvingIntro(JFrame frame) {
        this.frame = frame;
        lines = new ArrayList<>();
        lines.add("The King is dead!");
        lines.add("What happened?");
        lines.add("Nobody really knows...");
        lines.add("...but he was found face down in a wine barrel this morning.");
        lines.add("Regardless, the King is dead without any known heirs,");
        lines.add("so it’s up to the five factions of the realm to decide who will be the new king");
        lines.add("Do you have what it takes to win over the realm’s factions?");

        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        font = new Font("Algerian", Font.PLAIN, 30);

        Timer timer = new Timer(100, this::updateAnimation);
        timer.start();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    launchMainApplication();
                }
            }
        });
    }

    private void updateAnimation(ActionEvent e) {
        alpha += 0.02f;
        if (alpha >= 1f) {
            alpha = 0f;
            currentLineIndex++;
            if (currentLineIndex >= lines.size()) {
                ((Timer) e.getSource()).stop();
                launchMainApplication();
                return;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the main animated text
        if (currentLineIndex < lines.size()) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setFont(font);
            drawString(g2d, lines.get(currentLineIndex), 50, getHeight() / 2);
        }

        // Draw the ESC indicator without any animation
        drawEscIndicator(g2d);
    }

    private void drawString(Graphics2D g2d, String text, int x, int y) {
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        int width = getWidth();
        int lineHeight = g2d.getFontMetrics().getHeight();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (g2d.getFont().getStringBounds(line.toString() + word, frc).getWidth() > width - 100) {
                g2d.drawString(line.toString(), x, y);
                y += lineHeight;
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }
        g2d.drawString(line.toString(), x, y);
    }

    private void drawEscIndicator(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Ensure full opacity
        g2d.setFont(new Font("Serif", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);
        FontMetrics metrics = g2d.getFontMetrics();
        String escText = "Press ESC to skip";
        int x = getWidth() - metrics.stringWidth(escText) - 10;
        int y = metrics.getHeight();
        g2d.drawString(escText, x, y);
    }

    private void launchMainApplication() {
        frame.dispose();
        org.example.Claim.main(new String[0]);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dissolving Intro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        DissolvingIntro introPanel = new DissolvingIntro(frame);
        frame.add(introPanel);
        frame.setVisible(true);
    }
}
