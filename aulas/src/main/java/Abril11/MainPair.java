package Abril11;

public class MainPair {

    public static void main(String[] args) {
        Pair<Integer, String> p1 = new Pair<>(1, "Apple");
        Pair<Integer, String> p2 = new Pair<>(2, "Pear");
        boolean same = Util.compare(p1, p2);
        // boolean same = Util.<Integer, String>compare(p1, p2);
        System.out.println(same);

        // Versão igual
        // Pair<Integer, String> p1 = new Pair<>(1, "Apple");
        // Pair<Integer, String> p2 = new Pair<>(1, "Apple");
        // boolean same = Util.<Integer, String>compare(p1, p2);
        // System.out.println(same);
    }
}
