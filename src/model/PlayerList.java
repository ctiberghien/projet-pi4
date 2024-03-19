package model;

import java.util.LinkedList;

//A voir si on l'utilise ou pas 

public class PlayerList extends LinkedList<Joueur> {
    int cursor;

    public PlayerList() {
        cursor = 0;
    }

    public Joueur getNext() {
        if (cursor == this.size() - 1) {
            cursor = 0;
        } else {
            cursor++;
        }
        return this.get(cursor);
    }

}