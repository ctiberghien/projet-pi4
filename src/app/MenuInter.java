package app;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MouseInputAdapter;
import model.*;
import View.*;
import model.TypeOfPlateau.Plateau1;
import model.TypeOfPlateau.Plateau2;
import model.TypeOfPlateau.Plateau3;
import java.nio.file.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import View.Custom.TypeButtons.ButtonIngame3;
import java.util.Arrays;

public class MenuInter extends JLayeredPane{

    int pageCourant;
    int numPlateau;
    int nbrPlateauPrefait;
    boolean isPlateauSave;
    JButton start;
    JButton supprimer;
    JButton[] plateaux;
    JCheckBox objet;
    JCheckBox teleportation;

    public MenuInter(JFrame frame, boolean bot) {
        this.setLayout(null);
        pageCourant = 1;

        // JPanel représentant l'endroit où sont placés les boutons de plateaux
        JPanel choixPlateau = new JPanel();
        choixPlateau.setBounds(0, Launcher.height * 15/100, Launcher.width * 4/5, Launcher.height * 75/100);
        choixPlateau.setLayout(new FlowLayout(FlowLayout.CENTER ,20,20));
        choixPlateau.setBackground(Color.BLACK);
        choixPlateau.setOpaque(false);
        choixPlateau.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        JComponent sideLayout = new JComponent() {
            private final Image img1 = Toolkit.getDefaultToolkit().getImage("src/resources/GUI/sideLayout.png");
            private final Image img = img1.getScaledInstance(Launcher.width /5, Launcher.height,Image.SCALE_DEFAULT);

            @Override
            public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(img, 0, 0, null);
            }
        };
        sideLayout.setBounds(Launcher.width * 4/5, 0, Launcher.width /5, Launcher.height);
        start = new ButtonIngame3("Start");
        start.setEnabled(false);
        start.setBounds(Launcher.width * 85 / 100, Launcher.height * 20 / 100, Launcher.width * 10 / 100,
                Launcher.height * 50 / 1000);
        start.addActionListener(event -> {
            if(isPlateauSave) {
                initialisePlateauSave(frame, "src/resources/PlateauSauvegarde/Plateau" + numPlateau + ".ser", bot, teleportation.isSelected(), objet.isSelected());
            }
            else initialiseJeu(frame, numPlateau, bot, teleportation.isSelected(), objet.isSelected());
        });

        supprimer = new ButtonIngame3("Supprimer");
        supprimer.setEnabled(false);
        supprimer.setBounds(Launcher.width * 85 / 100, Launcher.height * 30 / 100, Launcher.width * 10 / 100,
                Launcher.height * 50 / 1000);
        supprimer.addActionListener(event -> {
            if(isPlateauSave) {
                supprimerPlateau("src/resources/PlateauSauvegarde/Plateau" + numPlateau + ".ser",
                                    "src/resources/PlateauImg/Plateau" + numPlateau + ".png", numPlateau, frame, bot);
            }
        });

        objet = new JCheckBox("Objets");
        objet.setBounds(Launcher.width * 85 / 100, Launcher.height * 40 / 100, Launcher.width * 10 / 100,
        Launcher.height * 50 / 1000);
        objet.setBackground(new Color(50,50,50));
        objet.setForeground(Color.gray);
        objet.setFont(new Font("Tahoma", Font.BOLD, 15));

        teleportation = new JCheckBox("Téléportation");
        teleportation.setBounds(Launcher.width * 85 / 100, Launcher.height * 50 / 100, Launcher.width * 10 / 100,
        Launcher.height * 50 / 1000);
        teleportation.setBackground(new Color(50,50,50));
        teleportation.setForeground(Color.gray);
        teleportation.setFont(new Font("Tahoma", Font.BOLD, 15));

        Border sideborder = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK);
        sideLayout.setBorder(sideborder);

        File folder = new File("src/resources/PlateauImg");
        File[] files = folder.listFiles();
        assert files != null;
        int lastFileNumber = files.length;
        int nbrPage = 0;
        if(lastFileNumber%15==0) {
            nbrPage = lastFileNumber/15;
        } else {
            nbrPage = lastFileNumber/15+1;
        }
        nbrPage++;

        JPanel basDeLaPage = new JPanel();
        basDeLaPage.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        basDeLaPage.setOpaque(false);
        basDeLaPage.setBounds(0, Launcher.height * 90/100, Launcher.width, Launcher.height * 10/100);


        JButton suivant = new JButton("suivant");
        suivant.setPreferredSize(new Dimension(100,20));
        JLabel page = new JLabel(pageCourant + "/" + nbrPage);
        page.setForeground(Color.WHITE);
        JButton precedent = new JButton("précédent");
        precedent.setPreferredSize(new Dimension(100,20));

        final int nbrPagefinale = nbrPage;

        // Les JButton des différents plateaux
        plateaux = new JButton[nbrPage*15];
        initiliserBoutons();
        changerPage(pageCourant, choixPlateau);


        precedent.addActionListener(event -> {
            if(pageCourant>1) {
                pageCourant--;
                page.setText(pageCourant + "/" + nbrPagefinale);
                changerPage(pageCourant, choixPlateau);
            }
        });

        suivant.addActionListener(event -> {
            if(pageCourant<nbrPagefinale) {
                pageCourant++;
                page.setText(pageCourant + "/" + nbrPagefinale);
                changerPage(pageCourant, choixPlateau);
            }
        });
        basDeLaPage.add(precedent);
        basDeLaPage.add(page);
        basDeLaPage.add(suivant);

        JPanel bande = new JPanel();
        bande.setBounds(0, 0, Launcher.width * 4/5, Launcher.height * 15/100);
        bande.setBackground(new Color(30,30,30));
        bande.setOpaque(true);
        bande.setLayout(null);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        bande.setBorder(bottomBorder);


        JPanel textePanel = new JPanel();
        textePanel.setBounds(Launcher.width/2-Launcher.width/4 - sideLayout.getWidth()/2, Launcher.height * 15/200 - Launcher.height * 15/240, Launcher.width/2, Launcher.height * 15/120);
        textePanel.setOpaque(true);
        textePanel.setLayout(new BorderLayout());
        textePanel.setBackground(new Color(210, 201, 124));
        bande.add(textePanel);
        JLabel texte = new JLabel("Choisissez votre plateau");
        texte.setFont(new Font("Arial", Font.PLAIN, 30));
        texte.setHorizontalAlignment(SwingConstants.CENTER);
        textePanel.add(texte, BorderLayout.CENTER);


        JButton quitter = new ButtonIngame3("Quitter");
        quitter.setBounds(0, 0, Launcher.width * 7 / 100, Launcher.height * 35 / 1000);
        quitter.addActionListener(event -> {
            Menu m = new Menu(frame);
            frame.setContentPane(m);
            frame.revalidate();
            frame.repaint();
        });


        JComponent bgPlateauPanel = new JComponent() {
            private final Image img1 = Toolkit.getDefaultToolkit().getImage("src/resources/GUI/bgplateau.jpg");
            private final Image img = img1.getScaledInstance(Launcher.width, Launcher.height,Image.SCALE_DEFAULT);

            @Override
            public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(img, 0, 0, null);
            }
        };
        bgPlateauPanel.setBounds( 0, Launcher.height * 15/100, Launcher.width * 4/5, Launcher.height * 85/100);
        bgPlateauPanel.setOpaque(false);

        this.add(quitter, DRAG_LAYER);
        this.add(bande, DRAG_LAYER);
        this.add(bgPlateauPanel, MODAL_LAYER);
        this.setBackground(Color.BLACK);
        this.add(choixPlateau, DRAG_LAYER);
        this.add(basDeLaPage, DRAG_LAYER);
        this.add(sideLayout, MODAL_LAYER);
        this.add(start, DRAG_LAYER);
        this.add(supprimer, DRAG_LAYER);
        this.add(teleportation, DRAG_LAYER);
        this.add(objet, DRAG_LAYER);
    }

    public Plateau initialisePlateau(int num, Joueur j1, Joueur j2) {
        return switch (num) {
            case 1 -> new Plateau1(j1, j2);
            case 2 -> new Plateau2(j1, j2);
            case 3 -> new Plateau3(j1, j2);
            default ->
                // à mettre a jour si plus de plateaux sont ajoutés
                    null;
        };
    }

    public void initialiseJeu(JFrame frame, int numplateau, boolean bot, boolean tp, boolean obj) {
        Plateau p1;
        Joueur j1;
        if(bot) {
            j1 = new AIJoueur("AI2", Couleur.BASE2.getColor(), 2);
        } else {
            j1 = new Joueur("Joueur 2", Couleur.BASE2.getColor(), 2);
        }
        Joueur j2 = new Joueur("Joueur1", Couleur.BASE1.getColor(), 1);
        p1 = initialisePlateau(numplateau, j1, j2);
        p1.isTPActive = tp;
        p1.teleporteur = new Teleporteur(p1, 4);
        p1.objetActif = obj;
        p1.objTurn = 4;
        j1.setPlat(p1);
        Model model = new Model(p1, j1, j2);
        InGameView game = new InGameView(frame, model, null);
        frame.setContentPane(game);
        frame.revalidate();
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.revalidate();
            frame.repaint();
        });
    }

    public void initialisePlateauSave(JFrame frame, String PlateauSaveName, boolean bot, boolean tp, boolean obj) {
        try {
            Joueur j1;
            if(bot) {
                j1 = new AIJoueur("AI2", Couleur.BASE2.getColor(), 2);
            } else {
                j1 = new Joueur("Joueur 2", Couleur.BASE2.getColor(), 2);
            }
            Joueur j2 = new Joueur("Joueur1", Couleur.BASE1.getColor(), 1);
            final FileInputStream fichier = new FileInputStream(PlateauSaveName);
            ObjectInputStream ois = new ObjectInputStream(fichier);
            PlateauJeu pan = (PlateauJeu) ois.readObject();
            Plateau p = new Plateau(pan.p.plat_jeu, j1, j2);
            p.isTPActive = tp;
            p.teleporteur = new Teleporteur(p, 4);
            p.objetActif = obj;
            p.objTurn = 4;
            j1.setPlat(p);
            Model model = new Model(p, j1, j2);
            InGameView game = new InGameView(frame, model, null);
            frame.setContentPane(game);
            frame.revalidate();
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.revalidate();
                frame.repaint();
            });
            ois.close();
        } catch (Exception ignored) {ignored.printStackTrace();}
    }

    public void initiliserBoutons() {
        for(int i=0; i<plateaux.length; i++) {
            int numplateau = i+1;
            plateaux[i] = new JButton("Plateau "+ numplateau);
            plateaux[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            plateaux[i].setHorizontalTextPosition(SwingConstants.CENTER);
            plateaux[i].setBackground(Color.gray);
            plateaux[i].setPreferredSize(new Dimension(250,180));
            String PlateauName = "src/resources/PlateauImg/Plateau" + numplateau + ".png";
            String PlateauSaveName = "src/resources/PlateauSauvegarde/Plateau" + numplateau + ".ser";
            File file = new File(PlateauName);
            File fileSave = new File(PlateauSaveName);
            if(fileSave.exists()) {
                try {
                    BufferedImage originalImage = ImageIO.read(new File(PlateauName));
                    Image resizedImage = originalImage.getScaledInstance(250, 150, Image.SCALE_SMOOTH);
                    plateaux[i].setIcon(new ImageIcon(resizedImage));
                    plateaux[i].addActionListener(event -> {
                        SoundPlayer.joueSon(2, 0);
                        plateaux[numplateau-1].setSelected(true);
                        numPlateau = numplateau;
                        isPlateauSave = true;
                        start.setEnabled(true);
                        supprimer.setEnabled(true);
                    });
                } catch(Exception ignored) {}
            } else if (file.exists()) {
                nbrPlateauPrefait++;
                try {
                    BufferedImage originalImage = ImageIO.read(new File(PlateauName));
                    Image resizedImage = originalImage.getScaledInstance(250, 150, Image.SCALE_SMOOTH);
                    plateaux[i].setIcon(new ImageIcon(resizedImage));
                } catch(Exception ignored) {}
                plateaux[i].addActionListener(event -> {
                    SoundPlayer.joueSon(2, 0);
                    plateaux[numplateau-1].setSelected(true);
                    isPlateauSave = false;
                    numPlateau = numplateau;
                    start.setEnabled(true);
                    supprimer.setEnabled(false);
                });
            } else {
                plateaux[i].addActionListener(event -> {
                    start.setEnabled(false);
                    supprimer.setEnabled(false);
                });
            }
            plateaux[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public void changerPage(int page, JPanel choixPlateau) {
        choixPlateau.removeAll();
        for(int i=0; i<15; i++) {
            choixPlateau.add(plateaux[i+(page-1)*15]);
        }
        choixPlateau.revalidate();
        choixPlateau.repaint();
    }

    public void supprimerPlateau(String plateauSaveName, String plateauPngName, int plateauNum, JFrame frame, boolean bot) {
        if(isPlateauSave) {
            File fileToDelete = new File(plateauSaveName);
            File fileToDelete2 = new File(plateauPngName);
            if (fileToDelete.delete() && fileToDelete2.delete()) {
                File dossier = new File(fileToDelete.getParent());
                File dossier2 = new File(fileToDelete2.getParent());
                File[] files = dossier.listFiles();
                File[] filesPng = dossier2.listFiles();
                assert filesPng != null;
                Arrays.sort(filesPng);
                assert files != null;
                Arrays.sort(files);
                for(int i=plateauNum-1; i<filesPng.length; i++) {
                    if (filesPng[i].isFile() && filesPng[i].getName().startsWith("Plateau")) {
                        String newFileNameSer = dossier + "/Plateau" + (i+1) + files[i - nbrPlateauPrefait].getName().substring(files[i - nbrPlateauPrefait].getName().lastIndexOf('.'));
                        String newFileNamePng = dossier2 + "/Plateau" + (i+1) + filesPng[i].getName().substring(filesPng[i].getName().lastIndexOf("."));
                        files[i-nbrPlateauPrefait].renameTo(new File(newFileNameSer));
                        filesPng[i].renameTo(new File(newFileNamePng));
                    }
                }
                
            }
            SoundPlayer.joueSon(2, 0);
            MenuInter m = new MenuInter(frame, bot);
            m.setVisible(true);
            frame.setContentPane(m);
            frame.revalidate();
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.revalidate();
                frame.repaint();
            });
        }
    }
}
