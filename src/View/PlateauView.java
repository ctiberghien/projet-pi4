package View;

import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import javax.swing.*;

import app.CreationPlateau;
import model.*;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

public abstract class PlateauView extends JLayeredPane implements SizeHexagone {
    public Plateau p;
    public int platCenterX = 0;
    public int platCenterY = 0;

    public JScrollPane container;
    public LinkedList<Coordonnee> deplacement;
    public LinkedList<Couleur> perleADeplacer;
    public boolean deplacerPerle;
    public boolean saut;

    boolean partieDebutee;
    int baseAPlacer = 0;
     //tab de tab qui contient les coordonnees des cases autour des bases pour les cases de depart
     Coordonnee[][] autourDesBases = new Coordonnee[2][6];
    

    /**
     * @param p             Plateau model
     * @param partieDebute boolean, indique si la partie à débuter
     * @param container     correspond au Jscrollpane qui contiendra le PlateauView
     */
    public PlateauView(Plateau p, boolean partieDebute, JScrollPane container) {
        deplacement = new LinkedList<>();
        perleADeplacer = new LinkedList<>();
        this.p = p;
        this.setOpaque(true);
        this.setLayout(null);
        this.setPlateauView();
        this.partieDebutee = partieDebute;
        this.container = container;
        setVisible(true);
        setBackground(new Color(0, 0, 0,0));
    }

     //Change la vue pour un plateauPosePion
     void toPosePion(Coordonnee[] c1, Coordonnee[] c2, JScrollPane container, CreationPlateau plateau, int nbrPions) {
        PlateauPosePion ppp = new PlateauPosePion(p, c1, c2,container, plateau , nbrPions);
        container.setViewportView(ppp);
        container.repaint();
        container.revalidate();
    }

     
    public abstract void setPlateauView();

    public void toPoseBase(Plateau p , int nbrPions, JScrollPane container, CreationPlateau plateau){
        PlateauPoseBase pb = new PlateauPoseBase(p, nbrPions, partieDebutee,container , plateau);
        container.setViewportView(pb);
        pb.partieDebutee = true;
        pb.baseAPlacer = 1;
        container.repaint();
        container.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] txty = SizeHexagone.x3coord;
        int scax = 0;
        int scay = 0;
        g.setColor(new Color(100, 120, 180));
        for (Coordonnee c : p.plat_jeu.keySet()) {
            scax += HexagoneSize[1] / 2 * c.py;
            scax += HexagoneSize[0] * c.px;
            scay -= 3 * HexagoneSize[1] / 4 * c.py;

            if (p.plat_jeu.get(c) != null && p.plat_jeu.get(c).hasTuile()) {
                for (int i = 0; i < txty.length - 1; i++) {
                    for (int j = 0; j < txty[0].length; j++) {
                        txty[i][j] += platCenterX + scax + HexagoneSize[0] / 2;
                        txty[i + 1][j] += platCenterY + scay + HexagoneSize[1] / 2;
                    }
                }
                if (p.plat_jeu.get(c).getTuile().getNbBase() == 1) {
                    try {
                        g.drawImage(ImageIO.read(new File("src/resources/tuile_normal.png")), txty[0][0] - 40,
                                txty[1][0] - 60, null);
                        PionView.Disque shape = new PionView.Disque(new Ellipse2D.Double(txty[0][0] + 23, txty[1][0], 35, 35), Couleur.BASE1.getColor());
                        Graphics2D g2d= (Graphics2D) g;
                        g2d.setColor(shape.getColor());
                        g2d.draw(shape.getShape());
                        g2d.fill(shape.shape);
                        g.drawImage(ImageIO.read(new File("src/resources/tuile_base.png")), txty[0][0] + 30,
                                txty[1][0]+5, null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (p.plat_jeu.get(c).getTuile().getNbBase() == 2) {
                    try {
                        g.drawImage(ImageIO.read(new File("src/resources/tuile_normal.png")), txty[0][0] - 40,
                                txty[1][0] - 60, null);
                        PionView.Disque shape = new PionView.Disque(new Ellipse2D.Double(txty[0][0] + 23, txty[1][0], 35, 35), Couleur.BASE2.getColor());
                        Graphics2D g2d= (Graphics2D) g;
                        g2d.setColor(shape.getColor());
                        g2d.draw(shape.getShape());
                        g2d.fill(shape.shape);
                        g.drawImage(ImageIO.read(new File("src/resources/tuile_base.png")), txty[0][0] + 30,
                                txty[1][0]+5, null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        g.drawImage(ImageIO.read(new File("src/resources/tuile_normal.png")), txty[0][0] - 40,
                                txty[1][0] - 60, null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                for (int i = 0; i < txty.length - 1; i++) {
                    for (int j = 0; j < txty[0].length; j++) {
                        txty[i][j] -= platCenterX + scax + HexagoneSize[0] / 2;
                        txty[i + 1][j] -= platCenterY + scay + HexagoneSize[1] / 2;
                    }
                }
            }
            scax = 0;
            scay = 0;
        }
    }
}
