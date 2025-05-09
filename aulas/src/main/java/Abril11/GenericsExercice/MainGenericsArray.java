package Abril11.GenericsExercice;

public class MainGenericsArray {

    public static void main(String[] args) {
        Integer[] intArray = { 1, 2, 3 };
        GenericsArray<Integer> genArray = new GenericsArray<>(intArray);
        int count = ArrayUtil.countGreaterThan(genArray.getArray(), 2);
        System.out.println("Maiores que 2: " + count);

        Double[] doubleArray = { 1.34, 2.54, 3.12 };
        GenericsArray<Double> genArray2 = new GenericsArray<>(doubleArray);
        int count2 = ArrayUtil.countGreaterThan(genArray2.getArray(), 2.43);
        System.out.println("Maiores que 2.43: " + count2);

        Character[] charArray = { 'a', 'd', 'b', 'f', 'c' };
        GenericsArray<Character> genArray3 = new GenericsArray<>(charArray);
        int count3 = ArrayUtil.countGreaterThan(genArray3.getArray(), 'b');
        System.out.println("Maiores que 'b': " + count3);
    }
}
