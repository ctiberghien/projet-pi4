package app;

import javax.swing.*;

import View.Custom.ScrollBarCustom.ScrollBarUI;
import View.Custom.TypeButtons.ButtonIngame3;
import View.PlateauCreation;
import View.PlateauView;
import View.PlateauUneTuileView;
import model.*;

import java.awt.*;

public class CreationPlateau extends JLayeredPane {
    JFrame frame;
    private final JLabel pieces;
    private final JButton start;
    public JButton turn;
    public PlateauUneTuileView nextTuileView;
    JLabel textinfo;
    JPanel infoArea;
    public Joueur j1;
    public Joueur j2;
    boolean hasBot;

    public CreationPlateau(JFrame frame, int nbrPiece, int nbrPions, boolean hasBot,Deck deck,
                           boolean modeClassic, boolean hasTP , int nbTP, boolean hasObj, int nbObj) {
        this.hasBot = hasBot;
        this.frame = frame;
        Plateau p1 = new Plateau(nbTP);
        p1.isTPActive = hasTP && !modeClassic;
        p1.objetActif = !modeClassic;
        setLayout(null);
        p1.addTuile(new Coordonnee(0, 0), deck.piocher());

        p1.objetActif = hasObj;
        p1.objTurn = nbObj;

        PlateauView pan = new PlateauCreation(p1, null, nbrPiece, nbrPions, this, deck,modeClassic, hasTP , nbTP);
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
        this.add(jscp);
        pan.setPlateauView();
        JPanel p = new JPanel() {
            private final Image img1 = Toolkit.getDefaultToolkit().getImage("src/resources/GUI/blacklayout.png");
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
            private final Image img1 = Toolkit.getDefaultToolkit().getImage("src/resources/GUI/bgplateau.jpg");
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

        bgPlateauPanel.setOpaque(false);
        this.add(bgPlateauPanel, DEFAULT_LAYER);

        if (hasBot) {
            j1 = new AIJoueur("Karatsuba", Couleur.BASE2.getColor(), 2);
        } else {
            j1 = new Joueur("Karatsuba", Couleur.BASE2.getColor(), 2);
        }
        j2 = new Joueur("Karatsuba", Couleur.BASE1.getColor(), 1);

        start = new ButtonIngame3("start ");
        start.addActionListener(event -> {
            p1.removeEmptyCase();
            if (hasBot) {
                ((AIJoueur) j1).setPlat(p1);
            }
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
        });
        JButton retour = new ButtonIngame3("retour ");
        retour.addActionListener(event -> {
            OptionGame optionGame = new OptionGame(frame);
            frame.setContentPane(optionGame);

            frame.revalidate();
            frame.repaint();
        });
        retour.setBounds(Launcher.width * 84 / 100, Launcher.height * 54 / 100,
                Launcher.width * 10 / 100,
                Launcher.height * 50 / 1000);
        start.setBounds(Launcher.width * 84 / 100, Launcher.height * 13 / 100, Launcher.width * 10 / 100,
                Launcher.height * 50 / 1000);
        start.setEnabled(false);
        pieces = new JLabel();
        updatePieces(nbrPiece);
        pieces.setFont(new Font("Tahoma", Font.PLAIN, 22));
        // pieces.setBounds(Launcher.width * 84 / 100, Launcher.height * 33 / 100,
        // Launcher.width * 10 / 100,
        // Launcher.height * 50 / 1000);
        JButton quitter;
        quitter = new ButtonIngame3("quitter");
        quitter.setBounds(0, 0,
                Launcher.width * 7 / 100,
                Launcher.height * 35 / 1000);
        quitter.addActionListener(event -> {
            Menu m = new Menu(frame);
            frame.setContentPane(m);
            frame.revalidate();
            frame.repaint();
        });

        textinfo = new JLabel("");
        textinfo.setFont(new Font("Tahoma", Font.PLAIN, 22));
        infoArea = new JPanel();
        infoArea.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoArea.add(textinfo);
        infoArea.add(pieces);
        infoArea.setBackground(new Color(255, 228, 181));
        infoArea.setBounds(16 * Launcher.width / 100, 89 * Launcher.height / 100, 46 * Launcher.width / 100,
                7 * Launcher.height / 100);
        nextTuileView = new PlateauUneTuileView(new Plateau(), false);
        nextTuileView.changeTuileToView(deck.peek());
        nextTuileView.setBounds(Launcher.width * 84 / 100, Launcher.height * 25 / 100, 2 * 80, 80 * 2 - 40 / 2);
        turn = new ButtonIngame3("Tourner >");
        turn.addActionListener(event -> {

            nextTuileView.removeAll();
            deck.peek().rotateRight();
            nextTuileView.p.resetThisTuile(deck.peek(), new Coordonnee(0, 0));
            nextTuileView.setPlateauView();
            repaint();
        });
        turn.setBounds(Launcher.width * 84 / 100, Launcher.height * 45 / 100,
                Launcher.width * 10 / 100,
                Launcher.height * 50 / 1000);

        this.add(nextTuileView, DRAG_LAYER);

        this.add(infoArea, DRAG_LAYER);

        this.add(quitter, DRAG_LAYER);
        this.add(start, DRAG_LAYER);
        this.add(retour, DRAG_LAYER);
        this.add(turn, DRAG_LAYER);
    }

    public void updatePieces(int nbrPiece) {
        pieces.setText("Creer votre plateau , nombre de pieces restant a placer : " + nbrPiece);
        frame.revalidate();
        frame.repaint();
    }

    public void updateInfoText(String text) {
        textinfo.setText(text);
        frame.revalidate();
        frame.repaint();
    }

    public void removeText() {
        infoArea.remove(pieces);
    }

    public void toAccept() {
        start.setEnabled(true);
    }

    public Model createModel(Plateau p1) {
        p1.removeEmptyCase();
        if (hasBot) {
            ((AIJoueur) j1).setPlat(p1);
        }
        return new Model(p1, j1, j2);
    }

    public InGameView createInGameView(Model model) {
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
        return game;
    }

}
