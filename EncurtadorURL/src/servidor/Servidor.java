package servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/** classe de inciacao do servidor.
 * cria um objeto ServicoRemotoImpl.
 * cria registro RMI  na porta 1099.
 * registra o nome do servico como "ServicoURL"*/
public class Servidor {

    public static void main(String[] args) {

        try {
           
            ServicoRemotoImpl servico = new ServicoRemotoImpl();

            // Inicia o registro RMI na porta 1099
            Registry registro = LocateRegistry.createRegistry(1099);

            // Registra o serviço com um nome
            registro.rebind("ServicoURL", servico);

            System.out.println("Servidor iniciado com sucesso!");
            System.out.println("Aguardando conexoes dos clientes...");

        } catch (Exception e) {
            System.out.println("Erro ao iniciar o servidor:");
            e.printStackTrace();
        }
    }
}
