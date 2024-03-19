package View;

import app.CreationPlateau;
import model.Coordonnee;
import model.Deck;
import model.Plateau;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PlateauCreation extends PlateauView {
    int nbrTuile;
    int nbrPions;
    CreationPlateau frame;
    Deck deck;
    boolean modeClassic;
    public boolean isTPActive;
    public int nbTP;

    /**
     * @param p         Plateau model
     * @param container correspond au Jscrollpane qui contiendra le PlateauView
     * @param nbrTuile  Le nombre de piece du plateau
     */
    public PlateauCreation(Plateau p, JScrollPane container, int nbrTuile, int nbrPions, CreationPlateau plateau,
            Deck deck, boolean modeClassic, boolean hasTp, int nbrTP) {
        super(p, false, container);
        this.deck = deck;
        this.nbrTuile = nbrTuile;
        this.nbrPions = nbrPions;
        this.frame = plateau;
        this.modeClassic = modeClassic;
        isTPActive = hasTp;
        nbTP = nbrTP;
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
            platCenterY = ((Math.abs(p.getMaxKeyYPlat_Jeu())) * HexagoneSize[1] * 3 / 4);
        }
        this.removeAll();
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
                    public void mouseEntered(MouseEvent e) {
                        actualPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        actualPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                });
                tmp.setBounds(platCenterX + scax, platCenterY + scay, HexagoneSize[0], HexagoneSize[1]);
            } else {
                tmp = new EmptyHexagone(c);
                tmp.addMouseListener(new MouseInputAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (nbrTuile == 2) {
                            Coordonnee c1 = new Coordonnee(c.px, c.py + 1);
                            Coordonnee c2 = new Coordonnee(c.px + 1, c.py);
                            Coordonnee c3 = new Coordonnee(c.px + 1, c.py - 1);
                            Coordonnee c4 = new Coordonnee(c.px, c.py - 1);
                            Coordonnee c5 = new Coordonnee(c.px - 1, c.py);
                            Coordonnee c6 = new Coordonnee(c.px - 1, c.py + 1);
                            Coordonnee[] coordBaseTab = { c1, c2, c3, c4, c5, c6 };
                            autourDesBases[1] = coordBaseTab;
                        } else if (nbrTuile == 1) {
                            Coordonnee c1 = new Coordonnee(c.px, c.py + 1);
                            Coordonnee c2 = new Coordonnee(c.px + 1, c.py);
                            Coordonnee c3 = new Coordonnee(c.px + 1, c.py - 1);
                            Coordonnee c4 = new Coordonnee(c.px, c.py - 1);
                            Coordonnee c5 = new Coordonnee(c.px - 1, c.py);
                            Coordonnee c6 = new Coordonnee(c.px - 1, c.py + 1);
                            Coordonnee[] coordBaseTab = { c1, c2, c3, c4, c5, c6 };
                            autourDesBases[0] = coordBaseTab;
                        }
                        p.addTuile(c, deck.piocher());
                        setPlateauView();
                        frame.nextTuileView.changeTuileToView(deck.peek());
                        container.repaint();
                        revalidate();
                        nbrTuile -= 1;
                        frame.updatePieces(nbrTuile);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        actualPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        actualPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                });
                tmp.setBounds(platCenterX + scax, platCenterY + scay, HexagoneSize[0], HexagoneSize[1]);

            }
            this.add(tmp);

            scax = 0;
            scay = 0;
            if (nbrTuile == 1) {
                frame.remove(frame.nextTuileView);
                frame.remove(frame.turn);
                frame.removeText();
                if (!modeClassic) {
                    frame.updateInfoText("Choisissez l'emplacement de la base bleu (Joueur 1). ");
                    toPoseBase(p, nbrPions, container, frame);
                } else {
                    frame.updateInfoText("Placez vos pions autour de vos bases. ");
                    toPosePion(autourDesBases[0], autourDesBases[1], container, frame, nbrPions);
                }
                container.repaint();
                revalidate();
            }
        }
    }

}
