package org.example.Modele;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GestionAnnuleRefaire {
    Stack<Plateau> annule;
    Stack<Plateau> refaire;

    // Constructeur par défaut de la classe GestionAnnuleRefaire 

    /**
     * Constructeur de la classe GestionAnnuleRefaire qui initialise les piles annule et refaire et commence un nouveau jeu
     */

    public GestionAnnuleRefaire() {
        annule = new Stack<>();
        refaire = new Stack<>();
    }

    /**
     * Constructeur de la classe GestionAnnuleRefaire
     *
     * @param fichier le fichier qui contient un jeu en cours sauvegardé
     * @throws Exception si le fichier n'existe pas
     */
    public GestionAnnuleRefaire(String fichier) throws Exception {
        annule = new Stack<>();
        refaire = new Stack<>();
    }

    /**
     * Méthode pour vérifier si on peut annuler
     *
     * @return true si on peut annuler, false sinon
     */
    public boolean peutAnnuler() {
        return !annule.isEmpty();
    }

    /**
     * Méthode pour vérifier si on peut refaire
     *
     * @return true si on peut refaire, false sinon
     */
    public boolean peutRefaire() {
        return !refaire.isEmpty();
    }

    /**
     * Méthode pour vider la pile refaire
     */
    public void clearStack() {
        while (!refaire.empty()) {
            refaire.pop();
        }
    }

    // il faut remetre la carte de chaque joueur dans son main
    // remettre la carte afficher dans la pioche 
    public void annuler(Plateau p) throws IOException {
        System.out.println(annule);
        if (peutAnnuler()) {
            Hand main = new Hand();
            main.addCard(p.getCarteAffichee());
            // p.getJoueur1().setHand(main);
            System.out.println("peut annuler == true");
            Plateau np;
            np = annule.pop();
            np.getJoueur1().getHand().printHand();
            p.setPhase(np.getPhase());
            p.setCarteAffichee(np.getCarteAffichee());
            p.setCarteJoueur1(np.getCarteJoueur1());
            p.setCarteJoueur2(np.getCarteJoueur2());
            p.setDefausse(np.getDefausse());
            p.setJoueur1(np.getJoueur1());
            p.setJoueur2(np.getJoueur2());
            p.setPioche(np.getPioche());
            p.getJoueur1().setHand(np.getJoueur1().hand);
            p.getJoueur2().setHand(np.getJoueur2().hand);
            if (np.getJoueurCourant().getName().equals(p.getJoueur1().getName())) {
                p.setJoueurCourant(p.getJoueur1());
            } else {
                p.setJoueurCourant(p.getJoueur2());
            }
            // p.setPlateau(np.getPhase(), np.getCarteAffichee(), np.getCarteJoueur1(), np.getCarteJoueur2(), np.getDefausse(), np.getJoueur1(), np.getJoueur2(), np.getPioche(), np.getJoueurCourant().getName(), np.getJoueur1().getHand(), np.getJoueur2().getHand());
            refaire.push(p);
        }
    }


    // il faut enlever la carte afficher de la pioche
    // enlever la carte jouer de la main de chaque joueur 
    public Plateau refaire() {
        if (peutRefaire()) {
            annule.push(getPlateau());
            return refaire.pop();
        } else return getPlateau();
    }

    // Ajoute un plateau à la pile annule
    public void addToHistory(Plateau plateau) {
        annule.push(plateau);
        clearStack();
    }


    public void saveHand(Hand hand, PrintStream p) {
        for (Card card : hand.getAllCards()) {
            p.println(card.getFaction() + ":" + card.getValeur());
        }
    }

    public void saveCards(Cards cards, PrintStream p) {
        for (Card card : cards.getCards()) {
            p.println(card.getFaction() + ":" + card.getValeur());
        }
    }

    public void savePileDeScore(PileDeScore pile, PrintStream p) {
        for (Map.Entry<String, List<Card>> entry : pile.getPileDeScore().entrySet()) {
            String key = entry.getKey();
            List<Card> cards = entry.getValue();
            p.println(key + ":" + cards.size());
            for (Card card : cards) {
                p.println(card.getFaction() + ":" + card.getValeur());
            }

        }
    }

    public void saveDefausse(Defausse defausse, PrintStream p) {
        for (Card card : defausse.getCartes()) {
            p.println(card.getFaction() + ":" + card.getValeur());
        }
    }

    public void saveInfoPlayer(Player j, PrintStream p, boolean phase) {
        p.println(j.getName());
        saveHand(j.getHand(), p);//l'ecriture de la main du joueur
        p.println();
        if (phase) {
            saveHand(j.getHandScndPhase(), p);
            p.println();
        }
        savePileDeScore(j.getPileDeScore(), p);
        p.println();
        p.println(j.getScore());
    }

    public Hand restaureHand(BufferedReader r) throws IOException {
        Hand hand = new Hand();
        String[] card = r.readLine().split(":");
        while (!(card[0].isEmpty())) {
            String faction = card[0];
            int valeur = Integer.parseInt(card[1]);
            Card carte = new Card(valeur, faction);
            hand.addCard(carte);
            card = r.readLine().split(":");
        }
        return hand;
    }

    public PileDeScore restaurePileDeScore(BufferedReader r) throws IOException {//restaurer la pile de score
        PileDeScore pileDeScore = new PileDeScore();
        String[] line = r.readLine().split(":");
        while (!(line[0].isEmpty())) {
            int taille = Integer.parseInt(line[1]);
            for (int i = 0; i < taille; i++) {
                String[] carte = r.readLine().split(":");
                String faction = carte[0];
                int valeur = Integer.parseInt(carte[1]);
                Card card = new Card(valeur, faction);
                pileDeScore.addCard(card);
            }
            line = r.readLine().split(":");
        }
        return pileDeScore;
    }

    public Defausse restaureDefausse(BufferedReader r) throws IOException {
        Defausse defausse = new Defausse();
        String[] line = r.readLine().split(":");
        while (!(line[0].isEmpty())) {
            String faction = line[0];
            int valeur = Integer.parseInt(line[1]);
            Card card = new Card(valeur, faction);
            defausse.ajouterCarte(card);
            line = r.readLine().split(":");
        }
        return defausse;
    }

    public Cards restaureCards(BufferedReader r) throws IOException {
        ArrayList<Card> list = new ArrayList<>();
        String[] line = r.readLine().split(":");
        while (!(line[0].isEmpty())) {
            String faction = line[0];
            int valeur = Integer.parseInt(line[1]);
            Card card = new Card(valeur, faction);
            list.add(card);
            line = r.readLine().split(":");
        }
        Cards cards = new Cards();
        while (!(list.isEmpty())) {
            Card c = list.remove(list.size() - 1);
            cards.setCard(c);
        }
        return cards;
    }

    public void sauve(String filename, Cards cards, Plateau plateau) throws FileNotFoundException {
        PrintStream p = new PrintStream(new File(filename));
        //si on est dans la premiere on va ecrire toute les infos
        //on ecrit la phase ou on est
        if (plateau.phase)
            p.println("FirstPhase");
        else
            p.println("SecondPhase");
        //on va ecrire tous les infos du joueur 1
        saveInfoPlayer(plateau.getJoueur1(), p, plateau.phase);
        //on va ecrire tous les infos du joueur 2
        saveInfoPlayer(plateau.getJoueur2(), p, plateau.phase);
        //on va ecrire les infos du plateau
        p.println(plateau.getJoueurCourant().getName());
        saveCards(plateau.getPioche(), p);
        p.println();//saute de ligne pour separer la pioche de la defausse
        //ecriture de la defausse
        saveDefausse(plateau.getDefausse(), p);
        p.println();
        //ecriture de la carte affiché
        if (plateau.phase) {
            p.println(plateau.getCarteAffichee().getFaction() + ":" + plateau.getCarteAffichee().getValeur());
        }
        if (plateau.getCarteJoueur1() != null) {
            p.println(plateau.getCarteJoueur1().getFaction() + ":" + plateau.getCarteJoueur1().getValeur());
        } else {
            p.println();
        }
        if (plateau.getCarteJoueur2() != null) {
            p.println(plateau.getCarteJoueur2() + ":" + plateau.getCarteJoueur2().getValeur());
        } else {
            p.println();
        }
    }

    public Card restaureCard(BufferedReader r) throws IOException {
        String[] carte = r.readLine().split(":");
        String faction = carte[0];
        int valeur = Integer.parseInt(carte[1]);
        return new Card(valeur, faction);

    }

    public Card restaureCardIfExist(BufferedReader r) throws IOException {
        String[] line = r.readLine().split(":");
        if (line[0].isEmpty()) {
            return null;
        } else {
            String faction = line[0];
            int valeur = Integer.parseInt(line[1]);
            return new Card(valeur, faction);
        }
    }

    public Player restaureInfoPlayer(BufferedReader r, boolean phase) throws IOException {
        String namePlayer1 = r.readLine();
        Hand handP1J1 = restaureHand(r);
        Hand handP2J1 = restaureHand(r);
        PileDeScore pileJ1 = restaurePileDeScore(r);
        int scoreJ1 = Integer.parseInt(r.readLine());
        Player joueur1 = new Player(namePlayer1);
        if (phase)
            joueur1.setHand(handP1J1);
        joueur1.setScore(scoreJ1);
        joueur1.setHandScndPhase(handP2J1);
        joueur1.setPileDeScore(pileJ1);
        return joueur1;
    }

    public Plateau setPlateau(boolean phase, Card carteAffichee, Card carteJoueur1, Card carteJoueur2, Defausse defausse, Player joueur1, Player joueur2, Cards pioche, String nameCurrentPlayer, Hand mainJ1, Hand mainJ2) throws IOException {
        Plateau plateau = new Plateau();
        plateau.setPhase(phase);
        plateau.setCarteAffichee(carteAffichee);
        plateau.setCarteJoueur1(carteJoueur1);
        plateau.setCarteJoueur2(carteJoueur2);
        plateau.setDefausse(defausse);
        plateau.setJoueur1(joueur1);
        plateau.setJoueur2(joueur2);
        plateau.setPioche(pioche);
        plateau.getJoueur1().setHand(mainJ1);
        plateau.getJoueur2().setHand(mainJ2);
        if (nameCurrentPlayer.equals(plateau.getJoueur1().getName())) {
            plateau.joueurCourant = plateau.getJoueur1();
        } else {
            plateau.joueurCourant = plateau.getJoueur2();
        }
        return plateau;
    }

    public void restaure(String Filename) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(Filename));
        String Phase = r.readLine();
        boolean phase;
        if (Phase.equals("FirstPhase")) {
            phase = true;
        } else {
            phase = false;
        }
        Player joueur1 = restaureInfoPlayer(r, phase);

        Player joueur2 = restaureInfoPlayer(r, phase);

        // infos du plateau
        String nameCurrentPlayer = r.readLine();
        Cards pioche = restaureCards(r);
        Defausse defausse = restaureDefausse(r);
        Card carteAffiche = restaureCard(r);
        Card carteJoueur1 = restaureCardIfExist(r);
        Card carteJoueur2 = restaureCardIfExist(r);
        Hand mainJ1 = restaureHand(r);
        Hand mainJ2 = restaureHand(r);
        Plateau p = getPlateau();
        setPlateau(phase, carteAffiche, carteJoueur1, carteJoueur2, defausse, joueur1, joueur2, pioche, nameCurrentPlayer, mainJ1, mainJ2);

    }

    public Plateau getPlateau() {
        return getPlateau();
    }


    /* pour sauve restaure il faut stocker le plateau actuell les piles de refaire & annule mais il faut aussi stocker la main de chaque joueur  */
    public void sauve(String fileName) {
        try {
            System.out.println("le sauvegarde dans le fichier " + fileName + " est en cours");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
        }
    }

    // reccupere le plateau actuel les piles de refaire & annule mais il faut aussi reccupere la main de chaque joueur
    /*public void restaure(String fileName){
        try {
            System.out.println("le restauration du fichier " + fileName + " est en cours");
        } catch (Exception e) {
            System.out.println("Erreur lors de la restauration");
        }
    }*/

}
