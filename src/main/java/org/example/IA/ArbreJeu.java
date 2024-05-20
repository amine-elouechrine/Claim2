package org.example.IA;

import org.example.Modele.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.Modele.ReglesDeJeu.carteGagnante;
import static org.example.Modele.ReglesDeJeu.getMaxCardValue;


public class ArbreJeu{
    Node node;

    public ArbreJeu(Node node){
        this.node = node;
    }

    public static List<Card> filterCardsByFaction(List<Card> cards, String faction) {
        List<Card> filteredCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getFaction().equals(faction)) {
                filteredCards.add(card);
            }
        }
        return filteredCards;
    }

    public List<Card> pileDeScoreCards(Player joueur){
        return joueur.getPileDeScore().getAllCards();
    }

    public static int evaluer(Node node){
        // Initialiser un compteur pour chaque joueur pour suivre le nombre de factions qu'ils ont gagnées
        int factionsGagneesIA = 0;
        int factionsGagneesAdversaire = 0;
        List<String> factions = Arrays.asList("Goblins", "Knights", "Undead", "Dwarves", "Doppelgangers");
        List<Card> cartesGagneIA=node.plateau.getJoueur1().getPileDeScore().getAllCards();
        List<Card> cartesGagneAdversaire=node.plateau.getJoueur2().getPileDeScore().getAllCards();
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


    public static void construireArbre(Node racine,int profondeur , boolean maximizingPlayer , int alpha, int beta){

        //cas de base
        if(profondeur==0 || racine.plateau.isEndOfGame()){
            racine.setScore(evaluer(racine));
            return;
        }

        List<Node> tempEnfants = new ArrayList<>();

        if(maximizingPlayer){
            int maxScore = Integer.MIN_VALUE;

            for(int i=0;i<racine.plateau.getJoueurCourant().getHandScndPhase().size();i++){
                Plateau copie = new Plateau(racine.plateau);
                Card cardi=racine.plateau.getJoueurCourant().getHandScndPhase().get(i);
                copie.jouerCarte(cardi);
                copie.switchJoueur();
    
                List<Card> carteJouable = cartesJouables2(cardi, racine);
                for(int j=0;j<carteJouable.size();j++){
    
                    Card cardj=carteJouable.get(j); 
                    copie.jouerCarte(cardj);
                    Card gagnant=carteGagnante(cardi,cardj , copie); 
    
                    System.out.println(copie.getJoueur1());
                    System.err.println(copie.getCarteJoueur1());
                    System.out.println(copie.getJoueur2());
                    System.err.println(copie.getCarteJoueur2());
                    System.err.println("****** " + gagnant + " ******");
                    System.err.println("-----------------------------------------------------");
    
                    System.out.println(j+"-"+i);
                    copie.attribuerCarteSecondPhase(gagnant,new ReglesDeJeu());
    
                    Node enfant = new Node(copie);
                    tempEnfants.add(enfant);
                    construireArbre(enfant,profondeur-1 , !maximizingPlayer, alpha, beta);
                    
                    maxScore = Math.max(maxScore, enfant.score);
                    alpha = Math.max(alpha, enfant.score);
                    
                    if (beta <= alpha) {
                        break; // Élagage beta
                    }
                }
    
            }
            racine.setScore(maxScore);
        
        }else{
            int minScore = Integer.MAX_VALUE;

            for (Card cardi : racine.plateau.getJoueurCourant().getHandScndPhase().getAllCards()) {
                Plateau copie = new Plateau(racine.plateau);
                copie.jouerCarte(cardi);
                copie.switchJoueur();

                List<Card> carteJouable = cartesJouables2(cardi, racine);
                for (Card cardj : carteJouable) {
                    Plateau copieDeux = new Plateau(copie);
                    copieDeux.jouerCarte(cardj);
                    Card gagnant = carteGagnante(cardi, cardj, copieDeux);

                    copieDeux.attribuerCarteSecondPhase(gagnant, new ReglesDeJeu());

                    Node enfant = new Node(copieDeux);
                    tempEnfants.add(enfant);

                    construireArbre(enfant, profondeur - 1, !maximizingPlayer, alpha, beta);

                    minScore = Math.min(minScore, enfant.score);
                    beta = Math.min(beta, enfant.score);

                    if (beta <= alpha) {
                        break; // Élagage alpha
                    }
                }
            }
            racine.setScore(minScore);
        } 
        System.err.println(tempEnfants.size());

        racine.setEnfants(tempEnfants);

    }

    public static List<Card> cartesJouables2(Card carteAd , Node racine){
        return ReglesDeJeu.cartesJouables(carteAd, racine.plateau.getJoueurCourant().getHandScndPhase());
    }

    public static List<Card> cartesJouables1(Card carteAd , Node racine){
        return ReglesDeJeu.cartesJouables(carteAd, racine.plateau.getJoueurCourant().getHand());
    }



    public Node construireArbreJeu(Plateau plateau, int profondeur , boolean maximizingPlayer , int alpha, int beta) {
        Node racine=new Node(plateau);
        construireArbre(racine,profondeur, maximizingPlayer, alpha, beta);
        return racine;
    }

    public static void main(String[] args) {
        Plateau plateau1 = new Plateau();
        plateau1.setPhase(false);//phase 2
        Player IA = new Player("IA");
        Player adversaire = new Player("Adversaire");
        plateau1.setJoueur1(IA);
        plateau1.setJoueur2(adversaire);
        plateau1.setJoueurCourant(IA);
        Cards pioche = new Cards();
        pioche.addAllCards();
        plateau1.getJoueur1().setHandScndPhase(pioche.getHandOf13Cards());//ia
        plateau1.getJoueur2().setHandScndPhase(pioche.getHandOf13Cards());//adversaire
        ArbreJeu arbreJeu = new ArbreJeu(new Node(plateau1));
        Node racine = arbreJeu.construireArbreJeu(plateau1, 2, true, Integer.MIN_VALUE, Integer.MAX_VALUE); // a l'apel initi alpha et beta doivent avoir ces valeurs
    }


}