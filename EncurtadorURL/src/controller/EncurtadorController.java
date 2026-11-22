
package controller;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.net.URI;

import comunicador.ClienteRMI;
import view.EncurtadorGUI;

public class EncurtadorController {

    private EncurtadorGUI view;
    private ClienteRMI cliente;

    /** construtor com a view e o cliente para trabalhar com os dois */
    public EncurtadorController(EncurtadorGUI view, ClienteRMI cliente) {

        this.view = view;
        this.cliente = cliente;

        this.view.getBotaoEncurtar().addActionListener(e -> encurtarURL());

        this.view.getBotaoCopiar().addActionListener(e -> copiarURL());
    }

    private void encurtarURL() {

        String url = view.getURL().trim();

        if (url.isEmpty()) {
            view.exibirMensagem("Informe uma URL!");
            return;
        }

        try {
            URI endereco = URI.create(url);

            String protocolo = endereco.getScheme();

            if (!endereco.isAbsolute()
                    || protocolo == null
                    || !(protocolo.equalsIgnoreCase("http")
                    || protocolo.equalsIgnoreCase("https"))
                    || endereco.getHost() == null) {

                view.exibirMensagem("Informe uma URL valida!");
                return;
            }

        } catch (IllegalArgumentException e) {
            view.exibirMensagem("Informe uma URL valida!");
            return;
        }

        String resultado = cliente.encurtarURL(url);

        if (resultado != null) {
            view.setResultado(resultado);
        } else {
            view.exibirMensagem("Nao foi possivel encurtar a URL!");
        }
    }

    /** utiliza as classes "Toolkit" e "StringSelection" para copiar 
     * o link para area de transferencia do usuario */
    private void copiarURL() {

        String resultado = view.getResultado();

        if (resultado.isEmpty()) {
            view.exibirMensagem("Nao existe nenhum link para copiar!");
            return;
        }

        StringSelection selecao = new StringSelection(resultado);

        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(selecao, null);

        view.exibirMensagem("Link copiado com sucesso!");
    }
}
