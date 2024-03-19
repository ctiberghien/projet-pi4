package View.Custom.TypeButtons;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

public class ButtonIngame2 extends JButton {

    public ButtonIngame2(String m) {
        super(m);
        setBackground(new Color(254, 248, 108, 230));
        setForeground(new Color(30, 30, 30, 200));
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Tahoma", Font.BOLD, 20));
        this.addMouseListener(new MouseInputAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                getParent().repaint();
                setBackground(new Color(120, 120, 120, 140));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(254, 248, 108, 230));
            }

        });
    }

}
