
package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

import comunicador.ServicoRemoto;
import dados.Dados;
import model.URL;

public class ServicoRemotoImpl extends UnicastRemoteObject implements ServicoRemoto {

    private static final long serialVersionUID = 1L;

    private Dados Dados;

    public ServicoRemotoImpl() throws RemoteException {
        super();

        Dados = new Dados();
    }

    @Override
    public boolean autenticar(String usuario, String senha) throws RemoteException {

        return Dados.autenticar(usuario, senha);
    }
    
    /** metodo SINCRONIZADO para encurtar o URL.
     * recebe o URL original, gera um hash utilizando SHA-256, 
     * gerando um codigo hexadecimal de 64 caracteres
     * armazena o hash e a URL original no HashMap e devolve o endereço encurtado*/
    @Override
    public synchronized String encurtarURL(String url) throws RemoteException {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String hashGerado;
            int contador = 0;

            do {

                String textoHash = url + contador;

                byte[] hashBytes = digest.digest(
                        textoHash.getBytes(StandardCharsets.UTF_8)
                );

                StringBuilder hash = new StringBuilder();

                for (byte b : hashBytes) {
                    hash.append(String.format("%02x", b));
                }

                hashGerado = hash.toString();

                contador++;

            } while (Dados.existeCodigo(hashGerado)
                    && !Dados.buscarURL(hashGerado)
                    .getEnderecoOriginal().equals(url));

            URL novaURL = new URL(url, hashGerado);

            Dados.salvarURL(novaURL);

            return "https://encurtador.com/" + hashGerado;

        } catch (Exception e) {
            throw new RemoteException("Erro ao gerar hash da URL", e);
        }
    }
}
