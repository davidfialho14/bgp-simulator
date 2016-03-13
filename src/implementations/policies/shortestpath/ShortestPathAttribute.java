package implementations.policies.shortestpath;

import network.Attribute;

public class ShortestPathAttribute implements Attribute {

    Integer length = null;

    /**
     * Creates an invalid shortest-path attribute.
     * Should only be called by the factory to create invalid attributes.
     */
    ShortestPathAttribute() {
    }

    public ShortestPathAttribute(int length) {
        this.length = length;
    }

    @Override
    public boolean isInvalid() {
        return length == null;
    }

    @Override
    public int compareTo(Attribute attribute) {
        ShortestPathAttribute other = (ShortestPathAttribute) attribute;
        return this.length - other.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortestPathAttribute that = (ShortestPathAttribute) o;

        return length != null ? length.equals(that.length) : that.length == null;

    }

    @Override
    public int hashCode() {
        return length != null ? length.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (length == null) {
            return "SP(•)";
        } else {
            return "SP(" + length + ')';
        }
    }
}
