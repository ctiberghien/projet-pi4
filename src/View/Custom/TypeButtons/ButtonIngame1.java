package View.Custom.TypeButtons;


    
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;
public class ButtonIngame1 extends JButton {
  


    public ButtonIngame1(String m){
        super(m);
        setBackground(new Color(202,178,163));
        setForeground(new Color(30,30,30,200));
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Tahoma", Font.BOLD, 20));
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                getParent().repaint();
                setBackground(new Color(120,120,120,140));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(202,178,163));
                repaint();
            }
        });
    }


}
