package org.example.Modele;

import org.example.Patternes.Observable;

import java.io.IOException;
import java.util.List;

public class Jeu extends Observable {
    public Plateau plateau;

    GestionAnnuleRefaire g;

    public ReglesDeJeu r;

    public Jeu() {
        plateau = new Plateau();
        plateau.initialiserJeu();
        this.g = new GestionAnnuleRefaire(plateau);
        r = new ReglesDeJeu();
    }

    public boolean getPhase() {
        return g.plateau.getPhase();
    }

    public void switchPhase() {
        g.plateau.switchPhase();
        switchHand();
    }

    public void switchJoueur() {
        r.switchJoueur(g.plateau);
    }

    public void afficherMain() {
        g.plateau.joueur1.getHand().printHand();
        g.plateau.joueur2.getHand().printHand();

    }

    public void switchHand() {
        // Changer main joueur 1
        Hand main;
        main = g.plateau.getJoueur1().getHandScndPhase();
        g.plateau.joueur1.hand = main;

        // Changer main joueur 2
        main = g.plateau.getJoueur2().getHandScndPhase();
        g.plateau.joueur2.hand = main;
    }

    public Hand getHandJ1P1() {
        return g.plateau.getJoueur1().getHand();
    }

    public Hand getHandJ2P1() {
        return g.plateau.getJoueur2().getHand();
    }

    public Hand getHandJ1P2() {
        return g.plateau.getJoueur1().getHandScndPhase();
    }

    public Hand getHandJ2P2() {
        return g.plateau.getJoueur2().getHandScndPhase();
    }

    public Plateau getPlateau() {
        return g.plateau;
    }

    public boolean estFinPhase1() {
        return g.plateau.estFinPhase(g.plateau.getPhase());
    }

    public boolean estFinPartie() {
        return g.plateau.isEndOfGame();
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
        List<Card> listeCarte = ReglesDeJeu.cartesJouables(carteAdversaire, mainJoueur);
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
        Card carte = g.plateau.joueurCourant.hand.getCard(index);
        int valeur = carte.getValeur();
        String faction = carte.getFaction();
        return "\nCarte jouée : " + faction + " " + valeur;
    }

    public int[][] getCarteJouable(Card carteJoue, Hand main) {
        return getListeCarte(ReglesDeJeu.cartesJouables(carteJoue, main));
    }

    public boolean estCarteJouable(Card CarteAdverse, int indiceCarteJoue) {
        List<Card> preselected = preselected(CarteAdverse, getPlateau().getJoueurCourant().getHand());
        return g.plateau.coupJouable(preselected, indiceCarteJoue, g.plateau.getJoueurCourant().getHand());
    }

    public int getCarteFaction(int index) {
        return g.plateau.joueurCourant.hand.getCard(index).getFactionScore();
    }

    public int getCarteValeur(int index) {
        return g.plateau.joueurCourant.hand.getCard(index).getValeur();
    }

    public int getCarteAfficheeFactionScore() {
        return getPlateau().getCarteAffichee().getFactionScore();
    }

    public int getCarteAfficheeValeur() {
        return getPlateau().getCarteAffichee().getValeur();
    }

    public int getNbCardFactionFromPileScoreJ1(String faction) {
        return getPlateau().getJoueur1().getPileDeScore().getCardFaction(faction).size();
    }

    public int getNbCardFactionFromPileScoreJ2(String faction) {
        return getPlateau().getJoueur2().getPileDeScore().getCardFaction(faction).size();
    }

    public int getMaxValueoOfFactionFromPileScoreJ1(String faction) {
        return getPlateau().getJoueur1().getPileDeScore().maxValueOfFaction(faction);
    }

    public int getMaxValueoOfFactionFromPileScoreJ2(String faction) {
        return getPlateau().getJoueur2().getPileDeScore().maxValueOfFaction(faction);
    }

    public int getCarteJoueur1F() {
        if (g.plateau.getCarteJoueur1() != null)
            return g.plateau.getCarteJoueur1().getFactionScore();
        else
            return -1;
    }

    public List<Card> preselected(Card carte, Hand hand) {
        return ReglesDeJeu.cartesJouables(carte, hand);
    }

    public int getCarteJoueur1V() {
        if (g.plateau.getCarteJoueur1() != null)
            return g.plateau.getCarteJoueur1().getValeur();
        else
            return -1;
    }

    public int getCarteJoueur2F() {
        if (g.plateau.getCarteJoueur2() != null)
            return g.plateau.getCarteJoueur2().getFactionScore();
        else
            return -1;
    }

    public int getCarteJoueur2V() {
        if (g.plateau.getCarteJoueur2() != null)
            return g.plateau.getCarteJoueur2().getValeur();
        else
            return -1;
    }

    public boolean estCarteJoueJ1() {
        return (g.plateau.getCarteJoueur1() != null);
    }

    public boolean estCarteJoueJ2() {
        return (g.plateau.getCarteJoueur2() != null);
    }

    public void playTrick() {
        if (getPhase()) {
            Card carteGagnante = ReglesDeJeu.carteGagnante(g.plateau.getCarteJoueur1(), g.plateau.getCarteJoueur2(), g.plateau);
            g.plateau.attribuerCarteFirstPhase(carteGagnante, r);
            if (estFinPhase1()) {
                switchPhase();
            }
            if (getPhase()) {
                g.plateau.carteAffichee = g.plateau.pioche.getCard();
            }
        } else {
            Card carteGagnante = ReglesDeJeu.carteGagnante(g.plateau.getCarteJoueur1(), g.plateau.getCarteJoueur2(), g.plateau);
            g.plateau.attribuerCarteSecondPhase(carteGagnante, r);

        }
    }

    public void setCarteJouer() {
        g.plateau.setCarteJoueur1(null);
        g.plateau.setCarteJoueur2(null);
    }


    public void getHandtoString() {
        g.plateau.joueurCourant.hand.printHand();
    }

    public String getNomJoueur(Player joueur) {
        return joueur.getName();
    }

    public void annulerCoup() {
        System.out.println(g.plateau.getCarteJoueur1());
        g.annuler();
        System.out.println(g.plateau.getCarteJoueur1());
    }

    public void refaireCoup() {
        g.annuler();
    }

    public void sauve(String filename) {
    }

    public void restaure(String filename) throws IOException {
    }
}
