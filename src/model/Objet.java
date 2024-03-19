package model;
import java.awt.*;
import java.io.Serializable;

import javax.swing.*;

import View.*;
import app.InGameView;

public abstract class Objet implements Serializable, SizeHexagone {
    public String name;
    public int taux;
    public String description;

    //fonction pour les objets à effet immédiat
    public abstract void activate(PlateauJeu m);

    // fonction pour les objet à effet long
    public abstract void setPlateauObjet(PlateauJeu pan, Plateau p, InGameView g);

    // Return un label que vous pouvez customisez comme vous le voulez pour votre objet
    public JLabel getObjetView(int width, int height, boolean inInventaire){
        String s = toString().substring(7);
        String arg;
        if (inInventaire){
            arg = "src/resources/Obj/"+s+"_HQ.png";
        }else {
            arg = "src/resources/Obj/"+s+".png";
        }
        ImageIcon icon = new ImageIcon(arg);
        Image newimg = icon.getImage().getScaledInstance(width/3 * 2, height/3 * 2,
                Image.SCALE_DEFAULT);
        icon.setImage(newimg);
        JLabel obj = new JLabel();
        obj.setIcon(icon);
        return obj;
    }

    public abstract String toString();
}
