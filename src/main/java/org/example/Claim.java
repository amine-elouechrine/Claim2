package org.example;

import org.example.Controleur.ControleurMediateur;
import org.example.IA.IA;
import org.example.IA.Intermediare;
import org.example.Modele.Jeu;
import org.example.Vue.CollecteurEvenements;
import org.example.Vue.InterfaceGraphique;

public class Claim {
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        IA facile = new Intermediare();
        CollecteurEvenements control = new ControleurMediateur(jeu, facile);
        InterfaceGraphique.demarrer(jeu, control);
    }
}
