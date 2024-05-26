package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Hand;
import org.example.Modele.Player;

import java.util.Random;

public class Facile extends IA {
    Player joueur1;
    Player IA;
    Random rand;

    public Facile() {
        super("Facile");
        rand = new Random();
    }


    // mainIA : doit etre hand 
    public Card joueCoupPhase1F(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        System.out.println(";;;''''''" + mainIA.size());
        if (suivre_faction) {
            return jouerAvecSuiviFaction(carte_adversaire, mainIA);
        } else {
            return jouerCarteAleatoire(mainIA);
        }
    }
  
    private Card jouerAvecSuiviFaction(Plateau plateau) {
        Hand carteJouable;

        carteJouable = getCardsOfSameFaction2(plateau.getJoueurCourant().getHand(),plateau.getCardAdversaire().getFaction());

        if (carteJouable.isEmpty()) {
            if(plateau.estPhase1_2()){
                return jouerCarteAleatoire(plateau.getJoueurCourant().getHand());
            } else 
                return jouerCarteAleatoire(plateau.getJoueurCourant().getHand());
        } else {
            return choisirCarteAleatoire(carteJouable);
        }
    }

    private Card jouerCarteAleatoire(Hand main) {
        int index = rand.nextInt(main.getAllCards().size());

        Card carte = main.getAllCards().get(index);
        //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
        return carte;
    }

    private Card choisirCarteAleatoire(Hand carteJouable) {
        int index = rand.nextInt(carteJouable.getAllCards().size());
        Card carte = carteJouable.getAllCards().get(index);
        //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
        return carte;
    }



    public Card jouer_coup_phase1_F(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        System.out.println(";;;''''''"+hand.size());
        if(suivre_faction){
            Hand carteJouable;
            carteJouable = getCardsOfSameFaction(carte_adversaire.getFaction());
            if(carteJouable.isEmpty()){
                //je peux pas suivre la faction de l'adversaire
                int index = rand.nextInt(hand.getAllCards().size());
                Card carte = hand.getAllCards().get(index);
                return carte;
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
            }else{
                int index = rand.nextInt(carteJouable.getAllCards().size());
                Card carte = carteJouable.getAllCards().get(index);
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
                return carte;
            }
        }else{
            int index = rand.nextInt(hand.getAllCards().size());
            Card carte = hand.getAllCards().get(index);
            //System.out.println("jouer carte par facile: " + carte.getFaction() + " " + carte.getValeur());
            return carte;
        }
    }

    // mainIA : doit etre handScndPhase
    public Card jouerCoupPhase2F(Plateau plateau) {
        //System.out.println(";;;''''''" + handScndPhase.size());
        if (!plateau.estLeader()) {
            return jouerAvecSuiviFaction(plateau);
        } else {
            return jouerCarteAleatoire(plateau.getJoueurCourant().getHand());
        }
    }


    public Card jouer_coup_phase2_F(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        System.out.println(";;;''''''"+handScndPhase.size());
        if(suivre_faction){
            Hand carteJouable;
            carteJouable = getCardsOfSameFaction(carte_adversaire.getFaction());
            if(carteJouable.isEmpty()){
                //je peux pas suivre la faction de l'adversaire
                int index = rand.nextInt(handScndPhase.getAllCards().size());
                Card carte = handScndPhase.getAllCards().get(index);
                return carte;
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
            }else{
                int index = rand.nextInt(carteJouable.getAllCards().size());
                Card carte = carteJouable.getAllCards().get(index);
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
                return carte;
            }
        }else{
            int index = rand.nextInt(handScndPhase.getAllCards().size());
            Card carte = handScndPhase.getAllCards().get(index);
            //System.out.println("jouer carte par facile: " + carte.getFaction() + " " + carte.getValeur());
            return carte;
        }
    }

    @Override
    public Card jouerCoupPhase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        return jouer_coup_phase1_F(mainIA, suivre_faction, carte_adversaire);
    }

    @Override
    public Card jouerCoupPhase2(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        return jouer_coup_phase2_F(mainIA, suivre_faction, carte_adversaire);
    }

    public static void main(String[] args) {
        Facile ia = new Facile();
        Plateau plateau = new Plateau();
        plateau.initialiserJeu();
        plateau.setCarteJoueur2(plateau.getJoueur2().getHand().getCard(0));
        Card card=ia.joueCoupPhase1F(plateau);
        System.out.println(card);
    }


    @Override
    public Card jouerCarte(int indexCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'jouerCarte'");
    }
}
