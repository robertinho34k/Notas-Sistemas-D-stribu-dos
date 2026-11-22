
package comunicador;

import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
import model.Token;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import model.Pessoa;

public class ServidorUDP {

    private static final int PORTA = 50000;

    /** lista de pessoas cadastradas */
    private List<Pessoa> pessoas = new ArrayList<>();
    
    /** mapa de associacao de email cadastrados a tokens */
    private Map<String, Token> tokensPorEmail = new HashMap<>();

    /**metodo de inicicao do servidor na porta 50000 e aguarda as mensagens dos clientes.
     * cria uma thread para cada solicitação */
    public void iniciarServidor() {
        try {
            DatagramSocket servidor = new DatagramSocket(50000);

            System.out.println("Servidor UDP iniciado na porta 50000...");

            while (true) {
                byte[] buffer = new byte[1024];

                DatagramPacket pacoteRecebido =
                        new DatagramPacket(buffer, buffer.length);

                servidor.receive(pacoteRecebido);

                String mensagem = new String(
                        pacoteRecebido.getData(),
                        pacoteRecebido.getOffset(),
                        pacoteRecebido.getLength(),
                        StandardCharsets.UTF_8
                );

                InetAddress enderecoCliente = pacoteRecebido.getAddress();
                int portaCliente = pacoteRecebido.getPort();

                Thread thread = new Thread(() -> {
                    try {
                        System.out.println(
                                "Solicitação recebida de " + enderecoCliente
                        );

                        String resposta = processarMensagem(mensagem);

                        byte[] dadosResposta =
                                resposta.getBytes(StandardCharsets.UTF_8);

                        DatagramPacket pacoteResposta = new DatagramPacket(
                                dadosResposta,
                                dadosResposta.length,
                                enderecoCliente,
                                portaCliente
                        );

                        synchronized (servidor) {
                            servidor.send(pacoteResposta);
                        }

                    } catch (IOException e) {
                        System.out.println(
                                "Erro ao enviar resposta: " + e.getMessage()
                        );
                    }
                });

                thread.start();
            }

        } catch (IOException e) {
            System.out.println(
                    "Erro ao iniciar servidor: " + e.getMessage()
            );
        }
    }
    
    /** metodo SINCRONIZADO para processar a mensagem recebida pelo servidor
     * identifica o tipo de operacao do cliente 
     * e aplica os outros processos */
    private synchronized String processarMensagem(String mensagem) {

        String[] partes = mensagem.split("\\|", -1);

        if (partes.length == 0) {
            return "ERRO_DADOS";
        }

        switch (partes[0]) {

            case "CADASTRO":
                return processarCadastro(partes);

            case "TOKEN":
                return processarToken(partes);

            default:
                return "OPERACAO_INVALIDA";
        }
    }

    /** metodo para processar o cadastro no servidor
     *  adiciona o nome e o email a lista de cadastrados */
    private String processarCadastro(String[] partes) {

        if (partes.length != 3) {
            return "ERRO_DADOS";
        }

        String nome = partes[1].trim();
        String email = partes[2].trim();

        if (nome.isEmpty() || email.isEmpty()) {
            return "ERRO_DADOS";
        }

        for (Pessoa pessoa : pessoas) {

            if (pessoa.getEmail().equalsIgnoreCase(email)) {
                return "CADASTRO_DUPLICADO";
            }
        }

        Pessoa novaPessoa = new Pessoa(nome, email);

        pessoas.add(novaPessoa);

        System.out.println("Pessoa cadastrada: " + novaPessoa);

        return "CADASTRO_OK";
    }

    /** verifica se o email ja e de uma pessoa cadastrada e fornece um token,
     * caso o token anterior expirou, gera um novo  */
    private String processarToken(String[] partes) {

        if (partes.length != 2 || partes[1].trim().isEmpty()) {
            return "ERRO_DADOS";
        }

        String email = partes[1].trim().toLowerCase(Locale.ROOT);

        boolean encontrado = false;

        for (Pessoa pessoa : pessoas) {

            if (pessoa.getEmail().equalsIgnoreCase(email)) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return "USUARIO_NAO_CADASTRADO";
        }

        Token token = tokensPorEmail.get(email);

        if (token == null || token.estaExpirado()) {

            token = new Token();

            tokensPorEmail.put(email, token);

            System.out.println("Novo token gerado para: " + email);

        } else {

            System.out.println("Token anterior mantido para: " + email);
        }

        return "TOKEN_OK|" + token.getCodigo()
        + "|" + token.getTempoRestante();
    }
}
