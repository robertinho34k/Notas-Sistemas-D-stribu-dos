
package controller;
import model.TokenResposta;

import comunicador.ClienteUDP;
import model.TokenResposta;

public class ClienteController {

    private ClienteUDP clienteUDP;

    public ClienteController() {
        clienteUDP = new ClienteUDP();
    }

    /** metodo de cadastro de pessoa 
     * verifica se os dados estao corretos ou se a duplicacao  */
    public String cadastrarPessoa(String nome, String email) {

        if (nome == null || nome.trim().isEmpty()) {
            return "Informe seu nome completo.";
        }

        if (email == null || email.trim().isEmpty()) {
            return "Informe seu e-mail.";
        }

        nome = nome.trim();
        email = email.trim();

        if (!nome.matches("[\\p{L}]+([ '-][\\p{L}]+)+")) {
            return "Informe seu nome completo corretamente.";
        }

        if (!email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            return "Informe um e-mail válido.";
        }

        String resposta = clienteUDP.cadastrarPessoa(nome, email);

        if (resposta == null) {
            return "Não foi possível conectar ao servidor.";
        }

        switch (resposta) {

            case "CADASTRO_OK":
                return "Cadastro realizado com sucesso!";

            case "CADASTRO_DUPLICADO":
                return "Este e-mail já está cadastrado.";

            case "ERRO_DADOS":
                return "Os dados enviados são inválidos.";
 
            default:
                return "Resposta desconhecida do servidor.";
        }
    }
    
    /** solicita um token ao servidor utilizando o email informado. interpreta a resposta e
     * retorna um objeto TokenResposta com o codigo, tempo restante ou erro  */ 
    public TokenResposta solicitarToken(String email) {

        if (email == null || email.trim().isEmpty()) {

            return new TokenResposta(
                    null, 0, "Informe seu e-mail."
            );
        }

        String resposta = clienteUDP.solicitarToken(email.trim());

        if (resposta == null) {

            return new TokenResposta(
                    null, 0, "Não foi possível conectar ao servidor."
            );
        }

        if (resposta.equals("USUARIO_NAO_CADASTRADO")) {

            return new TokenResposta(
                    null, 0, "Usuário não cadastrado."
            );
        }

        String[] partes = resposta.split("\\|", -1);

        if (partes.length == 3 && partes[0].equals("TOKEN_OK")) {

            try {

                String codigo = partes[1];
                long tempoRestante = Long.parseLong(partes[2]);

                return new TokenResposta(
                        codigo, tempoRestante, null
                );

            } catch (NumberFormatException e) {

                return new TokenResposta(
                        null, 0, "Resposta inválida do servidor."
                );
            }
        }

        return new TokenResposta(
                null, 0, "Erro ao solicitar token."
        );
    }
}
