package repartiseur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

class ServeurB implements Serveur{
    public static String chaine = "";
    public static ServerSocket socketserver ;
    public static Socket socketduserveur;
    public static PrintWriter out;
    private static BufferedReader in;
    public static ArrayList<String> contenue= new ArrayList<String>();

    //@Override
    public static ArrayList<String> lectureFichier(String nomFichier) throws RemoteException {
        try {
            InputStream ips = new FileInputStream(nomFichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                chaine += ligne + "\n";
                contenue.add(ligne + "\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenue;
    }

    //@Override
    public static boolean ecritureFichier(String nomFichier, String data) throws RemoteException {
        boolean maj = false;
        contenue=lectureFichier(nomFichier);
        try {
            FileWriter fw = new FileWriter (nomFichier);
            BufferedWriter bw = new BufferedWriter (fw);
            PrintWriter fichierSortie = new PrintWriter (bw);
            for (String i : contenue){
                fichierSortie.print(i);
            }
            fichierSortie.print(data);
            fichierSortie.close();
            maj = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maj;
    }
    public static void main(String[] args) throws IOException {
        try{
            socketserver = new ServerSocket(3001);
            System.out.println("Serveur démarrer sur le port 3001");
            socketduserveur = socketserver.accept();
            System.out.println("Un client est connecté");
            out = new PrintWriter(socketduserveur.getOutputStream());
            in = new BufferedReader (new InputStreamReader (socketduserveur.getInputStream()));
            String msg = in.readLine();
            System.out.println("Message : ".concat(msg));
            System.out.println("Message non vide : ".concat(String.valueOf(msg!=null)));
            if(msg!=null){
                System.out.println("Lecture ? ".concat(String.valueOf(msg.equals("lecture"))));
                if (msg.equals("lecture")){
                    System.out.println("Lecture en cours");
                    contenue = lectureFichier("test.txt");
                    System.out.println("Revoie au client la lecture du fichier");
                    out.println(contenue);
                    out.flush();
                }else{
                    System.out.println("Ecriture en cours");
                    boolean fait = ecritureFichier("test.txt",msg);
                    System.out.println("Ecriture faite : ".concat(String.valueOf(fait)));
                    out.println("true");
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
