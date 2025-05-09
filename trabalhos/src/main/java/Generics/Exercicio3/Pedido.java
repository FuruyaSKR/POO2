package Generics.Exercicio3;

import java.util.ArrayList;
import java.util.List;

public class Pedido<T extends ProdutoX> {
    private List<T> produtos;

    public Pedido() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(T produto) {
        produtos.add(produto);
    }

    public void mostrarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Pedido vazio.");
        } else {
            System.out.println("Produtos no pedido:");
            for (T produto : produtos) {
                System.out.println("- " + produto);
            }
        }
    }
}
