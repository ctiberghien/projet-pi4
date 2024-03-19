package View;

import model.Coordonnee;
import model.Couleur;

import java.awt.*;

public class EmptyHexagone extends Hexagone {
    boolean colored;

    public EmptyHexagone(Coordonnee coord) {

        lines = new Line[6];
        for (int i = 0; i < lines.length; i++) {
            if (i != lines.length - 1) {
                lines[i] = new Line(HexagoneSize[0] / 2 + x[i], HexagoneSize[1] / 2 + y[i],
                        HexagoneSize[0] / 2 + x[i + 1], HexagoneSize[1] / 2 + y[i + 1], Couleur.NEUTRAL);
            } else {
                lines[i] = new Line(HexagoneSize[0] / 2 + x[i], HexagoneSize[1] / 2 + y[i], HexagoneSize[0] / 2 + x[0],
                        HexagoneSize[1] / 2 + y[0], Couleur.NEUTRAL);
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : lines) {
            if (line.color != null) {
                g.setColor(line.color);
                ((Graphics2D) g).setStroke(new BasicStroke(2));
                g.drawString("Add", HexagoneSize[0] / 2 - 10, HexagoneSize[1] / 2);
                g.drawLine(line.x1, line.y1, line.x2, line.y2);

            }
        }
    }

}
