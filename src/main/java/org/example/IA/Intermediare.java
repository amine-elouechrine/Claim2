/*package org.example.IA;

import org.example.Modele.*;

import java.util.List;

public class Intermediare extends IA {

     public Intermediare(){
          super("Intermediare");
     }

     public Card jouerCoupPhase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire,Plateau plateau) {
          if (suivre_faction) {
               return jouerAvecSuiviFaction(hand, carte_adversaire,plateau);
          } else {
               return getHighestValueCard(plateau);
          }
     }

     public Card getHighestValueCard(List<Card> cards) {
          Card highestCard = cards.get(0);
          for (Card card : cards) {
              if (card.getValeur() > highestCard.getValeur()) {
                  highestCard = card;
              }
          }
          return highestCard;
      }

     public Card jouerAvecSuiviFaction(Hand mainIA, Card carte_adversaire,Plateau plateau) {
        List<Card> cartesJouables = (List<Card>)getCardsOfSameFaction(carte_adversaire.getFaction());
      
          if (cartesJouables.isEmpty()) {
              return jouerSansCarteDeMemeFaction(mainIA, carte_adversaire);
          } else {
              return jouerAvecCarteDeMemeFaction(cartesJouables, carte_adversaire);
          }
     }

     private Card jouerAvecCarteDeMemeFaction(List<Card> cartesJouables, Card carte_adversaire) {
          Card reponse = getSmallestHigherCard(carte_adversaire, cartesJouables);
          if (reponse != null && reponse.getValeur() > carte_adversaire.getValeur()) {
              return reponse;
          } else {
              return getLowestValueCard(cartesJouables);
          }
      }

     private Card jouerSansCarteDeMemeFaction(Hand mainIA, Card carte_adversaire) {
          if (carte_adversaire.getFaction().equals("Goblins") && containsKnight()) {
              return jouerChevalier(mainIA);
          } else {
              return jouerDoppelgangerOuMin(mainIA);
          }
     }

     private Card jouerDoppelgangerOuMin(Hand mainIA) {
          Hand doppelgangers = getCardsOfSameFaction("Doppelganger");
          if (doppelgangers.isEmpty()) {
              return getLowestValueCard(hand.getAllCards());
          } else {
              return getLowestValueCard(hand.getAllCards());
          }
      }


     public Card jouerChevalier(Hand mainIA) {
          Hand chevaliers = getCardsOfSameFaction("Chevalier");
          return chevaliers.getMin();
     }


     public Card jouerCoupPhase2(Hand mainI, boolean suivre_faction, Card carte_adversaire) {
          if (suivre_faction) {
              return jouerAvecSuiviFactionPhase2(carte_adversaire);
          } else {
              return getHighestValueCard(handScndPhase.getAllCards());
          }
     }

     private Card jouerAvecSuiviFactionPhase2(Card carte_adversaire) {
          if (carte_adversaire.getFaction().equals("Nains")) {
              return jouerAvecNains(carte_adversaire);
          } else {
              return suivreFactionAdversaire(carte_adversaire);
          }
     }

     private Card suivreFactionAdversaire(Card carte_adversaire) {
          Hand cartesJouables = getCardsOfSameFaction(carte_adversaire.getFaction());
      
          if (cartesJouables.isEmpty()) {
              return getLowestValueCard((List<Card>)handScndPhase);
          } else {
               return cartesJouables.getSmallestHigherCard(carte_adversaire);
          }
      }


     private Card jouerAvecNains(Card carte_adversaire) {
          List<Card> carteJouable = getCardsOfSameFaction("Nains").getAllCards();
      
          if (getLowestValueCard(carteJouable).getValeur() > carte_adversaire.getValeur()) {
              return getHighestValueCard(carteJouable);
          } else {
              return getHighestCardSmallerThan(carte_adversaire, carteJouable);
          }
     }

     @Override
     public Card jouerCarte(int indexCard) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'jouerCarte'");
     }


    /* public static void main(String[] args) {;
          Cards pioche = new Cards();
          pioche.shuffle();
          Hand hand1 = pioche.getHandOf13Cards();
          Hand handIA =new Hand();
          Intermediare i = new Intermediare();
          i.hand=pioche.getHandOf13Cards();
          Card cartejouer = hand1.getRandomCard();
          i.hand.printHand();
          System.out.println("La carte jouer par le joueur 1 est : "+cartejouer.getFaction()+" "+cartejouer.getValeur());
          //Card card=i.jouer_coup_phase1(handIA, true, cartejouer);
          Card card=i.jouerCoupPhase1(handIA, true, cartejouer);
          System.out.println("La carte jouer par l'IA est : "+card.getFaction()+" "+card.getValeur());
     }

}*/