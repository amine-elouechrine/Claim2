package org.example.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceInitiale extends JFrame {
    public InterfaceInitiale() {
        // Setting up the frame
        setTitle("Claim Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creating a BackgroundPanel with the image path
        BackgroundImage backgroundPanel = new BackgroundImage("src\\main\\resources\\backgroundImage.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Creating the buttons
        RoundedButton startGameButton = new RoundedButton("Commencer une partie");
        RoundedButton rulesButton = new RoundedButton("Règles du jeu");

        // Adding hover effect to buttons
        addHoverEffect(startGameButton);
        addHoverEffect(rulesButton);

        // Adding action listeners to the buttons
        startGameButton.addActionListener(e -> startGame());
        rulesButton.addActionListener(e -> showRules());

        // Adding buttons to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(startGameButton, gbc);
        gbc.gridy = 1;
        backgroundPanel.add(rulesButton, gbc);

        // Adding the background panel to the frame
        add(backgroundPanel);

        // Making the frame visible
        setVisible(true);
    }
        private void addHoverEffect(JButton button) {
        Font originalFont = button.getFont();
        Font hoverFont = originalFont.deriveFont(originalFont.getSize() * 1.2f);
        Color originalColor = button.getForeground();
        Color hoverColor = Color.RED;  // Change this to the desired hover color

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(hoverFont);
                button.setForeground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(originalFont);
                button.setForeground(originalColor);
            }
        });
    }

    private void startGame() {
        // Logic to start the game against an AI
        JOptionPane.showMessageDialog(this, "La partie commence contre une IA!");
    }

    private void showRules() {
        // Logic to show the game rules
        JOptionPane.showMessageDialog(this, "Voici les règles du jeu...");
    }

    public static void main(String[] args) {
        // Running the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(InterfaceInitiale::new);
    }
}
