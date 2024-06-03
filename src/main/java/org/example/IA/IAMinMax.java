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


    // les erreurs rencontrer !
    // il y a un decalage entre le score (l'evaluation) et l'affichage de maxEval !!!!
    // lors de la minimisation tous les coup en le meme minEval ??? -infinie !!!
    // lors de la minimisation les calculs sont incorrecte determiner coup possible ! parce que lors de la minimisation l'adversaire a deja jouer donc les calculs seront fais sur les tous les cartes qui sont dans la main de l'adversaire et non pas par rapport a la carte qu'il a jouer
    public static Result minimax(Node node, int depth, boolean maximizingPlayer, int alpha, int beta) {
        Result evalResult;

        // Incrémenter le compteur de nodes
        nodeCount++;

        // Cas de base: si la profondeur est 0 ou si le jeu est terminé
        if (node.plateau.isEndOfGame()) {
            int evaluation = evaluer1(node);
            System.out.println("Evaluation : " + evaluation);
            Result result = new Result(evaluation, node.plateau.getCarteJoueur1()); // coup jouer par l'ia
            System.out.println("carte jouer par l'ia" + node.plateau.getCarteJoueur1());
            return result;
        }
        PlateauState savedState =new PlateauState(node.plateau); // Sauvegarder l'état du plateau (clonage)
        if (maximizingPlayer) { // si c'est le tour de l'ia
            int maxEval = Integer.MIN_VALUE;
            Card bestCard = null;
            for (Coup c : Coup.determinerCoupsPossibles(node.plateau)) { // parcourir les coups possibles
                //joueur un coup sur le meme plateau
                node.plateau.jouerCarte(c.getCarte1());
                node.plateau.switchJoueur();
                node.plateau.jouerCarte(c.getCarte2());
                Card carteGagnante = ReglesDeJeu.carteGagnante(c.getCarte1(), c.getCarte2(), node.plateau);
                node.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());

                //creer un nouveau noeud avec le plateau apres avoir jouer
                Node child = new Node(node.plateau , c.getCarte1()); // cree un nouveau noeud avec le plateau apres avoir jouer et la carte jouer par l'ia
                if(child.plateau.getJoueurCourant().getName().equals("MinMax")){
                    evalResult = minimax(child, depth - 1, true , alpha, beta );
                }else{
                    evalResult = minimax(child, depth - 1, false , alpha, beta );
                }

                //restaurer le plateau
                node.plateau.restoreState(savedState);
                //maxEval = Math.max(maxEval, eval);
                if (evalResult.score > maxEval) {
                    maxEval = evalResult.score;
                    bestCard = c.getCarte1(); // La carte jouée par l'IA
                    System.out.println("bestCard : " + bestCard);
                    System.out.println("maxEval : " + maxEval);
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
            return result;
        } else {   // lorsqu'on minimise lors du premiere appel c'est que l'adversaire a deja jouer don il faut ajouter la carte jouer par l'adversaire

                    // pour resoudre le probleme de la minimisation il faut mettre les cartes 1 et 2 a null apres chaque tour
                    // pour puisse faire une condition sur la carte jouer par l'adversaire et faire les coup possible que par rapport a cette carte


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
                Node child = new Node(node.plateau , c.getCarte2()); // lorsqu'on est minimizant c'est le l'adversaire qui commence a jouer donc l'ia a la carte 2
                if(child.plateau.getJoueurCourant().getName().equals("MinMax")){
                    evalResult = minimax(child, depth - 1, true , alpha, beta );
                }else{
                    evalResult = minimax(child, depth - 1, false , alpha, beta );
                }
                //restaurer le plateau
                node.plateau.restoreState(savedState);
                //minEval = Math.min(minEval, eval);
                if (evalResult.score < minEval) {
                    minEval = evalResult.score;
                    bestCard = c.getCarte2(); // La carte jouée par l'IA, même si c'est un noeud adversaire pour la consistance
                    System.out.println("bestCard : " + bestCard);
                    System.out.println("minEval : " + minEval);
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
            return result;
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

    // ==> pour apeller l'algo min max il faut a la fin de chaque tour remettre la carte de joueur1 et la carte de joueur2 a null !!!
    public static Card carteJouerIa(Plateau plateau){
        Node racine = new Node(plateau);
        Node nodeRacine = racine.clone();
        Result result;


        // si MinMax est leader
        if(nodeRacine.plateau.estLeader()){
            // Appel de l'algorithme Minimax pour évaluer le meilleur coup
            result = minimax(nodeRacine, 13, true,Integer.MIN_VALUE , Integer.MAX_VALUE);
            System.out.println("maximizing .......");
            return result.coup;
        }else{
            // Appel de l'algorithme Minimax pour évaluer le meilleur coup
            result = minimax(nodeRacine, 13, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("minimizing .......");
            return result.coup;
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
            System.err.println("La défausse est null.");
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
        // nbr de carte de cette faction qui sont en jeu de l'ia
        int nbrCarteFactionIa = plateau.getJoueur1().getPileDeScore().getCardFaction(faction).size();
        // nbr de carte de cette faction qui sont en jeu de l'autre joueur
        int nbrCarteFactionAdversaire = plateau.getJoueur2().getPileDeScore().getCardFaction(faction).size();

        if(nbrCarteFactionIa > nbrCarteFactionAdversaire) {
            return true;
        }else if(nbrCarteFactionIa < nbrCarteFactionAdversaire) {
            return false;
        }else{
            int carteMaxIaFaction = plateau.getJoueur1().getPileDeScore().maxValueOfFaction(faction);
            int carteMaxAdversaireFaction = plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction);
            return carteMaxIaFaction > carteMaxAdversaireFaction;
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
