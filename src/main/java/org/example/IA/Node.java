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
    private List<Node> enfants;
    private int scoreIA;
    private int scoreAdversaire;
    private int score;
    public boolean IsIaTurn;
    //tableau d'entiers
    PileDeScore pileDeScoreIa;
    PileDeScore pileDeScoreAdversaire;
    public Card CarteGagnante;


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
    }
    public void afficherArbre(String prefix) {
        System.out.println(prefix + (IsIaTurn ? "IA" : "Adversaire") + " - Score: " + score );
        for (Node enfant : enfants) {
            enfant.afficherArbre(prefix + "  ");
        }
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

    public Node clone(Card carteIA,Card carteAdversaire){
        Node clone= new Node(Ia,adversaire,IsIaTurn,carteIA,carteAdversaire,pileDeScoreIa,pileDeScoreAdversaire,score);
        clone.setScore(score);
        clone.IsIaTurn=IsIaTurn;
        clone.enfants=enfants;
        clone.scoreAdversaire=scoreAdversaire;
        clone.scoreIA=scoreIA;
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
    public void setScore(int score) {
        this.score=score;
    }

    public Hand getHandIa() {
        return Ia;
    }

    public Hand getHandAdversaire() {
        return adversaire;
    }



}
