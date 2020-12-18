package repartiseur;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Serveur extends Remote{
    int charge = 0;

    static ArrayList<String> lectureFichier(String nomFichier) throws RemoteException {
        return null;
    }

    static boolean ecritureFichier(String nomFichier, String data) throws RemoteException {
        return false;
    }
}
