package policies.shortestpath;

import core.Attribute;

import static core.InvalidAttribute.invalidAttr;

/**
 * Implements any attribute of the shortest path policy.
 */
public class ShortestPathAttribute implements Attribute {

    private int length;

    /**
     * Constructs a SP attribute assigning it the given length.
     * @param length length to be assigned to the new SP attribute.
     */
    public ShortestPathAttribute(int length) {
        this.length = length;
    }

    /**
     * Two SP attributes are compared using their lengths. Attributes with lower lengths
     * are less than attribute with higher lengths.
     */
    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == invalidAttr()) return -1;

        ShortestPathAttribute other = (ShortestPathAttribute) attribute;
        return this.length - other.length;
    }

    /**
     * Two SP attributes are equal if they have the same length.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortestPathAttribute that = (ShortestPathAttribute) o;

        return length == that.length;

    }

    @Override
    public int hashCode() {
        return length;
    }

    @Override
    public String toString() {
        return "SP(" + length + ')';
    }

    /**
     * Returns the length of the SP attribute.
     * @return length of the SP attribute.
     */
    int getLength() {
        return length;
    }
}
