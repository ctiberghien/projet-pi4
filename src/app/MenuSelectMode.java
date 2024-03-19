package app;

import javax.swing.*;

import View.SoundPlayer;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import model.Deck;

import java.awt.*;

public class MenuSelectMode extends MenuBgCustom {
    JButton jouer = new ButtonMenu("2 Joueur");
    JButton jouersolo = new ButtonMenu("1 Joueur");
    JButton retour = new ButtonMenu("Retour");
    JButton creation = new ButtonMenu("Creation");
    JButton classic = new ButtonMenu("Classic");
    JButton online = new ButtonMenu("Online");

    public MenuSelectMode(JFrame frame) {
        super(frame);
        JPanel containerButton = new JPanel();
        containerButton.setBounds(Launcher.width * 20 / 100, Launcher.height * 70 / 100, Launcher.width * 60 / 100,
                Launcher.height * 20 / 100);
        containerButton.setBackground(new Color(30, 30, 30,240));
        containerButton
                .setLayout(new FlowLayout(FlowLayout.CENTER, Launcher.width * 3 / 100, Launcher.height * 6 / 100));

        jouer.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            MenuInter m = new MenuInter(frame, false);
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
        jouersolo.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            MenuInter m = new MenuInter(frame, true);
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
        creation.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            OptionGame optionGame = new OptionGame(frame);
            frame.setContentPane(optionGame);

            frame.revalidate();
            frame.repaint();
        });
        classic.addActionListener(event -> {

            SoundPlayer.joueSon(2, 0);
            CreationPlateau creationpanel = new CreationPlateau(frame, 11,
                    3, false, new Deck(),
                    true , false , 0, false , 0);
            frame.setContentPane(creationpanel);
            frame.revalidate();
            frame.repaint();
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

        retour.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            Menu m = new Menu(frame);
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

        online.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            ChoixMdj ch = new ChoixMdj(frame);
            frame.setContentPane(ch);
            frame.revalidate();
            frame.revalidate();
            frame.repaint();
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

        jouer.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        retour.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        creation.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        jouersolo.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        classic.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        online.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        containerButton.add(classic);
        containerButton.add(jouersolo);
        containerButton.add(jouer);
        containerButton.add(creation);
        containerButton.add(retour);
        containerButton.add(online);
        add(containerButton, DRAG_LAYER);
    }

   
}
