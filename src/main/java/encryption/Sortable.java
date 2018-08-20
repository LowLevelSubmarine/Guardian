package encryption;

public class Sortable<T> implements Comparable<Sortable<T>> {

    T key;
    Integer value;

    public Sortable(T key, int value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return this.key;
    }

    public Integer getValue() {
        return this.value;
    }

    @Override
    public int compareTo(Sortable<T> o) {
        if (this.value == o.value) {
            return 0;
        } else if (this.value < o.value) {
            return -1;
        } else {
            return 1;
        }
    }
}
