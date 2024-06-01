package org.example.Vue;

import org.example.Controleur.ControleurMediateur;
import org.example.IA.IA;
import org.example.IA.Intermediare;
import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterfaceInitiale extends JFrame implements Runnable {
    public InterfaceInitiale() {
        // Setting up the frame
        this.setTitle("Claim jeu de carte");

        // Change l'icone de la fenetre principale
        try {
            this.setIconImage(ImageIO.read(new File("src/main/resources/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }
        setSize(800, 600);

        // Quand on quitte la fênetre
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a BackgroundPanel with the image path
        BackgroundImage backgroundPanel = new BackgroundImage("src/main/resources/backgroundImage.jpg");
        backgroundPanel.setLayout(new GridLayout());

        // Creating a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        buttonPanel.setOpaque(false);

        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 10, 10, 10);

        // Creating the buttons with resized icons
        ImageIcon startIcon = resizeIcon(new ImageIcon("src/main/resources/startIcon.png"), 30, 30);
        ImageIcon rulesIcon = resizeIcon(new ImageIcon("src/main/resources/rulesIcon.jpg"), 30, 30);

        RoundedButton startQuickGame = new RoundedButton("Partie Rapide", startIcon);
        RoundedButton startGameButton = new RoundedButton("Commencer une partie", startIcon);
        RoundedButton rulesButton = new RoundedButton("Règles du jeu", rulesIcon);

        /*
        // Setting button sizes
        startQuickGame.setButtonSize(new Dimension(250, 60));
        startGameButton.setButtonSize(new Dimension(250, 60));
        rulesButton.setButtonSize(new Dimension(250, 60));

        // Setting font sizes
        startQuickGame.setFont(new Font("Arial", Font.PLAIN, 20));
        startGameButton.setFont(new Font("Arial", Font.PLAIN, 20));
        rulesButton.setFont(new Font("Arial", Font.PLAIN, 20));

         */

        // Adding action listeners to the buttons
        startQuickGame.addActionListener(e -> startQuickGame());
        startGameButton.addActionListener(e -> startGame());
        rulesButton.addActionListener(e -> showRules());

        // Adding buttons to the panel
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        buttonPanel.add(startQuickGame, gbcButtons);
        gbcButtons.gridy = 1;
        buttonPanel.add(startGameButton, gbcButtons);
        gbcButtons.gridy = 2;
        buttonPanel.add(rulesButton, gbcButtons);

        /*
        // Create a JLabel for the GIF animation
        JLabel gifLabel = new JLabel(new ImageIcon("src/main/resources/animationKnight.gif"));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
         */

        // Adding components to the background panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(buttonPanel, gbc);

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
        InterfacePartieCustom.demarrer();
        // JOptionPane.showMessageDialog(this, "La partie commence");
        this.setVisible(false);
    }

    private void startQuickGame() {
        // Commencer une partie rapide contre une IA moyenne
        Jeu jeu = new Jeu(true, "", "IA Intermediare");
        IA ia = new Intermediare();
        CollecteurEvenements control = new ControleurMediateur(jeu, ia);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(control::tictac, 0, 100, TimeUnit.MILLISECONDS);
        InterfaceGraphique.demarrer(jeu, control);
        // JOptionPane.showMessageDialog(this, "La partie commence");
        this.setVisible(false);
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

    public static void demarrer() {
        SwingUtilities.invokeLater(InterfaceInitiale::new);
    }

    @Override
    public void run() {
        // Running the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(InterfaceInitiale::new);
    }
}