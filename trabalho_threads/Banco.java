public class Banco {
    // variavel compartilhada
    private static double saldo_global = 0.0;

    // metodo sincronizado para adicionar saldo para a variavel compartilhada
    // secao critica
    public static synchronized void adicionarSaldo(double valor) {
        saldo_global += valor;
    }

    public static double getSaldo() {
        return saldo_global;
    }
}
