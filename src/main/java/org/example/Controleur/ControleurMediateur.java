package org.example.Controleur;

import org.example.Modele.Card;
import org.example.Vue.CollecteurEvenements;
import org.example.Modele.Jeu;
import org.example.Patternes.Observable;

public class ControleurMediateur implements CollecteurEvenements {

    Jeu jeu;

    public ControleurMediateur(Jeu j) {
        jeu = j;
    }

    public boolean getPhase() {
        return jeu.getPhase();
    }

    public int getNbCardsJ1P1() {
        return jeu.getHandJ1P1().size();
    }

    public int getNbCardsJ2P1() {
        return jeu.getHandJ2P1().size();
    }

    public int getNbCardsJ1P2() {
        return jeu.getHandJ1P2().size();
    }

    public int getNbCardsJ2P2() {
        return jeu.getHandJ2P2().size();
    }


    public void clicSourisJ2(int index) {
        // jeu.afficherMain();
        System.out.println(jeu.getCardtoString(index));

        // Application des règles de jeu pour la selection de carte
        Card carteJoue = jeu.getPlateau().jouerCarte(index);
        jeu.switchJoueur();

        if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
            jeu.playTrick();
            jeu.setCarteJouer();
        }
        if(jeu.estFinPhase1()) {
            jeu.switchPhase();
            // Fin de la phase 1
        }

        if(jeu.estFinPartie()) {
            // Calcul des scores
        }

        jeu.metAJour();
    }

    public void clicSouris(int index) {
        if (index == -1) {
            ;
        } else {
            // jeu.getHandtoString();
            // jeu.afficherMain();
            System.out.println(jeu.getCardtoString(index));

            // Application des règles de jeu pour la selection de carte
            Card carteJoue = jeu.getPlateau().jouerCarte(index);

            jeu.switchJoueur();
            if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                jeu.playTrick();
                jeu.setCarteJouer();
            }

            if(jeu.estFinPhase1()) {
                jeu.switchPhase();
                // Fin de la phase 1
            }

            if(jeu.estFinPartie()) {
                // Calcul des scores
            }

            // Ajouter temporisation / animation

            // L'IA joue une carte
            // IA.joue() ?

            // Ajouter temporisation / animation pour la carte jouer par l'IA

            // On joue le plie
            // jeu.playTrick();

            // Ajouter temporisation / Animation pour la bataille et l'attribution des cartes après le plie

            // jeu.setCarteJouer();
            jeu.metAJour();
        }
    }
}
