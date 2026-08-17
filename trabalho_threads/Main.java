public class Main {
    public static void main(String[] args) {

        //cria os caixas/thread
        Caixa caixa1 = new Caixa(1);
        Caixa caixa2 = new Caixa(2);
        Caixa caixa3 = new Caixa(3);
        Caixa caixa4 = new Caixa(4);
        Caixa caixa5 = new Caixa(5);

        // Inicio das 5 threads
        caixa1.start();
        caixa2.start();
        caixa3.start();
        caixa4.start();
        caixa5.start();

        //join faz a main esperar as threads terminarem para depois continuar
        try {
            caixa1.join();
            caixa2.join();
            caixa3.join();
            caixa4.join();
            caixa5.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Exibe o saldo final
        System.out.printf(
                "Saldo central final: R$ %.2f%n",
                Banco.getSaldo()
        );

    }
}
