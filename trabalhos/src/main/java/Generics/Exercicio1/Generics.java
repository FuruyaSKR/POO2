package Generics.Exercicio1;

public class Generics<T> {

    private T dado;

    public Generics(T dado) {
        this.dado = dado;
    }

    public T getDado() {
        return dado;
    }
}
