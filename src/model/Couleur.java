package model;

import java.awt.*;

public enum Couleur {
    PERLE1, PERLE2, PERLE3, NEUTRAL, PERLE4, PERLE5, BASE1, BASE2;

    /*
     * On a ce tableau qui va se modifier et se récrire selon les paramètres des
     * joueurs On a dans l'ordre
     * Les perles 1 2 3 4 5 et en suite la base 1 puis la base 2
     */

    public static Color[] defaultCouleurs = new Color[] { new Color(243, 0, 36), Color.green,
            new Color(254, 163, 71), Color.pink, Color.blue,
            new Color(10, 60, 155),
            new Color(150, 4, 4) };

    public static Color[] actualCouleurs = new Color[7];

    public String toString() {
        return switch (this) {
            case PERLE1 -> "0";
            case PERLE2 -> "1";
            case PERLE3 -> "2";
            case PERLE4 -> "3";
            case PERLE5 -> "4";
            case BASE1 -> "5";
            case BASE2 -> "6";
            default -> "N";
        };
    }

    public Color getColor() {
        if (this != NEUTRAL) {
            return actualCouleurs[Integer.parseInt(toString())];
        }
        return new Color(255, 228, 181);
    }

}
