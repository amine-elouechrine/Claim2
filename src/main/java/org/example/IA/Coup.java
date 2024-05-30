package org.example.IA;

import org.example.Modele.*;

import java.util.ArrayList;
import java.util.List;

public class Coup {
    public Card carte1 ; //  carte jouer par le leader
    public Card carte2 ; //  carte jouer par l'adversaire
    public String leader ; //  nom de joueur leader

    public Coup(Card carte1, Card carte2, String leader) {
        this.carte1 = carte1;
        this.carte2 = carte2;
        this.leader = leader;
    }

    public Card getCarte1() {
        return carte1;
    }

    public Card getCarte2() {
        return carte2;
    }

    public String getLeader() {
        return leader;
    }

    public static List<Coup> determinerCoupsPossibles(Plateau plateau) {

        GeneralPlayer currentPlayer = plateau.getJoueurCourant();
        GeneralPlayer opponentPlayer = plateau.getAdversaire();
        List<Card> currentPlayerHand = currentPlayer.getHandScndPhase().getAllCards();
        Hand opponentHand = opponentPlayer.getHandScndPhase();

        List<Coup> coupsPossibles = new ArrayList<>();
        for (Card carteLeader : currentPlayerHand) {
            List<Card> carteJouable = ReglesDeJeu.cartesJouables(carteLeader, opponentHand);
            for (Card carteAdversaire : carteJouable) {
                coupsPossibles.add(new Coup(carteLeader, carteAdversaire, currentPlayer.getName()));
            }
        }
        return coupsPossibles;
    }


}
