package Generics.Exercicio2;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Produto<Integer> produtoNumerico = new Produto<>(
                101, 25.90,
                LocalDate.of(2024, 8, 10),
                LocalDate.of(2025, 8, 10));

        Produto<String> produtoAlfanumerico = new Produto<>(
                "A45B-ZX", 79.99,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2025, 5, 1));

        System.out.println(produtoNumerico);
        System.out.println(produtoAlfanumerico);
    }
}
