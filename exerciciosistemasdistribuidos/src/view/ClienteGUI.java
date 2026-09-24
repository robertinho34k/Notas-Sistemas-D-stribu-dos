package view;

import javax.swing.*;
import java.awt.*;
import controller.ClienteController;
import model.Pessoa;


public class ClienteGUI extends JFrame {
    private JTextField txtNome, txtDataNasc;
    private JTextField txtRetornoNome, txtRetornoEmail, txtRetornoDataNasc;
    private JButton btnEnviar;
    private ClienteController controller;

    //classe de interface gráfica para o cliente
    public ClienteGUI() {
        super("Cliente - Cadastro Académico");
        controller = new ClienteController("localhost", 50000);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 320);
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel(" Nome Completo:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel(" Data Nasc. (dd/MM/yyyy):"));
        txtDataNasc = new JTextField();
        add(txtDataNasc);

        btnEnviar = new JButton("Enviar");
        add(btnEnviar);
        add(new JLabel("")); 

        add(new JLabel("--- Retorno do Servidor ---"));
        add(new JLabel(""));

        add(new JLabel(" Nome:"));
        txtRetornoNome = new JTextField();
        txtRetornoNome.setEditable(false);
        add(txtRetornoNome);

        add(new JLabel(" E-mail:"));
        txtRetornoEmail = new JTextField();
        txtRetornoEmail.setEditable(false);
        add(txtRetornoEmail);

        add(new JLabel(" Data Nasc.:"));
        txtRetornoDataNasc = new JTextField();
        txtRetornoDataNasc.setEditable(false);
        add(txtRetornoDataNasc);

        btnEnviar.addActionListener(e -> enviar());
    }

    //metodo para enviar dados escritos nos fields 
    private void enviar() {
        btnEnviar.setEnabled(false);
        
        //metodo do controller para cadastrar
        controller.cadastrarPessoa(txtNome.getText(), txtDataNasc.getText(), new ClienteController.RespostaCallback() {
            @Override
            public void onSuccess(Pessoa pessoa) {
                if (pessoa != null) {
                    txtRetornoNome.setText(pessoa.getNome());
                    txtRetornoEmail.setText(pessoa.getEmail());
                    txtRetornoDataNasc.setText(pessoa.getDataNascimento());
                }
                btnEnviar.setEnabled(true);
            }

            @Override
            public void onError(String mensagemErro) {
                JOptionPane.showMessageDialog(ClienteGUI.this, mensagemErro, "Aviso", JOptionPane.WARNING_MESSAGE);
                btnEnviar.setEnabled(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteGUI().setVisible(true));
    }
}