package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Hand;
import org.example.Modele.PileDeScore;
import org.example.Modele.Plateau;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Node {

    private List<Node> enfants;
    private int score;
    public Plateau plateau;




    public Node (Plateau plateau){
        this.plateau=plateau;
        enfants=new ArrayList<>();
    }

    public Node(){
        enfants=new ArrayList<>();
    }

    public void setPlateau(Plateau plateau){
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




    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score=score;
    }


    /*public static void printArbre(Node node, int level, PrintWriter writer) {
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
    }*/
}


