package controller;

import comunicador.ServidorTCP;
import model.Pessoa;
import view.ServidorGUI;

//classe controller do servidor
public class ServidorController {
    private ServidorTCP servidor;
    private ServidorGUI view;

    public ServidorController(ServidorGUI view, int porta) {
        this.view = view;
        this.servidor = new ServidorTCP(porta, this);
    }

    public void iniciarServidor() {
        servidor.iniciar();
    }

    public void notificarLog(String mensagem) {
        view.adicionarLog(mensagem);
    }

    public void notificarNovoCadastro(Pessoa pessoa) {
        view.atualizarLista(pessoa);
    }
}