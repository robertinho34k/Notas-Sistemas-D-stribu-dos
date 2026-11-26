package model;

import java.util.HashMap;

/** classe da tabela hash, cada numero de celular sera a chave e 
 * o objeto seesao sera o valor.*/
public class TabelaHash {

    private HashMap<String, Sessao> sessoes;

    public TabelaHash() {
        sessoes = new HashMap<>();
    }

    public void adicionarSessao(Sessao sessao) {
        sessoes.put(sessao.getNumeroCelular(), sessao);
    }

    public Sessao pesquisarSessao(String numeroCelular) {
        return sessoes.get(numeroCelular);
    }

    public int quantidadeSessoes() {
        return sessoes.size();
    }

    public void limpar() {
        sessoes.clear();
    }
}