//Contexto: Uma franquia precisa calcular o faturamento total anual somando os dados independentes de suas 4 filiais.
//Requisitos:
//1-Crie 4 listas independentes de números locais, cada um simulando as vendas de uma filial (ex: 10.000 registros por lista).
//2-Dispare 4 threads. Cada thread recebe apenas a lista da sua respectiva filial e calcula a soma localmente.
//3-As threads não podem acessar variáveis globais durante a execução.
//4-A thread principal deve aguardar o fim de todas e somar os 4 resultados finais.
//O que avalia: Conceito de Fork-Join e isolamento. Em Java, avalia o uso de join() com classes que estendem Thread/implementam Runnable (guardando o resultado em um atributo do objeto) ou Future/Callable. Em Python, avalia o uso de threading.Thread com retorno planejado ou concurrent.futures.


import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Listas das 4 filiais
        List<Double> vendas1 = new ArrayList<>();
        List<Double> vendas2 = new ArrayList<>();
        List<Double> vendas3 = new ArrayList<>();
        List<Double> vendas4 = new ArrayList<>();

        // Simulando 1000 vendas em cada filial
        for (int i = 0; i < 1000; i++) {
            vendas1.add(10.0); //10000 +
            vendas2.add(15.0); //15000 +
            vendas3.add(20.0); //20000 +
            vendas4.add(25.0); //25000 + = 70000 -> resultado esperado
        }
    
        // Criacao das threads
        Filial filial1 = new Filial(vendas1);
        Filial filial2 = new Filial(vendas2);
        Filial filial3 = new Filial(vendas3);
        Filial filial4 = new Filial(vendas4);

        // FORK: inicia as 4 threads
        filial1.start();
        filial2.start();
        filial3.start();
        filial4.start();

        // JOIN: aguarda as 4 threads terminarem usando join()
        try {
            filial1.join();
            filial2.join();
            filial3.join();
            filial4.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // A Main soma os resultados
        double faturamentoTotal =
                filial1.getResultado()
                + filial2.getResultado()
                + filial3.getResultado()
                + filial4.getResultado();

        System.out.printf("Faturamento total: R$ %.2f%n", faturamentoTotal);
    }
}
