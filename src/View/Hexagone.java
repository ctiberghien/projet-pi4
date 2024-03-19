package View;

import javax.swing.JComponent;
import java.awt.*;

public abstract class Hexagone extends JComponent implements SizeHexagone {
    Line[] lines;

    @Override
    public boolean contains(int hx, int hy) {
        int[] tx = new int[6];
        int[] ty = new int[6];
        for (int i = 0; i < x.length; i++) {
            tx[i] += HexagoneSize[0] / 2 + x[i];
            ty[i] += HexagoneSize[1] / 2 + y[i];
        }
        Shape tmp = new Polygon(tx, ty, 6);
        return tmp.contains(hy, hx);
    }
}
