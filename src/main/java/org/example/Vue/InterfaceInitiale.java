package org.example.Vue;

import org.example.Controleur.ControleurMediateur;
import org.example.IA.IA;
import org.example.IA.Intermediare;
import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterfaceInitiale extends JFrame implements Runnable {

    public InterfaceInitiale() {
        // Setting up the frame
        setTitle("Claim jeu de carte");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set window icon
        setWindowIcon("/Claim.png");

        // Create and set up the background panel
        BackgroundImage backgroundPanel = new BackgroundImage("/backgroundImage.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // Use GridBagLayout

        // Create the button panel
        JPanel buttonPanel = createButtonPanel();

        // Add button panel to the background panel with constraints to center it
        addComponentToPanel(backgroundPanel, buttonPanel, 0, 0, GridBagConstraints.CENTER);

        // Add the background panel to the frame
        add(backgroundPanel);

        // Making the frame visible
        setVisible(true);
    }

    public static void demarrer() {
        SwingUtilities.invokeLater(InterfaceInitiale::new);
    }

    public void setWindowIcon(String path) {
        // Utilisez getClass().getResource pour obtenir l'URL de la ressource
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image = icon.getImage();
            setIconImage(image); // Méthode à appeler sur JFrame pour définir l'icône
        } else {
            System.err.println("Erreur de chargement de l'icone");
        }
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);

        // Create a common dimension for buttons
        Dimension buttonSize = new Dimension(250, 60);

        // Create buttons
        RoundedButton startQuickGame = createButton("Partie Rapide", "src/main/resources/startIcon.png", e -> startQuickGame(), buttonSize);
        RoundedButton startGameButton = createButton("Commencer une partie", "src/main/resources/startIcon.png", e -> startGame(), buttonSize);
        RoundedButton rulesButton = createButton("Règles du jeu", "src/main/resources/rulesIcon.jpg", e -> showRules(), buttonSize);
        RoundedButton quitButton = createButton("Quitter", "src/main/resources/quitIcon.png", e -> System.exit(0), buttonSize);
        // Add buttons to the panel with constraints to space them
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(startQuickGame, gbc);

        gbc.gridy = 1;
        buttonPanel.add(startGameButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(rulesButton, gbc);

        gbc.gridy = 3;
        buttonPanel.add(quitButton, gbc);
        return buttonPanel;
    }

    private RoundedButton createButton(String text, String iconPath, ActionListener actionListener, Dimension size) {
        ImageIcon icon = resizeIcon(new ImageIcon(iconPath), 30, 30);
        RoundedButton button = new RoundedButton(text, icon);
        button.addActionListener(actionListener);
        button.setPreferredSize(size); // Set preferred size for the button
        return button;
    }

    private void addComponentToPanel(JPanel panel, Component component, int gridx, int gridy, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        panel.add(component, gbc);
    }

    private void startGame() {
        InterfacePartieCustom.demarrer();
        this.setVisible(false);
    }

    private void startQuickGame() {
        Jeu jeu = new Jeu(true, "", "IA Intermediare");
        IA ia = new Intermediare();
        CollecteurEvenements control = new ControleurMediateur(jeu, ia);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(control::tictac, 0, 100, TimeUnit.MILLISECONDS);
        InterfaceGraphique.demarrer(jeu, control,this);
        this.setVisible(false);
    }

    private void showRules() {
        new ComposantRegle();
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(InterfaceInitiale::new);
    }
}
