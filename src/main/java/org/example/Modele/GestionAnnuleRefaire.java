package org.example.Modele;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Map;
import java.util.Stack;

public class GestionAnnuleRefaire {
    Plateau plateau;
    Stack<Plateau> annule;
    Stack<Plateau> refaire;

    // Constructeur par défaut de la classe GestionAnnuleRefaire 
    /**
     * Constructeur de la classe GestionAnnuleRefaire qui initialise les piles annule et refaire et commence un nouveau jeu
     * @param plateau le plateau de jeu
     */
    public GestionAnnuleRefaire(Plateau plateau) {
        this.plateau = plateau;
        annule = new Stack<>();
        refaire = new Stack<>();
    }

    /**
     * Constructeur de la classe GestionAnnuleRefaire 
     * @param fichier le fichier qui contient un jeu en cours sauvegardé
     * @throws Exception si le fichier n'existe pas
     */
    public GestionAnnuleRefaire(String fichier) throws Exception {
        annule = new Stack<>();
        refaire = new Stack<>();
    }

    /**
     * Méthode pour vérifier si on peut annuler
     * @return true si on peut annuler, false sinon
     */
    public boolean peutAnnuler() {
        return !annule.isEmpty();
    }

    /**
     * Méthode pour vérifier si on peut refaire
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
    public void annuler() {
        if (peutAnnuler()) {
            refaire.push(plateau);
            plateau = annule.pop();
        }
    }


    // il faut enlever la carte afficher de la pioche
    // enlever la carte jouer de la main de chaque joueur 
    public void refaire() {
        if (peutRefaire()) {
            annule.push(plateau);
            plateau = refaire.pop();
        }
    }

    // Ajoute un plateau à la pile annule
    public void addToHistory(Plateau plateau) {
        annule.push(plateau);
        clearStack();
    }


    public void saveHand(Hand hand, PrintStream p){
        for(Card card : hand.getAllCards()){
            p.println(card.getFaction()+":"+card.getValeur());
        }
    }
    public void saveCards(Cards cards,PrintStream p){
        for(Card card : cards.getCards()){
            p.println(card.getFaction()+":"+card.getValeur());
        }
    }
    public void savePileDeScore(PileDeScore pile , PrintStream p){
        for(Map.Entry<String ,List<Card>> entry :pile.getPileDeScore().entrySet()){
            String key = entry.getKey();
            List<Card> cards = entry.getValue();
            p.println(key+":"+cards.size());
            for(Card card : cards){
                p.println(card.getFaction()+":"+card.getValeur());
            }

        }
    }
    public void saveDefausse(Defausse defausse , PrintStream p){
        for(Card card : defausse.getCartes()){
            p.println(card.getFaction()+":"+card.getValeur());
        }
    }

    public void sauve (String filename,Cards cards,Plateau plateau,boolean phase1,int numTourJoue) throws FileNotFoundException {
        PrintStream p = new PrintStream(new File(filename));
        if (phase1){//si on est dans la premiere on va ecrire toute les infos
            //on ecrit la phase ou on est
            p.println("FirstPhase");
            p.println(numTourJoue);
            //on va ecrire tous les infos du joueur 1
            p.println(plateau.getJoueur1().getName());
            saveHand(plateau.getJoueur1().getHand(),p);//l'ecriture de la main du joueur 1
            p.println();
            saveHand(plateau.getJoueur1().getHandScndPhase(),p);
            p.println();
            savePileDeScore(plateau.getJoueur1().getPileDeScore(),p);
            p.println();
            p.println(plateau.getJoueur1().getScore());
            //on va ecrire tous les infos du joueur 2
            p.println(plateau.getJoueur2().getName());
            saveHand(plateau.getJoueur2().getHand(),p);//l'ecriture de la main du joueur 1
            p.println();
            saveHand(plateau.getJoueur2().getHandScndPhase(),p);
            p.println();
            savePileDeScore(plateau.getJoueur2().getPileDeScore(),p);
            p.println();
            p.println(plateau.getJoueur2().getScore());
            //on va ecrire les infos du plateau
            p.println(plateau.getJoueurCourant().getName());
            saveCards(plateau.getPioche(),p);
            //ecriture de la defausse
            saveDefausse(plateau.getDefausse(),p);
            p.println();
            //ecriture de la carte affiché
            p.println(plateau.getCarteAffichee());
            if(plateau.getCarteJoueur1()!=null){
                p.println(plateau.getCarteJoueur1());
            }
            else{
                p.println();
            }
            if (plateau.getCarteJoueur2()!=null){
                p.println(plateau.getCarteJoueur2());
            }
            else{
                p.println();
            }


        }
        else{
            p.println("SecondPhase");
            p.println(numTourJoue);
            //infos premier joueur
            p.println(plateau.getJoueur1().getName());
            saveHand(plateau.getJoueur1().getHandScndPhase(),p);
            p.println();
            savePileDeScore(plateau.getJoueur1().getPileDeScore(),p);
            p.println();
            p.println(plateau.getJoueur1().getScore());
            //infos second joueur
            p.println(plateau.getJoueur2().getName());
            saveHand(plateau.getJoueur2().getHandScndPhase(),p);
            p.println();
            savePileDeScore(plateau.getJoueur2().getPileDeScore(),p);
            p.println();
            p.println(plateau.getJoueur2().getScore());
            //infos plateau
            p.println(plateau.getJoueurCourant().getName());
            saveCards(plateau.getPioche(),p);
            //ecriture de la defausse
            saveDefausse(plateau.getDefausse(),p);
            p.println();
            //ecriture de la carte affiché
            p.println(plateau.getCarteAffichee());
            if(plateau.getCarteJoueur1()!=null){
                p.println(plateau.getCarteJoueur1());
            }
            else{
                p.println();
            }
            if (plateau.getCarteJoueur2()!=null){
                p.println(plateau.getCarteJoueur2());
            }
            else{
                p.println();
            }

        }
    }











    public void restoreHand(){

    }
    /* pour sauve restaure il faut stocker le plateau actuell les piles de refaire & annule mais il faut aussi stocker la main de chaque joueur  */
    public void sauve(String fileName){
        try {
            System.out.println("le sauvegarde dans le fichier " + fileName + " est en cours");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
        }
    }

    // reccupere le plateau actuel les piles de refaire & annule mais il faut aussi reccupere la main de chaque joueur
    public void restaure(String fileName){
        try {
            System.out.println("le restauration du fichier " + fileName + " est en cours");
        } catch (Exception e) {
            System.out.println("Erreur lors de la restauration");
        }
    }
    
}
