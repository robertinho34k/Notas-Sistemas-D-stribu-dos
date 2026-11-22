
package maincliente;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import comunicador.ClienteRMI;
import controller.LoginController;
import view.LoginGUI;

/**classe de execução do cliente. 
 * Estabelece conxao com o servidor,
 * cria a interface de login
 * e conecta a interface ao controller */
public class Cliente {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ClienteRMI cliente = new ClienteRMI();

            if (!cliente.estaConectado()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Nao foi possivel conectar ao servidor!",
                        "Erro de conexao",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            LoginGUI telaLogin = new LoginGUI();

            new LoginController(telaLogin, cliente);

            telaLogin.setVisible(true);

        });
    }
}
