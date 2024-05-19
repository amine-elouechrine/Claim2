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

    public ArbreJeu(){
        this.node = new Node();
    }

    // Méthode pour filtrer les cartes par faction d'une liste de cards
    public static List<Card> filterCardsByFaction(List<Card> cards, String faction) {
        List<Card> filteredCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getFaction().equals(faction)) {//si la faction de la carte est égale à la faction donnée
                filteredCards.add(card);
            }
        }
        return filteredCards;
    }

    public static int evaluer1(Node node){

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
    public static void construireArbre(Node racine,int profondeur){

        //cas de base
        if(profondeur==0 || racine.plateau.isEndOfGame()){
            racine.setScore(evaluer1(racine));
            return;
        }
        List<Node> tempEnfants = new ArrayList<>();

        // 1er joueur joue n'importe qu'elle carte 
            // 2eme joueur a une liste de carte jouable (boucle de snd player doit etre sur les cartes jouables)

        //cas recursif
        for(int i=0;i<racine.plateau.getJoueurCourant().getHandScndPhase().size();i++){
            Plateau copie = new Plateau(racine.plateau);
            Card cardi=racine.plateau.getJoueurCourant().getHandScndPhase().get(i);
            copie.jouerCarte(cardi);;
            copie.switchJoueur();
            for(int j=0;j<racine.plateau.getJoueurCourant().getHandScndPhase().size();j++){

                Card cardj=racine.plateau.getJoueurCourant().getHandScndPhase().get(j);
                copie.jouerCarte(cardj);;

                Card gagnant=carteGagnante(copie);

                System.out.println(copie.getJoueur1());
                System.err.println(copie.getCarteJoueur1());
                System.out.println(copie.getJoueur2());
                System.err.println(copie.getCarteJoueur2());
                System.err.println("****** " + gagnant + " ******");
                System.err.println("-----------------------------------------------------");

                System.out.println(j+"-"+i);
                copie.attribuerCarteSecondPhase(gagnant,new ReglesDeJeu());
                //copie.switchJoueur();

                Node enfant = new Node(copie);
                tempEnfants.add(enfant);
                construireArbre(enfant,profondeur-1);

            }

        }
        System.err.println(tempEnfants.size());

        racine.setEnfants(tempEnfants);

    }

    public Node construireArbreJeu(Plateau plateau, int profondeur) {
        Node racine=new Node(plateau);
        construireArbre(racine,profondeur);
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
        Node racine = arbreJeu.construireArbreJeu(plateau1, 2);

    }
}



/*class MinimaxAI {
    private static final int MAX_DEPTH = 3;

    public static Card getBestMove(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        List<Card> possibleMoves = currentPlayer.getHand();
        Card bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        for (Card move : possibleMoves) {
            Game simulatedGame = simulateGameAfterMove(game, move);
            int moveValue = minimax(simulatedGame, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private static Game simulateGameAfterMove(Game game, Card move) {
        Game simulatedGame = new Game(game.getCurrentPlayer(), game.getOpponentPlayer(), new ArrayList<>(game.getDeck()));
        simulatedGame.playCard(game.getCurrentPlayer(), move);
        return simulatedGame;
    }

    private static int minimax(Game game, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || game.isGameOver()) {
            return evaluateGame(game);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Card move : game.getCurrentPlayer().getHand()) {
                Game simulatedGame = simulateGameAfterMove(game, move);
                int eval = minimax(simulatedGame, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Card move : game.getOpponentPlayer().getHand()) {
                Game simulatedGame = simulateGameAfterMove(game, move);
                int eval = minimax(simulatedGame, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private static int evaluateGame(Game game) {
        // Implement a heuristic evaluation function
        // For simplicity, count the number of cards in the score pile
        Player currentPlayer = game.getCurrentPlayer();
        return currentPlayer.getScorePile().size();
    }
}

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        List<Card> deck = new ArrayList<>();
        // Initialize the deck with cards (values and factions)

        Game game = new Game(player1, player2, deck);

        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            Card bestMove = MinimaxAI.getBestMove(game);
            game.playCard(currentPlayer, bestMove);
            game.resolveTrick();
        }

        // Determine the winner based on the score piles
    }
}*/

