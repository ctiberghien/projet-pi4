package model;

import Error.EmptyMap;
import View.PlateauJeu;

import java.io.Serializable;
import java.util.*;

public class Plateau implements Serializable {

    public Set<Coordonnee> plat_hexagoneB;
    public Map<Coordonnee, Case> plat_jeu;
    public static boolean isJumping;
    // Un pion pourra se déplacer selon les coordonnées d'une case

    public Teleporteur teleporteur;
    public boolean isTPActive;
    public boolean objetActif;
    public int tour = 0;
    public int objTurn;

    public Plateau() {
        plat_hexagoneB = new HashSet<>();
        plat_jeu = new HashMap<>();
        plat_jeu.put(new Coordonnee(0, 0), null);
        Coordonnee[] t = new Coordonnee[plat_jeu.size()];
        plat_jeu.keySet().toArray(t);
        teleporteur = new Teleporteur(this, 0);
        isTPActive = false;
    }

    public Plateau(int nbTp) {
        plat_hexagoneB = new HashSet<>();
        plat_jeu = new HashMap<>();
        plat_jeu.put(new Coordonnee(0, 0), null);
        Coordonnee[] t = new Coordonnee[plat_jeu.size()];
        plat_jeu.keySet().toArray(t);
        teleporteur = new Teleporteur(this, nbTp);
        isTPActive = true;
    }

    public Plateau(Map<Coordonnee, Case> plat, Joueur j1, Joueur j2) {
        this();
        for (Coordonnee c : plat.keySet()) {
            if (plat.get(c).hasTuile()) {
                if (plat.get(c).tuile.getNbBase() == 2) {
                    addTuile(c, new Tuile(plat.get(c).tuile.getNbBase()));
                    LinkedList<Coordonnee> tmp = Plateau.getRandNextCoord(c);
                    plat_jeu.get(tmp.get(0)).pion = new Pion(j1, Couleur.PERLE3, tmp.get(0), 1);
                    plat_jeu.get(tmp.get(1)).pion = new Pion(j1, Couleur.PERLE1, tmp.get(1), 2);
                    plat_jeu.get(tmp.get(2)).pion = new Pion(j1, Couleur.PERLE2, tmp.get(2), 3);
                    j1.pions = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        j1.pions.add(plat_jeu.get(tmp.get(i)).pion);
                    }
                } else if (plat.get(c).tuile.getNbBase() == 1) {
                    addTuile(c, new Tuile(plat.get(c).tuile.getNbBase()));
                    LinkedList<Coordonnee> tmp = Plateau.getRandNextCoord(c);
                    plat_jeu.get(tmp.get(0)).pion = new Pion(j2, Couleur.PERLE3, tmp.get(0), 4);
                    plat_jeu.get(tmp.get(1)).pion = new Pion(j2, Couleur.PERLE1, tmp.get(1), 5);
                    plat_jeu.get(tmp.get(2)).pion = new Pion(j2, Couleur.PERLE2, tmp.get(2), 6);
                    j2.pions = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        j2.pions.add(plat_jeu.get(tmp.get(i)).pion);
                    }
                } else
                    addTuile(new Coordonnee(c.px, c.py), new Tuile(0));
            }
        }
        removeEmptyCase();
    }

    public int getMinKeyXPlat_Jeu() {
        if (plat_jeu.isEmpty()) {
            throw new EmptyMap();
        }
        int min = plat_jeu.keySet().iterator().next().px;
        for (Coordonnee c : plat_jeu.keySet()) {
            if (c.px < min) {
                min = c.px;
            }
        }
        return min;

    }

    public int getMaxKeyXPlat_Jeu() {
        if (plat_jeu.isEmpty()) {
            throw new EmptyMap();
        }
        int max = plat_jeu.keySet().iterator().next().px;
        for (Coordonnee c : plat_jeu.keySet()) {
            if (c.px > max) {
                max = c.px;
            }
        }
        return max;
    }

    public int getMinKeyYPlat_Jeu() {
        if (plat_jeu.isEmpty()) {
            throw new EmptyMap();
        }
        int min = plat_jeu.keySet().iterator().next().py;
        for (Coordonnee c : plat_jeu.keySet()) {
            if (c.py < min) {
                min = c.py;
            }
        }
        return min;
    }

    public int getMaxKeyYPlat_Jeu() {
        if (plat_jeu.isEmpty()) {
            throw new EmptyMap();
        }
        int max = plat_jeu.keySet().iterator().next().py;
        for (Coordonnee c : plat_jeu.keySet()) {
            if (c.py > max) {
                max = c.py;
            }
        }
        return max;
    }

    public Coordonnee getMinCoordonneeTuile() {
        if (plat_jeu.isEmpty()) {
            throw new EmptyMap();
        }
        Coordonnee res = plat_jeu.keySet().iterator().next();
        for (Coordonnee c : plat_jeu.keySet()) {
            if (c.py < res.py) {
                if (c.px <= res.px) {
                    res = c;
                }

            }
        }
        return res;

    }

    public boolean addTuile(Coordonnee coord, Tuile tuile) {

        Coordonnee c1 = new Coordonnee(coord.px, coord.py - 1);
        Coordonnee c2 = new Coordonnee(coord.px + 1, coord.py - 1);
        // correspong au tableau de coord des case de rayon 2 , la ou une autre tuiles
        // peut potentiellement placer
        Coordonnee[] coordonneesOfNextToTuile = {
                new Coordonnee(coord.px - 2, coord.py + 2), new Coordonnee(coord.px - 1, coord.py + 2),
                new Coordonnee(coord.px, coord.py + 2),
                new Coordonnee(coord.px - 2, coord.py + 1), new Coordonnee(coord.px + 1, coord.py + 1),
                new Coordonnee(coord.px - 2, coord.py), new Coordonnee(coord.px + 2, coord.py),
                new Coordonnee(coord.px - 1, coord.py - 1), new Coordonnee(coord.px + 2, coord.py - 1),
                new Coordonnee(coord.px, coord.py - 2), new Coordonnee(coord.px + 1, coord.py - 2),
                new Coordonnee(coord.px + 2, coord.py - 2)
        };
        // si le plateau est vide alors on pourra poser la tuile où on le souhaite
        if (plat_jeu.size() > 1) {

            // on vérifie si le plat_jeu contient ou pas une case au coord ; si celle ci
            // existe et qu'il y a deja une tuile alors on return false;
            if (plat_jeu.get(coord) != null && (plat_jeu.containsKey(coord) && plat_jeu.get(coord).tuile != null)) {
                System.out.println("Une Tuile est deja placé ici");
                return false;
            }
            // on vérifie si il a deja un morceau de tuile sur le plateau d'hexagone
            // composant les tuiles
            if (plat_hexagoneB.contains(coord) || plat_hexagoneB.contains(c1) || plat_hexagoneB.contains(c2)) {
                System.out.println("Impossible de chevauché une autre tuile");
                return false;
            }
            // on vérifie que la tuile touche une autre tuile c'est a dire que l'on vérifie
            // dans son rayon de la ou on peut poser une tuile si il y'en a au moins 1
            boolean NextTo = false;
            for (int i = 0; i < coordonneesOfNextToTuile.length; i++) {
                NextTo = NextTo || (plat_jeu.get(coordonneesOfNextToTuile[i]) != null
                        && plat_jeu.containsKey(coordonneesOfNextToTuile[i])
                        && plat_jeu.get(coordonneesOfNextToTuile[i]).tuile != null);
            }
            if (!NextTo) {
                System.out.println("Votre Tuile doit toucher une autre tuile");
                return false;
            }

        }
        // A partir d'ici une tuile est forcement posable
        // On ajoute l'espace qu'occupe la tuile dans plat_Hexagone
        plat_hexagoneB.add(coord);
        plat_hexagoneB.add(c1);
        plat_hexagoneB.add(c2);
        // puis l'ajoute au plat_jeu
        plat_jeu.put(coord, new Case(tuile));

        // On ajoute egalement les case ou le pion pourra etre posé
        Coordonnee tmpcoord[] = { new Coordonnee(-1, 1), new Coordonnee(0, 1), new Coordonnee(1, 0),
                new Coordonnee(1, -1), new Coordonnee(0, -1), new Coordonnee(-1, 0) };

        for (int i = 0; i < tmpcoord.length; i++) {
            Coordonnee tmp = new Coordonnee(coord.px + tmpcoord[i].px, coord.py + tmpcoord[i].py);
            if (!plat_jeu.containsKey(tmp) || plat_jeu.get(tmp) == null) {
                plat_jeu.put(new Coordonnee(coord.px + tmpcoord[i].px, coord.py + tmpcoord[i].py), new Case(null));
            }
        }

        // On ajoute egalement les case ou une nouvelle tuile peut etre potentiellement
        // placer
        for (int i = 0; i < coordonneesOfNextToTuile.length; i++) {
            Coordonnee tmp = coordonneesOfNextToTuile[i];
            if (!plat_jeu.containsKey(tmp)) {
                plat_jeu.put(tmp, null);
            }
        }

        // On initialise les couleurs des Cases
        plat_jeu.get(coord).couleurBorder = tuile.hexagoneCentral;
        // x-1 ; y+1
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py + 1)).couleurBorder[3] = tuile.hexagoneCentral[0];
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py + 1)).couleurBorder[2] = tuile.triplet[0];

        // x ; y+1
        plat_jeu.get(new Coordonnee(coord.px, coord.py + 1)).couleurBorder[4] = tuile.hexagoneCentral[1];
        plat_jeu.get(new Coordonnee(coord.px, coord.py + 1)).couleurBorder[5] = tuile.triplet[0];

        // x+1 ; y
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py)).couleurBorder[5] = tuile.hexagoneCentral[2];
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py)).couleurBorder[4] = tuile.triplet[1];

        // x+1 ; y-1
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py - 1)).couleurBorder[0] = tuile.hexagoneCentral[3];
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py - 1)).couleurBorder[1] = tuile.triplet[1];

        // x; y-1
        plat_jeu.get(new Coordonnee(coord.px, coord.py - 1)).couleurBorder[1] = tuile.hexagoneCentral[4];
        plat_jeu.get(new Coordonnee(coord.px, coord.py - 1)).couleurBorder[0] = tuile.triplet[2];

        // x-1 ; y
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py)).couleurBorder[2] = tuile.hexagoneCentral[5];
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py)).couleurBorder[3] = tuile.triplet[2];

        return true;
    }

    public void removeEmptyCase() {
        LinkedList<Coordonnee> coordToRemove = new LinkedList<>();

        for (Coordonnee c : plat_jeu.keySet()) {
            if (plat_jeu.get(c) == null) {
                coordToRemove.add(c);
            }
        }
        for (Coordonnee c : coordToRemove) {
            plat_jeu.remove(c);
        }
    }

    public boolean jump(Pion p, Coordonnee c, Coordonnee pointDeSaut) {
        boolean adjacent = isAdjacent(c, pointDeSaut);
        if (plat_jeu.get(c) != null && plat_jeu.get(c).lockedTurn > 0) {
            return false;
        }
        if (adjacent && !isAdjacent(c, p.coordonnee)) {
            plat_jeu.get(p.coordonnee).pion = null;
            p.coordonnee = c;
            plat_jeu.get(c).pion = p;
            return true;
        }
        return false;
    }

    public boolean deplacementPion(Pion p, Coordonnee c) {
        if (p.coordonnee.equals(c))
            return false;
        int x = c.px - p.coordonnee.px;
        int y = c.py - p.coordonnee.py;
        int couleur = -1;
        for (int i = 0; i < 6; i++) {
            if (Case.couleurCoordonneesIndex[i].equals(new Coordonnee(x, y))) {
                couleur = i;
                break;
            }
        }
        if (plat_jeu.get(c).getLockedTurn() > 0) {
            return false;
        }
        if (!p.perles.isEmpty() && couleur != -1) {
            if (p.perles.peek().equals(plat_jeu.get(p.coordonnee).couleurBorder[couleur])
                    && plat_jeu.get(c).pion == null) {
                plat_jeu.get(p.coordonnee).pion = null;
                p.coordonnee = c;
                plat_jeu.get(c).pion = p;
                return true;
            }
        }
        return false;
    }

    public boolean deplacementPionSansContrainteDeCouleur(Pion p, Coordonnee c) {
        if (p.coordonnee.equals(c))
            return false;
        int x = c.px - p.coordonnee.px;
        int y = c.py - p.coordonnee.py;
        boolean nextTo = false;
        for (int i = 0; i < 6; i++) {
            if (Case.couleurCoordonneesIndex[i].equals(new Coordonnee(x, y))) {
                nextTo = true;
            }
        }
        if (plat_jeu.get(c).getLockedTurn() > 0) {
            return false;
        }
        if (nextTo && plat_jeu.get(c).pion == null) {
            if (plat_jeu.get(c).tuile == null
                    || (plat_jeu.get(c).tuile != null && plat_jeu.get(c).tuile.nbBase != p.joueur.getTurn())) {
                plat_jeu.get(p.coordonnee).pion = null;
                p.coordonnee = c;
                plat_jeu.get(c).pion = p;
                return true;
            }
        }

        return false;
    }

    public void deplacementVentilateur(Pion pion, Coordonnee c , Plateau p) {
        if (pion.coordonnee.equals(c))
            return;
        int x = c.px - pion.coordonnee.px;
        int y = c.py - pion.coordonnee.py;
        if (( x > 0 && y == 0) || ( x > 0 && y < 0) || ( x == 0 && y > 0)
                || ( x == 0 && y < 0) || ( x < 0 && y == 0) || ( x < 0 && y > 0)){
            LinkedList<Pion> aBouger = new LinkedList<>();
            int cx = pion.coordonnee.px + x;
            int cy = pion.coordonnee.py + y;
            while (p.getMaxKeyXPlat_Jeu() >= cx && p.getMaxKeyYPlat_Jeu() >= cy && p.getMinKeyXPlat_Jeu() <= cx && p.getMinKeyYPlat_Jeu() <= cy ){
                if (p.plat_jeu.get(new Coordonnee(cx , cy)) != null){
                    if (p.plat_jeu.get(new Coordonnee(cx, cy)).lockedTurn > 0) break;
                    if (p.plat_jeu.get(new Coordonnee(cx , cy)).pion != null){
                        aBouger.add(p.plat_jeu.get(new Coordonnee(cx , cy)).pion);
                    }
                }
                cx +=x;
                cy+=y;
            }
            aBouger.sort(((Comparator<Pion>) (pion1, t1) -> Math.abs(t1.coordonnee.px) - Math.abs(pion1.coordonnee.px))
                    .thenComparing((pion12, t1) -> Math.abs(t1.coordonnee.py) - Math.abs(pion12.coordonnee.py)));
            for (Pion pa: aBouger) {
                Coordonnee tmpcoord = new Coordonnee(pa.coordonnee.px + x,pa.coordonnee.py + y);
                if (p.plat_jeu.get(tmpcoord) != null){
                    if (p.plat_jeu.get(tmpcoord).pion == null){
                        if (p.teleporteur != null && p.teleporteur.pointB != null && p.teleporteur.pointA != null){
                            if (teleporteur.canBeUsed) {
                                if (p.teleporteur.pointA.px == tmpcoord.px && p.teleporteur.pointA.py == tmpcoord.py ){
                                    p.plat_jeu.get(pa.coordonnee).pion = null;
                                    pa.coordonnee = p.teleporteur.pointB;
                                    p.plat_jeu.get(p.teleporteur.pointB).pion = pa;
                                    p.teleporteur.canBeUsed = false;
                                    p.teleporteur.usedNow = true;
                                    p.teleporteur.isUsed = true;
                                    p.teleporteur.tour = -1;
                                }else if (p.teleporteur.pointB.px == tmpcoord.px && p.teleporteur.pointB.py == tmpcoord.py){
                                    p.plat_jeu.get(pa.coordonnee).pion = null;
                                    pa.coordonnee = p.teleporteur.pointA;
                                    p.plat_jeu.get(p.teleporteur.pointA).pion = pa;
                                    p.teleporteur.canBeUsed = false;
                                    p.teleporteur.usedNow = true;
                                    p.teleporteur.isUsed = true;
                                    p.teleporteur.tour = -1;
                                }
                            }
                        }else {
                            if (p.plat_jeu.get(tmpcoord).tuile != null){
                                if (p.plat_jeu.get(tmpcoord).tuile.nbBase == 0){
                                    p.plat_jeu.get(pa.coordonnee).pion = null;
                                    pa.coordonnee = tmpcoord;
                                    p.plat_jeu.get(tmpcoord).pion = pa;
                                }
                            } else {
                                if (p.plat_jeu.get(tmpcoord).lockedTurn == 0){
                                    p.plat_jeu.get(pa.coordonnee).pion = null;
                                    pa.coordonnee = tmpcoord;
                                    p.plat_jeu.get(tmpcoord).pion = pa;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean deplacementPionIA(Pion p, Coordonnee c, LinkedList<Couleur> perleADeplacer) {
        if (p.coordonnee.equals(c))
            return false;
        int x = c.px - p.coordonnee.px;
        int y = c.py - p.coordonnee.py;
        int couleur = -1;
        for (int i = 0; i < 6; i++) {
            if (Case.couleurCoordonneesIndex[i].equals(new Coordonnee(x, y))) {
                couleur = i;
                break;
            }
        }
        if (plat_jeu.get(c).getLockedTurn() > 0) {
            return false;
        }
        if (!p.perles.isEmpty() && couleur != -1) {
            if (p.perles.peek().equals(plat_jeu.get(p.coordonnee).couleurBorder[couleur])
                    && plat_jeu.get(c).pion == null) {
                perleADeplacer.add(p.perles.pop());
                plat_jeu.get(p.coordonnee).pion = null;
                p.coordonnee = c;
                plat_jeu.get(c).pion = p;
                return true;
            }
        }
        return false;
    }

    public static LinkedList<Coordonnee> getRandNextCoord(Coordonnee c) {
        LinkedList<Coordonnee> res = new LinkedList<>();
        Coordonnee[] tmpcoord = { new Coordonnee(-1, 1), new Coordonnee(0, 1), new Coordonnee(1, 0),
                new Coordonnee(1, -1), new Coordonnee(0, -1), new Coordonnee(-1, 0) };
        while (res.size() < 3) {
            Random rand = new Random();
            int r = rand.nextInt(6);
            Coordonnee tmp = new Coordonnee(c.px + tmpcoord[r].px, c.py + tmpcoord[r].py);
            if (!res.contains(tmp)) {
                res.add(tmp);
            }
        }
        return res;
    }

    public boolean isAdjacent(Coordonnee c, Coordonnee c2) {
        int x = c2.px - c.px;
        int y = c2.py - c.py;
        for (int i = 0; i < 6; i++) {
            if (Case.couleurCoordonneesIndex[i].equals(new Coordonnee(x, y))) {
                return true;
            }
        }
        return false;
    }

    public void resetThisTuile(Tuile tuile, Coordonnee coord) {
        // On initialise les couleurs des Cases
        plat_jeu.get(coord).couleurBorder = tuile.hexagoneCentral;
        // x-1 ; y+1
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py + 1)).couleurBorder[3] = tuile.hexagoneCentral[0];
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py + 1)).couleurBorder[2] = tuile.triplet[0];

        // x ; y+1
        plat_jeu.get(new Coordonnee(coord.px, coord.py + 1)).couleurBorder[4] = tuile.hexagoneCentral[1];
        plat_jeu.get(new Coordonnee(coord.px, coord.py + 1)).couleurBorder[5] = tuile.triplet[0];

        // x+1 ; y
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py)).couleurBorder[5] = tuile.hexagoneCentral[2];
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py)).couleurBorder[4] = tuile.triplet[1];

        // x+1 ; y-1
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py - 1)).couleurBorder[0] = tuile.hexagoneCentral[3];
        plat_jeu.get(new Coordonnee(coord.px + 1, coord.py - 1)).couleurBorder[1] = tuile.triplet[1];

        // x; y-1
        plat_jeu.get(new Coordonnee(coord.px, coord.py - 1)).couleurBorder[1] = tuile.hexagoneCentral[4];
        plat_jeu.get(new Coordonnee(coord.px, coord.py - 1)).couleurBorder[0] = tuile.triplet[2];

        // x-1 ; y
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py)).couleurBorder[2] = tuile.hexagoneCentral[5];
        plat_jeu.get(new Coordonnee(coord.px - 1, coord.py)).couleurBorder[3] = tuile.triplet[2];

    }

    public void ajoutObj() {
        Coordonnee[] cases = plat_jeu.keySet().toArray(new Coordonnee[plat_jeu.size()]);
        Collections.shuffle(Arrays.asList(cases));
        int i = 2;
        for (Coordonnee aCase : cases) {
            if (i == 0)
                break;
            // on vérifie s'il n'y a pas d'objet ou de joueur sur la case
            if (plat_jeu.get(aCase).obj == null && plat_jeu.get(aCase).pion == null && teleporteur.pointA != aCase
                    && teleporteur.pointB != aCase && plat_jeu.get(aCase).lockedTurn == 0) {
                // on vérifie si la case n'est pas une base
                if (plat_jeu.get(aCase).hasTuile()) {
                    if (plat_jeu.get(aCase).getTuile().getNbBase() != 0) {
                        continue;
                    }
                }
                plat_jeu.get(aCase).generateObj();
                i--;
            }
        }
    }

    public void deplacementPionTP(Pion p, PlateauJeu pan, boolean tpAvecObj) {
        if (teleporteur.canBeUsed) {
            plat_jeu.get(p.coordonnee).pion = null;
            if (p.coordonnee.equals(teleporteur.pointA)) {
                p.coordonnee = teleporteur.pointB;
                plat_jeu.get(teleporteur.pointB).pion = p;
                if (!tpAvecObj)
                    pan.deplacement.add(teleporteur.pointB);
            } else {
                p.coordonnee = teleporteur.pointA;
                plat_jeu.get(teleporteur.pointA).pion = p;
                if (!tpAvecObj)
                    pan.deplacement.add(teleporteur.pointA);
            }
            teleporteur.canBeUsed = false;
            teleporteur.usedNow = true;
            teleporteur.isUsed = true;
            teleporteur.tour = -1;
        }
    }

    // bloque une case avec l'objet Mur
    public boolean blockCase(Coordonnee c) {
        Case tmp = plat_jeu.get(c);
        if (tmp != null) {
            if (tmp.obj == null) {
                if (tmp.pion == null) {
                    if ((tmp.tuile != null && tmp.tuile.nbBase == 0) || tmp.tuile == null) {
                        tmp.lockedTurn += 6;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean createWall(Coordonnee c) {
        int nbr = 1;
        if (!blockCase(c)) {
            return false;
        }
        Case tmp;
        Coordonnee[] tmpcoord = { new Coordonnee(-1, 1), new Coordonnee(0, 1), new Coordonnee(1, 0),
                new Coordonnee(1, -1), new Coordonnee(0, -1), new Coordonnee(-1, 0) };
        int i = 0;
        while (i < tmpcoord.length && nbr > 0) {
            int alea = (new Random().nextInt(tmpcoord.length) + i) % tmpcoord.length;
            if (blockCase(new Coordonnee(c.px + tmpcoord[alea].px, c.py + tmpcoord[alea].py))) {
                nbr--;
                System.out.println("block");
            }
            i++;
        }
        if (nbr != 3) {
            return true;
        }
        return false;
    }

    public void decreaseBlockCase() {
        Case tmp;
        for (Coordonnee c : plat_jeu.keySet()) {
            tmp = plat_jeu.get(c);
            if (tmp != null) {
                if (tmp.lockedTurn > 0) {
                    System.out.println(tmp.lockedTurn + "" + c);
                    tmp.lockedTurn--;
                }
            }
        }
        System.out.println("He?");
    }

    public String genereString(boolean pion, boolean tourB) {
        String s = "";
        for (Coordonnee c : this.plat_jeu.keySet()) {
            if (plat_jeu.get(c) != null) {
                if (pion) {
                    if (plat_jeu.get(c).pion != null) {
                        s += c + ";";
                        s += plat_jeu.get(c).pion.idPion + ";" + plat_jeu.get(c).pion.toStringPerle() + ";";
                    }
                } else {
                    if (plat_jeu.get(c).tuile != null) {
                        s += c + ";";
                        s += "[";
                        for (int i = 0; i < plat_jeu.get(c).tuile.hexagoneCentral.length; i++) {
                            s += plat_jeu.get(c).tuile.hexagoneCentral[i];
                            if (i != plat_jeu.get(c).tuile.hexagoneCentral.length - 1) {
                                s += ",";
                            }
                        }
                        s += "];[";
                        for (int i = 0; i < plat_jeu.get(c).tuile.triplet.length; i++) {
                            s += plat_jeu.get(c).tuile.triplet[i];
                            if (i != plat_jeu.get(c).tuile.triplet.length - 1) {
                                s += ",";
                            }
                        }
                        s += "]" + plat_jeu.get(c).tuile.nbBase + ";";
                    }
                }
            }
        }
        if (tourB)
            s += tour;
        return s;
    }

    public void affichage() {
        for (Coordonnee c : this.plat_jeu.keySet()) {
            System.out.print(c + " : ");
            if (plat_jeu.get(c) != null) {
                plat_jeu.get(c).affichage();
            } else {
                System.out.println();
            }
        }
        System.out.println();
    }

}