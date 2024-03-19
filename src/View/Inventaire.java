package View;

import app.*;
import model.Joueur;
import model.Objets.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.border.*;

public class Inventaire extends JPanel {

    public JPanel[] slot;
    JButton utiliser;
    JPanel slots;
    JTextArea description;

    public Inventaire() {
        Border sideborder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
        setBorder(sideborder);
        setBackground(Color.BLACK);
        setOpaque(true);
        setPreferredSize(new Dimension(Launcher.width / 2, Launcher.height / 2));
        setLayout(null);
        slots = new JPanel();
        slots.setBackground(Color.GRAY);
        slots.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        slots.setBounds(0, this.getPreferredSize().height / 10, this.getPreferredSize().width * 35 / 50,
                this.getPreferredSize().height * 9 / 10);
        File folder = new File("src/model/Objets");
        File[] files = folder.listFiles();
        slot = files != null ? new JPanel[files.length] : new JPanel[0];
        initialiserSlot();

        utiliser = new JButton("utiliser");
        utiliser.setEnabled(false);
        utiliser.setBounds(getPreferredSize().width * 39 / 50, getPreferredSize().height * 10 / 100,
                getPreferredSize().width * 15 / 100, 30);

        description = new JTextArea();
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBounds(getPreferredSize().width * 36 / 50, getPreferredSize().height * 20 / 100,
                getPreferredSize().width * 26 / 100, getPreferredSize().height * 75 / 100);
        description.setVisible(true);

        this.add(description);
        this.add(utiliser);
        this.add(slots);
    }

    private static int witdhSlot;
    private static int heightSlot;

    public void initialiserSlot() {
        witdhSlot = (this.getPreferredSize().width * 35 / 50) / 4;
        heightSlot = (this.getPreferredSize().height * 9 / 10) / 3;
        for (int i = 0; i < slot.length; i++) {
            slot[i] = new JPanel();
            slot[i].setPreferredSize(new Dimension(witdhSlot, heightSlot));
            slots.add(slot[i]);
        }
    }

    public void setInitInv(InGameView g, PlateauJeu pan) {
        JLabel f = new FreeMove().getObjetView(witdhSlot * 7 / 10, witdhSlot * 7 / 10, true);
        f.setBounds(0, 0, witdhSlot * 7 / 10, witdhSlot * 7 / 10);
        slot[0].add(f);
        slot[0].setBackground(Color.green);
        slot[0].setLayout(new FlowLayout(FlowLayout.CENTER, 10, slot[0].getPreferredSize().height/4));
        slot[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                utiliser.setEnabled(true);
                FreeMove f = new FreeMove();
                description.setText("Description : " + f.description);
                utiliser.addActionListener(event2 -> {
                    utiliser.removeActionListener(utiliser.getActionListeners()[0]);
                    g.sac.doClick();
                    f.setPlateauObjet(pan, pan.p, g);
                    utiliser.setEnabled(false);
                    description.setText("");
                });
                revalidate();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                slot[0].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 150), 5, false));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                slot[0].setBorder(null);
            }
        });

        JLabel s = new Switch().getObjetView(witdhSlot * 7 / 10, witdhSlot * 7 / 10, true);
        s.setBounds(0, 0, witdhSlot * 7 / 10, witdhSlot * 7 / 10);
        slot[1].add(s);
        slot[1].setBackground(Color.blue);
        slot[1].setLayout(new FlowLayout(FlowLayout.CENTER, 10, slot[0].getPreferredSize().height/4));
        slot[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                utiliser.setEnabled(true);
                Switch S = new Switch();
                description.setText("Description : " + S.description);
                utiliser.addActionListener(event2 -> {
                    utiliser.removeActionListener(utiliser.getActionListeners()[0]);
                    g.sac.doClick();
                    S.setPlateauObjet(pan, pan.p, g);
                    utiliser.setEnabled(false);
                    description.setText("");
                });
                repaint();
                revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                slot[1].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 150), 5, false));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                slot[1].setBorder(null);
            }

        });

        JLabel m = new MelangeCouleur().getObjetView(witdhSlot * 7 / 10, witdhSlot * 7 / 10, true);
        m.setBounds(0, 0, witdhSlot * 7 / 10, witdhSlot * 7 / 10);
        slot[2].add(m);
        slot[2].setBackground(Color.cyan);
        slot[2].setLayout(new FlowLayout(FlowLayout.CENTER, 10, slot[0].getPreferredSize().height/4));
        slot[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                utiliser.setEnabled(true);
                MelangeCouleur t = new MelangeCouleur();
                description.setText("Description : " + t.description);
                utiliser.removeActionListener(null);
                utiliser.addActionListener(event2 -> {
                    utiliser.removeActionListener(utiliser.getActionListeners()[0]);
                    g.sac.doClick();
                    t.activate(pan);
                    utiliser.setEnabled(false);
                    description.setText("");
                });
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                slot[2].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 150), 5, false));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                slot[2].setBorder(null);
            }
        });

        JLabel r = new Renvoi().getObjetView(witdhSlot * 7 / 10, witdhSlot * 7 / 10, true);
        r.setBounds(0, 0, witdhSlot * 7 / 10, witdhSlot * 7 / 10);
        slot[3].add(r);
        slot[3].setBackground(Color.red);
        slot[3].setLayout(new FlowLayout(FlowLayout.CENTER, 10, slot[3].getPreferredSize().height/4));
        slot[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                utiliser.setEnabled(true);
                Renvoi R = new Renvoi();
                description.setText("Description : " + R.description);
                utiliser.addActionListener(event2 -> {
                    utiliser.removeActionListener(utiliser.getActionListeners()[0]);
                    g.sac.doClick();
                    R.setPlateauObjet(pan, pan.p, g);
                    utiliser.setEnabled(false);
                    description.setText("");
                });
                revalidate();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                slot[3].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 150), 5, false));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                slot[3].setBorder(null);
            }
        });

        JLabel mu = new Mur().getObjetView(witdhSlot * 7 / 10, witdhSlot * 7 / 10, true);
        mu.setBounds(0, 0, witdhSlot * 7 / 10, witdhSlot * 7 / 10);
        slot[4].add(mu);
        slot[4].setBackground(Color.orange);
        slot[4].setLayout(new FlowLayout(FlowLayout.CENTER, 10, slot[0].getPreferredSize().height/4));
        slot[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                utiliser.setEnabled(true);
                Mur m = new Mur();
                description.setText("Description : " + m.description);
                utiliser.addActionListener(event2 -> {
                    utiliser.removeActionListener(utiliser.getActionListeners()[0]);
                    g.sac.doClick();
                    m.setPlateauObjet(pan, pan.p, g);
                    utiliser.setEnabled(false);
                    description.setText("");
                });
                repaint();
                revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                slot[4].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 150), 5, false));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                slot[4].setBorder(null);
            }
        });

        JLabel v = new Ventilateur().getObjetView(witdhSlot * 7 / 10, witdhSlot * 7 / 10, true);
        v.setBounds(0, 0, witdhSlot * 7 / 10, witdhSlot * 7 / 10);
        slot[5].add(v);
        slot[5].setBackground(Color.pink);
        slot[5].setLayout(new FlowLayout(FlowLayout.CENTER, 10, slot[0].getPreferredSize().height/4));
        slot[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                utiliser.setEnabled(true);
                Ventilateur V = new Ventilateur();
                description.setText("Description : " + V.description);
                utiliser.addActionListener(event2 -> {
                    utiliser.removeActionListener(utiliser.getActionListeners()[0]);
                    g.sac.doClick();
                    V.setPlateauObjet(pan, pan.p, g);
                    utiliser.setEnabled(false);
                    description.setText("");
                });
                repaint();
                revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                slot[5].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 150), 5, false));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                slot[5].setBorder(null);
            }
        });
    }

    public boolean setInventaire(Joueur j) {
        boolean bool = false;
        if (j.sacCountItem(new FreeMove()) != 0) {
            slot[0].setVisible(true);
            if (slot[0].getComponentCount() > 1) {
                slot[0].remove(1);
            }
            JLabel tmp = new JLabel("" + j.sacCountItem(new FreeMove()));
            tmp.setFont(new Font("Tahoma", Font.BOLD, 20));
            tmp.setBounds(witdhSlot * 4 / 5, 0, 0, 10);
            slot[0].add(tmp);

            bool = true;
        } else {
            slot[0].setVisible(false);
        }
        if (j.sacCountItem(new Switch()) != 0) {
            slot[1].setVisible(true);
            if (slot[1].getComponentCount() > 1) {
                slot[1].remove(1);
            }
            JLabel tmp = new JLabel("" + j.sacCountItem(new Switch()));
            tmp.setFont(new Font("Tahoma", Font.BOLD, 20));
            tmp.setBounds(witdhSlot * 4 / 5, 0, 50, 50);
            slot[1].add(tmp);
            bool = true;
        } else {
            slot[1].setVisible(false);
        }
        if (j.sacCountItem(new MelangeCouleur()) != 0) {
            slot[2].setVisible(true);
            if (slot[2].getComponentCount() > 1) {
                slot[2].remove(1);
            }
            JLabel tmp = new JLabel("" + j.sacCountItem(new MelangeCouleur()));
            tmp.setFont(new Font("Tahoma", Font.BOLD, 20));
            tmp.setBounds(witdhSlot * 4 / 5, 0, 50, 50);
            slot[2].add(tmp);
            bool = true;
        } else {
            slot[2].setVisible(false);
        }
        if (j.sacCountItem(new Renvoi()) != 0) {
            slot[3].setVisible(true);
            if (slot[3].getComponentCount() > 1) {
                slot[3].remove(1);
            }
            JLabel tmp = new JLabel("" + j.sacCountItem(new Renvoi()));
            tmp.setFont(new Font("Tahoma", Font.BOLD, 20));
            tmp.setBounds(witdhSlot * 4 / 5, 0, 50, 50);
            slot[3].add(tmp);
            bool = true;
        } else {
            slot[3].setVisible(false);
        }

        if (j.sacCountItem(new Mur()) != 0) {
            slot[4].setVisible(true);
            if (slot[4].getComponentCount() > 1) {
                slot[4].remove(1);
            }
            JLabel tmp = new JLabel("" + j.sacCountItem(new Mur()));
            tmp.setFont(new Font("Tahoma", Font.BOLD, 20));
            tmp.setBounds(witdhSlot * 4 / 5, 0, 50, 50);
            slot[4].add(tmp);
            bool = true;
        } else {
            slot[4].setVisible(false);
        }
        if (j.sacCountItem(new Ventilateur()) != 0) {
            slot[5].setVisible(true);
            if (slot[5].getComponentCount() > 1) {
                slot[5].remove(1);
            }
            JLabel tmp = new JLabel("" + j.sacCountItem(new Ventilateur()));
            tmp.setFont(new Font("Tahoma", Font.BOLD, 20));
            tmp.setBounds(witdhSlot * 4 / 5, 0, 50, 50);
            slot[5].add(tmp);
            bool = true;
        } else {
            slot[5].setVisible(false);
        }
        revalidate();
        repaint();
        return bool;
    }
}
