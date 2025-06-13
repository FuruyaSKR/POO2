package Junho6.Pagamentos;

public class Main {
    public static void main(String[] args) {

        Pagamento pagamento = new Pagamento(4.22);
        pagamento.processarPagamento();

        SistemaPagamentoTerceiros pagamento2 = new SistemaPagamentoTerceiros(123.22);
        pagamento2.processarPagamento();
    }
}
