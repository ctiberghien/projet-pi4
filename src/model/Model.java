package model;

import java.io.Serializable;

public class Model implements Serializable{

    public Plateau plat;
    public Joueur j1;
    public Joueur j2;

    public Model(Plateau p, Joueur j1, Joueur j2) {
        plat = p;
        this.j1 = j1;
        this.j2 = j2;

    }
}
