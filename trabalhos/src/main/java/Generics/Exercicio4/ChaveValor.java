package Generics.Exercicio4;

public class ChaveValor<T> {
    private String chave;
    private T valor;

    public ChaveValor(String chave, T valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public String getChave() {
        return chave;
    }

    public T getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return chave + " => " + valor;
    }
}
