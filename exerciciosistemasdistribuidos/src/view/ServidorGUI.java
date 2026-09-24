package view;

import javax.swing.*;
import java.awt.*;
import controller.ServidorController;
import model.Pessoa;


//classe de interface gráfica para o servidor 
public class ServidorGUI extends JFrame {
    private JTextArea txtLog;
    private DefaultListModel<String> modelListaPessoas;
    private JList<String> listPessoas;
    private ServidorController controller;

    
    public ServidorGUI() {
        super("Servidor - Painel de Controlo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 400);
        setLayout(new GridLayout(1, 2, 5, 5));

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log de Operações"));

        modelListaPessoas = new DefaultListModel<>();
        listPessoas = new JList<>(modelListaPessoas);
        JScrollPane scrollLista = new JScrollPane(listPessoas);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Pessoas Cadastradas na Base"));

        add(scrollLog);
        add(scrollLista);

        // porta 
        controller = new ServidorController(this, 50000);
        controller.iniciarServidor();
    }

    public void adicionarLog(String mensagem) {
        SwingUtilities.invokeLater(() -> txtLog.append(mensagem + "\n"));
    }

    public void atualizarLista(Pessoa pessoa) {
        SwingUtilities.invokeLater(() -> modelListaPessoas.addElement(pessoa.toString()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServidorGUI().setVisible(true));
    }
}