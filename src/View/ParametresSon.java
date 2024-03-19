package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import app.Launcher;
import app.Parametre;

public class ParametresSon extends MenuBgCustom {

    JFrame frame;
    private static JSlider uiSlider;
    private static JSlider sfxSlider;
    private static JSlider masterSlider;
    private static JSlider musicSlider;
    private static JCheckBox uiCb;
    private static JCheckBox musicCb;
    private static JCheckBox masterCb;
    private static JCheckBox sfxCb;
    private static JButton okBtn;
    private static JButton resetBtn;

    public ParametresSon(JFrame frame, int a, int b, int c, int d) {
        super(frame);
        this.frame = frame;

        JPanel container = new JPanel();
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100,
                Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        add(container, POPUP_LAYER);

        // construct components
        uiSlider = new JSlider(-50, 6);
        sfxSlider = new JSlider(-50, 6);
        masterSlider = new JSlider(-50, 6);
        musicSlider = new JSlider(-50, 6);
        uiCb = new JCheckBox("UI");
        musicCb = new JCheckBox("Music");
        masterCb = new JCheckBox("Master");
        sfxCb = new JCheckBox("SFX");
        okBtn = new ButtonMenu("OK");
        resetBtn = new ButtonMenu("Reset");

        uiSlider.setValue(a);
        sfxSlider.setValue(b);
        musicSlider.setValue(c);
        masterSlider.setValue(d);

        // set components properties
        uiSlider.setOrientation(JSlider.HORIZONTAL);
        uiSlider.setMinorTickSpacing(1);
        uiSlider.setPaintTicks(false);
        uiSlider.setPaintLabels(false);
        uiSlider.setOpaque(false);
        sfxSlider.setOrientation(JSlider.HORIZONTAL);
        sfxSlider.setMinorTickSpacing(1);
        sfxSlider.setPaintTicks(false);
        sfxSlider.setPaintLabels(false);
        sfxSlider.setOpaque(false);
        masterSlider.setOrientation(JSlider.HORIZONTAL);
        masterSlider.setMinorTickSpacing(1);
        masterSlider.setPaintTicks(false);
        masterSlider.setPaintLabels(false);
        masterSlider.setOpaque(false);
        musicSlider.setOrientation(JSlider.HORIZONTAL);
        musicSlider.setMinorTickSpacing(10);
        musicSlider.setPaintTicks(false);
        musicSlider.setPaintLabels(false);
        musicSlider.setOpaque(false);
        uiCb.setSelected(true);
        uiCb.setOpaque(false);
        uiCb.setForeground(Color.white);
        sfxCb.setSelected(true);
        sfxCb.setOpaque(false);
        sfxCb.setForeground(Color.white);
        masterCb.setSelected(true);
        masterCb.setOpaque(false);
        masterCb.setForeground(Color.white);
        musicCb.setSelected(true);
        musicCb.setOpaque(false);
        musicCb.setForeground(Color.white);

        // adjust size and set layout
        setPreferredSize(new Dimension(1266, 816));
        setLayout(null);

        // add components
        add(uiSlider, DRAG_LAYER);
        add(sfxSlider, DRAG_LAYER);
        add(masterSlider, DRAG_LAYER);
        add(musicSlider, DRAG_LAYER);
        add(uiCb, DRAG_LAYER);
        add(musicCb, DRAG_LAYER);
        add(masterCb, DRAG_LAYER);
        add(sfxCb, DRAG_LAYER);
        add(okBtn, DRAG_LAYER);
        add(resetBtn, DRAG_LAYER);

        // set component bounds (only needed by Absolute Positioning)
        uiSlider.setBounds(Launcher.width * 2 / 10, Launcher.height * 55 / 100, Launcher.width * 22 / 100,
                Launcher.height * 2 / 100);
        sfxSlider.setBounds(Launcher.width * 55 / 100, Launcher.height * 23 / 100, Launcher.width * 22 / 100,
                Launcher.height * 2 / 100);
        masterSlider.setBounds(Launcher.width * 2 / 10, Launcher.height * 23 / 100, Launcher.width * 22 / 100,
                Launcher.height * 2 / 100);
        musicSlider.setBounds(Launcher.width * 55 / 100, Launcher.height * 55 / 100, Launcher.width * 22 / 100,
                Launcher.height * 2 / 100);

        uiCb.setBounds(Launcher.width * 2 / 10, Launcher.height * 5 / 10, Launcher.width * 4 / 100,
                Launcher.height * 4 / 100);
        sfxCb.setBounds(Launcher.width * 55 / 100, Launcher.height * 18 / 100, Launcher.width * 4 / 100,
                Launcher.height * 4 / 100);
        masterCb.setBounds(Launcher.width * 2 / 10, Launcher.height * 18 / 100, Launcher.width * 5 / 100,
                Launcher.height * 4 / 100);
        musicCb.setBounds(Launcher.width * 55 / 100, Launcher.height * 5 / 10, Launcher.width * 5 / 100,
                Launcher.height * 4 / 100);

        okBtn.setBounds(Launcher.width*60/100, Launcher.height*68/100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);
        resetBtn.setBounds(Launcher.width*30/100, Launcher.height*68/100, 70 * Launcher.width / 1000, 61 * Launcher.height / 1000);

        // listeners
        uiSlider.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                SoundPlayer.uiSound = uiSlider.getValue();
                if (uiSlider.getValue() > masterSlider.getValue()) {
                    masterSlider.setValue(uiSlider.getValue());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        sfxSlider.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                SoundPlayer.sfxSound = sfxSlider.getValue();
                if (sfxSlider.getValue() > masterSlider.getValue()) {
                    masterSlider.setValue(sfxSlider.getValue());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        musicSlider.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                SoundPlayer.musicSound = musicSlider.getValue();
                SoundPlayer.changeVolumeMusic();
                if (musicSlider.getValue() > masterSlider.getValue()) {
                    masterSlider.setValue(musicSlider.getValue());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        masterSlider.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                SoundPlayer.masterSound = masterSlider.getValue();
                if (SoundPlayer.uiSound > SoundPlayer.masterSound) {
                    uiSlider.setValue(SoundPlayer.masterSound);
                    SoundPlayer.uiSound = SoundPlayer.masterSound;
                }
                if (SoundPlayer.sfxSound > SoundPlayer.masterSound) {
                    sfxSlider.setValue(SoundPlayer.masterSound);
                    SoundPlayer.sfxSound = SoundPlayer.masterSound;
                }
                if (SoundPlayer.musicSound > SoundPlayer.masterSound) {
                    musicSlider.setValue(SoundPlayer.masterSound);
                    SoundPlayer.musicSound = SoundPlayer.masterSound;
                    SoundPlayer.changeVolumeMusic();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        uiCb.addActionListener(e -> {
            SoundPlayer.joueSon(2, 0);
            SoundPlayer.uiEnabled = uiCb.isSelected();
            if (uiCb.isSelected()) {
                uiSlider.setEnabled(true);
                if (!masterCb.isSelected()) {
                    masterCb.setSelected(true);
                    masterSlider.setEnabled(true);
                }
            } else {
                uiSlider.setEnabled(false);
            }
        });

        musicCb.addActionListener(e -> {
            SoundPlayer.joueSon(2, 0);
            SoundPlayer.musicEnabled = musicCb.isSelected();
            if (musicCb.isSelected()) {
                musicSlider.setEnabled(true);
                SoundPlayer.joueSon(0, 0);
                SoundPlayer.loop();
                if (!masterCb.isSelected()) {
                    masterCb.setSelected(true);
                    masterSlider.setEnabled(true);
                }
            } else {
                SoundPlayer.stopSon();
                musicSlider.setEnabled(false);
            }
        });

        masterCb.addActionListener(e -> {
            SoundPlayer.joueSon(2, 0);
            SoundPlayer.masterEnabled = masterCb.isSelected();
            if (masterCb.isSelected()) {
                masterSlider.setEnabled(true);
            } else {
                masterSlider.setEnabled(false);
                uiSlider.setEnabled(false);
                sfxSlider.setEnabled(false);
                musicSlider.setEnabled(false);
                uiCb.setSelected(false);
                sfxCb.setSelected(false);
                musicCb.setSelected(false);
                SoundPlayer.stopSon();
            }
        });

        sfxCb.addActionListener(e -> {
            SoundPlayer.joueSon(2, 0);
            SoundPlayer.sfxEnabled = sfxCb.isSelected();
            if (sfxCb.isSelected()) {
                sfxSlider.setEnabled(true);
                if (!masterCb.isSelected()) {
                    masterCb.setSelected(true);
                    masterSlider.setEnabled(true);
                }
            } else {
                sfxSlider.setEnabled(false);
            }
        });

        okBtn.addActionListener(e -> {
            SoundPlayer.joueSon(2, 0);
            Parametre parametre = new Parametre(frame);
            frame.setContentPane(parametre);
            frame.revalidate();
        });

        resetBtn.addActionListener(e -> {
            SoundPlayer.joueSon(2, 0);
            sfxCb.setEnabled(true);
            uiCb.setEnabled(true);
            musicCb.setEnabled(true);
            masterCb.setEnabled(true);
            sfxCb.setSelected(true);
            uiCb.setSelected(true);
            musicCb.setSelected(true);
            masterCb.setSelected(true);
            sfxSlider.setEnabled(true);
            uiSlider.setEnabled(true);
            musicSlider.setEnabled(true);
            masterSlider.setEnabled(true);
            sfxSlider.setValue(0);
            uiSlider.setValue(0);
            musicSlider.setValue(0);
            masterSlider.setValue(0);
            SoundPlayer.sfxEnabled = true;
            SoundPlayer.musicEnabled = true;
            SoundPlayer.uiEnabled = true;
            SoundPlayer.masterEnabled = true;
            SoundPlayer.sfxSound = 0;
            SoundPlayer.musicSound = 0;
            SoundPlayer.uiSound = 0;
            SoundPlayer.masterSound = 0;
            SoundPlayer.joueSon(0, 0);
            SoundPlayer.loop();
        });
    }
}
