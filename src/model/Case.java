package model;

import java.io.Serializable;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import model.Objets.*;

public class Case implements Serializable {

    static Coordonnee[] couleurCoordonneesIndex = {
            new Coordonnee(-1, 1), new Coordonnee(0, 1), new Coordonnee(1, 0), new Coordonnee(1, -1),
            new Coordonnee(0, -1), new Coordonnee(-1, 0) };
    Tuile tuile;
    public Pion pion;
    public Couleur[] couleurBorder;
    public Objet obj;
    public int lockedTurn=0;

    public Case(Tuile tuile) {
        this.tuile = tuile;
        couleurBorder = new Couleur[6];
        pion = null;

    }

    Case(Tuile tuile, Couleur[] couleursBorder) {
        this(tuile);
        this.couleurBorder = couleursBorder;
    }

    public void setLockedTurn(int lockedTurn) {
        this.lockedTurn = lockedTurn;
    }

    public int getLockedTurn() {
        return lockedTurn;
    }

    public boolean hasTuile() {
        return tuile != null;
    }

    public void affichage() {
        if (this.tuile != null) {
            System.out.print("Tuile : ");
            this.tuile.affichage();
        } else {
            System.out.print("Border : [ ");
            for (int i = 0; i < couleurBorder.length; i++) {
                System.out.print(couleurBorder[i]);
                if (i != couleurBorder.length - 1) {
                    System.out.print(" , ");
                }
            }
            System.out.println(" ] ");
        }
    }

    public Tuile getTuile() {
        return tuile;
    }

    public void setTuile(Tuile t) {
        tuile = t;
    }

    public void generateObj() {
        File folder = new File("src/model/Objets");
        File[] files = folder.listFiles();
        Random rand = new Random();
        Object[] Objets = new Objet[files.length];
        int tauxMax = 0;
        for (int j = 0; j < files.length; j++) {
            Objet o = objgenerer(j);
            tauxMax += o.taux;
            Objets[j] = o;
        }
        int alea = rand.nextInt(1, tauxMax + 1);
        int tmp = 0;
        for (int i = 0; i < files.length; i++) {
            if (((Objet) Objets[i]).taux + tmp >= alea && alea > tmp) {
                obj = (Objet) Objets[i];
                break;
            }
            tmp += ((Objet) Objets[i]).taux;
        }
    }

    public Objet objgenerer(int n) {
        switch (n) {
            case 0:
                return new Switch();
            case 1:
                return new FreeMove();
            case 2:
                return new MelangeCouleur();
            case 3:
                return new Renvoi();
            case 4:
                return new Mur();
            case 5:
                return new Ventilateur();
        }
        return null;
    }
}
