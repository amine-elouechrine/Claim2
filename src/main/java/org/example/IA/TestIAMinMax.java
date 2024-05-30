package org.example.IA;

import java.util.*;

import org.example.Modele.*;

import static org.example.IA.IA.determinerGagnantMancheIA;

public class TestIAMinMax {

    // fonction qui simmule le deroulement de la 1er phase Ia facile contre strategie gagnante
    public static Plateau jouerPatie1(){
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";
        boolean partieTerminee = false;

        // creation de l'ia MinMax 1st phase
        IAFirstPhase iaFirstPhase = new IAFirstPhase();

        Plateau plateauInitial = createInitialPlateau();

        while (!partieTerminee) {
            // set carte afficher
            Card carcarteAfficher = plateauInitial.getPioche().getCard();
            plateauInitial.setCardAffiche(carcarteAfficher);
            System.out.println(" carte Afficher : " + carcarteAfficher);

            if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                System.out.println("Tour de l'IA...");
                coupIa = iaFirstPhase.jouerCarteIA(plateauInitial);
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                plateauInitial.setCarteJoueur1(coupIa);
                plateauInitial.switchJoueur();
            }else{
                // Tour de l'adversaire
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
                plateauInitial.switchJoueur();
            }
            // -------------------------------------------------------------------------
            if (plateauInitial.getJoueurCourant().getName().equals("Facile")) {
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
                // attribution des cartes
                winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateauInitial);
            } else {
                System.out.println("Tour de l'IA...");
                coupIa = iaFirstPhase.jouerCarteIA(plateauInitial); // Profondeur de recherche = 13
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                plateauInitial.setCarteJoueur1(coupIa);
                // attribution des cartes
                winningCard = ReglesDeJeu.carteGagnante(coupAdversaire,coupIa, plateauInitial);
            }

            System.out.println("Carte gagnante : " + winningCard);
            plateauInitial.attribuerCarteFirstPhase(winningCard, new ReglesDeJeu());

            if (plateauInitial.estFinPhase(plateauInitial.getPhase())) {
                partieTerminee = true;
            }
            System.out.println("\n\n\n");

        }
        // passer a la phase 2
        plateauInitial.setPhase(false);
        return plateauInitial;
    }

    public static String jouerPartieFacileMinMax(Plateau plateauInitial) {
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";
        boolean partieTerminee = false;

        // creation de l'ia MinMax
        IAMinMax iaMinMax = new IAMinMax();

        // Création d'un plateau de jeu initial

        while (!partieTerminee) {
            if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                // Tour de l'IA
                System.out.println("Tour de l'IA...");
                coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                plateauInitial.switchJoueur();
            } else {
                // Tour de l'adversaire
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
                plateauInitial.switchJoueur();
            }

            if (plateauInitial.getJoueurCourant().getName().equals("Facile")) {
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
            } else {
                System.out.println("Tour de l'IA...");
                coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                if (coupIa == null) {
                    coupIa = plateauInitial.getJoueur1().getHandScndPhase().get(0);
                }
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
            }

            winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateauInitial);
            System.out.println("Carte gagnante : " + winningCard);
            plateauInitial.attribuerCarteSecondPhase(winningCard, new ReglesDeJeu());

            if (plateauInitial.isEndOfGame()) {
                winner = ReglesDeJeu.determinerGagnantPartie(plateauInitial.getJoueur1(), plateauInitial.getJoueur2());
                System.out.println("Fin de la partie. Victoire de " + winner);
                partieTerminee = true;
            }
        }

        return winner;
    }



    public static void main(String[] args) {
        int scoreMinMax = 0;
        int scoreFacile = 0;


        for (int i = 0; i < 100; i++) {
            Plateau plateau;
            plateau = jouerPatie1();
            System.out.println("Fin de la phase 1 \n\n\n");
            plateau.setCarteJoueur1(null);
            plateau.setCarteJoueur2(null);

            String winner = jouerPartieFacileMinMax(plateau);

            if (winner.equals("MinMax")) {
                scoreMinMax++;
            } else {
                scoreFacile++;
            }

            System.out.println("Fin de partie num : " + i);
            System.out.println("\n\n\n");
        }

        System.out.println("Score MinMax : " + scoreMinMax);
        System.out.println("Score Facile : " + scoreFacile);
    }


    public static Card carteAleatoire(Plateau plateau){

        Hand cartesJoueurCourant ;
        GeneralPlayer joueurCourant = plateau.getJoueurCourant();
        if(plateau.getPhase()){
            cartesJoueurCourant = joueurCourant.getHand();
        }else{
            cartesJoueurCourant = joueurCourant.getHandScndPhase();
        }
        List<Card> cartesJouable;
        if(plateau.estLeader()){
            cartesJouable = cartesJoueurCourant.getCards();
        }else{
            Card carteAdversaire = plateau.getCarteJoueur1();
            cartesJouable = ReglesDeJeu.cartesJouables(carteAdversaire , cartesJoueurCourant);
        }
        // choisir une carte aleatoire de cartes Jouables
        Random rand = new Random();
        int index = rand.nextInt(cartesJouable.size());
        return cartesJouable.get(index);
    }



    // faire un plateau avec configuration qui fait perdre l'ia forcement
    private static Plateau configurationPerdante(){
        Player joueur1 = new Player("MinMax");
        Player joueur2 = new Player("Facile");
        // creation des mains des joueurs
        Cards pioche = new Cards();
        pioche.addAllCards();

        // Donner une main à l'IA qui lui fait perdre
        // 5 * Goblins 0
        // Dwarves 0, Dwarves 1, Dwarves 2
        // Knight 2, Knight 3
        // Undead 0, Undead 1, Undead 2
        // Donner une main à l'IA qui lui fait perdre
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Dwarves"));
        joueur1.getHandScndPhase().addCard(new Card(1, "Dwarves"));
        joueur1.getHandScndPhase().addCard(new Card(2, "Dwarves"));
        joueur1.getHandScndPhase().addCard(new Card(2, "Knight"));
        joueur1.getHandScndPhase().addCard(new Card(3, "Knight"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Undead"));
        joueur1.getHandScndPhase().addCard(new Card(1, "Undead"));
        joueur1.getHandScndPhase().addCard(new Card(2, "Undead"));

        // donner au joueur 2 les cartes qui lui font gagner
        joueur2.getHandScndPhase().addCard(new Card(3, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(4, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(5, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(6, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(7, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(9, "Dwarves"));
        joueur2.getHandScndPhase().addCard(new Card(5, "Dwarves"));
        joueur2.getHandScndPhase().addCard(new Card(6, "Dwarves"));
        joueur2.getHandScndPhase().addCard(new Card(6, "Knight"));
        joueur2.getHandScndPhase().addCard(new Card(7, "Knight"));
        joueur2.getHandScndPhase().addCard(new Card(5, "Undead"));
        joueur2.getHandScndPhase().addCard(new Card(4, "Undead"));
        joueur2.getHandScndPhase().addCard(new Card(9, "Undead"));

        // Initialisation du plateau avec les joueurs
        Plateau plateau = new Plateau();
        plateau.setPhase(false);//phase 2

        // configuration du plateau
        plateau.setJoueur1(joueur1);  // ia
        plateau.setJoueur2(joueur2); // adversaire
        plateau.setJoueurCourant(joueur1); // on commande par l'ia


        return plateau;

    }

    public static Plateau createInitialPlateau(){
        // creation de plateau
        Plateau p = new Plateau();

        //creation des joueurs
        Player IA = new Player("MinMax");
        Player adversaire = new Player("Facile");

        // creation de la pioche
        Cards pioche = new Cards();
        pioche.addAllCards();
        pioche.shuffle();

        // attribution des hand des joueurs
        IA.setHand(pioche.getHandOf13Cards());
        adversaire.setHand(pioche.getHandOf13Cards());

        p.setPioche(pioche);
        p.setJoueur1(IA);
        p.setJoueur2(adversaire);
        p.setJoueurCourant(IA);
        p.setPhase(true);

        return p; // retourner le plteau apres construction
    }

    private static Plateau createInitialPlateauPhase2() {
        // Création des joueurs
        Player joueur1 = new Player("MinMax");
        Player joueur2 = new Player("Facile");

        // creation des mains des joueurs
        Cards pioche = new Cards();
        pioche.addAllCards();
        joueur1.setHandScndPhase(pioche.getHandOf13Cards());//ia
        joueur2.setHandScndPhase(pioche.getHandOf13Cards());//adversaire

        // Initialisation du plateau avec les joueurs
        Plateau plateau = new Plateau();
        plateau.setPhase(false);//phase 2

        // configuration du plateau
        plateau.setJoueur1(joueur1);  // ia
        plateau.setJoueur2(joueur2); // adversaire
        plateau.setJoueurCourant(joueur1); // on commande par l'ia


        return plateau;
    }
}


/*public static void main(String[] args) {

        Card coupIa = null ;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";

        int scoreMinMax = 0;
        int scoreFacile = 0;

        // Création de l'instance de l'IA Minimax
        IAMinMax iaMinMax = new IAMinMax();
        //Facile facile =new Facile();

        // Boucle principale de jeu
        boolean partieTerminee = false;
        int i = 0;
        while(i<100) {
            // Création d'un plateau de jeu initial
            Plateau plateauInitial = configurationPerdante();
            partieTerminee = false;
            winner = "";
            coupIa = null;
            coupAdversaire = null;

            //System.out.println("Joueur 1 est : "+plateauInitial.getJoueur1().getName());
            //System.out.println("Joueur 2 est : "+plateauInitial.getJoueur2().getName());
            //System.out.println("Hand Joeur1 : "+plateauInitial.getJoueur1().getHandScndPhase());
            //System.out.println("Hand Joeur2 : "+plateauInitial.getJoueur2().getHandScndPhase());
            while (!partieTerminee) {
                // si c'est le tour de l'ai
                if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                    // Tour de l'IA
                    System.out.println("Tour de l'IA...");
                    coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                    System.out.println("Carte choisie par l'IA: " + coupIa);
                    plateauInitial.jouerCarte(coupIa);
                    plateauInitial.switchJoueur();
                } else {// Tour de l'adversaire humain
                    System.out.println("Tour de l'adversaire...");
                    coupAdversaire = carteAleatoire(plateauInitial);
                    System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                    // Jouer le coup de l'adversaire
                    plateauInitial.jouerCarte(coupAdversaire);
                    plateauInitial.switchJoueur();
                }
                // --------------------------------------------------------------------------------------------
                if (plateauInitial.getJoueurCourant().getName().equals("Facile")) {
                    System.out.println("Tour de l'adversaire...");
                    coupAdversaire = carteAleatoire(plateauInitial);
                    System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                    // Jouer le coup de l'adversaire
                    plateauInitial.jouerCarte(coupAdversaire);

                } else {
                    System.out.println("Tour de l'IA...");
                    coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                    if(coupIa == null){
                        coupIa = plateauInitial.getJoueur1().getHandScndPhase().get(0);
                    }
                    System.out.println("Carte choisie par l'IA: " + coupIa);
                    plateauInitial.jouerCarte(coupIa);
                }

                winningCard = ReglesDeJeu.determinerCarteGagnante(coupIa, coupAdversaire, plateauInitial);
                System.out.println("Carte gagnante : " + winningCard);
                plateauInitial.attribuerCarteSecondPhase(winningCard, new ReglesDeJeu());


                // Vérification de la fin de la partie
                if (plateauInitial.isEndOfGame()) {
                    winner = ReglesDeJeu.determinerGagnantPartie(plateauInitial.getJoueur1(), plateauInitial.getJoueur2());
                    System.out.println("Fin de la partie. Victoire de " + winner);
                    partieTerminee = true;
                    break;
                }


            }
            System.out.println("fin de partie num :"+ i);
            System.out.println("\n\n\n");
            if(winner.equals("MinMax")) {
                scoreMinMax++;
            }else{
                scoreFacile++;
            }
            i++;
        }

        System.out.println("Score MinMax : "+scoreMinMax);
        System.out.println("Score Facile : "+scoreFacile);

    }*/

