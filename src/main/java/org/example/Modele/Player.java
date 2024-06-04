package org.example.Modele;

import java.util.ArrayList;
import java.util.List;

public class Player extends GeneralPlayer {

    public Player(String Name) {
        super(Name);
    }

    public Player(Player other) {
        super(other.Name);
        this.handScndPhase = new Hand(other.handScndPhase);
        this.hand = new Hand(other.hand);
        this.pileDeScore = new PileDeScore(other.pileDeScore);
    }

    /**
     * Joue une carte de la main du joueur et la retire de sa main.
     * @param indexCard carte à jouer.
     * @return La carte jouée, ou null si la carte n'est pas dans la main du joueur.
     */

    public Card jouerCarte(int indexCard) {
        Card carte = hand.getCard(indexCard);
        // Vérifie si la carte est présente dans la main du joueur
        if (hand.contains(carte)) {
            // Retire la carte de la main du joueur
            hand.removeCard(carte);
            return carte;
        } else {
            System.out.println("La carte n'est pas dans la main du joueur.");
            // Si la carte n'est pas dans la main du joueur, retourne null
            return null;
        }

    }}







