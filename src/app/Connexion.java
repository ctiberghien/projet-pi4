package app;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import View.SoundPlayer;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import View.Custom.TypeButtons.TexteBox;
import app.OnlinePanel.CharFilter;

import javax.swing.text.DocumentFilter;

public class Connexion extends MenuBgCustom {

    private TexteBox pseudo;
    private JLabel pseudoLabel = new JLabel("Entrez votre pseudo :");
    private JButton retour = new ButtonMenu("Retour");
    private JButton okBtn = new ButtonMenu("OK");

    public Connexion(JFrame frame, String currentName) {
        super(frame);
        String allowedChars = "abcdefghijklmnopqrstuvwqxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        OnlinePanel.CharFilter filter = new OnlinePanel.CharFilter(allowedChars);
        JPanel container = new JPanel();
        pseudo = new TexteBox(5,5, filter);
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100, Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        add(container, POPUP_LAYER);
        pseudo.setText(currentName);

        setLayout(null);
        //add components

        pseudo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!((e.getKeyChar()>=48 && e.getKeyChar()<=57) ||(e.getKeyChar()>=65 && e.getKeyChar()<=90) ||(e.getKeyChar()>=97 && e.getKeyChar()<=122)||e.getKeyChar()==8)) {
                    pseudo.setBackground(Color.RED);
                } else {
                    pseudo.setBackground(new Color(255, 228, 181));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
            
        });

        container.add(pseudo);
        container.add(pseudoLabel);
        container.add(retour);
        container.add(okBtn);

        retour.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            ChoixMdj m = new ChoixMdj(frame);
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

        okBtn.addActionListener(e -> {
            SoundPlayer.joueSon(2, 0);
            ChoixMdj.currentName=pseudo.getText();
            ChoixMdj m = new ChoixMdj(frame);
            frame.setContentPane(m);
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

        pseudoLabel.setFont(new Font("Tahoma", Font.PLAIN, 47));
        pseudoLabel.setForeground(Color.GRAY);

        pseudo.setBounds(Launcher.width * 20 / 100, Launcher.height *35 / 100, Launcher.width * 300 / 1000,Launcher.height * 50 / 1000);
        pseudoLabel.setBounds(Launcher.width * 20 / 100, Launcher.height * 26 / 100, 500 * Launcher.width / 1000,61 * Launcher.height / 1000);
        retour.setBounds(Launcher.width*20/100, Launcher.height*58/100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);
        okBtn.setBounds(Launcher.width*45/100, Launcher.height*58/100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);
    
        container.add(okBtn, DRAG_LAYER);
        container.add(retour,DRAG_LAYER);
        frame.setContentPane(this);
        frame.revalidate();
    }
    
}
