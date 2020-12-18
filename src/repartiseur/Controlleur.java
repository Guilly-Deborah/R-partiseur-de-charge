package repartiseur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlleur implements Serveur,Controle {
    public static ArrayList<Integer> listePort = new ArrayList<Integer>();
    public static Socket clientSocket;
    private static PrintWriter outServeur;
    private static BufferedReader inClient;
    private static PrintWriter outClient;
    private static BufferedReader inServeur;
    public static ServerSocket socketserver;
    public static Socket socketduserveur;

    @Override
    public boolean addServeur(int port) throws RemoteException {
        listePort.add(port);
        return true;
    }

    @Override
    public boolean remoteServeur(int port) throws RemoteException {
        listePort.remove(port);
        return true;
    }

    public static void main(String[] args) throws IOException {
        int cpt =0;
        Controlleur c1 =new Controlleur();
        c1.addServeur(3000);
        c1.addServeur(3001);
        int nbPort = c1.listePort.size();
        try {
            //Partie Serveur
            socketserver = new ServerSocket(1600);
            System.out.println("Controlleur démarrer sur le port 1600");
            socketduserveur = socketserver.accept();
            System.out.println("Un client est connecté");
            outServeur = new PrintWriter(socketduserveur.getOutputStream());
            inClient = new BufferedReader (new InputStreamReader (socketduserveur.getInputStream()));
            String msg1 = inClient.readLine();
            //Partie client
            if (cpt<=nbPort){
                int port = c1.listePort.get(cpt);
                clientSocket = new Socket("192.168.1.97",port);
                System.out.println("Controlleur connecté sur le serveur du port ".concat(Integer.toString(port)));
                outClient = new PrintWriter(clientSocket.getOutputStream());
                inServeur = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
                outClient.println(msg1);
                outClient.flush();
                //Partie serveur
                String msg2 = inServeur.readLine();
                outServeur.println(msg2);
                outServeur.flush();
                cpt+=1;
            }else{
                cpt=0;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}