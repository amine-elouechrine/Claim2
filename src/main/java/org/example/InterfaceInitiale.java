package org.example;

import org.example.Vue.BackgroundImage;
import org.example.Vue.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class InterfaceInitiale extends JFrame {
    public InterfaceInitiale() {
        // Setting up the frame
        setTitle("Claim Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a BackgroundPanel with the image path
        BackgroundImage backgroundPanel = new BackgroundImage("src\\main\\resources\\backgroundImage.jpg");
        backgroundPanel.setLayout(new GridBagLayout());

        // Creating a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 10, 10, 10);

        // Creating the buttons with resized icons
        ImageIcon startIcon = resizeIcon(new ImageIcon("src\\main\\resources\\startIcon.png"), 30, 30);
        ImageIcon rulesIcon = resizeIcon(new ImageIcon("src\\main\\resources\\rulesIcon.jpg"), 30, 30);

        RoundedButton startGameButton = new RoundedButton("Commencer une partie", startIcon);
        RoundedButton rulesButton = new RoundedButton("Règles du jeu", rulesIcon);

        // Setting button sizes
        startGameButton.setButtonSize(new Dimension(250, 80));
        rulesButton.setButtonSize(new Dimension(250, 80));

        // Setting font sizes
        startGameButton.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesButton.setFont(new Font("Arial", Font.PLAIN, 16));

        // Adding action listeners to the buttons
        startGameButton.addActionListener(e -> startGame());
        rulesButton.addActionListener(e -> showRules());

        // Adding buttons to the panel
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        buttonPanel.add(startGameButton, gbcButtons);
        gbcButtons.gridy = 1;
        buttonPanel.add(rulesButton, gbcButtons);

        // Create a JLabel for the GIF animation
        JLabel gifLabel = new JLabel(new ImageIcon("src\\main\\resources\\animationKnight.gif"));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally

        // Adding components to the background panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(buttonPanel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END; // Align to the bottom
        backgroundPanel.add(gifLabel, gbc);

        // Adding the background panel to the frame
        add(backgroundPanel);

        // Making the frame visible
        setVisible(true);
    }

    private void startGame() {
        // Logic to start the game against an AI
        JOptionPane.showMessageDialog(this, "La partie commence contre une IA!");
    }

    private void showRules() {
        // Logic to show the game rules
        JOptionPane.showMessageDialog(this, "Voici les règles du jeu...");
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public static void main(String[] args) {
        // Running the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(InterfaceInitiale::new);
    }
}
