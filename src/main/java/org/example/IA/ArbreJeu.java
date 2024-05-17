package org.example.IA;

import org.example.Modele.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.Modele.ReglesDeJeu.carteGagnante;
import static org.example.Modele.ReglesDeJeu.getMaxCardValue;

public class ArbreJeu {

    public static int Difference_Faction(Node node){

        // Initialiser un compteur pour chaque joueur pour suivre le nombre de factions qu'ils ont gagnées
        int factionsGagneesIA = 0;
        int factionsGagneesAdversaire = 0;

        List<String> factions = Arrays.asList("Goblins", "Knights", "Undead", "Dwarves", "Doppelgangers");
        // Calcul du nombre de factions gagnées par chaque joueur
        for (String faction : factions) {
            List<Card> cartesIA = node.pileDeScoreIa.getCardFaction(faction);
            List<Card> cartesAdversaires = node.pileDeScoreAdversaire.getCardFaction(faction);

            if (cartesIA.size() > cartesAdversaires.size()) {
                factionsGagneesIA++;
            } else if (cartesAdversaires.size() > cartesIA.size()) {
                factionsGagneesAdversaire++;
            } else {
                // Si les deux joueurs ont le même nombre de cartes, comparez les valeurs des cartes
                int valeurMaxJoueur1 = getMaxCardValue(cartesIA);
                int valeurMaxJoueur2 = getMaxCardValue(cartesAdversaires);

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
                node.addScore(evaluer(node));
                return;
            }

            Hand enPremier;
            Hand enSecond;
            if(node.IsIaTurn){
                enPremier=node.getHandIa();
                enSecond=node.getHandAdversaire();
            }else{
                enPremier=node.getHandAdversaire();
                enSecond=node.getHandIa();
            }
            List<Node> tempEnfants = new ArrayList<>();
            int i=0;
            for(Card card: enPremier.getAllCards()){
                for(Card card2: enSecond.getAllCards()){
                    Card CarteGagnante= carteGagnante(card,card2);//determiner carte gagnante
                    if(CarteGagnante==null){//dans le cas ou c'est null alors on a eu gobolins 0 contre gobolins 0
                         CarteGagnante=card;
                    }
                    //System.out.println(i+"/"+card+"->"+card2+"="+CarteGagnante);
                    Hand HandGagnate=CarteGagnante.equals(card) ? enPremier : enSecond;//determiner la main gagnante
                    Node copie;
                    Node enfant;
                    if(enPremier==node.getHandIa()){//si c'est l'ia qui commence
                        copie=node.clone(card,card2);//clone le noeud
                        enfant =new Node(copie.getHandIa(),copie.getHandAdversaire(),HandGagnate==node.Ia,card,card2,copie.pileDeScoreIa,copie.pileDeScoreAdversaire,copie.getScore());//enfant avec les cartes jouer puor chaque jouer
                        enfant.CarteGagnante=CarteGagnante;

                    }else{
                        copie=node.clone(card2,card);
                        enfant =new Node(copie.getHandIa(),copie.getHandAdversaire(),HandGagnate==node.Ia,card2,card,copie.pileDeScoreIa,copie.pileDeScoreAdversaire,copie.getScore());
                    }
                    tempEnfants.add(enfant);
                    Construire_Arbre(enfant,profondeur-1);
                    i++;
                }

            }
            node.setEnfants(tempEnfants);
            //algorithme minmax a faire



        }

        /*public static void ConstruireArbre(Node node,int profondeur) {
            //Evaluer le noeud
            if (profondeur == 0 || node.getHandIa().isEmpty() || node.getHandAdversaire().isEmpty()) {
                node.setScore(evaluer(node));
                return;
            }
            //Générer les enfants
            Hand main= node.IsIaTurn ? node.getHandIa() : node.getHandAdversaire();
            List<Node> tempEnfants = new ArrayList<>();
            List<Card> mainCopy = new ArrayList<>(main.getAllCards());
            for(Card carte: mainCopy){
                Node copie= node.clone();
                jouerCarte(copie,carte,node.IsIaTurn);
                Node enfant=new Node(copie.getHandIa(),copie.getHandAdversaire(),!node.IsIaTurn);
                tempEnfants.add(enfant);
                ConstruireArbre(enfant,profondeur-1);
            }
            node.setEnfants(tempEnfants);
            //c'est a l'ia de jouer
            if(node.IsIaTurn){
                int max=Integer.MIN_VALUE;
                for(Node enfant: node.getEnfants()){
                    max=Math.max(max,enfant.getScore());//chercher le meuilleur coup puor l'ia
                }
                node.setScore(max);
            }else{//au joueur
                int min=Integer.MAX_VALUE;
                for(Node enfant: node.getEnfants()){
                    min=Math.min(min,enfant.getScore());//adversaire cherche le meuilleur coup pour lui
                }
                node.setScore(min);
            }

        }*/

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
            Node racine = construireArbreJeu(ia, adversaire, 1);
            //racine.afficherArbre("");
        }


}