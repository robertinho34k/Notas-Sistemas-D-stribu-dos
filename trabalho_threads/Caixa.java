public class Caixa extends Thread{
    private int numeroCaixa;


    public Caixa(int numeroCaixa) {
        this.numeroCaixa = numeroCaixa;
    }

    @Override
    public void run() {

        // 1000 fichas
        for (int i = 0; i < 1000; i++) {
            //metodo sincronizado
            Banco.adicionarSaldo(10.00);
        }

        System.out.println("Caixa " + numeroCaixa +
                " terminou suas vendas.");
    }
}
