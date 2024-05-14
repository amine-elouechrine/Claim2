package org.example.IA;

import org.example.Modele.*;

import java.util.ArrayList;
import java.util.List;

public class Intermediare extends IA {

     public Intermediare(){
          super("Intermediare");
     }

     public Card jouer_coup_phase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {

          if (suivre_faction) {
               //jouer la carte la plus petite plus grande que celle de l'adversaire
               List<Card> carteJouable = new ArrayList<>();
               carteJouable = getCardsOfSameFactionAs(carte_adversaire);

               if (carteJouable.isEmpty()) {
                    //Regarder si la carte jouer est un Gobelin
                    if (carte_adversaire.getFaction().equals("Goblins") && containsKnight()) {
                         //verifier si on a pas de gobelin avant de jouer le chevalier
                         List<Card> Chevalier = new ArrayList<>();
                         for (Card c : hand.getAllCards()) {
                              if (c.getFaction().equals("Chevalier")) {
                                   Chevalier.add(c);
                              }
                         }

                         Card Chevaliermin = Chevalier.get(0);
                         for (Card c : Chevalier) {
                              if (c.getValeur() < Chevaliermin.getValeur()) {
                                   Chevaliermin = c;
                              }
                              return Chevaliermin;
                              //JOUER LA CARTE IL MANQUE LA FONCTION JOUER CARTE
                              //il faut jouer le Chevaliermin
                         }
                    } else {
                         //Regarder si on a des Doppelgängers
                         for (Card c : hand.getAllCards()) {
                              if (c.getFaction().equals("Doppelganger")) {
                                   carteJouable.add(c);
                              }
                         }
                         if (!carteJouable.isEmpty()) {
                              //jouer la carte la plus petite des dopplegangers
                              return getLowestValueCard(carteJouable);
                              //jouer min
                              //System.out.println("jouer min ");
                         } else {
                              //Cas ou on pourrais jamais gagner la faction
                              //jouer mainIA.min_valeur() il manque la fonction jouer carte
                              return getLowestValueCard(hand.getAllCards());
                         }
                    }


               } else {
                    //Cards Main_backup = carteJouable;
                    Card reponse = getSmallestHigherCard(carte_adversaire,carteJouable);
                    if (reponse!=null&&reponse.getValeur() > carte_adversaire.getValeur()) {
                         //gagner la faction
                         return reponse;
                         //System.out.println("jouer la carte " + reponse.getValeur() + " de la faction " + reponse.getFaction() + " pour gagner la faction)");
                    } else if(reponse==null){
                         //jouer la carte la plus petite parcequ'on peux pas gagner la faction
                         return getLowestValueCard(carteJouable);
                         //System.out.println(carteJouable.getLowestValueCard().getFaction() + "-" + carteJouable.getLowestValueCard().getValeur());
                    }
               }
          } else {
               //je commence le tour
               //A modifier: je joue la carte la plus null
               //jouer  mainIA.min_valeur() il manque la fonction jouer carte
               return getLowestValueCard(hand.getAllCards());
               //System.out.println("La carte jouer est :" + mainIA.getLowestValueCard().getValeur() + " " + mainIA.getLowestValueCard().getFaction());
          }
          return null;
     }

     public Card jouer_coup_phase2(Hand mainI, boolean suivre_faction, Card carte_adversaire) {
          if (suivre_faction) {
               //l'adversaire a jouer une carte je dois jouer une carte de la meme faction
               //si la faction de la carte adversaire est un nain
               if (carte_adversaire.getFaction().equals("Nains")) {
                    //je dois jouer le plus petit nains possible pour gagner la faction
                    Cards carteJouable = new Cards();
                    for (Card c : handScndPhase.getAllCards()) {
                         if (c.getFaction().equals("Nains")) {
                              carteJouable.setCard(c);
                         }
                    }
                    if (carteJouable.min_valeur().getValeur() > carte_adversaire.getValeur()) {
                         //jouer la carte la plus grande
                         return carteJouable.max_valeur();
                         //System.out.println(carteJouable.max_valeur());
                    } else {
                         //jouer la carte la plus petite mais plus grande que le nain de l'adversaire
                         return carteJouable.getHighestCardSmallerThan(carte_adversaire);
                         //System.out.println( carteJouable.getHighestCardSmallerThan(carte_adversaire));
                    }
               } else {
                    //suivre la faction de l'adversaire
                    Hand cartesJouables = handScndPhase.getCardsOfSameFaction(carte_adversaire);
                    if (cartesJouables.isEmpty()) {
                         //si on a pas de carte de la meme faction
                         //jouer la carte la plus petite
                         return handScndPhase.getLowestValueCard();
                         //System.out.println(cartesJouables.getLowestValueCard());
                    } else {
                         //jouer la carte la plus petite plus grande que celle de l'adversaire de la meme faction
                         return cartesJouables.getSmallestHigherCard(carte_adversaire);
                         //System.out.println(cartesJouables.getLargestSmallerCard(carte_adversaire));
                    }
               }
          } else {
               //je commence le tour
               //A modifier: je joue la carte la plus null
               return handScndPhase.getLowestValueCard();
               //System.out.println(mainIA.getLowestValueCard());
          }
     }

     public static void main(String[] args) {;
          Cards pioche = new Cards();
          pioche.shuffle();
          Hand hand1 = pioche.getHandOf13Cards();
          Hand handIA =new Hand();
          Intermediare i = new Intermediare();
          i.hand=pioche.getHandOf13Cards();
          Card cartejouer = hand1.getRandomCard();
          i.hand.printHand();
          System.out.println("La carte jouer par le joueur 1 est : "+cartejouer.getFaction()+" "+cartejouer.getValeur());
          Card card=i.jouer_coup_phase1(handIA, true, cartejouer);
          System.out.println("La carte jouer par l'IA est : "+card.getFaction()+" "+card.getValeur());
     }

}