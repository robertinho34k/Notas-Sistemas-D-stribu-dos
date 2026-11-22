
package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public LoginGUI() {

        setTitle("Encurtador de URLs - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel labelUsuario = new JLabel("Usuario:");
        campoUsuario = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        botaoEntrar = new JButton("Entrar");

        painel.add(labelUsuario);
        painel.add(campoUsuario);

        painel.add(labelSenha);
        painel.add(campoSenha);

        painel.add(new JLabel());
        painel.add(botaoEntrar);

        add(painel);
    }

    public String getUsuario() {
        return campoUsuario.getText();
    }

    public String getSenha() {
        return new String(campoSenha.getPassword());
    }

    public JButton getBotaoEntrar() {
        return botaoEntrar;
    }

    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
