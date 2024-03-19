package View.Custom;

import javax.swing.JFrame;
import javax.swing.*;

import View.Custom.TypeButtons.ButtonMenu;
import app.Launcher;

import java.awt.*;

public class MenuBgCustom extends JLayeredPane {
    JButton jouer = new ButtonMenu("2 Joueur");
    JButton jouersolo = new ButtonMenu("1 Joueur");
    JButton retour = new ButtonMenu("Retour");
    JButton creation = new ButtonMenu("Creation");
    JButton classic = new ButtonMenu("Classic");
    JButton online = new ButtonMenu("Online");

    public MenuBgCustom(JFrame frame) {

        JLabel title = new JLabel("N I W A ", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 50));
        title.setForeground(Color.white);
        title.setBounds(Launcher.width * 40 / 100, Launcher.height / 10, Launcher.width * 2 / 10, Launcher.height / 10);
        add(title, MODAL_LAYER);

        String arg = "src/resources/GUI/cherry2.gif";
        ImageIcon icon = new ImageIcon(arg);
        Image tmp = icon.getImage();
        Image newimg = tmp.getScaledInstance(Launcher.width, Launcher.height,
                java.awt.Image.SCALE_DEFAULT);
        icon.setImage(newimg);
        JLabel cherry = new JLabel();
        cherry.setBounds(0, 0, Launcher.width, Launcher.height);
        cherry.setIcon(icon);

        add(cherry, MODAL_LAYER);

        JButton cherryON = new ButtonMenu("Activated");
        cherryON.addActionListener(event -> {
            if (cherryON.getText().equals("Activated")) {
                cherryON.setText("Disabled");
                remove(cherry);
                repaint();
            } else {
                add(cherry, MODAL_LAYER);
                cherryON.setText("Activated");
            }
        });
        cherryON.setBounds(10, 10, 20, 20);
        add(cherryON, DRAG_LAYER);

        JComponent stylemid = new JComponent() {
            private Image img1 = Toolkit.getDefaultToolkit().getImage("src/resources/GUI/menubg.png");
            private Image img = img1.getScaledInstance(Launcher.width * 4 / 10, Launcher.height * 4 / 10,
                    Image.SCALE_DEFAULT);

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        };
        stylemid.setBounds(Launcher.width * 29 / 100, Launcher.height * 26 / 100, Launcher.width * 4 / 10,
                Launcher.height * 4 / 10);

        stylemid.setOpaque(false);
        this.add(stylemid, MODAL_LAYER);

        JPanel bgcolor = new JPanel();
        bgcolor.setBackground(new Color(255, 150, 180, 200));
        bgcolor.setBounds(0, 0, Launcher.width, Launcher.height);
        add(bgcolor, DEFAULT_LAYER);

        JPanel line = new JPanel();
        line.setBackground(new Color(255, 240, 240, 200));
        line.setBounds(Launcher.width * 3 / 100, 0, Launcher.width * 5 / 100, Launcher.height);
        add(line, PALETTE_LAYER);

        JPanel line2 = new JPanel();
        line2.setBackground(new Color(255, 240, 240, 200));
        line2.setBounds(Launcher.width * 13 / 100, 0, Launcher.width * 3 / 100, Launcher.height);
        add(line2, PALETTE_LAYER);

        JPanel line3 = new JPanel();
        line3.setBackground(new Color(255, 240, 240, 200));
        line3.setBounds(Launcher.width * 21 / 100, 0, Launcher.width * 1 / 100, Launcher.height);
        add(line3, PALETTE_LAYER);

        JPanel line5 = new JPanel();
        line5.setBackground(new Color(255, 240, 240, 200));
        line5.setBounds(Launcher.width * 92 / 100, 0, Launcher.width * 5 / 100, Launcher.height);
        add(line5, PALETTE_LAYER);

        JPanel line6 = new JPanel();
        line6.setBackground(new Color(255, 240, 240, 200));
        line6.setBounds(Launcher.width * 84 / 100, 0, Launcher.width * 3 / 100, Launcher.height);
        add(line6, PALETTE_LAYER);

        JPanel line7 = new JPanel();
        line7.setBackground(new Color(255, 240, 240, 200));
        line7.setBounds(Launcher.width * 78 / 100, 0, Launcher.width * 1 / 100, Launcher.height);
        add(line7, PALETTE_LAYER);

    }
}
