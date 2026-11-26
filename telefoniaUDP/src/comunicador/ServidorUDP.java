package comunicador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import model.Sessao;
import model.TabelaHash;


/** classe da funcao do servidor
 * recebe a solicitacao de sessao, pesquisa ela na tabela pelo numero de celular 
 * e manda para o cliente*/
public class ServidorUDP {

    private int porta;
    private TabelaHash tabelaHash;

    public ServidorUDP(int porta, TabelaHash tabelaHash) {
        this.porta = porta;
        this.tabelaHash = tabelaHash;
    }

    /** classe da funcao do servidor
     * recebe a solicitacao de sessao, pesquisa ela na tabela pelo numero de celular 
     * e manda para o cliente*/
    public void iniciar() {

        try (DatagramSocket socket = new DatagramSocket(
                porta, InetAddress.getByName("127.0.0.1"))) {

            System.out.println("Servidor UDP iniciado!");
            System.out.println("Escutando na porta " + porta);

            while (true) {

                byte[] buffer = new byte[4096];

                DatagramPacket pacoteRecebido =
                        new DatagramPacket(buffer, buffer.length);

                socket.receive(pacoteRecebido);

                String numeroCelular = new String(
                        pacoteRecebido.getData(),
                        0,
                        pacoteRecebido.getLength(),
                        StandardCharsets.UTF_8
                ).trim();

                System.out.println("Solicitacao recebida.");

                Sessao sessao =
                        tabelaHash.pesquisarSessao(numeroCelular);

                String resposta;

                if (sessao != null) {
                    resposta = sessao.getDadosSessao();
                } else {
                    resposta = "Numero de celular nao encontrado.";
                }

                byte[] dadosResposta =
                        resposta.getBytes(StandardCharsets.UTF_8);

                DatagramPacket pacoteResposta =
                        new DatagramPacket(
                                dadosResposta,
                                dadosResposta.length,
                                pacoteRecebido.getAddress(),
                                pacoteRecebido.getPort()
                        );

                socket.send(pacoteResposta);

                System.out.println("Resposta enviada.");
            }

        } catch (IOException e) {
            System.out.println("Erro no servidor UDP: "
                    + e.getMessage());
        }
    }
}