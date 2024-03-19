package View.Custom.TypeButtons;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

public class ButtonServer extends JButton {
    public ButtonServer(String m) {
        super(m);
        setBackground(new Color(120, 50, 50, 20));
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
                    setBackground(new Color(120, 50, 50, 20));
                    getParent().repaint();
                }
            }

        });
    }
}
