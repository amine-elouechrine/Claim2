package org.example.Modele;

import org.example.Vue.NiveauGraphique;
import org.example.Patternes.Observable;

import java.util.List;

public class Jeu extends Observable {
    Plateau plateau;
    Player joueur1;
    Player joueur2;
    Cards cards;

    public ReglesDeJeu r;


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
        switchHand();
    }

    public void switchJoueur() {
        r.switchJoueur(plateau);
    }

    public void afficherMain() {
        plateau.joueur1.getHand().printHand();
        plateau.joueur2.getHand().printHand();

    }

    public void switchHand() {
        // Changer main joueur 1
        Hand main;
        main = plateau.getJoueur1().getHandScndPhase();
        plateau.joueur1.hand = main;

        // Changer main joueur 2
        main = plateau.getJoueur2().getHandScndPhase();
        plateau.joueur2.hand = main;
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

    public ReglesDeJeu getRegles() {
        return r;
    }

    public boolean estFinPhase1() {
        return plateau.estFinPhase(plateau.getPhase());
    }

    public boolean estFinPartie() {
        return plateau.isEndOfGame();
    }

    private int[][] getListeCarte(List<Card> listeCarte) {
        int[][] tableauCartes = new int[listeCarte.size()][2];
        int i = 0;
        for (Card carte : listeCarte) {
            tableauCartes[i][0] = carte.getValeur();
            tableauCartes[i][1] = carte.getFactionScore();
            i++;
        }
        return tableauCartes;
    }

    public int[][] getHand(Hand main) {
        List<Card> cartes = main.getAllCards();
        return getListeCarte(cartes);
    }

    public int[][] getListCardJouable(Card carteAdversaire, Hand mainJoueur) {
        List<Card> listeCarte = r.cartesJouables(carteAdversaire, mainJoueur);
        return getListeCarte(listeCarte);
    }

    public int[][] getMainJoueur1Phase1() {
        Hand main = getHandJ1P1();
        return getHand(main);
    }

    public int[][] getMainJoueur2Phase1() {
        Hand main = getHandJ2P1();
        return getHand(main);
    }

    public int[][] getMainJoueur1Phase2() {
        Hand main = getHandJ1P2();
        return getHand(main);
    }

    public int[][] getMainJoueur2Phase2() {
        Hand main = getHandJ2P2();
        return getHand(main);
    }

    public String getCardtoString(int index) {
        Card carte = plateau.joueurCourant.hand.getCard(index);
        int valeur = carte.getValeur();
        String faction = carte.getFaction();
        return "\nCarte jouée : " + faction + " " + valeur;
    }

    public int[][] getCarteJouable(Card carteJoue, Hand main) {
        return getListeCarte(r.cartesJouables(carteJoue, main));
    }

    public boolean estCarteJouable(Card CarteAdverse, int indiceCarteJoue) {
        List<Card> preselected = preselected(CarteAdverse, getPlateau().getJoueurCourant().getHand());
        return plateau.coupJouable(preselected, indiceCarteJoue, plateau.getJoueurCourant().getHand());
    }

    public int getCarteFaction(int index) {
        return plateau.joueurCourant.hand.getCard(index).getFactionScore();
    }

    public int getCarteValeur(int index) {
        return plateau.joueurCourant.hand.getCard(index).getValeur();
    }

    public int getFactionScoreCarteAffiche() {
        return plateau.getCarteAffichee().getFactionScore();
    }

    public int getValeurCarteAffiche() {
        return plateau.getCarteAffichee().getValeur();
    }

    public int getMaxValeurFactionJ1(String faction) {
        return getPlateau().getJoueur1().getPileDeScore().maxValueOfFaction(faction);
    }

    public int getMaxValeurFactionJ2(String faction) {
        return getPlateau().getJoueur2().getPileDeScore().maxValueOfFaction(faction);
    }

    public int getCarteJoueur1F() {
        if (plateau.getCarteJoueur1() != null)
            return plateau.getCarteJoueur1().getFactionScore();
        else
            return -1;
    }

    public List<Card> preselected(Card carte, Hand hand) {
        return r.cartesJouables(carte, hand);
    }

    public int getCarteJoueur1V() {
        if (plateau.getCarteJoueur1() != null)
            return plateau.getCarteJoueur1().getValeur();
        else
            return -1;
    }

    public int getCarteJoueur2F() {
        if (plateau.getCarteJoueur2() != null)
            return plateau.getCarteJoueur2().getFactionScore();
        else
            return -1;
    }

    public int getCarteJoueur2V() {
        if (plateau.getCarteJoueur2() != null)
            return plateau.getCarteJoueur2().getValeur();
        else
            return -1;
    }

    public boolean estCarteJoueJ1() {
        return (plateau.getCarteJoueur1() != null);
    }

    public boolean estCarteJoueJ2() {
        return (plateau.getCarteJoueur2() != null);
    }

    public void playTrick() {
        if (getPhase()) {
            Card carteGagnante = r.carteGagnante(plateau.getCarteJoueur1(), plateau.getCarteJoueur2());
            plateau.attribuerCarteFirstPhase(carteGagnante,r);

            if (estFinPhase1()) {
                switchPhase();
            }

            if (getPhase()) {
                plateau.carteAffichee = plateau.pioche.getCard();
            }
        } else {
            Card carteGagnante = r.carteGagnante(plateau.getCarteJoueur1(), plateau.getCarteJoueur2());
            plateau.attribuerCarteSecondPhase(carteGagnante, r);

        }
    }

    public void setCarteJouer() {
        plateau.setCarteJoueur1(null);
        plateau.setCarteJoueur2(null);
    }


    public void getHandtoString() {
        plateau.joueurCourant.hand.printHand();
    }

    public String getNomJoueur(Player joueur) {
        return joueur.getName();
    }
}
