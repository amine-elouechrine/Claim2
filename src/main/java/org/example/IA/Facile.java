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

    private Card jouerAvecSuiviFaction(Card carte_adversaire , Hand main) {
        Hand carteJouable = getCardsOfSameFaction(carte_adversaire.getFaction());

        if (carteJouable.isEmpty()) {
            return jouerCarteAleatoire(main);
        } else {
            return choisirCarteAleatoire(carteJouable);
        }
    }

    private Card jouerCarteAleatoire(Hand main) {
        int index = rand.nextInt(main.getAllCards().size());
        Card carte = main.getAllCards().get(index);
        System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
        return carte;
    }

    private Card choisirCarteAleatoire(Hand carteJouable) {
        int index = rand.nextInt(carteJouable.getAllCards().size());
        Card carte = carteJouable.getAllCards().get(index);
        System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
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
    public Card jouerCoupPhase2F(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        System.out.println(";;;''''''" + handScndPhase.size());
        if (suivre_faction) {
            return jouerAvecSuiviFaction(carte_adversaire , mainIA); 
        } else {
            return jouerCarteAleatoire(handScndPhase);
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


    @Override
    public Card jouerCarte(int indexCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'jouerCarte'");
    }
}
