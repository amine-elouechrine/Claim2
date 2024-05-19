package org.example.IA;

import org.example.Modele.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.IA.Node.printArbre;
import static org.example.Modele.ReglesDeJeu.carteGagnante;
import static org.example.Modele.ReglesDeJeu.getMaxCardValue;

public class ArbreJeu {
    // Récupère les cartes de la même faction à partir d'une liste donnée
    public static List<Card> filterCardsByFaction(List<Card> cards, String faction) {
        List<Card> filteredCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getFaction().equals(faction)) {
                filteredCards.add(card);
            }
        }
        return filteredCards;
    }

    public static int Difference_Faction(Node node){

        // Initialiser un compteur pour chaque joueur pour suivre le nombre de factions qu'ils ont gagnées
        int factionsGagneesIA = 0;
        int factionsGagneesAdversaire = 0;

        List<String> factions = Arrays.asList("Goblins", "Knights", "Undead", "Dwarves", "Doppelgangers");

        List<Card> cartesGagneIA=node.pileDeScoreIa.getAllCards();
        List<Card> cartesGagneAdversaire=node.pileDeScoreAdversaire.getAllCards();

        // Calcul du nombre de factions gagnées par chaque joueur
        for (String faction : factions) {
            List<Card> cartesIAFaction = filterCardsByFaction(cartesGagneIA, faction);
            List<Card> cartesAdversairesFaction = filterCardsByFaction(cartesGagneAdversaire, faction);

            if (cartesIAFaction.size() > cartesAdversairesFaction.size()) {
                factionsGagneesIA++;
            } else if (cartesAdversairesFaction.size() > cartesIAFaction.size()) {
                factionsGagneesAdversaire++;
            } else {
                // Si les deux joueurs ont le même nombre de cartes, comparez les valeurs des cartes
                int valeurMaxJoueur1 = getMaxCardValue(cartesIAFaction);
                int valeurMaxJoueur2 = getMaxCardValue(cartesAdversairesFaction);

                if (valeurMaxJoueur1 > valeurMaxJoueur2) {
                    factionsGagneesIA++;
                } else if (valeurMaxJoueur2 > valeurMaxJoueur1) {
                    factionsGagneesAdversaire++;
                }
            }
        }

        return factionsGagneesIA-factionsGagneesAdversaire;
    }

        public static int evaluer(Node node){
            if(node.CarteGagnante.equals(node.carteIA)) {
                node.pileDeScoreIa.addCard(node.carteIA);
                node.pileDeScoreIa.addCard(node.Adversaire);
            }else{
                node.pileDeScoreAdversaire.addCard(node.carteIA);
                node.pileDeScoreAdversaire.addCard(node.Adversaire);
            }
            return Difference_Faction(node);

        }



       public static void Construire_Arbre(Node node,int profondeur){
            //Evaluer le noeud
            if (profondeur == 0 || node.getHandIa().isEmpty() || node.getHandAdversaire().isEmpty()) {
                node.setScore(evaluer(node));
                return;
            }

            Hand enPremier;
            Hand enSecond;
            if(node.IsIaTurn){
                enPremier=new Hand(node.getHandIa());
                enSecond=new Hand(node.getHandAdversaire());
            }else{
                enPremier=new Hand(node.getHandAdversaire());
                enSecond=new Hand(node.getHandIa());
            }
            List<Node> tempEnfants = new ArrayList<>();
            int i=0;


            for(Card card: enPremier.getAllCards()){
                for(Card card2: enSecond.getAllCards()){


                    Card CarteGagnante= carteGagnante(card,card2);//determiner carte gagnante
                    if(CarteGagnante==null){//dans le cas ou c'est null alors on a eu gobolins 0 contre gobolins 0
                         CarteGagnante=card;
                    }


                    Hand HandGagnate=CarteGagnante.equals(card) ? new Hand(enPremier) : new Hand(enSecond);//determiner la main gagnante
                    Node copie;
                    Node enfant;


                    if(node.IsIaTurn){//si c'est l'ia qui commence
                        copie=node.clone(card,card2,node);//clone le noeud
                        copie.Remove_played_cards(card,card2,copie.Ia,copie.adversaire);
                        System.out.println(copie.Ia.size()+"-"+copie.adversaire.size());
                        enfant =new Node(copie.getHandIa(),copie.getHandAdversaire(),HandGagnate==node.Ia,card,card2,copie.pileDeScoreIa,copie.pileDeScoreAdversaire,copie.getScore());//enfant avec les cartes jouer puor chaque jouer
                    }else{
                        copie=node.clone(card2,card,node);
                        copie.Remove_played_cards(card,card2,copie.Ia,copie.adversaire);
                        enfant =new Node(copie.getHandIa(),copie.getHandAdversaire(),HandGagnate==node.Ia,card2,card,copie.pileDeScoreIa,copie.pileDeScoreAdversaire,copie.getScore());

                    }
                    enfant.CarteGagnante=CarteGagnante;
                    tempEnfants.add(enfant);
                    Construire_Arbre(enfant,profondeur-1);
                    i++;
                }

            }


            node.setEnfants(tempEnfants);
            //algorithme minmax a faire



        }


        public static Node construireArbreJeu(Hand ia,Hand adversaire, int profondeur) {
            Node racine = new Node(ia, adversaire,true,null,null,new PileDeScore(),new PileDeScore(),0);
            Construire_Arbre(racine, profondeur);
            return racine;
        }



        public static void main(String[] args) {
            Hand ia ;
            Hand adversaire ;
            Cards pioche = new Cards();
            pioche.addAllCards();
            pioche.shuffle();
            ia=pioche.getHandOf13Cards();
            adversaire=pioche.getHandOf13Cards();
            Node racine = construireArbreJeu(ia, adversaire, 2);
            System.out.println(racine.getEnfants().size());
            try (PrintWriter writer = new PrintWriter("output.txt", "UTF-8")) {
                printArbre(racine, 0, writer);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }

        }


}