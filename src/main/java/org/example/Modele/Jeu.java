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

    public String getCardtoString(int index) {
        Card carte = plateau.joueurCourant.hand.getCard(index);
        int valeur = carte.getValeur();
        String faction = carte.getFaction();
        return "\nCarte jouée : " + faction + " " + valeur;
    }

    public int getCarteFaction(int index) {
        return plateau.joueurCourant.hand.getCard(index).getFactionScore();
    }

    public int getCarteValeur(int index) {
        return plateau.joueurCourant.hand.getCard(index).getValeur();
    }

    public int getCarteJoueur1F() {
        if(plateau.getCarteJoueur1() != null)
            return plateau.getCarteJoueur1().getFactionScore();
        else
            return -1;
    }

    public int getCarteJoueur1V() {
        if(plateau.getCarteJoueur1() != null)
            return plateau.getCarteJoueur1().getValeur();
        else
            return -1;
    }
    public int getCarteJoueur2F() {
        if(plateau.getCarteJoueur2() != null)
            return plateau.getCarteJoueur2().getFactionScore();
        else
            return -1;
    }
    public int getCarteJoueur2V() {
        if(plateau.getCarteJoueur2() != null)
            return plateau.getCarteJoueur2().getValeur();
        else
            return -1;
    }

    public void playTrick() {
        Card carteGagnante = r.carteGagnante(plateau.getCarteJoueur1(), plateau.getCarteJoueur2());
        plateau.attribuerCarteFirstPhase(carteGagnante);
        plateau.carteAffichee = plateau.pioche.getCard();
    }

    public void setCarteJouer() {
        plateau.setCarteJoueur1(null);
        plateau.setCarteJoueur2(null);
    }


    public void getHandtoString() {
        plateau.joueurCourant.hand.printHand();
    }
}
