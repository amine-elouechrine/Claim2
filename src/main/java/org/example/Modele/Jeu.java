package org.example.Modele;

import org.example.Vue.NiveauGraphique;
import org.example.Patternes.Observable;

import java.util.List;

public class Jeu extends Observable {
    Plateau plateau;
    Player joueur1;
    Player joueur2;
    Cards cards;

    ReglesDeJeu r;


    public Jeu() {
        plateau = new Plateau();
        plateau.initialiserJeu();
        r = new ReglesDeJeu();
    }

    public boolean getPhase() {
        return plateau.getPhase();
    }

    public void switchPhase() {
        plateau.switchPhase();
    }

    public Hand getHandJ1P1() {
        return plateau.getJoueur1().getHand();
    }

    public Hand getHandJ2P1() {
        return plateau.getJoueur2().getHand();
    }

    public Hand getHandJ1P2() {
        return plateau.getJoueur1().getHandScndPhase();
    }

    public Hand getHandJ2P2() {
        return plateau.getJoueur2().getHandScndPhase();
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public int[][] getListCardJouable(Card carteAdversaire, Hand mainJoueur) {
        List<Card> listeCarte = r.cartesJouables(carteAdversaire, mainJoueur);
        int[][] tableauCartes = new int[listeCarte.size()][2];
        int i = 0;
        for (Card carte : listeCarte) {
            tableauCartes[i][0] = carte.getValeur();
            tableauCartes[i][1] = carte.getFactionScore();
            i++;
        }
        return tableauCartes;
    }

    public int[][] getMainJoueur1Phase1() {

        int i = 0;
        Hand main = getHandJ1P1();
        List<Card> cartes = main.getAllCards();
        int[][] tableauCartes = new int[cartes.size()][2];
        for (Card carte : cartes) {
            tableauCartes[i][0] = carte.getValeur();
            tableauCartes[i][1] = carte.getFactionScore();
            i++;
        }
        return tableauCartes;
    }
}
