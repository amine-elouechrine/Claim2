package org.example.Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.example.Controleur.ControleurMediateur;
import org.example.IA.Difficile;
import org.example.IA.Facile;
import org.example.IA.Intermediare;
import org.example.Modele.Jeu;
import org.example.IA.IA;

public class InterfacePartieCustom extends JFrame implements Runnable {

    private RoundedTextField joueur1Field;
    private RoundedTextField joueur2Field;
    private JComboBox<String> IA1ComboBox;

    IA ia;

    public InterfacePartieCustom() {
        this.setTitle("Claim jeu de carte");
        BackgroundImage backgroundPanel = new BackgroundImage("/backgroundImage.jpg");
        try {
            this.setIconImage(ImageIO.read(getClass().getResource("/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }
        System.out.println("InterfacePartieCustom");
        add(backgroundPanel);


        // Quand on quitte la fênetre
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(1000, 650); // Set initial size
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel joueur1Label = new JLabel("Nom du joueur 1 : ");
        joueur1Field = new RoundedTextField(10);
        panel.add(joueur1Label, gbc);
        gbc.gridx++;
        panel.add(joueur1Field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel joueur2Label = new JLabel("Nom du joueur 2 : ");
        joueur2Field = new RoundedTextField(10);
        panel.add(joueur2Label, gbc);
        gbc.gridx++;
        panel.add(joueur2Field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        String[] options = {"IA Facile", "IA Intermédiaire", "IA Difficile", "Humain"};
        IA1ComboBox = new JComboBox<>(options);

        gbc.gridx = 0;
        panel.add(new JLabel("IA Joueur:"), gbc);
        gbc.gridx++;
        panel.add(IA1ComboBox, gbc);
        IA1ComboBox.setSelectedIndex(0);
        gbc.gridy++;

        gbc.gridx = 0;
        RoundedButton startButton = new RoundedButton("Commencer le jeu");
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(startButton, gbc);
        RoundedButton retourButton = new RoundedButton("Retour");
        //rajouter le bouton retour coin bas droite
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel.add(retourButton, gbc);


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String joueur1 = joueur1Field.getText();
                    String joueur2 = joueur2Field.getText();
                    Jeu jeu;
                    if (joueur1 != null && joueur2 != null) {

                        String IA1Selected = (String) IA1ComboBox.getSelectedItem();
                        ControleurMediateur control;
                        switch (IA1Selected) {
                            case "IA Facile":
                                ia = new Facile();
                                jeu = new Jeu(true, joueur1, "IA Facile");
                                control = new ControleurMediateur(jeu, ia);
                                break;
                            case "IA Intermédiaire":
                                // Création de l'IA
                                ia = new Intermediare();
                                jeu = new Jeu(true, joueur1, "IA Intermediare");
                                control = new ControleurMediateur(jeu, ia);
                                break;
                            case "IA Difficile":
                                // Création de l'IA
                                ia = new Difficile();
                                jeu = new Jeu(true, joueur1, "IA Difficile");
                                control = new ControleurMediateur(jeu, ia);
                                break;
                            default:
                                jeu = new Jeu(false, joueur1, joueur2);
                                control = new ControleurMediateur(jeu, null);

                                break;
                        }
                        InterfaceGraphique.demarrer(jeu, control, InterfacePartieCustom.this);

                        /*ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                        scheduler.scheduleAtFixedRate(control::tictac, 0, 100, TimeUnit.MILLISECONDS);
                        InterfaceGraphique.demarrer(jeu, control);*/
                        setVisible(false);
                    }
                } catch (NumberFormatException ex) {
                    // Show a dialog to the user
                    JOptionPane.showMessageDialog(null, "Erreur de nom de joueur", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        add(panel);
        setVisible(true);
    }


    private void setNiveaux(ControleurMediateur control, String niveaux) {
        // control.setIA(niveaux);
    }

    public static void demarrer() {
        SwingUtilities.invokeLater(InterfacePartieCustom::new);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(InterfacePartieCustom::new);
    }
}
