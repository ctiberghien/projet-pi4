package View;

import app.InGameView;
import model.AIJoueur;
import model.ControlPlateauJeuClient;
import model.Coordonnee;
import model.Joueur;
import model.Plateau;
import model.Objets.FreeMove;
import model.Model;
import model.*;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;
import java.util.Stack;

public class PlateauJeu extends PlateauView {
    /**
     * @param p         Plateau model
     * @param container correspond au Jscrollpane qui contiendra le PlateauView
     */
    public PlateauJeu(Plateau p, JScrollPane container, InGameView inGameView, Model model) {
        super(p, true, container);
        game = inGameView;
        this.model = model;
    }

    public transient InGameView game;
    public Model model;
    public ControlPlateauJeuClient control;
    public Joueur joueurenjeu;
    public Pion lastPion;

    public void setPlateauViewSize() {
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
    }

    @Override
    public void setPlateauView() {
        setPlateauViewSize();
        if (game != null && game.textTour != null) {
            if (control==null) {
                game.textTour.setText("Tour du Joueur " + (p.tour % 2 + 1) + " : ");
                game.textTour.setFont(new Font("Tahoma", Font.PLAIN, 30));
            }
        }

        PlateauView actualPanel = this;
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
                tmp.setLockedTurn(p.plat_jeu.get(c).getLockedTurn());// MUR1
                tmp.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // System.out.println(p.plat_jeu.get(c).lockedTurn);
                        if (p.tour == -1)
                            return;
                        if (control != null)
                            p.tour += 2;
                        SoundPlayer.joueSon(1, 0);
                        // En jeu
                        if (p.plat_jeu.get(c).pion != null && deplacement.isEmpty() && !deplacerPerle) {
                            if (p.plat_jeu.get(c).pion.joueur.getTurn() == p.tour % 2 + 1) {
                                deplacement.add(c);
                                remove(p.plat_jeu.get(deplacement.getLast()).pion.pionView);
                                setPlateauView();
                                container.repaint();
                                revalidate();
                                // test
                                game.sac.setVisible(false);
                            }

                        } else if (p.plat_jeu.get(c).pion != null && p.plat_jeu.get(deplacement.getLast()).pion != null
                                && !deplacement.isEmpty() && deplacerPerle) {
                            if (p.plat_jeu.get(c).pion.joueur.equals(p.plat_jeu.get(deplacement.getLast()).pion.joueur)
                                    && p.plat_jeu.get(c).pion != p.plat_jeu.get(deplacement.getLast()).pion
                                    && p.plat_jeu.get(c).pion.perles.size() < 3) {
                                game.perles.pion.perles.pop();
                                game.perles.repaint();
                                p.plat_jeu.get(c).pion.perles.add(perleADeplacer.getLast());
                                perleADeplacer.removeLast();
                                remove(p.plat_jeu.get(c).pion.pionView);
                                setPlateauView();
                                revalidate();
                                container.repaint();
                                if (perleADeplacer.size() == 0) {
                                    if (control == null)
                                        game.sac.setVisible(true);
                                    if (control != null) {
                                        p.tour++;
                                        control.c.sendMessage(p.genereString(true, true));
                                        game.textTour.setText("Tour de " + (control.c.pseudos[p.tour % 2]) + " : ");
                                        p.tour = -1;
                                        p.decreaseBlockCase();
                                    } else {
                                        // System.out.println("incrementer C");
                                        // tour = tour+1;
                                        game.textTour.setText("Tour du Joueur " + p.tour + " : ");
                                        p.tour = p.tour + 1;
                                        p.decreaseBlockCase();
                                    }
                                    // Tout les x tours le jeu ajoute des objets sur le terrain
                                    if (p.objetActif) {
                                        if (p.objTurn != 0) {
                                            if (p.tour % p.objTurn == 0) {
                                                p.ajoutObj();
                                            }
                                        }
                                    }
                                    if (p.isTPActive) {
                                        if (p.teleporteur.hasToSpawn()
                                                || (p.tour / 2 == p.teleporteur.nbTour && !p.teleporteur.isSpawned)) {
                                            p.teleporteur.ajoutTp();
                                        }
                                    }
                                    perleADeplacer = new LinkedList<>();
                                    deplacement = new LinkedList<>();
                                    deplacerPerle = false;
                                    p.teleporteur.tour += 1;
                                    if (control == null)
                                        game.textTour.setText("Tour du Joueur " + (p.tour % 2 + 1) + " : ");
                                    // On fait jouer LIA si s'en est un

                                    switch (p.tour % 2) {
                                        case 0:
                                            joueurenjeu = game.model.j2;
                                            break;
                                        default:
                                            joueurenjeu = game.model.j1;
                                            break;
                                    }
                                    if (joueurenjeu instanceof AIJoueur) {
                                        ((AIJoueur) joueurenjeu).play();
                                        try {
                                            Thread.sleep(300);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        if (((AIJoueur) joueurenjeu).haswin) {
                                            game.setFin(p, container, joueurenjeu);
                                        }
                                        p.tour = p.tour % 2 + 1;
                                        p.decreaseBlockCase();
                                        switch (p.tour % 2) {
                                            case 0:
                                                joueurenjeu = game.model.j2;
                                                break;
                                            default:
                                                joueurenjeu = game.model.j1;
                                                break;
                                        }
                                        setPlateauView();
                                        revalidate();
                                        game.textTour.setText("Tour du Joueur " + (p.tour % 2 + 1) + " : ");
                                    }
                                    setPlateauView();
                                    revalidate();
                                    repaint();
                                }
                            }
                        } else if (Plateau.isJumping) {
                            if (p.plat_jeu.get(c).hasTuile()) {
                                if (p.plat_jeu.get(c).getTuile().getNbBase() == p.plat_jeu
                                        .get(deplacement.get(deplacement.size() - 2)).pion.joueur.getTurn()) {
                                    return;
                                }
                            }

                            if (p.plat_jeu.get(c).pion == null) {
                                if (p.jump(p.plat_jeu.get(deplacement.get(deplacement.size() - 2)).pion, c,
                                        deplacement.getLast())) {
                                    Plateau.isJumping = false;
                                    deplacement.add(c);
                                    remove(p.plat_jeu.get(deplacement.getLast()).pion.pionView);
                                    setPlateauView();
                                    container.repaint();
                                    revalidate();
                                    saut = true;
                                    if (c == p.teleporteur.pointA || c == p.teleporteur.pointB) {
                                        SoundPlayer.joueSon(1, 1);
                                        // p.deplacementPionTp(p.plat_jeu.get(deplacement.getLast()).pion, c,
                                        // perleADeplacer, deplacement, PlateauJeu.this, game);
                                        p.deplacementPionTP(p.plat_jeu.get(deplacement.getLast()).pion, PlateauJeu.this,
                                                false);
                                        deplacement.add(c);
                                        remove(p.plat_jeu.get(deplacement.getLast()).pion.pionView);
                                        setPlateauView();
                                        container.repaint();
                                        revalidate();
                                    }
                                }
                            }
                        } else if (!deplacement.isEmpty() && p.plat_jeu.get(deplacement.getLast()).pion != null
                                && p.plat_jeu.get(c).pion != null && p.isAdjacent(deplacement.getLast(), c)
                                && p.plat_jeu.get(deplacement.getLast()).pion.perles.isEmpty() && !deplacement.isEmpty()
                                && !deplacerPerle
                                && !saut) {
                            if (p.plat_jeu.get(c).pion.joueur != p.plat_jeu.get(deplacement.getLast()).pion.joueur) {
                                Plateau.isJumping = true;
                                deplacement.add(c);
                            }
                        } else if (!deplacement.isEmpty() && !deplacerPerle) {
                            if (p.plat_jeu.get(c).hasTuile()) {
                                if (p.plat_jeu.get(c).getTuile()
                                        .getNbBase() == p.plat_jeu.get(deplacement.getLast()).pion.joueur.getTurn()) {
                                    return;
                                }
                            }

                            if (p.deplacementPion(p.plat_jeu.get(deplacement.getLast()).pion, c)) {
                                deplacement.add(c);
                                perleADeplacer.add(p.plat_jeu.get(deplacement.getLast()).pion.perles.pop());
                                remove(p.plat_jeu.get(deplacement.getLast()).pion.pionView);
                                game.perles.pion.perles.add(perleADeplacer.getLast());
                                game.perles.repaint();
                                if (c == p.teleporteur.pointA || c == p.teleporteur.pointB) {
                                    SoundPlayer.joueSon(1, 1);
                                    // p.deplacementPionTp(p.plat_jeu.get(deplacement.getLast()).pion, c,
                                    // perleADeplacer, deplacement, PlateauJeu.this, game);
                                    p.deplacementPionTP(p.plat_jeu.get(deplacement.getLast()).pion, PlateauJeu.this,
                                            false);
                                    remove(p.plat_jeu.get(deplacement.getLast()).pion.pionView);
                                    // setPlateauView();
                                    // container.repaint();
                                    // revalidate();
                                }
                            }
                            setPlateauView();
                            container.repaint();
                            revalidate();
                        }
                        setPlateauView();
                        revalidate();
                        container.repaint();
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
                this.add(tmp);
                // ajout des Pions sur le plateau
                if (p.plat_jeu.get(c) != null) {
                    if (p.plat_jeu.get(c).pion != null) {
                        if (!deplacement.isEmpty() && !deplacerPerle
                                && p.plat_jeu.get(deplacement.getLast()).pion != null) {
                            if (p.plat_jeu.get(deplacement.getLast()).pion.equals(p.plat_jeu.get(c).pion)) {
                                PionView pv = new PionView(p.plat_jeu.get(c).pion,
                                        p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                        new Coordonnee(platCenterX + scax + 20, scay + platCenterY + 10), true);
                                p.plat_jeu.get(c).pion.pionView = pv;
                                add(pv, DRAG_LAYER);
                            } else {
                                PionView pv = new PionView(p.plat_jeu.get(c).pion,
                                        p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                        new Coordonnee(platCenterX + scax + 20, scay + platCenterY + 10), false);
                                p.plat_jeu.get(c).pion.pionView = pv;
                                add(pv, DRAG_LAYER);
                            }
                        } else {
                            PionView pv = new PionView(p.plat_jeu.get(c).pion,
                                    p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                    new Coordonnee(platCenterX + scax + 20, scay + platCenterY + 10), false);
                            p.plat_jeu.get(c).pion.pionView = pv;
                            add(pv, DRAG_LAYER);
                        }
                    }
                    if (p.plat_jeu.get(c).obj != null) {
                        JLabel obj = p.plat_jeu.get(c).obj.getObjetView(HexagoneSize[0] * 9 / 10,
                                HexagoneSize[1] * 9 / 10, false);
                        obj.setBounds(platCenterX + scax + 18, scay + platCenterY + 5, HexagoneSize[0] * 9 / 10,
                                HexagoneSize[1] * 9 / 10);
                        add(obj, DRAG_LAYER);
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
                nether.setBounds(platCenterX + scaxtp1 + 20, scaytp1 + platCenterY + 20, 42, 42);
                nether.setIcon(icon);
                JLabel nether2 = new JLabel();
                nether2.setBounds(platCenterX + scaxtp2 + 20, scaytp2 + platCenterY + 20, 42, 42);
                nether2.setIcon(icon);
                add(nether, MODAL_LAYER);
                add(nether2, MODAL_LAYER);
                p.teleporteur.canBeUsed = true;
            }
        } else {
            p.teleporteur.canBeUsed = false;
            p.teleporteur.isUsed = true;
        }
    }
}
