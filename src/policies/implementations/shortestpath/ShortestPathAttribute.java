package policies.implementations.shortestpath;

import policies.Attribute;

public class ShortestPathAttribute extends Attribute {

    Integer length = null;

    /**
     * Creates an invalid shortest-path attribute.
     * Should only be called by the factory to create invalid attributes.
     */
    ShortestPathAttribute() {
    }

    // TODO replace all calls to default constructor with the new createInvalid() method
    public static ShortestPathAttribute createInvalidShortestPath() {
        return new ShortestPathAttribute();
    }

    public ShortestPathAttribute(int length) {
        this.length = length;
    }

    @Override
    public int compareTo(Attribute attribute) {
        if (this.isInvalid() && attribute.isInvalid()) return 0;
        else if (this.isInvalid() && !attribute.isInvalid()) return 1;
        else if (!this.isInvalid() && attribute.isInvalid()) return -1;

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
