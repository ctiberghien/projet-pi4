package View.Custom.ScrollBarCustom;

import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import javax.swing.*;

public class ScrollBarUI extends BasicScrollBarUI {
    private Dimension d = new Dimension(10, 10);

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton b = new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }

        };
        b.setVisible(false);
        return b;
    }
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton b = new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }

        };
        b.setVisible(false);
        return b;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(new Color(100, 100, 100, 100));
        g.fillRect(0, 0, w - 5, h - 5);
    }
    

}