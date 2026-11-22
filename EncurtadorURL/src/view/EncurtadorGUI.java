
package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EncurtadorGUI extends JFrame {

    private JTextField campoURL;
    private JTextField campoResultado;

    private JButton botaoEncurtar;
    private JButton botaoCopiar;

    public EncurtadorGUI() {

        setTitle("Encurtador de URLs");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel labelURL = new JLabel("URL original:");
        campoURL = new JTextField();

        JLabel labelResultado = new JLabel("URL encurtada:");
        campoResultado = new JTextField();

        campoResultado.setEditable(false);

        botaoEncurtar = new JButton("Encurtar");
        botaoCopiar = new JButton("Copiar link");

        painel.add(labelURL);
        painel.add(campoURL);

        painel.add(labelResultado);
        painel.add(campoResultado);

        painel.add(botaoEncurtar);
        painel.add(botaoCopiar);

        add(painel);
    }

    public String getURL() {
        return campoURL.getText();
    }

    public void setResultado(String resultado) {
        campoResultado.setText(resultado);
    }

    public JButton getBotaoEncurtar() {
        return botaoEncurtar;
    }

    public JButton getBotaoCopiar() {
        return botaoCopiar;
    }

    public void exibirMensagem(String mensagem) {
        javax.swing.JOptionPane.showMessageDialog(this, mensagem);
    }

    public String getResultado() {
        return campoResultado.getText();
    }
}
