package model.Objets;
import javax.swing.JLabel;

import View.PlateauJeu;
import app.InGameView;
import model.*;
import View.*;
import View.Custom.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Stack;
import java.util.LinkedList;

public class Switch extends Objet{

    public Switch() {
        super();
        this.name = "Switch";
        this.taux = 20;
        this.description = "Objet permettant d'échanger les perles de deux pions adverse ou pas";
    }

    @Override
    public void activate(PlateauJeu m) {
        
    }

    @Override
    public void setPlateauObjet(PlateauJeu pan, Plateau p, InGameView game) {
        game.textTour.setText("Echangez les perles de deux pions");
        game.textTour.setFont(new Font("Tahoma", Font.PLAIN, 25));
        pan.setPlateauViewSize();
        PlateauView actualPanel = pan;
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
                        actualPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        actualPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                });
                tmp.setBounds(pan.platCenterX + scax, pan.platCenterY + scay, HexagoneSize[0], HexagoneSize[1]);
                pan.add(tmp);
                // ajout des Pions / pouvoir sur le plateau
                if (p.plat_jeu.get(c) != null) {
                    if (p.plat_jeu.get(c).pion != null) {
                        // Instanciation du pionView que l'on pose
                        PionView pv = new PionView(p.plat_jeu.get(c).pion,
                                p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                new Coordonnee(pan.platCenterX + scax + 20, scay + pan.platCenterY + 10), false);
                        p.plat_jeu.get(c).pion.pionView = pv;
                        // si ce pion est le dernier selectioné il apparait selectionée
                        if (pan.lastPion == p.plat_jeu.get(c).pion) {
                            pv.selectionnee = true;
                        }
                        pv.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (pan.lastPion != null) {
                                    if(p.plat_jeu.get(c).pion.joueur.equals(pan.lastPion.joueur)) {
                                        Stack<Couleur> perlej1 = p.plat_jeu.get(c).pion.perles;
                                        p.plat_jeu.get(c).pion.perles = pan.lastPion.perles;
                                        pan.lastPion.perles = perlej1;
                                        pan.joueurenjeu.sacRemoveItem(new Switch());
                                        pan.setPlateauView();
                                        pan.container.repaint();
                                        pan.container.revalidate();
                                        game.revalidate();
                                        game.repaint();
                                    }
                                }
                                if (pan.lastPion==null) {
                                    System.out.println("truc");
                                    pan.lastPion = pv.pion;
                                    setPlateauObjet(pan, p, game);
                                    pan.container.repaint();
                                }
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                if (pan.p.tour % 2 == pv.pion.joueur.getTurn() - 1)
                                    actualPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                actualPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }

                        });
                        pan.add(pv, JLayeredPane.DRAG_LAYER);

                    }

                    if (p.plat_jeu.get(c).obj != null) {
                        JLabel obj = p.plat_jeu.get(c).obj.getObjetView(HexagoneSize[0] * 9 / 10, HexagoneSize[1] * 9 / 10, false);
                        obj.setBounds(pan.platCenterX + scax  + 18, scay + pan.platCenterY + 5, HexagoneSize[0] * 9 / 10,
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
                System.out.println("test");
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
        return "Objet: Switch";
    }
    
}
