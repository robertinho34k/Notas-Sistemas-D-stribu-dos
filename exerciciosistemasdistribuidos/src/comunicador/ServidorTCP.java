package comunicador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import controller.ServidorController;
import model.Pessoa;

public class ServidorTCP {
    private int porta;
    private ServidorController controller;
    private List<Pessoa> listaPessoas;

    //classe para o controle da comunicação por parte do servidor
    public ServidorTCP(int porta, ServidorController controller) {
        this.porta = porta;
        this.controller = controller;
        this.listaPessoas = Collections.synchronizedList(new ArrayList<>());
    }

    public void iniciar() {
        new Thread(() -> {
            try (ServerSocket servidor = new ServerSocket(porta)) {
                controller.notificarLog("Servidor ouvinte na porta: " + porta);

                while (true) {
                    Socket cliente = servidor.accept();
                    String enderecoIP = cliente.getInetAddress().getHostAddress();
                    int portaCliente = cliente.getPort();

                    controller.notificarLog("Cliente conectado no IP: " + enderecoIP + " via porta: " + portaCliente);

                    new Thread(new ClienteHandler(cliente)).start();
                }
            } catch (IOException e) {
                controller.notificarLog("Erro no servidor: " + e.getMessage());
            }
        }).start();
    }

    private class ClienteHandler implements Runnable {
        private Socket cliente;

        public ClienteHandler(Socket cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            try (ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                 ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream())) {

                saida.flush();
                Pessoa p = (Pessoa) entrada.readObject();

                Pessoa pessoaRetorno = null;

                synchronized (listaPessoas) {
                    int index = listaPessoas.indexOf(p);
                    if (index != -1) {
                        pessoaRetorno = listaPessoas.get(index);
                        controller.notificarLog("Pessoa já existente na base: " + pessoaRetorno.getNome());
                    } else {
                        p.gerarEmail();
                        listaPessoas.add(p);
                        pessoaRetorno = p;
                        controller.notificarLog("Nova pessoa cadastrada: " + p.getNome());
                        controller.notificarNovoCadastro(p);
                    }
                }

                saida.writeObject(pessoaRetorno);
                saida.flush();

            } catch (Exception e) {
                controller.notificarLog("Erro ao atender cliente: " + e.getMessage());
            } finally {
                try {
                    cliente.close();
                } catch (IOException ignored) {}
            }
        }
    }
}