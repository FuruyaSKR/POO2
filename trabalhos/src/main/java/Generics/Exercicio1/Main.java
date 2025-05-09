package Generics.Exercicio1;

public class Main {

    public static void main(String[] args) {
        Generics<String> g1 = new Generics<>("Olá, mundo!");
        System.out.println("Valor String: " + g1.getDado());

        Generics<Integer> g2 = new Generics<>(42);
        System.out.println("Valor Integer: " + g2.getDado());

        Pessoa pessoa = new Pessoa("Renan", 25);
        Generics<Pessoa> g3 = new Generics<>(pessoa);
        System.out.println("Valor Pessoa: " + g3.getDado());
    }

}
