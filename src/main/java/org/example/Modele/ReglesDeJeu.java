package org.example.Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * @file ReglesDeJeu.java
 * @brief Contient les règles spécifiques du jeu.
 *
 * La classe ReglesDeJeu définit les méthodes pour déterminer la carte gagnante entre deux cartes,
 * le gagnant d'une manche, les cartes jouables par un joueur en fonction de la carte jouée par l'adversaire,
 * le gagnant d'une partie, ainsi que l'application des règles spéciales des factions.
 */
public class ReglesDeJeu {

    /**
     * Méthode pour déterminer quelle carte l'emporte entre deux cartes selon les règles du jeu.
     * @param carte1 La première carte jouer .
     * @param carte2 La deuxième carte jouer .
     * @param plateau Le plateau de jeu.
     * l'ordre de passage en paramettre des cartes n'est pas important
     * @return La carte gagnante ou null en cas d'égalité.
     */
    public static Card carteGagnante(Card carte1, Card carte2, Plateau plateau) {
        String faction1 = carte1.getFaction();
        String faction2 = carte2.getFaction();

        // Règle spéciale pour les Gobelins et les Chevaliers
        if ((faction1.equals("Goblins") && faction2.equals("Knights") )|| (faction1.equals("Knights") && faction2.equals("Goblins"))) {
            return GobelinVsKnight(carte1, carte2);
        }

        // Règle spéciale pour les Doppelgangers
        else if (faction1.equals("Doppelganger") || faction2.equals("Doppelganger")) {

            return DoppelgangerVsCard(carte1, carte2, plateau);
        }

        // si carte1 vs carte2 il n'y a pas de regles speciaux a appliquer
        else {
            return cardVScard(carte1, carte2, plateau);
        }
    }

    /**
     * Méthode pour déterminer quelle carte l'emporte entre un Doppelganger et une autre carte.
     * @param carte1
     * @param carte2
     * @param plateau Le plateau de jeu.
     * @return La carte gagnante
     */
    public static Card DoppelgangerVsCard(Card carte1, Card carte2, Plateau plateau) {
        // si les deux cartes sont des doppelgangers alors on compare les valeurs des cartes
        if (carte1.getFaction().equals("Doppelganger")) {
            if (carte2.getFaction().equals("Doppelganger")) {
                return determinerCarteGagnante(carte1, carte2, plateau);

            } else { // si la carte 1 est doppelganger et la carte 2 est une autre carte alors la carte 1 gagne
                Player Adversaire = plateau.getJoueurNonCourant();
                if(Adversaire == plateau.getJoueur1()){ // leader est joueur1
                    if(plateau.getCarteJoueur1().getFaction().equals("Doppelganger")){ //si le leader a jouer doppelganger alors il gagne le trick
                        return plateau.getCarteJoueur1();
                    }else{ // si ce n'est pas le leader qui a jouer doppelganger alors on compare les valeurs des cartes
                        return compareCard(carte1, carte2, plateau);
                    }

                }else{ // si l'adversaire est le joueur 2
                    if(plateau.getCarteJoueur2().getFaction().equals("Doppelganger")) { //si le leader a jouer doppelganger alors il gagne le trick
                        return plateau.getCarteJoueur2();
                    }else{
                        return compareCard(carte1, carte2, plateau);
                    }
                }
            }
        } else { // si la carte 1 n'est pas un doppelganger
            if (carte2.getFaction().equals("Doppelganger")) { // carte 2 est un doppelganger
                Player Adversaire = plateau.getJoueurNonCourant();

                if(Adversaire == plateau.getJoueur1()){ // leader est joueur1
                    if(plateau.getCarteJoueur1().getFaction().equals("Doppelganger")){ //si le leader a jouer doppelganger alors il gagne le trick
                        return plateau.getCarteJoueur1();
                    }else{ // si ce n'est pas le leader qui a jouer doppelganger alors on compare les valeurs des cartes
                        return compareCard(carte1, carte2, plateau);
                    }
                }else{ // si l'adversaire est le joueur 2
                    if(plateau.getCarteJoueur2().getFaction().equals("Doppelganger")) { //si le leader a jouer doppelganger alors il gagne le trick
                        return plateau.getCarteJoueur2();
                    }else{
                        return compareCard(carte1, carte2, plateau);
                    }
                }
            } else {
                return cardVScard(carte1, carte2, plateau);
            }

        }
    }

    /**
     * Méthode pour déterminer quelle carte l'emporte entre deux cartes sans s'occuper des regles speciaux.
     *
     * @param carte1
     * @param carte2
     * @param plateau Le plateau de jeu.
     * @return carte1 si faction de carte2 est differente sinon elle retourne la carte gagnante
     */
    public static Card cardVScard(Card carte1, Card carte2, Plateau plateau) {
        if (!(carte2.getFaction().equals(carte1.getFaction()))) {
            if (plateau.estLeader()) {
                return plateau.getCarteJoueur1();
            } else {
                return plateau.getCarteJoueur2();
            }
        } else {
            return determinerCarteGagnante(carte1, carte2, plateau);
        }
    }

    /**
     * Méthode pour déterminer quelle carte l'emporte entre un Gobelin et un Chevalier.
     * @return La carte gagnante.
     */
    public static Card GobelinVsKnight(Card carte1, Card carte2) {

        if (carte1.getFaction().equals("Goblins") && carte2.getFaction().equals("Knights")) {
            return carte2; // Le Chevalier bat toujours le Gobelin
        } else {
            return carte1; // Le Chevalier bat toujours le Gobelin
        }
    }

    /**
     * Méthode pour déterminer quelle carte l'emporte entre deux cartes de même faction selon la valeur des cartes.
     *
     * @param carte1 La première carte.
     * @param carte2 La deuxième carte.
     * @param plateau Le plateau de jeu.
     * @return La carte gagnante ou null en cas d'égalité.
     */
    public static Card compareCard(Card carte1, Card carte2 , Plateau plateau){
        if(carte1.getValeur() > carte2.getValeur()){
            return carte1;
        }else if(carte1.getValeur() < carte2.getValeur()){
            return carte2;
        }else{
            if(plateau.estLeader()){
                return plateau.getCarteJoueur1();
            }else{
                return plateau.getCarteJoueur2();
            }
        }

    }


    /**
     * Méthode pour déterminer quelle carte l'emporte entre deux cartes de meme faction selon la valeur des cartes.
     *
     * @param carte1
     * @param carte2
     * @return La carte gagnante
     */
    public static Card determinerCarteGagnante(Card carte1, Card carte2, Plateau plateau) {
        if (carte1.getFaction().equals(carte2.getFaction())) {
            return compareCard(carte1, carte2, plateau);
        } else {
            if (plateau.estLeader()) {
                return plateau.getCarteJoueur1();
            } else {
                return plateau.getCarteJoueur2();
            }
        }
    }


    /**
     * Méthode pour déterminer le gagnant d'une manche.
     *
     * @param joueur1      Le premier joueur.
     * @param joueur2      Le deuxième joueur.
     * @param carteJoueur1 La carte jouée par le premier joueur.
     * @param carteJoueur2 La carte jouée par le deuxième joueur.
     * @return Le joueur gagnant ou null en cas d'égalité.
     */
    public static Player determinerGagnantManche(Player joueur1, Player joueur2, Card carteJoueur1, Card carteJoueur2, Plateau plateau) {
        Card carteGagnante = carteGagnante(carteJoueur1, carteJoueur2, plateau);
        if (carteJoueur1.equals(carteJoueur2)){
            return plateau.getJoueurNonCourant();
        }
        if (carteGagnante == carteJoueur2) {
            return joueur2;
        } else if (carteGagnante == carteJoueur1) {
            return joueur1;
        } else {
            return null; // En cas d'égalité
        }

        // ajouter une condition lorsque les deux cartes sont identiques gobelin zero il faut retourner le leader
    }


    /**
     * Méthode pour déterminer quelles cartes le deuxième joueur peut jouer en fonction de la carte jouée par le premier joueur.
     *
     * @param carteAdversaire La carte jouée par l'adversaire.
     * @param mainJoueur      La main du joueur.
     * @return La liste des cartes jouables par le joueur.
     */
    public static List<Card> cartesJouables(Card carteAdversaire, Hand mainJoueur) {
        List<Card> cartesJouables = new ArrayList<>();

        // Vérifier si le joueur possède une carte de la même faction que celle jouée par l'adversaire
        for (Card carte : mainJoueur.getAllCards()) {
            if ((carte.getFaction().equals(carteAdversaire.getFaction())) || (carte.getFaction().equals("Doppelganger"))) {
                cartesJouables.add(carte);
            }
        }

        // Si le joueur n'a pas de carte de la même faction, il peut jouer n'importe quelle carte
        if (cartesJouables.isEmpty()) {
            cartesJouables.addAll(mainJoueur.getAllCards());
        }
        return cartesJouables;
    }

    /**
     * Méthode pour déterminer quelles cartes sont gagnantes parmi les cartes jouables
     *utilise pour la distinction entre les cartes gagnantes et les cartes perdantes (Vue)
     *
     * @param carteAdversaire La carte jouée par l'adversaire.
     * @param carteJouable la liste des cartes jouables par le joueur
     * @param plateau Le plateau de jeu
     * @return La liste des cartes gagnantes
     */
    public static  List<Card> cartesJouablesGagnant(Card carteAdversaire, List<Card> carteJouable , Plateau plateau) {
        List<Card> cartesGagnates = new ArrayList<>();
        for (Card carte : carteJouable) {
            Card carteGangante = carteGagnante(carte, carteAdversaire , plateau);
            if(carteGangante == carte){
                cartesGagnates.add(carte);
            }
        }
        return cartesGagnates;
    }

    /**
     * Méthode pour déterminer quelles cartes sont perdantes parmi les cartes jouables
     * utilise pour la distinction entre les cartes gagnantes et les cartes perdantes (Vue)
     *
     * @param carteAdversaire La carte jouée par l'adversaire.
     * @param carteJouable la liste des cartes jouables par le joueur
     * @param plateau Le plateau de jeu
     * @return La liste des cartes perdantes
     */
    public static List<Card> cartesJouablesPerdant(Card carteAdversaire, List<Card> carteJouable , Plateau plateau) {
        List<Card> cartesPerdantes = new ArrayList<>();
        for (Card carte : carteJouable) {
            Card carteGangante = carteGagnante(carte, carteAdversaire , plateau);
            if(carteGangante != carte){
                cartesPerdantes.add(carte);
            }
        }
        return cartesPerdantes;
    }


    /**
     * Méthode pour déterminer le gagnant d'une partie.
     *
     * @param joueur1 Le premier joueur.
     * @param joueur2 Le deuxième joueur.
     * @return Le nom du joueur gagnant.
     */
    public static String determinerGagnantPartie(GeneralPlayer joueur1, GeneralPlayer joueur2) {
        PileDeScore pileDeScoreJoueur1 = joueur1.getPileDeScore();
        PileDeScore pileDeScoreJoueur2 = joueur2.getPileDeScore();

        // Initialiser un compteur pour chaque joueur pour suivre le nombre de factions qu'ils ont gagnées
        int factionsGagneesJoueur1 = 0;
        int factionsGagneesJoueur2 = 0;

        List<String> factions = Arrays.asList("Goblins", "Knights", "Undead", "Dwarves", "Doppelgangers");
        // Calcul du nombre de factions gagnées par chaque joueur
        for (String faction : factions) {
            List<Card> cartesJoueur1 = pileDeScoreJoueur1.getCardFaction(faction);
            List<Card> cartesJoueur2 = pileDeScoreJoueur2.getCardFaction(faction);

            if (cartesJoueur1.size() > cartesJoueur2.size()) {
                return joueur1.getName();
            } else if (cartesJoueur2.size() > cartesJoueur1.size()) {
                return joueur2.getName();
            } else {
                // Si les deux joueurs ont le même nombre de cartes, comparez les valeurs des cartes
                int valeurMaxJoueur1 = pileDeScoreJoueur1.maxValueOfFaction(faction);
                int valeurMaxJoueur2 = pileDeScoreJoueur2.maxValueOfFaction(faction);

                if (valeurMaxJoueur1 > valeurMaxJoueur2) {
                    factionsGagneesJoueur1++;
                } else if (valeurMaxJoueur2 > valeurMaxJoueur1) {
                    factionsGagneesJoueur2++;
                }
            }
        }

        // Déterminer le gagnant de la partie en fonction du nombre de factions remportées
        if (factionsGagneesJoueur1 > factionsGagneesJoueur2) {
            return joueur1.getName();
        } else {
            return joueur2.getName();
        }
    }


    /**
     * Méthode pour appliquer les règles spéciales des factions (1ère phase uniquement).
     *
     * @param trickWinner Le joueur remportant le tour.
     * @param //plateau Le plateau de jeu.
     */
    // Méthode pour appliquer les règles spéciales des factions (1er phase uniquement)
    // 1er phase si une carte de type undead etait jouer par l'un des joueur celui qui gagne le tour gagne les cartes undead (de lui meme et la carte de l'adversaire si elle est undead)
    public void applyUndeadRule(GeneralPlayer trickWinner, Card cardPlayer1, Card cardPlayer2, Defausse defausse) {

        // Vérifier si la carte jouée par l'un des joueur est de la faction Undead
        if (cardPlayer1.getFaction().equals("Undead")) {
            // Ajouter la carte à la pile de score du joueur qui a remporté le tour
            trickWinner.getPileDeScore().addCard(cardPlayer1);
        } else {
            // ajouter la carte à la défausse
            defausse.ajouterCarte(cardPlayer1);
        }
        if (cardPlayer2.getFaction().equals("Undead")) {
            // Ajouter la carte à la pile de score du joueur qui a remporté le tour
            trickWinner.getPileDeScore().addCard(cardPlayer2);
        } else {
            // ajouter la carte à la défausse
            defausse.ajouterCarte(cardPlayer2);
        }

    }

    /**
     * Méthode pour vérifier si deux cartes sont égales en terme de faction et de valeur.
     *
     * @param carteJoueur1 La première carte.
     * @param carteJoueur2 La deuxième carte.
     * @return true si les cartes sont égales, false sinon.
     */
    public boolean carteEgaux(Card carteJoueur1, Card carteJoueur2) {
        if (carteJoueur1.getFaction().equals(carteJoueur2.getFaction()) && carteJoueur1.getValeur() == carteJoueur2.getValeur()) {
            return true;
        }
        if ((carteJoueur1.getFaction().equals("Doppelganger") || carteJoueur2.getFaction().equals("Doppelganger")) && carteJoueur1.getValeur() == carteJoueur2.getValeur()) {
            return true;
        }
        return false;
    }

    /**
     * Méthode pour appliquer les règles spéciales des factions à une manche dans la 1ere phase.
     *
     * @param trickWinner Le joueur remportant le tour.
     * @param cardPlayer1 carte jouer par le joueur 1
     * @param cardPlayer2 carte jouer par le joueur 2
     * @param defausse defausse de jeu
     */
    public void applyFirstPhaseRules(GeneralPlayer trickWinner, Card cardPlayer1, Card cardPlayer2, Defausse defausse) {
        applyUndeadRule(trickWinner, cardPlayer1, cardPlayer2, defausse);
    }


    /**
     * Méthode pour appliquer les règles spéciales des factions à une manche dans la 1ere phase.
     *
     * @param trickwinner Le joueur remportant le tour.
     * @param trickLoser le joueur perdant le tour
     * @param trickWinnerCard carte du joueur gagnant le tour
     * @param trickLoserCard carte du joueur perdant
     */
    public void applySecondPhaseRules(GeneralPlayer trickwinner, GeneralPlayer trickLoser, Card trickWinnerCard, Card trickLoserCard) { //: le gagnant prends dans sa pile de score les deux cartes jouées dans le tour classique sinon
        ApplyDwarvesRules(trickwinner, trickLoser, trickWinnerCard, trickLoserCard);
    }


    public void ApplyDwarvesRules(GeneralPlayer trickwinner, GeneralPlayer trickLoser, Card trickWinnerCard, Card trickLoserCard) {
        // Vérifier si la carte jouée par le joueur perdant est de la faction Dwarves
        if (trickWinnerCard.getFaction().equals("Dwarves")) {
            // Ajouter la carte jouée par le joueur perdant à sa pile de score
            trickLoser.getPileDeScore().addCard(trickWinnerCard);
        } else { // si la carte jouée par le joueur perdant n'est pas de la faction Dwarves
            // Ajouter la carte jouée par le joueur perdant à la pile de score du joueur gagnant
            trickwinner.getPileDeScore().addCard(trickWinnerCard);
        }

        // Vérifier si la carte jouée par le joueur gagnant est de la faction Dwarves
        if (trickLoserCard.getFaction().equals("Dwarves")) {
            // Ajouter la carte jouée par le joueur gagnant à la pile de score du joueur perdant
            trickLoser.getPileDeScore().addCard(trickLoserCard);
        } else { // si la carte jouée par le joueur gagnant n'est pas de la faction Dwarves
            // Ajouter la carte jouée par le joueur gagnant à sa pile de score
            trickwinner.getPileDeScore().addCard(trickLoserCard);
        }
    }

    /**
     * methode pour changer le joueur qui tient le tour
     * @param p le plateau de jeu
     */
    public void switchJoueur(Plateau p) {
        if (p.joueurCourant == p.joueur1) {
            p.joueurCourant = p.joueur2;
        } else if (p.joueurCourant == p.joueur2) {
            p.joueurCourant = p.joueur1;
        }
    }

}
