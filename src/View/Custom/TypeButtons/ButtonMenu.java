package View.Custom.TypeButtons;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

public class ButtonMenu extends JButton {

    public ButtonMenu(String m) {
        super(m);
        setBackground(new Color(255, 245, 245, 240));
        setForeground(new Color(30, 30, 30, 200));
        setFocusPainted(false);
        setBorderPainted(true);
        setBorder(new LineBorder(new Color(255, 215, 0)));
        setFont(new Font("Tahoma", Font.BOLD, 16));
        this.addMouseListener(new MouseInputAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (isEnabled()) {
                    setBorder(new LineBorder(Color.pink));
                    setBackground(new Color(160, 160, 160, 200));
                    getParent().repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(new Color(255, 255, 255));
                    setBorder(new LineBorder(new Color(255, 215, 0)));
                    getParent().repaint();
                }
            }

        });
    }

}