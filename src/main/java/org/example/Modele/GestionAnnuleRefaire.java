package org.example.Modele;

import java.io.*;
import java.util.*;

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
    public void clearStackRefaire() {
        while (!refaire.empty()) {
            refaire.pop();
        }
    }
    /**
     * @brief Vide la pile des actions annulées.
     */
    public void clearStackAnnule() {
        while (!annule.empty()) {
            annule.pop();
        }
    }
    /**
     * @brief Annule la dernière action effectuée sur le plateau.
     * @param p Le plateau de jeu à modifier.
     */

    public void annuler(Plateau p) {
        if (peutAnnuler()) {
            Plateau np = annule.pop();
            Plateau n=clonePlateau(p);
            refaire.push(n);

            p.setPhase(np.getPhase());
            p.setPioche(np.getPioche());
            p.setDefausse(np.getDefausse());
            p.setJoueur1(np.getJoueur1());
            p.setJoueur2(np.getJoueur2());
            if (!np.phase){
                p.getJoueur1().setHand(p.getJoueur1().getHandScndPhase());
                p.getJoueur2().setHand(p.getJoueur2().getHandScndPhase());
            }
            p.setCarteAffichee(np.getCarteAffichee());
            p.setCarteJoueur1(np.getCarteJoueur1());
            p.setCarteJoueur2(np.getCarteJoueur2());
            p.setJoueurCourant(np.getJoueurCourant());
        }
    }
    /**
     * @brief Refait la dernière action annulée sur le plateau.
     * @param p Le plateau de jeu à modifier.
     */
    public void refaire(Plateau p) {
        if (peutRefaire()) {
            Plateau np = refaire.pop();
            Plateau n = clonePlateau(p);
            annule.push(n);

            p.setPhase(np.getPhase());
            p.setPioche(np.getPioche());
            p.setDefausse(np.getDefausse());
            p.setJoueur1(np.getJoueur1());
            p.setJoueur2(np.getJoueur2());
            if (!np.phase){
                p.getJoueur1().setHand(p.getJoueur1().getHandScndPhase());
                p.getJoueur2().setHand(p.getJoueur2().getHandScndPhase());
            }
            p.setCarteAffichee(np.getCarteAffichee());
            p.setCarteJoueur1(np.getCarteJoueur1());
            p.setCarteJoueur2(np.getCarteJoueur2());
            p.setJoueurCourant(np.getJoueurCourant());
        }
    }
    /**
     * @brief Clone un plateau de jeu.
     * @param plateau Le plateau de jeu à cloner.
     * @return Un nouveau plateau identique au plateau passé en paramètre.
     */
    public Plateau clonePlateau(Plateau plateau){
        Plateau p = new Plateau();

        p.setPhase(plateau.getPhase());

        // creation de la nouvelle pioche
        Cards ncards = new Cards();
        for (Card carte : plateau.getPioche().getCards()) {
            ncards.addCard(carte);
        }
        p.setPioche(ncards);

        //creation de la defausse
        Defausse ndefausse = new Defausse();
        for (int i = 0; i < plateau.getDefausse().getSize(); i++) {
            ndefausse.addCard(plateau.getDefausse().getCard(i));
        }
        p.setDefausse(ndefausse);

        //creation des donneers de joueur 1
        //creation de sa main phase1
        Player nPlayer1 = new Player(plateau.getJoueur1().getName());
        Hand nHand = new Hand();
        for (Card carte : plateau.getJoueur1().getHand().getCards()) {
            nHand.addCard(carte);
        }
        // Creation de la main phase 2
        Hand nHand2 = new Hand();
        for (Card carte : plateau.getJoueur1().getHandScndPhase().getCards()) {
            nHand2.addCard(carte);
        }

        // creation de la pile de score
        PileDeScore nPileDeScore = new PileDeScore();
        for (Map.Entry<String, List<Card>> entry : nPileDeScore.getPileDeScore().entrySet()) {
            List<Card> cards = entry.getValue();
            for (Card card : cards) {
                nPileDeScore.addCard(card);
            }
        }
        nPlayer1.setHand(nHand);
        nPlayer1.setHandScndPhase(nHand2);
        nPlayer1.setPileDeScore(nPileDeScore);
        p.setJoueur1(nPlayer1);

        // Creation des donnes du deuxieme joueur
        Player nPlayer2 = new Player(plateau.getJoueur2().getName());
        Hand Hand = new Hand();
        for (Card carte : plateau.getJoueur2().getHand().getCards()) {
            Hand.addCard(carte);
        }

        // Creation de la main phase 2
        Hand Hand2 = new Hand();
        for (Card carte : plateau.getJoueur2().getHandScndPhase().getCards()) {
            Hand2.addCard(carte);
        }

        // Creation de la pile de score
        PileDeScore PileDeScore = new PileDeScore();
        for (Map.Entry<String, List<Card>> entry : plateau.getJoueur2().getPileDeScore().getPileDeScore().entrySet()) {
            List<Card> cards = entry.getValue();
            for (Card card : cards) {
                PileDeScore.addCard(card);
            }
        }
        nPlayer2.setHand(Hand);
        nPlayer2.setHandScndPhase(Hand2);
        nPlayer2.setPileDeScore(PileDeScore);
        p.setJoueur2(nPlayer2);

        // Creation de la carte affichee
        Card carteaffichee=null;
        if (plateau.getPhase())
            carteaffichee = new Card(plateau.getCarteAffichee().getValeur(), plateau.getCarteAffichee().getFaction());
        p.setCarteAffichee(carteaffichee);

        // Creation de la carte du joueeur 1
        Card cardJ1 = null;
        if (plateau.getCarteJoueur1() != null) {
            cardJ1 = new Card(plateau.getCarteJoueur1().getValeur(), plateau.getCarteJoueur1().getFaction());

        }
        p.setCarteJoueur1(cardJ1);

        // Creation de la carte du joueur 2
        Card cardJ2 = null;
        if (plateau.getCarteJoueur2() != null) {
            cardJ2 = new Card(plateau.getCarteJoueur2().getValeur(), plateau.getCarteJoueur2().getFaction());

        }
        p.setCarteJoueur2(cardJ2);

        // Creation du joueur courant
        Player joueurCour;
        if (plateau.getJoueurCourant() == plateau.getJoueur1()) {
            joueurCour = nPlayer1;
        } else {
            joueurCour = nPlayer2;
        }
        p.setJoueurCourant(joueurCour);
        return p;
    }

    /**
     * @brief Ajoute un plateau à l'historique des actions annulées.
     * @param plateau Le plateau à ajouter.
     */
    public void addToHistory(Plateau plateau) {
        Plateau p = clonePlateau(plateau);
        annule.push(p);
        clearStackRefaire();
    }

    /**
     * @brief Sauvegarde une main de cartes dans un flux de sortie.
     * @param hand La main de cartes à sauvegarder.
     * @param p Le flux de sortie.
     */
    public void saveHand(Hand hand, PrintStream p) {
        for (Card card : hand.getAllCards()) {
            p.println(card.getFaction() + ":" + card.getValeur());
        }
    }
    /**
     * @brief Sauvegarde une collection de cartes dans un flux de sortie.
     * @param cards La collection de cartes à sauvegarder.
     * @param p Le flux de sortie.
     */
    public void saveCards(Cards cards, PrintStream p) {
        for (Card card : cards.getCards()) {
            p.println(card.getFaction() + ":" + card.getValeur());
        }
    }
    /**
     * @brief Sauvegarde une pile de score dans un flux de sortie.
     * @param pile La pile de score à sauvegarder.
     * @param p Le flux de sortie.
     */
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
    /**
     * @brief Sauvegarde une défausse dans un flux de sortie.
     * @param defausse La défausse à sauvegarder.
     * @param p Le flux de sortie.
     */
    public void saveDefausse(Defausse defausse, PrintStream p) {
        for (Card card : defausse.getCartes()) {
            p.println(card.getFaction() + ":" + card.getValeur());
        }
    }
    /**
     * @brief Sauvegarde les informations d'un joueur dans un flux de sortie.
     * @param j Le joueur dont les informations sont à sauvegarder.
     * @param p Le flux de sortie dans lequel les informations du joueur sont écrites.
     * @param phase Indique si le jeu est en phase 1 ou non.
     */
    public void saveInfoPlayer(Player j, PrintStream p, boolean phase) {
        p.println(j.getName());
        saveHand(j.getHand(), p);//l'ecriture de la main du joueur
        p.println();
        saveHand(j.getHandScndPhase(), p);
        p.println();
        savePileDeScore(j.getPileDeScore(), p);
        p.println();
        p.println(j.getScore());
    }
    /**
     * @brief Restaure la main d'un joueur à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel la main est restaurée.
     * @return La main restaurée.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public Hand restaureHand(BufferedReader r) throws IOException {
        Hand hand = new Hand();
        String[] card = r.readLine().split(":");
        while (!(card[0].isEmpty())) {
            System.out.println(card[0]);
            String faction = card[0];
            int valeur = Integer.parseInt(card[1]);
            Card carte = new Card(valeur, faction);
            hand.addCard(carte);
            card = r.readLine().split(":");
        }
        return hand;
    }
    /**
     * @brief Restaure la pile de score d'un joueur à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel la pile de score est restaurée.
     * @return La pile de score restaurée.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
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
    /**
     * @brief Restaure la défausse à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel la défausse est restaurée.
     * @return La défausse restaurée.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
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
    /**
     * @brief Restaure un ensemble de cartes à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel les cartes sont restaurées.
     * @return Les cartes restaurées.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
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
    /**
     * @brief Sauvegarde l'état d'un plateau de jeu dans un flux de sortie.
     * @param p Le flux de sortie dans lequel l'état du plateau est écrit.
     * @param plateau Le plateau de jeu à sauvegarder.
     */
    public void sauvePlateau(PrintStream p , Plateau plateau){
        if (plateau.phase) p.println("FirstPhase");
        else p.println("SecondPhase");
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
            p.println(plateau.getCarteJoueur2().getFaction() + ":" + plateau.getCarteJoueur2().getValeur());
        } else {
            p.println();
        }
    }
    /**
     * @brief Sauvegarde l'état d'un plateau de jeu dans un fichier.
     * @param filename Le nom du fichier où sauvegarder le plateau.
     * @param plateau Le plateau de jeu à sauvegarder.
     * @throws FileNotFoundException Si le fichier ne peut pas être créé ou ouvert.
     */
    public void sauve(String filename,Plateau plateau) throws FileNotFoundException {
        PrintStream p = new PrintStream(new File(filename));
        sauvePlateau(p,plateau);
        sauvePile(p);

    }
    /**
     * @brief Sauvegarde la pile des plateaux annulés dans un flux de sortie.
     * @param p Le flux de sortie dans lequel la pile des plateaux annulés est écrite.
     * @throws FileNotFoundException Si le fichier ne peut pas être créé ou ouvert.
     */
    public void  sauvePile (PrintStream p) throws FileNotFoundException {
        p.println(annule.size());
        Stack<Plateau> ns = new Stack<>() ;
        while(!annule.isEmpty()){
           ns.push(annule.pop()) ;
        }
        while(!ns.isEmpty()){
            Plateau pl = ns.pop();
            sauvePlateau(p,pl);
            p.println();
        }
    }
    /**
     * @brief Restaure une carte à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel la carte est restaurée.
     * @return La carte restaurée.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public Card restaureCard(BufferedReader r) throws IOException {
        String[] carte = r.readLine().split(":");
        String faction = carte[0];
        int valeur = Integer.parseInt(carte[1]);
        return new Card(valeur, faction);

    }
    /**
     * @brief Restaure une carte si elle existe à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel la carte est restaurée.
     * @return La carte restaurée ou null si elle n'existe pas.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
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
    /**
     * @brief Restaure les informations d'un joueur à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel les informations du joueur sont restaurées.
     * @param phase Indique si le jeu est en phase 1 ou non.
     * @return Le joueur restauré.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public Player restaureInfoPlayer(BufferedReader r, boolean phase) throws IOException {
        String namePlayer1 = r.readLine();
        Hand handP1J1 = restaureHand(r);
        Hand handP2J1 = restaureHand(r);
        PileDeScore pileJ1 = restaurePileDeScore(r);
        int scoreJ1 = Integer.parseInt(r.readLine());
        Player joueur1 = new Player(namePlayer1);
        if (phase)
            joueur1.setHand(handP1J1);
        else
            joueur1.setHand(handP2J1);
        joueur1.setScore(scoreJ1);
        joueur1.setHandScndPhase(handP2J1);
        joueur1.setPileDeScore(pileJ1);
        return joueur1;
    }
    /**
     * @brief Crée un plateau avec les paramètres spécifiés.
     * @param phase Indique la phase du jeu.
     * @param carteAffichee La carte affichée sur le plateau.
     * @param carteJoueur1 La carte du joueur 1.
     * @param carteJoueur2 La carte du joueur 2.
     * @param defausse La défausse du jeu.
     * @param joueur1 Le joueur 1.
     * @param joueur2 Le joueur 2.
     * @param pioche La pioche du jeu.
     * @param nameCurrentPlayer Le nom du joueur courant.
     * @return Le plateau créé avec les paramètres spécifiés.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public Plateau setPlateau(boolean phase, Card carteAffichee, Card carteJoueur1, Card carteJoueur2, Defausse defausse, Player joueur1, Player joueur2, Cards pioche, String nameCurrentPlayer) throws IOException {
        Plateau plateau = new Plateau();
        plateau.setPhase(phase);
        plateau.setCarteAffichee(carteAffichee);
        plateau.setCarteJoueur1(carteJoueur1);
        plateau.setCarteJoueur2(carteJoueur2);
        plateau.setDefausse(defausse);
        plateau.setJoueur1(joueur1);
        plateau.setJoueur2(joueur2);
        plateau.setPioche(pioche);

        if (nameCurrentPlayer.equals(plateau.getJoueur1().getName())) {
            plateau.joueurCourant = plateau.getJoueur1();
        } else {
            plateau.joueurCourant = plateau.getJoueur2();
        }
        return plateau;
    }
    /**
     * @brief Restaure un plateau à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel le plateau est restauré.
     * @return Le plateau restauré.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public Plateau restaurePlateau(BufferedReader r) throws IOException {
        String Phase = r.readLine();
        boolean phase;
        if (Phase.equals("")){
            Phase = r.readLine();
        }
        if (Phase.equals("FirstPhase")) {
            phase = true;
        }
        else{
            phase=false ;
        }
        Player joueur1 = restaureInfoPlayer(r, phase);

        Player joueur2 = restaureInfoPlayer(r, phase);

        // infos du plateau
        String nameCurrentPlayer = r.readLine();
        Cards pioche = restaureCards(r);
        Defausse defausse = restaureDefausse(r);
        Card carteAffiche =null;
        if (phase)
            carteAffiche = restaureCard(r);
        Card carteJoueur1 = restaureCardIfExist(r);
        Card carteJoueur2 = restaureCardIfExist(r);

        return setPlateau(phase, carteAffiche, carteJoueur1, carteJoueur2, defausse, joueur1, joueur2, pioche, nameCurrentPlayer);
    }
    /**
     * @brief Restaure un plateau à partir d'un fichier.
     * @param Filename Le nom du fichier contenant les informations du plateau à restaurer.
     * @return Le plateau restauré.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public Plateau restaure(String Filename) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(Filename));
        Plateau pl = restaurePlateau(r);
        restaurePile(r);
        return pl;
    }
    /**
     * @brief Restaure la pile des plateaux annulés à partir d'un flux de lecture.
     * @param r Le flux de lecture à partir duquel la pile des plateaux annulés est restaurée.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public void restaurePile(BufferedReader r) throws IOException {
        clearStackAnnule();
        String Char = r.readLine();
        int taille =Integer.parseInt(Char);
        for (int i =0;i<taille ;i++){
            Plateau pl  = restaurePlateau(r);
            annule.push(pl);

        }
    }
}

