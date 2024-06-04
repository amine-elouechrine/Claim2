package org.example.IA;

import org.example.Modele.*;

import java.util.*;

public class IAMinMax {

    public IAMinMax() {
    }

    // Classe pour encapsuler le score et le coup
    public static class Result {
        int score;
        Card carteIa; // carte jouée par l'IA

        public Result(int score, Card carteIa) {
            this.score = score;
            this.carteIa = carteIa;
        }
    }

    private static int nodeCount = 0; // Variable pour compter les noeuds visités

    public static int minimax(Node node, int depth, int alpha, int beta) {
        int evalResult;

        // Incrémenter le compteur de nodes
        nodeCount++;

        // Cas de base: si le jeu est terminé (pas de condition sur le profondeur parce qu'on arrive au profondeur 26)
        if (node.plateau.estFinPartie()) {
            return evaluer1(node);
        }

        PlateauState savedState = new PlateauState(node.plateau); // Sauvegarder l'état du plateau (clonage)
        List<Card> cartesJouables = new ArrayList<>();
        if (node.plateau.estTourIa()) { // si c'est le tour de l'ia maximiser
            int maxEval = Integer.MIN_VALUE;
            Card carteAdversaire = node.plateau.getCardAdversaire();

            if (carteAdversaire == null) {
                cartesJouables = node.plateau.getJoueurCourant().getHandScndPhase().getAllCards();
            } else {
                cartesJouables = ReglesDeJeu.cartesJouables(carteAdversaire,
                        node.plateau.getJoueurCourant().getHandScndPhase());
            }
            if (cartesJouables.isEmpty()) {
                node.plateau.switchJoueur();
                return minimax(node, depth, alpha, beta);
            }

            List<Card> cartesJouablesCopy = new ArrayList<>(cartesJouables);
            for (Card c : cartesJouablesCopy) {
                node.plateau.jouerCarte(c);

                if (carteAdversaire != null) { // determiner le gagnant
                    Card carteGagnante = ReglesDeJeu.carteGagnante(node.plateau.getCarteJoueur1(),
                            node.plateau.getCarteJoueur2(), node.plateau);
                    node.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                    node.plateau.setCarteJoueur1(null);
                    node.plateau.setCarteJoueur2(null);

                } else {
                    node.plateau.switchJoueur(); // pour passer le tour a l'adversaire
                    // System.out.println("Tour après switchJoueur : " + (node.plateau.estTourIa() ? "IA" : "Adversaire"));
                }

                // creer un nouveau noeud avec le plateau apres avoir jouer
                Node child = new Node(node.plateau);
                child.setCarteJoueeParIa(c);
                evalResult = minimax(child, depth - 1, alpha, beta);

                if (evalResult > maxEval) {
                    maxEval = evalResult;

                }
                alpha = Math.max(alpha, evalResult);
                node.plateau.restoreState(savedState);
                if (beta <= alpha) {
                    break; // Élagage alpha
                }
            }
            carteAdversaire = null;
            return maxEval;

        } else { // c'est le tour de l'adversaire
            int minEval = Integer.MAX_VALUE;
            Card carteAdversaire = node.plateau.getCardAdversaire();

            if (carteAdversaire == null) {
                cartesJouables = node.plateau.getJoueurCourant().getHandScndPhase().getAllCards();
            } else {
                cartesJouables = ReglesDeJeu.cartesJouables(carteAdversaire,
                        node.plateau.getJoueurCourant().getHandScndPhase());
            }

            if (cartesJouables.isEmpty()) {
                node.plateau.switchJoueur();
                return minimax(node, depth, alpha, beta);
            }

            List<Card> cartesJouablesCopy = new ArrayList<>(cartesJouables);
            for (Card c : cartesJouablesCopy) {
                node.plateau.jouerCarte(c);
                // System.out.println("carte jouer par l'adversiare : " + c + "au depth :" + depth);

                if (carteAdversaire != null) { // determiner le gagnant
                    Card carteGagnante = ReglesDeJeu.carteGagnante(node.plateau.getCarteJoueur1(),
                            node.plateau.getCarteJoueur2(), node.plateau);
                    node.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                    node.plateau.setCarteJoueur1(null);
                    node.plateau.setCarteJoueur2(null);
                } else {
                    node.plateau.switchJoueur(); // pour passer le tour a l'adversaire
                    // System.out.println("Tour après switchJoueur : " + (node.plateau.estTourIa()  "IA" : "Adversaire"));
                }

                // creer un nouveau noeud avec le plateau apres avoir jouer
                Node child = new Node(node.plateau);
                evalResult = minimax(child, depth - 1, alpha, beta);

                if (evalResult < minEval) {
                    minEval = evalResult;
                }

                beta = Math.min(beta, evalResult);
                node.plateau.restoreState(savedState);
                if (beta <= alpha) {
                    break; // Élagage bêta
                }
            }
            carteAdversaire = null;

            return minEval;
        }
    }


    public static Card carteJouerIa(Plateau plateau) {
        Node racine = new Node(plateau);
        Node nodeRacine = racine.clone();
        int result = Integer.MIN_VALUE;

        Card bestCard = null;

        PlateauState savedState = new PlateauState(plateau);

        Card carteAdversaire = plateau.getCardAdversaire();
        List<Card> cartesJouables = new ArrayList<>();

        if (carteAdversaire == null) {
            cartesJouables = plateau.getJoueurCourant().getHandScndPhase().getAllCards();
        } else {
            cartesJouables = ReglesDeJeu.cartesJouables(carteAdversaire,
                    plateau.getJoueurCourant().getHandScndPhase());
        }

        List<Card> cartesJouablesCopy = new ArrayList<>(cartesJouables);

        for (Card c : cartesJouablesCopy) {
            nodeRacine.plateau.jouerCarte(c);

            if (carteAdversaire != null) {
                Card carteGagnante = ReglesDeJeu.carteGagnante(nodeRacine.plateau.getCarteJoueur1(),
                        nodeRacine.plateau.getCarteJoueur2(), nodeRacine.plateau);
                nodeRacine.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                nodeRacine.plateau.setCarteJoueur1(null);
                nodeRacine.plateau.setCarteJoueur2(null);
            } else {
                nodeRacine.plateau.switchJoueur();
            }
            int tmp = minimax(nodeRacine, 25, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (result < tmp) {
                result = tmp;
                bestCard = c;
            }
            nodeRacine.plateau.restoreState(savedState);
        }
        carteAdversaire = null;

        //System.out.println("carte jouer par l'ia : " + bestCard);
        //System.out.println("score : " + result);
        return bestCard;
    }

    // Méthode pour obtenir le nombre de nœuds
    public static int getNodeCount() {
        return nodeCount;
    }

    public static boolean gagnerFaction(Plateau plateau, String faction) {
        // nbr de carte de cette faction qui sont en jeu de l'ia
        int nbrCarteFactionIa = plateau.getJoueur1().getName().equals("IA Difficile")?plateau.getJoueur1().getPileDeScore().getCardFaction(faction).size():plateau.getJoueur2().getPileDeScore().getCardFaction(faction).size();
        // nbr de carte de cette faction qui sont en jeu de l'autre joueur
        int nbrCarteFactionAdversaire = plateau.getJoueur2().getName().equals("IA Difficile")?plateau.getJoueur1().getPileDeScore().getCardFaction(faction).size():plateau.getJoueur2().getPileDeScore().getCardFaction(faction).size();

        if (nbrCarteFactionIa > nbrCarteFactionAdversaire) {
            return true;
        } else if (nbrCarteFactionIa < nbrCarteFactionAdversaire) {
            return false;
        } else {
            int carteMaxIaFaction = plateau.getJoueur1().getName().equals("IA Difficile")?plateau.getJoueur1().getPileDeScore().maxValueOfFaction(faction):plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction);
            int carteMaxAdversaireFaction = plateau.getJoueur1().getName().equals("IA Difficile")?plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction):plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction);
            return carteMaxIaFaction > carteMaxAdversaireFaction;
        }
    }

    private static int evaluer1(Node node) {
        Plateau plateau = node.getPlateau();
        int scoreIa = 0;
        int nbrFactionGagner;
        int nbrCarteGagner = plateau.getJoueur1().equals("IA Difficile")
                ? plateau.getJoueur1().getPileDeScore().getAllCards().size()
                : plateau.getJoueur2().getPileDeScore().getAllCards().size();

        // Liste des factions disponibles dans le jeu
        List<String> factions = Arrays.asList("Goblins", "Knight", "Undead", "Dwarves", "Doppelganger"); // Remplacer

        // Calculer le score de l'IA pour chaque faction gagner par l'IA ajouter 100
        // pour le score
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
        List<String> factions = Arrays.asList("Goblins", "Knight", "Undead", "Dwarves", "Doppelganger"); // Remplacer

        // Calculer le score de l'IA pour chaque faction gagner par l'IA ajouter 100
        // pour le score
        for (String faction : factions) {
            // Vérifier si l'IA a gagné cette faction
            if (gagnerFaction(plateau, faction)) {
                // Ajouter 100 points pour la faction gagnée
                scoreIa += 1;
            }
        }
        if (scoreIa >= 3) {
            return 1;
        } else {
            return 0;
        }

    }

}
