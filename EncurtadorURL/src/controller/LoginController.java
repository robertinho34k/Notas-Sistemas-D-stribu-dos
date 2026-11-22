
package controller;

import view.LoginGUI;
import view.EncurtadorGUI;
import comunicador.ClienteRMI;

import javax.swing.SwingUtilities;

/** classe responsavel por conectar a interface grafica a comunicacao com o servidor*/
public class LoginController {

    private LoginGUI view;
    private ClienteRMI cliente;

    public LoginController(LoginGUI view, ClienteRMI cliente) {

        this.view = view;
        this.cliente = cliente;

        /** determina que ao clicar no botao entrar,
         *	o metodo "realizarLogin()" sera executado */
        this.view.getBotaoEntrar().addActionListener(e -> realizarLogin());
    }

    /** metodo de login
     * pega os dados da interface grafica e
     * solicita a autenticação atraves do ClienteRMI, que encaminha os dados ao servidor*/
    private void realizarLogin() {

        String usuario = view.getUsuario();
        String senha = view.getSenha();

        if (usuario.isEmpty() || senha.isEmpty()) {
            view.exibirMensagem("Preencha todos os campos!");
            return;
        }

        boolean autenticado = cliente.autenticar(usuario, senha);

        if (autenticado) {

            view.exibirMensagem("Login realizado com sucesso!");

            view.dispose();

            SwingUtilities.invokeLater(() -> {

                EncurtadorGUI telaEncurtador = new EncurtadorGUI();

                new EncurtadorController(telaEncurtador, cliente);

                telaEncurtador.setVisible(true);

            });

        } else {

            view.exibirMensagem("Usuario ou senha incorretos!");

        }
    }
}
