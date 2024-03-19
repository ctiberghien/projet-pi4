package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Serializable {
    public String nom;
    public ArrayList<Pion> pions;
    Color couleur;
    int turn;
    public ArrayList<Objet> sac;

    public Joueur(String nom, ArrayList<Pion> pions, Color couleur, int turn) {
        this(nom, couleur, turn);
        this.pions = pions;
        this.couleur = couleur;
        sac = new ArrayList<>();
    }

    public Joueur(String nom, Color couleur, int turn) {
        this.nom = nom;
        this.couleur = couleur;
        this.turn = turn;
        pions = new ArrayList<>();
        sac = new ArrayList<>();
    }

    public String toString() {
        String res = nom;
        for (int i = 0; i < pions.size(); i++) {
            res += pions.get(i).toString() + "\n";
        }
        return res;
    }

    public Color getCouleur() {
        return couleur;
    }

    public int getTurn() {
        return turn;
    }

    public void addPion(Pion pion) {
        if (pions == null) {
            pions = new ArrayList<Pion>();
        }
        pions.add(pion);
    }

    public void setPlat(Plateau plat) {
    }

    public boolean sacEmpty() {
        return sac.isEmpty();
    }

    public int sacCountItem() {
        return sac.size();
    }

    public int sacCountItem(Objet c) {
        int res = 0;
        for (Objet tmp : sac) {
            if (tmp.name.equals(c.name)) {
                res++;
            }
        }
        return res;
    }

    public boolean sacRemoveItem(Objet c) {
        for (Objet tmp : sac) {
            if (tmp.name.equals(c.name)) {
                sac.remove(tmp);
                return true;
            }
        }
        return false;
    }

}
