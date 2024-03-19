package model.Objets;

import View.*;
import app.InGameView;
import model.Coordonnee;
import model.Objet;
import model.Plateau;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Ventilateur extends Objet{

    public Ventilateur() {
        super();
        this.name = "Ventilateur";
        this.taux = 7;
        this.description = "Objet permettant de bouger touts les pions d'une ligne du plateau, enemies ou allier.";
    }

    // fonction pour les objets à effet immédiat
    @Override
    public void activate(PlateauJeu m) {}

    // fonction pour les objets à effet long
    public void setPlateauObjet(PlateauJeu pan, Plateau p, InGameView game) {
        game.textTour.setText("Selectionnez un pion et la direction du ventilateur");
        game.textTour.setFont(new Font("Tahoma", Font.PLAIN, 25));
        pan.setPlateauViewSize();
        // On affiche d'abord Le fond de la tuile (Les hexagones bleus)
        ColoredHexagone tmp;
        int scax = 0;
        int scay = 0;
        Coordonnee tp1 = null;
        Coordonnee tp2 = null;
        int scaxtp1 = 0;
        int scaytp1 = 0;
        int scaxtp2 = 0;
        int scaytp2 = 0;
        // On affiche les traits de couleurs de chaque case
        for (Coordonnee c : p.plat_jeu.keySet()) {
            scax += HexagoneSize[1] / 2 * c.py;
            scax += HexagoneSize[0] * c.px;
            scay -= 3 * HexagoneSize[1] / 4 * c.py;
            if (p.plat_jeu.get(c) != null) {
                tmp = new ColoredHexagone(c, p.plat_jeu.get(c).couleurBorder);
                tmp.setLockedTurn(p.plat_jeu.get(c).getLockedTurn());
                tmp.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SoundPlayer.joueSon(1, 0);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        pan.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        pan.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                });
                tmp.setBounds(pan.platCenterX + scax, pan.platCenterY + scay, HexagoneSize[0], HexagoneSize[1]);
                pan.add(tmp);
                if (p.plat_jeu.get(c) != null) {
                    if (p.plat_jeu.get(c).pion != null) {
                        // Instanciation du pionView que l'on pose
                        PionView pv = new PionView(p.plat_jeu.get(c).pion,
                                p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                new Coordonnee(pan.platCenterX + scax + 20, scay + pan.platCenterY + 10), false);
                        p.plat_jeu.get(c).pion.pionView = pv;
                        if (pan.lastPion == p.plat_jeu.get(c).pion) {
                            pv.selectionnee = true;
                        }
                        pv.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (pan.p.tour % 2 + 1 == pv.pion.joueur.getTurn()) {
                                    pan.lastPion = pv.pion;
                                    setPlateauObjet(pan, p, game);
                                    pan.container.repaint();
                                }
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                if (pan.p.tour % 2 == pv.pion.joueur.getTurn() - 1)
                                    pan.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                pan.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }

                        });
                        pan.add(pv, JLayeredPane.DRAG_LAYER);

                        if (pan.joueurenjeu == p.plat_jeu.get(c).pion.joueur && pv.selectionnee) {
                            ArrayList<Coordonnee> cProx = getTuilesCProx(p , c);
                            for (Coordonnee ctmp: cProx) {
                                int scaxtmp = HexagoneSize[1] / 2 * ctmp.py;
                                scaxtmp += HexagoneSize[0] * ctmp.px;
                                int scaytmp = - (3 * HexagoneSize[1] / 4 * ctmp.py);
                                String arg = "src/resources/fan.gif";
                                ImageIcon icon = new ImageIcon(arg);
                                Image newimg = icon.getImage().getScaledInstance(42, 42,
                                        Image.SCALE_DEFAULT);
                                icon.setImage(newimg);
                                JLabel fan = new JLabel();
                                fan.setBounds(pan.platCenterX + scaxtmp + 20, scaytmp + pan.platCenterY + 20, 42, 42);
                                fan.setIcon(icon);
                                fan.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        super.mouseClicked(e);
                                        if (pan.p.tour % 2 + 1 == pv.pion.joueur.getTurn()) {
                                            p.deplacementVentilateur( pv.pion, ctmp, p);
                                            pan.setPlateauView();
                                            pan.joueurenjeu.sacRemoveItem(new Ventilateur());
                                            pan.container.repaint();
                                            pan.container.revalidate();
                                            game.revalidate();
                                            game.repaint();
                                        }
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {
                                        if (pan.p.tour % 2 == pv.pion.joueur.getTurn() - 1)
                                            pan.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {
                                        pan.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    }

                                });
                                pan.add(fan, JLayeredPane.DRAG_LAYER);
                            }
                        }
                        pv.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {

                            }
                        });
                        pan.add(pv, JLayeredPane.DRAG_LAYER);

                    }

                    if (p.plat_jeu.get(c).obj != null) {
                        JLabel obj = p.plat_jeu.get(c).obj.getObjetView(HexagoneSize[0] * 9 / 10, HexagoneSize[1] * 9 / 10, false);
                        obj.setBounds(pan.platCenterX + scax + 18, scay + pan.platCenterY + 5, HexagoneSize[0] * 9 / 10,
                                HexagoneSize[1] * 9 / 10);
                        pan.add(obj, JLayeredPane.DRAG_LAYER);
                    }
                }
            }
            if (p.teleporteur.pointA == c) {
                tp1 = c;
                scaxtp1 = scax;
                scaytp1 = scay;
            } else if (p.teleporteur.pointB == c) {
                tp2 = c;
                scaxtp2 = scax;
                scaytp2 = scay;
            }
            scax = 0;
            scay = 0;
        }
        if (tp1 != null && tp2 != null) {
            if (!p.teleporteur.isUsed) {
                String arg = "src/resources/netherPortal.gif";
                ImageIcon icon = new ImageIcon(arg);
                Image newimg = icon.getImage().getScaledInstance(42, 42,
                        Image.SCALE_DEFAULT);
                icon.setImage(newimg);
                JLabel nether = new JLabel();
                nether.setBounds(pan.platCenterX + scaxtp1 + 20, scaytp1 + pan.platCenterY + 20, 42, 42);
                nether.setIcon(icon);
                pan.add(nether, JLayeredPane.DRAG_LAYER);
                JLabel nether2 = new JLabel();
                nether2.setBounds(pan.platCenterX + scaxtp2 + 20, scaytp2 + pan.platCenterY + 20, 42, 42);
                nether2.setIcon(icon);
                pan.add(nether2, JLayeredPane.DRAG_LAYER);
            }
        } else {
            p.teleporteur.isUsed = true;
        }
    }

    @Override
    public String toString() {
        return "Objet: Ventilateur";
    }

    private ArrayList<Coordonnee> getTuilesCProx(Plateau p, Coordonnee c) {
        ArrayList<Coordonnee> res = new ArrayList<>();
        Coordonnee[] tab = new Coordonnee[6];
        tab[0] = new Coordonnee(c.px, c.py+1);
        tab[4] = new Coordonnee(c.px, c.py - 1);
        tab[1] = new Coordonnee(c.px+1, c.py);
        tab[2] = new Coordonnee(c.px+1, c.py-1);
        tab[3] = new Coordonnee(c.px-1, c.py);
        tab[5] = new Coordonnee(c.px-1, c.py + 1);
        for (int i = 0; i < 6; i++) {
            if (p.plat_jeu.get(tab[i]) != null){
                res.add(tab[i]);
            }
        }
        return res;
    }

}
