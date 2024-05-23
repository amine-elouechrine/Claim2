package org.example.Modele;

import org.example.Patternes.Observable;

import java.io.IOException;
import java.util.List;

public class Jeu extends Observable {

    Plateau plateau;

    GestionAnnuleRefaire g;

    ReglesDeJeu r;

    public Jeu() {
        plateau = new Plateau();
        plateau.initialiserJeu();
        g = new GestionAnnuleRefaire();
        g.addToHistory(getPlateau());
        r = new ReglesDeJeu();
    }

    public boolean getPhase() {
        return getPlateau().getPhase();
    }

    public void switchPhase() {
        getPlateau().switchPhase();
        switchHand();
    }

    public void switchJoueur() {
        r.switchJoueur(getPlateau());
    }

    public void afficherMain() {
        getPlateau().joueur1.getHand().printHand();
        getPlateau().joueur2.getHand().printHand();

    }

    public void switchHand() {
        // Changer main joueur 1
        Hand main;
        main = getPlateau().getJoueur1().getHandScndPhase();
        getPlateau().getJoueur1().hand = main;

        // Changer main joueur 2
        main = getPlateau().getJoueur2().getHandScndPhase();
        getPlateau().getJoueur2().hand = main;
    }

    public Hand getHandJ1P1() {
        return getPlateau().getJoueur1().getHand();
    }

    public Hand getHandJ2P1() {
        return getPlateau().getJoueur2().getHand();
    }

    public Hand getHandJ1P2() {
        return getPlateau().getJoueur1().getHandScndPhase();
    }

    public Hand getHandJ2P2() {
        return getPlateau().getJoueur2().getHandScndPhase();
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean estFinPhase1() {
        return getPlateau().estFinPhase(getPlateau().getPhase());
    }

    public boolean estFinPartie() {
        return getPlateau().isEndOfGame();
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
        Card carte = getPlateau().getJoueurCourant().getHand().getCard(index);
        int valeur = carte.getValeur();
        String faction = carte.getFaction();
        return "\nCarte jouée : " + faction + " " + valeur;
    }

    public int[][] getCarteJouable(Card carteJoue, Hand main) {
        return getListeCarte(ReglesDeJeu.cartesJouables(carteJoue, main));
    }

    public boolean estCarteJouable(Card CarteAdverse, int indiceCarteJoue) {
        List<Card> preselected = preselected(CarteAdverse, getPlateau().getJoueurCourant().getHand());
        return getPlateau().coupJouable(preselected, indiceCarteJoue, getPlateau().getJoueurCourant().getHand());
    }

    public int getCarteFaction(int index) {
        return getPlateau().getJoueurCourant().getHand().getCard(index).getFactionScore();
    }

    public int getCarteValeur(int index) {
        return getPlateau().getJoueurCourant().getHand().getCard(index).getValeur();
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
        if (getPlateau().getCarteJoueur1() != null)
            return getPlateau().getCarteJoueur1().getFactionScore();
        else
            return -1;
    }

    public List<Card> preselected(Card carte, Hand hand) {
        return ReglesDeJeu.cartesJouables(carte, hand);
    }

    public int getCarteJoueur1V() {
        if (getPlateau().getCarteJoueur1() != null)
            return getPlateau().getCarteJoueur1().getValeur();
        else
            return -1;
    }

    public int getCarteJoueur2F() {
        if (getPlateau().getCarteJoueur2() != null)
            return getPlateau().getCarteJoueur2().getFactionScore();
        else
            return -1;
    }

    public int getCarteJoueur2V() {
        if (getPlateau().getCarteJoueur2() != null)
            return getPlateau().getCarteJoueur2().getValeur();
        else
            return -1;
    }

    public boolean estCarteJoueJ1() {
        return (getPlateau().getCarteJoueur1() != null);
    }

    public boolean estCarteJoueJ2() {
        return (getPlateau().getCarteJoueur2() != null);
    }

    public void playTrick() {
        if (getPhase()) {
            Card carteGagnante = ReglesDeJeu.carteGagnante(getPlateau().getCarteJoueur1(), getPlateau().getCarteJoueur2(), getPlateau());
            getPlateau().attribuerCarteFirstPhase(carteGagnante, r);
            if (estFinPhase1()) {
                switchPhase();
            }
            if (getPhase()) {
                getPlateau().carteAffichee = getPlateau().getPioche().getCard();
            }
        } else {
            Card carteGagnante = ReglesDeJeu.carteGagnante(getPlateau().getCarteJoueur1(), getPlateau().getCarteJoueur2(), getPlateau());
            getPlateau().attribuerCarteSecondPhase(carteGagnante, r);

        }
    }

    public void setCarteJouer() {
        getPlateau().setCarteJoueur1(null);
        getPlateau().setCarteJoueur2(null);
    }


    public void getHandtoString() {
        getPlateau().getJoueurCourant().getHand().printHand();
    }

    public String getNomJoueur(Player joueur) {
        return joueur.getName();
    }

    public void annulerCoup() throws IOException {
        System.out.println(getPlateau().getCarteJoueur1());
        g.annuler(plateau);
        System.out.println(getPlateau().getCarteJoueur1());
    }

    public void refaireCoup() {
        this.plateau = g.refaire();
    }

    public void addAction() {
        g.addToHistory(getPlateau());
    }

    public void sauve(String filename) {
    }

    public void restaure(String filename) throws IOException {
    }
}
