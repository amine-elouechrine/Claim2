package org.example.Controleur;

import org.example.Modele.Card;
import org.example.Vue.CollecteurEvenements;
import org.example.Modele.Jeu;
import org.example.Patternes.Observable;

public class ControleurMediateur implements CollecteurEvenements {

    Jeu jeu;

    Card carteLeader;
    int IndiceCarteLeader;

    boolean jouable = true;

    public ControleurMediateur(Jeu j) {
        jeu = j;
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
        return jeu.getCarteJouable(carteLeader, jeu.getPlateau().getJoueurCourant().getHand());
    }
    public String getNomJoueurCourant() {
        return jeu.getNomJoueur(jeu.getPlateau().getJoueurCourant());
    }

    public void clicSouris(int index) {
        if (index == -1) {
            ;
        } else {
            if (jeu.estFinPartie()) {
                // Calcul des scores
            }

            if(getPhase()) {
                // Application des règles de jeu pour la selection de carte
                if(carteLeader != null) {
                   jouable = jeu.estCarteJouable(IndiceCarteLeader, index);
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else {
                        carteLeader = carteJoue;
                        IndiceCarteLeader = index;
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
                    jouable = jeu.estCarteJouable(IndiceCarteLeader, index);
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else {
                        carteLeader = carteJoue;
                        IndiceCarteLeader = index;
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

                // Application des règles de jeu pour la selection de carte
                if(carteLeader != null) {
                    jouable = jeu.estCarteJouable(IndiceCarteLeader, index);
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else {
                        carteLeader = carteJoue;
                        IndiceCarteLeader = index;
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
                    jouable = jeu.estCarteJouable(IndiceCarteLeader, index);
                }

                if(jouable) {
                    Card carteJoue = jeu.getPlateau().jouerCarte(index);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        jeu.playTrick();
                        jeu.setCarteJouer();
                        carteLeader = null;
                    } else {
                        carteLeader = carteJoue;
                        IndiceCarteLeader = index;
                        jeu.switchJoueur();
                    }
                }
            }
        }
        jeu.metAJour();
    }
}
