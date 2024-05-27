package org.example.Modele;

public class Player extends GeneralPlayer {

    public Player(String Name) {
        super(Name);
    }

    /**
     * Joue une carte de la main du joueur et la retire de sa main.
     * @param indexCard carte à jouer.
     * @return La carte jouée, ou null si la carte n'est pas dans la main du joueur.
     */

    /*public Card jouerCarte(int indexCard) {
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
    }*/

    /*public Card jouerCarte2(int indexCard) {
        Card carte = handScndPhase.getCard(indexCard);
        if (handScndPhase.contains(carte)) {
            // Retire la carte de la main du joueur
            handScndPhase.removeCard(carte);
            return carte;
        } else {
            System.out.println("La carte n'est pas dans la main du joueur.");
            // Si la carte n'est pas dans la main du joueur, retourne null
            return null;
        }
    }*/
}
