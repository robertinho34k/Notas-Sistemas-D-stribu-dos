
package model;

public class URL {

    private String enderecoOriginal;
    private String codigoEncurtado;

    public URL(String enderecoOriginal, String codigoEncurtado) {
        this.enderecoOriginal = enderecoOriginal;
        this.codigoEncurtado = codigoEncurtado;
    }

    public String getEnderecoOriginal() {
        return enderecoOriginal;
    }

    public String getCodigoEncurtado() {
        return codigoEncurtado;
    }

    public void setEnderecoOriginal(String enderecoOriginal) {
        this.enderecoOriginal = enderecoOriginal;
    }

    public void setCodigoEncurtado(String codigoEncurtado) {
        this.codigoEncurtado = codigoEncurtado;
    }
}
