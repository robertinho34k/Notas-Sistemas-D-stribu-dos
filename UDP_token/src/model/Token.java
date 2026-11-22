
package model;

import java.util.UUID;

public class Token {

    private String codigo;
    private long dataCriacao;

    private static final long TEMPO_VALIDADE = 60_000;

    public Token() {
        this.codigo = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        this.dataCriacao = System.currentTimeMillis();
    }

    public String getCodigo() {
        return codigo;
    }

    public long getDataCriacao() {
        return dataCriacao;
    }

    public boolean estaExpirado() {

        long tempoAtual = System.currentTimeMillis();

        return (tempoAtual - dataCriacao) >= TEMPO_VALIDADE;
    }
    
    public long getTempoRestante() {

        long tempoDecorrido =
                System.currentTimeMillis() - dataCriacao;

        return Math.max(0, TEMPO_VALIDADE - tempoDecorrido);
    }
}
