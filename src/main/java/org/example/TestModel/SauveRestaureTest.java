package org.example.TestModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.example.Modele.Cards;
import org.example.Modele.Card;
import org.example.Modele.Defausse;
import org.example.Modele.GestionAnnuleRefaire;
import org.example.Modele.Hand;
import org.example.Modele.PileDeScore;
import org.example.Modele.Player;

public class SauveRestaureTest {

    public static void main(String[] args) throws IOException {
        // Initialisation des objets nécessaires pour les tests
        Scanner scanner = new Scanner(System.in);
        GestionAnnuleRefaire g = new GestionAnnuleRefaire();
        PrintStream p = new PrintStream("test.txt");
        BufferedReader r = new BufferedReader(new FileReader("test.txt"));
        SauveRestaureTest s = new SauveRestaureTest();

        // Affichage des options de test
        System.out.println("Tests des fonctions de la class GestionAnnuleRefaire : ");
        System.out.println("1. Test de la méthode save restaure Hand");
        System.out.println("2. Test de la méthode save restaure Pioche");
        System.out.println("3. Test de la méthode save restaure PileDeScore");
        System.out.println("4. Test de la méthode save restaure InfoPlayer");
        System.out.println("5. Test de la méthode save restaure Defausse");

        // Lecture de l'option de test choisie par l'utilisateur
        System.out.print("Entrez le numéro du test à exécuter : ");
        String test = scanner.nextLine();
        switch (test) {
            case "1":
                s.testSaveHand(p, r, g);
                break;
            case "2":
                s.testSavePioche(p, r, g);
                break;
            case "3":
                s.testPileDeScore(p, r, g);
                break;
            case "4":
                s.testInfoPlayer(p, r, g);
                break;
            case "5":
                s.testDefausse(p, r, g);
                break;
            default:
                System.out.println("Test non reconnu.");
                break;
        }
        scanner.close();
    }
    

    /**
     * Cette méthode crée un objet Hand, le sauvegarde, puis le récupère et compare avec l'original.
     * @param p PrintStream pour sauvegarder les données.
     * @param r BufferedReader pour lire les données sauvegardées.
     * @param g GestionAnnuleRefaire pour gérer les sauvegardes et restaurations.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void testSaveHand(PrintStream p , BufferedReader r , GestionAnnuleRefaire g) throws IOException{
        // creer la pioche 
        Cards pioche = new Cards();
        pioche.addAllCards();

        // Créer et initialiser le hand
        Hand main = pioche.getHandOf13Cards();
        for(Card card: main.getAllCards()){
            System.out.println(card);
        }

        // sauvgarder le hand
        g.saveHand(main , p);
        p.println();
        System.out.println();
        // reccuperer le hand
        Hand main2 = g.restaureHand(r);
        for(Card card: main2.getAllCards()){
            System.out.println(card);
        }
        // compare les deux hand
        verifieData(main, main2);
    }

    /**
     * Cette méthode crée une pioche de cartes, la sauvegarde, puis la récupère et compare avec l'original.
     * @param p PrintStream pour sauvegarder les données.
     * @param r BufferedReader pour lire les données sauvegardées.
     * @param g GestionAnnuleRefaire pour gérer les sauvegardes et restaurations.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void testSavePioche(PrintStream p , BufferedReader r , GestionAnnuleRefaire g) throws IOException {
        // creer la pioche 
        Cards pioche = new Cards();
        pioche.addAllCards();


        // sauvgarder la pioche
        g.saveCards(pioche , p);
        p.println();
        System.out.println();
        // reccuperer la pioche
        Cards pioche2 = g.restaureCards(r);
        for(int i=0;i<52;i++){
            Card card1 = pioche.getCard();
            Card card2 = pioche2.getCard();
            System.out.println(card1.getFaction()+":"+card2.getFaction());
            System.out.println(card1.getValeur()+";"+card2.getValeur());
            if ((card1.getFaction().equals(card2.getFaction()))&& (card1.getValeur()==card2.getValeur())){
                System.out.println("true");
            }
            else{
                System.out.println("false");
            }
        }

        // creer un nombre aleatoire entre 0 et 52
        /*int nombre = (int)(Math.random() * 52);

        // Retirer un nombre aléatoire de cartes de la pioche
        for (int i = 0; i < nombre; i++) {
            pioche.removeCard();
        }

        //save la pioche
        pioche2 = g.restaureCards(r);*/

        // compare les deux pioches
        verifieData(pioche, pioche2);
    }

    /**
     * Cette méthode crée une pile de score, la sauvegarde, puis la récupère et compare avec l'original.
     * @param p PrintStream pour sauvegarder les données.
     * @param r BufferedReader pour lire les données sauvegardées.
     * @param g GestionAnnuleRefaire pour gérer les sauvegardes et restaurations.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void testPileDeScore(PrintStream p , BufferedReader r , GestionAnnuleRefaire g) throws IOException {
        // Créer la pioche
        Cards pioche = new Cards();
        pioche.addAllCards();

        // creer la pile de score
        PileDeScore pileDeScore = new PileDeScore();

        // Ajouter un nombre aléatoire de cartes à la pile de score
        int nombre = (int)(Math.random() * 15);
        for (int i = 0; i < 10; i++) {
            pileDeScore.addCard(pioche.getCard());
        }

        // sauvgarder la pile de score
        g.savePileDeScore(pileDeScore , p);
        p.println();

        // reccuperer la pile de score
        PileDeScore pileDeScore2 = g.restaurePileDeScore(r);
        for(Map.Entry<String , List<Card>> entry :pileDeScore.getPileDeScore().entrySet()){
            String key = entry.getKey();
            List<Card> cards = entry.getValue();
            for(Card card : cards){
                System.out.println(card.getFaction()+":"+card.getValeur());
            }

        }
        
        // Comparer les deux piles de score
        verifieData(pileDeScore, pileDeScore2);
    }

    /**
     * Cette méthode crée un joueur avec ses informations, les sauvegarde, puis les récupère et compare avec l'original.
     * @param p PrintStream pour sauvegarder les données.
     * @param r BufferedReader pour lire les données sauvegardées.
     * @param g GestionAnnuleRefaire pour gérer les sauvegardes et restaurations.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void testInfoPlayer(PrintStream p , BufferedReader r , GestionAnnuleRefaire g) throws IOException {
        // creer un player 
        Player player = new Player("Alice");

        // creer la pioche 
        Cards pioche = new Cards();
        pioche.addAllCards();

        // Créer les mains du joueur pour les deux phases
        Hand main1 = pioche.getHandOf13Cards();
        Hand main2 = pioche.getHandOf13Cards();

        // Créer la pile de score du joueur
        PileDeScore pileDeScore = new PileDeScore();
        for (int i = 0; i < 9; i++) {
            pileDeScore.addCard(pioche.getCard());
        }

        // Assigner les mains et la pile de score au joueur
        player.setHand(main1);
        player.setHandScndPhase(main2);
        player.setPileDeScore(pileDeScore);
        player.setScore(10);

        // sauvgarder les informations du player
        Random random = new Random();
        Boolean phase = random.nextBoolean();
        g.saveInfoPlayer(player , p , phase);

        // reccuperer les informations du player
        Player player2 = g.restaureInfoPlayer(r,phase);

        // Comparer les informations du joueur
        verifieData(player, player2);
    }

    /**
     * Cette méthode crée une défausse, la sauvegarde, puis la récupère et compare avec l'original.
     * @param p PrintStream pour sauvegarder les données.
     * @param r BufferedReader pour lire les données sauvegardées.
     * @param g GestionAnnuleRefaire pour gérer les sauvegardes et restaurations.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    void testDefausse(PrintStream p , BufferedReader r , GestionAnnuleRefaire g) throws IOException {
        // creer la pioche 
        Cards pioche = new Cards();
        pioche.addAllCards();

        // creer la defausse
        Defausse defausse = new Defausse();

        // Ajouter un nombre aléatoire de cartes à la défausse
        int nombre = (int) (Math.random() * 15);
        for (int i = 0; i < nombre; i++) {
            defausse.ajouterCarte(pioche.getCard());
        }


        // sauvgarder la defausse
        g.saveDefausse(defausse , p);
        p.println();

        // reccuperer la defausse
        Defausse defausse2 = g.restaureDefausse(r);
        for(int i =0; i<defausse.size();i++){
            Card card1 = defausse.getCarte(i);
            Card card2 = defausse2.getCarte(i);
            if (card1.getFaction().equals(card2.getFaction())){
                System.out.println("true");
            }
            else {
                System.out.println("false");
            }
        }
        // Comparer les deux défausses
        verifieData(defausse, defausse2);
    }

    /**
     * Cette méthode compare deux objets et affiche si le test est réussi ou échoué.
     * @param o1 Premier objet à comparer.
     * @param o2 Deuxième objet à comparer.
     */
    public void verifieData(Object o1 , Object o2){
        if(o1.equals(o2)){
            System.out.println("test passed !");
        }else{
            System.out.println("test failed !");
        }
    }

}
