package Generics.Exercicio3;

public class Main {
    public static void main(String[] args) {
        Pedido<ProdutoX> pedido = new Pedido<>();

        ProdutoX p1 = new ProdutoX("Camiseta", 59.90);
        ProdutoX p2 = new ProdutoX("Tênis", 229.99);

        pedido.adicionarProduto(p1);
        pedido.adicionarProduto(p2);

        pedido.mostrarProdutos();
    }
}
