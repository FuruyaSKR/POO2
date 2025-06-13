package Junho6.Pagamentos;

import java.text.DecimalFormat;

public class Pagamento implements SistemaPagamento {
    private double valor;

    public Pagamento(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public void processarPagamento() {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Processando o pagamento *interno* de RS " + df.format(this.getValor()));
    }
}
