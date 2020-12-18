package repartiseur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class Client {
    public static Socket clientSocket;
    public static PrintWriter out;
    public static BufferedReader in;


    public static void main(String[] args) throws RemoteException {
        try{
            clientSocket = new Socket("192.168.1.97",1600);
            System.out.println("Client connecté sur le port 1600");
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Envoie demande de lecture");
            String msg = "Hello World !";
            out.println(msg);
            out.flush();
            String recu = in.readLine();
            if (recu!=null){
                if(recu.equals("true")){
                    System.out.println("Ecriture faite");
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
