package app;

import View.Custom.ScrollBarCustom.ScrollBarUI;
import View.Custom.TypeButtons.ButtonIngame1;
import View.Custom.TypeButtons.ButtonIngame2;
import View.Custom.TypeButtons.ButtonIngame3;
import View.Inventaire;
import View.PionView;
import View.PlateauJeu;
import model.*;
import model.AIJoueur;
import model.Joueur;
import model.Model;
import model.Objets.Ventilateur;
import model.Pion;
import model.Plateau;
import model.Objets.FreeMove;
import model.Objets.Mur;
import model.Objets.Renvoi;
import model.Objets.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.Stack;
import java.io.*;
import java.nio.file.*;

import static java.lang.Thread.*;

public class InGameView extends JLayeredPane {
        public PlateauJeu pan;
        public JButton b1;
        public JButton sac;
        public Model model;

        public Inventaire inventaire;
        public JButton b2;
        JButton valider;
        JButton annuler;
        JButton quitter;
        public JFrame frame;
        public JLabel textTour;
        public PionView perles;
        public boolean haswin = false;

        public InGameView(JFrame frame, Model model, PlateauJeu pa) {
                Plateau p1 = model.plat;
                this.model = model;
                this.frame = frame;
                this.setLayout(null);
                // On set le Jpanel du jeu
                if (pa == null) {
                        pan = new PlateauJeu(p1, null, this, model);
                } else {
                        pan = pa;
                        pan.game = this;
                }
                switch (pan.p.tour % 2) {
                        case 0:
                                pan.joueurenjeu = model.j2;
                                break;
                        case 1:
                                pan.joueurenjeu = model.j1;
                                break;
                }
                JScrollPane jscp = new JScrollPane(pan);
                // On set le Jscrollpane/bar
                jscp.getVerticalScrollBar().setUnitIncrement(3);
                jscp.getHorizontalScrollBar().setUnitIncrement(3);
                jscp.getVerticalScrollBar().setUI(new ScrollBarUI());
                jscp.getHorizontalScrollBar().setUI(new ScrollBarUI());
                jscp.getVerticalScrollBar().setOpaque(false);
                jscp.getHorizontalScrollBar().setOpaque(false);
                jscp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                jscp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                jscp.setOpaque(false);
                jscp.setBounds(2 * Launcher.width / 100, 35 * Launcher.height / 1000, 76 * Launcher.width / 100,
                                84 * Launcher.height / 100);
                pan.container = jscp;
                pan.setPlateauView();
                // rend le fond du plateau invisible
                pan.setBackground(new Color(0, 0, 0, 0));

                this.add(jscp, MODAL_LAYER);

                JPanel p = new JPanel() {
                        private final Image img1 = Toolkit.getDefaultToolkit()
                                        .getImage("src/resources/GUI/blacklayout.png");
                        private final Image img = img1.getScaledInstance(Launcher.width, Launcher.height,
                                        Image.SCALE_DEFAULT);

                        @Override
                        public void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                g.drawImage(img, 0, 0, null);
                        }
                };
                p.setBounds(0, 0, Launcher.width, Launcher.height);
                p.setOpaque(false);
                this.add(p, MODAL_LAYER);

                JComponent bgPlateauPanel = new JComponent() {
                        private final Image img1 = Toolkit.getDefaultToolkit()
                                        .getImage("src/resources/GUI/bgplateau.jpg");
                        private final Image img = img1.getScaledInstance(80 * Launcher.width / 100,
                                        85 * Launcher.height / 100,
                                        Image.SCALE_DEFAULT);

                        @Override
                        public void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                g.drawImage(img, 0, 0, null);
                        }
                };
                bgPlateauPanel.setBounds(2 * Launcher.width / 100, 35 * Launcher.height / 1000,
                                76 * Launcher.width / 100,
                                84 * Launcher.height / 100);
                this.add(bgPlateauPanel, DEFAULT_LAYER);

                sac = new ButtonIngame2("Inventaire");
                sac.setBounds(Launcher.width * 83 / 100, Launcher.height * 13 / 100, Launcher.width * 10 / 100,
                                Launcher.height * 50 / 1000);
                this.add(sac, POPUP_LAYER);
                inventaire = new Inventaire();
                inventaire.setBounds(Launcher.width / 2 - inventaire.getPreferredSize().width / 2,
                                Launcher.height / 2 - inventaire.getPreferredSize().height / 2,
                                inventaire.getPreferredSize().width, inventaire.getPreferredSize().height);
                inventaire.setVisible(false);
                inventaire.setInitInv(this, pan);
                inventaire.setInventaire(pan.joueurenjeu);
                inventaire.setOpaque(true);
                this.add(inventaire, DRAG_LAYER);
                if (pan.control != null)
                        sac.setVisible(false);
                fondGris f = new fondGris();
                sac.addActionListener(event -> {
                        inventaire.setInventaire(pan.joueurenjeu);
                        inventaire.setVisible(!inventaire.isVisible());
                        if (inventaire.isVisible()) {
                                InGameView.this.setLayer(sac, DRAG_LAYER);
                                add(f, DRAG_LAYER);
                        } else {
                                InGameView.this.setLayer(sac, POPUP_LAYER);
                                remove(f);
                        }
                        repaint();
                });
                pan.model.j1.sac.add(new FreeMove());
                pan.model.j1.sac.add(new Renvoi());
                pan.model.j1.sac.add(new Mur());
                pan.model.j1.sac.add(new Ventilateur());
                pan.model.j1.sac.add(new Switch());
                b2 = new ButtonIngame1("Bouton 2");
                b2.setBounds(Launcher.width * 83 / 100, Launcher.height * 22 / 100, Launcher.width * 10 / 100,
                                Launcher.height * 50 / 1000);

                b2.addActionListener(event -> {
                        System.out.println(pan.joueurenjeu.sac);
                });
                b2.removeActionListener(b2.getActionListeners()[0]);

                b2.addActionListener(event -> {
                        System.out.println("HHELLOOO");
                });
                ///////////////////////////////// test
                this.add(b2, POPUP_LAYER);

                textTour = new JLabel();

                if (pan.control==null)  textTour.setText("Tour du Joueur " + (pan.p.tour % 2 + 1) + " : ");
                textTour.setFont(new Font("Tahoma", Font.PLAIN, 30));
                this.add(textTour, POPUP_LAYER);

                perles = new PionView(new Pion(new Joueur(null, Couleur.BASE1.getColor(), 1), new Stack<>(), null, -1),
                                Couleur.BASE1.getColor(), false,
                                true);
                perles.setPreferredSize(new Dimension(32 * Launcher.width / 1000, 620 * Launcher.height / 10000));
                this.add(perles, POPUP_LAYER);

                JPanel infoArea = new JPanel();
                infoArea.setLayout(new FlowLayout(FlowLayout.CENTER));
                infoArea.setBackground(new Color(255, 228, 181));
                infoArea.add(textTour);
                infoArea.add(perles);

                infoArea.setBounds(17 * Launcher.width / 100, 89 * Launcher.height / 100, 46 * Launcher.width / 100,
                                7 * Launcher.height / 100);

                this.add(infoArea, POPUP_LAYER);

                //////////////////////////////

                valider = new ButtonIngame2("valider");

                valider.setBounds(Launcher.width * 83 / 100, Launcher.height * 32 / 100,
                                Launcher.width * 10 / 100,
                                Launcher.height * 50 / 1000);
                valider.addActionListener(e -> {
                        if (Plateau.isJumping) {
                                pan.deplacement.removeLast();
                        }
                        if (!pan.deplacement.isEmpty()) {
                                // on vérifie si le joueur est sur la base adverse et donc s'il a gagné
                                if (p1.plat_jeu.get(pan.deplacement.getLast()).hasTuile()) {
                                        if (p1.plat_jeu.get(pan.deplacement.getLast()).getTuile().getNbBase() != 0) {
                                                try {
                                                        Path fileToDelete = Paths.get("src/resources/.sauvegarde.ser");
                                                        Files.delete(fileToDelete);
                                                } catch (Exception ignored) {
                                                }
                                                haswin = true;
                                                setFin(p1, jscp, p1.plat_jeu
                                                                .get(pan.deplacement.getLast()).pion.joueur);
                                                if (pan.control != null) {
                                                        if (pan.control.c != null)
                                                                pan.control.c.sendMessage("win");
                                                }
                                        }
                                }
                                // on vérifie si le pion est sur une case avec un objet dessus
                                if (p1.plat_jeu.get(pan.deplacement.getLast()).obj != null) {
                                        // on ajoute l'objet
                                        System.out.println("VOICI : "
                                                        + p1.plat_jeu.get(pan.deplacement.getLast()).obj.getClass());
                                        System.out.println(p1.plat_jeu.get(pan.deplacement.getLast()).pion.joueur.sac
                                                        .add(p1.plat_jeu.get(pan.deplacement.getLast()).obj));
                                        System.out.println(p1.plat_jeu.get(pan.deplacement.getLast()).pion.joueur
                                                        .sacCountItem(new FreeMove()));
                                        p1.plat_jeu.get(pan.deplacement.getLast()).obj = null;

                                }

                        }
                        // Il y a trois cas, 1 si le joueur a bougé, 2 si le joueur a juste déplacer une
                        // perle et 3 si le joueur a juste sauté sans bouger de perle
                        // Ces cas engendre des choses différentes dans le code
                        if (pan.perleADeplacer.isEmpty() && pan.deplacement.size() == 1) {
                                if (!p1.plat_jeu.get(pan.deplacement.getLast()).pion.perles.isEmpty()) {
                                        pan.perleADeplacer
                                                        .add(pan.p.plat_jeu.get(pan.deplacement.getLast()).pion.perles
                                                                        .pop());
                                        perles.pion.perles.add(pan.perleADeplacer.getLast());
                                        perles.repaint();
                                        pan.deplacerPerle = true;
                                }
                        } else if (!pan.perleADeplacer.isEmpty() && !pan.deplacement.isEmpty()) {
                                pan.deplacerPerle = true;
                        } else if (pan.perleADeplacer.isEmpty() && pan.deplacement.size() > 1) {
                                if (pan.control == null)
                                        sac.setVisible(true);
                                // Tout les x tours le jeu ajoute des objets sur le terrain
                                if (pan.p.objetActif) {
                                        if (pan.p.tour % pan.p.objTurn == 0) {
                                                pan.p.ajoutObj();
                                        }
                                }
                                if (pan.p.isTPActive) {
                                        if (pan.p.teleporteur.hasToSpawn()
                                                        || (pan.p.tour / 2 == pan.p.teleporteur.nbTour
                                                                        && !pan.p.teleporteur.isSpawned)) {
                                                pan.p.teleporteur.ajoutTp();
                                        }
                                }
                                pan.deplacement = new LinkedList<>();
                                pan.p.tour = pan.p.tour + 1;
                                p1.teleporteur.tour += 1;
                                p1.decreaseBlockCase();

                                Joueur tmp = switch (pan.p.tour % 2 + 1) {
                                        case 1 -> model.j2;
                                        case 2 -> model.j1;
                                        default -> null;
                                };
                                if (pan.control != null) {
                                        pan.control.c.sendMessage(pan.p.genereString(true, true));
                                        textTour.setText(
                                                        "Tour de " + (pan.control.c.pseudos[pan.p.tour % 2]) + " : ");
                                        pan.p.tour = -1;
                                } else {
                                        textTour.setText("Tour du Joueur " + (pan.p.tour % 2 + 1) + " : ");
                                }

                                if (tmp instanceof AIJoueur) {

                                        ((AIJoueur) tmp).play();
                                        try {
                                                sleep(300);
                                        } catch (InterruptedException e1) {
                                                e1.printStackTrace();
                                        }
                                        if (((AIJoueur) tmp).haswin) {
                                                setFin(p1, jscp, tmp);
                                        }
                                        pan.p.tour = pan.p.tour + 1;
                                        p1.teleporteur.tour += 1;
                                        // on réduit le temps de bloquage des cases mur
                                        p1.decreaseBlockCase();

                                        switch (pan.p.tour % 2) {
                                                case 0:
                                                        pan.joueurenjeu = model.j2;
                                                        break;
                                                default:
                                                        pan.joueurenjeu = model.j1;
                                                        break;
                                        }
                                        pan.setPlateauView();
                                        revalidate();
                                        textTour.setText("Tour du Joueur " + (pan.p.tour % 2 + 1) + " : ");
                                }

                        }
                        p1.teleporteur.usedNow = false;
                        Plateau.isJumping = false;
                        pan.saut = false;
                        pan.setPlateauView();
                        pan.revalidate();
                        pan.container.repaint();

                });
                this.add(valider, POPUP_LAYER);

                annuler = new ButtonIngame1("annuler");
                annuler.setBounds(Launcher.width * 83 / 100, Launcher.height * 42 / 100,
                                Launcher.width * 10 / 100,
                                Launcher.height * 50 / 1000);
                annuler.addActionListener(e -> {
                        textTour.setFont(new Font("Tahoma", Font.PLAIN, 30));
                        if (pan.control==null) textTour.setText("Tour du Joueur " + (pan.p.tour % 2 + 1) + " : ");
                        if (!pan.deplacement.isEmpty() && !pan.deplacerPerle) {
                                if (pan.deplacement.size() > 1) {
                                        if (p1.teleporteur.isUsed && p1.teleporteur.tour < p1.teleporteur.nbTour) {
                                                if (p1.teleporteur.usedNow) {
                                                        p1.teleporteur.isUsed = false;
                                                        p1.teleporteur.canBeUsed = false;
                                                        p1.teleporteur.usedNow = false;
                                                        p1.teleporteur.tour = 0;

                                                }
                                                System.out.println("On doit pas le faire reaparaitre a voir mdr");
                                        } else {
                                                p1.teleporteur.isUsed = false;
                                                p1.teleporteur.canBeUsed = false;
                                                p1.teleporteur.tour = 0;
                                        }
                                        if (pan.deplacement.getFirst().equals(pan.deplacement.getLast())) {
                                                int size = pan.perleADeplacer.size();
                                                for (int i = 0; i < size; i++) {
                                                        p1.plat_jeu.get(pan.deplacement.getFirst()).pion.perles
                                                                        .add(pan.perleADeplacer.getLast());
                                                        pan.perleADeplacer.removeLast();
                                                }
                                        } else {
                                                if (Plateau.isJumping) {
                                                        if (!(p1.plat_jeu.get(pan.deplacement.getLast()).pion
                                                                        .equals(p1.plat_jeu.get(pan.deplacement
                                                                                        .get(pan.deplacement.size()
                                                                                                        - 2)).pion))) {
                                                                pan.deplacement.removeLast();
                                                                Plateau.isJumping = false;
                                                                annuler.doClick();
                                                                return;
                                                        }
                                                }
                                                remove(p1.plat_jeu.get(pan.deplacement.getLast()).pion.pionView);
                                                pan.setPlateauView();
                                                repaint();
                                                pan.revalidate();
                                                Pion pion = p1.plat_jeu.get(pan.deplacement.getLast()).pion;
                                                p1.plat_jeu.get(pan.deplacement.getFirst()).pion = pion;
                                                pion.coordonnee = pan.deplacement.getFirst();
                                                p1.plat_jeu.get(pan.deplacement.getLast()).pion = null;
                                                Plateau.isJumping = false;

                                                int taille = pan.perleADeplacer.size();
                                                for (int i = 0; i < taille; i++) {
                                                        p1.plat_jeu.get(pan.deplacement.getFirst()).pion.perles
                                                                        .add(pan.perleADeplacer.getLast());
                                                        pan.perleADeplacer.removeLast();
                                                }
                                        }
                                } else if (!pan.perleADeplacer.isEmpty()) {
                                        p1.plat_jeu.get(pan.deplacement.getFirst()).pion.perles
                                                        .add(pan.perleADeplacer.pop());
                                }
                                sac.setVisible(true);
                                pan.saut = false;
                                pan.deplacement = new LinkedList<>();
                                perles.pion.perles = new Stack<>();
                                perles.repaint();
                        }
                        pan.setPlateauView();
                        pan.revalidate();
                        pan.container.repaint();
                        revalidate();
                        repaint();
                });

                this.add(annuler, POPUP_LAYER);

                quitter = new ButtonIngame3("quitter");
                quitter.setBounds(0, 0,
                                Launcher.width * 7 / 100,
                                Launcher.height * 35 / 1000);
                Sauvegarde sauvegarde = new Sauvegarde(frame);
                quitter.addActionListener(event -> {
                        /*if (pan.control.c!=null) {
                                pan.control.c.disconnect();
                        }*/
                        sauvegarde.getOui().addActionListener(event2 -> {
                                if (!haswin) {
                                        try {
                                                pan.deplacerPerle = false;
                                                annuler.doClick();
                                               
                                                        final FileOutputStream sauvegardeFile = new FileOutputStream(
                                                                "src/resources/.sauvegarde.ser");
                                                        ObjectOutputStream oos = new ObjectOutputStream(sauvegardeFile);
                                                        oos.writeObject(pan);
                                                        oos.flush();
                                                        oos.close();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                }
                                Menu m = new Menu(frame);
                                frame.setContentPane(m);
                                frame.revalidate();
                                frame.repaint();
                        });
                        sauvegarde.getNon().addActionListener(event3 -> {
                                Menu m = new Menu(frame);
                                frame.setContentPane(m);
                                frame.revalidate();
                                frame.repaint();
                        });
                        if (pan.control==null) {add(sauvegarde, DRAG_LAYER);} else {
                                
                                        try {
                                            pan.control.c.socket.close();
                                        } catch (IOException e1) {}
                                        pan.control.c.receiveMessages(false);
                                    
                                Menu m = new Menu(frame);
                                frame.setContentPane(m);
                                frame.revalidate();
                                frame.repaint();
                        }
                });
                this.add(quitter, POPUP_LAYER);
                repaint();
                revalidate();
        }

        public void setFin(Plateau p1, JScrollPane jscp, Joueur jr) {
                try {
                        Path fileToDelete = Paths.get("src/resources/.sauvegarde.ser");
                        Files.delete(fileToDelete);
                } catch (Exception ignored) {
                }
                JPanel fin = new JPanel();
                fin.setLayout(new BorderLayout());
                fin.setBackground(new Color(100, 100, 100, 150));
                JLabel texteFin = new JLabel("Joueur " + jr.getTurn()
                                + " a gagné !");
                if (pan.control != null)
                        texteFin = new JLabel(jr.nom + " a gagné !");
                texteFin.setForeground(Color.white);
                texteFin.setVerticalAlignment(SwingConstants.CENTER);
                texteFin.setHorizontalAlignment(SwingConstants.CENTER);
                texteFin.setFont(new Font("Tahoma", Font.BOLD, 50));
                fin.add(texteFin);
                fin.setBounds(2 * Launcher.width / 100, 36 * Launcher.height /
                                1000,
                                76 * Launcher.width / 100,
                                84 * Launcher.height / 100);

                String arg = "src/resources/GUI/cherry2.gif";
                ImageIcon icon = new ImageIcon(arg);
                Image tmp = icon.getImage();
                Image newimg = tmp.getScaledInstance(80 * Launcher.width / 100,
                                85 * Launcher.height / 100,
                                java.awt.Image.SCALE_DEFAULT);
                icon.setImage(newimg);
                JLabel cherry = new JLabel();
                cherry.setBounds(2 * Launcher.width / 100, 35 * Launcher.height / 1000,
                                76 * Launcher.width / 100,
                                84 * Launcher.height / 100);
                cherry.setIcon(icon);
                this.remove(jscp);
                this.add(fin, MODAL_LAYER);
                this.add(cherry, POPUP_LAYER);
                repaint();
        }

        class Sauvegarde extends JPanel {
                JButton oui;
                JButton non;
                JPanel sauvegarde;

                public Sauvegarde(JFrame frame) {
                        setBounds(0, 0, Launcher.width, Launcher.height);
                        setOpaque(false);
                        setBackground(Color.gray);
                        addMouseListener(new MouseAdapter() {
                        });
                        setLayout(null);
                        sauvegarde = new JPanel();
                        sauvegarde.setBounds(Launcher.width / 2 - Launcher.width / 10,
                                        Launcher.height / 2 - Launcher.height / 10, Launcher.width / 5,
                                        Launcher.height / 5);
                        sauvegarde.setLayout(null);
                        sauvegarde.setBackground(new Color(30, 30, 30));

                        oui = new JButton("Oui");
                        oui.setBounds(sauvegarde.getWidth() / 5, sauvegarde.getHeight() / 3 * 2,
                                        sauvegarde.getWidth() / 5, sauvegarde.getHeight() / 6);
                        non = new JButton("Non");
                        non.setBounds(sauvegarde.getWidth() / 5 * 3, sauvegarde.getHeight() / 3 * 2,
                                        sauvegarde.getWidth() / 5, sauvegarde.getHeight() / 6);

                        JLabel texte = new JLabel("Voulez vous sauvegarder ?");
                        texte.setBounds(sauvegarde.getWidth() / 10, sauvegarde.getHeight() / 10 * 2,
                                        sauvegarde.getWidth() / 10 * 8, sauvegarde.getHeight() / 10 * 2);
                        texte.setBackground(new Color(255, 228, 181));
                        texte.setOpaque(true);
                        texte.setHorizontalAlignment(SwingConstants.CENTER);
                        texte.setVerticalAlignment(SwingConstants.CENTER);

                        sauvegarde.add(oui);
                        sauvegarde.add(non);
                        sauvegarde.add(texte);
                        add(sauvegarde);
                }

                public JButton getOui() {
                        return oui;
                }

                public JButton getNon() {
                        return non;
                }

                @Override
                protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
                        g2.setColor(getBackground());
                        g2.fillRect(0, 0, getWidth(), getHeight());
                        g2.dispose();
                        super.paintComponent(g);
                }
        }

        class fondGris extends JPanel {

                public fondGris() {
                        setBounds(0, 0, Launcher.width, Launcher.height);
                        setOpaque(false);
                        setBackground(Color.gray);
                        addMouseListener(new MouseAdapter() {
                        });
                }

                @Override
                protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
                        g2.setColor(getBackground());
                        g2.fillRect(0, 0, getWidth(), getHeight());
                        g2.dispose();
                        super.paintComponent(g);
                }
        }
}
