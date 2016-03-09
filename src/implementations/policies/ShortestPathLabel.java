package implementations.policies;

import network.Label;

public class ShortestPathLabel implements Label {

    int length;

    public ShortestPathLabel(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortestPathLabel that = (ShortestPathLabel) o;

        return length == that.length;

    }

    @Override
    public int hashCode() {
        return length;
    }
}
