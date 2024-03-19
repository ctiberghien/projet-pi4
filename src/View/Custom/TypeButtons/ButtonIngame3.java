package View.Custom.TypeButtons;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

public class ButtonIngame3 extends JButton {

    public ButtonIngame3(String m) {
        super(m);
        setBackground(new Color(60, 60, 60, 100));
        setForeground(new Color(130, 130, 130, 200));
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Tahoma", Font.BOLD, 16));
        this.addMouseListener(new MouseInputAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (isEnabled()) {
                    getParent().repaint();
                    setBackground(new Color(120, 120, 120, 140));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(new Color(60, 60, 60, 100));
                    getParent().repaint();
                }
            }

        });
    }

}
