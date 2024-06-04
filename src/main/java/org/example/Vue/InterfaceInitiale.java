package org.example.Vue;

import org.example.Controleur.ControleurMediateur;
import org.example.IA.IA;
import org.example.IA.Intermediare;
import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public void showRules() {
        String rules =
                "Set up\n" +
                        "Shuffle all cards and place them in a face-down deck in the middle of the table.\n" +
                        "Deal each player 13 cards. Each player takes their cards into their hand without showing these to the other player.\n\n" +

                        "Game overview\n" +
                        "The game is played in two distinct phases. In Phase One, each player gets a hand of cards that they will use to recruit\n" +
                        "followers. In Phase Two, they will use the followers they gained in Phase One to compete and win over the five factions\n" +
                        "of the realms. At the end of the game the player who has the majority of followers of a faction wins that faction’s vote. The\n" +
                        "player who wins the vote of at least three factions wins the game!\n\n" +

                        "Phase One: Recruit followers\n" +
                        "This phase consists of thirteen tricks, one for each card in each player’s hand. The Leader for each trick is the player who won the last\n" +
                        "trick (or in the case of the first trick the youngest player).\n" +
                        "Each trick is played by following these three steps:\n" +
                        "1. Reveal one card\n" +
                        "   Flip the top card from the deck in the middle of the table and place it between both players. The players will be\n" +
                        "   competing for this card.\n" +
                        "2. Play cards\n" +
                        "   1. The Leader plays any single card from his hand.\n" +
                        "      Note: He can choose a card of any faction, this doesn’t have to be a card of the faction the players are competing for.\n" +
                        "   2. Then the other player plays any single card from their hand.\n" +
                        "      Important: If able, the other player must follow faction. Which means, if this player has a card in their hand of the\n" +
                        "      faction of the card just played then it must be played (exception: Doppelgänger). In other words, a player can only\n" +
                        "      play a card of a different faction if no card in their hand matches the faction of the one played by the Leader.\n" +
                        "3. Collect cards\n" +
                        "   1. The player that played the highest valued card (0 is the lowest, 9 the highest) of the faction played by the Leader wins\n" +
                        "      the card in the middle. In case of a tie, the Leader wins the trick.\n" +
                        "      Note: If the second player played a card of another faction then the Leader automatically wins the trick, unless a\n" +
                        "      faction power (Doppelgänger or Knight) is in effect.\n" +
                        "   2. The winner places their won card face-down in their Follower deck. The loser takes a card from the top of the deck in the\n" +
                        "      middle and places it face-down in their Follower deck. This player may look at the card, but is not allowed to show it to the\n" +
                        "      other player.\n" +
                        "      Note: Cards in your Follower deck will be your hand for Phase Two.\n" +
                        "   3. If either player has played an Undead card, the winner of the trick takes them and places them face-up in their Score pile.\n" +
                        "      Important: A won Undead card from the middle of the table still goes to the winner’s Followers pile.\n" +
                        "   4. Discard all (remaining) cards played.\n" +
                        "      Important: Be sure to keep your Score pile face-up and your Follower deck face-down to keep them separated.\n" +
                        "Continue until the deck is depleted and the players are out of cards in their hand.\n" +
                        "Now the game moves onto Phase Two.\n\n" +

                        "Phase Two: Gather supporters\n" +
                        "Both players take the 13 Follower cards they gained during Phase One into their hand. Now, players will play another\n" +
                        "13 tricks. However, instead of competing for particular cards in the middle, this time players compete for both cards\n" +
                        "played each trick.\n" +
                        "Each trick is played by following these two steps:\n" +
                        "1. Play cards\n" +
                        "   1. The leader plays any single card from their hand.\n" +
                        "   2. Then the other player plays any single card from their hand.\n" +
                        "      Important: The rules regarding following faction are still in effect!\n" +
                        "2. Collect cards\n" +
                        "   1. Determine the winner like in Phase One.\n" +
                        "   2. The winner of the trick places both played cards face-up in their Score pile, unless a faction power (Dwarves) is in effect.\n\n" +

                        "After all players have played all cards from their hand, all factions are scored.\n\n" +

                        "End of the game & Scoring\n" +
                        "The players count how many cards of each faction they have in their Score pile. Whoever has the most cards of a faction\n" +
                        "wins that faction’s vote. If there is a tie, whoever has the highest single card of that faction wins the vote. The player\n" +
                        "who wins the vote of at least three factions wins the game!\n\n" +

                        "Faction’s special powers\n" +
                        "Each faction has a special power that effects play. They are as follows:\n\n" +

                        "Goblins\n" +
                        "No special power.\n\n" +

                        "Knights\n" +
                        "When played after a Goblin, it automatically beats a Goblin regardless of its value.\n" +
                        "Important: The player must still follow faction, if able.\n\n" +

                        "Undead\n" +
                        "Played Undead cards are not discarded in Phase One like the other faction’s cards, but instead are added to\n" +
                        "the trick winner’s Score Pile.\n\n" +

                        "Dwarves\n" +
                        "In Phase Two, the player losing a trick collects all Dwarves played during this trick and adds them to their\n" +
                        "Score Pile. The winner will still collect any non-Dwarves cards that have been played.\n\n" +

                        "Doppelgängers\n" +
                        "This faction is considered wild. You may play a Doppelgänger instead of the asked suit, even if you’re able to\n" +
                        "follow suit. When played second, it is considered to be the same faction of the first card and is considered to be\n" +
                        "following faction.\n" +
                        "Note: If the Leader plays a Doppelgänger, the other player still must follow faction by playing a Doppelgänger,\n" +
                        "if able.\n" +
                        "Important: A Doppelgänger does not take any special powers from the faction it follows. For instance, if\n" +
                        "played in Phase One after an Undead it does not get taken by the winner, nor would it get taken in Phase Two\n" +
                        "by the loser like a Dwarf would.\n";

        JTextArea textArea = new JTextArea(20, 50);
        textArea.setText(rules);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0); // Scroll to the top
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JOptionPane.showMessageDialog(null, scrollPane, "Règles du jeu", JOptionPane.INFORMATION_MESSAGE);
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
        SwingUtilities.invokeLater(InterfaceInitiale::new);
    }
}
