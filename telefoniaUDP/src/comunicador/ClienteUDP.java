package comunicador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/** classe de comunicacao UDP do cliente 
 * manda o pacote e espera 5 segundos pela resposta.
 * caso nao seja respondido, chama SocketTimeoutException*/ 
public class ClienteUDP {

    private String enderecoServidor;
    private int portaServidor;

    public ClienteUDP(String enderecoServidor, int portaServidor) {
        this.enderecoServidor = enderecoServidor;
        this.portaServidor = portaServidor;
    }

    public String consultarSessao(String numeroCelular) {

        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);

            InetAddress endereco =
                    InetAddress.getByName(enderecoServidor);

            byte[] dadosEnvio =
                    numeroCelular.getBytes(StandardCharsets.UTF_8);

            DatagramPacket pacoteEnvio = new DatagramPacket(
                    dadosEnvio,
                    dadosEnvio.length,
                    endereco,
                    portaServidor
            );

            socket.send(pacoteEnvio);

            byte[] buffer = new byte[4096];

            DatagramPacket pacoteResposta =
                    new DatagramPacket(buffer, buffer.length);

            socket.receive(pacoteResposta);

            return new String(
                    pacoteResposta.getData(),
                    0,
                    pacoteResposta.getLength(),
                    StandardCharsets.UTF_8
            );

        } catch (SocketTimeoutException e) {

            return "Erro: servidor nao respondeu.";

        } catch (IOException e) {

            return "Erro de comunicacao: " + e.getMessage();
        }
    }
}