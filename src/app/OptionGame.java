package app;

import javax.swing.*;

import View.SoundPlayer;
import View.Custom.MenuBgCustom;
import View.Custom.TypeButtons.ButtonMenu;
import model.Deck;
import java.awt.*;

public class OptionGame extends MenuBgCustom {
    JFrame frame;
    boolean hasBot;

    OptionGame(JFrame frame1) {
        super(frame1);
        frame = frame1;
        hasBot = false;
        setLayout(null);
        JPanel container = new JPanel();
        container.setBounds(Launcher.width * 15 / 100, Launcher.height * 10 / 100, Launcher.width * 70 / 100,
                Launcher.height * 70 / 100);
        container.setLayout(null);
        container.setBackground(new Color(30, 30, 30, 240));
        JLabel tuiles = new JLabel("Nombre de Tuile : 3", JLabel.CENTER);
        tuiles.setFont(new Font("Tahoma", Font.BOLD, 20));
        tuiles.setForeground(Color.white);

        JSlider sliderTuiles = new JSlider(3, 60);
        sliderTuiles.setOpaque(false);
        sliderTuiles.setForeground(Color.white);

        sliderTuiles.setMinorTickSpacing(3);
        sliderTuiles.setMajorTickSpacing(6);
        sliderTuiles.setPaintTicks(true);
        sliderTuiles.setPaintLabels(true);
        sliderTuiles.setValue(3);
        sliderTuiles.addChangeListener(
                changeEvent -> tuiles.setText("Nombre de Tuiles : " + sliderTuiles.getValue()));

        JLabel pions = new JLabel("Nombre de Pions : 3", JLabel.CENTER);
        pions.setFont(new Font("Tahoma", Font.BOLD, 20));
        pions.setForeground(Color.white);

        JSlider sliderPion = new JSlider(2, 5);
        sliderPion.setMajorTickSpacing(1);
        sliderPion.setOpaque(false);
        sliderPion.setForeground(Color.white);
        sliderPion.setPaintTicks(true);
        sliderPion.setPaintLabels(true);
        sliderPion.setValue(3);

        sliderPion.addChangeListener(changeEvent -> {
            pions.setText("Nombre de Pions : " + sliderPion.getValue());
            if (sliderPion.getValue() == 3)
                sliderTuiles.setMinimum(3);
            else
                sliderTuiles.setMinimum(((sliderPion.getValue() - 1) * 3));
            sliderTuiles.setValue(sliderTuiles.getMinimum());
        });
        JButton retour = new ButtonMenu("Retour");
        retour.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            Menu menu = new Menu(frame);
            frame.setContentPane(menu);

            frame.revalidate();
            frame.repaint();
        });

        JButton hasBotButton = new ButtonMenu("Mode 2 joueurs");
        hasBotButton.addActionListener((event) -> {
            SoundPlayer.joueSon(2, 0);
            if (hasBot) {
                hasBot = false;
                hasBotButton.setText("Mode 2 joueurs ");
            } else {
                hasBot = true;
                hasBotButton.setText("Mode 1 joueur ");
            }
        });

        JSlider sliderTp = new JSlider(1, 10);
        sliderTp.setOpaque(false);
        sliderTp.setVisible(false);
        sliderTp.setForeground(Color.white);

        sliderTp.setMinorTickSpacing(1);
        sliderTp.setMajorTickSpacing(5);
        sliderTp.setPaintTicks(true);
        sliderTp.setPaintLabels(true);
        sliderTp.setValue(4);

        JCheckBox hasTeleporteur = new JCheckBox();
        hasTeleporteur.addChangeListener(changeEvent -> {
            if (hasTeleporteur.isSelected()){
                sliderTp.setVisible(true);
                hasTeleporteur.setText("Teleporteur : tout les " + sliderTp.getValue() + " tour(s)");
                if (sliderTuiles.getMinimum()<5){
                    sliderTuiles.setMinimum(5);
                }
            }else {
                hasTeleporteur.setText("Teleporteur");
                sliderTp.setVisible(false);
            }
        });
        hasTeleporteur.setFont(new Font("Tahoma", Font.BOLD, 20));
        hasTeleporteur.setText("Teleporteur");
        hasTeleporteur.setOpaque(false);
        hasTeleporteur.setForeground(Color.white);

        sliderTp.addChangeListener(changeEvent -> hasTeleporteur.setText("Teleporteur : tout les " + sliderTp.getValue() + " tour(s)"));

        JSlider sliderObj = new JSlider(1, 10);
        sliderObj.setOpaque(false);
        sliderObj.setVisible(false);
        sliderObj.setForeground(Color.white);

        sliderObj.setMinorTickSpacing(1);
        sliderObj.setMajorTickSpacing(5);
        sliderObj.setPaintTicks(true);
        sliderObj.setPaintLabels(true);
        sliderObj.setValue(4);

        JCheckBox hasObject = new JCheckBox();
        hasObject.addChangeListener(changeEvent -> {
            if (hasObject.isSelected()){
                sliderObj.setVisible(true);
                hasObject.setText("Objet : tout les " + sliderObj.getValue() + " tour(s)");
                if (sliderTuiles.getMinimum()<5){
                    sliderTuiles.setMinimum(5);
                }
            }else {
                hasObject.setText("Objet");
                sliderObj.setVisible(false);
            }
        });
        hasObject.setFont(new Font("Tahoma", Font.BOLD, 20));
        hasObject.setText("Objet");
        hasObject.setOpaque(false);
        hasObject.setForeground(Color.white);

        sliderObj.addChangeListener(changeEvent -> hasObject.setText("Objet : tout les " + sliderObj.getValue() + " tour(s)"));

        JButton valider = new ButtonMenu("Valider");
        valider.addActionListener(event -> {
            SoundPlayer.joueSon(2, 0);
            CreationPlateau creationpanel = new CreationPlateau(frame, sliderTuiles.getValue() - 1,
                    sliderPion.getValue(), hasBot, new Deck(sliderTuiles.getValue(), sliderPion.getValue()), false , hasTeleporteur.isSelected(), sliderTp.getValue(), hasObject.isSelected(), sliderObj.getValue());
            frame.setContentPane(creationpanel);
            frame.revalidate();
            frame.repaint();
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


        int xs = container.getBounds().x + container.getBounds().width / 18;
        int ys = container.getBounds().y + container.getBounds().height / 10;
        int slw = container.getBounds().width * 4 / 10;
        int slh = container.getBounds().height * 2 / 10;
        sliderPion.setBounds(xs, ys, slw, slh);
        int x = sliderPion.getBounds().width + sliderPion.getBounds().x + 20;
        pions.setBounds(x, ys, slw, slh);

        ys = ys + container.getBounds().height * 4 / 18;
        sliderTuiles.setBounds(xs, ys, container.getBounds().width * 4 / 10, container.getBounds().height * 2 / 10);
        tuiles.setBounds(x, ys, slw, slh);

        ys = ys + container.getBounds().height * 4 / 18;
        hasTeleporteur.setBounds(xs ,ys,container.getBounds().width * 4 / 10,container.getBounds().height * 2 / 10);
        sliderTp.setBounds(x, ys, container.getBounds().width * 4 / 10, container.getBounds().height * 2 / 10);

        ys = ys + container.getBounds().height * 4 / 18;
        hasObject.setBounds(xs ,ys,container.getBounds().width * 4 / 10,container.getBounds().height * 2 / 10);
        sliderObj.setBounds(x, ys, container.getBounds().width * 4 / 10, container.getBounds().height * 2 / 10);

        int bw = container.getBounds().width / 10;
        int bh = container.getBounds().height / 10;
        x = xs + slw *37/100 - bw / 2;
        int y = ys + container.getBounds().height / 17 + slh;
        retour.setBounds(x, y, bw, bh);
        x += container.getBounds().width / 10 + bw;
        bw += bw;
        hasBotButton.setBounds(x, y, bw, bh);
        x += container.getBounds().width / 10 + bw;
        bw /= 2;
        valider.setBounds(x, y, bw, bh);

        add(sliderTuiles, DRAG_LAYER);
        add(tuiles, DRAG_LAYER);
        add(hasBotButton, DRAG_LAYER);
        add(sliderPion, DRAG_LAYER);
        add(pions, DRAG_LAYER);
        add(hasTeleporteur, DRAG_LAYER);
        add(sliderTp, DRAG_LAYER);
        add(hasObject, DRAG_LAYER);
        add(sliderObj, DRAG_LAYER);

        add(valider, DRAG_LAYER);
        add(retour, DRAG_LAYER);
        add(container, POPUP_LAYER);
    }
}
