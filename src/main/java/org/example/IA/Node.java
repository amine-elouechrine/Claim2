package org.example.IA;

import org.example.Modele.Plateau;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

public class Node {

    private List<Node> enfants;
    public int score;
    public boolean IsIaTurn;
    public Plateau plateau;


    public Node() {
        enfants=new ArrayList<>();

    }

    public Node (Plateau plateau){
        this.plateau=plateau;
        enfants=new ArrayList<>();
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


    public void afficherArbre(String prefix) {
        System.out.println(prefix + (IsIaTurn ? "IA" : "Adversaire") + " - Score: " + score );
        for (Node enfant : enfants) {
            enfant.afficherArbre(prefix + "  ");
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score=score;
    }


    public void afficherArbreProfondeur(PrintWriter writer, String prefix, int profondeur) {
        if (profondeur < 0) {
            return; // Si la profondeur est négative, on ne fait rien
        }
        writer.println("--------------------------------------------");
        writer.println(prefix +plateau.getJoueurCourant().getName());
        writer.println(prefix +plateau.getJoueur1().getName());
        writer.println(prefix +plateau.getCarteJoueur1());
        writer.println(prefix +plateau.getJoueur2().getName());
        writer.println(prefix +plateau.getCarteJoueur2());
        if (profondeur > 0) {
            for (Node enfant : enfants) {
                enfant.afficherArbreProfondeur(writer, prefix + "       ", profondeur - 1);
            }
        }
    }
}

