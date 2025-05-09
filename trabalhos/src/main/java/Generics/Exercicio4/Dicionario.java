package Generics.Exercicio4;

import java.util.ArrayList;
import java.util.List;

public class Dicionario<T> {
    private List<ChaveValor<T>> itens;

    public Dicionario() {
        this.itens = new ArrayList<>();
    }

    public boolean add(String chave, T valor) {
        for (ChaveValor<T> item : itens) {
            if (item.getChave().equals(chave)) {
                return false;
            }
        }
        itens.add(new ChaveValor<>(chave, valor));
        return true;
    }

    public ChaveValor<T> buscar(String chave) {
        for (ChaveValor<T> item : itens) {
            if (item.getChave().equals(chave)) {
                return item;
            }
        }
        return null;
    }
}
