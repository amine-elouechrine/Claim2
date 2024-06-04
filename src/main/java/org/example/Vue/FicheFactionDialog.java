package org.example.Vue;

import org.example.Modele.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FicheFactionDialog extends JDialog {

    CollecteurEvenements control;

    public FicheFactionDialog(Frame owner, String faction, List<Card> cardsJ1, List<Card> cardsJ2, CollecteurEvenements c) {
        super(owner, faction, true);
        setLayout(new BorderLayout());

        control = c;

        // Panel pour les cartes du joueur 1
        JPanel panelJ1 = new JPanel();
        panelJ1.setLayout(new GridLayout(0, 1));
        panelJ1.setBorder(BorderFactory.createTitledBorder(control.getNomJoueur1()));

        for (Card card : cardsJ1) {
            panelJ1.add(new JLabel(card.getValeur()+" de "+card.getFaction()));  // Remplacez par un affichage graphique de la carte si disponible
        }

        // Panel pour les cartes du joueur 2
        JPanel panelJ2 = new JPanel();
        panelJ2.setLayout(new GridLayout(0, 1));
        panelJ2.setBorder(BorderFactory.createTitledBorder(control.getNomJoueur2()));
        for (Card card : cardsJ2) {
            panelJ2.add(new JLabel(card.getValeur()+" de "+card.getFaction()));  // Remplacez par un affichage graphique de la carte si disponible
        }

        add(panelJ1, BorderLayout.WEST);
        add(panelJ2, BorderLayout.EAST);

        setSize(400, 300);
        setLocationRelativeTo(owner);
    }
}
