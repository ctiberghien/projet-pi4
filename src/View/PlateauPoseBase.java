package View;

import app.CreationPlateau;
import model.Coordonnee;
import model.Plateau;
import model.Tuile;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PlateauPoseBase extends PlateauView{

    private final CreationPlateau frame;
    private int  nbrPions;

    /**
     * @param p             Plateau model
     * @param partieDebute boolean, indique si la partie à débuter
     * @param container     correspond au Jscrollpane qui contiendra le PlateauView
     */

    public PlateauPoseBase(Plateau p, int nbrPions, boolean partieDebute, JScrollPane container, CreationPlateau plateau) {
        super(p, partieDebute, container);
        this.nbrPions = nbrPions;
        frame = plateau;
    }

    @Override
    public void setPlateauView() {
        PlateauView actualPanel = this;
        this.setPreferredSize(
                new Dimension(
                        (Math.abs(p.getMaxKeyXPlat_Jeu()) + Math.abs(p.getMinKeyXPlat_Jeu()) + 1) * (HexagoneSize[0])
                                + (Math.abs(p.getMaxKeyYPlat_Jeu())
                                        + Math.abs(p.getMinKeyYPlat_Jeu() + 1) * HexagoneSize[1] / 2),
                        (Math.abs(p.getMaxKeyYPlat_Jeu()) + Math.abs(p.getMinKeyYPlat_Jeu()) + 1)
                                * (HexagoneSize[1] * 3 / 4) + HexagoneSize[1] / 4));

        if (container != null) {
            if (container.getBounds().width * 0.8 > getPreferredSize().width
                    && container.getBounds().height * 0.8 > getPreferredSize().height) {
                platCenterX = container.getBounds().width / 2 - HexagoneSize[0] / 2;
                platCenterY = container.getBounds().height / 2 - HexagoneSize[1] / 2;
            } else {

                Coordonnee minCoord = p.getMinCoordonneeTuile();

                platCenterX = ((Math.abs(p.getMinKeyXPlat_Jeu())) * HexagoneSize[0])
                        + ((Math.abs(minCoord.py))) * HexagoneSize[1] / 2 - HexagoneSize[0] / 2;
                platCenterY = (Math.abs(p.getMaxKeyYPlat_Jeu())) * HexagoneSize[1] * 3 / 4;

            }
        } else {
            
            Coordonnee minCoord = p.getMinCoordonneeTuile();

            platCenterX = ((Math.abs(p.getMinKeyXPlat_Jeu())) * HexagoneSize[0])
                    + ((Math.abs(minCoord.py))) * HexagoneSize[1] / 2 - HexagoneSize[0] / 2;
            platCenterY = (Math.abs(p.getMaxKeyYPlat_Jeu())) * HexagoneSize[1] * 3 / 4;
        }
        this.removeAll();
        // On affiche d'abord Le fond de la tuile (Les hexagones bleus)
        Hexagone tmp;
        int scax = 0;
        int scay = 0;
        // On affiche les traits de couleurs de chaque case
        for (Coordonnee c : p.plat_jeu.keySet()) {
            scax += HexagoneSize[1] / 2 * c.py;
            scax += HexagoneSize[0] * c.px;
            scay -= 3 * HexagoneSize[1] / 4 * c.py;
            if (p.plat_jeu.get(c) != null) {
                tmp = new ColoredHexagone(c, p.plat_jeu.get(c).couleurBorder);
                tmp.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //Creation Plateau
                        boolean bienPlace = true;
                        if (baseAPlacer!=0 && partieDebutee && p.plat_jeu.get(c).getTuile().getNbBase()==0) {
                            Coordonnee c1 = new Coordonnee(c.px, c.py+1);
                            Coordonnee c2 = new Coordonnee(c.px+1, c.py);
                            Coordonnee c3 = new Coordonnee(c.px+1, c.py-1);
                            Coordonnee c4 = new Coordonnee(c.px, c.py-1);
                            Coordonnee c5 = new Coordonnee(c.px-1, c.py);
                            Coordonnee c6 = new Coordonnee(c.px-1, c.py+1);
                            Coordonnee[] coordBaseTab = {c1,c2,c3,c4,c5,c6};
                            if (baseAPlacer==2) {
                                for (Coordonnee coord : autourDesBases[1]) {
                                    for (Coordonnee coord2 : coordBaseTab) {
                                        if (coord.px == coord2.px && coord.py == coord2.py) {
                                            bienPlace = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (bienPlace) {
                                Tuile t = p.plat_jeu.get(c).getTuile();
                                t.setNbBase(baseAPlacer);
                                //met dans le tab les coordonnées des cases autour d'une base
                                autourDesBases[baseAPlacer%2]=coordBaseTab;
                                frame.updateInfoText("Choisissez l'emplacement de la base rouge (Joueur 2). ");
                                p.plat_jeu.get(c).setTuile(t);
                                setPlateauView();
                                revalidate();
                                repaint();
                                baseAPlacer++;
                                if (baseAPlacer==3) {
                                    toPosePion(autourDesBases[0], autourDesBases[1],container , frame , nbrPions);
                                    frame.updateInfoText("Placez vos pions autour de vos bases. ");

                                }
                            }
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        actualPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        actualPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                });
                tmp.setBounds(platCenterX + scax, platCenterY + scay, HexagoneSize[0], HexagoneSize[1]);
                // ajout des Pions sur le plateau
                if (p.plat_jeu.get(c) != null) {
                    if (p.plat_jeu.get(c).pion != null) {
                        if(!deplacement.isEmpty() && !deplacerPerle) {
                            if(p.plat_jeu.get(deplacement.getLast()).pion.equals(p.plat_jeu.get(c).pion)) {
                                PionView pv = new PionView(p.plat_jeu.get(c).pion, p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                        new Coordonnee(platCenterX + scax + 20, scay + platCenterY + 10), true);
                                p.plat_jeu.get(c).pion.pionView = pv;
                                add(pv, DRAG_LAYER);
                            } else {
                                PionView pv = new PionView(p.plat_jeu.get(c).pion, p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                        new Coordonnee(platCenterX + scax + 20, scay + platCenterY + 10), false);
                                p.plat_jeu.get(c).pion.pionView = pv;
                                add(pv, DRAG_LAYER);
                            }
                        } else {
                            PionView pv = new PionView(p.plat_jeu.get(c).pion, p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                    new Coordonnee(platCenterX + scax + 20, scay + platCenterY + 10), false);
                            p.plat_jeu.get(c).pion.pionView = pv;
                            add(pv, DRAG_LAYER);
                        }
                    }
                    this.add(tmp);
                }
            }
            scax = 0;
            scay = 0;
        }
    }
}
