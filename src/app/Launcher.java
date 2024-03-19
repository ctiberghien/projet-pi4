package app;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import View.ParametreVisuelPlateau;
import View.SizeHexagone;
import View.SoundPlayer;
public class Launcher extends JFrame {

    public static int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    public static int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height; // (int)
                                                                                                                  // Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public Launcher() {
        this.setResizable(true);
        this.setTitle("Niwa");
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        SoundPlayer.joueSon(0, 0);
        SoundPlayer.loop();
        this.setContentPane(new Menu(this));
        SizeHexagone.setSize(0.8);

        ParametreVisuelPlateau.readParametres();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ParametreVisuelPlateau.save();
                System.exit(0);
                super.windowClosed(e);
            }
        });

        this.setIconImage(new ImageIcon("").getImage());
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new Launcher();
    }

}