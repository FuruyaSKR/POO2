package Generics.Exercicio4;

public class Main {
    public static void main(String[] args) {
        Dicionario<Integer> dicionario = new Dicionario<>();

        System.out.println("Adicionando 'codigo1': " + dicionario.add("codigo1", 100));
        System.out.println("Adicionando 'codigo2': " + dicionario.add("codigo2", 200));

        System.out.println("Adicionando 'codigo1' novamente: " + dicionario.add("codigo1", 999));

        ChaveValor<Integer> resultado = dicionario.buscar("codigo2");
        if (resultado != null) {
            System.out.println("Busca por 'codigo2': " + resultado);
        } else {
            System.out.println("Chave 'codigo2' não encontrada.");
        }

    }
}
