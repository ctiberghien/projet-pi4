package model.TypeOfPlateau;

import java.util.ArrayList;
import java.util.LinkedList;

import model.Coordonnee;
import model.Couleur;
import model.Joueur;
import model.Pion;
import model.Plateau;
import model.Tuile;



public class Plateau1 extends Plateau {
    Joueur j1;
    Joueur j2;

    public Plateau1(Joueur j1, Joueur j2) {
        super();
        this.isTPActive =false;
        this.j1 = j1;
        this.j2 = j2;
        this.addTuile(new Coordonnee(0, 0), new Tuile(2));
        LinkedList<Coordonnee> tmp = Plateau.getRandNextCoord(new Coordonnee(0, 0));
        plat_jeu.get(tmp.get(0)).pion = new Pion(j1, Couleur.PERLE3, tmp.get(0), 1);
        plat_jeu.get(tmp.get(1)).pion = new Pion(j1, Couleur.PERLE1, tmp.get(1), 2);
        plat_jeu.get(tmp.get(2)).pion = new Pion(j1, Couleur.PERLE2, tmp.get(2), 3);
        j1.pions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            j1.pions.add(plat_jeu.get(tmp.get(i)).pion);
        }

        this.addTuile(new Coordonnee(2, -1), new Tuile(0));
        this.addTuile(new Coordonnee(1, 1), new Tuile(0));
        this.addTuile(new Coordonnee(2, 2), new Tuile(0));
        this.addTuile(new Coordonnee(4, -2), new Tuile(0));
        this.addTuile(new Coordonnee(6, -3), new Tuile(0));
        this.addTuile(new Coordonnee(3, 3), new Tuile(0));
        this.addTuile(new Coordonnee(5, 2), new Tuile(0));
        this.addTuile(new Coordonnee(7, -2), new Tuile(0));
        this.addTuile(new Coordonnee(8, -1), new Tuile(0));
        this.addTuile(new Coordonnee(7, 1), new Tuile(0));

        this.addTuile(new Coordonnee(9, 0), new Tuile(1));
        tmp = Plateau.getRandNextCoord(new Coordonnee(9, 0));
        plat_jeu.get(tmp.get(0)).pion = new Pion(j2, Couleur.PERLE3, tmp.get(0), 4);
        plat_jeu.get(tmp.get(1)).pion = new Pion(j2, Couleur.PERLE1, tmp.get(1), 5);
        plat_jeu.get(tmp.get(2)).pion = new Pion(j2, Couleur.PERLE2, tmp.get(2), 6);
        j2.pions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            j2.pions.add( plat_jeu.get(tmp.get(i)).pion);
        }

        this.addTuile(new Coordonnee(-1, 2), new Tuile(0));
        this.addTuile(new Coordonnee(1, -2), new Tuile(0));
        this.addTuile(new Coordonnee(8, 2), new Tuile(0));
        this.addTuile(new Coordonnee(10, -2), new Tuile(0));

        this.removeEmptyCase();

    }

}
