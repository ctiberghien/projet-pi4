package View;

import app.Launcher;
import app.Parametre;
import model.Couleur;

import javax.imageio.ImageIO;
import javax.swing.*;

import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.*;

public class ParametreVisuelPlateau extends MenuBgCustom {
    JFrame frame;
    public static boolean hasToSave;
    boolean modifier;
    JColorChooser colorChooser;
    int actualCoolerTochange = 0;
    static Color[] newColors = new Color[7];

    JLabel error = new JLabel("Veuillez choisir des couleurs plus différentes !",JLabel.CENTER);

    public ParametreVisuelPlateau(JFrame frame1) {
        super(frame1);
        frame = frame1;
    }

    public void setParameterVisuel() {
        System.arraycopy(Couleur.actualCouleurs, 0, newColors, 0, 7);

        setLayout(null);
        JPanel container = new JPanel();
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100,
                Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        add(container, POPUP_LAYER);
        error.setForeground(Color.RED);
        error.setFont(new Font("Tahoma", Font.BOLD, 17));

        BasePreview base1 = new BasePreview(newColors[5],
                new Point(Launcher.width * 11 / 40 - 80, Launcher.height / 2 - 70));
        BasePreview base2 = new BasePreview(newColors[6],
                new Point(Launcher.width * 29 / 40 - 80, Launcher.height / 2 - 70));

        // On ceer les jComponant pour pouvoir selectionner et changer leur couleur
        RoundPreview perle1 = new RoundPreview(newColors[0],
                new Rectangle(Launcher.width * 106 / 400,
                        Launcher.height * 75 / 400 + Launcher.height / 20, 24, 24),
                1);
        RoundPreview perle2 = new RoundPreview(newColors[1],
                new Rectangle(Launcher.width * 106 / 400,
                        Launcher.height * 91 / 400 + Launcher.height / 20, 24, 24),
                2);
        RoundPreview perle3 = new RoundPreview(newColors[2],
                new Rectangle(Launcher.width * 106 / 400,
                        Launcher.height * 108 / 400 + Launcher.height / 20, 24, 24),
                3);
        RoundPreview perle4 = new RoundPreview(newColors[3],
                new Rectangle(Launcher.width * 286 / 400,
                        Launcher.height * 75 / 400 + Launcher.height / 20, 24, 24),
                4);
        RoundPreview perle5 = new RoundPreview(newColors[4],
                new Rectangle(Launcher.width * 286 / 400,
                        Launcher.height * 91 / 400 + Launcher.height / 20, 24, 24),
                5);
        RoundPreview pionJ1 = new RoundPreview(newColors[5],
                new Rectangle(Launcher.width * 109 / 400 - Launcher.height / 20,
                        Launcher.height * 9 / 40 + Launcher.height / 20, Launcher.height / 10, Launcher.height / 10),
                6);
        RoundPreview pionJ2 = new RoundPreview(newColors[6],
                new Rectangle(Launcher.width * 289 / 400 - Launcher.height / 20,
                        Launcher.height * 9 / 40 + Launcher.height / 20, Launcher.height / 10, Launcher.height / 10),
                7);
        RoundPreview[] rounds = new RoundPreview[] { perle1, perle2, perle3, perle4, perle5, pionJ1, pionJ2 };

        JButton retour = new ButtonMenu("Retour");
        JButton appliquer = new ButtonMenu("Appliquer");
        JButton save = new ButtonMenu("Sauvegarder");
        colorChooser = new JColorChooser(newColors[0]); // default color is black
        colorChooser.setDragEnabled(false);
        colorChooser.setBounds(Launcher.width / 2 - Launcher.width / 8, Launcher.height * 14 / 100, Launcher.width / 4,
                300);
        appliquer.setBounds(Launcher.width * 68 / 100, Launcher.height * 66 / 100, 70 * Launcher.width / 1000,
                61 * Launcher.height / 1000);
        retour.setBounds(Launcher.width * 25 / 100, Launcher.height * 66 / 100, 70 * Launcher.width / 1000,
                61 * Launcher.height / 1000);
        save.setBounds(Launcher.width * 45 / 100, Launcher.height * 66 / 100, 100 * Launcher.width / 1000,
                61 * Launcher.height / 1000);

        error.setBounds(Launcher.width / 2-Launcher.width*2/10, Launcher.height / 2, Launcher.width * 4 / 10, 75);
        colorChooser.getSelectionModel().addChangeListener(e -> {
            if (actualCoolerTochange != 0) {
                if (verifColor(colorChooser.getColor())) {
                    newColors[actualCoolerTochange - 1] = colorChooser.getColor();
                    rounds[actualCoolerTochange - 1].changeCouleur(colorChooser.getColor());
                    if (actualCoolerTochange == 6) {
                        base1.changeCouleur(colorChooser.getColor());
                    } else if (actualCoolerTochange == 7) {
                        base2.changeCouleur(colorChooser.getColor());
                    }
                    appliquer.setEnabled(true);
                    save.setEnabled(true);
                }
            }
        });

        retour.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            Parametre parametre = new Parametre(frame);
            frame.setContentPane(parametre);
            frame.revalidate();
        });

        save.setEnabled(false);
        save.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            hasToSave = true;
            changeColors(newColors);
        });

        JButton reset = new ButtonMenu("Reset");
        reset.addActionListener(event -> {
            setToDefault(rounds);
            save.doClick();
        });

        add(colorChooser, DRAG_LAYER);
        add(appliquer, DRAG_LAYER);
        add(reset, DRAG_LAYER);
        add(save, DRAG_LAYER);
        add(retour, DRAG_LAYER);

        pionJ1.addMouseListener(listener(pionJ1, rounds));
        perle1.addMouseListener(listener(perle1, rounds));
        perle2.addMouseListener(listener(perle2, rounds));
        perle3.addMouseListener(listener(perle3, rounds));
        pionJ2.addMouseListener(listener(pionJ2, rounds));
        perle4.addMouseListener(listener(perle4, rounds));
        perle5.addMouseListener(listener(perle5, rounds));

        base1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (actualCoolerTochange != 0) {
                    rounds[actualCoolerTochange - 1].setSelected();
                }
                actualCoolerTochange = 6;
            }
        });

        base2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (actualCoolerTochange != 0) {
                    rounds[actualCoolerTochange - 1].setSelected();
                }
                actualCoolerTochange = 7;
            }
        });

        add(perle1, DRAG_LAYER);
        add(perle2, DRAG_LAYER);
        add(perle3, DRAG_LAYER);
        add(pionJ1, DRAG_LAYER);
        add(perle4, DRAG_LAYER);
        add(perle5, DRAG_LAYER);
        add(pionJ2, DRAG_LAYER);
        add(base2, DRAG_LAYER);
        add(base1, DRAG_LAYER);
    }

    MouseAdapter listener(RoundPreview roundPreview, RoundPreview[] tous) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                roundPreview.setSelected();
                if (actualCoolerTochange != 0) {
                    tous[actualCoolerTochange - 1].setSelected();
                }
                actualCoolerTochange = roundPreview.ind;
            }
        };
    }

    private void changeColors(Color[] newColors) {
        modifier = true;
        System.arraycopy(newColors, 0, Couleur.actualCouleurs, 0, 7);
    }

    private static String tabToString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            res.append(Integer.toHexString(Couleur.actualCouleurs[i].getRGB())).append("/");
        }
        return res.toString();
    }

    public static void save() {
        try {
            if (hasToSave) {
                FileWriter myWriter = new FileWriter("src/resources/.parameters");
                myWriter.write(tabToString());
                myWriter.close();
            }
        } catch (IOException ignore) {
        }
    }

    public static void readParametres() {
        try {
            File file = new File("src/resources/.parameters");
            BufferedReader obj = new BufferedReader(new FileReader(file));
            String col = obj.readLine();
            if (!col.equals("")) {
                for (int i = 0; i < 7; i++) {
                    StringBuilder tmp = new StringBuilder();
                    for (int j = 0; j < 8; j++) {
                        tmp.append(col.charAt(i * 9 + j));
                    }
                    String sa = tmp.charAt(0) + String.valueOf(tmp.charAt(1));
                    String sr = tmp.charAt(2) + String.valueOf(tmp.charAt(3));
                    String sg = tmp.charAt(4) + String.valueOf(tmp.charAt(5));
                    String sb = tmp.charAt(6) + String.valueOf(tmp.charAt(7));
                    int a = Integer.parseInt(sa, 16);
                    int r = Integer.parseInt(sr, 16);
                    int g = Integer.parseInt(sg, 16);
                    int b = Integer.parseInt(sb, 16);
                    Couleur.actualCouleurs[i] = new Color(r, g, b, a);
                }
            }
        } catch (IOException ignored) {
        }
    }

    // TODO revoir ca
    void setToDefault(RoundPreview[] rounds) {
        Color[] originalCouleurs = new Color[] { new Color(243, 0, 36), Color.green,
                new Color(254, 163, 71), Color.pink, Color.blue,
                new Color(10, 60, 155),
                new Color(150, 4, 4) };
        changeColors(originalCouleurs);
        for (int i = 0; i < newColors.length; i++) {
            rounds[i].changeCouleur(originalCouleurs[i]);
        }
    }

    public boolean canUseThisColor(Color color) {
        for (Color c : newColors) {
            int diffR = c.getRed() - color.getRed();
            int diffG = c.getGreen() - color.getGreen();
            int diffB = c.getBlue() - color.getBlue();
            if (diffR < 30 && diffR > -30) {
                if (diffG < 30 && diffG > -30) {
                    if (diffB < 30 && diffB > -30) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    boolean verifColor(Color color) {
        if (error.getParent() == this) {
            remove(error);
        }
        if (!canUseThisColor(color)) {
            if (error.getParent() != this) {
                add(error, DRAG_LAYER);
            }
            return false;
        } else {
            return true;
        }
    }
}

class RoundPreview extends JComponent {
    public Color couleur;
    public Rectangle rectangle;
    public int ind;
    boolean selected = false;

    RoundPreview(Color c, Rectangle r, int i) {
        couleur = c;
        rectangle = r;
        setBounds(rectangle);
        ind = i;
        setSize(rectangle.width, rectangle.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(couleur);
        Shape round = new Ellipse2D.Double(0, 0, rectangle.height, rectangle.width);
        g2d.draw(round);
        g2d.fill(round);
        if (selected) {
            g2d.setColor(Color.white);
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.draw(round);
    }

    public void changeCouleur(Color color) {
        couleur = color;
        repaint();
    }

    public void setSelected() {
        selected = !selected;
        repaint();
    }
}

class BasePreview extends JComponent {
    public Color couleur;
    public Rectangle rectangle;
    public RoundPreview pion;

    BasePreview(Color c, Point p) {
        couleur = c;
        rectangle = new Rectangle(p, new Dimension(160, 140));
        setBounds(rectangle);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("src/resources/tuile_normal.png")), 0,
                    0, null);
            PionView.Disque shape = new PionView.Disque(new Ellipse2D.Double(63, 62, 35, 35), couleur);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(shape.getColor());
            g2d.draw(shape.getShape());
            g2d.fill(shape.getShape());
            g.drawImage(ImageIO.read(new File("src/resources/tuile_base.png")), 70, 68, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeCouleur(Color color) {
        couleur = color;
        repaint();
    }
}
