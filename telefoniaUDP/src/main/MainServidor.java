package main;

import model.TabelaHash;
import utilidades.Util;
import comunicador.ServidorUDP;

/** classe de inicializacao do servidor, 
 * carrega a tabela hash e diz quantas sessoes estao cadastradas
 */
public class MainServidor {

    public static void main(String[] args) {

        TabelaHash tabelaHash = new TabelaHash();

        String resposta = Util.carregarArquivoEmHashMap(
                "telephony_sessions.txt", tabelaHash);

        if (!resposta.isEmpty()) {
            System.out.println(resposta);
            return;
        }

        System.out.println("Base de dados carregada com sucesso");

        System.out.println("Quantidade de sessoes: "
                + tabelaHash.quantidadeSessoes());

        ServidorUDP servidor = new ServidorUDP(
                50000, tabelaHash);


        servidor.iniciar();
    }
}