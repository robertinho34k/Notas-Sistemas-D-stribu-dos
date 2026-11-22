
package comunicador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ClienteUDP {

    private static final String SERVIDOR = "localhost";
    private static final int PORTA = 50000;
    
    /** Monta e envia uma solicitacao de cadastro ao servidor, 
     * 	contendo o nome completo e o email do usuario */ 
    public String cadastrarPessoa(String nome, String email) {

        String mensagem = "CADASTRO|" + nome + "|" + email;

        return enviarMensagem(mensagem);
    }

    /** envia uma solicitacao de token ao servidor utilizando o email informado
     * e retorna a resposta recebida */ 
    public String solicitarToken(String email) {

    	String mensagem = "TOKEN|" + email;

    	return enviarMensagem(mensagem);
    }
    
    /** envia mensagem ao servidor e aguarda uma resposta. 
     * retorna o conteudo  */
    public String enviarMensagem(String mensagem) {

        try (DatagramSocket cliente = new DatagramSocket()) {
        	
        	cliente.setSoTimeout(5000);
        	
            InetAddress enderecoServidor =
                    InetAddress.getByName(SERVIDOR);

            byte[] dadosEnvio =
                    mensagem.getBytes(StandardCharsets.UTF_8);

            DatagramPacket pacoteEnvio = new DatagramPacket(
                    dadosEnvio,
                    dadosEnvio.length,
                    enderecoServidor,
                    PORTA
            );

            cliente.send(pacoteEnvio);

            byte[] dadosRecebidos = new byte[1024];

            DatagramPacket pacoteRecebido =
                    new DatagramPacket(dadosRecebidos, dadosRecebidos.length);

            cliente.receive(pacoteRecebido);

            String resposta = new String(
                    pacoteRecebido.getData(),
                    pacoteRecebido.getOffset(),
                    pacoteRecebido.getLength(),
                    StandardCharsets.UTF_8
            );

            return resposta;

        } catch (IOException e) {

            System.out.println("Erro na comunicação: " + e.getMessage());

            return null;
        }
    }
}
