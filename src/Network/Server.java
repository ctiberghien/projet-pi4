package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server extends Thread{
    
    private ServerSocket serverSocket;
    private LinkedList<ClientHandler> clients = new LinkedList<>();
    private LinkedList<String> noms = new LinkedList<>();
    private int state=0;
    private boolean isPublicServer=false;
    private LinkedList<String> nomClassement = new LinkedList<>();
    private LinkedList<Integer> nbVictoireClassment = new LinkedList<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public void run() {
        while (true) {
            try {
                if (clients.size()<2) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("test");
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    // Créer un nouveau thread pour gérer chaque client
                    ClientHandler clientHandler = new ClientHandler(clientSocket, false);
                    if (clients.size()==0) {
                        clientHandler = new ClientHandler(clientSocket, true);
                    }
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                }
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
    
    private int choisitPlateau() {
        Random rand = new Random();
        return rand.nextInt(3)+1;
    }

    public String getClassment() {
        String res="c_";
        for (int i = 0; i<nomClassement.size(); i++) {
            res+=nomClassement.get(i);
            res+=";";
            res+=nbVictoireClassment.get(i);
            res+="!";
        }
        return res;
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private boolean isHosting;
        private String nom ="";

        public ClientHandler(Socket socket, boolean isHosting) {
            this.isHosting=isHosting;
            this.clientSocket = socket;
            state=0;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                String inputLine;
                int cpt = 0;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    if (state==0) {
                        System.out.println("taille client:"+clients.size());
                        if (nom.equals("")) nom=inputLine;
                        if (isHosting) {
                            noms.add("0_"+inputLine+"_h");
                            isHosting=false;    
                        } else {
                            noms.add("0_"+inputLine);
                        }
                        System.out.println("taille nom:"+noms.size());
                        System.out.println("inputline: "+inputLine);
                        if (noms.size()==2) {
                            if (noms.get(1).equals(noms.get(0).substring(0, noms.get(0).length()-2))) {
                                noms.removeLast();
                                noms.add(noms.get(0).substring(0, noms.get(0).length()-2)+"2");
                            }
                            sendMessageToAll(noms);
                            state++;
                            System.out.println("state:"+state);
                        }
                    }
                    else if (state==1) {
                        LinkedList<String> l = new LinkedList<>();
                        l.add("1_"+inputLine);
                        sendMessageToAll(l);
                        state++;
                    }
                    else if (state==2) {
                        LinkedList<String> l = new LinkedList<>();
                        l.add("2_"+inputLine);
                        sendMessageToAll(l);
                        if (inputLine.equals("win")) {
                            if (isPublicServer) {
                                ajtClientAuClassement();
                                sendMessageToAll(getClassment());
                            }
                            noms.clear();
                            in.close();
                            out.close();
                            clientSocket.close();
                            clients.clear();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client connection: " + e.getMessage());
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                    clients.remove(this);
                    if (state==2) {
                        clients.getLast().ajtClientAuClassement();
                        sendMessageToAll("b_win");
                        sendMessageToAll(getClassment());
                        noms.clear();
                        in.close();
                        out.close();
                        clientSocket.close();
                        clients.clear();
                    }
                    try {noms.remove("0_"+nom);} catch (Exception e) {}
                    try {noms.remove("0_"+nom+"_h");} catch (Exception e) {}
                } catch (IOException e) {
                    System.out.println("Error closing client connection: " + e.getMessage());
                }
            }
        }

        public void ajtClientAuClassement() {
            boolean existe=false;
            for (int i = 0; i < nomClassement.size(); i++) {
                if (nomClassement.get(i).equals(nom)) {
                    nbVictoireClassment.set(i, nbVictoireClassment.get(i)+1);
                    existe=true;
                }
            }
            if (!existe) {
                nomClassement.addLast(nom);
                nbVictoireClassment.addLast(1);
            }
        }

        private void sendMessage(String s) {
            for (ClientHandler client : clients) {
                if (client==this) {
                    client.out.println(s);
                }
            }
        }

        private void sendMessageToAll(String s) {
            for (ClientHandler client : clients) {
                client.out.println(s);
            }
        }

        private void sendMessageToAll(LinkedList<String> noms) {
            for (ClientHandler client : clients) {
                if (state==2 || state==1) {
                    if (client!=this) {
                        client.out.println(noms.get(0));
                    }
                } else {
                    for (String nom : noms) {
                        client.out.println(nom);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Server s;
        try {
            s = new Server(4242);
            s.isPublicServer=true;
            s.start();
        } catch (IOException e) {
            // TODO AuGIT to-generated catch block
            e.printStackTrace();
        }
    }
}
