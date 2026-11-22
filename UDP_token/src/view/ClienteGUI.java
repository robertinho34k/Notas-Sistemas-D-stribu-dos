
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.SwingWorker;

import controller.ClienteController;
import model.TokenResposta;

public class ClienteGUI extends JFrame {

    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextField campoToken;

    private JLabel labelTempo;

    private JButton botaoCadastrar;
    private JButton botaoToken;

    private ClienteController controller;

    private Timer timerSolicitacao;
    private Timer timerContador;

    private long tempoRestante = 0;

    private boolean requisicaoEmAndamento = false;

    public ClienteGUI() {

        controller = new ClienteController();

        setTitle("Cadastro e Autenticação UDP");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel labelNome = new JLabel("Nome completo:");
        campoNome = new JTextField();

        JLabel labelEmail = new JLabel("E-mail:");
        campoEmail = new JTextField();

        JLabel labelToken = new JLabel("Token:");
        campoToken = new JTextField();
        campoToken.setEditable(false);

        labelTempo = new JLabel("Tempo restante: --");

        painelCampos.add(labelNome);
        painelCampos.add(campoNome);

        painelCampos.add(labelEmail);
        painelCampos.add(campoEmail);

        painelCampos.add(labelToken);
        painelCampos.add(campoToken);

        painelCampos.add(new JLabel("Validade:"));
        painelCampos.add(labelTempo);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 10));

        botaoCadastrar = new JButton("Cadastrar");
        botaoToken = new JButton("Solicitar token");

        botaoToken.setEnabled(false);

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoToken);

        painel.add(painelCampos, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);

        botaoCadastrar.addActionListener(e -> cadastrar());

        botaoToken.addActionListener(e -> iniciarSolicitacaoToken());

        timerSolicitacao = new Timer(5000, e -> solicitarTokenAutomatico());

        timerContador = new Timer(1000, e -> atualizarContador());
    }

    private void cadastrar() {

        String nome = campoNome.getText();
        String email = campoEmail.getText();

        String resultado = controller.cadastrarPessoa(nome, email);

        JOptionPane.showMessageDialog(this, resultado);

        if (resultado.equals("Cadastro realizado com sucesso!")
                || resultado.equals("Este e-mail já está cadastrado.")) {

            botaoToken.setEnabled(true);
        }
    }

    private void iniciarSolicitacaoToken() {

        if (!timerSolicitacao.isRunning()) {

            timerSolicitacao.start();

            solicitarTokenAutomatico();
        }
    }

    private void solicitarTokenAutomatico() {

        if (requisicaoEmAndamento) {
            return;
        }

        String email = campoEmail.getText().trim();

        requisicaoEmAndamento = true;
        botaoToken.setEnabled(false);

        SwingWorker<TokenResposta, Void> worker =
                new SwingWorker<TokenResposta, Void>() {

            @Override
            protected TokenResposta doInBackground() {

                return controller.solicitarToken(email);
            }

            @Override
            protected void done() {

                try {

                    TokenResposta resposta = get();

                    if (resposta.foiSucesso()) {

                        campoToken.setText(resposta.getCodigo());

                        tempoRestante = resposta.getTempoRestante();

                        atualizarTextoTempo();

                        if (tempoRestante > 0 && !timerContador.isRunning()) {
                            timerContador.start();
                        }

                    } else {

                        timerSolicitacao.stop();
                        timerContador.stop();

                        campoToken.setText("");
                        labelTempo.setText("Tempo restante: --");

                        JOptionPane.showMessageDialog(
                                ClienteGUI.this,
                                resposta.getMensagemErro()
                        );
                    }

                } catch (Exception e) {

                    timerSolicitacao.stop();
                    timerContador.stop();

                    JOptionPane.showMessageDialog(
                            ClienteGUI.this,
                            "Erro ao receber resposta do servidor."
                    );

                } finally {

                    requisicaoEmAndamento = false;
                    botaoToken.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private void atualizarContador() {

        tempoRestante -= 1000;

        if (tempoRestante <= 0) {

            tempoRestante = 0;

            timerContador.stop();
        }

        atualizarTextoTempo();
    }

    private void atualizarTextoTempo() {

        long segundos = (tempoRestante + 999) / 1000;

        labelTempo.setText("Tempo restante: " + segundos + " segundos");
    }
}
