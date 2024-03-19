package model;

import java.io.Serializable;

public class Coordonnee implements Serializable{
    public int py;
    public int px;

    public Coordonnee(int x, int y) {
        this.px = x;
        this.py = y;
    }

    @Override
    public int hashCode() {
        return (String.valueOf(px) + String.valueOf(py)).hashCode();
    }

    @Override
    public String toString() {
        return "[" + px + "," + py + "]";
    }

    @Override
    public boolean equals(Object c) {
        return ((Coordonnee) c).px == this.px && ((Coordonnee) c).py == this.py;
    }

}
