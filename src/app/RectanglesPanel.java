package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.*;

import Network.Client;
import View.AttenteOnline;
import View.Custom.TypeButtons.ButtonServer;

public class RectanglesPanel extends JLayeredPane {
    
    private static final int RECTANGLE_COUNT = 1;
    private static final int RECTANGLE_SIZE = 100;

    
    public RectanglesPanel(int width, JFrame frame, String currentName) {
        setLayout(null);
        int preferredHeight = RECTANGLE_COUNT * RECTANGLE_SIZE + getInsets().top + getInsets().bottom;
        System.out.println(preferredHeight);
        setPreferredSize(new Dimension(width, preferredHeight));
        for (int i = 0; i < RECTANGLE_COUNT; i++) {
            JButton rectangle = new ButtonServer("Serveur Local");
            JLabel trophText=new JLabel("🏆");
            JButton classementBtn = new ButtonServer("");
            rectangle.setBounds(0, i*RECTANGLE_SIZE, width-width/10, RECTANGLE_SIZE);
            classementBtn.setBounds(width-width/10, i*RECTANGLE_SIZE, width/10, RECTANGLE_SIZE);
            trophText.setBounds(classementBtn.getX()+classementBtn.getWidth()/2, classementBtn.getY()+classementBtn.getY()/100, 20, 20);
            trophText.setFont(new Font("", Font.BOLD, Launcher.width/100));
            add(rectangle);
            add(classementBtn);
            classementBtn.add(trophText);
            rectangle.addActionListener((event) -> {
                AttenteOnline a = currentName.equals("") ? new AttenteOnline(frame, "WoofWoof", false) : new AttenteOnline(frame, currentName, false);
                Client c = new Client(frame,a, -1);
                a.c=c;
                c.connect("127.0.0.1", Integer.parseInt("4242"));
                frame.setContentPane(a);
                frame.revalidate();
            });
            classementBtn.addActionListener((event) -> {
                Classement classm = new Classement(frame);
                frame.setContentPane(classm);
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
        }
    }
}