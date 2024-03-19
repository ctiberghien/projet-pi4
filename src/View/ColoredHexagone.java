package View;

import model.Coordonnee;
import model.Couleur;

import java.awt.*;

public class ColoredHexagone extends Hexagone {
    int lockedTurn=0;

    ColoredHexagone(Line[] lines) {
        this.lines = lines;
    }

    public ColoredHexagone(Coordonnee coord, Couleur[] colors) {
        lines = new Line[6];
        for (int i = 0; i < lines.length; i++) {
            if (i != lines.length - 1) {
                lines[i] = new Line(HexagoneSize[0] / 2 + x[i], HexagoneSize[0] / 2 + y[i],
                        HexagoneSize[0] / 2 + x[i + 1], HexagoneSize[1] / 2 + y[i + 1], colors[i]);
            } else {
                lines[i] = new Line(HexagoneSize[0] / 2 + x[i], HexagoneSize[1] / 2 + y[i], HexagoneSize[0] / 2 + x[0],
                        HexagoneSize[1] / 2 + y[0], colors[i]);
            }
        }
    }

    public void setLockedTurn(int lockedTurn) {
        this.lockedTurn = lockedTurn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : lines) {
            if (line.color != null) {
                g.setColor(line.color);
                ((Graphics2D) g).setStroke(new BasicStroke(HexagoneSize[0] / 25));
                g.drawLine(line.x1, line.y1, line.x2, line.y2);
            }
        }
        if (this.lockedTurn > 0) {
            int[] tx = new int[6];
            int[] ty = new int[6];
            for (int i = 0; i < x.length; i++) {
                tx[i] += HexagoneSize[0] / 2 + x[i];
                ty[i] += HexagoneSize[1] / 2 + y[i];
            }

            g.setColor(new Color(10, 10, 10, 200));
            g.fillPolygon(new Polygon(tx, ty, 6));
            g.setColor(Color.white);
            g.setFont(new Font("font", Font.PLAIN, 22));
            g.drawString(String.valueOf(lockedTurn)+"🔒", HexagoneSize[0] / 2-7- String.valueOf(lockedTurn).length()*5, HexagoneSize[1] / 2 + 5);
        }
    }
}
