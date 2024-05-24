package org.example.IA;

import org.example.Modele.*;


import java.util.*;

public class IAMinMax {

    // Hach map  de Node qui stock tous les cofiguration tester (ou une noeud represente une configuration de jeu)
    private Map<Plateau, Integer> memo;  // memorisation des configurations deja calculer

    public IAMinMax() {
        memo = new HashMap<>();
    }

    public int minimax(Node node, int depth, boolean maximizingPlayer, int alpha, int beta) {
        // Vérifiez si l'état du plateau actuel a déjà été évalué
        if (memo.containsKey(node.plateau)) {
            return memo.get(node.plateau);
        }

        // Cas de base: si la profondeur est 0 ou si le jeu est terminé
        if (depth == 0 || node.plateau.isEndOfGame()) {
            int evaluation = evaluer(node);
            memo.put(node.plateau, evaluation);
            return evaluation;
        }

        if (maximizingPlayer) { // si c'est le tour de l'ia (verifier isIaTurn dans le noeud si c'est vrai
            int maxEval = Integer.MIN_VALUE;
            for (Node child : generateChildren(node)) {
                int eval = minimax(child, depth - 1, false , alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Élagage alpha
                }
            }
            node.setScore(maxEval); // Mettre à jour la valeur d'évaluation du node
            memo.put(node.plateau, maxEval);
            return maxEval;
        } else {   // si c'est le tour de l'adversaire (isIaTurn doit etre false)
            int minEval = Integer.MAX_VALUE;
            for (Node child : generateChildren(node)) {

                int eval = minimax(child, depth - 1, true, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Élagage bêta
                }
            }
            node.setScore(minEval); // Mettre à jour la valeur d'évaluation du node
            memo.put(node.plateau, minEval);
            return minEval;
        }
    }

    private List<Node> generateChildren(Node node) {
        List<Node> children = new ArrayList<>();
        GeneralPlayer currentPlayer = node.plateau.getJoueurCourant();
        GeneralPlayer opponentPlayer = node.plateau.getAdversaire();
        List<Card> currentPlayerHand = currentPlayer.getHandScndPhase().getAllCards();
        Hand opponentHand = opponentPlayer.getHandScndPhase();

        Plateau copie = node.plateau.clone();
        // Sauvegarder l'état actuel
        PlateauState savedState = copie.saveState();
        // Générer les états enfants en fonction des mouvements possibles
        for (Card cardi : currentPlayerHand) {
            List<Card> carteJouable = ReglesDeJeu.cartesJouables(cardi, opponentHand);
            for (Card cardj : carteJouable) {

                copie.jouerCarte2(cardi);
                copie.switchJoueur();
                copie.jouerCarte2(cardj);
                Card carteGagnante = ReglesDeJeu.carteGagnante(cardi, cardj, copie);
                copie.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                Node enfant = new Node(copie);
                children.add(enfant);

                // Restaurer l'état
                copie.restoreState(savedState);
            }
        }
        return children;
    }

    public void jouerCarteIa(Node node){
        Card bestCard = null;
        int bestScore = Integer.MIN_VALUE;
        List<Node> configs = generateChildren(node);
        
        // pour chaque coup (c : coups possibles)
        //jeu = jeu.clone();
        //score = minimax(c ...)
        //si score > bestScore
        //bestScore = score
        //bestCard = c
        //jouerCarte(bestCard)
    }

    private int evaluer1(Node node) {
        Plateau plateau = node.getPlateau();
        Player joueur1 = plateau.getJoueur1();
        Player joueur2 = plateau.getJoueur2();

        // Exemple d'évaluation : différence de score entre les joueurs
        int scoreJoueur1 = joueur1.getPileDeScore().getAllCards().size();
        int scoreJoueur2 = joueur2.getPileDeScore().getAllCards().size();

        return scoreJoueur1 - scoreJoueur2;
    }

    private int evaluer(Node node) {
        // Créer un générateur de nombres aléatoires
        Random random = new Random();
        int borneNegative = -1000;
        int bornePositive = 1000;

        // Générer une valeur aléatoire entre borneNegative et bornePositive
        int randomValue = random.nextInt(bornePositive - borneNegative + 1) + borneNegative;

        return randomValue;
    }


}
