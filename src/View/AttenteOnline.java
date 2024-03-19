package View;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;
import javax.swing.event.*;

import Network.Client;
import Network.Server;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import app.ChoixMdj;
import app.Classement;
import app.Launcher;

public class AttenteOnline extends MenuBgCustom{

    private JLabel joueur1Label;
    private JLabel joueur2Label;
    public JButton okBtn;
    private JLabel listeLabel;
    private boolean okPourLancer;
    private JLabel errLabel;
    public Client c;
    public String nameJ1;
    public JButton classBtn;
    public JButton retour;

    public AttenteOnline(JFrame frame, String nameJ1, boolean isHosting) {
        super(frame);
        this.nameJ1=nameJ1;
        //construct components
        retour = new ButtonMenu("retour");
        joueur1Label = new JLabel(nameJ1);
        joueur2Label = new JLabel("en attente ...");
        okBtn = new ButtonMenu("OK");
        listeLabel = new JLabel("Liste des joueurs");
        okPourLancer=false;
        classBtn=new ButtonMenu("Classement");
        errLabel=new JLabel("pas assez de joueur");
        JPanel container = new JPanel();
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100,
                Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        add(container, POPUP_LAYER);

        //adjust size and set layout
        setPreferredSize(new Dimension(1266, 816));
        setLayout(null);

        //add components
        container.add(joueur1Label, MODAL_LAYER);
        container.add(joueur2Label, MODAL_LAYER);
        container.add(okBtn, MODAL_LAYER); 
        container.add(classBtn, MODAL_LAYER);
        container.add(retour, MODAL_LAYER);
        classBtn.setVisible(false);
        okBtn.setVisible(false);
        container.add(listeLabel, MODAL_LAYER);

        //set component bounds (only needed by Absolute Positioning)
        joueur1Label.setBounds(Launcher.width * 20 / 100, Launcher.height * 31 / 100, 1000,
        50);
        joueur2Label.setBounds(Launcher.width * 20 / 100, Launcher.height * 40 / 100, 1000,
        50);
        okBtn.setBounds(Launcher.width * 25 / 100, Launcher.height * 60 / 100, 100 * Launcher.width / 1000,
        61 * Launcher.height / 1000);
        listeLabel.setBounds(Launcher.width * 20 / 100, Launcher.height * 15 / 100, 1000,
        55);
        classBtn.setBounds(Launcher.width * 60 / 100, Launcher.height * 60 / 100, 100 * Launcher.width / 1000,
        61 * Launcher.height / 1000);

        joueur1Label.setFont(new Font("Tahoma", Font.PLAIN, 47));
        joueur2Label.setFont(new Font("Tahoma", Font.PLAIN, 47));
        listeLabel.setFont(new Font("Tahoma", Font.PLAIN, 47));
        joueur1Label.setForeground(Color.GRAY);
        joueur2Label.setForeground(Color.GRAY);
        listeLabel.setForeground(Color.GRAY);

        errLabel.setBounds(800, 200, 200, 100);
        container.add(errLabel, MODAL_LAYER);
        errLabel.setVisible(false);

        okBtn.addActionListener(e -> {
            System.out.println(c.getNbLabel());
            if (c.getNbLabel()==3) {
                c.getMessages().add("1");
                c.startGame();
                c.sendMessage(c.p.genereString(false, false));
                c.sendMessage(c.p.genereString(true, false));
                System.out.println("clicked");
            } else {
                errLabel.setVisible(true);
            }
        });

        retour.setBounds(Launcher.width*20/100, Launcher.height*58/100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);

        retour.addActionListener((event) -> {
            if (c.getSocket()!=null) {
                try {
                    c.socket.close();
                } catch (IOException e1) {}
                c.receiveMessages(false);
            }
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
    }

    

    public JLabel getJoueur2Label() {
        return joueur2Label;
    }

    public JLabel getJoueur1Label() {
        return joueur1Label;
    }

    public void setJoueur2Label(JLabel j) {
       this.joueur2Label=j; 
    }

    public void setJoueur1Label(JLabel joueur1Label) {
        this.joueur1Label = joueur1Label;
    }

    public void setC(Client c) {
        this.c = c;
    }
}
