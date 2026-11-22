package model;

//esta classe serve como objeto de resposta
public class TokenResposta {

    private String codigo;
    private long tempoRestante;
    private String mensagemErro;

    public TokenResposta(String codigo, long tempoRestante,
                         String mensagemErro) {

        this.codigo = codigo;
        this.tempoRestante = tempoRestante;
        this.mensagemErro = mensagemErro;
    }

    public String getCodigo() {
        return codigo;
    }

    public long getTempoRestante() {
        return tempoRestante;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public boolean foiSucesso() {
        return codigo != null;
    }
}
