package org.example.Controleur;

import org.example.Modele.Card;
import org.example.Modele.GestionAnnuleRefaire;
import org.example.Vue.CollecteurEvenements;
import org.example.Modele.Jeu;
import org.example.Patternes.Observable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControleurMediateur implements CollecteurEvenements {

    Jeu jeu;

    Card carteLeader;

    GestionAnnuleRefaire g;

    boolean jouable = true;

    public ControleurMediateur(Jeu j) {
        jeu = j;
        g = new GestionAnnuleRefaire(jeu.getPlateau());
    }

    public boolean getPhase() {
        return jeu.getPhase();
    }

    public int getNbCardsJ1P1() {
        return jeu.getHandJ1P1().size();
    }

    public int getNbCardsJ2P1() {
        return jeu.getHandJ2P1().size();
    }

    public int getNbCardsJ1P2() {
        return jeu.getHandJ1P2().size();
    }

    public int getNbCardsJ2P2() {
        return jeu.getHandJ2P2().size();
    }

    public int[][] getHandJ1P1() {
        return jeu.getMainJoueur1Phase1();
    }

    public int[][] getHandJ2P1() {
        return jeu.getMainJoueur2Phase1();
    }

    public int[][] getHandJ1P2() {
        return jeu.getMainJoueur1Phase2();
    }

    public int[][] getHandJ2P2() {
        return jeu.getMainJoueur2Phase2();
    }

    public int[][] getMainJoueurCourant() {
        return jeu.getHand(jeu.getPlateau().getJoueurCourant().getHand());
    }

    public int[][] getCarteJouable() {
        if(carteLeader != null) {
            return jeu.getCarteJouable(carteLeader, jeu.getPlateau().getJoueurCourant().getHand());
        }else{
            return getMainJoueurCourant();
        }
    }
    public String getNomJoueurCourant() {
        return jeu.getNomJoueur(jeu.getPlateau().getJoueurCourant());
    }
    @Override
    public void refaire(){
        g.refaire();
        jeu.metAJour();
    }
    @Override
    public void annuler(){
        g.annuler();;
        jeu.metAJour();
    }
    @Override
    public void sauvegarder(String filename ){
        g.sauve(filename);
        jeu.metAJour();
    }
    @Override
    public void restaure(String filename ) throws IOException  {
        g.restaure(filename);
        jeu.metAJour();
    }


    public void clicSouris(int index) {
        if (index == -1) {
            ;
        } else {
            if (jeu.estFinPartie()) {
                // Calcul des scores
            }
            System.out.println("indice : " + index);
            if(getPhase()) {

                // Application des règles de jeu pour la selection de carte
                if(carteLeader != null) {
                    jouable = jeu.estCarteJouable(carteLeader, index);
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else if ((jeu.estCarteJoueJ1() && !jeu.estCarteJoueJ2()) ||
                            (!jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2())) {
                        carteLeader = carteJoue;
                        jeu.switchJoueur();
                    }
                }
                // Ajouter temporisation / animation

                // L'IA joue une carte
                // IA.joue() ?

                // Ajouter temporisation / animation pour la carte jouer par l'IA

                // On joue le plie
                // jeu.playTrick();

                // Ajouter temporisation / Animation pour la bataille et l'attribution des cartes après le plie

                // jeu.setCarteJouer();
            }
            else {
                // Application des règles de jeu pour la selection de carte
                if(carteLeader != null) {
                    jouable = jeu.estCarteJouable(carteLeader, index);
                    for(Map.Entry<String ,List<Card>> entry :jeu.getPlateau().getJoueurCourant().getPileDeScore().getPileDeScore().entrySet()){
                        String key = entry.getKey();
                        List<Card> cards = entry.getValue();
                        System.out.println(key+":"+cards.size());
                        for(Card card : cards){
                            System.out.println(card.getFaction()+":"+card.getValeur());
                        }

                    }
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else {
                        carteLeader = carteJoue;
                        jeu.switchJoueur();
                    }
                }
            }
        }
        jeu.metAJour();
    }

    public void clicSourisJ2(int index) {

        if (index == -1) {
            ;
        } else {
            if (getPhase()) {
                System.out.println("indice : " + index);
                // Application des règles de jeu pour la selection de carte
                if(carteLeader != null) {
                    jouable = jeu.estCarteJouable(carteLeader, index);
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else {
                        carteLeader = carteJoue;
                        jeu.switchJoueur();
                    }
                }
                // Ajouter temporisation / animation

                // L'IA joue une carte
                // IA.joue() ?

                // Ajouter temporisation / animation pour la carte jouer par l'IA

                // On joue le plie
                // jeu.playTrick();

                // Ajouter temporisation / Animation pour la bataille et l'attribution des cartes après le plie

                // jeu.setCarteJouer();
            } else {
                // Application des règles de jeu pour la selection de carte
                if(carteLeader != null) {
                    jouable = jeu.estCarteJouable(carteLeader, index);
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else {
                        carteLeader = carteJoue;
                        jeu.switchJoueur();
                    }
                }
            }
        }
        jeu.metAJour();
    }
}
