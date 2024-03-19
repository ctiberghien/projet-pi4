package View;

import java.awt.Color;
import java.io.Serializable;

import model.Couleur;

public class Line implements Serializable{
    final int x1;
    final int y1;
    final int x2;
    final int y2;
    final Color color;

    public Line(int x1, int y1, int x2, int y2, Couleur color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        if (color != null) {
            this.color = color.getColor();
        } else {
            this.color = null;
        }
    }
}