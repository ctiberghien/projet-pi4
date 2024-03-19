package app;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import View.ParametresSon;
import View.PlateauJeu;
import View.SoundPlayer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.*;

public class Menu extends MenuBgCustom {

    boolean peutContinuer = false;

    public Menu(JFrame frame) {
        super(frame);
        // panel qui contient tout les boutons
        JPanel containerButton = new JPanel();
        containerButton.setBounds(Launcher.width * 20 / 100, Launcher.height * 70 / 100, Launcher.width * 60 / 100,
                Launcher.height * 20 / 100);
        containerButton.setBackground(new Color(30, 30, 30,240));
        containerButton
                .setLayout(new FlowLayout(FlowLayout.CENTER, Launcher.width * 3 / 100, Launcher.height * 6 / 100));

        JButton jouer = new ButtonMenu("Nouvelle Partie");
        JButton quitter = new ButtonMenu("Quitter");
        JButton continuer = new ButtonMenu("Continuer");
        JLabel son = new JLabel("🔉", JLabel.CENTER);
        JLabel param = new JLabel("⚙", JLabel.CENTER);
        jouer.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            MenuSelectMode m = new MenuSelectMode(frame);
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
        });
        quitter.addActionListener((event) -> {
            frame.dispose();
        });

        try {
            final FileInputStream fichier = new FileInputStream("src/resources/.sauvegarde.ser");
            ObjectInputStream ois = new ObjectInputStream(fichier);
            ois.close();
            peutContinuer = true;
        } catch (Exception ignored) {
        }

        continuer.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            try {
                final FileInputStream fichier = new FileInputStream("src/resources/.sauvegarde.ser");
                ObjectInputStream ois = new ObjectInputStream(fichier);
                PlateauJeu pan = (PlateauJeu) ois.readObject();
                InGameView game = new InGameView(frame, pan.model,
                        pan);
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
                peutContinuer = true;
            } catch (Exception e) {
                try {
                    Path fileToDelete = Paths.get("src/resources/.sauvegarde.ser");
                    Files.delete(fileToDelete);
                } catch (Exception ignored) {
                }
                Menu m = new Menu(frame);
                frame.setContentPane(m);
                frame.revalidate();
                frame.repaint();
            }
        });

        param.setFont(new Font("", Font.BOLD, 90));
        param.setBounds(Launcher.width * 92 / 100, 0, Launcher.width * 7 / 100, Launcher.width * 7 / 100);

        param.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                param.setFont(new Font("", Font.BOLD, 100));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                param.setFont(new Font("", Font.BOLD, 90));
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.joueSon(2, 0);
                Parametre parametre = new Parametre(frame);
                frame.setContentPane(parametre);
                frame.revalidate();
            }

        });

        son.setFont(new Font("", Font.BOLD, 90));
        son.setBounds(Launcher.width * 84 / 100, 0, Launcher.width * 7 / 100, Launcher.width * 7 / 100);

        son.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                son.setFont(new Font("", Font.BOLD, 100));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                son.setFont(new Font("", Font.BOLD, 90));
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.joueSon(2, 0);
                ParametresSon param = new ParametresSon(frame, SoundPlayer.uiSound, SoundPlayer.sfxSound,
                        SoundPlayer.musicSound, SoundPlayer.masterSound);
                frame.setContentPane(param);
                frame.revalidate();
            }

        });

        jouer.setPreferredSize(new Dimension(100 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        quitter.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        continuer.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));

        if (peutContinuer)
            containerButton.add(continuer);
        containerButton.add(jouer);
            containerButton.add(quitter);
        add(containerButton, DRAG_LAYER);
     //   add(son, DRAG_LAYER);
        add(param, DRAG_LAYER);
    }
}
