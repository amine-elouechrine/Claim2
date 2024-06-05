package org.example.Modele;

import org.example.Patternes.Observable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Jeu extends Observable {

    Plateau plateau;
    GestionAnnuleRefaire g;
    ReglesDeJeu r;
    boolean estIA;
    /**
     * @brief Initialise un nouveau jeu avec des paramètres spécifiés.
     * @param ia Indique si le jeu est contre une intelligence artificielle.
     * @param nameJ1 Le nom du joueur 1.
     * @param nameJ2 Le nom du joueur 2.
     */
    public Jeu(boolean ia, String nameJ1, String nameJ2) {
        plateau = new Plateau();
        plateau.initialiserJeu(ia, nameJ1, nameJ2);
        g = new GestionAnnuleRefaire();
        g.addToHistory(getPlateau());
        r = new ReglesDeJeu();
    }
    /**
     * @brief Obtient la phase actuelle du jeu.
     * @return La phase actuelle du jeu.
     */
    public boolean getPhase() {
        return getPlateau().getPhase();
    }
    /**
     * @brief Bascule vers la phase suivante du jeu.
     */
    public void switchPhase() {
        getPlateau().switchPhase();
        switchHand();
    }
    /**
     * @brief Bascule vers le joueur suivant.
     */
    public void switchJoueur() {
        r.switchJoueur(getPlateau());
    }


    /**
     * @brief Bascule les mains des joueurs.
     */
    public void switchHand() {
        // Changer main joueur 1
        Hand main;
        main = getPlateau().getJoueur1().getHandScndPhase();
        getPlateau().getJoueur1().hand = main;

        // Changer main joueur 2
        main = getPlateau().getJoueur2().getHandScndPhase();
        getPlateau().getJoueur2().hand = main;
    }
    /**
     * @brief Obtient le joueur 2.
     * @return Le joueur 2.
     */
    public Player getJoueur2() {
        return getPlateau().getJoueur2();
    }
    /**
     * @brief Obtient le joueur courant.
     * @return Le joueur courant.
     */
    public Player getJoueurCourant() {
        return getPlateau().getJoueurCourant();
    }
    /**
     * @brief Obtient la main du joueur 1 de la première phase.
     * @return La main du joueur 1 de la première phase.
     */
    public Hand getHandJ1P1() {
        return getPlateau().getJoueur1().getHand();
    }
    /**
     * @brief Obtient la main du joueur 2 de la première phase.
     * @return La main du joueur 2 de la première phase.
     */
    public Hand getHandJ2P1() {
        return getPlateau().getJoueur2().getHand();
    }
    /**
     * @brief Obtient la main du joueur 1 de la deuxième phase.
     * @return La main du joueur 1 de la deuxième phase.
     */
    public Hand getHandJ1P2() {
        return getPlateau().getJoueur1().getHandScndPhase();
    }
    /**
     * @brief Obtient la main du joueur 2 de la deuxième phase.
     * @return La main du joueur 2 de la deuxième phase.
     */
    public Hand getHandJ2P2() {
        return getPlateau().getJoueur2().getHandScndPhase();
    }
    /**
     * @brief Obtient le plateau de jeu.
     * @return Le plateau de jeu.
     */
    public Plateau getPlateau() {
        return plateau;
    }
    /**
     * @brief Vérifie si la première phase du jeu est terminée.
     * @return Vrai si la première phase est terminée, sinon faux.
     */
    public boolean estFinPhase1() {
        return getPlateau().estFinPhase(getPlateau().getPhase());
    }
    /**
     * @brief Vérifie si la partie est terminée.
     * @return Vrai si la partie est terminée, sinon faux.
     */
    public boolean estFinPartie() {
        return getPlateau().isEndOfGame();
    }
    /**
     * @brief Vérifie si le jeu implique une intelligence artificielle.
     * @return Vrai si le jeu implique une IA, sinon faux.
     */
    public boolean estIA() {
        return estIA;
    }
    /**
     * @brief Obtient une liste de cartes sous forme de tableau d'entiers.
     * @param listeCarte La liste de cartes à convertir.
     * @return Le tableau d'entiers représentant la liste de cartes.
     */
    private int[][] getListeCarte(List<Card> listeCarte) {
        int[][] tableauCartes = new int[listeCarte.size()][2];
        int i = 0;
        for (Card carte : listeCarte) {
            tableauCartes[i][0] = carte.getValeur();
            tableauCartes[i][1] = carte.getFactionScore();
            i++;
        }
        return tableauCartes;
    }
    /**
     * @brief Obtient la main d'un joueur sous forme de tableau d'entiers.
     * @param main La main du joueur à convertir.
     * @return Le tableau d'entiers représentant la main du joueur.
     */
    public int[][] getHand(Hand main) {
        List<Card> cartes = main.getAllCards();
        return getListeCarte(cartes);
    }
    /**
     * @brief Obtient une liste de cartes jouables sous forme de tableau d'entiers.
     * @param carteAdversaire La carte de l'adversaire.
     * @param mainJoueur La main du joueur.
     * @return Le tableau d'entiers représentant la liste de cartes jouables.
     */
    public int[][] getListCardJouable(Card carteAdversaire, Hand mainJoueur) {
        List<Card> listeCarte = ReglesDeJeu.cartesJouables(carteAdversaire, mainJoueur);
        return getListeCarte(listeCarte);
    }
    /**
     * @brief Obtient la main du joueur 1 de la première phase sous forme de tableau d'entiers.
     * @return Le tableau d'entiers représentant la main du joueur 1 de la première phase.
     */
    public int[][] getMainJoueur1Phase1() {
        Hand main = getHandJ1P1();
        return getHand(main);
    }
    /**
     * @brief Obtient la main du joueur 2 de la première phase sous forme de tableau d'entiers.
     * @return Le tableau d'entiers représentant la main du joueur 2 de la première phase.
     */
    public int[][] getMainJoueur2Phase1() {
        Hand main = getHandJ2P1();
        return getHand(main);
    }
    /**
     * @brief Obtient la main du joueur 1 de la deuxième phase sous forme de tableau d'entiers.
     * @return Le tableau d'entiers représentant la main du joueur 1 de la deuxième phase.
     */
    public int[][] getMainJoueur1Phase2() {
        Hand main = getHandJ1P2();
        return getHand(main);
    }
    /**
     * @brief Obtient la main du joueur 2 de la deuxième phase sous forme de tableau d'entiers.
     * @return Le tableau d'entiers représentant la main du joueur 2 de la deuxième phase.
     */
    public int[][] getMainJoueur2Phase2() {
        Hand main = getHandJ2P2();
        return getHand(main);
    }
    /**
     * @brief Obtient les cartes jouables à partir d'une carte jouée et d'une main.
     * @param carteJoue La carte jouée.
     * @param main La main du joueur.
     * @return Le tableau d'entiers représentant les cartes jouables.
     */
    public int[][] getCarteJouable(Card carteJoue, Hand main) {
        return getListeCarte(ReglesDeJeu.cartesJouables(carteJoue, main));
    }
    /**
     * @brief Obtient les cartes jouables gagnantes à partir d'une carte jouée et d'une main.
     * @param carteJoue La carte jouée.
     * @param main La main du joueur.
     * @return Le tableau d'entiers représentant les cartes jouables gagnantes.
     */
    public int[][] getCarteJouableGagnante(Card carteJoue, Hand main) {
        return getListeCarte(ReglesDeJeu.cartesJouablesGagnant(carteJoue, ReglesDeJeu.cartesJouables(carteJoue, main), getPlateau()));
    }
    /**
     * @brief Obtient les cartes jouables perdantes à partir d'une carte jouée et d'une main.
     * @param carteJoue La carte jouée.
     * @param main La main du joueur.
     * @return Le tableau d'entiers représentant les cartes jouables perdantes.
     */
    public int[][] getCarteJouablePerdante(Card carteJoue, Hand main) {
        return getListeCarte(ReglesDeJeu.cartesJouablesPerdant(carteJoue, ReglesDeJeu.cartesJouables(carteJoue, main), getPlateau()));
    }
    /**
     * @brief Vérifie si une carte est jouable.
     * @param CarteAdverse La carte adverse.
     * @param indiceCarteJoue L'indice de la carte jouée.
     * @return Vrai si la carte est jouable, sinon faux.
     */
    public boolean estCarteJouable(Card CarteAdverse, int indiceCarteJoue) {
        List<Card> preselected = preselected(CarteAdverse, getPlateau().getJoueurCourant().getHand());
        return getPlateau().coupJouable(preselected, indiceCarteJoue, getPlateau().getJoueurCourant().getHand());
    }
    /**
     * @brief Obtient le score de faction de la carte affichée.
     * @return Le score de faction de la carte affichée.
     */
    public int getCarteAfficheeFactionScore() {
        return getPlateau().getCarteAffichee().getFactionScore();
    }
    /**
     * @brief Obtient la valeur de la carte affichée.
     * @return La valeur de la carte affichée.
     */
    public int getCarteAfficheeValeur() {
        return getPlateau().getCarteAffichee().getValeur();
    }
    /**
     * @brief Obtient le nombre de cartes d'une faction dans la pile de score du joueur 1.
     * @param faction La faction des cartes.
     * @return Le nombre de cartes de la faction dans la pile de score du joueur 1.
     */
    public int getNbCardFactionFromPileScoreJ1(String faction) {
        return getPlateau().getJoueur1().getPileDeScore().getCardFaction(faction).size();
    }
    /**
     * @brief Obtient le nombre de cartes d'une faction dans la pile de score du joueur 2.
     * @param faction La faction des cartes.
     * @return Le nombre de cartes de la faction dans la pile de score du joueur 2.
     */
    public int getNbCardFactionFromPileScoreJ2(String faction) {
        return getPlateau().getJoueur2().getPileDeScore().getCardFaction(faction).size();
    }
    /**
     * @brief Obtient la valeur maximale d'une faction dans la pile de score du joueur 1.
     * @param faction La faction des cartes.
     * @return La valeur maximale de la faction dans la pile de score du joueur 1.
     */
    public int getMaxValueoOfFactionFromPileScoreJ1(String faction) {
        return getPlateau().getJoueur1().getPileDeScore().maxValueOfFaction(faction);
    }
    /**
     * @brief Obtient la valeur maximale d'une faction dans la pile de score du joueur 2.
     * @param faction La faction des cartes.
     * @return La valeur maximale de la faction dans la pile de score du joueur 2.
     */
    public int getMaxValueoOfFactionFromPileScoreJ2(String faction) {
        return getPlateau().getJoueur2().getPileDeScore().maxValueOfFaction(faction);
    }
    /**
     * @brief Obtient le score de faction de la carte du joueur 1.
     * @return Le score de faction de la carte du joueur 1 s'il existe, sinon -1.
     */
    public int getCarteJoueur1F() {
        if (getPlateau().getCarteJoueur1() != null)
            return getPlateau().getCarteJoueur1().getFactionScore();
        else
            return -1;
    }
    /**
     * @brief Préselectionne les cartes jouables à partir d'une carte et d'une main.
     * @param carte La carte à partir de laquelle les cartes jouables sont préselectionnées.
     * @param hand La main du joueur.
     * @return La liste des cartes jouables.
     */
    public List<Card> preselected(Card carte, Hand hand) {
        return ReglesDeJeu.cartesJouables(carte, hand);
    }
    /**
     * @brief Obtient la valeur de la carte du joueur 1.
     * @return La valeur de la carte du joueur 1 s'il existe, sinon -1.
     */
    public int getCarteJoueur1V() {
        if (getPlateau().getCarteJoueur1() != null)
            return getPlateau().getCarteJoueur1().getValeur();
        else
            return -1;
    }
    /**
     * @brief Obtient le score de faction de la carte du joueur 2.
     * @return Le score de faction de la carte du joueur 2 s'il existe, sinon -1.
     */
    public int getCarteJoueur2F() {
        if (getPlateau().getCarteJoueur2() != null)
            return getPlateau().getCarteJoueur2().getFactionScore();
        else
            return -1;
    }
    /**
     * @brief Obtient la valeur de la carte du joueur 2.
     * @return La valeur de la carte du joueur 2 s'il existe, sinon -1.
     */
    public int getCarteJoueur2V() {
        if (getPlateau().getCarteJoueur2() != null)
            return getPlateau().getCarteJoueur2().getValeur();
        else
            return -1;
    }
    /**
     * @brief Vérifie si une carte a été jouée par le joueur 1.
     * @return Vrai si une carte a été jouée par le joueur 1, sinon faux.
     */
    public boolean estCarteJoueJ1() {
        return (getPlateau().getCarteJoueur1() != null);
    }
    /**
     * @brief Vérifie si une carte a été jouée par le joueur 2.
     * @return Vrai si une carte a été jouée par le joueur 2, sinon faux.
     */
    public boolean estCarteJoueJ2() {
        return (getPlateau().getCarteJoueur2() != null);
    }
    /**
     * @brief Effectue un tour de jeu.
     */
    public void playTrick() {
        if (getPhase()) {
            Card carteGagnante;
            if (getPlateau().getJoueurCourant() == getPlateau().getJoueur1())
                carteGagnante = ReglesDeJeu.carteGagnante(getPlateau().getCarteJoueur2(), getPlateau().getCarteJoueur1(), getPlateau());
            else
                carteGagnante = ReglesDeJeu.carteGagnante(getPlateau().getCarteJoueur1(), getPlateau().getCarteJoueur2(), getPlateau());
            getPlateau().attribuerCarteFirstPhase(carteGagnante, r);
            if (estFinPhase1()) {
                switchPhase();
            }
            if (getPhase()) {
                getPlateau().carteAffichee = getPlateau().getPioche().getCard();
            }
        } else {
            Card carteGagnante;
            if (getPlateau().getJoueurCourant() == getPlateau().getJoueur1())
                carteGagnante = ReglesDeJeu.carteGagnante(getPlateau().getCarteJoueur2(), getPlateau().getCarteJoueur1(), getPlateau());
            else
                carteGagnante = ReglesDeJeu.carteGagnante(getPlateau().getCarteJoueur1(), getPlateau().getCarteJoueur2(), getPlateau());
            getPlateau().attribuerCarteSecondPhase(carteGagnante, r);

        }
    }
    /**
     * @brief Obtient le joueur gagnant de la manche.
     * @return Le joueur gagnant de la manche.
     */
    public Player getJoueurGagnant() {
        return  r.determinerGagnantManche(plateau.getJoueur1(),plateau.getJoueur2(),plateau.getCarteJoueur1(),plateau.getCarteJoueur2(),plateau);
    }
    /**
     * @brief Réinitialise les cartes jouées par les joueurs.
     */
    public void setCarteJouer() {
        getPlateau().setCarteJoueur1(null);
        getPlateau().setCarteJoueur2(null);
    }

    /**
     * @brief Obtient le nom du joueur.
     * @param joueur Le joueur dont le nom est obtenu.
     * @return Le nom du joueur.
     */
    public String getNomJoueur(Player joueur) {
        return joueur.getName();
    }
    /**
     * @brief Annule le dernier coup joué.
     */
    public void annulerCoup() {
        g.annuler(getPlateau());
    }
    /**
     * @brief Refait le dernier coup annulé.
     */
    public void refaireCoup() {
        g.refaire(getPlateau());
    }
    /**
     * @brief Efface la pile des coups annulés.
     */
    public void clearStackAnnule() {
        g.clearStackAnnule();
    }
    /**
     * @brief Efface la pile des coups à refaire.
     */
    public void clearStackRefaire() {
        g.clearStackRefaire();
    }
    /**
     * @brief Ajoute une action à l'historique du jeu.
     */
    public void addAction() {
        g.addToHistory(getPlateau());
    }
    /**
     * @brief Sauvegarde l'état actuel du jeu dans un fichier.
     * @param filename Le nom du fichier de sauvegarde.
     * @throws FileNotFoundException Si le fichier spécifié n'est pas trouvé.
     */
    public void sauve(String filename) throws FileNotFoundException {
        g.sauve(filename, getPlateau());
    }
    /**
     * @brief Restaure l'état du jeu à partir d'un fichier de sauvegarde.
     * @param filename Le nom du fichier à partir duquel restaurer l'état du jeu.
     * @throws IOException En cas d'erreur d'entrée/sortie lors de la restauration.
     */
    public void restaure(String filename) throws IOException {
        this.plateau = g.restaure(filename);
    }
    /**
     * @brief Obtient le joueur 1.
     * @return Le joueur 1.
     */
    public Player getJoueur1() {
        return getPlateau().getJoueur1();
    }
    /**
     * @brief Obtient le nom du joueur gagnant.
     * @return Le nom du joueur gagnant.
     */
    public String getJoueurNomGagnant() {
        return r.determinerGagnantPartie(getJoueur1(),getJoueur2());
    }
    /**
     * @brief Fournit de l'aide en fonction de la phase actuelle du jeu.
     * @return Un message d'aide en fonction de la phase de jeu.
     */
    public String help() {
        if (plateau.getPhase()) {//si phase 1
            if (plateau.estLeader2()) {
                return "Si la carte affichée au milieu est une carte que vous pensez être utile pour la phase 2, alors vous pouvez jouer une carte de votre main qui vous permettra de gagner la carte affichée";
            } else {
                return "Pour gagner la carte affiché au milieu, il faut jouer une carte de la meme faction, les cartes qui vous permettent de gagner la manche sont en vert";
            }
        } else {
            if (plateau.estLeader2()) {
                return "C'est à vous de commencer la manche, jouer une carte que vous pensez être utile pour gagner la manche et dominer la faction";
            } else {
                //phase 2
                return "C'est à vous de jouer, il faut jouer une carte de la même faction que la carte de l'adversaire pour gagner la manche ou un doppleganger";
            }
        }
    }
}