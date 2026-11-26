package main;

import java.util.Scanner;
import comunicador.ClienteUDP;

public class MainCliente {

    public static void main(String[] args) {

        ClienteUDP cliente = new ClienteUDP(
                "127.0.0.1", 50000);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Cliente UDP iniciado");

        while (true) {

            System.out.print(
                    "\nInforme o numero do celular (0 para sair): ");

            String numeroCelular = scanner.nextLine().trim();

            if (numeroCelular.equals("0")) {
                break;
            }

            String resposta =
                    cliente.consultarSessao(numeroCelular);

            System.out.println("\nResposta do servidor:");
            System.out.println(resposta);
        }

        scanner.close();

        System.out.println("Cliente encerrado.");
    }
}