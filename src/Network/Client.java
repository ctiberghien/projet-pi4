package Network;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import View.AttenteOnline;
import View.PlateauJeu;
import app.InGameView;
import model.ControlPlateauJeuClient;
import model.Coordonnee;
import model.Couleur;
import model.Joueur;
import model.Model;
import model.Pion;
import model.Plateau;
import model.Tuile;
import model.TypeOfPlateau.Plateau1;
import model.TypeOfPlateau.Plateau2;
import model.TypeOfPlateau.Plateau3;

import java.awt.Color;

public class Client implements Serializable{

    public Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private LinkedList<String> messages;
    transient private AttenteOnline a;
    private int nbLabel=1;
    private JFrame frame;
    private int idJoueur;
    ControlPlateauJeuClient control;
    boolean recupPlateau=false;
    boolean premierTour=true;
    public Plateau p;
    public String[] pseudos = new String[2];

    public static LinkedList<String> classements = new LinkedList<>();

    public static Thread recevMsg;

    public Client(JFrame j, AttenteOnline a, int idJoueur) {
        this.a=a;
        frame=j;
        this.idJoueur=idJoueur;
        messages = new LinkedList<>();
        recevMsg = new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println("msgrecu:"+message);
                        System.out.println(message.getBytes().length);
                        messages.add(message);
                        parseMsgRecu();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
            }
        });
    }
        
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public void sendPlateau() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(control.pj.model);
            byte[] bytes = bos.toByteArray();
            //woof  oprnttno oretosfkdofdsojgjojigfsjioijsdgfjdoisjgfwjidfowsgomjqgsefmomqttttttttikdsfersojidertdertdretdretdretdretdertdertdertdretdretdretdretdertdertdertdretdretdretdretdertdertdretdretdretdretdertdertdertdretdretdretdertdertdertdretdretdretdretdertdertdert
            // quelle différence avec un new string(bytes) c'est de lka merde
            //c'est quoi le probleme
            //ca quoi ? rante 9 3hein
            // Jarod il a écrit j'exige -> j'ai xije jsuis choqué
            // il a prouvé l'existence de la dyslexie
            // il a 16 de moyenne mec Nn 1.6 
            // Il sait combien fait 6 * 9 
            // y a un mec en guerre il a 8 villages sur 20 qui ont le meeme nom mdr
            // c'est une famille mec
            // en vrai mec c'est génial ce nouveau systeme de messagerie
            // vscode_chat quoi 
            // discord en pls vscode live share >>> vasy la fini le mode en ligne
            // on va pas encore décaler la deadline
            //jvais faire un parser nsm au moins ca va marcher
            out.println(bytes);
            oos.close();
            bos.close();
        } catch (Exception e) {e.printStackTrace();}
    }   

    public Plateau creePlateau(String s) {
        /*try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Model obj = (Model) ois.readObject();
            System.out.println(obj.j1.pions.size());
            control.pj.model=obj;
            control.pj.tour=idJoueur;
            frame.revalidate();
            ois.close();
            bis.close();
        } catch (Exception e) {e.printStackTrace();}*/

        //Tuile : T;[0,0];[V,V,V,V,V,V];[V,V,V]0
        //Border: B;[0,0];[V,V,V,V,V,V];0
        //Pion :  [0,1];2;[R]

        int type = 1;
        int[] coord = new int[6];
        int[] colorBorder = new int[6];
        int[] colorTriplet = new int[3];
        int idBase = 0;
        int current = 1;
        String a = "";
        Plateau pl = new Plateau();
        LinkedList<Tuile> listErr= new LinkedList<>();
        LinkedList<Coordonnee> coordErr = new LinkedList<>();
        for (char ch : s.toCharArray()) {
            if (ch==';') {
                /*if (current==0) {
                    if (ch=='T') {
                        type=1;
                        current++;
                    }
                }*/
                if (current==1) {
                    int virg = 0;
                    for (int i = 2; i < a.length(); i++) {
                        if (a.charAt(i)==',') {virg=i; break;}
                    }
                    coord[0]=Integer.parseInt(a.substring(1, virg));
                    coord[1]=Integer.parseInt(a.substring(virg+1, a.length()-1));
                    System.out.println("coord:"+coord[0]+"/"+coord[1]);
                    current++;
                    a="";
                }
                else if (current==2) {
                    colorBorder[0]=a.charAt(1);
                    colorBorder[1]=a.charAt(3);
                    colorBorder[2]=a.charAt(5);
                    colorBorder[3]=a.charAt(7);
                    colorBorder[4]=a.charAt(9);
                    colorBorder[5]=a.charAt(11);
                    current++;
                    a="";
                }
                else if (current==3) {
                    if (type==1) {
                        System.out.println(a);
                        colorTriplet[0]=a.charAt(1);
                        colorTriplet[1]=a.charAt(3);
                        colorTriplet[2]=a.charAt(5);
                        idBase=a.charAt(7)-48;
                        System.out.println("idbase:"+idBase);
                    } else {
                        System.out.println(a);
                        idBase=a.charAt(0);
                    }
                    Coordonnee coords = new Coordonnee(coord[0], coord[1]);
                    Couleur[] hexCentral = new Couleur[6];
                    Couleur[] trip = new Couleur[3];
                    int cpt = 0;
                    for (int chr : colorBorder) {
                        System.out.println(chr);
                        if (chr==49) hexCentral[cpt]=Couleur.PERLE2;
                        else if (chr==48) hexCentral[cpt]=Couleur.PERLE1;
                        else if (chr==50) hexCentral[cpt]=Couleur.PERLE3;
                        cpt++;
                    }
                    cpt=0;
                    for (int chr : colorTriplet) {
                        System.out.println(chr);
                        if (chr==49) trip[cpt]=Couleur.PERLE2;
                        else if (chr==48) trip[cpt]=Couleur.PERLE1;
                        else if (chr==50) trip[cpt]=Couleur.PERLE3;
                        cpt++;
                    }
                    Tuile t = new Tuile(hexCentral, trip, idBase);
                    System.out.println("okk");
                    if (!pl.addTuile(coords, t)) {
                        coordErr.add(coords);
                        listErr.add(t);
                    }   
                    a="";
                    current=1;
                }    
            } else {
                a+=ch;
            }
        }
        while (!listErr.isEmpty()) {
            if (pl.addTuile(coordErr.get(0), listErr.get(0))) {
                coordErr.remove(0);
                listErr.remove(0);
            }  
        }
        /*LinkedList<Tuile> resultat = new LinkedList<Tuile>();
        resultat.add(list.get(0));
        coordlist.remove(0);
        for (int j = 0; j < resultat.size(); j++) {
            for (int i = 0; i < coordlist.size(); i++) {
                int x1 = coordlist.get(i).px;
                int y1 = coordlist.get(i).py;
                int x2 = coordlist.get(j).px;
                int y2 = coordlist.get(j).py;
                int r1 = x2-x1;
                int r2 = y2-y1;
                if ((r1==-1 && r2==-1)||(r1==-2 && r2==1)||(r1==-1 && r2==2)||(r1==2 && r2==1)||(r1==2 && r2==-1)||(r1==1 && r2==1)) {
                    resultat.add(list.get(i));
                    coordlist.remove(i);
                    break;
                }
            }
        }
        for (int i = 0; i < resultat.size(); i++) {
            pl.addTuile(coordlist.get(i), resultat.get(i));
        }*/
        return pl;
    }


    public void connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receiveMessages(true);
            System.out.println(socket.toString());
            out.println(a.getJoueur1Label().getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void receiveMessages(boolean x) {
        if (x) recevMsg.start();
        else recevMsg.interrupt();
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<String> getMessages() {
        return messages;
    }

    public void parseMsgRecu() {
        //state puis _ en premier
        if (messages.get(0).charAt(1)=='_') {
            if (messages.get(0).charAt(0)=='b') {
                if (idJoueur==1) control.pj.game.setFin(control.pj.p,control.pj.container, control.pj.model.j1);
                else control.pj.game.setFin(control.pj.p,control.pj.container, control.pj.model.j2);
                messages.pop();
            }
            else if (messages.get(0).charAt(0)=='c') {
                if (classements.isEmpty()) {
                    System.out.println("vide");
                    classements.add(0,messages.get(0).substring(2));
                } else {
                    classements.set(0,messages.get(0).substring(2));
                }
            }
            else {
                switch (messages.get(0).charAt(0)) {
                case '0':
                    if (messages.get(0).substring(messages.get(0).length()-2, messages.get(0).length()).equals("_h")) {
                        System.out.println("ok cond");
                        if (a.nameJ1.equals(messages.get(0).substring(2, messages.get(0).length()-2))) {
                            a.okBtn.setVisible(true);
                            idJoueur=1;
                            System.out.println("ok cond 2");
                        } else {
                            idJoueur=2;
                        }
                        messages.set(0, messages.get(0).substring(0, messages.get(0).length()-2));
                    }
                    setLabels();
                    break;
                case '1':
                    p = creePlateau(messages.get(0).substring(2));
                    p.affichage();
                    startGame();
                    break;
                case '2':
                    if (messages.get(0).substring(2).equals("win")) {
                        if (idJoueur==1) control.pj.game.setFin(control.pj.p,control.pj.container, control.pj.model.j2);
                        else control.pj.game.setFin(control.pj.p,control.pj.container, control.pj.model.j1);
                        messages.pop();
                    } else {
                        regenerePlateau();
                    }
                    break;
                /*default:
                    creePlateau(messages.get(0).getBytes());
                    break;*/
                } 
            }
        }
    }

    public void regenerePlateau() {
        //2_[0,1];2;[R];[-1,0];3;[V, V, R];[-2,2];1;[O, O];[7,1];3;[V, V];[7,2];1;[O, O];[8,0];2;[R, R];tour
        int[][] coord = new int[6][2];
        int[][] color = new int[6][3];
        int[] idPions = new int[6];
        int trucARemplir=0;
        int current = 0;
        String s = "";
        boolean tour = false;
        if (messages.get(0).substring(2).toCharArray()[messages.get(0).substring(2).length()-1]!=';') tour = true;
        for (char car : messages.get(0).substring(2).toCharArray()) {
            if (car==';') {
                if (trucARemplir==0) {
                    System.out.println(s);
                    int virg = 0;
                    for (int i = 2; i < s.length(); i++) {
                        if (s.charAt(i)==',') {virg=i; break;}
                    }
                    coord[current][0]=Integer.parseInt(s.substring(1, virg));
                    coord[current][1]=Integer.parseInt(s.substring(virg+1, s.length()-1));
                }
                else if (trucARemplir==1) {
                    System.out.println(s);
                    idPions[current]=s.charAt(0)-48;
                }
                else if (trucARemplir==2) {
                    System.out.println("color: "+s);
                    if (s.length()==3) {
                        color[current][0]=s.charAt(1);
                        color[current][1]='v';
                        color[current][2]='v';
                    }
                    else if (s.length()==6) {
                        color[current][0]=s.charAt(1);
                        color[current][1]=s.charAt(4);
                        color[current][2]='v';
                    }
                    else if (s.length()==9) {
                        color[current][0]=s.charAt(1);
                        color[current][1]=s.charAt(4);
                        color[current][2]=s.charAt(7);
                    }
                    current++;
                }
                trucARemplir+=1;
                trucARemplir%=3;
                s="";
            }
            else s+=car;
        }
        if (tour) {
            control.pj.p.tour=Integer.parseInt(s);
            control.pj.game.textTour.setText("Tour de " + (pseudos[control.pj.p.tour%2]) + " : ");
        }
        Coordonnee[] tabCoordonnees = new Coordonnee[6];
        for (int i = 0; i < 6; i++) {
            tabCoordonnees[i] = new Coordonnee(coord[i][0], coord[i][1]);
        }
        for (Coordonnee c : p.plat_jeu.keySet()) {
            if (p.plat_jeu.get(c) != null) {
                if (p.plat_jeu.get(c).pion != null) {
                    control.pj.remove(p.plat_jeu.get(c).pion.pionView);
                    p.plat_jeu.get(c).pion = null;
                }
            }
        }
        Stack<Couleur> st1 = new Stack<Couleur>();
        Stack<Couleur> st2 = new Stack<Couleur>();
        Stack<Couleur> st3 = new Stack<Couleur>();
        Stack<Couleur> st4 = new Stack<Couleur>();
        Stack<Couleur> st5 = new Stack<Couleur>();
        Stack<Couleur> st6 = new Stack<Couleur>();
        int cpt = 0;
        for (int[] ctab : color){
            Stack<Couleur> st = new Stack<>();
            for (int ch : ctab) {
                System.out.println(ch);
                switch (ch) {
                    case 49:
                        st.push(Couleur.PERLE2);
                        break;
                    case 50:
                        st.push(Couleur.PERLE3);
                        break;
                    case 48:
                        st.push(Couleur.PERLE1);
                        break;
                }
            }
            switch (cpt) {
                case 0:
                    st1=st;
                    break;
                case 1:
                    st2=st;
                    break;
                case 2:
                    st3=st;
                    break;
                case 3:
                    st4=st;
                    break;
                case 4:
                    st5=st;
                    break;
                case 5:
                    st6=st;
                    break;
            }
            cpt++;
        }
        control.pj.model.j2.pions.clear();
        control.pj.model.j1.pions.clear();
        Pion p1;
        Pion p2;
        Pion p3;
        Pion p4;
        Pion p5;
        Pion p6;
        System.out.println(Arrays.toString(idPions));
        if (idPions[0]>3) {
            p1 = new Pion(control.pj.model.j2, st1, new Coordonnee(tabCoordonnees[0].px, tabCoordonnees[0].py), idPions[0]);
        } else {
            p1 = new Pion(control.pj.model.j1, st1, new Coordonnee(tabCoordonnees[0].px, tabCoordonnees[0].py), idPions[0]);
        }
        if (idPions[1]>3) {
            p2 = new Pion(control.pj.model.j2, st2, new Coordonnee(tabCoordonnees[1].px, tabCoordonnees[1].py), idPions[1]);
        } else {
            p2 = new Pion(control.pj.model.j1, st2, new Coordonnee(tabCoordonnees[1].px, tabCoordonnees[1].py), idPions[1]);
        }
        if (idPions[2]>3) {
            p3 = new Pion(control.pj.model.j2, st3, new Coordonnee(tabCoordonnees[2].px, tabCoordonnees[2].py), idPions[2]);
        } else {
            p3 = new Pion(control.pj.model.j1, st3, new Coordonnee(tabCoordonnees[2].px, tabCoordonnees[2].py), idPions[2]);
        }
        if (idPions[3]>3) {
            p4 = new Pion(control.pj.model.j2, st4, new Coordonnee(tabCoordonnees[3].px, tabCoordonnees[3].py), idPions[3]);
        } else {
            p4 = new Pion(control.pj.model.j1, st4, new Coordonnee(tabCoordonnees[3].px, tabCoordonnees[3].py), idPions[3]);
        }
        if (idPions[4]>3) {
            p5 = new Pion(control.pj.model.j2, st5, new Coordonnee(tabCoordonnees[4].px, tabCoordonnees[4].py), idPions[4]);
        } else {
            p5 = new Pion(control.pj.model.j1, st5, new Coordonnee(tabCoordonnees[4].px, tabCoordonnees[4].py), idPions[4]);
        }
        if (idPions[5]>3) {
            p6 = new Pion(control.pj.model.j2, st6, new Coordonnee(tabCoordonnees[5].px, tabCoordonnees[5].py), idPions[5]);
        } else {
            p6 = new Pion(control.pj.model.j1, st6, new Coordonnee(tabCoordonnees[5].px, tabCoordonnees[5].py), idPions[5]);
        }
        Pion[] ptab = {p1,p2,p3,p4,p5,p6};
        for (int i = 0; i < ptab.length; i++) {
            p.plat_jeu.get(tabCoordonnees[i]).pion=ptab[i];
        }
        frame.revalidate();
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.revalidate();
            frame.repaint();
        });
        System.out.println("tour:"+p.tour);
        messages.pop();
        control.pj.setPlateauView();
        control.pj.p.affichage();
        control.pj.container.repaint();
        control.pj.revalidate();
    }

    public void startGame() {
        System.out.println("lancements");
        Random rand = new Random();
        int nbPlateau = rand.nextInt(3)+1;
        Plateau p1;
        Joueur j1 = new Joueur(a.getJoueur1Label().getText(), Color.BLUE, 1);
        Joueur j2 = new Joueur(a.getJoueur2Label().getText(), Color.PINK, 2);
        if (nbPlateau==1) {
            p1 = new Plateau1(j1, j2);
        } else if (nbPlateau == 3) {
            p1 = new Plateau3(j1, j2);
        } else {
            p1 = new Plateau2(j1, j2);
        }
        Model model = new Model(p1, j1, j2);
        if (p!=null) {
            model = new Model(p, j1, j2);
        }
        InGameView game = new InGameView(frame, model, null);
        ControlPlateauJeuClient controller = new ControlPlateauJeuClient(game.pan, this);
        control = controller;
        p=game.pan.p;
        game.pan.control=controller;
        System.out.println("id:"+idJoueur);
        if (idJoueur==2) game.pan.p.tour=-1;
        control.pj.game.textTour.setText("Tour de " + (pseudos[0]) + " : ");
        frame.setContentPane(game);
        frame.revalidate();
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.revalidate();
            frame.repaint();
        });
        System.out.println("tour:"+game.pan.p.tour);
        control.pj.setPlateauView();
        control.pj.p.affichage();
        control.pj.container.repaint();
        control.pj.revalidate();
        control.pj.model.j1.nom=pseudos[0];
        control.pj.model.j2.nom=pseudos[1];
        messages.clear();
    }

    public void setLabels() {        
        JLabel j1 = nbLabel==1 ? a.getJoueur1Label() : a.getJoueur2Label();
        System.out.println(messages.get(0).substring(0, 2));
        if (nbLabel==1) {
            j1.setText(messages.get(0).substring(2));
            a.setJoueur1Label(j1);
            pseudos[0]=messages.get(0).substring(2);
        }
        else {
            j1.setText(messages.get(0).substring(2));
            a.setJoueur2Label(j1);
            pseudos[1]=messages.get(0).substring(2);
        }
        nbLabel++;
        a.retour.setVisible(false);
        frame.revalidate();
        messages.clear();
    }

    public int getNbLabel() {
        return nbLabel;
    }

}