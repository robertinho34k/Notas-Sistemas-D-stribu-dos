import java.util.List;

public class Filial extends Thread {

    // lista que cada filial recebe
    private List<Double> vendas;

    // resultado da soma das vendas na thread
    private double resultado;

    public Filial(List<Double> vendas) {
        this.vendas = vendas;
    }

    @Override
    public void run() {

        double soma = 0;

        // Calcula a soma localmente
        for (double venda : vendas) {
            soma += venda;
        }

        // Guarda o resultado na instancia do objeto
        resultado = soma;
    }

    public double getResultado() {
        return resultado;
    }
}