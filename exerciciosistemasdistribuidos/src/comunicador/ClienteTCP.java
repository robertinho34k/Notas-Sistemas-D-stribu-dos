package comunicador;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.Pessoa;

//classe para o controle da comunicação por parte do cliente
public class ClienteTCP {
    private String host;
    private int porta;

    public ClienteTCP(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public Pessoa enviarPessoa(Pessoa pessoa) throws Exception {
        try (Socket socket = new Socket(host, porta);
             ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

            saida.flush();
            saida.writeObject(pessoa);
            saida.flush();

            return (Pessoa) entrada.readObject();
        }
    }
}