package model.TypeOfPlateau;

import java.util.LinkedList;
import java.util.Random;

import model.Coordonnee;
import model.Plateau;
import model.Tuile;

public class PlateauAlea extends Plateau {

    public PlateauAlea(int nbDeTuile) {
        super();
        LinkedList<Coordonnee> emptyCaseCoord = new LinkedList<>();

        for (int i = 0; i < nbDeTuile; i++) {
            emptyCaseCoord = getAllEmptyCase();
            Random rand = new Random();
            int r = rand.nextInt(emptyCaseCoord.size());
            addTuile(emptyCaseCoord.get(r), new Tuile(0));
        }
    }

    public LinkedList<Coordonnee> getAllEmptyCase() {
        LinkedList<Coordonnee> res = new LinkedList<>();
        for (Coordonnee c : plat_jeu.keySet()) {
            if (plat_jeu.get(c) == null) {
                res.add(c);
            }
        }
        return res;
    }
}
