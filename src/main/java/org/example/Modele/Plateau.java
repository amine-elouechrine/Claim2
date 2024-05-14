package org.example.Modele;

//import javax.smartcardio.Card;

import java.util.List;

/**
 * Cette classe représente le plateau de jeu dans lequel les cartes sont placées pendant la partie.
 * Elle contient la carte affichée, les cartes des joueurs, la pioche, la défausse et les joueurs.
 */

public class Plateau {
    Card carteAffichee; // Carte affichée sur le plateau
    Card carteJoueur1;
    Card carteJoueur2;
    Cards pioche;
    Defausse defausse; // Pile de cartes défaussées
    Player joueur1;
    Player joueur2;
    Player joueurCourant;
    Boolean phase; // true si on est dans la phase 1, false sinon
    int numberCardPlayed;

    /**
     * Constructeur de la classe Plateau.
     * Initialise la défausse .
     */
    public Plateau() {
        this.defausse = new Defausse();
        this.phase = true; // commancer a la phase 1
    }
    public void setPhase (boolean val){
        phase=val;
    }
    /**
     * passer a la phase 2.
     */
    public void switchPhase() {
        if (phase == true) {
            phase = !phase;
        } else {
            System.out.println("Vous etes deja dans la phase 2");
        }
    }

    public boolean getPhase() {
        return phase;
    }

    /**
     * verifie si c'est la fin de la phase ou pas
     *
     * @param phase
     * @return true si c'est la fin de la phase, false sinon
     */
    public boolean estFinPhase(boolean phase) {
        if (joueur1.isHandEmpty(phase) && joueur2.isHandEmpty(phase)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * verifier si c'est la fin du jeu
     *
     * @return true si c'est la fin du jeu, false sinon
     */
    public boolean isEndOfGame() {
        if (phase == false) { // si on est dans la 2eme phase
            return estFinPhase(getPhase());
        }
        return false;
    }

    /**
     * Renvoie la carte affichée sur le plateau.
     *
     * @return La carte affichée sur le plateau.
     */
    public Card getCarteAffichee() {
        return carteAffichee;
    }

    public void setCarteAffichee(Card carteAffichee) {
        this.carteAffichee = carteAffichee;
    }

    /**
     * Renvoie la carte du joueur 1.
     *
     * @return La carte du joueur 1.
     */
    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    public void setCarteJoueur1(Card carteJoueur1) {
        this.carteJoueur1 = carteJoueur1;
    }

    /**
     * Renvoie la carte du joueur 2.
     *
     * @return La carte du joueur 2.
     */
    public Card getCarteJoueur2() {
        return carteJoueur2;
    }

    public void setCarteJoueur2(Card carteJoueur2) {
        this.carteJoueur2 = carteJoueur2;
    }

    /**
     * Renvoie le joueur courant.
     *
     * @return Le joueur courant.
     */
    public Player getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Player joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    /**
     * Renvoie le joueur 1.
     *
     * @return Le joueur 1.
     */
    public Player getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Player joueur1) {
        this.joueur1 = joueur1;
    }

    /**
     * Renvoie le joueur 2.
     *
     * @return Le joueur 2.
     */
    public Player getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Player joueur2) {
        this.joueur2 = joueur2;
    }

    /**
     * Ajoute une carte à la défausse.
     *
     * @param card La carte à ajouter à la défausse.
     */
    public void addToDefausse(Card card) {
        defausse.ajouterCarte(card);
    }



    /**
     * Renvoie la liste des cartes dans la défausse.
     *
     * @return La liste des cartes dans la défausse.
     */
    public Defausse getDefausse() {
        return defausse;
    }
    public void setDefausse(Defausse defausse) {
        this.defausse = defausse;
    }

    /**
     * Renvoie la pioche.
     *
     * @return
     */
    public Cards getPioche() {
        return pioche;
    }

    public void setPioche(Cards pioche) {
        this.pioche = pioche;
    }

    /**
     *
     */
    public void initialiserJeu() {
        //creation des cartes de jeu et shuffle (pioche)
        pioche = new Cards();
        pioche.addAllCards();
        pioche.shuffle();
        //creation & initialiser les mains 
        Hand mainJoueur1 = pioche.getHandOf13Cards();
        Hand mainJoueur2 = pioche.getHandOf13Cards();
        //creation des joueurs
        joueur1 = new Player("Joueur 1");
        joueur2 = new Player("Joueur 2");
        //initialiser les mains des joueurs
        joueur1.setHand(mainJoueur1);
        joueur2.setHand(mainJoueur2);

        // Init joueur courant
        joueurCourant = joueur1;
        //initialiser la carte affichee
        carteAffichee = pioche.getCard();
    }

    /**
     * Joue une carte de la main du joueur leader (qui commance le tour) et la retire de sa main.
     *
     * @param indexCard
     * @return La carte jouée.
     */
    public Card jouerCarte(int indexCard) {
        // jouer une carte quelconque de sa main
        return joueurCourant.jouerCarte(indexCard);
    }

    /**
     * verifierle joueur courant est le leader
     *
     * @return true si le joueur courant est le leader, false sinon
     */
    public boolean estLeader() {
        if (carteJoueur1 == null && carteJoueur2 == null) {
            return true;
        } else {
            return false;
        }
    }

    public void attribuerCarteFirstPhase(Card winningCard) {
        if (winningCard == carteJoueur1) {
            joueur1.getHandScndPhase().addCard(winningCard);
            joueur2.getHandScndPhase().addCard(pioche.getCard());
            joueurCourant = joueur1;
        } else {
            joueur2.getHandScndPhase().addCard(winningCard);
            joueur1.getHandScndPhase().addCard(pioche.getCard());
            joueurCourant = joueur2;
        }
    }

    public void attribuerCarteSecondPhase(Card winningCard, ReglesDeJeu r) {// on doit changer la fonction ApplyDwarveRule:c'est fait
        if (winningCard == carteJoueur1) {
            r.ApplyDwarvesRules(joueur1, joueur2, carteJoueur1, carteJoueur2);
        } else {
            r.ApplyDwarvesRules(joueur2, joueur1, carteJoueur2, carteJoueur1);
        }
    }

    public boolean coupJouable(List<Card> preselected, int indice, Hand hand) {
        return preselected.contains(hand.getCard(indice));
    }

    public Boolean estPhase1() {
        return !(joueur1.getHand().isEmpty() && joueur2.getHand().isEmpty());
    }

}