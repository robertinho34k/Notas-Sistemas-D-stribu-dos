package controller;

import comunicador.ClienteTCP;
import model.Pessoa;
import javax.swing.SwingUtilities;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.ResolverStyle;

//classe controller do cliente
public class ClienteController {
    private ClienteTCP comunicador;

    public ClienteController(String host, int porta) {
        this.comunicador = new ClienteTCP(host, porta);
    }

    public interface RespostaCallback {
        void onSuccess(Pessoa pessoaRetornada);
        void onError(String mensagemErro);
    }

    public void cadastrarPessoa(String nome, String dataNascimento, RespostaCallback callback) {
        // Validação dos campos antes do envio
        if (nome == null || nome.trim().isEmpty() || !dataNascimento.matches("\\d{2}/\\d{2}/\\d{4}")) {
            callback.onError("Preencha o nome e a data no formato dd/MM/yyyy!");
            return;
        }
        //verifica se a data esta de acordo. evita ex: 55/55/2000
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(dataNascimento, formatter);
        } catch (DateTimeParseException e) {
            callback.onError("Data de nascimento inválida!");
            return;
        }

        // Executa em thread de segundo plano para não congelar a View
        new Thread(() -> {
            try {
                Pessoa pEnviada = new Pessoa(nome, dataNascimento);
                Pessoa pRecebida = comunicador.enviarPessoa(pEnviada);

                SwingUtilities.invokeLater(() -> callback.onSuccess(pRecebida));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> callback.onError("Erro na comunicação: " + ex.getMessage()));
            }
        }).start();
    }
}