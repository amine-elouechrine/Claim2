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
        // nbr de carte ia
        int nbrCarteIa = plateau.getJoueurCourant().getHandScndPhase().getAllCards().size();
        // nbr de carte adversaire
        int nbrCarteAdversaire = plateau.getAdversaire().getHandScndPhase().getAllCards().size();
        if(!(plateau.getAdversaire().getName().equals("MinMax"))){ // si l'ia n'est pas leader (c'est a dire l'humain qui a deja jouer)

        }
        if(plateau.getCarteJoueur1() == null && plateau.getCarteJoueur2() == null) {
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
        }else{ // si on fait l'appel a l'algo minMax avec minimisation l'aversaire a deja jouer donc pour la 1er simulation on doit suivre sa faction et sa carte
            List<Coup> coupsPossibles = new ArrayList<>();
            Card adversaireCard = plateau.getAdversaireCard();
            List<Card> carteJouable = ReglesDeJeu.cartesJouables(adversaireCard, plateau.getJoueurCourant().getHandScndPhase());
            for (Card carte : carteJouable) { // on doit determiner les cartes que l'ia peut jouer
                coupsPossibles.add(new Coup(carte, adversaireCard, plateau.getJoueurCourant().getName()));
            }
            // remettre la carte jouer par l'adversaire dans sa hand pour faire la simulation et le remettre comme joueur courant
            plateau.getAdversaire().getHandScndPhase().addCard(adversaireCard);
            plateau.switchJoueur();

            return coupsPossibles;

        }
    }

    /**
     * lorsque l'adversaire est le leader on doit determiner les cartes que l'ia peut jouer
     * donc dans l'algo de minmax lors de la minimisation on doit determiner les cartes que l'ia peut jouer la 1er fois
     * @return
     */
    public static List<Card> determinerCarteIaPossible(Plateau plateau){
        Card adversaireCard = plateau.getAdversaireCard();
        Hand IaHand = plateau.getJoueurCourant().getHandScndPhase();
        return ReglesDeJeu.cartesJouables(adversaireCard, IaHand);
    }



    public void afficherCoup(){
        System.out.println("Le leader est : " + leader);
        System.out.println("La carte du leader est : " + carte1);
        System.out.println("La carte de l'adversaire est : " + carte2);
    }

}
