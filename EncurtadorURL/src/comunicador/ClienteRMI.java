
package comunicador;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/** classe responsavel por encontrar o servidor e obter acesso aos metodos remotos*/
public class ClienteRMI {

    private ServicoRemoto servico;

    public ClienteRMI() {

        try {
        	
            Registry registro = LocateRegistry.getRegistry("localhost", 1099);

            /** procura o servico de nome "ServicoURL"*/
            servico = (ServicoRemoto) registro.lookup("ServicoURL");

            System.out.println("Cliente conectado ao servidor!");

        } catch (Exception e) {
            System.out.println("Erro ao conectar com o servidor:");
            e.printStackTrace();
        }
    }

    /** metodo de autenticacao
     * recebe usuario e senha e encaminha essas inforcoes ao servidor
     * o servidor verifica e devolve o boolean*/
    public boolean autenticar(String usuario, String senha) {

        try {
            return servico.autenticar(usuario, senha);

        } catch (Exception e) {
            System.out.println("Erro ao realizar autenticacao:");
            e.printStackTrace();
            return false;
        }
    }

    /** encaminha o URL ao servidor e recebe o link encurtado*/
    public String encurtarURL(String url) {

        try {
            return servico.encurtarURL(url);

        } catch (Exception e) {
            System.out.println("Erro ao encurtar URL:");
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean estaConectado() {
        return servico != null;
    }
}
