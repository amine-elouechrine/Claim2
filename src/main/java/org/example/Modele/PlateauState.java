package org.example.Modele;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Defausse;
import org.example.Modele.Player;

public class PlateauState {
    private Card carteJoueur1;
    private Card carteJoueur2;
    private Player joueur1;
    private Player joueur2;
    private Player joueurCourant;
    private Boolean phase;

    public PlateauState(Card carteJoueur1, Card carteJoueur2,
                        Player joueur1, Player joueur2 , Player CurrentPlayer) {
        this.carteJoueur1 = carteJoueur1;
        this.carteJoueur2 = carteJoueur2;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.joueurCourant = joueur1 ;
        this.phase = false;
    }

    // ajouter pioche et defausse
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
        this.phase = other.phase;
    }

    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    public Card getCarteJoueur2() {
        return carteJoueur2;
    }


    public Player getJoueur1() {
        return joueur1;
    }

    public Player getJoueur2() {
        return joueur2;
    }

    public Player getJoueurCourant() {
        return joueurCourant;
    }

    public Boolean getPhase() {
        return phase;
    }

}
