package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Plateau;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Node {

    public int score;
    public Plateau plateau;
    Card carteJoueeParIa; // carte jouée par l'IA


    public Node() {

    }

    public Node(Plateau plateau, Card carteJoueeParIa) {
        this.plateau = plateau;
        this.score = 0;
        this.carteJoueeParIa = carteJoueeParIa;
    }

    public Node (Plateau plateau ){
        this.plateau=plateau;
    }


    // Constructeur de copie
    public Node(Node other) {
        this.score = other.score;
        this.plateau = other.plateau != null ? new Plateau(other.plateau) : null;

    }

    public Node clone() {
        return new Node(this);
    }

    // l'ia doit etre toujours le joueur1 et l'adversaire le joueur2
    public Card getCarteAI() {
        return plateau.getCarteJoueur1();
    }

    public Plateau getPlateau(){
        return this.plateau ;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCarteJoueeParIa(Card carte) {
        this.carteJoueeParIa = carte;
    }

    public Card getCarteJoueeParIa() {
        return carteJoueeParIa;
    }

}