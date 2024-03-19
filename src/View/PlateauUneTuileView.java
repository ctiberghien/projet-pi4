package View;

import model.Coordonnee;
import model.Plateau;
import model.Tuile;

public class PlateauUneTuileView extends PlateauView {

    public PlateauUneTuileView(Plateau p, boolean partieDebute) {
        super(p, partieDebute, null);
    }

    @Override
    public void setPlateauView() {
        platCenterX = 40;
        platCenterY = 40;
        this.removeAll();
        Hexagone tmp;
        int scax = 0;
        int scay = 0;
        // On affiche les traits de couleurs de chaque case
        for (Coordonnee c : p.plat_jeu.keySet()) {
            if (p.plat_jeu.get(c) != null) {
                scax += HexagoneSize[1] / 2 * c.py;
                scax += HexagoneSize[0] * c.px;
                scay -= 3 * HexagoneSize[1] / 4 * c.py;
                tmp = new ColoredHexagone(c, p.plat_jeu.get(c).couleurBorder);
                tmp.setBounds(platCenterX + scax, platCenterY + scay, HexagoneSize[0], HexagoneSize[1]);
                this.add(tmp);
                scax = 0;
                scay = 0;
            }
        }
    }

    public void changeTuileToView(Tuile t) {
        p.plat_jeu.clear();
        p.plat_hexagoneB.clear();
        if (t != null) {
            p.addTuile(new Coordonnee(0, 0), t);
            p.removeEmptyCase();
            setPlateauView();
        }
    }

}
