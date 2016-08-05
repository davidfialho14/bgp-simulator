package policies.shortestpath;

import network.Link;
import core.Attribute;
import core.Label;

import static core.InvalidAttribute.invalid;

public class ShortestPathLabel implements Label {

    int length;

    public ShortestPathLabel(int length) {
        this.length = length;
    }

    /**
     * Return the label's length.
     *
     * @return label's length.
     */
    public int getLength() {
        return length;
    }

    /**
     * It returns a ShortestPathAttribute with the length equal to the sum of the given attribute length and the
     * label length.
     * @param link link used to extend.
     * @param attribute attribute to be extended.
     * @return extended attribute.
     */
    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute.isInvalid()) {
            return invalid();
        } else {
            ShortestPathAttribute shortestPathAttribute = (ShortestPathAttribute) attribute;
            return new ShortestPathAttribute(length + shortestPathAttribute.getLength());
        }
    }

    /**
     * Two SP labels are equal if they both have the same length.
     */
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

    @Override
    public String toString() {
        return "SPLabel(" + length + ')';
    }
}
