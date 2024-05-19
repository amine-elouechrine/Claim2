package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Hand;
import org.example.Modele.PileDeScore;

import java.util.ArrayList;
import java.util.List;

public class Node {
    // remplace tous ces attribut par plateau il englobe tout
    Hand Ia;
    Hand adversaire;
    Card carteIA;
    Card Adversaire;
    // remplace tous ces attribut par plateau il englobe tout
    Hand Ia;
    Hand adversaire;
    Card carteIA;
    Card Adversaire;
    private List<Node> enfants;
    private int scoreIA;
    private int scoreAdversaire;
    private int scoreIA;
    private int scoreAdversaire;
    private int score;
    public boolean IsIaTurn;
    //tableau d'entiers
    PileDeScore pileDeScoreIa;
    PileDeScore pileDeScoreAdversaire;
    public Card CarteGagnante;
    public Plateau plateau;


    public Node(Hand handIa, Hand handAdversaire,boolean isIaTurn,Card IACard,Card Cardadversaire,PileDeScore pileDeScoreIa,PileDeScore pileDeScoreAdversaire,int score) {
        this.carteIA=IACard;
        this.Adversaire=Cardadversaire;
        Ia=handIa;
        adversaire=handAdversaire;
        enfants=new ArrayList<>();
        this.IsIaTurn=isIaTurn;
        this.pileDeScoreIa=pileDeScoreIa;
        this.pileDeScoreAdversaire=pileDeScoreAdversaire;
        this.score=score;
        this.IsIaTurn=isIaTurn;
        this.pileDeScoreIa=pileDeScoreIa;
        this.pileDeScoreAdversaire=pileDeScoreAdversaire;
        this.score=score;
    }
    public void afficherArbre(String prefix) {
        System.out.println(prefix + (IsIaTurn ? "IA" : "Adversaire") + " - Score: " + score );
        for (Node enfant : enfants) {
            enfant.afficherArbre(prefix + "  ");
        }

    public Node (Plateau plateau){
        this.plateau=plateau;
    }

    public void setEnfants(List<Node> enfants) {
        this.enfants = enfants;
    }

    //ajouter un enfant
    public void addEnfant(Node enfant){
        enfants.add(enfant);
    }

    public List<Node> getEnfants(){
        return enfants;
    }

    /*public Node clone(Card carteIA,Card carteAdversaire){

        Node clone= new Node(Ia,adversaire,IsIaTurn,carteIA,carteAdversaire,pileDeScoreIa,pileDeScoreAdversaire,score);
        clone.setScore(score);
        clone.IsIaTurn=IsIaTurn;
        clone.enfants=enfants;
        clone.scoreAdversaire=scoreAdversaire;
        clone.scoreIA=scoreIA;
        return clone;
    }*/

    public void Remove_played_cards(Card IaCard, Card adversaireCard,Hand Ia,Hand adversaire){
        Ia.removeCard(IaCard);
        adversaire.removeCard(adversaireCard);
    }

    public Node clone(Card carteIA, Card carteAdversaire,Node node) {
        Hand clonedHandIa = new Hand(node.Ia); // Ensure Hand has a proper copy constructor
        Hand clonedHandAdversaire = new Hand(node.adversaire); // Ensure Hand has a proper copy constructor
        PileDeScore clonedPileDeScoreIa = new PileDeScore(node.pileDeScoreIa); // Ensure PileDeScore has a proper copy constructor
        PileDeScore clonedPileDeScoreAdversaire = new PileDeScore(node.pileDeScoreAdversaire); // Ensure PileDeScore has a proper copy constructor

        Node clone = new Node(clonedHandIa, clonedHandAdversaire, node.IsIaTurn, carteIA, carteAdversaire, clonedPileDeScoreIa, clonedPileDeScoreAdversaire, node.score);
        clone.setScore(node.score);
        clone.enfants = new ArrayList<>(); // Initialize a new list for children
        clone.scoreAdversaire = node.scoreAdversaire;
        clone.scoreIA = node.scoreIA;

        return clone;
    }




    public int addScore(int score){
        this.score+=score;
        return this.score;
    }

    public int getScore() {
        return score;
    }

    public int getScoreIA() {
        return scoreIA;
    }

    public int getScoreAdversaire() {
        return scoreAdversaire;
    }
    public int getScoreIA() {
        return scoreIA;
    }

    public int getScoreAdversaire() {
        return scoreAdversaire;
    }
    public void setScore(int score) {
        this.score=score;
    }

    public Hand getHandIa() {
        return Ia;
    }

    public Hand getHandAdversaire() {
        return adversaire;
    }

    public static void printArbre(Node node, int level, PrintWriter writer) {
        if (node == null) {
            return;
        }

        // Indentation selon le niveau de profondeur
        for (int i = 0; i < level; i++) {
            writer.print("    ");
        }

        // Afficher les informations du nœud courant
        writer.println("Niveau " + level +"Carte joue par l'ia "+node.carteIA+" Carte joue par l'adversaire "+ node.Adversaire+ " | Carte Gagnante: " + node.CarteGagnante);

        // Appel récursif pour les enfants
        for (Node enfant : node.getEnfants()) {
            printArbre(enfant, level + 1, writer);
        }
    }
}


    public Hand getHandIa() {
        return Ia;
    }

    public Hand getHandAdversaire() {
        return adversaire;
    }



}
