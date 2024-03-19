package model;

import java.util.Random;
import java.io.Serializable;

public class Tuile implements Serializable {
    public Couleur[] hexagoneCentral; // utiliser pour définir le plateau initial
    public Couleur[] triplet; // utiliser pour définir le plateau initial
    // 0 -> normal, 1 -> bleu, 2 -> rouge
    int nbBase;

    public void affichage() {
        String res = "HexCentral[6] = [ ";
        for (int i = 0; i < hexagoneCentral.length; i++) {
            res += hexagoneCentral[i];
            res += (i == hexagoneCentral.length - 1) ? " " : " , ";
        }
        res += "] Triplet[3] = [ ";
        for (int i = 0; i < triplet.length; i++) {
            res += triplet[i];
            res += (i == triplet.length - 1) ? " " : " , ";
        }
        res += "] nbBase : " + nbBase;
        System.out.println(res);
    }

    public Tuile(Couleur[] hexCouleurs, Couleur[] triplet, int nbBase) {

        this.hexagoneCentral = hexCouleurs;
        this.triplet = triplet;
        this.nbBase = nbBase;
    }

    public Tuile(int nbBase) {
        this.nbBase = nbBase;
        hexagoneCentral = new Couleur[6];
        hexagoneCentral[0] = Couleur.PERLE3;
        hexagoneCentral[1] = Couleur.PERLE1;
        hexagoneCentral[2] = Couleur.PERLE2;
        for (int i = 3; i < hexagoneCentral.length; i++) {
            hexagoneCentral[i] = randCoulor();
        }
        shuffleColorTab(hexagoneCentral);

        // Les triplets reste aleatoire
        triplet = new Couleur[3];
        for (int i = 0; i < triplet.length; i++) {
            triplet[i] = randCoulor();
        }
    }

    public Tuile(int nbBase, int nbPion) {
        this.nbBase = nbBase;
        hexagoneCentral = new Couleur[6];
        if (nbPion >= 3) {
            hexagoneCentral[2] = Couleur.PERLE2;
        } else {
            hexagoneCentral[2] = Couleur.PERLE1;
        }
        hexagoneCentral[0] = Couleur.PERLE3;
        hexagoneCentral[1] = Couleur.PERLE1;
        for (int i = 3; i < hexagoneCentral.length; i++) {
            hexagoneCentral[i] = randCoulor(nbPion);
        }
        shuffleColorTab(hexagoneCentral);

        // Les triplets reste aleatoire
        triplet = new Couleur[3];
        for (int i = 0; i < triplet.length; i++) {
            triplet[i] = randCoulor(nbPion);
        }
    }

    public static Tuile tuileBase(int nbBase) {
        Couleur[] hexC = { Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE3,
                Couleur.PERLE3 };
        Couleur[] triP = { Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE3 };
        Tuile res = new Tuile(hexC, triP, nbBase);
        return res;

    }

    public void shuffleColorTab(Couleur[] t) {
        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int r = rand.nextInt(6);
            Couleur tmp = t[r];
            t[r] = t[i];
            t[i] = tmp;
        }
    }

    Couleur randCoulor() {
        Random rand = new Random();
        int r = rand.nextInt(3);
        switch (r) {
            case 0:
                return Couleur.PERLE1;
            case 1:
                return Couleur.PERLE2;
            default:
                return Couleur.PERLE3;
        }
    }

    Couleur randCoulor(int nbrPion) {
        Random rand = new Random();
        int r = rand.nextInt(nbrPion);
        return switch (r) {
            case 0 -> Couleur.PERLE1;
            case 1 -> Couleur.PERLE3;
            case 2 -> Couleur.PERLE2;
            case 3 -> Couleur.PERLE4;
            case 4 -> Couleur.PERLE5;
            default -> null;
        };
    }

    public void setNbBase(int nbBase) {
        this.nbBase = nbBase;
    }

    public int getNbBase() {
        return nbBase;
    }

    public void rotateRight() {
        shift(this.triplet, 1);
        shift(hexagoneCentral, 2);
    }

    public static void shift(Couleur[] t, int n) {
        for (int i = 0; i < n; i++) {
            shift(t);
        }
    }

    public static void shift(Couleur[] t) {
        Couleur tmp = t[t.length - 1];
        for (int i = t.length - 2; i >= 0; i--) {
            t[i + 1] = t[i];
        }
        t[0] = tmp;
    }

}
