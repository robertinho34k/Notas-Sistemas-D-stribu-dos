package comunicador;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** Interface de definição das funcinalidades do servidor */
public interface ServicoRemoto extends Remote {

    boolean autenticar(String usuario, String senha) throws RemoteException;

    String encurtarURL(String url) throws RemoteException;

}