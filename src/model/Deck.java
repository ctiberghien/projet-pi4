package model;

import java.util.Random;

public class Deck {
    Tuile[] deck;
    int cursor;

    public Deck(int nbTuile, int nbPions) {
        deck = new Tuile[nbTuile];
        for (int i = 0; i < deck.length; i++) {
            deck[i] = new Tuile(0, nbPions);
        }
        cursor = 0;
    }

    public Deck() {
        cursor = 0;
        deck = new Tuile[12];
        for (int i = 0; i < 10; i++) {
            deck[i] = new Tuile(0);
        }
        Couleur[] hexC = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE3,
                Couleur.PERLE3);
        Couleur[] triP = getTabCouleur(Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE3);
        deck[0] = new Tuile(hexC, triP, 0);

        hexC = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE3,
                Couleur.PERLE3);
        triP = getTabCouleur(Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE3);
        deck[1] = new Tuile(hexC, triP, 0);

        hexC = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE3,
                Couleur.PERLE3);
        triP = getTabCouleur(Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE3);
        deck[2] = new Tuile(hexC, triP, 0);

        hexC = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE2,
                Couleur.PERLE2);
        triP = getTabCouleur(Couleur.PERLE3, Couleur.PERLE3, Couleur.PERLE3);
        deck[3] = new Tuile(hexC, triP, 0);

        hexC = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE2,
                Couleur.PERLE2);
        triP = getTabCouleur(Couleur.PERLE3, Couleur.PERLE3, Couleur.PERLE3);
        deck[4] = new Tuile(hexC, triP, 0);

        hexC = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE3, Couleur.PERLE3,
                Couleur.PERLE3);
        triP = getTabCouleur(Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE2);
        deck[5] = new Tuile(hexC, triP, 0);
        hexC = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE3, Couleur.PERLE3,
                Couleur.PERLE3);
        triP = getTabCouleur(Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE2);
        deck[6] = new Tuile(hexC, triP, 0);

        hexC = getTabCouleur(Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE2, Couleur.PERLE3, Couleur.PERLE3,
                Couleur.PERLE3);
        triP = getTabCouleur(Couleur.PERLE1, Couleur.PERLE1, Couleur.PERLE1);
        deck[7] = new Tuile(hexC, triP, 0);

        hexC = getTabCouleur(Couleur.PERLE3, Couleur.PERLE2, Couleur.PERLE1, Couleur.PERLE3, Couleur.PERLE2,
                Couleur.PERLE1);
        triP = getTabCouleur(Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE3);
        deck[8] = new Tuile(hexC, triP, 0);
        hexC = getTabCouleur(Couleur.PERLE3, Couleur.PERLE2, Couleur.PERLE1, Couleur.PERLE3, Couleur.PERLE2,
                Couleur.PERLE1);
        triP = getTabCouleur(Couleur.PERLE1, Couleur.PERLE2, Couleur.PERLE3);
        deck[9] = new Tuile(hexC, triP, 0);

        deck[10] = Tuile.tuileBase(1);
        deck[11] = Tuile.tuileBase(2);

        this.shuffle();

    }

    public Tuile piocher() {
        if (cursor == deck.length) {
            return null;
        } else {
            return deck[cursor++];
        }
    }

    public Tuile peek() {
        if (cursor >= deck.length) {
            return null;
        } else {
            return deck[cursor];
        }
    }

    public static Couleur[] getTabCouleur(Couleur c1, Couleur c2, Couleur c3) {
        Couleur[] res = { c1, c2, c3 };
        return res;
    }

    public static Couleur[] getTabCouleur(Couleur c1, Couleur c2, Couleur c3, Couleur c4, Couleur c5, Couleur c6) {
        Couleur[] res = { c1, c2, c3, c4, c5, c6 };
        return res;
    }

    public void shuffle() {
        for (int i = 0; i < deck.length - 3; i++) {
            Random rand = new Random();
            int r = rand.nextInt(deck.length - 3);
            Tuile tmp = deck[i];
            deck[i] = deck[r];
            deck[r] = tmp;
        }
    }

}
