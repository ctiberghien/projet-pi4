package View;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import app.CreationPlateau;
import model.*;
import app.*;
import View.Custom.TypeButtons.*;
import java.awt.event.*;
import java.util.Stack;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

import model.Plateau;

public class PlateauPosePion extends PlateauView {

    // tab de tab qui contient les coordonnées des cases autour de la base bleue
    Coordonnee[] autourBaseBleu;

    // tab de tab qui contient les coordonnées des cases autour de la base rouge
    Coordonnee[] autourBaseRouge;

    // tab qui contient le nombre de pions placés
    int[] nbPionsPlaces = { 0, 0 };

    // bool qui se met à true quand tous les pions ont été posés
    boolean tag;

    int nbPions;


    CreationPlateau frame;

    public PlateauPosePion(Plateau p, Coordonnee[] autourBaseBleu, Coordonnee[] autourBaseRouge,
            JScrollPane container,  CreationPlateau plateau, int nbrPions) {
        super(p, false, container);
        this.autourBaseBleu = autourBaseBleu;
        this.autourBaseRouge = autourBaseRouge;
        nbPions = nbrPions;
        tag = true;
        setPlateauView();
        repaint();
        revalidate();
        frame = plateau;
    }

    class CaseDeDepart extends JComponent {
        // bool qui est true si la case est associée à la base rouge, bleu sinon
        boolean color;

        // int qui correspond à un indice dans autourBaseRouge ou autourBaseBleu
        int coord;

        Color couleurCase;

        public CaseDeDepart(boolean color, int coord, int nbCouleurCase) {
            this.color = color;
            this.coord = coord;
            switch (nbCouleurCase) {
                case 0 -> couleurCase = Couleur.PERLE1.getColor();
                case 1 -> couleurCase = Couleur.PERLE3.getColor();
                case 2 -> couleurCase = Couleur.PERLE2.getColor();
                case 3 -> couleurCase = Couleur.PERLE4.getColor();
                case 4 -> couleurCase = Couleur.PERLE5.getColor();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            PionView.Disque shape = new PionView.Disque(new Ellipse2D.Double(0, 0, 50, 50), couleurCase);
            g2d.setColor(shape.getColor());
            g2d.draw(shape.getShape());
            g2d.fill(shape.shape);
            addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (color) {
                        // s'il n'y a pas de pion
                        if (p.plat_jeu.get(autourBaseRouge[coord]).pion == null) {
                            if (nbPionsPlaces[1] != nbPions) {
                                Stack<Couleur> pile = new Stack<>();
                                switch (nbPionsPlaces[1]) {
                                    case 0 -> {
                                        if (nbPions>2)pile.add(Couleur.PERLE1);
                                        pile.add(Couleur.PERLE1);
                                    }
                                    case 1 -> {
                                        if (nbPions>2)pile.add(Couleur.PERLE3);
                                        pile.add(Couleur.PERLE3);
                                    }
                                    case 2 -> {
                                        pile.add(Couleur.PERLE2);
                                        pile.add(Couleur.PERLE2);
                                    }
                                    case 3 -> {
                                        pile.add(Couleur.PERLE4);
                                        pile.add(Couleur.PERLE4);
                                    }
                                    case 4 -> {
                                        pile.add(Couleur.PERLE5);
                                        pile.add(Couleur.PERLE5);
                                    }
                                }
                                Pion tmp = new Pion(
                                        frame.j2, pile, autourBaseRouge[coord], -1);
                                p.plat_jeu.get(autourBaseRouge[coord]).pion = tmp;
                                frame.j2.pions.add( tmp);
                                nbPionsPlaces[1] += 1;
                                autourBaseRouge[coord] = null;

                            }
                        }
                    } else {
                        if (p.plat_jeu.get(autourBaseBleu[coord]).pion == null) {
                            if (nbPionsPlaces[0] != nbPions) {
                                Stack<Couleur> pile = new Stack<>();
                                switch (nbPionsPlaces[0]) {
                                    case 0 -> {
                                        if (nbPions>2)pile.add(Couleur.PERLE1);
                                        pile.add(Couleur.PERLE1);
                                    }
                                    case 1 -> {
                                        if (nbPions>2)pile.add(Couleur.PERLE3);
                                        pile.add(Couleur.PERLE3);
                                    }
                                    case 2 -> {
                                        pile.add(Couleur.PERLE2);
                                        pile.add(Couleur.PERLE2);
                                    }
                                    case 3 -> {
                                        pile.add(Couleur.PERLE4);
                                        pile.add(Couleur.PERLE4);
                                    }
                                    case 4 -> {
                                        pile.add(Couleur.PERLE5);
                                        pile.add(Couleur.PERLE5);
                                    }
                                }
                                Pion tmp = new Pion(
                                        frame.j1, pile, autourBaseBleu[coord], -1);
                                p.plat_jeu.get(autourBaseBleu[coord]).pion = tmp;
                                frame.j1.pions.add(tmp);
                                nbPionsPlaces[0] += 1;
                                autourBaseBleu[coord] = null;
                            }
                        }
                    }
                    // tous les pions sont poses
                    if (nbPionsPlaces[0] == nbPions && nbPionsPlaces[1] == nbPions) {
                        tag = false;
                        frame.toAccept();
                        frame.updateInfoText("Plateau terminé , Appuyer sur Start pour lancer");

                        JButton sauvegardePlateau = new ButtonIngame2("Sauvegarder");
                        sauvegardePlateau.setFont(new Font("Tahoma", Font.BOLD, 15));
                        sauvegardePlateau.setBounds(Launcher.width * 84 / 100, Launcher.height * 20 / 100, Launcher.width * 10 / 100,
                        Launcher.height * 50 / 1000);
                        sauvegardePlateau.addActionListener(event -> sauvegardePlateau());
                        frame.add(sauvegardePlateau, DRAG_LAYER);
                    }
                    container.repaint();
                    container.revalidate();
                    setPlateauView();
                }
            });
        }
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
        this.setLayout(null);
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
                if (p.plat_jeu.get(c) != null) {
                    if (tag) {
                        // vérification si c correspond à une coord autour des bases et si oui poser la
                        // case de
                        // depart
                        if (nbPionsPlaces[0] != nbPions) {
                            for (int i = 0; i < autourBaseBleu.length; i++) {
                                if (autourBaseBleu[i] != null) {
                                    if (autourBaseBleu[i].px == c.px && autourBaseBleu[i].py == c.py) {
                                        CaseDeDepart test = new CaseDeDepart(false, i, nbPionsPlaces[0]);
                                        test.setBounds(platCenterX + scax + 20, scay + platCenterY + 10, 50, 50);
                                        add(test);
                                    }
                                }
                            }
                        }
                        if (nbPionsPlaces[1] != nbPions) {
                            for (int i = 0; i < autourBaseRouge.length; i++) {
                                if (autourBaseRouge[i] != null) {
                                    if (autourBaseRouge[i].px == c.px && autourBaseRouge[i].py == c.py) {
                                        CaseDeDepart test = new CaseDeDepart(true, i, nbPionsPlaces[1]);
                                        test.setBounds(platCenterX + scax + 20, scay + platCenterY + 10, 50, 50);
                                        add(test);
                                    }
                                }
                            }
                        }
                    }
                    if (p.plat_jeu.get(c).pion != null) {
                        PionView pv = new PionView(p.plat_jeu.get(c).pion, p.plat_jeu.get(c).pion.joueur.getCouleur(),
                                new Coordonnee(platCenterX + scax + 20, scay + platCenterY + 10), false);
                        p.plat_jeu.get(c).pion.pionView = pv;
                        add(pv, DRAG_LAYER);
                    }
                }
                this.add(tmp);
            }
            scax = 0;
            scay = 0;
        }

    }

    public void sauvegardePlateau() {
        BufferedImage image = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        this.paint(g2d);
        g2d.dispose();
        File folder = new File("src/resources/PlateauImg");
        File[] files = folder.listFiles();
        int lastFileNumber = 0;
        assert files != null;
        for (File file : files) {
            String fileName = file.getName();
            fileName = fileName.substring(0, fileName.length()-4);
            if (fileName.startsWith("Plateau")) {
                try {
                    int fileNumber = Integer.parseInt(fileName.substring(7));
                    if (fileNumber > lastFileNumber) {
                        lastFileNumber = fileNumber;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        String newFileName = "Plateau" + (lastFileNumber + 1) + ".png";
        File file = new File(folder, newFileName);
        try {
            ImageIO.write(image, "png", file);
        } catch (Exception ignored) {}

        try {
            Model m = frame.createModel(p);
            InGameView game = frame.createInGameView(m);
            PlateauJeu pan = new PlateauJeu(p, null, game, m);
            final FileOutputStream sauvegarde = new FileOutputStream(
                            "src/resources/PlateauSauvegarde/Plateau"+ (lastFileNumber+1) +".ser");
            ObjectOutputStream oos = new ObjectOutputStream(sauvegarde);
            oos.writeObject(pan);
            oos.flush();
            oos.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

}
