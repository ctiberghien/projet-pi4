package app;
import javax.swing.*;

import Network.Client;
import Network.Server;
import View.AttenteOnline;
import View.SoundPlayer;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import View.Custom.TypeButtons.ButtonServer;
import model.*;
import model.TypeOfPlateau.Plateau1;
import model.TypeOfPlateau.Plateau2;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;


public class ChoixMdj extends MenuBgCustom {

    private JButton onlineCBtn=new ButtonMenu("Online (Join)");
    private JButton onlineHBtn = new ButtonMenu("Online (Host)");
    private JButton retour = new ButtonMenu("Retour");
    public JLabel nom = new JLabel("Pseudo : ", JLabel.CENTER);
    private JButton btnConnexion = new ButtonMenu("Connexion");
    public static String currentName="";

    

    public ChoixMdj(JFrame frame) {
        super(frame);
        JPanel container = new JPanel();
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100,
                Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        add(container, POPUP_LAYER);

        setLayout(null);

        nom.setFont(new Font("Tahoma", Font.BOLD, 20));
        //add components

        RectanglesPanel rp = new RectanglesPanel(Launcher.width * 363 / 1000, frame, currentName);

        JScrollPane scrollPane = new JScrollPane(rp);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setBounds(Launcher.width * 31 / 100, Launcher.height * 25 / 100, Launcher.width * 37 / 100,
                Launcher.height * 30 / 100);

        add(scrollPane, DRAG_LAYER);

        retour.addActionListener((event) -> {
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

        onlineCBtn.addActionListener(e -> {
            OnlinePanel o = new OnlinePanel(false, frame, currentName);
            o.setVisible(true);
            frame.setContentPane(o);
            frame.revalidate();
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                frame.revalidate();
                frame.repaint();
            });
        });

        onlineHBtn.addActionListener(e -> {
            OnlinePanel o = new OnlinePanel(true, frame, currentName);
            o.setVisible(true);
            frame.setContentPane(o);
            frame.revalidate();
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                frame.revalidate();
                frame.repaint();
            });
        });

        System.out.println(currentName);

        if (currentName.equals("")) {
            btnConnexion.addActionListener(e -> {
                Connexion o = new Connexion(frame, currentName);
                o.setVisible(true);
                frame.setContentPane(o);
                frame.revalidate();
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    frame.revalidate();
                    frame.repaint();
                });
            });
            btnConnexion.setBounds(Launcher.width * 91 / 100, Launcher.width * 2 / 100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);
            add(btnConnexion, MODAL_LAYER);
        } else {
            nom.setText(nom.getText()+currentName);
            nom.setBounds(Launcher.width * 90 / 100, 0, Launcher.width * 7 / 100, Launcher.width * 7 / 100);
            add(nom, MODAL_LAYER);
        }


        retour.setBounds(Launcher.width * 68 / 100, Launcher.height * 66 / 100, 70 * Launcher.width / 1000,
                61 * Launcher.height / 1000);
        onlineCBtn.setBounds(Launcher.width * 25 / 100, Launcher.height * 66 / 100, 70 * Launcher.width / 1000,
                61 * Launcher.height / 1000);
        onlineHBtn.setBounds(Launcher.width * 45 / 100, Launcher.height * 66 / 100, 100 * Launcher.width / 1000,
                61 * Launcher.height / 1000);
        add(retour, DRAG_LAYER);
        add(onlineCBtn, DRAG_LAYER);
        add(onlineHBtn, DRAG_LAYER);
        frame.setContentPane(this);
        frame.revalidate();
    }
}
