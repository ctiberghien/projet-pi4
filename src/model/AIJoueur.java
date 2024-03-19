package model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

public class AIJoueur extends Joueur {

    Plateau plat;
    Coordonnee Ebase;
    Coordonnee MyBase;
    Pion lastPion;
    Coordonnee lastCoordonnee;
    public boolean haswin;

    public AIJoueur(String nom, Color couleur, int turn) {
        super(nom, couleur, turn);
        haswin = false;
        lastPion = null;
        lastCoordonnee = null;

    }

    public void setPlat(Plateau plat) {
        this.plat = plat;
        Ebase = getEnemyBaseCoord();
    }

    public Coordonnee getEnemyBaseCoord() {
        if (plat != null) {
            for (Coordonnee c : plat.plat_jeu.keySet()) {
                if (plat.plat_jeu.get(c).tuile != null && plat.plat_jeu.get(c).tuile.nbBase != turn
                        && plat.plat_jeu.get(c).tuile.nbBase > 0) {
                    return c;
                }
            }
        }
        return null;
    }

    public void tri_tabPion() {
        int taille = this.pions.size();

        for (int i = 1; i < taille; i++) {
            Pion index = this.pions.get(i);
            int j = i - 1;

            while (j >= 0 && this.getPionScore(this.pions.get(j)) > this.getPionScore(index)) {
                this.pions.set(j + 1, this.pions.get(j));
                j--;
            }
            this.pions.set(j + 1, index);
        }
    }

    public void shuffle_tabPion() {
        int taille = this.pions.size();
        for (int i = 0; i < taille; i++) {
            Random rand = new Random();
            int r = rand.nextInt(taille);
            Pion tmp = this.pions.get(i);
            this.pions.set(i, this.pions.get(r));
            this.pions.set(r, tmp);
        }
    }

    public boolean noPerles(Pion pion) {
        return pion.perles.isEmpty();
    }

    public Coordonnee canMove1Case(Pion pion) {
        if (this.noPerles(pion)) {
            return null;
        }
        Couleur perleAjouer = pion.perles.peek();
        Coordonnee res = null;
        Coordonnee[] nextTCoordonnees = getNextCoordonnees(pion.coordonnee);
        for (int i = 0; i < nextTCoordonnees.length; i++) {
            // On vérifie que cette case existe dans le plateau
            if (plat.plat_jeu.containsKey(nextTCoordonnees[i])) {
                // On vérifie que la bordure de la case permet de se déplacer vers cette case
                if (plat.plat_jeu.get(pion.coordonnee).couleurBorder[i] == perleAjouer) {
                    // On vérifie que aucun pion n'est présent sur la case de destination
                    if (plat.plat_jeu.get(nextTCoordonnees[i]).pion == null) {
                        // On verifie qu'elle va soit sur une case libre et qui n'est pas sa base
                        if (plat.plat_jeu.get(nextTCoordonnees[i]).tuile == null
                                || (plat.plat_jeu.get(nextTCoordonnees[i]).tuile != null
                                        && plat.plat_jeu.get(nextTCoordonnees[i]).tuile.nbBase != pion.joueur.turn)) {
                            if (res == null
                                    && this.getCoordScore(nextTCoordonnees[i]) <= this.getCoordScore(pion.coordonnee)) {
                                res = nextTCoordonnees[i];
                            } else if (this.getCoordScore(nextTCoordonnees[i]) < this.getCoordScore(res)) {
                                res = nextTCoordonnees[i];

                            }
                        }
                    }
                }
            }
        }
        return res;

    }

    public static Coordonnee[] getNextCoordonnees(Coordonnee c) {
        Coordonnee[] res = new Coordonnee[6];
        for (int i = 0; i < Case.couleurCoordonneesIndex.length; i++) {
            res[i] = new Coordonnee(Case.couleurCoordonneesIndex[i].px + c.px,
                    Case.couleurCoordonneesIndex[i].py + c.py);
        }
        return res;
    }

    /*
     * public Pion[] getNoPerlesAndNextToAnother() {
     * Pion[] res = null;
     * // En theorie la liste des pions sera triée selon la fonction play (soit du
     * plus
     * // proche , soit au pif )
     * for (Pion p : this.pions) {
     * Coordonnee tmp = isNextToPion(plat, p);
     * if (p.perles.isEmpty() && tmp != null) {
     * res = new Pion[2];
     * res[0] = p;
     * res[1] = tmp;
     * }
     * }
     * return res;
     * }
     */

    /*
     * public Coordonnee isNextToPion(Plateau p, Pion pion) {
     * Coordonnee[] tmpcoord = getNextCoordonnees(pion.coordonnee);
     * for (Coordonnee tmp : tmpcoord) {
     * if (p.plat_jeu.containsKey(tmp)) {
     * if (p.plat_jeu.get(tmp).pion != null && p.plat_jeu.get(tmp).pion.joueur !=
     * this) {
     * return tmp;
     * }
     * }
     * }
     * return null;
     * }
     */
    /*
     * public Coordonnee CoordJump(Pion p, Coordonnee from) {
     * Coordonnee res = null;
     * Coordonnee[] nextTCoordonnees = getNextCoordonnees(from);
     * for (int i = 0; i < nextTCoordonnees.length; i++) {
     * // On vérifie que cette case existe dans le plateau
     * if (plat.plat_jeu.containsKey(nextTCoordonnees[i])) {
     * // On vérifie que aucun pion n'est présent sur la case de destination
     * if (plat.plat_jeu.get(nextTCoordonnees[i]).pion == null) {
     * // On verifie qu'elle va soit sur une case libre et qui n'est pas sa base
     * if (plat.plat_jeu.get(nextTCoordonnees[i]).tuile == null
     * || (plat.plat_jeu.get(nextTCoordonnees[i]).tuile != null
     * && plat.plat_jeu.get(nextTCoordonnees[i]).tuile.nbBase != p.joueur.turn)) {
     * if (res == null
     * && this.getCoordScore(nextTCoordonnees[i]) <=
     * this.getCoordScore(p.coordonnee)) {
     * res = nextTCoordonnees[i];
     * } else if (this.getCoordScore(nextTCoordonnees[i]) < this.getCoordScore(res))
     * {
     * res = nextTCoordonnees[i];
     * 
     * }
     * }
     * }
     * }
     * }
     * return res;
     * 
     * }
     */

    public void play() {

        LinkedList<Couleur> perleADeplacer = new LinkedList<>();

        Random rand = new Random();
        int r = rand.nextInt(10);
        if (r <= 8) {
            r = rand.nextInt(r + 1);
            if (r == 0) {
                // On melange l'ordre des pions
                this.shuffle_tabPion();
            } else {
                // On tri les pions du joueur par distance par rapport a la base ennemie
                this.tri_tabPion();
            }

            // On teste si il peut jouer avec au moins l'un des 3 pions
            for (int i = 0; i < this.pions.size(); i++) {
                Coordonnee tmp = this.canMove1Case(pions.get(i));

                if (tmp != null) {
                    // System.out.println("pion numero " + i + " " + tmp);
                    if (!plat.deplacementPionIA(pions.get(i), tmp, perleADeplacer)) {
                        // en theorie n'arrive jamais ici si Julien a bien fait son code :woofwoof:
                    } else {

                        lastCoordonnee = tmp;
                        lastPion = pions.get(i);
                        // On vérifie si l'IA a gagner
                        if (plat.plat_jeu.get(lastCoordonnee).hasTuile()) {
                            if (plat.plat_jeu.get(lastCoordonnee).getTuile().getNbBase() != 0) {
                                haswin = true;
                            }
                        }
                        tmp = this.canMove1Case(pions.get(i));
                        // System.out.println(tmp + " de coord : " + this.getCoordScore(tmp));

                        while (tmp != null && plat.deplacementPionIA(pions.get(i), tmp, perleADeplacer)) {
                            // System.out.println("pion numero " + i + " " + tmp);
                            lastCoordonnee = tmp;
                            lastPion = pions.get(i);
                            // On vérifie si l'IA a gagner
                            if (plat.plat_jeu.get(lastCoordonnee).hasTuile()) {
                                if (plat.plat_jeu.get(lastCoordonnee).getTuile().getNbBase() != 0) {
                                    haswin = true;
                                }
                            }
                            tmp = this.canMove1Case(pions.get(i));
                            // System.out.println(tmp + " de coord : " + this.getCoordScore(tmp));
                        }

                        // System.out.println(perleADeplacer);
                        LinkedList<Pion> tmp2 = this.getPionsExept(pions.get(i));
                        this.replacerPerles(perleADeplacer, tmp2);
                        return;
                    }
                }
            }
        }
        // Si on sort de la boucle alors cela signifie que aucun des pions n'a pu jouer
        // on déplace donc une perle uniquement
        shuffle_tabPion();
        shiftColor();
        // System.out.println("Shifttted");
    }

    public LinkedList<Pion> getPionsExept(Pion NotThispion) {
        LinkedList<Pion> res = new LinkedList<>();
        for (int i = 0; i < this.pions.size(); i++) {
            if (this.pions.get(i)!= NotThispion) {
                res.add(this.pions.get(i));
            }
        }
        return res;
    }

    public void shiftColor() {
        for (Pion p : this.pions) {
            if (!p.perles.isEmpty()) {
                LinkedList<Couleur> tmp = new LinkedList<>();
                tmp.add(p.perles.pop());
                LinkedList<Pion> lp = getPionsExept(p);
                replacerPerles(tmp, lp);
                return;
            }
        }
    }

    public void replacerPerles(LinkedList<Couleur> perleADeplacer, LinkedList<Pion> PionLibre) {
        if (perleADeplacer == null) {
            return;
        }
        while (!perleADeplacer.isEmpty()) {
            Couleur tmp = perleADeplacer.pollFirst();
            for (Pion ptmp : PionLibre) {
                if (ptmp.addPerle(tmp)) {
                    break;
                }
            }

        }

    }

    /**
     * @return Le score du pion noté par rapport a sa distance vis a vis de la base
     *         ennemie
     */
    public int getPionScore(Pion pion) {
        if (pion != null) {
            return this.getCoordScore(pion.coordonnee);
        }
        return -1;
    }   
    public int getCoordScore(Coordonnee c2) {
        if (c2 != null) {
            int vx = Math.abs(c2.px - Ebase.px);
            int vy = Math.abs(c2.py - Ebase.py);
            return Math.max(vx, vy);

        }
        return -1;
    }

}
