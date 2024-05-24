package org.example.Modele;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Defausse;
import org.example.Modele.Player;

public class PlateauState {
    //private Card carteAffichee;
    private Card carteJoueur1;
    private Card carteJoueur2;
    //private Cards pioche;
    private Defausse defausse;
    private Player joueur1;
    private Player joueur2;
    private Player joueurCourant;
    private Boolean phase;
    //private int numberCardPlayed;

    public PlateauState(Card carteJoueur1, Card carteJoueur2,
                        Player joueur1, Player joueur2 , Player CurrentPlayer) {
        this.carteJoueur1 = carteJoueur1;
        this.carteJoueur2 = carteJoueur2;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.joueurCourant = joueur1 ;
        this.phase = false;
    }


    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    public Card getCarteJoueur2() {
        return carteJoueur2;
    }

    public Defausse getDefausse() {
        return defausse;
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
