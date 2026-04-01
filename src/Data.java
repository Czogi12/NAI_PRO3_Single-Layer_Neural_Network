import java.util.Arrays;
import java.util.Objects;

public record Data(String label, double[] vector) implements Comparable<Data> {
    @Override
    public String toString() {
        return "Data{" + "label=" + label + ", vector=" + Arrays.toString(vector) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return label.equals(data.label) && Objects.deepEquals(vector, data.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, Arrays.hashCode(vector));
    }

    @Override
    public int compareTo(Data o) {
        var labelCompare = label.compareTo(o.label);
        if (labelCompare != 0) return labelCompare;
        return Double.compare(vector.length, o.vector.length);
    }
}
