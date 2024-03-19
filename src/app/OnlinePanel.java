package app;
import View.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;

import Network.Client;
import Network.Server;
import View.Custom.TypeButtons.*;
import View.Custom.TypeButtons.TexteBox;
import View.Custom.MenuBgCustom;

public class OnlinePanel extends MenuBgCustom {
    String allowedChars = ".0123456789";
    CharFilter filter = new CharFilter(allowedChars);
    String allowedCharsP = "0123456789";
    CharFilter filterP = new CharFilter(allowedCharsP);
    private TexteBox textIp = new TexteBox(5, 5, filter);
    private TexteBox textPort = new TexteBox(5, 5, filterP);
    private JLabel ipLabel;
    private JLabel portLabel;
    private JButton okBtn = new ButtonMenu("OK");
    private JLabel errMsgIp;
    private JButton retour = new ButtonMenu("Retour");
    private JButton btnConnexion = new ButtonMenu("Connexion");
    private JLabel nom = new JLabel("Pseudo : ", JLabel.CENTER);
    private String currentName;

    public OnlinePanel(boolean isHosting, JFrame frame, String name) {
        super(frame);
        currentName=name;
        JPanel container = new JPanel();
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100,
                Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        add(container, POPUP_LAYER);
        //construct components
        ipLabel = new JLabel("Ip");
        portLabel = new JLabel("Port");
        errMsgIp = new JLabel("erreur veuillez entrer une bonne ip/un bon port");

        //adjust size and set layout
        setPreferredSize(new Dimension(1266, 816));
        setLayout(null);

        textIp.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if ((e.getKeyChar()>57 || e.getKeyChar()<48) && e.getKeyChar()!=46 && e.getKeyChar()!=8) {
                    textIp.setBackground(Color.RED);
                } else {
                    textIp.setBackground(new Color(255, 228, 181));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
            
        });

        textPort.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if ((e.getKeyChar()>57 || e.getKeyChar()<48) && e.getKeyChar()!=8) {
                    textPort.setBackground(Color.RED);
                } else {
                    textPort.setBackground(new Color(255, 228, 181));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
            
        });

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

        nom.setFont(new Font("Tahoma", Font.BOLD, 20));

        ipLabel.setFont(new Font("Tahoma", Font.PLAIN, 47));
        ipLabel.setForeground(Color.GRAY);

        portLabel.setFont(new Font("Tahoma", Font.PLAIN, 47));
        portLabel.setForeground(Color.GRAY);

        //set component bounds (only needed by Absolute Positioning)frame.setContentPane(o);œ
        errMsgIp.setBounds(800, 200, 500, 100);
        add(errMsgIp);
        errMsgIp.setVisible(false);        
        container.add(textPort);
        container.add(portLabel);
        textPort.setBounds(Launcher.width * 20 / 100, Launcher.height *40 / 100, Launcher.width * 300 / 1000,
        Launcher.height * 50 / 1000);
            portLabel.setBounds(Launcher.width * 20 / 100, Launcher.height * 33 / 100, 70 * Launcher.width / 1000,
        61 * Launcher.height / 1000);

        System.out.println(isHosting);

        //add components
        if (!isHosting) {
            container.add(textIp);
            container.add(ipLabel);
            textIp.setBounds(Launcher.width * 20 / 100, Launcher.height * 26 / 100, Launcher.width * 300 / 1000,
            Launcher.height * 50 / 1000);
            ipLabel.setBounds(Launcher.width * 20 / 100, Launcher.height * 19 / 100, Launcher.width * 300 / 1000,
            Launcher.height * 50 / 1000);
        } else {
            textPort.setBounds(Launcher.width * 20 / 100, Launcher.height *35 / 100, Launcher.width * 300 / 1000,
        Launcher.height * 50 / 1000);
            portLabel.setBounds(Launcher.width * 20 / 100, Launcher.height * 28 / 100, 70 * Launcher.width / 1000,
        61 * Launcher.height / 1000);
        }

        retour.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            ChoixMdj m = new ChoixMdj(frame);
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
        
        okBtn.addActionListener(e -> {
            if (!isHosting) {
                if (testParseIp() && testParsePort()) {
                    if (currentName=="") {
                        currentName="WoofWoof";
                    }
                    errMsgIp.setVisible(false);
                    AttenteOnline a = new AttenteOnline(frame, currentName, false);
                    Client c = new Client(frame,a, 2);
                    c.connect(textIp.getText(), Integer.parseInt(textPort.getText()));
                    frame.setContentPane(a);
                    frame.revalidate();
                } else {
                    errMsgIp.setVisible(true);
                    System.out.println("err");
                }
            } else {
                if (testParsePort()) {
                    if (currentName=="") {
                        currentName="WoofWoof";
                    }
                    try {
                        Server s = new Server(Integer.parseInt(textPort.getText()));
                        s.start();
                    } catch (Exception e1) {}
                    AttenteOnline a = new AttenteOnline(frame, currentName, true);
                    Client joueurQuiHost = new Client(frame,a, 1);
                    a.setC(joueurQuiHost);
                    joueurQuiHost.connect("127.0.0.1", Integer.parseInt(textPort.getText()));
                    errMsgIp.setVisible(false);
                    try {
                        frame.setContentPane(a);
                        frame.revalidate();
                    } catch (NumberFormatException e1) {}
                } else {
                    errMsgIp.setVisible(true);
                    System.out.println("err");
                }
            }
            frame.revalidate();
        });
    
        retour.setBounds(Launcher.width*20/100, Launcher.height*58/100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);
        okBtn.setBounds(Launcher.width*45/100, Launcher.height*58/100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);
    
        container.add(okBtn, DRAG_LAYER);
        container.add(retour,DRAG_LAYER);

        frame.setContentPane(this);
        frame.revalidate();

    }

    boolean testParsePort() {
        String s = textPort.getText();
        if (s.length()>5) return false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)<48 || s.charAt(i)>57) {
                return false;
            }
        }
        return true;
    }

    boolean testParseIp() {
        String s = textIp.getText();
        char[][] t = new char[4][3];
        int cpt = 0;
        int cpt2=0; 
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i)<48 || s.charAt(i)>57 || cpt2>=3) && s.charAt(i)!='.') {
                return false;
            }
            if (!(s.charAt(i)=='.')) {
                t[cpt][cpt2]=s.charAt(i);  
                cpt2++;    
            } else {
                cpt++;
                cpt2=0;
            }
        }
        for (char[] x : t) {
            if (x[0]==0) {
                return false;
            }
            System.out.println(Arrays.toString(x));
        }
        return true;
    }

    public static class CharFilter extends DocumentFilter {
        private String allowedChars;

        public CharFilter(String allowedChars) {
            this.allowedChars = allowedChars;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            for (int i = 0; i < text.length(); i++) {
                if (!allowedChars.contains(String.valueOf(text.charAt(i)))) {
                    return;
                }
            }
            super.insertString(fb, offset, text, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            for (int i = 0; i < text.length(); i++) {
                if (!allowedChars.contains(String.valueOf(text.charAt(i)))) {
                    return;
                }
            }
            super.replace(fb, offset, length, text, attrs);
        }
    }

}