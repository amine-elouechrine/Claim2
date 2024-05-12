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
    Player joueurCourant ;

    /**
     * Constructeur de la classe Plateau.
     * Initialise les joueurs, la pioche et la défausse.
     * @param joueur1 Le joueur 1
     * @param joueur2 Le joueur 2
     * @param pioche La pioche de cartes
     */
    public Plateau(Player joueur1, Player joueur2, Cards pioche) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.pioche = pioche;
        this.defausse = new Defausse();
    }

    /**
     * Renvoie la carte affichée sur le plateau.
     * @return La carte affichée sur le plateau.
     */
    public Card getCarteAffichee() {
        return carteAffichee;
    }

    /**
     * Renvoie la carte du joueur 1.
     * @return La carte du joueur 1.
     */
    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    /**
     * Renvoie la carte du joueur 2.
     * @return La carte du joueur 2.
     */
    public Card getCarteJoueur2() {
        return carteJoueur2;
    }

    /**
     * Renvoie le joueur courant.
     * @return Le joueur courant.
     */
    public Player getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * Renvoie le joueur 1.
     * @return Le joueur 1.
     */
    public Player getJoueur1() {
        return joueur1;
    }

    /**
     * Renvoie le joueur 2.
     * @return Le joueur 2.
     */
    public Player getJoueur2() {
        return joueur2;
    }

    /**
     * Ajoute une carte à la défausse.
     * @param card La carte à ajouter à la défausse.
     */
    public void addToDefausse(Card card) {
        defausse.ajouterCarte(card);
    }

    /**
     * Renvoie la liste des cartes dans la défausse.
     * @return La liste des cartes dans la défausse.
     */
    public List<Card> getDefausse() {
        return defausse.getCartes();
    }
    public Cards getPioche() {return pioche;}

}