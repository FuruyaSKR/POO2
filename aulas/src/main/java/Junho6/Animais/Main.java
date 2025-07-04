package Junho6.Animais;

public class Main {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();

        Zoo animais = new Zoo();

        // animais.addAnimal(new Cachorro());

        Zoo ordem = new Zoo();
        ordem.addAnimal(new Mamifero());

        zoo.addAnimal(animais);
        zoo.addAnimal(ordem);

        zoo.execucao();
    }
}
