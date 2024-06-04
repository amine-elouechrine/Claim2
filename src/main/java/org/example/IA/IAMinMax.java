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

    // les erreurs rencontrer !
    // il y a un decalage entre le score (l'evaluation) et l'affichage de maxEval
    // !!!!
    // lors de la minimisation tous les coup en le meme minEval ??? -infinie !!!
    // lors de la minimisation les calculs sont incorrecte determiner coup possible
    // ! parce que lors de la minimisation l'adversaire a deja jouer donc les
    // calculs seront fais sur les tous les cartes qui sont dans la main de
    // l'adversaire et non pas par rapport a la carte qu'il a jouer
    public static int minimax(Node node, int depth, int alpha, int beta) {
        int evalResult;

        // Incrémenter le compteur de nodes
        nodeCount++;

        // Cas de base: si la profondeur est 0 ou si le jeu est terminé
        if (node.plateau.estFinPartie()) {
            int evaluation = evaluer1(node);
            //System.out.println("Evaluation : " + evaluation);
            int result = evaluation;
            return result;
        }

        Player leader;
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
                /*
                 * System.out.println("\n\n");
                 * System.out.println("cartesJouables est vide.");
                 * System.out.println("Aucune carte jouable pour l'IA.");
                 * System.out.println("\n\n");
                 * return new Result(maxEval, bestCard);
                 */
            }
            List<Card> cartesJouablesCopy = new ArrayList<>(cartesJouables);
            for (Card c : cartesJouablesCopy) {
                node.plateau.jouerCarte(c);
                // System.out.println("carte jouer par l'ia : " + c + "au depth :" + depth);

                if (carteAdversaire != null) { // determiner le gagnant
                    Card carteGagnante = ReglesDeJeu.carteGagnante(node.plateau.getCarteJoueur1(),
                            node.plateau.getCarteJoueur2(), node.plateau);
                    node.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                    node.plateau.setCarteJoueur1(null);
                    node.plateau.setCarteJoueur2(null);

                } else {
                    node.plateau.switchJoueur(); // pour passer le tour a l'adversaire
                    // System.out.println("Tour après switchJoueur : " + (node.plateau.estTourIa() ?
                    // "IA" : "Adversaire"));
                }

                // creer un nouveau noeud avec le plateau apres avoir jouer
                Node child = new Node(node.plateau);
                child.setCarteJoueeParIa(c);
                evalResult = minimax(child, depth - 1, alpha, beta);
                // node.plateau.restoreState(savedState);

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

                /*
                 * System.out.println("\n\n");
                 * System.out.println("cartesJouables est vide.");
                 * System.out.println("Aucune carte jouable pour l'IA.");
                 * System.out.println("\n\n");
                 * return new Result(minEval, bestCard);
                 */
            }
            List<Card> cartesJouablesCopy = new ArrayList<>(cartesJouables);
            for (Card c : cartesJouablesCopy) {
                node.plateau.jouerCarte(c);
                // System.out.println("carte jouer par l'adversiare : " + c + "au depth :" +
                // depth);

                if (carteAdversaire != null) { // determiner le gagnant
                    Card carteGagnante = ReglesDeJeu.carteGagnante(node.plateau.getCarteJoueur1(),
                            node.plateau.getCarteJoueur2(), node.plateau);
                    node.plateau.attribuerCarteSecondPhase(carteGagnante, new ReglesDeJeu());
                    node.plateau.setCarteJoueur1(null);
                    node.plateau.setCarteJoueur2(null);
                } else {
                    node.plateau.switchJoueur(); // pour passer le tour a l'adversaire
                    // System.out.println("Tour après switchJoueur : " + (node.plateau.estTourIa() ?
                    // "IA" : "Adversaire"));
                }

                // creer un nouveau noeud avec le plateau apres avoir jouer
                Node child = new Node(node.plateau);

                evalResult = minimax(child, depth - 1, alpha, beta);
                // node.plateau.restoreState(savedState);

                if (evalResult < minEval) {
                    minEval = evalResult;
                    // bestCard = evalResult.carteIa; // carte jouer par l'adversaire
                    // System.out.println("bestCard Adversaire: " + bestCard);
                    // System.out.println("minEval : " + minEval);
                }

                beta = Math.min(beta, evalResult);
                node.plateau.restoreState(savedState);
                if (beta <= alpha) {
                    break; // Élagage bêta
                }
            }
            carteAdversaire = null;

            // System.out.println("minEval : " + minEval + " bestCard is : " + bestCard);

            return minEval;
        }
    }

    /**
     * Cette méthode détermine la meilleure carte à jouer pour l'IA en utilisant
     * l'algorithme Minimax.
     * Elle évalue toutes les configurations possibles du plateau de jeu jusqu'au
     * profondeur 13 (fin de l'arbres de recherche)
     * et retourne la carte qui maximise les chances de victoire pour l'IA.
     *
     * @param plateau Le nœud racine représentant l'état actuel du plateau de jeu.
     * @return La meilleure carte à jouer pour l'IA.
     */
    // carte jouer par l'ia (c'est la carte qui a le meilleur score) : (on peu
    // passer soit node en paramettre soit plateau

    // ==> pour apeller l'algo min max il faut a la fin de chaque tour remettre la
    // carte de joueur1 et la carte de joueur2 a null !!!
    public static Card carteJouerIa(Plateau plateau, Boolean est1) {
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
        if (cartesJouablesCopy.isEmpty()) {
            System.out.println("wwww");
            System.exit(result);
        }

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

        System.out.println("carte jouer par l'ia : " + bestCard);
        System.out.println("score : " + result);
        return bestCard;
    }

    // Méthode pour obtenir le nombre de nœuds
    public static int getNodeCount() {
        return nodeCount;
    }

    /**
     * Cette méthode calcule le nombre de cartes d'une faction donnée qui sont
     * encore en jeu.
     *
     * @param plateau Le plateau de jeu actuel.
     * @param faction Le nom de la faction pour laquelle on veut calculer le nombre
     *                de cartes en jeu.
     * @return Le nombre de cartes de la faction spécifiée qui sont encore en jeu.
     */
    // nombre de carte qui sont en jeu d'un faction donner
    // il faut connaitre le nbr totale de cette faction , calculer le nbr de carte
    // de cette faction qui sont dans la defausse
    // ==> le nbr de carte de cette faction qui sont en jeu = nbr totale - nbr
    // defausse ;
    public static int nbrCarteEnJeuFaction(Plateau plateau, String faction) {
        int nbrCarteTotFaction = Cards.getNbCarteFaction(faction);
        int nbrCarteDefausse = 0;
        // Vérifier si la défausse n'est pas null avant d'accéder à ses cartes
        if (plateau.getDefausse() != null) {
            for (Card card : plateau.getDefausse().getCartes()) {
                if (card.getFaction().equals(faction)) {
                    nbrCarteDefausse++;
                }
            }
        } else {
            // System.err.println("La défausse est null.");
        }

        return nbrCarteTotFaction - nbrCarteDefausse;
    }

    /**
     * Cette méthode détermine si l'IA a gagné une faction donnée en comparant le
     * nombre de cartes de cette faction
     * dans la pile de score de l'IA avec le nombre total de cartes de cette faction
     * encore en jeu.
     *
     * @param plateau Le plateau de jeu actuel.
     * @param faction Le nom de la faction pour laquelle on veut vérifier la
     *                victoire.
     * @return true si l'IA a gagné la faction, sinon false.
     */
    // est se qu'on a gagner une faction donner
    // il faut ajouter le faite que lorsque l'ia et l'adversaire on le meme nbr de
    // carte il faux comarer la carte de plus haut valeur de l'ia avec celle de
    // l'adversaire
    public static boolean gagnerFaction(Plateau plateau, String faction) {
        // nbr de carte de cette faction qui sont en jeu de l'ia
        int nbrCarteFactionIa = plateau.getJoueur1().getName().equals("MinMax")?plateau.getJoueur1().getPileDeScore().getCardFaction(faction).size():plateau.getJoueur2().getPileDeScore().getCardFaction(faction).size();
        // nbr de carte de cette faction qui sont en jeu de l'autre joueur
        int nbrCarteFactionAdversaire = plateau.getJoueur2().getName().equals("MinMax")?plateau.getJoueur1().getPileDeScore().getCardFaction(faction).size():plateau.getJoueur2().getPileDeScore().getCardFaction(faction).size();

        if (nbrCarteFactionIa > nbrCarteFactionAdversaire) {
            return true;
        } else if (nbrCarteFactionIa < nbrCarteFactionAdversaire) {
            return false;
        } else {
            int carteMaxIaFaction = plateau.getJoueur1().getName().equals("MinMax")?plateau.getJoueur1().getPileDeScore().maxValueOfFaction(faction):plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction);
            int carteMaxAdversaireFaction = plateau.getJoueur1().getName().equals("MinMax")?plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction):plateau.getJoueur2().getPileDeScore().maxValueOfFaction(faction);
            return carteMaxIaFaction > carteMaxAdversaireFaction;
        }
    }

    private static int evaluer1(Node node) {
        Plateau plateau = node.getPlateau();
        int scoreIa = 0;
        int nbrFactionGagner;
        int nbrCarteGagner = plateau.getJoueur1().equals("MinMax")
                ? plateau.getJoueur1().getPileDeScore().getAllCards().size()
                : plateau.getJoueur2().getPileDeScore().getAllCards().size();

        // Liste des factions disponibles dans le jeu
        List<String> factions = Arrays.asList("Goblins", "Knight", "Undead", "Dwarves", "Doppelganger"); // Remplacer
        // par les
        // vraies
        // factions du
        // jeu

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
        // par les
        // vraies
        // factions du
        // jeu

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
