package org.example.IA;

import org.example.Modele.*;


import java.util.*;

public class IAMinMax {

    // Hach map  de Node qui stock tous les cofiguration tester (ou une noeud represente une configuration de jeu)

    public IAMinMax() {
    }

    // Classe pour encapsuler le score et le coup
    public static class Result {
        int score;
        Card coup; //carte jouée par l'IA

        public Result(int score, Card coup) {
            this.score = score;
            this.coup = coup;
        }
    }

    private static int nodeCount = 0;      // Variable pour compter les noeuds visités

    public static Result minimax(Node node, int depth, boolean maximizingPlayer, int alpha, int beta) {


        // Incrémenter le compteur de nœuds
        nodeCount++;

        // Cas de base: si la profondeur est 0 ou si le jeu est terminé
        if (depth == 0 || node.plateau.isEndOfGame()) {
            int evaluation = evaluer(node);
            Result result = new Result(evaluation, null);
            return result;
        }
        PlateauState savedState =new PlateauState(node.plateau);
        if (maximizingPlayer) { // si c'est le tour de l'ia (verifier isIaTurn dans le noeud si c'est vrai
            int maxEval = Integer.MIN_VALUE;
            Card bestCard = null;
            for (Coup c : Coup.determinerCoupsPossibles(node.plateau)) {
                //joueur un coup sur le meme plateau
                node.plateau.jouerCarte(c.getCarte1());
                node.plateau.switchJoueur();
                node.plateau.jouerCarte(c.getCarte2());
                Card carteGagnante = ReglesDeJeu.carteGagnante(c.getCarte1(), c.getCarte2(), node.plateau);
                node.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                //creer un nouveau noeud avec le plateau apres avoir jouer
                Node child = new Node(node.plateau , c.getCarte1() );
                Result evalResult = minimax(child, depth - 1, false , alpha, beta );
                //restaurer le plateau
                node.plateau.restoreState(savedState);
                //maxEval = Math.max(maxEval, eval);
                if (evalResult.score > maxEval) {
                    maxEval = evalResult.score;
                    bestCard = c.getCarte1(); // La carte jouée par l'IA
                }
                alpha = Math.max(alpha, evalResult.score);
                if (beta <= alpha) {
                    break; // Élagage alpha
                }
            }
            node.setScore(maxEval); // Mettre à jour la valeur d'évaluation du node
            node.setCarteJoueeParIa(bestCard); // Mettre à jour la carte jouée par l'IA
            Result result = new Result(maxEval, bestCard);
            //return maxEval;
            return new Result(maxEval, bestCard);
        } else {   // si c'est le tour de l'adversaire (isIaTurn doit etre false)
            int minEval = Integer.MAX_VALUE;
            Card bestCard = null;
            for (Coup c : Coup.determinerCoupsPossibles(node.plateau)) {
                //joueur un coup sur le meme plateau
                node.plateau.jouerCarte(c.getCarte1());
                node.plateau.switchJoueur();
                node.plateau.jouerCarte(c.getCarte2());
                Card carteGagnante = ReglesDeJeu.carteGagnante(c.getCarte1(), c.getCarte2(), node.plateau);
                node.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                //creer un nouveau noeud avec le plateau apres avoir jouer
                Node child = new Node(node.plateau , c.getCarte1());

                Result evalResult = minimax(child, depth - 1, true, alpha, beta);
                //restaurer le plateau
                node.plateau.restoreState(savedState);
                //minEval = Math.min(minEval, eval);
                if (evalResult.score < minEval) {
                    minEval = evalResult.score;
                    bestCard = c.getCarte1(); // La carte jouée par l'IA, même si c'est un noeud adversaire pour la consistance
                }
                beta = Math.min(beta, evalResult.score);
                if (beta <= alpha) {
                    break; // Élagage bêta
                }
            }
            node.setScore(minEval); // Mettre à jour la valeur d'évaluation du node
            node.setCarteJoueeParIa(bestCard); // Mettre à jour la carte jouée par l'IA
            Result result = new Result(minEval, bestCard);
            //return minEval;
            return new Result(minEval, bestCard);
        }
    }

    public static Card carteJouerIa(Plateau plateau){
        Node racine = new Node(plateau);
        Node nodeRacine = racine.clone();
        Result result;
        if(nodeRacine.plateau.getJoueurCourant().getName().equals("MinMax")){
            // Appel de l'algorithme Minimax pour évaluer le meilleur coup
            result = minimax(nodeRacine, 13, true, Integer.MIN_VALUE , Integer.MAX_VALUE );
            return result.coup;
        }else{
            // Appel de l'algorithme Minimax pour évaluer le meilleur coup
            result = minimax(nodeRacine, 13, false, Integer.MIN_VALUE , Integer.MAX_VALUE );
            return result.coup;

            // si l'ia joue en deuxieme position a la fin du trick alors elle return null ! donc il faut faire une condition pour set le coup a la derniere carte
        }
    }

    // Méthode pour obtenir le nombre de nœuds
    public static int getNodeCount() {
        return nodeCount;
    }


    private static int evaluer(Node node) {
        // Créer un générateur de nombres aléatoires
        Random random = new Random();
        int borneNegative = -1000;
        int bornePositive = 1000;

        // Générer une valeur aléatoire entre borneNegative et bornePositive
        int randomValue = random.nextInt(bornePositive - borneNegative + 1) + borneNegative;

        return randomValue;
    }

    // nombre de carte qui sont en jeu d'un faction donner
    // il faut connaitre le nbr totale de cette faction , calculer le nbr de carte de cette faction qui sont dans la defausse
    // ==> le nbr de carte de cette faction qui sont en jeu = nbr totale - nbr defausse ;
    public int nbrCarteEnJeuFaction(Plateau plateau, String faction){
        int nbrCarteTotFaction = Cards.getNbCarteFaction(faction);
        int nbrCarteDefausse = 0;
        for (Card card : plateau.getDefausse().getCartes()) {
            if (card.getFaction().equals(faction)) {
                nbrCarteDefausse++;
            }
        }
        return nbrCarteTotFaction - nbrCarteDefausse;
    }

    // est se qu'on a gagner une faction donner
    public boolean gagnerFaction(Plateau plateau, String faction){
        int nbrCarteFactionEnJeu = nbrCarteEnJeuFaction(plateau , faction);
        // de l'ia  : il faut savoir si c'est le joueur1 ou le joueur2 qui est l'ia
        int nbCartePileScoreFaction = plateau.getJoueur1().getPileDeScore().getCardFaction(faction).size();
        return (nbCartePileScoreFaction*2) > nbrCarteFactionEnJeu;
    }

    /*private int evaluer1(Node node) {
        Plateau plateau = node.getPlateau();
        // Calculer le score de l'IA pour chaque faction gagner par l'IA ajouter 100 pour le score
        // et pour chaque faction gagner par l'adversaire soustraire 100
        // calculer le nbr de carte gagner par l'ia et l'ajouter au score
    }*/


}
