package org.example.IA;

import org.example.Modele.*;


import java.util.*;

public class IAMinMax {


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
        if (depth == 0 || node.plateau.estPartieTerminer()) {
            int evaluation = evaluer(node);
            Result result = new Result(evaluation, null);
            return result;
        }
        PlateauState savedState =new PlateauState(node.plateau);//=node.plateau.saveState(); // Sauvegarder l'état du plateau
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


    /**
     * Cette méthode détermine la meilleure carte à jouer pour l'IA en utilisant l'algorithme Minimax.
     * Elle évalue toutes les configurations possibles du plateau de jeu jusqu'au profondeur 13 (fin de l'arbres de recherche)
     * et retourne la carte qui maximise les chances de victoire pour l'IA.
     *
     * @param plateau Le nœud racine représentant l'état actuel du plateau de jeu.
     * @return La meilleure carte à jouer pour l'IA.
     */
    // carte jouer par l'ia (c'est la carte qui a le meilleur score) : (on peu passer soit node en paramettre soit plateau
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


    /**
     * Cette méthode calcule le nombre de cartes d'une faction donnée qui sont encore en jeu.
     *
     * @param plateau Le plateau de jeu actuel.
     * @param faction Le nom de la faction pour laquelle on veut calculer le nombre de cartes en jeu.
     * @return Le nombre de cartes de la faction spécifiée qui sont encore en jeu.
     */
    // nombre de carte qui sont en jeu d'un faction donner
    // il faut connaitre le nbr totale de cette faction , calculer le nbr de carte de cette faction qui sont dans la defausse
    // ==> le nbr de carte de cette faction qui sont en jeu = nbr totale - nbr defausse ;
    public static int nbrCarteEnJeuFaction(Plateau plateau, String faction){
        int nbrCarteTotFaction = Cards.getNbCarteFaction(faction);
        int nbrCarteDefausse = 0;
        // Vérifier si la défausse n'est pas null avant d'accéder à ses cartes
        if (plateau.getDefausse() != null) {
            for (Card card : plateau.getDefausse().getCartes()) {
                if (card.getFaction().equals(faction)) {
                    nbrCarteDefausse++;
                }
            }
        }else{
            //System.err.println("La défausse est null.");
        }

        return nbrCarteTotFaction - nbrCarteDefausse;
    }


    /**
     * Cette méthode détermine si l'IA a gagné une faction donnée en comparant le nombre de cartes de cette faction
     * dans la pile de score de l'IA avec le nombre total de cartes de cette faction encore en jeu.
     *
     * @param plateau Le plateau de jeu actuel.
     * @param faction Le nom de la faction pour laquelle on veut vérifier la victoire.
     * @return true si l'IA a gagné la faction, sinon false.
     */
    // est se qu'on a gagner une faction donner
    // il faut ajouter le faite que lorsque l'ia et l'adversaire on le meme nbr de carte il faux comarer la carte de plus haut valeur de l'ia avec celle de l'adversaire
    public static boolean gagnerFaction(Plateau plateau, String faction){
        int nbrCarteFactionEnJeu = nbrCarteEnJeuFaction(plateau , faction);
        // de l'ia  : il faut savoir si c'est le joueur1 ou le joueur2 qui est l'ia
        int nbCartePileDeScoreFaction = plateau.getJoueur1().getPileDeScore().getCardFaction(faction).size();
        // si les deux joueur on le meme nombre de carte de cette faction il faut comparer la carte de plus haute valeur
        if((nbCartePileDeScoreFaction * 2) == nbrCarteFactionEnJeu){
            int carteMaxIaFaction = plateau.getJoueur1().getPileDeScore().maxValueOfFaction(faction);
            int carteMaxAdversaireFaction = plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction);
            return carteMaxIaFaction > carteMaxAdversaireFaction;
        // si l'ia a plus de carte de cette faction que l'adversaire
        }else if((nbCartePileDeScoreFaction * 2) > nbrCarteFactionEnJeu){
            return true;
        }else{ // si l'ia a moins de carte de cette faction que l'adversaire
            return false;
        }
    }


    private static int evaluer1(Node node) {
        Plateau plateau = node.getPlateau();
        int scoreIa = 0;
        int nbrFactionGagner;
        int nbrCarteGagner = plateau.getJoueur1().getPileDeScore().getAllCards().size();

        // Liste des factions disponibles dans le jeu
        List<String> factions = Arrays.asList("Goblins", "Knight", "Undead", "Dwarves", "Doppelganger"); // Remplacer par les vraies factions du jeu

        // Calculer le score de l'IA pour chaque faction gagner par l'IA ajouter 100 pour le score
        for (String faction : factions) {
            // Vérifier si l'IA a gagné cette faction
            if (gagnerFaction(plateau, faction)) {
                // Ajouter 100 points pour la faction gagnée
                scoreIa += 100;
            }
        }

        // le nbr de carte gagner par l'ia ajoute au score
        return scoreIa + nbrCarteGagner;
    }

    private static int evaluer(Node node) {
        Plateau plateau = node.getPlateau();
        int scoreIa = 0;

        // Liste des factions disponibles dans le jeu
        List<String> factions = Arrays.asList("Goblins", "Knight", "Undead", "Dwarves", "Doppelganger"); // Remplacer par les vraies factions du jeu

        // Calculer le score de l'IA pour chaque faction gagner par l'IA ajouter 100 pour le score
        for (String faction : factions) {
            // Vérifier si l'IA a gagné cette faction
            if (gagnerFaction(plateau, faction)) {
                // Ajouter 100 points pour la faction gagnée
                scoreIa += 1;
            }
        }
        if(scoreIa >= 3){
            return 1;
        }else{
            return 0;
        }


    }



}
