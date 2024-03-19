package model;

import java.io.Serializable;

import Network.Client;
import View.PlateauJeu;

public class ControlPlateauJeuClient implements Serializable{
    public PlateauJeu pj;
    public Client c;
    
    public ControlPlateauJeuClient(PlateauJeu pj, Client c) {
        this.pj=pj;
        this.c=c;
    }
}
