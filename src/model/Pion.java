package model;

import java.io.Serializable;
import java.util.Stack;
import View.PionView;

public class Pion implements Serializable{

    public Stack<Couleur> perles;
    public Joueur joueur;
    public Coordonnee coordonnee; // coordonée sur la mapJ
    public PionView pionView;
    public int idPion;

    /**
     * @param joueur     : Joueur à qui le pion appartient
     * @param perles     : pile de perle de couleur
     * @param coordonnee :
     */
    public Pion(Joueur joueur, Stack<Couleur> perles, Coordonnee coordonnee, int idPion) {
        this.joueur = joueur;
        this.perles = perles;
        this.coordonnee = coordonnee;
        joueur.addPion(this);
        this.idPion=idPion;
    }

    public Pion(Joueur joueur, Couleur couleur, Coordonnee coordonnee, int idPion) {
        this.joueur = joueur;
        this.coordonnee = coordonnee;
        Stack<Couleur> stck = new Stack<>();
        stck.push(couleur);
        stck.push(couleur);
        this.perles = stck;
        this.idPion=idPion;

    }
    public String toString(){
        String res  = perles.toString() +" "+ coordonnee.toString();
        return res;
    }


    public Couleur[] getColors() {
        Couleur[] res = new Couleur[perles.size()];
        Stack<Couleur> tmp = new Stack<>();
        int i = 0;
        while (!perles.isEmpty()) {
            Couleur tmp2 = perles.pop();
            switch (tmp2) {
                case PERLE1 -> res[i] = Couleur.PERLE1;
                case PERLE2 -> res[i] = Couleur.PERLE2;
                case PERLE3 -> res[i] = Couleur.PERLE3;
                case PERLE4 -> res[i] = Couleur.PERLE4;
                case PERLE5 -> res[i] = Couleur.PERLE5;
            }
            i += 1;
            tmp.push(tmp2);
        }
        while (!tmp.isEmpty()) {
            perles.push(tmp.pop());
        }
        return res;
    }

    public boolean addPerle(Couleur c) {
        if (this.perles.size() < 3) {
            this.perles.push(c);
            return true;
        }
        return false;
    }

    public String toStringPerle() {
        return perles.toString();
    }

}
