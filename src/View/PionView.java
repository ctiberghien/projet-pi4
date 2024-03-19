package View;


import model.Coordonnee;
import model.Couleur;
import model.Pion;

import javax.swing.*;

import app.Launcher;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;

public class PionView extends JComponent {

    /*
     * La list composant est la liste des composants graphique des pions : le rond
     * principal et au plus les 3 autres ronds
     * des perles du pion
     */

    public Pion pion;
    Color color;
    ArrayList<Disque> composant;
    public Boolean selectionnee;
    Boolean invisible;

    /**
     * @param pion  : Pion au quelle est reliée la vue dans le model
     * @param color : Couleur du pion et du joueur
     */

    public PionView(Pion pion, Color color, Boolean select, boolean invisible) {
        this.pion = pion;
        this.color = color;
        this.invisible = invisible;
        selectionnee = select;

        setBounds(new Rectangle(500, 435, 43, 58));
        composant = new ArrayList<>(4);

        composant.add(new Disque(new Ellipse2D.Double(0, 14, 42, 42), color));
        Couleur[] tmp = pion.getColors();
        int y = 0;
        for (int i = 0; i < tmp.length; i++) {
            if (i == 1) {
                y = 18;
            } else if (i == 2) {
                y = 38;
            }
            addCompo(y , tmp[i]);
        }
    }

    public PionView(Pion pion, Color color, Coordonnee cords, Boolean select) {
        this.pion = pion;
        this.color = color;
        selectionnee = select;
        invisible = false;

        setBounds(new Rectangle(cords.px, cords.py, 43, 58));
        composant = new ArrayList<>(4);

        composant.add(new Disque(new Ellipse2D.Double(0, 14, 42, 42), color));
    }

    /*
     * Cette classe interne permet de facilement créer des formes de rond d'une
     * certaine couleur et avec un contour noir
     */

    public static class Disque implements Serializable {
        protected Shape shape;
        protected Color color;

        public Disque(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public Shape getShape() {
            return shape;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        composant = new ArrayList<>();
        if (!invisible) {
            composant.add(new Disque(new Ellipse2D.Double(0, 14, 42, 42), color));
        } else {
            Rectangle r = new Rectangle(new Dimension(26 * Launcher.width / 1000, 612 * Launcher.height / 10000));
            g2d.draw(r);

        }
        Couleur[] tmp = pion.getColors();
        int y = 0;
        for (int i = 0; i < tmp.length; i++) {
            if (i == 1) {
                y = 18;
            } else if (i == 2) {
                y = 38;
            }
            addCompo(y , tmp[i]);
        }
        for (Disque shape : composant) {
            g2d.setColor(shape.getColor());
            g2d.draw(shape.getShape());
            g2d.fill(shape.shape);
            if (selectionnee) {
                g2d.setColor(Color.WHITE);
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.draw(shape.getShape());
        }
    }

    void addCompo(int y, Couleur couleur){
        composant.add(new Disque(new Ellipse2D.Double(15, y, 12, 12), couleur.getColor()));
    }
}
