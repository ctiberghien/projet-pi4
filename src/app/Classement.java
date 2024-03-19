package app;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Network.Client;
import View.AttenteOnline;
import View.SoundPlayer;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;

public class Classement extends MenuBgCustom{

    private JButton retour=new ButtonMenu("Retour");
    private JLabel text = new JLabel();
    String test = "Dum;1!";

    public Classement( JFrame frame) {
        super(frame);
        
        setText();
        
        JPanel container = new JPanel();
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100,
                Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        add(container, POPUP_LAYER);

        container.add(text, MODAL_LAYER);

        setLayout(null);


        retour.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            ChoixMdj c =new ChoixMdj(frame);
            c.setVisible(true);
            frame.setContentPane(c);
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

        retour.setBounds(Launcher.width * 45 / 100, Launcher.height * 66 / 100, 100 * Launcher.width / 1000, 61 * Launcher.height / 1000);   
        add(retour, DRAG_LAYER);
        frame.setContentPane(this);
        frame.revalidate();
    }

    public LinkedList<Integer> parseWins() {
        LinkedList<Integer> res = new LinkedList<>();
        String cur = "";
        boolean fill = false;
        for (Character c : Client.classements.get(0).toCharArray()) {
            if (res.size()>=5) break;
            if (fill) {
                if (c=='!') {
                    fill=false;
                    res.add(Integer.parseInt(cur));
                    System.out.println(cur);
                    cur="";
                }
                else cur+=c;
            }
            if (c==';') {fill=true;}   
        }
        return res;
    }

    public LinkedList<String> parseNames() {
        LinkedList<String> res = new LinkedList<>();
        String cur = "";
        boolean fill = true;
        for (Character c : Client.classements.get(0).toCharArray()) {
            System.out.println(c);
            if (res.size()>=5) break;
            if (fill) {
                if (c==';') {
                    fill=false;
                    res.add(cur);
                    System.out.println(cur);
                    cur="";
                }
                else cur+=c;
            }
            if (c=='!') {fill=true;}   
        }
        return res;
    }


    public void setText() {
        String str =  "<html>";
        Font names = new Font("Tahoma", Font.BOLD, 25);

        if (!Client.classements.isEmpty()) {
            LinkedList<Integer> w= parseWins();
            LinkedList<String> n=parseNames();
            for (int i = 0; i < w.size(); i++) {
                str+="<center>"+n.get(i)+" : "+w.get(i)+" victoires <br>";
            }
        }
        str+="</html>";
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setBounds(Launcher.width * 15 / 100, Launcher.height * 20 / 100, 1000,
        50);
        text.setFont(names);
        text.setForeground(Color.WHITE);
        text.setText(str);
    }
}
