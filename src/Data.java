import java.util.Arrays;
import java.util.Objects;

public record Data(boolean label, double[] vector) implements Comparable<Data> {
    @Override
    public String toString() {
        return "Data{" + "label=" + label + ", vector=" + Arrays.toString(vector) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return label == data.label && Objects.deepEquals(vector, data.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, Arrays.hashCode(vector));
    }

    @Override
    public int compareTo(Data o) {
        if (label == o.label) return 0;
        if (label) {
            return 1;
        }
        return -1;
    }
}
