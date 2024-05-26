package org.example.IA;

import org.example.Modele.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.example.IA.IA.determinerGagnantMancheIA;

public class IA_intermediareVSIA_Facile {
    static IA Facile;
    static IA Intermediare;
    static Plateau plateau;
    static Player FacileP;
    static Player IntermediareP;
    static Cards pioche;
    static Hand handFacile;
    static Hand handIntermediare;
    static int nb_match_gagne_facile = 0;
    static int nb_match_gagne_intermediare = 0;

    private IA_intermediareVSIA_Facile() {
    }

    public static void Test_IA_F_VS_I() {
        ReglesDeJeu r = new ReglesDeJeu();
        Random rand = new Random();
        int i = 0;
        while (i < 13) {//phase 1
            System.out.println("i= " + i);
            System.out.println(plateau.getJoueur1().getHand().size()+"--"+plateau.getJoueur2().getHand().size());
            if (plateau.getJoueurCourant().getName().equals("Joueur 1")){//Joueur1=Facile
                //Card cartejouefacile=Facile.jouer_coup_phase1(null,false,null);
                Card cartejouefacile = Facile.jouerCoupPhase1(plateau);
                plateau.setCarteJoueur1(cartejouefacile);
                plateau.getJoueur1().getHand().removeCard(cartejouefacile);
                System.out.println("Carte jouée par Facile : " + cartejouefacile);
                plateau.switchJoueur();
                //Facile.hand.removeCard(cartejouefacile);
                //l'ia intermediare repond avec une carte
                //Intermediare.hand.printHand();
                //Card carteadversaire=Intermediare.jouer_coup_phase1(null,true,cartejouefacile);
                Card carteadversaire = Intermediare.jouerCoupPhase1(plateau);
                plateau.setCarteJoueur2(carteadversaire);
                plateau.getJoueur2().getHand().removeCard(carteadversaire);
                System.out.println("Carte jouée par Intermediare : " + carteadversaire);
                //Intermediare.hand.removeCard(carteadversaire);


                Card winningCard = IA.determinerCarteGagnanteIA(cartejouefacile, carteadversaire);
                if (winningCard == null) {//le leader gagne en cas d'egalite
                    winningCard = cartejouefacile;
                }
                System.out.println("Carte gagnante : " + winningCard);
                //plateau.setJoueurCourant(r.determinerGagnantManche(FacileP,IntermediareP,cartejouefacile,carteadversaire));
                IA gagnantMancheIA = determinerGagnantMancheIA(Facile, Intermediare, cartejouefacile, carteadversaire, plateau);
                System.out.println("Le gagnant de la manche est : " + gagnantMancheIA.Name);
                plateau.attribuerCarteFirstPhase(winningCard, r);
                System.out.println("\n" + i);
                i++;

            } else {
                //Card cartejoueintermediare=Intermediare.jouer_coup_phase1(null,false,null);
                Card cartejoueintermediare = Intermediare.jouerCoupPhase1(plateau);
                plateau.setCarteJoueur2(cartejoueintermediare);
                plateau.getJoueur2().getHand().removeCard(cartejoueintermediare);
                System.out.println("Carte jouée par Intermediare : " + cartejoueintermediare);
                //Intermediare.hand.removeCard(cartejoueintermediare);
                //Intermediare..removeCard(cartejoueintermediare);
                //Card carteadversaire=Facile.jouer_coup_phase2(Facile.hand,true,cartejoueintermediare);
                plateau.switchJoueur();
                Card carteadversaire = Facile.jouerCoupPhase1(plateau);
                plateau.setCarteJoueur1(carteadversaire);
                plateau.getJoueur1().getHand().removeCard(carteadversaire);
                System.out.println("Carte jouée par Facile : " + carteadversaire);
                //Facile.hand.removeCard(carteadversaire);
                //FacileP.removeCardFromHand(carteadversaire);
                Card winningCard = IA.determinerCarteGagnanteIA(cartejoueintermediare, carteadversaire);
                if (winningCard == null) {//le leader qui gagne la manche en cas d'egalite
                    winningCard = cartejoueintermediare;
                }
                IA gagnantMancheIA = determinerGagnantMancheIA(Facile, Intermediare, new Card(cartejoueintermediare.getValeur(),cartejoueintermediare.getFaction()),new Card( carteadversaire.getValeur(),carteadversaire.getFaction()), plateau);
                plateau.attribuerCarteFirstPhase(winningCard, r);
                System.out.println("Carte gagnante : " + winningCard);
                System.out.println("Le gagnant de la manche est : " + gagnantMancheIA.Name);
                System.out.println("\n" + i);
                /*System.out.println("::::>"+plateau.getJoueurCourant().getHandScndPhase().size());
                plateau.switchJoueur();
                System.out.println("::::>"+plateau.getJoueurCourant().getHandScndPhase().size());
                plateau.switchPhase();*/
                i++;
            }
            if(i!=13){
                plateau.setCardAffiche(plateau.getPioche().getCard());
            }


        }

        System.out.println("Phase 1 terminée");
        //System.out.println("Phase 2"+Facile.handScndPhase.size())+" - "+Intermediare.handScndPhase.size());
        //phase 2
        i = 0;

        plateau.setCardAffiche(null);
        plateau.setCarteJoueur1(null);
        plateau.setCarteJoueur2(null);
        plateau.setHand(plateau.getJoueur1().getHandScndPhase(), plateau.getJoueur2().getHandScndPhase());
        plateau.switchPhase();
        while(i<13){
            System.out.println("i= "+i);
           // System.out.println("----+-------->"+plateau.getJoueur1().getHand().size()+"--"+plateau.getJoueur2().getHand().size());
            if(plateau.getJoueurCourant().getName().equals("Joueur 1")){


                //Card cartejouefacile=Facile.jouer_coup_phase2(null,false,null);
                Card cartejouefacile=Facile.jouerCoupPhase2(plateau);
                plateau.setCarteJoueur1(cartejouefacile);
                plateau.getJoueur1().getHand().removeCard(cartejouefacile);
                plateau.switchJoueur();
                System.out.println("Carte jouée par Facile : "+cartejouefacile);
                //Facile.hand.removeCard(cartejouefacile);
                //plateau.joueurCourant.getHandScndPhase().removeCard(cartejouefacile);

                //Card carteadversaire=Intermediare.jouer_coup_phase2(null,true,cartejouefacile);
                Card carteadversaire=Intermediare.jouerCoupPhase2(plateau);
                plateau.setCarteJoueur2(carteadversaire);
                plateau.getJoueur2().getHand().removeCard(carteadversaire);

                System.out.println("Carte jouée par Intermediare : "+carteadversaire);
                //Intermediare.hand.removeCard(carteadversaire);
                //plateau.getJoueurNonCourant().getHandScndPhase().removeCard(carteadversaire);

                Card winningCard = IA.determinerCarteGagnanteIA(cartejouefacile, carteadversaire);
                if(winningCard==null){//le leader qui gagne la manche en cas d'egalite
                    winningCard=cartejouefacile;
                }
                System.out.println("Carte gagnante : "+winningCard);

                IA gagnantMancheIA= determinerGagnantMancheIA(Facile,Intermediare,cartejouefacile,carteadversaire,plateau);
                System.out.println("Le gagnant de la manche est : "+gagnantMancheIA.Name);
                plateau.attribuerCarteSecondPhase(winningCard,r);
                //plateau.getJoueurNonCourant().addPileDeScore(pioche.getCard());
                //System.out.println("\n"+i);
                i++;
            }else{
                //Card cartejoueintermediare=Intermediare.jouer_coup_phase2(null,false,null);
                Card cartejoueintermediare=Intermediare.jouerCoupPhase2(plateau);
                System.out.println("Carte jouée par Intermediare : "+cartejoueintermediare);
                plateau.setCarteJoueur2(cartejoueintermediare);
                plateau.getJoueur2().getHand().removeCard(cartejoueintermediare);
                plateau.switchJoueur();

                //Intermediare.hand.removeCard(cartejoueintermediare);

                //Card carteadversaire=Facile.jouer_coup_phase2(null, true,cartejoueintermediare);
                Card carteadversaire=Facile.jouerCoupPhase2(plateau);
                System.out.println("Carte jouée par Facile : "+carteadversaire);
                plateau.setCarteJoueur1(carteadversaire);
                plateau.getJoueur1().getHand().removeCard(carteadversaire);

                //Facile.hand.removeCard(carteadversaire);

                Card winningCard = IA.determinerCarteGagnanteIA(cartejoueintermediare, carteadversaire);
                if(winningCard==null){
                    winningCard=cartejoueintermediare;
                }

                System.out.println("Carte gagnante : "+winningCard);
                IA gagnantMancheIA= determinerGagnantMancheIA(Facile,Intermediare,cartejoueintermediare,carteadversaire,plateau);
                System.out.println("Le gagnant de la manche est : "+gagnantMancheIA.Name);
                plateau.attribuerCarteSecondPhase(winningCard,r);

                //plateau.getJoueurNonCourant().addPileDeScore(pioche.getCard());
                //System.out.println("\n"+i);
                i++;
            }



        }
        IA Gagnat = IA.determinerGagnantPartieIA(Facile, Intermediare);
        System.out.println("Le gagnant est : " + Gagnat);
        if (Gagnat.Name.equals("Facile")) {
            nb_match_gagne_facile++;
        } else {
            nb_match_gagne_intermediare++;

        }

    }

    public static void main(String[] args) {
        int i = 0;
        plateau = new Plateau();
        Facile=new Facile();
        Intermediare=new Intermediare();
       while(i<100000){
            plateau.initialiserJeu();
            Test_IA_F_VS_I();
            i++;
       }
        System.out.println("Le nombre de matchs gagnés par Facile est : " + nb_match_gagne_facile);
        System.out.println("Le nombre de matchs gagnés par Intermediare est : " + nb_match_gagne_intermediare);
    }

}
