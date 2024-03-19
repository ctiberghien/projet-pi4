package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Teleporteur implements Serializable {
    public boolean isSpawned = false;
    public boolean canBeUsed;
    Plateau plateau;
    Map<Coordonnee, Case> plat_jeu;
    public Coordonnee pointA;
    public Coordonnee pointB;
    public int tour = 0;
    public final int nbTour;
    public boolean isUsed = false;
    public boolean usedNow;


    public Teleporteur(Plateau p, int nb) {
        plateau = p;
        plat_jeu = p.plat_jeu;
        nbTour = nb;
    }

    public void ajoutTp() {
        if (!isSpawned && canBeUsed){
            Coordonnee[] cases = plat_jeu.keySet().toArray(new Coordonnee[0]);
            Collections.shuffle(Arrays.asList(cases));
            int i = 2;
            for (Coordonnee aCase : cases) {
                if (i == 0) break;
                if (plat_jeu.get(aCase).obj == null && plat_jeu.get(aCase).pion == null && plat_jeu.get(aCase).lockedTurn==0) {
                    if (plat_jeu.get(aCase).hasTuile()) {
                        if (plat_jeu.get(aCase).getTuile().getNbBase() != 0) {
                            continue;
                        }
                    }
                    if ( ! (aCase == pointA || aCase == pointB)){
                        if ( i == 1){
                            pointA = aCase;
                        }else {
                            pointB = aCase;
                        }
                        i--;
                    }
                }
            }
//            if (i != 0 ) isUsed = true;
            isSpawned = true;
            canBeUsed= false;
            usedNow = false;
        }
    }

    public boolean hasToSpawn() {
        if (isUsed && tour >= nbTour) {
            tour = 0;
            isUsed = false;
            usedNow = false;
            isSpawned = false;
            canBeUsed = true;
            return true;
        }else{
            return false;
        }
    }

    public boolean notOnCoord(Coordonnee c) {
        if (pointA != null && pointB != null) {
            return c.py != pointA.py && c.py != pointB.py && c.px != pointA.px && c.px != pointB.px && !isSpawned;
        }else {
            return true;
        }
    }
}
