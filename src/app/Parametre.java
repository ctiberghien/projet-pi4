package app;

import javax.swing.*;

import View.ParametreVisuelPlateau;
import View.ParametresSon;
import View.SoundPlayer;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import java.awt.*;

public class Parametre extends MenuBgCustom {
    JFrame frame;
    boolean hasBot;

    public Parametre(JFrame frame1) {
        super(frame1);
        frame = frame1;
        hasBot = false;
        JPanel containerButton = new JPanel();
        containerButton.setBounds(Launcher.width * 20 / 100, Launcher.height * 70 / 100, Launcher.width * 60 / 100,
                Launcher.height * 20 / 100);
        containerButton.setBackground(new Color(30, 30, 30, 240));
        containerButton
                .setLayout(new FlowLayout(FlowLayout.CENTER, Launcher.width * 10 / 100, Launcher.height * 6 / 100));

        JButton retour = new ButtonMenu("Retour");
        retour.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            Menu menu = new Menu(frame);
            frame.setContentPane(menu);
            frame.revalidate();
            frame.repaint();
        });

        JButton visu = new ButtonMenu("Skin");
        visu.addActionListener(event -> {
            ParametreVisuelPlateau parametreFrame = new ParametreVisuelPlateau(frame);
            frame.setContentPane(parametreFrame);
            SoundPlayer.joueSon(2, 0);
            frame.revalidate();
            frame.repaint();
            parametreFrame.setParameterVisuel();
        });

        JButton sonore = new ButtonMenu("Son");
        sonore.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            ParametresSon param = new ParametresSon(frame, SoundPlayer.uiSound, SoundPlayer.sfxSound,
                    SoundPlayer.musicSound, SoundPlayer.masterSound);
            frame.setContentPane(param);
            frame.revalidate();
        });

        sonore.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        visu.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));
        retour.setPreferredSize(new Dimension(70 * Launcher.width / 1000, 61 * Launcher.height / 1000));

        containerButton.add(sonore);
        containerButton.add(visu);
        containerButton.add(retour);
        add(containerButton, DRAG_LAYER);
    }
}
