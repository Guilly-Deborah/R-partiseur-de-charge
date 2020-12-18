package repartiseur;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Controle extends Remote{
    boolean addServeur (int port) throws RemoteException;
    boolean remoteServeur (int port) throws RemoteException;
}
