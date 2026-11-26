package utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import model.Sessao;
import model.TabelaHash;


/** carrega o arquivo recebendo o objeto TabelaHash */
public class Util {

    public static String carregarArquivoEmHashMap(
            String nomeArquivo, TabelaHash tabelaHash) {

        try {

            Path caminho = Paths.get(nomeArquivo);

            List<String> linhas = Files.readAllLines(caminho);

            tabelaHash.limpar();

            for (String linha : linhas) {

                if (!linha.trim().isEmpty()) {

                    String[] dadosLinha = linha.split(";", 2);

                    if (dadosLinha.length == 2) {

                        Sessao sessao = new Sessao(
                                dadosLinha[0].trim(),
                                dadosLinha[1]
                        );

                        tabelaHash.adicionarSessao(sessao);
                    }
                }
            }

            return "";

        } catch (IOException e) {

            return "Erro ao processar o arquivo: "
                    + e.getMessage();
        }
    }
}