package model.Objets;

import java.util.Arrays;
import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import View.PlateauJeu;
import View.SizeHexagone;
import app.InGameView;
import model.Couleur;
import model.Joueur;
import model.Objet;
import model.Pion;
import model.Plateau;

public class MelangeCouleur extends Objet implements SizeHexagone{

    public MelangeCouleur() {
        super();
        this.name = "MelangeCouleur";
        this.taux = 20;
        this.description = "Objet permettant de melanger les perles de l'adversaire";
    }

    @Override
    public void activate(PlateauJeu m) {
        Joueur joueurAMelanger = (m.p.tour%2==0) ? m.model.j1 : m.model.j2;
        int nbPion=0;
        int[] randPion = new int[5];
        randPion[0]=0;
        randPion[1]=0;
        switch (joueurAMelanger.pions.size()) {
            case 2: 
                nbPion=2;
                break;
            case 3: 
                nbPion=3;
                randPion[2]=0; 
                break;
            case 4: 
                nbPion=4;
                randPion[2]=0;
                randPion[3]=0;
                break;
            case 5: 
                nbPion=5;
                randPion[2]=0;
                randPion[3]=0;
                randPion[4]=0;
                break;
        }
        Couleur[] tab = new Couleur[nbPion*2];
        int cpt = 0;
        System.out.println(Arrays.toString(joueurAMelanger.pions.get(0).perles.toArray()));
        System.out.println(Arrays.toString(joueurAMelanger.pions.get(1).perles.toArray()));
        System.out.println(Arrays.toString(joueurAMelanger.pions.get(2).perles.toArray()));
        for (int i = 0; i < joueurAMelanger.pions.size(); i++) {
            Pion current = joueurAMelanger.pions.get(i);
            System.out.println(Arrays.toString(current.perles.toArray()));
            System.arraycopy(current.perles.toArray(), 0, tab, cpt, current.perles.toArray().length);
            cpt+=current.perles.toArray().length;
        }
        int[] pat = pattern(nbPion);
        cpt=0;
        int cpt2=0; 
        for (Pion p : joueurAMelanger.pions) {
            p.perles.clear();
            for (int i = 0; i < pat[cpt]; i++) {
                p.perles.add(tab[cpt2]);
                cpt2++;
            }
            cpt++;
        }
        m.joueurenjeu.sacRemoveItem(new MelangeCouleur());
        m.game.frame.revalidate();
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m.game.frame.revalidate();
            m.game.frame.repaint();
        });
        
        m.setPlateauView();
        m.p.affichage();
        m.container.repaint();
        m.revalidate();
        
    }

    @Override
    public void setPlateauObjet(PlateauJeu pan, Plateau p, InGameView g) {}

    public int[] pattern(int nbPion) {
        int[][] deuxPions = {{2,2},{1,3}};
        int[][] troisPions = {{0,3,3},{2,2,2},{1,2,3}};
        int[][] quatrePions = {{2,2,2,2},{0,2,3,3},{1,2,2,3}};
        int[][] cinqPions = {{2,2,2,2,2},{0,1,3,3,3},{0,2,2,3,3}}; 
        Random rand = new Random();
        int r;
        if (nbPion==2) {
            r = rand.nextInt(2);
        } else {
            r = rand.nextInt(3);
        }
        switch (nbPion) {
            case 2:
                return deuxPions[r];
            case 3:
                return troisPions[r];
            case 4: 
                return quatrePions[r];
            default:
                return cinqPions[r];
        }
    }

    @Override
    public String toString() {
        return "Objet: MelangeCouleur";
    }
}
