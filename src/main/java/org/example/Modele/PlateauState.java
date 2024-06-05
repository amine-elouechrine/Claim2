package org.example.Modele;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Defausse;
import org.example.Modele.Player;

/**
 * @brief La classe PlateauState permet de sauvegarder l'état d'un plateau de jeu pour être récupéré plus tard.
 */
public class PlateauState {
    private Card carteJoueur1;
    private Card carteJoueur2;
    private Player joueur1;
    private Player joueur2;
    private Player joueurCourant;
    private Defausse defausse;
    private PileDeScore pileDeScore;
    private Boolean phase;


    /**
     * @brief Constructeur de copie pour créer un état de plateau à partir d'un autre plateau.
     *
     * @param other L'objet Plateau à partir duquel l'état doit être copié.
     */
    public PlateauState(Plateau other){
        this.carteJoueur1 = other.carteJoueur1 != null ? new Card(other.carteJoueur1) : null;
        this.carteJoueur2 = other.carteJoueur2 != null ? new Card(other.carteJoueur2) : null;
        this.joueur1 = other.joueur1 != null ? new Player(other.joueur1) : null;
        this.joueur2 = other.joueur2 != null ? new Player(other.joueur2) : null;
        if (other.joueurCourant == other.joueur1) {
            this.joueurCourant = this.joueur1;
        } else {
            this.joueurCourant = this.joueur2;
        }
        this.defausse = other.defausse != null ? new Defausse(other.defausse) : null;
        this.phase = other.phase;
    }


    /**
     * @brief Récupère la carte du joueur 1.
     *
     * @return La carte du joueur 1.
     */
    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    /**
     * @brief Récupère la carte du joueur 2.
     *
     * @return La carte du joueur 2.
     */
    public Card getCarteJoueur2() {
        return carteJoueur2;
    }

    /**
     * @brief Récupère le joueur 1.
     *
     * @return Le joueur 1.
     */
    public Player getJoueur1() {
        return joueur1;
    }

    /**
     * @brief Récupère le joueur 2.
     *
     * @return Le joueur 2.
     */
    public Player getJoueur2() {
        return joueur2;
    }

    /**
     * @brief Récupère le joueur courant.
     *
     * @return Le joueur courant.
     */
    public Player getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * @brief Récupère la phase actuelle du jeu.
     *
     * @return La phase actuelle du jeu.
     */
    public Boolean getPhase() {
        return phase;
    }

    /**
     * @brief Récupère la défausse.
     *
     * @return La défausse.
     */
    public Defausse getDefausse() {
        return defausse;
    }

}
