package Abril11.GenericsExercice;

public class GenericsArray<T extends Comparable<T>> {
    private T[] array;

    public GenericsArray(T[] array) {
        this.array = array;
    }

    public T[] getArray() {
        return this.array;
    }

    public void setArray(T[] array) {
        this.array = array;
    }
}
