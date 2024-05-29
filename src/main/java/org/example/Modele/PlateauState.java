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
    Cards pioche;
    Defausse defausse;
    private Boolean phase;


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
        this.pioche = other.pioche ; // pas besoin de clonner la pioche parce quelle n'est pas utiliser lors du deuxieme phase
        this.defausse = new Defausse(other.defausse);
        this.phase = other.getPhase();
    }

    public Cards getPioche() {
        return pioche;
    }

    public Defausse getDefausse() {
        return defausse;
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
